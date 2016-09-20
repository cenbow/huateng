package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.account.manager.SmsNoticeService;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubSmsNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-9-15
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HubSmsNoticeServiceImpl implements HubSmsNoticeService {
    @Autowired(required = false)
    private SmsNoticeService smsNoticeService;

    private static final Logger logger = LoggerFactory.getLogger(HubSmsNoticeServiceImpl.class);
    @Override
    public Response<String> smsNoticeDb(SmsSendInfo smsSendInfo) throws Exception {
        logger.info("smsNoticeDb SmsSendInfo:{}", new Object[]{smsSendInfo.toString()});
        return smsNoticeService.smsNoticeDb(smsSendInfo);
    }

    @Override
    public Response<String> smsNoticeMq(SmsSendInfo smsSendInfo) throws Exception {
        logger.info("smsNoticeMq SmsSendInfo:{}", new Object[]{smsSendInfo.toString()});
        return smsNoticeService.smsNoticeMq(smsSendInfo);
    }
}
