package com.huateng.p3.account.manager.inner;

import com.huateng.p3.account.common.tools.activemq.AppCode;
import com.huateng.p3.account.component.ActiveMqTemplateComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.component.RabbitTemplateComponent;
import com.huateng.p3.account.component.SmsComponent;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-5-28
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SmsManager {
	@Autowired
    private RabbitTemplateComponent rabbitTemplateComponent;

    @Autowired
    private ActiveMqTemplateComponent activeMqTemplateComponent;

    @Autowired
    private SmsComponent smsComponent;
    

    /**
     * Db版短信
     *
     * @param smsSendInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String smsNoticeDb(SmsSendInfo smsSendInfo) {
    	checkSmsInfo(smsSendInfo);
    	smsComponent.insertNotice(smsSendInfo);	
    	return BussErrorCode.ERROR_CODE_000000.getErrorcode();

    }
    
    /**
     * Mq版短信
     *
     * @param smsSendInfo
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public String smsNoticeMq(SmsSendInfo smsSendInfo) {
    	checkSmsInfo(smsSendInfo);
        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,null,smsSendInfo);
    	//rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.SMS_QUEKEY,smsSendInfo);
    	return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    private void checkSmsInfo(SmsSendInfo smsSendInfo){
    	if(Strings.isNullOrEmpty(smsSendInfo.getBussinessType())||Strings.isNullOrEmpty(smsSendInfo.getMobilePhone())){
    		
    		throw new BizException(BussErrorCode.ERROR_CODE_500204.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500204.getErrordesc());
    	}
    	
    }
    
   
}
