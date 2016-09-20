package com.huateng.p3.account.risk;



import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;
import com.huateng.p3.account.risk.inner.RiskCheck;
import com.huateng.p3.component.Response;

/**
 * User: JamesTang
 * Date: 13-12-11
 * Time: 上午9:47
 */

@Service
@Slf4j
public class RiskCheckServiceImpl implements RiskCheckService {
	
	@Autowired
	private RiskCheck riskCheck;
	
    @Override
    public Response<String> accountRiskCheck(PaymentInfo paymentInfo, TInfoAccount account) {
    	log.info("call accountRiskCheck,PARAMETER:{} {}",paymentInfo,account);
    	Response<String> response = new Response <String>(); 
    	try
		{
            riskCheck.doAccountRiskCheck(paymentInfo, account) ;
    		response.setResult("ok");
    		log.info("success to accountRiskCheck,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,account,response});
		}
		catch (BizException e) {
			response.setErrorCode(e.getCode());
			response.setErrorMsg(e.getMessage());
            log.info("failed to accountRiskCheck,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,account,response});
        } catch (Exception e) {
        	response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to accountRiskCheck,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,account,Throwables.getStackTraceAsString(e)});
        }
        return response;
    }

    @Override
    public Response<String> merchantRiskCheck(PaymentInfo paymentInfo, OrgType orgType) {
    	//TODO To change body of implemented methods use File | Settings | File Templates. //To change body of implemented methods use File | Settings | File Templates.
    	Response<String> response = new Response <String>(); 
    	response.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
        return response;
    }



	@Override
	public Response<String> setRiskCustomerCommonRule(AccountInfo accountInfo,
			ManagerLog managerLog,TRiskCustomerCommonRule setRule) {
		log.info("call setRiskCustomerCommonRule,PARAMETER:{} {} {}",new Object[]{accountInfo,managerLog,setRule});
		Response<String> r = new Response<String>();
		try
		{
			r.setResult(riskCheck.setRiskCustomerCommonRule(accountInfo, managerLog, setRule));
			log.info("success to setRiskCustomerCommonRule,PARAMETER:{} {} {}, RESULT:{}",new Object[]{accountInfo,managerLog,setRule,r});
		}
		catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());    
            log.info("failed to setRiskCustomerCommonRule,PARAMETER:{} {} {}, RESULT:{}",new Object[]{accountInfo,managerLog,setRule,r});
        } catch (Exception e) {
   
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to setRiskCustomerCommonRule,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{accountInfo,managerLog,setRule,Throwables.getStackTraceAsString(e)});
                      
        }
        return r;
	}
	@Override
	public Response<TRiskCustomerCommonRule> queryRiskCustomerCommonRule(AccountInfo accountInfo,
			ManagerLog managerLog) {
		log.info("call queryRiskCustomerCommonRule,PARAMETER:{} {}",accountInfo,managerLog);    
		Response<TRiskCustomerCommonRule> r = new Response<TRiskCustomerCommonRule>();
		try
		{
			r.setResult(riskCheck.queryRiskCustomerCommonRule(accountInfo, managerLog));
			log.info("success to queryRiskCustomerCommonRule,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,managerLog,r});
		}
		catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());           
            log.info("failed to queryRiskCustomerCommonRule,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,managerLog,r});
        } catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to queryRiskCustomerCommonRule,PARAMETER:{} {}, CAUSE:{}",new Object[]{accountInfo,managerLog,Throwables.getStackTraceAsString(e)});
        }
        return r;
	}

	@Override
	public Response<String> accountRiskCheckOut(PaymentInfo paymentInfo, AccountInfo accountInfo) {
		log.info("call accountRiskCheckOut,PARAMETER:{} {}",paymentInfo,accountInfo);
    	Response<String> response = new Response <String>(); 
    	try
		{
            riskCheck.doAccountRiskCheckOut(paymentInfo, accountInfo) ;
    		response.setResult("ok");
    		log.info("success to accountRiskCheckOut,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,response});
		}
		catch (BizException e) {
			response.setErrorCode(e.getCode());
			response.setErrorMsg(e.getMessage());
            log.info("failed to accountRiskCheckOut,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,response});
        } catch (Exception e) {
        	response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to accountRiskCheckOut,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
        }
        return response;
	}
}
