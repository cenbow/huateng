package com.huateng.p3.hub.accountcore.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.persistence.models.TLogCashApply;
import com.huateng.p3.account.service.AccountCashService;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubAccountCashService;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午11:01
 */
@Service
public class HubAccountCashServicesImpl implements HubAccountCashService {

    @Autowired(required = false)
    private AccountCashService accountCashService;

    private static final Logger logger = LoggerFactory.getLogger(HubAccountCashServicesImpl.class);
   
    @Override
    public Response<TxnResultObject> cashApply(PaymentInfo paymentInfo, AccountInfo accountInfo)throws Exception
    {
        logger.info("cashApply AccountInfo:{},PaymentInfo:{}", new Object[]{accountInfo,paymentInfo});
        return accountCashService.cashApply(paymentInfo, accountInfo);
    }
    @Override
    public Response<TxnResultObject> cashComplete(AccountInfo accountInfo ,PaymentInfo paymentInfo )throws Exception
    {
        logger.info("cashComplete AccountInfo:{},PaymentInfo:{}", new Object[]{accountInfo,paymentInfo});
        return accountCashService.cashComplete(accountInfo, paymentInfo);
    }
    @Override
    public Response<TxnResultObject> cashFailComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo)throws Exception
    {
        logger.info("cashFailComplete AccountInfo:{},PaymentInfo:{},oldPaymentInfo:{}", new Object[]{accountInfo,paymentInfo,oldPaymentInfo});
        return accountCashService.cashFailComplete(paymentInfo, oldPaymentInfo, accountInfo);
    }
    @Override
    public Response<TxnResultObject> cashFailCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo)throws Exception{
        logger.info("cashFailCancel AccountInfo:{},PaymentInfo:{},oldPaymentInfo:{}", new Object[]{accountInfo,paymentInfo,oldPaymentInfo});
        return accountCashService.cashFailCancel(paymentInfo, oldPaymentInfo, accountInfo);
    }
    @Override
    public Response<TxnResultObject> cashApplyCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo)throws Exception{
        logger.info("cashApplyCancel AccountInfo:{},PaymentInfo:{},oldPaymentInfo:{}", new Object[]{accountInfo,paymentInfo,oldPaymentInfo});
        return accountCashService.cashApplyCancel(paymentInfo, oldPaymentInfo, accountInfo);
    }
    @Override
    public Response<TLogCashApply> selectTLogCashApply(String transSeqNo)throws Exception{
        logger.info("selectTLogCashApply transSeqNo:{}", new Object[]{transSeqNo});
        return accountCashService.selectTLogCashApply(transSeqNo);
    }
    @Override
    public Response<TLogCashApply> insertTLogCashApply(TLogCashApply cashApply)throws Exception{
        logger.info("insertTLogCashApply TLogCashApply:{}", new Object[]{cashApply});
        return accountCashService.insertTLogCashApply(cashApply);
    }
    @Override
    public Response<TLogCashApply> updateTLogCashApply(TLogCashApply cashApply)throws Exception{
        logger.info("updateTLogCashApply TLogCashApply:{}", new Object[]{cashApply});
        return accountCashService.updateTLogCashApply(cashApply);
    }
    

}
