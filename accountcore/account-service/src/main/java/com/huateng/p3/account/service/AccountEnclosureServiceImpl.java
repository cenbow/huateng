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
import com.huateng.p3.account.service.inner.AccountEnclosureManager;
import com.huateng.p3.component.Response;

/**
 * User: jijiandong
 * Date: 14-5-7
 */
@Service
@Slf4j
public class AccountEnclosureServiceImpl implements AccountEnclosureService {
	@Autowired
    private AccountEnclosureManager accountEnclosureManager;

    @Override
    public Response<TxnResultObject> enclosureInAccount(PaymentInfo paymentInfo , AccountInfo accountInfo) {
    	log.info("call enclosureInAccount,PARAMETER:{} {}",paymentInfo,accountInfo);  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountEnclosureManager.enclosureInAccount(paymentInfo, accountInfo));
            log.info("success to enclosureInAccount,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to enclosureInAccount,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to enclosureInAccount,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            log.error("failed to enclosureInAccount,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

        	r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to enclosureInAccount,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to enclosureInAccount,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
    
    @Override
    public Response<TxnResultObject> rollbackEnclosureInAccount(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	log.info("call rollbackEnclosureInAccount,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
    	Response<TxnResultObject> res = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountEnclosureManager.rollbackEnclosureInAccount(paymentInfo, oldPaymentInfo, accountInfo);
            res.setResult(balance);
            log.info("success to rollbackEnclosureInAccount,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo},res);   
            return res;
        } catch (BizException e) {
            res.setErrorCode(e.getCode());
            res.setErrorMsg(e.getMessage());
            log.info("failed to rollbackEnclosureInAccount,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo},res);             
            return res;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackEnclosureInAccount,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;

        } catch (CannotAcquireLockException e) {

        	res.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
        	res.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
        	log.error("failed to rollbackEnclosureInAccount,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }catch (Exception e) {
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackEnclosureInAccount,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }
    }
    
    @Override
    public Response<TxnResultObject> enclosureOutAccount(PaymentInfo paymentInfo , AccountInfo accountInfo) {
    	log.info("call enclosureOutAccount,PARAMETER:{} {}",paymentInfo,accountInfo);  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountEnclosureManager.enclosureOutAccount(paymentInfo, accountInfo));
            log.info("success to enclosureOutAccount,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to enclosureOutAccount,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to enclosureOutAccount,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            log.error("failed to enclosureOutAccount,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

        	r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to enclosureOutAccount,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to enclosureOutAccount,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
    
    @Override
    public Response<TxnResultObject> rollbackEnclosureOutAccount(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	log.info("call rollbackEnclosureOutAccount,PARAMETER:{} old{} {}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo});  
    	Response<TxnResultObject> res = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountEnclosureManager.rollbackEnclosureOutAccount(paymentInfo, oldPaymentInfo, accountInfo);
            res.setResult(balance);
            log.info("success to rollbackEnclosureOutAccount,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});
            return res;
        } catch (BizException e) {
            res.setErrorCode(e.getCode());
            res.setErrorMsg(e.getMessage());
            log.info("failed to rollbackEnclosureOutAccount,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,res});
            return res;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	res.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	res.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackEnclosureOutAccount,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;

        } catch (CannotAcquireLockException e) {

        	res.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
        	res.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
        	log.error("failed to rollbackEnclosureOutAccount,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }catch (Exception e) {
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackEnclosureOutAccount,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,oldPaymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return res;
        }
    }
}
