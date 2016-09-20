package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.component.Response;

/**
 * 资金账户消费相关的所有接口
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:46
 */
public interface HubAccountConsumeService {
    /**
     * 从账户内消费
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return 余额
     */
    Response<TxnResultObject> consume(PaymentInfo paymentInfo,AccountInfo accountInfo)throws Exception;

    /**
     * 无密消费
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return
     */
    Response<TxnResultObject> consumeWithoutPwd(PaymentInfo paymentInfo,AccountInfo accountInfo)throws Exception;

    /**
     * 消费撤销
     *
     * @param accountInfo
     * @param paymentInfo
     * @param oldpaymentInfo
     * @return
     */
    Response<TxnResultObject> cancelConsume( PaymentInfo paymentInfo, PaymentInfo oldpaymentInfo,AccountInfo accountInfo)throws Exception;


    /**
     * 消费冲正
     *
     * @param accountInfo
     * @param paymentInfo
     * @param oldpaymentInfo
     * @return
     */
    Response<TxnResultObject> rollbackConsume( PaymentInfo paymentInfo, PaymentInfo oldpaymentInfo,AccountInfo accountInfo)throws Exception;


    /**
     * 快捷消费 一充一销
     *
     * @param accountInfo 账户交易信息
     * @param consumePaymentInfo
     * @param chargePaymentInfo
     * @return
     */
    Response<TxnResultObject> quickConsume(AccountInfo accountInfo, PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo)throws Exception;

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
                                                 PaymentInfo oldConsumePaymentInfo,PaymentInfo oldChargePaymentInfo)throws Exception;


    /**
     * 消费撤销冲正
     *
     * @param accountInfo
     * @param paymentInfo
     * @param oldpaymentInfo
     * @return
     */
    Response<TxnResultObject> rollbackConsumeCancel( PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,AccountInfo accountInfo)throws Exception;

}
