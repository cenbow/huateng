package com.huateng.p3.account.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.persistence.models.TLogCashApply;
import com.huateng.p3.component.Response;

/**
 * 提现服务
 * User: JamesTang
 * Date: 14-1-3
 * Time: 下午1:42
 */
public interface AccountCashService {

    /**
     * 提现申请
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashApply(PaymentInfo paymentInfo, AccountInfo accountInfo);

    /**
     * 提现完成
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */

    Response<TxnResultObject> cashComplete(AccountInfo accountInfo ,PaymentInfo paymentInfo );
    

    /**
     * 提现失败
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashFailComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);
    
    /**
     * 提现完成撤销
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashFailCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);



    /**
     * 提现申请撤销
     *
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashApplyCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo);


    Response<TLogCashApply> selectTLogCashApply(String transSeqNo);
    
    Response<TLogCashApply> insertTLogCashApply(TLogCashApply cashApply);
    
    Response<TLogCashApply> updateTLogCashApply(TLogCashApply cashApply);
     


    
    
}
