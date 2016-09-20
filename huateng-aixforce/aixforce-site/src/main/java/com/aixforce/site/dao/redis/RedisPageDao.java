/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.Widget;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.*;


/*
* Author: jlchen
* Date: 2012-11-15
*/
@Repository
public class RedisPageDao extends RedisBaseDao<Page> {

    @Autowired
    public RedisPageDao(JedisTemplate template) {
        super(template);
    }

    public Page findById(Long pageId) {
        Page page = findByKey(pageId);
        return page.getId() != null ? page : null;
    }

    public List<Page> findBySiteInstanceId(final Long siteInstanceId) {
        Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(pages(siteInstanceId));
            }
        });
        return super.findByIds(ids);
    }

    public Page findByInstanceIdAndPath(Long siteInstanceId, String path) {
        List<Page> pages = findBySiteInstanceId(siteInstanceId);
        for (Page page : pages) {
            if (Objects.equal(page.getPath(), path)) {
                return page;
            }
        }
        return null;
    }

    public void create(final Page page) {
        final Long id = newId();
        page.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(Page.class, id), stringHashMapper.toHash(page));
                //add page to siteInstance index
                Long siteInstanceId = page.getSiteInstanceId();
                t.sadd(pages(siteInstanceId), id.toString());
                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final Page page = findById(id);
        if (page == null) {
            return;
        }
        final Set<String> pageWidgets = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(widgets(id));
            }
        });
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(Page.class, id));
                //remove form siteInstance index
                Long siteInstanceId = page.getSiteInstanceId();
                t.srem(pages(siteInstanceId), id.toString());
                //delete page widgets index and the page widgets
                t.del(widgets(id));
                if(!pageWidgets.isEmpty()){
                    t.del(Iterables.toArray(Iterables.transform(pageWidgets,new Function<String, String>() {
                        @Override
                        public String apply(String widgetId) {
                            return entityId(Widget.class, widgetId);
                        }
                    }),String.class));
                }
                t.exec();
            }
        });
    }

    public void update(final Page page) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.hmset(entityId(Page.class, page.getId()), stringHashMapper.toHash(page));
            }
        });
    }
}
