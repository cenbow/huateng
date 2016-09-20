package com.aixforce.redis.dao;
/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.redis.utils.KeyUtils;
import com.aixforce.redis.utils.StringHashMapper;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
* Author: jlchen
* Date: 2012-11-15
*/
public abstract class RedisBaseDao<T> {

    public final StringHashMapper<T>  stringHashMapper;
    protected final JedisTemplate template;
    protected final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public RedisBaseDao(JedisTemplate template) {
        this.template = template;
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        stringHashMapper = new StringHashMapper<T>(entityClass);
    }

    protected List<T> findByIds(final Iterable<String> ids){
        if(Iterables.isEmpty(ids)){
            return Collections.emptyList();
        }
        List<Response<Map<String,String>>> result = template.execute(new JedisTemplate.JedisAction<List<Response<Map<String,String>>>>() {
            @Override
            public List<Response<Map<String,String>>> action(Jedis jedis) {
                List<Response<Map<String,String>>> result = Lists.newArrayListWithCapacity(Iterables.size(ids));
                Pipeline p = jedis.pipelined();
                for (String id : ids) {
                    result.add(p.hgetAll(KeyUtils.entityId(entityClass,id)));
                }
                p.sync();
                return result;
            }
        });
        List<T> entities = Lists.newArrayListWithCapacity(result.size());
        for (Response<Map<String, String>> mapResponse : result) {
            entities.add(stringHashMapper.fromHash(mapResponse.get()));
        }
        return entities;
    }

    protected T findByKey(final Long id) {
        Map<String,String> hash = template.execute(new JedisTemplate.JedisAction<Map<String, String>>() {
            @Override
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(KeyUtils.entityId(entityClass,id));
            }
        });
        return stringHashMapper.fromHash(hash);
    }

    public Long newId(){
        return template.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.incr(KeyUtils.entityCount(entityClass));
            }
        });
    }
}
