package com.huateng.p3.account.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.component.Response;

/**
 * 积分账户
 * User: dongpeiji
 * Date: 14-9-13
 * Time: 下午1:15
 */
public interface AccountIntegralService {

    /**
     * 积分账户充值和消费
     *
     * @param accountInfo 账户交易信息
     * @param paymentInfo 额外的支付信息，清结算等日志相关信息
     * @return 积分余额
     */
    Response<TxnResultObject> chargeOrConsume(PaymentInfo paymentInfo,AccountInfo accountInfo,boolean bool);





}
