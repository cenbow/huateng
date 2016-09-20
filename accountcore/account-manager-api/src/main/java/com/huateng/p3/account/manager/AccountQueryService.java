package com.huateng.p3.account.manager;

import java.util.List;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.AccountResultObject;
import com.huateng.p3.account.common.bizparammodel.LogOnlinePaymentObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.TxnQueryObj;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.persistence.models.TInfoSubaccount;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public interface AccountQueryService {

    
    /**
	 * 账户实名请求记录查询
	 * 
	 * @param accountInfo
	 * @param managerLog
	 */
	Response<List<TRealnameApply>> queryRealnameAuthenticationStatusDetails(AccountInfo accountInfo, ManagerLog managerLog);
	
    /**
     * 子账户查询
     * @param accountInfo
     * @return
     */
    Response<List<TInfoSubaccount>> querysubaccount(AccountInfo accountInfo);
    
    
    /**
     * 账户余额查询
     * @param accountInfo
     * @return
     */
    Response<List<AccountResultObject>> queryAccountBalance(AccountInfo accountInfo);

    /**
     * 交易记录查询（当日）  个人与商户
     * 
     */
    Response<List<LogOnlinePaymentObject>> queryDayTxn(AccountInfo accountInfo, TxnQueryObj txnQueryObj);
    

    /**
     * 交易记录查询（历史）  个人与商户
     * 
     */
    Response<List<LogOnlinePaymentObject>> queryHisTxn(AccountInfo accountInfo, TxnQueryObj txnQueryObj);
    
    
    /**
     * 交易记录查询（查询有无交易成功）   acceptTransSeqNo必填  supplyOrgCode必填
     * 
     */
    Response<List<LogOnlinePaymentObject>> queryTxnBySeqNo(AccountInfo accountInfo, TxnQueryObj txnQueryObj);

    /**
     * 账户支付查询
     * @param accountInfo
     * @param txnQueryObj
     * @return
     */
    public Response<Paging<TLogAccountPayment>> queryAccountPaymentRecord(AccountInfo accountInfo, TxnQueryObj txnQueryObj);
    
    /**
     * 收支明细查询
     * @param accountInfo
     * @param txnQueryObj
     * @return
     */
    public Response<Paging<TLogAccountPayment>> queryPaymentDetails(AccountInfo accountInfo, TxnQueryObj txnQueryObj);
}