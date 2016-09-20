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
import com.huateng.p3.account.service.inner.AccountPreAuthManager;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 13-12-24
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class AccountPreAuthServiceImpl implements AccountPreAuthService {
	
	@Autowired
	AccountPreAuthManager accountPreAuthManager;

	@Override
	public Response<TxnResultObject> preAuthApply(PaymentInfo paymentInfo,
			AccountInfo accountInfo) {
		log.info("call preAuthRequest,PARAMETER:{} {}",paymentInfo,accountInfo);    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.preAuthApply(paymentInfo,accountInfo);
            r.setResult(txnResultObject);
            log.info("success to preAuthRequest,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to preAuthRequest,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to preAuthRequest,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to preAuthRequest,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to preAuthRequest,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to preAuthRequest,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> rollbackPreAuthApply(
			PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,
			AccountInfo accountInfo) {
		log.info("call rollbackPreAuthApply,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.rollbackPreAuthApply(paymentInfo, oldPaymentInfo, accountInfo);
            r.setResult(txnResultObject);
            log.info("call rollbackPreAuthApply,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});      
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("call rollbackPreAuthApply,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});   
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("call rollbackPreAuthApply,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});   
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.info("call rollbackPreAuthApply,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo}); 
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> cancelPreAuthApply(
			PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,
			AccountInfo accountInfo) {
		log.info("call cancelPreAuthApply,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.cancelPreAuthApply(paymentInfo, oldPaymentInfo, accountInfo);
            r.setResult(txnResultObject);
            log.info("success to cancelPreAuthApply,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cancelPreAuthApply,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to cancelPreAuthApply,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to cancelPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cancelPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cancelPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> rollbackCancelPreAuthApply(
			PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,
			AccountInfo accountInfo) {
		
		log.info("call rollbackCancelPreAuthApply,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.rollbackCancelPreAuthApply(paymentInfo, oldPaymentInfo, accountInfo);
            r.setResult(txnResultObject);
            log.info("success to rollbackCancelPreAuthApply,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to rollbackCancelPreAuthApply,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to rollbackCancelPreAuthApply,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to rollbackCancelPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackCancelPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackCancelPreAuthApply,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> preAuthComplete(PaymentInfo paymentInfo,
			PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
		
		log.info("call preAuthComplete,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.preAuthComplete(paymentInfo, accountInfo);
            r.setResult(txnResultObject);
            log.info("success to preAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to preAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to preAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to preAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to preAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to preAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> rollbackPreAuthComplete(
			PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,
			AccountInfo accountInfo) {
		log.info("call rollbackPreAuthComplete,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.rollbackPreAuthComplete(paymentInfo, oldPaymentInfo, accountInfo);
            r.setResult(txnResultObject);
            log.info("success to rollbackPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to rollbackPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to rollbackPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to rollbackPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> cancelPreAuthComplete(
			PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,
			AccountInfo accountInfo) {
		log.info("call cancelPreAuthComplete,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.cancelPreAuthComplete(paymentInfo, oldPaymentInfo, accountInfo);
            r.setResult(txnResultObject);
            log.info("success to cancelPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cancelPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to cancelPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to cancelPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cancelPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cancelPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> rollbackCancelPreAuthComplete(
			PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,
			AccountInfo accountInfo) {
		log.info("call rollbackCancelPreAuthComplete,PARAMETER:{} {} {}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo});    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountPreAuthManager.rollbackCancelPreAuthComplete(paymentInfo, oldPaymentInfo, accountInfo);
            r.setResult(txnResultObject);
            log.info("success to rollbackCancelPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to rollbackCancelPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to rollbackCancelPreAuthComplete,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to rollbackCancelPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackCancelPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackCancelPreAuthComplete,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo, accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

}
