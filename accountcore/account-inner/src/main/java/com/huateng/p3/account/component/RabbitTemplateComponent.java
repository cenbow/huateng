package com.huateng.p3.account.component;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * User: JamesTang
 * Date: 13-12-12
 * Time: 下午5:03
 */
@Component
@Slf4j
public class RabbitTemplateComponent implements ApplicationContextAware {

    public static final String SMS_QUEKEY = "q_sms_quekey_key";

    public static final String RISK_BIZ = "q_risk_biz_key";
    
    public static final String TYB_FINANCING = "q_tyb_financing_key";
    

    private ApplicationContext ctx;

  //  RabbitTemplate template;

    private RabbitTemplate getTemplate() {
        return ctx.getBean(RabbitTemplate.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public void convertAndSend(String key, Object obj) {
    	try {
    		 getTemplate().convertAndSend(key, obj);
		} catch (Exception e) {
			log.error("rabbitmq send fail KEY:{} OBJ:{} CAUSE:{}",new Object[]{key,obj, Throwables.getStackTraceAsString(e)});
		}
       
    }

    public void convertAndSend(Object obj) {
        getTemplate().convertAndSend(obj);
    }

}