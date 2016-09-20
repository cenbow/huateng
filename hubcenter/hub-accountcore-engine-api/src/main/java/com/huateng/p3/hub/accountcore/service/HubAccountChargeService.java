package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.component.Response;

/**
 * 资金账户充值相关的所有接口
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:45
 */
public interface HubAccountChargeService {
    /**
     * 向账户内充值
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return 余额
     */
    Response<TxnResultObject> charge(PaymentInfo paymentInfo,AccountInfo accountInfo )throws Exception;

    /**
     * 向账户内充值检查
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return 余额
     */
    Response<TxnResultObject> chargeCheck(PaymentInfo paymentInfo,AccountInfo accountInfo )throws Exception;

    /**
     * 账户充值撤销
     *
     * @param accountInfo
     * @param paymentInfo
     * @param originalPayInfo
     * @return
     */
    Response<TxnResultObject> cancelCharge( PaymentInfo paymentInfo, PaymentInfo originalPayInfo ,AccountInfo accountInfo)throws Exception;

    /**
     * 账户充值冲正
     * @param accountInfo
     * @param paymentInfo
     * @param originalPayInfo
     * @return
     */
    Response<TxnResultObject> rollbackCharge(PaymentInfo paymentInfo ,PaymentInfo originalPayInfo ,AccountInfo accountInfo)throws Exception;

    /**
     * 账户充值撤销冲正
     * @param accountInfo
     * @param paymentInfo
     * @param originalPayInfo
     * @return
     */
    Response<TxnResultObject> rollbackChargeCancel(PaymentInfo paymentInfo ,PaymentInfo originalPayInfo ,AccountInfo accountInfo)throws Exception;

}
