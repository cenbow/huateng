package com.huateng.p3.account.service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.service.AccountIntegralService;
import com.huateng.p3.account.service.inner.AccountIntegralManager;
import com.huateng.p3.component.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * User: dongpeiji
 * Date: 14-9-13
 * Time: 下午2:03
 */
@Service
@Slf4j
public class AccountIntegralServiceImpl implements AccountIntegralService {

    @Autowired
    private AccountIntegralManager accountIntegralManager;

    @Override
    public Response<TxnResultObject> chargeOrConsume(PaymentInfo paymentInfo, AccountInfo accountInfo, boolean bool) {
        log.info("call chargeOrConsume,PARAMETER:{} {} {}",new Object[]{paymentInfo,accountInfo,bool});
        Response<TxnResultObject> r = new Response<TxnResultObject>();
        try {
            r.setResult(accountIntegralManager.chargeOrConsume(accountInfo, paymentInfo,bool));
            log.info("success to chargeOrConsume,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,bool,r});
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to chargeOrConsume,PARAMETER:{} {} {}, RESULT:{}",new Object[]{paymentInfo,accountInfo,bool,r});
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
                r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
                r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }

            log.error("failed to chargeOrConsume,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,bool, Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to chargeOrConsume,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,bool,Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to chargeOrConsume,PARAMETER:{} {} {}, CAUSE:{}",new Object[]{paymentInfo,accountInfo,bool,Throwables.getStackTraceAsString(e)});
            return r;
        }
    }
}
