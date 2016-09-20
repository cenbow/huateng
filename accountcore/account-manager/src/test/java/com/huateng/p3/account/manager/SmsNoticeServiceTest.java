package com.huateng.p3.account.manager;

import base.BaseAccountManagerSpringTest;
import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.component.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: dongpeiji
 * Date: 14-9-18
 * Time: 上午10:31
 */
@Slf4j
public class SmsNoticeServiceTest extends BaseAccountManagerSpringTest {

    @Autowired
    private SmsNoticeService smsNoticeService;

    @Test
    public void SmsNoticeMqTest(){
        SmsSendInfo smsSendInfo = new SmsSendInfo();
        smsSendInfo.setBussinessType("1");
        smsSendInfo.setMobilePhone("18701996438");
        Response<String> response =  smsNoticeService.smsNoticeMq(smsSendInfo);
        log.info("SmsNoticeMqTest response RESULT: {}", response.getResult());
    }
}
