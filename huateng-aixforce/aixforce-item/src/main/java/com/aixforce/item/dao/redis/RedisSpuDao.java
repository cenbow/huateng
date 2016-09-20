package com.aixforce.item.dao.redis;

import com.aixforce.item.model.Spu;
import com.aixforce.item.model.SpuAttribute;
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
* Date: 2012-11-20
*/
@Repository
public class RedisSpuDao extends RedisBaseDao<Spu> {
    @Autowired
    public RedisSpuDao(JedisTemplate template) {
        super(template);
    }

    public Spu findById(Long id) {
        Spu spu = findByKey(id);
        return spu.getId() != null ? spu : null;
    }

    public void create(final Spu spu) {
        final Long id = newId();
        spu.setId(id);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(Spu.class,id),stringHashMapper.toHash(spu));

                //add to category index
                t.rpush(categorySpu(spu.getCategoryId()),id.toString());
                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final Spu spu = findById(id);
        if (spu == null) {
            return;
        }

        //find out attribute ids
        final List<String> attributeIds =  template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(spuAttributes(id),0,-1);
            }
        });

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(Spu.class,id));

                //remove category index
                t.lrem(categorySpu(spu.getCategoryId()),1,id.toString());
                //remove spuAttribute
                if(!attributeIds.isEmpty()){
                    t.del(Iterables.toArray(Iterables.transform(attributeIds,new Function<String, String>() {
                        @Override
                        public String apply( String attributeId) {
                            return entityId(SpuAttribute.class,Long.parseLong(attributeId));
                        }
                    }),String.class));
                }
                //remove spuAttribute index
                t.del(spuAttributes(id));
                t.exec();
            }
        });
    }

    public void update(final Spu spu) {
        final Long id = spu.getId();
        final Spu original = findById(id);
        if (original == null) {
            return;
        }

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(Spu.class,id),stringHashMapper.toHash(spu));

                //update category index if necessary
                if(spu.getCategoryId()!=null){
                    t.lrem(categorySpu(original.getCategoryId()),1,id.toString());
                    t.rpush(categorySpu(spu.getCategoryId()),id.toString());
                }
                t.exec();
            }
        });
    }

    public List<Spu> findByCategoryId(final Long categoryId) {
        final List<String>  spuIds =  template.execute(new JedisTemplate.JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(categorySpu(categoryId),0,-1);
            }
        });
        return super.findByIds(spuIds);
    }
}
