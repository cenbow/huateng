package com.huateng.p3.account.risk;



import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskQueryObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.risk.inner.RiskQuery;
import com.huateng.p3.component.Response;

/**
 * User: huwenjie
 * Date: 14-3-20
 * Time: 上午9:47
 */

@Service
@Slf4j
public class RiskQueryServiceImpl implements RiskQueryService {
	
	@Autowired
	private RiskQuery riskQuery;

	@Override
	public Response<RiskQueryObject> queryAccountRisk(AccountInfo accountInfo,
			PaymentInfo paymentInfo) {
		log.info("call queryAccountRisk,PARAMETER:{} {}",accountInfo,paymentInfo);
    	Response<RiskQueryObject> response = new Response <RiskQueryObject>(); 
    	try
		{
   
    		response.setResult(riskQuery.doAccountRiskQuery(accountInfo,paymentInfo));
    		log.info("success to queryAccountRisk,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,paymentInfo , response});
		}
		catch (BizException e) {
			response.setErrorCode(e.getCode());
			response.setErrorMsg(e.getMessage());
            log.info("failed to queryAccountRisk,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,paymentInfo,response});
        } catch (Exception e) {
        	response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to queryAccountRisk,PARAMETER:{} {}, CAUSE:{}",new Object[]{accountInfo,paymentInfo,Throwables.getStackTraceAsString(e)});
        }
        return response;
	}
	
   
}
