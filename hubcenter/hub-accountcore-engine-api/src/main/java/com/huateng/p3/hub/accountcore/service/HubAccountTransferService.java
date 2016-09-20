package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:48
 */
public interface HubAccountTransferService {
    /**
     * 帐户间互转
     * 从 accountKey 转到 targetAccountKey 中
     *
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @param accountInfo 账户交易信息
     * @return 余额
     */
    Response<TxnResultObject> transfer(PaymentInfo paymentInfo, AccountInfo accountInfo )throws Exception;

    /**
     * 帐户间互转检查
     * 从 accountKey 转到 targetAccountKey 中
     *
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @param accountInfo 账户交易信息
     * @return 余额
     */
    Response<TxnResultObject> transferCheck(PaymentInfo paymentInfo, AccountInfo accountInfo )throws Exception;

}
