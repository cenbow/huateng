package com.huateng.p3.account.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.service.inner.AccountRefundManager;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-2-24
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class AccountRefundServiceImpl implements AccountRefundService {
	
	@Autowired
    private AccountRefundManager accountRefundManager;
	
	
	@Override
	public Response<TxnResultObject> refundApply(PaymentInfo paymentInfo,PaymentInfo oldPaymentInfo) {
		log.info("call refundApply,PARAMETER:{} old{}", paymentInfo, oldPaymentInfo);  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountRefundManager.refundApply( paymentInfo, oldPaymentInfo));
            log.info("success to refundApply,PARAMETER:{} old{}, RESULT:{}", new Object[]{paymentInfo, oldPaymentInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to refundApply,PARAMETER:{} old{}, RESULT:{}", new Object[]{paymentInfo, oldPaymentInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }

            log.error("failed to refundApply,PARAMETER:{} old{}, CAUSE:{}", new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

        	r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to refundApply,PARAMETER:{} old{}, CAUSE:{}", new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;
        } catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to refundApply,PARAMETER:{} old{}, CAUSE:{}", new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> refundApplyCheck(PaymentInfo paymentInfo,
			PaymentInfo oldPaymentInfo) {
		log.info("call refundApplyCheck,PARAMETER:{} old{}", paymentInfo, oldPaymentInfo);  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountRefundManager.refundApplyCheck(paymentInfo, oldPaymentInfo));
            log.info("success to refundApplyCheck,PARAMETER:{} old{} , RESULT:{}", new Object[]{paymentInfo, oldPaymentInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to refundApplyCheck,PARAMETER:{} old{} , RESULT:{}", new Object[]{paymentInfo, oldPaymentInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            log.error("failed to refundApplyCheck,PARAMETER:{} old{} , CAUSE:{}", new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

        	r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to refundApplyCheck,PARAMETER:{} old{} , CAUSE:{}", new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;
        } catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to refundApplyCheck,PARAMETER:{} old{} , CAUSE:{}", new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}

	@Override
	public Response<TxnResultObject> refundNoApplyAudit(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo) {
		log.info("call refundNoApplyAudit,PARAMETER:{} old{} ",paymentInfo, oldPaymentInfo);  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountRefundManager.refundNoApplyAudit(paymentInfo, oldPaymentInfo));
            log.info("success to refundNoApplyAudit,PARAMETER:{} old{} , RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to refundNoApplyAudit,PARAMETER:{} old{} , RESULT:{}",new Object[]{paymentInfo, oldPaymentInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            log.error("failed to refundNoApplyAudit,PARAMETER:{} old{} , CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

        	r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to refundNoApplyAudit,PARAMETER:{} old{} , CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;
        } catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to refundNoApplyAudit,PARAMETER:{} old{} , CAUSE:{}",new Object[]{paymentInfo, oldPaymentInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
	}
	
}
