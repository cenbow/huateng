package com.huateng.p3.account.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.service.inner.AccountChargeManager;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class AccountChargeServiceImpl implements AccountChargeService {
    @Autowired
    private AccountChargeManager accountChargeManager;

    @Override
    public Response<TxnResultObject> charge(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	log.info("call charge,PARAMETER:{} {}",paymentInfo,accountInfo);    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountChargeManager.charge(accountInfo, paymentInfo));
            log.info("success to charge,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to charge,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
         
            log.error("failed to charge,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to charge,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to charge,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
    
    @Override
	public Response<TxnResultObject> chargeCheck(PaymentInfo paymentInfo,
			AccountInfo accountInfo) {
    	log.info("call chargeCheck,PARAMETER:{} {}",paymentInfo,accountInfo);    
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountChargeManager.chargeCheck(accountInfo, paymentInfo));
            log.info("success to chargeCheck,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to chargeCheck,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to chargeCheck,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to chargeCheck,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to chargeCheck,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

    @Override
    public Response<TxnResultObject> cancelCharge(PaymentInfo paymentInfo, PaymentInfo originalPayInfo ,AccountInfo accountInfo) {
    	log.info("call cancelCharge,PARAMETER:{} old{} {}",new Object[]{paymentInfo,originalPayInfo,accountInfo});  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountChargeManager.cancelCharge( paymentInfo, originalPayInfo ,accountInfo);
            r.setResult(balance);
            log.info("success to cancelCharge,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,r});   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to cancelCharge,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            
            log.error("failed to cancelCharge,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to cancelCharge,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to cancelCharge,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }

    @Override
    public Response<TxnResultObject> rollbackCharge(PaymentInfo paymentInfo, PaymentInfo originalPayInfo , AccountInfo accountInfo) {
    	log.info("call rollbackCharge,PARAMETER:{} old{} {}",new Object[]{paymentInfo,originalPayInfo,accountInfo});
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountChargeManager.rollbackCharge(paymentInfo, originalPayInfo ,accountInfo);
            r.setResult(balance);
            log.info("success to rollbackCharge,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,r});   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to rollbackCharge,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackCharge,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackCharge,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackCharge,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }

	@Override
	public Response<TxnResultObject> rollbackChargeCancel(
			PaymentInfo paymentInfo, PaymentInfo originalPayInfo,
			AccountInfo accountInfo) {
		log.info("call rollbackChargeCancel,PARAMETER:{} old{} {}",new Object[]{paymentInfo,originalPayInfo,accountInfo});
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            TxnResultObject balance = accountChargeManager.rollbackChargeCancel(paymentInfo, originalPayInfo ,accountInfo);
            r.setResult(balance);
            log.info("success to rollbackChargeCancel,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,r});   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to rollbackChargeCancel,PARAMETER:{} old{} {}, RESULT:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackChargeCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackChargeCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackChargeCancel,PARAMETER:{} old{} {}, CAUSE:{}",new Object[]{paymentInfo,originalPayInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}
}
