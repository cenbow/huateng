package com.aixforce.session.redis;

import redis.clients.jedis.Jedis;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-22
 */
public interface JedisCallback<V> {

    V execute(Jedis jedis);

}
