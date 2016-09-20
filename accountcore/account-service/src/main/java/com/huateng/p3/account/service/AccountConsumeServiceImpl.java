package com.huateng.p3.account.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.service.inner.AccountConsumeManager;
import com.huateng.p3.component.Response;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 12/3/13 3:26 PM
 */
@Service
@Slf4j
public class AccountConsumeServiceImpl implements AccountConsumeService {

    @Autowired
    private AccountConsumeManager accountConsumeManager;


    @Override
    public Response<TxnResultObject> consume(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	log.info("call consume,PARAMETER:{} {}",paymentInfo,accountInfo);  
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountConsumeManager.consume(paymentInfo, accountInfo));
            log.info("success to consume,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to consume,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to consume,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to consume,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to consume,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to consume,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }

    @Override
    public Response<TxnResultObject> consumeWithoutPwd(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	log.info("call consumeWithoutPwd,PARAMETER:{} {}",paymentInfo,accountInfo);  
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountConsumeManager.consumeWithoutPwd(paymentInfo, accountInfo);
            r.setResult(balance);
            log.info("success to consumeWithoutPwd,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to consumeWithoutPwd,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to consumeWithoutPwd,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to consumeWithoutPwd,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to consumeWithoutPwd,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }

    @Override
    public Response<TxnResultObject> cancelConsume(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	log.info("call cancelConsume,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
    	Response<TxnResultObject> res = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountConsumeManager.cancelConsume(paymentInfo, oldPaymentInfo, accountInfo);
            res.setResult(balance);
            log.info("success to cancelConsume,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});   
            return res;
        } catch (BizException e) {
            res.setErrorCode(e.getCode());
            res.setErrorMsg(e.getMessage());
            log.info("failed to cancelConsume,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});              
            return res;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
          
            log.error("failed to cancelConsume,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;

        } catch (CannotAcquireLockException e) {

        	res.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cancelConsume,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        } catch (Exception e) {
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cancelConsume,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }
    }

    @Override
    public Response<TxnResultObject> rollbackConsume(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	log.info("call rollbackConsume,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
    	Response<TxnResultObject> res = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountConsumeManager.rollbackConsume(paymentInfo, oldPaymentInfo, accountInfo);
            res.setResult(balance);
            log.info("success to rollbackConsume,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});   
            return res;
        } catch (BizException e) {
            res.setErrorCode(e.getCode());
            res.setErrorMsg(e.getMessage());
            log.info("failed to rollbackConsume,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});             
            return res;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackConsume,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;

        } catch (CannotAcquireLockException e) {

        	res.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
        	res.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
        	log.error("failed to rollbackConsume,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }catch (Exception e) {
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackConsume,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }
    }
    
   

    @Override
    public Response<TxnResultObject> quickConsume(AccountInfo accountInfo, PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo){
    	log.info("call quickConsume,PARAMETER:{} consume{} charge{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo});  
    	Response<TxnResultObject> res = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountConsumeManager.quickConsume(accountInfo,consumePaymentInfo,chargePaymentInfo);
            res.setResult(balance);
            log.info("success to quickConsume,PARAMETER:{} consume{} charge{}, RESULT:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo ,res});    
            return res;
        } catch (BizException e) {
            res.setErrorCode(e.getCode());
            res.setErrorMsg(e.getMessage());
            log.info("failed to quickConsume,PARAMETER:{} consume{} charge{}, RESULT:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,res});             
            return res;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to quickConsume,PARAMETER:{} consume{} charge{}, CAUSE:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,Throwables.getStackTraceAsString(e)});
            return res;

        } catch (CannotAcquireLockException e) {

        	res.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
        	res.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
        	log.error("failed to quickConsume,PARAMETER:{} consume{} charge{}, CAUSE:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }catch (Exception e) {
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to quickConsume,PARAMETER:{} consume{} charge{}, CAUSE:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }
    }
    
    @Override
    public Response<TxnResultObject> cancelQuickConsume(AccountInfo accountInfo, PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo,PaymentInfo oldConsumePaymentInfo,PaymentInfo oldChargePaymentInfo){
    	log.info("call cancelQuickConsume,PARAMETER:{} consume{} charge{} oldconsume{} oldcharge{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,oldConsumePaymentInfo,oldConsumePaymentInfo});  
    	Response<TxnResultObject> res = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountConsumeManager.cancelQuickConsume(accountInfo,consumePaymentInfo,chargePaymentInfo,oldConsumePaymentInfo,oldConsumePaymentInfo);
            res.setResult(balance);
            log.info("success to cancelQuickConsume,PARAMETER:{} consume{} charge{} oldconsume{} oldcharge{}, RESULT:{}", new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,oldConsumePaymentInfo,oldConsumePaymentInfo ,res});    
            return res;
        } catch (BizException e) {
            res.setErrorCode(e.getCode());
            res.setErrorMsg(e.getMessage());
            log.info("failed to cancelQuickConsume,PARAMETER:{} consume{} charge{} oldconsume{} oldcharge{}, RESULT:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,oldConsumePaymentInfo,oldConsumePaymentInfo,res});             
            return res;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to cancelQuickConsume,PARAMETER:{} consume{} charge{} oldconsume{} oldcharge{}, CAUSE:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,oldConsumePaymentInfo,oldConsumePaymentInfo,Throwables.getStackTraceAsString(e)});
            return res;

        } catch (CannotAcquireLockException e) {

        	res.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
        	res.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
        	log.error("failed to cancelQuickConsume,PARAMETER:{} consume{} charge{} oldconsume{} oldcharge{}, CAUSE:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,oldConsumePaymentInfo,oldConsumePaymentInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }catch (Exception e) {
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cancelQuickConsume,PARAMETER:{} consume{} charge{} oldconsume{} oldcharge{}, CAUSE:{}",new Object[]{accountInfo,consumePaymentInfo,chargePaymentInfo,oldConsumePaymentInfo,oldConsumePaymentInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }
    }

	@Override
	public Response<TxnResultObject> rollbackConsumeCancel(
			PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,
			AccountInfo accountInfo) {
		log.info("call rollbackConsumeCancle,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
    	Response<TxnResultObject> res = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountConsumeManager.rollbackConsumeCancel(paymentInfo, oldPaymentInfo, accountInfo);
            res.setResult(balance);
            log.info("success to rollbackConsumeCancle,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});   
            return res;
        } catch (BizException e) {
            res.setErrorCode(e.getCode());
            res.setErrorMsg(e.getMessage());
            log.info("failed to rollbackConsumeCancle,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});             
            return res;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackConsumeCancle,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;

        } catch (CannotAcquireLockException e) {

        	res.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
        	res.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
        	log.error("failed to rollbackConsumeCancle,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }catch (Exception e) {
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackConsumeCancle,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }
	}
/*
	@Override
	public Response<TxnResultObject> offlineConsume(AccountInfo accountInfo, PaymentInfo paymentInfo) {
    	log.info("call offlineConsume,parameter:{} {}",paymentInfo,accountInfo);  
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountConsumeManager.offlineConsume(accountInfo, paymentInfo));
            log.info("success to offlineConsume,parameter:{} {}, result:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to offlineConsume,parameter:{} {}, result:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to offlineConsume,parameter:{} {}, result:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to offlineConsume,parameter:{} {}, cause:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to offlineConsume,parameter:{} {}, cause:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to offlineConsume,parameter:{} {}, cause:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
    */
}