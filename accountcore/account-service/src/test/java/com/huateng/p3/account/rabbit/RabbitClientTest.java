package com.huateng.p3.account.rabbit;

import base.BaseAccountServiceSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: JamesTang
 * Date: 13-12-12
 * Time: 下午2:58
 */
public class RabbitClientTest extends BaseAccountServiceSpringTest {
  /*  @Autowired
    private org.springframework.amqp.core.AmqpTemplate amqpTemplate;*/

    @Test
    public void  sendmsg(String msg) {
        //amqpTemplate.convertAndSend(msg);
    }
};