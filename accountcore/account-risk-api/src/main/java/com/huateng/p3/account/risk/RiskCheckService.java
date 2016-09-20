package com.huateng.p3.account.risk;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-5
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public interface RiskCheckService {

    /**
     * 账户风控检查(内部版本)
     *
     * @param paymentInfo
     * @param account
     * @return
     */
    Response<String> accountRiskCheck(PaymentInfo paymentInfo,  TInfoAccount account);

    /**
     * 账户风控检查(外部版本)
     *
     * @param paymentInfo 
     * @param accountInfo 
     * @return
     */
    Response<String> accountRiskCheckOut(PaymentInfo paymentInfo,  AccountInfo accountInfo);

    
    /**
     * 商户交易风控
     *
     * @param paymentInfo
     * @param orgType
     * @return
     */
    Response<String> merchantRiskCheck(PaymentInfo paymentInfo, OrgType orgType);


    /**
     * 查询个人交易风控规则
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<TRiskCustomerCommonRule> queryRiskCustomerCommonRule(AccountInfo accountInfo, ManagerLog managerLog);
    
    /**
     *  设置个人交易风控 规则
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String>setRiskCustomerCommonRule(AccountInfo accountInfo, ManagerLog managerLog,TRiskCustomerCommonRule tRiskCustomerCommonRule);

}
