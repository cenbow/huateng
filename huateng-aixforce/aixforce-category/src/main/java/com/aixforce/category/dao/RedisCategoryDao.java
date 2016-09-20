package com.aixforce.category.dao;

import com.aixforce.category.model.Category;
import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

import static com.aixforce.redis.utils.KeyUtils.*;

/*
* Author: jlchen
* Date: 2012-11-17
*/
@Repository
public class RedisCategoryDao extends RedisBaseDao<Category> {
    @Autowired
    public RedisCategoryDao(JedisTemplate template) {
        super(template);
    }

    public Category findById(Long id) {
        Category category = findByKey(id);
        return category.getId() != null ? category : null;
    }

    public List<Category> findChildrenById(final Long id) {
        final List<String> ids = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(subCategoryOf(id),0,-1);
            }
        });
        return super.findByIds(ids);
    }

    public void create(final Category category) {
        final Long id = newId();
        category.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(Category.class,id),stringHashMapper.toHash(category));
                //add category to parent index
                t.rpush(subCategoryOf(category.getParentId()), id.toString());
                t.exec();
            }
        });

    }

    public void delete(final Long id) {
        final Category category = findById(id);
        if (id == null) {
            return;
        }

        //category attributeKeys
        final List<String> keys = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(attributeKeys(id),0,-1);
            }
        });
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                //remove category
                t.del(entityId(Category.class,id));
                //remove from parent index
                t.lrem(subCategoryOf(category.getParentId()), 1, id.toString());

                //delete spus index under this category
                t.del(categorySpu(id));

                //delete attribute values index
                if (!keys.isEmpty()) {
                    t.del(Iterables.toArray(Iterables.transform(keys, new Function<String, String>() {
                        @Override
                        public String apply(String attributeKeyId) {
                            return attributeValues(id, Long.parseLong(attributeKeyId));
                        }
                    }), String.class));
                }

                //delete attribute keys index
                t.del(attributeKeys(id));


                t.exec();
            }
        });
    }

    public void update(final Category category) {
        final Category original = findById(category.getId());
        if (original == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.hmset(entityId(Category.class,category.getId()),stringHashMapper.toHash(category));
            }
        });
    }

    public Long maxId() {
        return template.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return Long.parseLong(jedis.get(entityCount(Category.class)));
            }
        });
    }
}
