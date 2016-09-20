package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubRiskCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-9-15
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HubRiskCheckServiceImpl implements HubRiskCheckService {
    @Autowired(required = false)
    private RiskCheckService riskCheckService;

    private static final Logger logger = LoggerFactory.getLogger(HubRiskCheckServiceImpl.class);

    @Override
    public Response<String> accountRiskCheck(PaymentInfo paymentInfo, TInfoAccount account) throws Exception {
        logger.info("accountRiskCheck PaymentInfo:{},TInfoAccount:{}", new Object[]{paymentInfo.toString(),account.toString()});
        return riskCheckService.accountRiskCheck(paymentInfo,account);
    }

    @Override
    public Response<String> accountRiskCheckOut(PaymentInfo paymentInfo, AccountInfo accountInfo) throws Exception {
        logger.info("accountRiskCheckOut PaymentInfo:{},AccountInfo:{}", new Object[]{paymentInfo.toString(),accountInfo.toString()});
        return riskCheckService.accountRiskCheckOut(paymentInfo,accountInfo);
    }

    @Override
    public Response<String> merchantRiskCheck(PaymentInfo paymentInfo, OrgType orgType) throws Exception {
        logger.info("merchantRiskCheck PaymentInfo:{},OrgType:{}", new Object[]{paymentInfo.toString(),orgType.toString()});
        return riskCheckService.merchantRiskCheck(paymentInfo,orgType);
    }

    @Override
    public Response<TRiskCustomerCommonRule> queryRiskCustomerCommonRule(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("queryRiskCustomerCommonRule AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return riskCheckService.queryRiskCustomerCommonRule(accountInfo,managerLog);
    }

    @Override
    public Response<String> setRiskCustomerCommonRule(AccountInfo accountInfo, ManagerLog managerLog, TRiskCustomerCommonRule tRiskCustomerCommonRule) throws Exception {
        logger.info("setRiskCustomerCommonRule AccountInfo:{},ManagerLog:{},TRiskCustomerCommonRule:{}", new Object[]{accountInfo.toString(),managerLog.toString()},tRiskCustomerCommonRule.toString());
        return riskCheckService.setRiskCustomerCommonRule(accountInfo,managerLog,tRiskCustomerCommonRule);
    }
}
