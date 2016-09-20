package com.huateng.p3.account.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.persistence.models.TLogCashApply;
import com.huateng.p3.account.service.inner.AccountCashManager;
import com.huateng.p3.component.Response;

/**
 * User: JamesTang
 * Date: 14-1-6
 * Time: 上午10:42
 */
@Service
@Slf4j
public class AccountCashServiceImpl implements AccountCashService {
     @Autowired
     private
     AccountCashManager accountCashManager;

    @Override
    public Response<TxnResultObject> cashApply(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	log.info("call cashApply,PARAMETER:{} {}",paymentInfo,accountInfo);    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountCashManager.cashApply(paymentInfo,accountInfo);
            r.setResult(txnResultObject);
            log.info("success to cashApply,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});
            r.setErrorCode(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_000000.getErrordesc());
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cashApply,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
        	 r.setErrorCode(e.getCode());
             r.setErrorMsg(e.getMessage());
             log.info("failed to cashApply,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
             return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to cashApply,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cashApply,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cashApply,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }

    @Override
    public Response<TxnResultObject> cashComplete(AccountInfo accountInfo,PaymentInfo paymentInfo) {
    	log.info("call cashComplete,PARAMETER:{} {}",paymentInfo,accountInfo);  
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountCashManager.cashComplete(accountInfo,paymentInfo);
            r.setResult(txnResultObject);
            log.info("success to cashComplete,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cashComplete,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to cashComplete,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cashComplete,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cashComplete,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }

    @Override
    public Response<TxnResultObject> cashFailComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	log.info("call cashFailComplete,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountCashManager.cashFailComplete(paymentInfo,oldPaymentInfo,accountInfo);
            r.setResult(txnResultObject);
            log.info("success to cashFailComplete,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,r});   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cashFailComplete,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to cashFailComplete,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cashFailComplete,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cashFailComplete,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
    
    
    @Override
    public Response<TxnResultObject> cashFailCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	log.info("call cashFailCancel,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountCashManager.cashFailCancel(paymentInfo,oldPaymentInfo,accountInfo);
            r.setResult(txnResultObject);
            log.info("success to cashFailCancel,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,r});   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cashFailCancel,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to cashFailCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cashFailCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cashFailCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }

    @Override
    public Response<TxnResultObject> cashApplyCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	log.info("call cashApplyCancel,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject  txnResultObject=  accountCashManager.cashApplyCancel(paymentInfo,oldPaymentInfo,accountInfo);
            r.setResult(txnResultObject);
            log.info("success to cashApplyCancel,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,r});   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cashApplyCancel,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to cashApplyCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cashApplyCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cashApplyCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
    
    

    @Override
    public Response<TLogCashApply> selectTLogCashApply(String transSeqNo) {
    	log.info("call selectTLogCashApply,PARAMETER:{}",transSeqNo); 
        Response<TLogCashApply> result = new Response<TLogCashApply>();
        try {
        	TLogCashApply tLogCashApply = accountCashManager.selectTLogCashApply(transSeqNo);
            result.setResult(tLogCashApply);
            log.info("success to selectTLogCashApply,PARAMETER:{}, RESULT:{}",transSeqNo,result);
            return result;
        } catch (BizException ex) {
            result.setErrorCode(ex.getCode());
            result.setErrorMsg(ex.getMessage());
            log.info("failed to selectTLogCashApply,PARAMETER:{}, RESULT:{}",transSeqNo,result);
            return result;
        } catch (Exception ex) {
        	log.error("failed to selectTLogCashApply,PARAMETER:{}, CAUSE:{}",transSeqNo,Throwables.getStackTraceAsString(ex));
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }

    }
    
    @Override
    public Response<TLogCashApply> insertTLogCashApply(TLogCashApply cashApply) {
    	log.info("call insertTLogCashApply,PARAMETER:{}",cashApply); 
        Response<TLogCashApply> result = new Response<TLogCashApply>();
        try {
        	TLogCashApply tLogCashApply = accountCashManager.insertTLogCashApply(cashApply);
            result.setResult(tLogCashApply);
            log.info("success to insertTLogCashApply,PARAMETER:{}, RESULT:{}",cashApply,result);
            return result;
        } catch (BizException ex) {
            result.setErrorCode(ex.getCode());
            result.setErrorMsg(ex.getMessage());
            log.info("failed to insertTLogCashApply,PARAMETER:{}, RESULT:{}",cashApply,result);
            return result;
        } catch (Exception ex) {
        	log.error("failed to insertTLogCashApply,PARAMETER:{}, CAUSE:{}",cashApply,Throwables.getStackTraceAsString(ex));
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }

    }
  
    
    @Override
    public Response<TLogCashApply> updateTLogCashApply(TLogCashApply cashApply) {
    	log.info("updateTLogCashApply,PARAMETER:{}",cashApply); 
        Response<TLogCashApply> result = new Response<TLogCashApply>();
        try {
        	TLogCashApply tLogCashApply = accountCashManager.updateTLogCashApply(cashApply);
            result.setResult(tLogCashApply);
            log.info("success to updateTLogCashApply,PARAMETER:{}, RESULT:{}",cashApply,result);
            return result;
        } catch (BizException ex) {
            result.setErrorCode(ex.getCode());
            result.setErrorMsg(ex.getMessage());
            log.info("failed to updateTLogCashApply,PARAMETER:{}, RESULT:{}",cashApply,result);
            return result;
        } catch (Exception ex) {
        	log.error("failed to updateTLogCashApply,PARAMETER:{}, CAUSE:{}",cashApply,Throwables.getStackTraceAsString(ex));
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }

    }
    
   
    
}
