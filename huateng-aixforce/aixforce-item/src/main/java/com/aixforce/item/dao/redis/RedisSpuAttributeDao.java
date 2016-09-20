package com.aixforce.item.dao.redis;

import com.aixforce.item.model.SpuAttribute;
import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

import static com.aixforce.redis.utils.KeyUtils.entityId;
import static com.aixforce.redis.utils.KeyUtils.spuAttributes;


/*
* Author: jlchen
* Date: 2012-11-21
*/
@Repository
public class RedisSpuAttributeDao extends RedisBaseDao<SpuAttribute>{
    @Autowired
    public RedisSpuAttributeDao(JedisTemplate template) {
        super(template);
    }

    public SpuAttribute findById(Long id) {
        SpuAttribute spuAttribute = findByKey(id);
        return spuAttribute.getId()!=null? spuAttribute :null;
    }

    public void create(final List<SpuAttribute> spuAttributes) {
        if(spuAttributes.isEmpty()){
            return;
        }

        final long spuId = spuAttributes.get(0).getSpuId();

        final List<SpuAttribute> origin = findBySpuId(spuId);

        for (SpuAttribute spuAttribute : spuAttributes) {
            spuAttribute.setId(newId());
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();

                //remove old values first
                for (SpuAttribute spuAttribute : origin) {
                    t.del(entityId(SpuAttribute.class,spuAttribute.getId()));
                }
                t.del(spuAttributes(spuId));

                for (SpuAttribute spuAttribute : spuAttributes) {
                    Long id = spuAttribute.getId();
                    t.hmset(entityId(SpuAttribute.class,id),stringHashMapper.toHash(spuAttribute));
                    //add to spu index
                    t.rpush(spuAttributes(spuId),id.toString());
                }
                t.exec();
            }
        });

    }

    public void delete(final Long id) {
        final SpuAttribute spuAttribute = findById(id);
        if(spuAttribute == null){
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(SpuAttribute.class,id));
                //remove from spu index
                t.lrem(spuAttributes(spuAttribute.getSpuId()),1,id.toString());
                t.exec();
            }
        });
    }

    public void update(final SpuAttribute spuAttribute){
        final Long id = spuAttribute.getId();
        final SpuAttribute original = findById(id);
        if(original == null){
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(SpuAttribute.class,id),stringHashMapper.toHash(spuAttribute));
                //update spu index if necessary
                if(spuAttribute.getSpuId()!=null && !Objects.equal(spuAttribute.getSpuId(), original.getSpuId())){
                    t.lrem(spuAttributes(original.getSpuId()),1,id.toString());
                    t.lpush(spuAttributes(spuAttribute.getSpuId()),id.toString());
                }
                t.exec();
            }
        });
    }



    public List<SpuAttribute> findBySpuId(final Long spuId){
        final List<String> ids = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(spuAttributes(spuId),0,-1);
            }
        });
        return super.findByIds(ids);
    }

    public Integer countOf(final Long spuId){
        return template.execute(new JedisTemplate.JedisAction<Integer>() {
            @Override
            public Integer action(Jedis jedis) {
                return jedis.llen(spuAttributes(spuId)).intValue();
            }
        });
    }

/*    public Integer maxRank(Long spuId){
        return Objects.firstNonNull((Integer) getSqlSession().selectOne("SpuAttribute.maxRank", spuId), -1);
    }*/

    public void deleteBySpuId(final Long spuId) {
        final List<String> spuAttributes = template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(spuAttributes(spuId), 0, -1);
            }
        });
        if(spuAttributes.isEmpty()){
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(Iterables.toArray(Iterables.transform(spuAttributes,new Function<String, String>() {
                    @Override
                    public String apply( String attributeId) {
                        return entityId(SpuAttribute.class,attributeId);
                    }
                }),String.class));
                t.del(spuAttributes(spuId));
                t.exec();
            }
        });
    }

}
