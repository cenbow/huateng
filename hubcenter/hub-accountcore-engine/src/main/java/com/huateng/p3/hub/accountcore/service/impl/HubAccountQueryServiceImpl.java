package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.*;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.manager.AccountQueryService;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubAccountQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午11:01
 */
@Service
public class HubAccountQueryServiceImpl implements HubAccountQueryService {

    @Autowired(required = false)
    private AccountQueryService accountQueryService;

    private static final Logger logger = LoggerFactory.getLogger(HubAccountQueryServiceImpl.class);

    @Override
    public Response<List<TRealnameApply>> queryRealnameAuthenticationStatusDetails(AccountInfo accountInfo, ManagerLog managerLog) throws Exception{
        logger.info("queryRealnameAuthenticationStatusDetails AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(), managerLog.toString()});
        return accountQueryService.queryRealnameAuthenticationStatusDetails(accountInfo, managerLog);
    }

    @Override
    public Response<List<AccountResultObject>> queryAccountBalance(AccountInfo accountInfo) throws Exception {
        logger.info("queryAccountBalance AccountInfo:{}", new Object[]{accountInfo.toString()});
        return accountQueryService.queryAccountBalance(accountInfo);
    }

    @Override
    public  Response<List<LogOnlinePaymentObject>> queryDayTxn(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
        logger.info("queryDayTxn AccountInfo:{},TxnQueryObj:{}", new Object[]{accountInfo.toString(), txnQueryObj.toString()});
        return accountQueryService.queryDayTxn(accountInfo,txnQueryObj);
    }

    @Override
    public Response<List<LogOnlinePaymentObject>> queryHisTxn(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
        logger.info("queryHisTxn AccountInfo:{},TxnQueryObj:{}", new Object[]{accountInfo.toString(), txnQueryObj.toString()});
        return accountQueryService.queryHisTxn(accountInfo,txnQueryObj);
    }

    @Override
    public Response<List<LogOnlinePaymentObject>> queryTxnBySeqNo(AccountInfo accountInfo, TxnQueryObj txnQueryObj){
        logger.info("queryTxnBySeqNo AccountInfo:{},TxnQueryObj:{}", new Object[]{accountInfo.toString(), txnQueryObj.toString()});
        return accountQueryService.queryTxnBySeqNo(accountInfo, txnQueryObj);
    }

    @Override
    public  Response<Paging<TLogAccountPayment>> queryAccountPaymentRecord(AccountInfo accountInfo, TxnQueryObj txnQueryObj){
        logger.info("queryAccountPaymentRecord AccountInfo:{},TxnQueryObj:{}", new Object[]{accountInfo.toString(), txnQueryObj.toString()});
        return accountQueryService.queryAccountPaymentRecord(accountInfo, txnQueryObj);
    }

    @Override
    public Response<Paging<TLogAccountPayment>> queryPaymentDetails(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
        logger.info("queryPaymentDetails AccountInfo:{},TxnQueryObj:{}", new Object[]{accountInfo.toString(), txnQueryObj.toString()});
        return accountQueryService.queryPaymentDetails(accountInfo, txnQueryObj);
    }
}
