/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.Component;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.*;


/*
* Author: jlchen
* Date: 2012-11-16
*/
@Repository
public class RedisComponentDao extends RedisBaseDao<Component> {
    @Autowired
    public RedisComponentDao(JedisTemplate template) {
        super(template);
    }


    public Component findById(Long id) {
        Component component = findByKey(id);
        return component.getId() != null ? component : null;
    }

    public List<Component> findByCategoryId(final Long categoryId) {
        Set<String> ids = template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(components(categoryId));
            }
        });
        return super.findByIds(ids);
    }

    public Component findByPath(final String path){
        String id = template.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.get(component(path));
            }
        });
        if(Strings.isNullOrEmpty(id)){
            return null;
        }
        return findById(Long.parseLong(id));
    }


    public void create(final Component component) {
        final Long id = newId();
        component.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(Component.class,id),stringHashMapper.toHash(component));
                //add to component siteCategory index and path index
                t.sadd(components(component.getCompCategoryId()),id.toString());
                if (!Strings.isNullOrEmpty(component.getPath())) {
                    t.setnx(component(component.getPath()),id.toString());
                }
                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final Component component = findByKey(id);
        if (id == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(Component.class,id));
                t.srem(components(component.getCompCategoryId()),id.toString());
                if(!Strings.isNullOrEmpty(component.getPath())){
                    t.del(component(component.getPath()));
                }
                t.exec();
            }
        });
    }

    public void update(final Component component) {
        final Long id = component.getId();
        final Component original = findByKey(id);
        if (original == null) {
            throw new IllegalStateException("component not exist");
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(Component.class,id),stringHashMapper.toHash(component));
                if(component.getCompCategoryId()!=null){
                    t.srem(components(original.getCompCategoryId()),id.toString());
                    t.sadd(components(component.getCompCategoryId()),id.toString());
                }
                if(!Strings.isNullOrEmpty(component.getPath())){
                    t.del(component(original.getPath()));
                    t.setnx(component(component.getPath()),id.toString());
                }
                t.exec();
            }
        });
    }
}
