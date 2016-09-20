package com.huateng.p3.account.inner.component;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.account.common.enummodel.TxnOutType;
import com.huateng.p3.account.component.RabbitTemplateComponent;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-2
 * Time: 下午5:33
 * o change this template use File | Settings | File Templates.
 */
public class RabbitTemplateComponentTest extends BaseAccountServiceSpringTest {


    private Logger logger = LoggerFactory.getLogger(RabbitTemplateComponentTest.class);

    @Autowired
    RabbitTemplateComponent rabbitTemplateComponent;

    @org.junit.Test
    public void test() {
        for (int i = 0; i < 1000; i++) {
        	SmsSendInfo smsSendInfo = new SmsSendInfo();
        	smsSendInfo.setMobilePhone("1341111111");
        	smsSendInfo.setTxnTime(DateTime.now().toDate());
        	smsSendInfo.setBussinessType(TxnOutType.OUT_TXN_TYPE_F11022.getTxnOutType());
            rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.SMS_QUEKEY,smsSendInfo);
            logger.debug("----------------------------in rabbit mq -----------------------------------------------");

        }
    }

}

