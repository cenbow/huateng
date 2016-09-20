/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.ComponentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.componentCategoryChildrenOf;
import static com.aixforce.redis.utils.KeyUtils.entityId;


/*
* Author: jlchen
* Date: 2012-11-16
*/
@Repository
public class RedisComponentCategoryDao extends RedisBaseDao<ComponentCategory> {
    @Autowired
    public RedisComponentCategoryDao(JedisTemplate template) {
        super(template);
    }

    public ComponentCategory findById(Long id) {
        ComponentCategory componentCategory = findByKey(id);
        return componentCategory.getId() != null ? componentCategory : null;
    }

    public List<ComponentCategory> findByParentId(final Long parentId) {
        Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(componentCategoryChildrenOf(parentId));
            }
        });
        return super.findByIds(ids);
    }

    public void create(final ComponentCategory componentCategory) {
        final Long id = newId();
        componentCategory.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(ComponentCategory.class,id),stringHashMapper.toHash(componentCategory));
                //add to component componentCategory parent index
                if(componentCategory.getParentId()!=null){
                    t.sadd(componentCategoryChildrenOf(componentCategory.getParentId()),id.toString());
                }
                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final ComponentCategory componentCategory = findById(id);
        if (componentCategory == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(ComponentCategory.class,id));
                //remove from index if necessary
                if(componentCategory.getParentId()!=null){
                    t.srem(componentCategoryChildrenOf(componentCategory.getParentId()),id.toString());
                }
                t.exec();
            }
        });
    }

    public void update(final ComponentCategory componentCategory) {
        final Long id = componentCategory.getId();
        final ComponentCategory original = findById(id);
        if (original == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(ComponentCategory.class,id),stringHashMapper.toHash(componentCategory));
                //update parent index if necessary
                if(componentCategory.getParentId()!=null){
                    t.srem(componentCategoryChildrenOf(original.getParentId()),id.toString());
                    t.sadd(componentCategoryChildrenOf(componentCategory.getParentId()),id.toString());
                }
                t.exec();
            }
        });
    }
}
