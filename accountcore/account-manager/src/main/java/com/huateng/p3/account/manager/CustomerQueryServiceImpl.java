package com.huateng.p3.account.manager;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.manager.inner.QueryManager;
import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-6
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class CustomerQueryServiceImpl implements CustomerQueryService {

    @Autowired
    private QueryManager queryManager;



    @Override
    public Response<CustomerResultObject>queryCustomerInfo(AccountInfo accountInfo) {
    	log.info("call queryCustomerInfo,PARAMETER:{}",accountInfo); 
        Response<CustomerResultObject> result = new Response<CustomerResultObject>();
        try {
        	CustomerResultObject customerResultObject = queryManager.queryCustomerInfo(accountInfo);
            result.setResult(customerResultObject);
            log.info("success to queryCustomerInfo,PARAMETER:{}, RESULT:{}",accountInfo,result);  
            return result;
        } catch (BizException ex) {
            result.setErrorCode(ex.getCode());
            result.setErrorMsg(ex.getMessage());
            log.info("failed to queryCustomerInfo,PARAMETER:{}, RESULT:{}",accountInfo,result);     
            return result;
        } catch (Exception ex) {
        	log.error("failed to queryCustomerInfo,PARAMETER:{}, CAUSE:{}",accountInfo,Throwables.getStackTraceAsString(ex));
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }

    }
    
    @Override
	public Response<CustomerResultObject> queryCheckCardHandingInfo(AccountInfo accountInfo) {
    	log.info("call queryCheckCardHandingInfo,PARAMETER:{}",accountInfo); 
		Response<CustomerResultObject> result = new Response<CustomerResultObject>();
        try {            
            result.setResult(queryManager.doCheckCardHandingInfo(accountInfo));
            log.info("success to queryCheckCardHandingInfo,PARAMETER:{}, RESULT:{}",accountInfo,result);  
            return result;
        } catch (BizException e) {
        	result.setErrorCode(e.getCode());
        	result.setErrorMsg(e.getMessage());
        	log.info("failed to queryCheckCardHandingInfo,PARAMETER:{}, RESULT:{}",accountInfo,result);     
            return result;
        } catch (Exception e) {
        	log.error("failed to queryCheckCardHandingInfo,PARAMETER:{}, CAUSE:{}",accountInfo,Throwables.getStackTraceAsString(e));
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }
	}
    
    
    @Override
    public Response<SecurityResultObject> queryCustomerSecurityInfo(AccountInfo accountInfo) {
    	log.info("call queryCustomerInfo,PARAMETER:{}",accountInfo); 
        Response<SecurityResultObject> result = new Response<SecurityResultObject>();
        try {
        	SecurityResultObject securityResultObject = queryManager.queryCustomerSecurityInfo(accountInfo);
            result.setResult(securityResultObject);
            log.info("success to queryCustomerInfo,PARAMETER:{}, RESULT:{}",accountInfo,result);  
            return result;
        } catch (BizException ex) {
            result.setErrorCode(ex.getCode());
            result.setErrorMsg(ex.getMessage());
            log.info("failed to queryCustomerInfo,PARAMETER:{}, RESULT:{}",accountInfo,result);     
            return result;
        } catch (Exception ex) {
        	log.error("failed to queryCustomerInfo,PARAMETER:{}, CAUSE:{}",accountInfo,Throwables.getStackTraceAsString(ex));
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }

    }


	@Override
	public Response<String> queryRiskBlack(AccountInfo accountInfo, ManagerLog managerLog) {
        log.info("call queryRiskBlack, PARAMETER:{} {}",accountInfo,managerLog);
        Response<String> result = new Response<String>();
        try {
            result.setResult(queryManager.doQueryRiskBlack(accountInfo, managerLog));
            log.info("success to queryRiskBlack, PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,managerLog,result});
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to queryRiskBlack, PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,managerLog,result});
        } catch (Exception e) {
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to queryRiskBlack, PARAMETER:{} {}, CAUSE:{}",new Object[]{accountInfo,managerLog,Throwables.getStackTraceAsString(e)});

        }
        return result;
	}


	@Override
	public Response<List<TDictCode>> querySecurityQuestions(ManagerLog managerLog) {
		
		log.info("call querySecurityQuestions, parameter:{}", managerLog);
    	Response<List<TDictCode>> r = new Response<List<TDictCode>>();
        try {
        	r.setResult(queryManager.querySecurityQuestions(managerLog));
        	log.info("success to querySecurityQuestions, parameter:{}, result:{}", managerLog, r);
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to querySecurityQuestions, parameter:{}, result:{}", managerLog, r);
        } catch (Exception e) {
        	log.error("failed to querySecurityQuestions, parameter:{}, cause:{}", managerLog,
        			Throwables.getStackTraceAsString(e));
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
        }
        return r;
	}

	@Override
	public Response<TInfoCustomer> queryMobilePhoneBinding(
			TInfoCustomer customer) {
		
		log.info("call queryMobilePhoneBinding, parameter:{}", customer);
    	Response<TInfoCustomer> r = new Response<TInfoCustomer>();
        try {
        	r.setResult(queryManager.queryMobilePhoneBinding(customer));
        	log.info("success to queryMobilePhoneBinding, parameter:{}, result:{}", customer, r);
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to queryMobilePhoneBinding, parameter:{}, result:{}", customer, r);
        } catch (Exception e) {
        	log.error("failed to queryMobilePhoneBinding, parameter:{}, cause:{}", customer,
        			Throwables.getStackTraceAsString(e));
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
        }
        return r;
	}

	@Override
	public Response<List<TInfoBankcard>> queryBankCardBinding(
			TInfoBankcard tInfoBankcard) {
		log.info("call queryBankCardBinding, parameter:{}", tInfoBankcard);
    	Response<List<TInfoBankcard>> r = new Response<List<TInfoBankcard>>();
        try {
        	r.setResult(queryManager.queryBankCardBinding(tInfoBankcard));
        	log.info("success to queryBankCardBinding, parameter:{}, result:{}", tInfoBankcard, r);
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to queryBankCardBinding, parameter:{}, result:{}", tInfoBankcard, r);
        } catch (Exception e) {
        	log.error("failed to queryBankCardBinding, parameter:{}, cause:{}", tInfoBankcard,
        			Throwables.getStackTraceAsString(e));
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
        }
        return r;
	}

	@Override
	public Response<List<TDictCode>> querySecuritySysData(
			ManagerLog managerLog, String dictEng) {
		log.info("call querySecuritySysData, parameter:{}", managerLog);
    	Response<List<TDictCode>> r = new Response<List<TDictCode>>();
        try {
        	r.setResult(queryManager.querySecuritySysData(managerLog,dictEng));
        	log.info("success to querySecuritySysData, parameter:{}, result:{}", managerLog, r);
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to querySecuritySysData, parameter:{}, result:{}", managerLog, r);
        } catch (Exception e) {
        	log.error("failed to querySecuritySysData, parameter:{}, cause:{}", managerLog,
        			Throwables.getStackTraceAsString(e));
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
        }
        return r;
	}

	@Override
	public Response<List<TInfoAccountBankCard>> queryAccountBankCardBinding(
			TInfoAccountBankCard tInfoAccountBankCard) {
		log.info("call queryAccountBankCardBinding, parameter:{}", tInfoAccountBankCard);
    	Response<List<TInfoAccountBankCard>> r = new Response<List<TInfoAccountBankCard>>();
        try {
        	r.setResult(queryManager.queryAccountBankCardBinding(tInfoAccountBankCard));
        	log.info("success to queryAccountBankCardBinding, parameter:{}, result:{}", tInfoAccountBankCard, r);
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to queryAccountBankCardBinding, parameter:{}, result:{}", tInfoAccountBankCard, r);
        } catch (Exception e) {
        	log.error("failed to queryAccountBankCardBinding, parameter:{}, cause:{}", tInfoAccountBankCard,
        			Throwables.getStackTraceAsString(e));
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
        }
        return r;
	}

   

}
