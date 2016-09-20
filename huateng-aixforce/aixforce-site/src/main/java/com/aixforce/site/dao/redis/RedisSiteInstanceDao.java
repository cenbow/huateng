/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.redis.dao.RedisBaseDao;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.SiteInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;

import static com.aixforce.redis.utils.KeyUtils.*;


/*
* Author: jlchen
* Date: 2012-11-15
*/
@Repository
public class RedisSiteInstanceDao extends RedisBaseDao<SiteInstance> {

    @Autowired
    public RedisSiteInstanceDao(JedisTemplate template) {
        super(template);
    }

    public SiteInstance findById(final Long id) {
        SiteInstance siteInstance = findByKey(id);
        return siteInstance.getId() != null ? siteInstance : null;
    }

    public void create(final Long id, final SiteInstance siteInstance) {
        Date now = new Date();
        siteInstance.setId(id);
        siteInstance.setCreatedAt(now);
        siteInstance.setUpdatedAt(now);

        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(entityId(SiteInstance.class,id),stringHashMapper.toHash(siteInstance));
                //add it to site index
                t.sadd(siteInstances(siteInstance.getSiteId()),id.toString());
                t.exec();
            }
        });
    }

    public void create(SiteInstance siteInstance) {
        Long id = newId();
        create(id, siteInstance);
    }

    public void delete(final Long id) {
        final SiteInstance siteInstance = findById(id);
        if (siteInstance == null) {
            return;
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.del(entityId(SiteInstance.class,id));
                //remove from site index
                t.del(siteInstances(siteInstance.getSiteId()),id.toString());
                //delete widgets index
                t.del(headerWidgets(id),footerWidgets(id),siteWidgets(id));
                t.exec();
            }
        });
    }



    public void update(final SiteInstance siteInstance) {
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.hmset(entityId(SiteInstance.class,siteInstance.getId()),stringHashMapper.toHash(siteInstance));
            }
        });
    }

}
