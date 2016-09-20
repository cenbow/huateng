package com.aixforce.item.dao.redis;

import com.aixforce.item.model.AttributeValue;
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
public class RedisAttributeValueDao extends RedisBaseDao<AttributeValue> {
    private final static Logger log = LoggerFactory.getLogger(RedisAttributeValueDao.class);

    @Autowired
    public RedisAttributeValueDao(JedisTemplate template) {
        super(template);
    }

    public AttributeValue findById(Long id) {
        AttributeValue attributeValue = findByKey(id);
        return attributeValue.getId() != null ? attributeValue : null;
    }

    public AttributeValue findByValue(final String value) {
        String id = template.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.get(attributeValue(value));
            }
        });
        if (!Strings.isNullOrEmpty(id) && !Objects.equal(id, "null")) {
            return findById(Long.parseLong(id));
        }
        return null;
    }

    public List<AttributeValue> findByCategoryIdAndKeyId(final Long categoryId, final Long attributeKeyId) {
        final List<String> ids = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(attributeValues(categoryId,attributeKeyId),0, -1);
            }
        });
        return super.findByIds(ids);
    }


    public AttributeValue create(final String value) {
        final Long id = newId();
        final AttributeValue attributeValue = new AttributeValue();

        attributeValue.setId(id);
        attributeValue.setValue(value);

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                //persist value
                t.hmset(entityId(AttributeValue.class,id),stringHashMapper.toHash(attributeValue));

                //add index
                t.set(attributeValue(attributeValue.getValue()),id.toString());
                t.exec();
            }
        });
        return attributeValue;
    }

    public void delete(final Long id) {
        final AttributeValue original = findById(id);
        if (original == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(AttributeValue.class,id));

                //remove value index
                t.del(attributeValue(original.getValue()));
                t.exec();
            }
        });
    }

    public void update(final AttributeValue attributeValue) {
        final AttributeValue original = findById(attributeValue.getId());
        if (original == null) {
            throw new IllegalStateException("attribute value not exist");
        }
        //update name index if necessary
        if (!Strings.isNullOrEmpty(attributeValue.getValue())) {
            String originalId = template.execute(new JedisTemplate.JedisAction<String>() {
                @Override
                public String action(Jedis jedis) {
                    return jedis.get(attributeValue(attributeValue.getValue()));
                }
            });
            if (!Strings.isNullOrEmpty(originalId)&&!Objects.equal(originalId, attributeValue.getId().toString())) { //not the same attribute value
                log.error("duplicated attribute value data:{}", attributeValue.getValue());
                throw new IllegalArgumentException("duplicated attribute value data");
            }
        }

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(AttributeValue.class,attributeValue.getId()),stringHashMapper.toHash(attributeValue));
                //update name index if necessary
                if(!Strings.isNullOrEmpty(attributeValue.getValue())){
                    t.del(attributeValue(original.getValue()));
                    t.set(attributeValue(attributeValue.getValue()),attributeValue.getId().toString());
                }
                t.exec();
            }
        });
    }
}
