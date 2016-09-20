package com.aixforce.web.controller;

import com.aixforce.redis.utils.JedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-04-08
 */
@Controller
@RequestMapping("/forest")
public class Forrests {

    private final static Logger log = LoggerFactory.getLogger(Forrests.class);

    private final JedisTemplate template;

    @Autowired
    public Forrests(JedisTemplate template) {
        this.template = template;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String sync(){
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.setex("forrest", 15*60,String.valueOf(System.currentTimeMillis()));
            }
        });
        log.info("send forrest change signal");
        return "ok";
    }
}
