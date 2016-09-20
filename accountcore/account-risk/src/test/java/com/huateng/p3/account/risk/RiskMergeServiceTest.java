package com.huateng.p3.account.risk;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountRiskSpringTest;

import com.huateng.p3.account.common.bizparammodel.RiskCustomerTxnDataMergeInfo;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.CustomerGrade;
import com.huateng.p3.account.common.enummodel.TxnChannel;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.risk.RiskMergeService;


/**
 * User: JamesTang
 * Date: 14-2-12nextval for RISK.S_RISK_TXNRECORD
 * Time: 下午5:10
 */
public class RiskMergeServiceTest extends BaseAccountRiskSpringTest {

    @Autowired
    RiskMergeService riskMergeService;

    @Test
    public void testriskmerge() {
        RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo = new RiskCustomerTxnDataMergeInfo();
        riskCustomerTxnDataMergeInfo.setInnerTxnType("121180");
        riskCustomerTxnDataMergeInfo.setAccountNo("8600019287618218212");
        riskCustomerTxnDataMergeInfo.setAccountType(AccountType.FUND);
        riskCustomerTxnDataMergeInfo.setCustomerGrade(CustomerGrade.CUSTOMER_ACCOUNT_GRADE_GENERAL.getCustomerGradeCode());
        riskCustomerTxnDataMergeInfo.setTxnAmount(1200L);
        riskCustomerTxnDataMergeInfo.setTxnChannel(TxnChannel.TXN_CHANNEL_PORTAL.getTxnCode());
        riskCustomerTxnDataMergeInfo.setTxnType(TxnType.TXN_CHARGE);
        //riskCustomerTxnDataMergeInfo.setTxnDate(DateTime.now().toString("YYYYMMdd"));
        riskCustomerTxnDataMergeInfo.setTxnDate("20140402");
        //  riskCustomerTxnDataMergeInfo.setTxnMonth(DateTime.now().toString("YYYYMM"));
        riskCustomerTxnDataMergeInfo.setTxnMonth("201404");
        riskMergeService.accountRiskMerge(riskCustomerTxnDataMergeInfo);


    }
}
