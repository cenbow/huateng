package com.huateng.p3.account.service;

import com.huateng.p3.component.Response;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;

/**
 * 资金账户消费相关的所有接口
 * <p/>
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 12/3/13 11:25 AM
 */
public interface AccountConsumeService {

    /**
     * 从账户内消费
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return 余额
     */
    Response<TxnResultObject> consume(PaymentInfo paymentInfo,AccountInfo accountInfo);

    /**
     * 无密消费
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return
     */
    Response<TxnResultObject> consumeWithoutPwd(PaymentInfo paymentInfo,AccountInfo accountInfo);

    /**
     * 消费撤销
     *
     * @param accountInfo
     * @param paymentInfo
     * @param oldpaymentInfo
     * @return
     */
    Response<TxnResultObject> cancelConsume( PaymentInfo paymentInfo, PaymentInfo oldpaymentInfo,AccountInfo accountInfo);


    /**
     * 消费冲正
     *
     * @param accountInfo
     * @param paymentInfo
     * @param oldpaymentInfo
     * @return
     */
    Response<TxnResultObject> rollbackConsume( PaymentInfo paymentInfo, PaymentInfo oldpaymentInfo,AccountInfo accountInfo);
    
    
    /**
     * 快捷消费 一充一销
     *
     * @param accountInfo 账户交易信息
     * @param consumePaymentInfo 
     * @param chargePaymentInfo 
     * @return
     */
    Response<TxnResultObject> quickConsume(AccountInfo accountInfo, PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo);
    
    /**
     * 
     * @param accountInfo
     * @param consumePaymentInfo
     * @param chargePaymentInfo
     * @param oldConsumePaymentInfo
     * @param oldChargePaymentInfo
     * @return
     */
    Response<TxnResultObject> cancelQuickConsume(AccountInfo accountInfo, PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo,
    		PaymentInfo oldConsumePaymentInfo,PaymentInfo oldChargePaymentInfo);


    /**
     * 消费撤销冲正
     *
     * @param accountInfo
     * @param paymentInfo
     * @param oldpaymentInfo
     * @return
     */
    Response<TxnResultObject> rollbackConsumeCancel( PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,AccountInfo accountInfo);
    
   /* *//**
     * 脱机消费
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return 余额
     *//*
    Response<TxnResultObject> offlineConsume(AccountInfo accountInfo, PaymentInfo paymentInfo);*/
}
