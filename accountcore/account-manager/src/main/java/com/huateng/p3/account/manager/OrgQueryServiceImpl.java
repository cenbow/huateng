package com.huateng.p3.account.manager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.manager.inner.QueryManager;
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
public class OrgQueryServiceImpl implements OrgQueryService {

    @Autowired
    private QueryManager queryManager;

    @Override
	public Response<String> checkOrgPayment(ManagerLog managerLog) {
        log.info("call checkOrgPayment, PARAMETER:{}", managerLog);
        Response<String> result = new Response<String>();
        try {
            result.setResult(queryManager.doCheckOrgPayment(managerLog));
            log.info("success to checkOrgPayment, PARAMETER:{}, RESULT:{}", managerLog, result);
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to checkOrgPayment, PARAMETER:{}, RESULT:{}", managerLog, result);
        } catch (Exception e) {
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to checkOrgPayment, PARAMETER:{}, CAUSE:{}", managerLog, Throwables.getStackTraceAsString(e));
        }
        return result;
	}
	 
	
}
