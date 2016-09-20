package com.huateng.p3.account.service;

import com.huateng.p3.component.Response;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;

/**
 * 资金账户转账相关的所有接口
 *
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */
public interface AccountTransferService {
    /**
     * 帐户间互转
     * 从 accountKey 转到 targetAccountKey 中
     *
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @param accountInfo 账户交易信息
     * @return 余额
     */
    Response<TxnResultObject> transfer(PaymentInfo paymentInfo, AccountInfo accountInfo );
    
    /**
     * 帐户间互转检查
     * 从 accountKey 转到 targetAccountKey 中
     *
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @param accountInfo 账户交易信息
     * @return 余额
     */
    Response<TxnResultObject> transferCheck(PaymentInfo paymentInfo, AccountInfo accountInfo );
}
