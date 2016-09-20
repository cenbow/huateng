package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:40
 */
public interface HubRiskCheckService {
    /**
     * 账户风控检查(内部版本)
     *
     * @param paymentInfo
     * @param account
     * @return
     */
    Response<String> accountRiskCheck(PaymentInfo paymentInfo,  TInfoAccount account)throws Exception;

    /**
     * 账户风控检查(外部版本)
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    Response<String> accountRiskCheckOut(PaymentInfo paymentInfo,  AccountInfo accountInfo)throws Exception;


    /**
     * 商户交易风控
     *
     * @param paymentInfo
     * @param orgType
     * @return
     */
    Response<String> merchantRiskCheck(PaymentInfo paymentInfo, OrgType orgType)throws Exception;


    /**
     * 查询个人交易风控规则
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<TRiskCustomerCommonRule> queryRiskCustomerCommonRule(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;

    /**
     *  设置个人交易风控 规则
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String>setRiskCustomerCommonRule(AccountInfo accountInfo, ManagerLog managerLog,TRiskCustomerCommonRule tRiskCustomerCommonRule)throws Exception;

}
