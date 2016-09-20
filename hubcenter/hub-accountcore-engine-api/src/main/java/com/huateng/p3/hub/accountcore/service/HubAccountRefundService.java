package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.component.Response;

/**
 * 资金账户退货相关的所有接口
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:47
 */
public interface HubAccountRefundService {

    /**
     * 退货申请
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    Response<TxnResultObject> refundApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo)throws Exception;

    /**
     * 退货申请检查
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    Response<TxnResultObject> refundApplyCheck(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo)throws Exception;


    /**
     * 无审批直接退货
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    Response<TxnResultObject> refundNoApplyAudit(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo)throws Exception;

}
