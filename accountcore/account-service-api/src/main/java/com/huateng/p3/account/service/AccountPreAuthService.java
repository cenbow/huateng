package com.huateng.p3.account.service;

import com.huateng.p3.component.Response;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;

/**
 * @author James Tang
 *         Desc           预授权
 *         Date: 13-12-4
 *         Time: 上午9:29
 *         To change this template use File | Settings | File Templates.
 */
public interface AccountPreAuthService {
	
	/**
     * 预授权申请
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> preAuthApply(PaymentInfo paymentInfo, AccountInfo accountInfo);

    /**
     * 预授权申请 冲正
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> rollbackPreAuthApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
    /**
     * 预授权申请 撤销
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cancelPreAuthApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);

    /**
     * 预授权申请 撤销冲正
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> rollbackCancelPreAuthApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
    
    /**
     * 预授权完成
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> preAuthComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
    /**
     * 预授权完成冲正
     * 
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> rollbackPreAuthComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
    /**
     * 预授权完成撤销
     * 
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cancelPreAuthComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
    /**
     * 预授权完成撤销冲正
     * 
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> rollbackCancelPreAuthComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
}
