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
import com.huateng.p3.account.service.inner.AccountTransferManager;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 13-12-31
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class AccountTransferServiceImpl implements AccountTransferService {
	@Autowired
    private AccountTransferManager accountTransferManager;

    @Override
    public Response<TxnResultObject> transfer(PaymentInfo paymentInfo , AccountInfo accountInfo) {
    	log.info("call transfer,PARAMETER:{} {}",paymentInfo,accountInfo);  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountTransferManager.transfer(paymentInfo, accountInfo));
            log.info("success to transfer,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to transfer,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to transfer,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            log.error("failed to transfer,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

        	r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to transfer,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to transfer,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
    
    @Override
    public Response<TxnResultObject> transferCheck(PaymentInfo paymentInfo , AccountInfo accountInfo) {
    	log.info("call transferCheck,PARAMETER:{} {}",paymentInfo,accountInfo);  
    	Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountTransferManager.transferCheck(paymentInfo, accountInfo));
            log.info("success to transferCheck,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});     
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to transferCheck,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (SubmitBizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to transferCheck,PARAMETER:{} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,r});  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
            	r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
            log.error("failed to transferCheck,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

        	r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to transferCheck,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to transferCheck,PARAMETER:{} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
}
