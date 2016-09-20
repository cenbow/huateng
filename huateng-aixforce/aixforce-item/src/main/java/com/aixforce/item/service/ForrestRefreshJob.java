package com.aixforce.item.service;

import com.aixforce.redis.utils.JedisTemplate;
import com.google.common.base.Strings;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-08-28
 */
@Component
public class ForrestRefreshJob {
    private final static Logger log = LoggerFactory.getLogger(ForrestRefreshJob.class);

    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private final JedisTemplate jedisTemplate;

    private final  Forrest forrest;

    @Autowired
    public ForrestRefreshJob(JedisTemplate jedisTemplate, Forrest forrest) {
        this.jedisTemplate = jedisTemplate;
        this.forrest = forrest;
    }

    /**
     * run every 15 minutes;
     */
    @Scheduled(cron = "0 0/1 * * * ?")  //每隔1分钟触发一次
    public void check () {
        jedisTemplate.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                String changed = jedis.get("forrest");
                if(Strings.isNullOrEmpty(changed)){
                    log.info("no forrest update event");
                }else{
                    log.info("receive forrest update event, it occurs at {}",TIME_FORMATTER.print(Long.parseLong(changed)));
                    forrest.invalidAll();
                }
            }
        });
    }
}
