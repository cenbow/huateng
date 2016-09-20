package com.aixforce.item.dao.redis;

import com.aixforce.item.model.AttributeKey;
import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

import static com.aixforce.redis.utils.KeyUtils.*;

/*
* Author: jlchen
* Date: 2012-11-20
*/
@Repository
public class RedisAttributeKeyDao extends RedisBaseDao<AttributeKey> {
    private final static Logger log = LoggerFactory.getLogger(RedisAttributeKeyDao.class);

    @Autowired
    public RedisAttributeKeyDao(JedisTemplate template) {
        super(template);
    }

    public AttributeKey findById(Long id) {
        AttributeKey attributeKey = findByKey(id);
        return attributeKey.getId() != null ? attributeKey : null;
    }

    public AttributeKey findByName(final String name) {
        String id = template.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.get(attributeKey(name));
            }
        });
        if (!Strings.isNullOrEmpty(id)) {
            return findById(Long.parseLong(id));
        }
        return null;
    }

    public List<AttributeKey> findByCategoryId(final Long categoryId) {
        final List<String> ids = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(attributeKeys(categoryId),0, -1);
            }
        });
        return super.findByIds(ids);
    }

    public List<AttributeKey> findSkuKeysBySpuId(final Long spuId){
        final List<String> ids = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(skuKeysOf(spuId),0,-1);
            }
        });
        return super.findByIds(ids);
    }

    public List<AttributeKey> findSpuKeysBySpuId(final Long spuId){
        final List<String> ids = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(spuKeysOf(spuId),0,-1);
            }
        });
        return super.findByIds(ids);
    }

    public void create(final AttributeKey attributeKey) {

        final String name = attributeKey.getName();
        //test name exists
        String existedId = template.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.get(attributeKey(attributeKey(name)));
            }
        });
        if (!Strings.isNullOrEmpty(existedId)) {
            log.warn("attribute key {} has been existed,use existed key(id={}) ", name, existedId);
            attributeKey.setId(Long.parseLong(existedId));
            return;
        }
        final Long id = newId();
        attributeKey.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(AttributeKey.class,id),stringHashMapper.toHash(attributeKey));
                //set name index
                t.set(attributeKey(name),id.toString());
                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final AttributeKey attributeKey = findById(id);
        if (attributeKey == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(AttributeKey.class,id));

                //remove name index
                t.del(attributeKey(attributeKey.getName()));
                t.exec();
            }
        });
    }

    public void update(final AttributeKey attributeKey) {
        final AttributeKey original = findById(attributeKey.getId());
        if (original == null) {
            throw new IllegalStateException("attribute key not exist");
        }
        if(!Strings.isNullOrEmpty(attributeKey.getName())){
            String originalId = template.execute(new JedisTemplate.JedisAction<String>() {
                @Override
                public String action(Jedis jedis) {
                    return jedis.get(entityId(AttributeKey.class,attributeKey.getId()));
                }
            });
            if(!Strings.isNullOrEmpty(originalId)&&!Objects.equal(originalId,attributeKey.getId().toString())){ //not the same attribute key
                log.error("duplicated attribute key name:{}",attributeKey.getName());
                throw new IllegalArgumentException("duplicated attribute key name");
            }
        }

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();

                t.hmset(entityId(AttributeKey.class,attributeKey.getId()),stringHashMapper.toHash(attributeKey));

                //update name index if necessary
                if(!Strings.isNullOrEmpty(attributeKey.getName())){
                    t.del(attributeKey(original.getName()));
                    t.set(attributeKey(attributeKey.getName()),attributeKey.getId().toString());
                }
                t.exec();
            }
        });
    }
}
