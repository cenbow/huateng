package com.huateng.p3.account.manager;

import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.component.Response;


/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-5-20
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public interface SmsNoticeService {
	/**
     * 短信发送 db版
     * 
     * @param smsSendInfo
     * @return 
     */
    Response<String> smsNoticeDb(SmsSendInfo smsSendInfo);
    
    /**
     * 短信发送 mq版
     * 
     * @param smsSendInfo
     * @return 
     */
    Response<String> smsNoticeMq(SmsSendInfo smsSendInfo);
    
	
}