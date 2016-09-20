package com.aixforce.session.redis;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import java.util.Properties;
import java.util.Set;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-22
 */
public class JedisPoolExecutor {
    private volatile Pool<Jedis> jedisPool;

    public JedisPoolExecutor(JedisPoolConfig config,boolean sentinel,Properties params) {
        if(sentinel){
            String sentinelProps = params.getProperty("session.redis.sentinel.hosts");
            Iterable<String> parts = Splitter.on(',').trimResults().omitEmptyStrings().split(sentinelProps);

            final Set<String> sentinelHosts = Sets.newHashSet(parts);
            String masterName = params.getProperty("session.redis.sentinel.masterName");
            this.jedisPool = new JedisSentinelPool(masterName,sentinelHosts,config);
        }else{
            String redisHost = params.getProperty("session.redis.host");
            int redisPort = Integer.parseInt(params.getProperty("session.redis.port"));
            this.jedisPool = new JedisPool(config, redisHost, redisPort);
        }
    }

    public <V> V execute(JedisCallback<V> cb) {
        Jedis jedis = jedisPool.getResource();
        boolean success = true;
        try {
            return cb.execute(jedis);
        } catch (JedisException e) {
            success = false;
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            throw e;
        } finally {
            if (success) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }
}
