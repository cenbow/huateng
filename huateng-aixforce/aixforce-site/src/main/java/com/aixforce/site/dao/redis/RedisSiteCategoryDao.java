/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.SiteCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.allSiteCategories;
import static com.aixforce.redis.utils.KeyUtils.entityId;


/*
* Desc: 模板分类
* Author: jlchen
* Date: 2012-11-28
*/
@Repository
public class RedisSiteCategoryDao extends RedisBaseDao<SiteCategory> {

    @Autowired
    public RedisSiteCategoryDao(JedisTemplate template) {
        super(template);
    }

    public void create(final SiteCategory siteCategory) {
        final Long id = newId();
        siteCategory.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(SiteCategory.class,id),stringHashMapper.toHash(siteCategory));
                //add to all site category index
                t.sadd(allSiteCategories(),id.toString());
                t.exec();
            }
        });
    }

    public void update(final SiteCategory siteCategory) {
        SiteCategory original = findById(siteCategory.getId());
        if(original == null){
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.hmset(entityId(SiteCategory.class, siteCategory.getId()), stringHashMapper.toHash(siteCategory));
            }
        });
    }

    public SiteCategory findById(Long id) {
        SiteCategory siteCategory = findByKey(id);
        return siteCategory.getId()!=null?siteCategory:null;
    }

    public void delete(final Long id){
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(SiteCategory.class,id));

                //remove from all category index
                t.srem(allSiteCategories(),id.toString());
            }
        });
    }

    public List<SiteCategory> loadAll(){
        final Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(allSiteCategories());
            }
        });
        return super.findByIds(ids);
    }
}
