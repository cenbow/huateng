package com.huateng.p3.account.service;

import com.huateng.p3.component.Response;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;

/**
 * 资金账户圈存相关的所有接口
 *
 * User: jijiandong
 * Date: 14-5-7
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */
public interface AccountEnclosureService {

	/**
	 * 圈存，从现金账户转入脱机账户
	 * @param paymentInfo
	 * @param accountInfo
	 * @return
	 */
    Response<TxnResultObject> enclosureInAccount(PaymentInfo paymentInfo, AccountInfo accountInfo);
    
    /**
     * 圈存冲正
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> rollbackEnclosureInAccount(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
    /**
     * 圈提，从脱机账户转入现金账户
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> enclosureOutAccount(PaymentInfo paymentInfo, AccountInfo accountInfo);
    
    /**
     * 圈提冲正
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> rollbackEnclosureOutAccount(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
}
