package com.huateng.p3.hub.accountcore.service;


import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.persistence.models.TLogCashApply;
import com.huateng.p3.component.Response;

/**
 * 提现服务
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:44
 */
public interface HubAccountCashService {
    /**
     * 提现申请
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashApply(PaymentInfo paymentInfo, AccountInfo accountInfo)throws Exception;

    /**
     * 提现完成
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */

    Response<TxnResultObject> cashComplete(AccountInfo accountInfo ,PaymentInfo paymentInfo )throws Exception;


    /**
     * 提现失败
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashFailComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo)throws Exception;

    /**
     * 提现完成撤销
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashFailCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo)throws Exception;



    /**
     * 提现申请撤销
     *
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    Response<TxnResultObject> cashApplyCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo)throws Exception;



    Response<TLogCashApply> selectTLogCashApply(String transSeqNo)throws Exception;
    
    Response<TLogCashApply> insertTLogCashApply(TLogCashApply cashApply)throws Exception;


    Response<TLogCashApply> updateTLogCashApply(TLogCashApply cashApply)throws Exception;

    
    
    
}
