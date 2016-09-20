package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午10:58
 */
public interface HubSmsNoticeService {
    /**
     * 短信发送 db版
     *
     * @param smsSendInfo
     * @return
     */
    Response<String> smsNoticeDb(SmsSendInfo smsSendInfo)throws Exception;

    /**
     * 短信发送 mq版
     *
     * @param smsSendInfo
     * @return
     */
    Response<String> smsNoticeMq(SmsSendInfo smsSendInfo)throws Exception;
}
