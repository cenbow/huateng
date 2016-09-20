package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.*;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.persistence.models.TInfoSubaccount;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;

import java.util.List;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午10:53
 */
public interface HubAccountQueryService {

    /**
     * 账户实名请求记录查询
     *
     * @param accountInfo
     * @param managerLog
     */
    Response<List<TRealnameApply>> queryRealnameAuthenticationStatusDetails(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;


    /**
     * 账户余额查询
     *
     * @param accountInfo
     * @return
     */
    Response<List<AccountResultObject>> queryAccountBalance(AccountInfo accountInfo)throws Exception;

    /**
     * 交易记录查询（当日）  个人与商户
     */
    Response<List<LogOnlinePaymentObject>> queryDayTxn(AccountInfo accountInfo, TxnQueryObj txnQueryObj)throws Exception;


    /**
     * 交易记录查询（历史）  个人与商户
     */
    Response<List<LogOnlinePaymentObject>> queryHisTxn(AccountInfo accountInfo, TxnQueryObj txnQueryObj)throws Exception;


    /**
     * 交易记录查询（查询有无交易成功）   acceptTransSeqNo必填  supplyOrgCode必填
     */
    Response<List<LogOnlinePaymentObject>> queryTxnBySeqNo(AccountInfo accountInfo, TxnQueryObj txnQueryObj)throws Exception;

    /**
     * 充值提现记录（收支明细）
     */
    Response<Paging<TLogAccountPayment>> queryAccountPaymentRecord(AccountInfo accountInfo, TxnQueryObj txnQueryObj);


    /**
     * 收支明细
     */
    Response<Paging<TLogAccountPayment>> queryPaymentDetails(AccountInfo accountInfo, TxnQueryObj txnQueryObj);


}
