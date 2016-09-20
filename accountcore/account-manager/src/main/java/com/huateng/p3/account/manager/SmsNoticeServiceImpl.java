package com.huateng.p3.account.manager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.manager.inner.SmsManager;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: huenjie
 * Date: 14-3-24
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class SmsNoticeServiceImpl implements SmsNoticeService {

    @Autowired
    private SmsManager smsManager;
    
	@Override
	public Response<String> smsNoticeDb(SmsSendInfo smsSendInfo) {
		log.info("call smsNoticeDb, PARAMETER:{}", smsSendInfo);
        Response<String> result = new Response<String>();
        try {
            result.setResult(smsManager.smsNoticeDb(smsSendInfo));
            log.info("success to smsNoticeDb, PARAMETER:{}, RESULT:{}", smsSendInfo, result);
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to smsNoticeDb, PARAMETER:{}, RESULT:{}", smsSendInfo, result);
        } catch (Exception e) {
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to smsNoticeDb, PARAMETER:{}, CAUSE:{}", smsSendInfo, Throwables.getStackTraceAsString(e));
        }
        return result;
	}

	@Override
	public Response<String> smsNoticeMq(SmsSendInfo smsSendInfo) {
		log.info("call smsNoticeMq, PARAMETER:{}", smsSendInfo);
        Response<String> result = new Response<String>();
        try {
            result.setResult(smsManager.smsNoticeMq(smsSendInfo));
            log.info("success to smsNoticeMq, PARAMETER:{}, RESULT:{}", smsSendInfo, result);
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to smsNoticeMq, PARAMETER:{}, RESULT:{}", smsSendInfo, result);
        } catch (Exception e) {
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to smsNoticeMq, PARAMETER:{}, CAUSE:{}", smsSendInfo, Throwables.getStackTraceAsString(e));
        }
        return result;
	}
	 
	
}
