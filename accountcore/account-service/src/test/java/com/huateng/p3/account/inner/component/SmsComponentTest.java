package com.huateng.p3.account.inner.component;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.enummodel.TxnOutType;
import com.huateng.p3.account.component.SmsComponent;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-31
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public class SmsComponentTest extends BaseAccountServiceSpringTest {

    @Autowired
    private SmsComponent smsComponent;

    @Test
    public void createAndSendSms(){
        String mobilePhone = "13111111111";
        String outTxnType  = TxnOutType.OUT_TXN_TYPE_F11022.getTxnOutType();  
        smsComponent.createAndSendSms(mobilePhone,outTxnType);
    }
}
