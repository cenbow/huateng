/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.common.model.Paging;
import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.Site;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.*;
import static com.google.common.base.Preconditions.checkArgument;


/*
* Author: jlchen
* Date: 2012-11-16
*/
@Repository
public class RedisSiteDao extends RedisBaseDao<Site> {

    @Autowired
    public RedisSiteDao(JedisTemplate template) {
        super(template);
    }


    public Site findById(Long id) {
        Site site = findByKey(id);
        return site.getId() != null ? site : null;
    }

    public Site findByDomain(String domain, boolean isSubDomain) {
        final String key = isSubDomain?subDomain(domain):domain(domain);
        String siteId = template.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
        if (!Strings.isNullOrEmpty(siteId)) {
            return findById(Long.parseLong(siteId));
        }
        return null;
    }

    public void create(final Site site) {
        final Long id = site.getId();
        checkArgument(id != null, "Site's id can not be null.");
        Date now = new Date();
        site.setCreatedAt(now);
        site.setUpdatedAt(now);

        if (!Strings.isNullOrEmpty(site.getDomain())) {
            if (domainExists(site.getDomain())) {
                throw new IllegalArgumentException("domain exists");
            }
        }

        if (!Strings.isNullOrEmpty(site.getSubDomain())) {
            if (subDomainExists(site.getSubDomain())) {
                throw new IllegalArgumentException("subDomain exists");
            }
        }

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(Site.class,id),stringHashMapper.toHash(site));
                //add to user index  and domain index
                t.sadd(sites(site.getUserId()),id.toString());
                if(!Strings.isNullOrEmpty(site.getDomain())){
                    t.setnx(domain(site.getDomain()),id.toString());
                }
                if(!Strings.isNullOrEmpty(site.getSubDomain())){
                    t.setnx(subDomain(site.getSubDomain()),id.toString());
                }
                //if site is forkable,then add to designer index
                if (Objects.equal(site.getForkable(), 1)) {
                    t.zadd(templatesOf(site.getUserId()), (double) -id, id.toString());
                }
                //add id to all sites
                t.lpush(allSites(), id.toString());
                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final Site site = findById(id);
        if (site == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                String subDomain = site.getSubDomain();
                String domain = site.getDomain();
                Transaction t = jedis.multi();
                t.del(entityId(Site.class,id));

                //delete user index and domain index if necessary
                t.srem(sites(site.getUserId()),id.toString());
                if (!Strings.isNullOrEmpty(subDomain)) {
                    t.del(subDomain(subDomain));
                }
                if (!Strings.isNullOrEmpty(domain)) {
                    t.del(domain(domain));
                }
                //remove id from all sites
                t.lrem(allSites(), 1, id.toString());
                t.exec();
            }
        });
    }

    public void update(final Site site) {
        checkArgument(site.getDomain() == null, "can not update domain use this method");
        checkArgument(site.getSubDomain() == null, "can not update subDomain use this method");
        site.setUpdatedAt(new Date());
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                Long id = site.getId();
                t.hmset(entityId(Site.class, id), stringHashMapper.toHash(site));
                //put site id to left
                t.lrem(allSites(), 1, id.toString());
                t.lpush(allSites(), id.toString());
                t.exec();
            }
        });
    }

    public void updateSubDomain(final Long id, final String subDomain) {
        final Site site = findById(id);
        if (site == null) {
            return;
        }
        if (subDomainExists(subDomain)) {
            throw new IllegalArgumentException("subDomain exists");
        }

        final String originSubDomain = site.getSubDomain();
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                //remove original subDomain index
                if (!Strings.isNullOrEmpty(originSubDomain)) {
                    t.del(subDomain(originSubDomain));
                }
                t.hset(entityId(Site.class, id), "subDomain",subDomain);
                t.setnx(subDomain(subDomain), id.toString());
                t.exec();
            }
        });
    }

    public Boolean domainExists(final String domain) {
        return template.execute(new JedisTemplate.JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.exists(domain(domain));
            }
        });
    }

    public Boolean subDomainExists(final String subDomain) {
        return template.execute(new JedisTemplate.JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.exists(subDomain(subDomain));
            }
        });
    }

    public void updateDomain(final Long id, final String domain) {
        final Site site = findById(id);
        if (site == null) {
            return;
        }
        if (domainExists(domain)) {
            throw new IllegalArgumentException("domain exists");
        }

        //remove original subDomain index
        final String originalDomain = site.getDomain();

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                if(!Strings.isNullOrEmpty(originalDomain)){
                    t.del(domain(originalDomain));
                }
                t.hset(entityId(Site.class, id), "domain", domain);
                t.set(domain(domain),id.toString());
                t.exec();
            }
        });
    }

    //find sites owned by user
    public List<Site> findByUserId(final Long userId) {
        final Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(sites(userId));
            }
        });
        return super.findByIds(ids);
    }

    public Long maxId() {
        return template.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return Long.parseLong(jedis.get(entityCount(Site.class)));
            }
        });
    }

    public void changeOwner(final Long siteId, final Long oldUerId, final Long newUserId) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.srem(sites(oldUerId),siteId.toString());
                t.sadd(sites(newUserId),siteId.toString());
                t.exec();
            }
        });
    }

    public Paging<Site> pagination(final int offset,final Integer size) {
        Long total = template.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.llen(allSites());
            }
        });
        if(offset>=total){
            return new Paging<Site>(total, Collections.<Site>emptyList());
        }
        List<String> ids = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(allSites(),offset,offset+size-1);
            }
        });
        List<Site> sites = super.findByIds(ids);
        return new Paging<Site>(total,sites);
    }
}
