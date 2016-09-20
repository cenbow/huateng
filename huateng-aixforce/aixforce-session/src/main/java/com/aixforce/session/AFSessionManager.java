package com.aixforce.session;

import com.aixforce.session.exception.SessionException;
import com.aixforce.session.redis.JedisCallback;
import com.aixforce.session.redis.JedisPoolExecutor;
import com.aixforce.session.redis.JsonSerializer;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public class AFSessionManager {
    private final static Logger log = LoggerFactory.getLogger(AFSessionManager.class);

    private final String sessionPrefix;

    private final SessionIdGenerator sessionIdGenerator;

    private volatile JedisPoolExecutor executor;

    /**
     * cache for unmodified session attributes to avoid too many redis query
     */
    //private final Cache<String, AFSession> sessions;

    private final JsonSerializer redisSerializer;

    static class SingletonHolder {
        static AFSessionManager instance = new AFSessionManager();
    }

    private AFSessionManager() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
             input = Resources.newInputStreamSupplier(Resources.getResource("session.properties")).getInput();
            properties.load(input);
        } catch (Exception e) {
            log.error("failed to load session.properties", e);
        } finally {
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    //ignore this shit
                }
            }
        }

        this.sessionIdGenerator = new DefaultSessionIdGenerator();
        this.redisSerializer = new JsonSerializer();
        this.sessionPrefix = properties.getProperty("session.redis.prefix", "afsession");

        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        config.setMaxActive(Integer.parseInt(properties.getProperty("session.redis.pool.maxActive", "200")));
        config.setMaxIdle(Integer.parseInt(properties.getProperty("session.redis.pool.maxIdle", "10")));
        config.setMaxWait(1000l);

        final String mode = properties.getProperty("session.redis.mode");
        if (Objects.equal(mode,"cluster")) { //initialize by sentinels
            this.executor = new JedisPoolExecutor(config,true, properties);
        }else{                                   //单机版的redis

            this.executor = new JedisPoolExecutor(config,false, properties);
        }
    }

    public static AFSessionManager instance() {
        return SingletonHolder.instance;
    }

    public SessionIdGenerator getSessionIdGenerator() {
        return sessionIdGenerator;
    }


    public Map<String, Object> findSessionById(final String id) {
        final String sessionId = sessionPrefix + ":" + id;
        try {
            return this.executor.execute(new JedisCallback<Map<String, Object>>() {
                @Override
                public Map<String, Object> execute(Jedis jedis) {

                    String session = jedis.get(sessionId);
                    if (!Strings.isNullOrEmpty(session)) {
                        return redisSerializer.deserialize(session);
                    }
                    return Collections.emptyMap();
                }
            });
        } catch (Exception e) {
            log.error("failed to find session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
            throw new SessionException("get session failed",e);
        }

    }

    public void refreshExpireTime(AFSession afSession, final int maxInactiveInterval) {
        final String sessionId = sessionPrefix + ":" + afSession.getId();
        try {
            this.executor.execute(new JedisCallback<Void>() {
                @Override
                public Void execute(Jedis jedis) {
                    jedis.expire(sessionId, maxInactiveInterval);
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("failed to refresh expire time session(key={}) in redis,cause:{}",
                    sessionId, Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * remove from db physically
     *
     * @param id sessionId
     */
    public void deletePhysically(final String id) {
        final String sessionId = sessionPrefix + ":" + id;

        try {
            this.executor.execute(new JedisCallback<Void>() {
                @Override
                public Void execute(Jedis jedis) {
                    jedis.del(sessionId);
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("failed to delete session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * flush session to redis
     *
     * @param id       session id
     * @param snapshot session snapshot
     */
    public boolean save(final String id, final Map<String, Object> snapshot, final int maxInactiveInterval) {
        final String sessionId = sessionPrefix + ":" + id;
        try {
            this.executor.execute(new JedisCallback<Void>() {
                @Override
                public Void execute(Jedis jedis) {
                    if (snapshot.isEmpty()) {
                        jedis.del(sessionId);
                    } else {
                        jedis.setex(sessionId, maxInactiveInterval, redisSerializer.serialize(snapshot));
                    }
                    return null;
                }
            });

            return true;
        } catch (Exception e) {
            log.error("failed to delete session(key={}) in redis,cause:{}", sessionId, Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    public void destroy() {
        if (executor != null) {
            executor.getJedisPool().destroy();
        }
    }
}
