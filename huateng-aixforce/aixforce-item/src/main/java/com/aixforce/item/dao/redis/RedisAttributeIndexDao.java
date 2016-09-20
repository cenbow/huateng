package com.aixforce.item.dao.redis;

import com.aixforce.item.dto.AttributeDto;
import com.aixforce.redis.utils.JedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import static com.aixforce.redis.utils.KeyUtils.*;

/*
* Author: jlchen
* Date: 2012-11-26
*/
@Repository
public class RedisAttributeIndexDao {

    @Autowired
    private JedisTemplate template;


    public void addCategoryAttributeKey(final Long categoryId, final Long attributeKeyId) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.rpush(attributeKeys(categoryId), attributeKeyId.toString());
            }
        });
    }

    public void removeCategoryAttributeKey(final Long categoryId, final Long attributeKeyId) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.lrem(attributeKeys(categoryId), 1, attributeKeyId.toString());
                //remove associated attribute values of this categoryAttributeKey
                t.del(attributeValues(categoryId,attributeKeyId));
                t.exec();
            }
        });
    }


    public void addCategoryAttributeValue(final Long categoryId, final Long attributeKeyId, final Long attributeValueId) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.rpush(attributeValues(categoryId, attributeKeyId), attributeValueId.toString());
            }
        });
    }

    public void removeCategoryAttributeValue(final Long categoryId, final Long attributeKeyId, final Long attributeValueId) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.lrem(attributeValues(categoryId, attributeKeyId), 1, attributeValueId.toString());
            }
        });
    }

    /*public void addSpuKeyOfSpu(Long spuId, Long attributeKeyId) {
        template.opsForList().rightPush(spuKeysOf(spuId), attributeKeyId.toString());
    }

    public void addSkuKeyOfSpu(Long spuId, Long attributeKeyId) {
        template.opsForList().rightPush(skuKeysOf(spuId), attributeKeyId.toString());
    }

    public void addSkuValueOfSpu(Long spuId, Long attributeKeyId, Long attributeValueId) {
        template.opsForList().rightPush(skuValuesOf(spuId, attributeKeyId), attributeValueId.toString());
    }*/

    public void addSkuKeysForSpu(final Long spuId, final Iterable<AttributeDto> skuKeys) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                //remove old values first
                t.del(skuKeysOf(spuId));
                for (AttributeDto skuKey : skuKeys) {
                    t.rpush(skuKeysOf(spuId),skuKey.getAttributeKeyId().toString());
                }
                t.exec();
            }
        });
    }
}
