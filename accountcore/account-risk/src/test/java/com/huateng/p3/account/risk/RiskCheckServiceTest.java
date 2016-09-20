package com.huateng.p3.account.risk;


import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountRiskSpringTest;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.CustomerGrade;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.TxnChannel;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;
import com.huateng.p3.account.risk.RiskCheckServiceImpl;
import com.huateng.p3.component.Response;


public class RiskCheckServiceTest extends BaseAccountRiskSpringTest {

    private Logger logger = LoggerFactory
            .getLogger(RiskCheckServiceTest.class);


    @Autowired
    private RiskCheckServiceImpl riskCheckService;

    @Test
    public void testAll() throws Exception {

       // riskCheckdo();
      setRiskCustomerCommonRule();//设置个人风控规则
       // queryRiskCustomerCommonRule();//查询个人风控规则

    }


    private void riskCheckdo() {

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMerchantCode("222222222222222");
        paymentInfo.setTerminalNo("00431000");
        paymentInfo.setAcceptOrgCode("004310000040000");
        //paymentInfo.setAcceptOrgCode("001999999020000");
        paymentInfo.setInnerTxnType("121010");
        paymentInfo.setAmount(1l);
        paymentInfo.setAcceptDate("20131219");
        paymentInfo.setAcceptTime("164649");
        paymentInfo.setAcceptOperatorNo(null);
        paymentInfo.setAcceptTxnSeqNo("2013121800094730");
        paymentInfo.setBussinessType("C4100C");
        paymentInfo.setChannel("02");
        paymentInfo.setPayOrgCode("03");
        paymentInfo.setTerminalSeqNo("131218006900453");
       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        paymentInfo.setTxnType(TxnType.TXN_CONSUME);
        paymentInfo.setTxnDscpt("有密支付");
        paymentInfo.setSupplyOrgCode("111310049001138");
        TInfoAccount account = new TInfoAccount();
//        account.setAccountNo("8631510000005101");
        account.setAccountNo("8630596000000109");
        account.setStatus("1");
        account.setType(AccountType.FUND.getValue());
        account.setGrade(CustomerGrade.CUSTOMER_ACCOUNT_GRADE_GENERAL.getCustomerGradeCode());
        Response<String>  response=   riskCheckService.accountRiskCheck(paymentInfo, account);
        logger.debug("risk check result is :"+response);
        Assert.assertEquals(true, response.isSuccess());
    }


    public void queryRiskCustomerCommonRule() throws Exception {

        ManagerLog managerLog = new ManagerLog();
        managerLog.setTxnChannel(TxnChannel.TXN_CHANNEL_PORTAL.getTxnCode());
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("18018354882");
        accountInfo.setKeyType(KeyType.UNIFY);
        Response<TRiskCustomerCommonRule> actual = riskCheckService.queryRiskCustomerCommonRule(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());

    }

    public void setRiskCustomerCommonRule() throws Exception {
        ManagerLog managerLog = new ManagerLog();
        managerLog.setTxnChannel(TxnChannel.TXN_CHANNEL_PORTAL.getTxnCode());
        managerLog.setInputTime(DateTime.now().toDate());
        managerLog.setInputUid("xujiaqi");
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124299");
        accountInfo.setKeyType(KeyType.UNIFY);
        TRiskCustomerCommonRule tRiskCustomerCommonRule = new TRiskCustomerCommonRule();
        //tRiskCustomerCommonRule.setConsumeTransMaxAmt(200l);
        tRiskCustomerCommonRule.setConsumeTransMaxAmt(200l);
        tRiskCustomerCommonRule.setConsumeTransMaxAmtSum(400l);
        tRiskCustomerCommonRule.setMonthMaxConsAmt(1000l);
        Response<String> actual = riskCheckService.setRiskCustomerCommonRule(accountInfo, managerLog, tRiskCustomerCommonRule);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());

    }


}

