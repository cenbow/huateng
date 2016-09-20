package com.huateng.p3.account.service;

import com.huateng.p3.component.Response;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;

/**
 * 资金账户退货相关的所有接口
 * <p/>
 * User: huwenjie
 * Date: 14-2-24
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public interface AccountRefundService {

   
    /**
     * 退货申请
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    Response<TxnResultObject> refundApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo);
    
    /**
     * 退货申请检查
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    Response<TxnResultObject> refundApplyCheck(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo);
    
    
    /**
     * 无审批直接退货
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    Response<TxnResultObject> refundNoApplyAudit(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo);


}
