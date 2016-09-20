package com.huateng.p3.account.inner;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.commonservice.RiskService;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-27
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */
public class RiskServiceTest extends BaseAccountServiceSpringTest{

    @Autowired
    private RiskService riskService;

    @Test
    public void cutomerTxnDataSend() {
        String s = "21133";
        Long l = 11L;
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMerchantCode(s);
        paymentInfo.setSupplyOrgCode(s);
        paymentInfo.setChannel(s);
        paymentInfo.setBussinessType(s);
        paymentInfo.setInnerTxnType(s);
        paymentInfo.setAmount(l);
        paymentInfo.setTerminalNo(s);
        paymentInfo.setTerminalSeqNo(s);
        paymentInfo.setAcceptOrgCode(s);
        paymentInfo.setAcceptTxnSeqNo(s);
        paymentInfo.setAcceptOperatorNo(s);
        paymentInfo.setAcceptDate(s);
        paymentInfo.setAcceptTime(s);
        paymentInfo.setPayOrgCode(s);
        paymentInfo.setTxnType(TxnType.TXN_CONSUME);
        paymentInfo.setTxnDscpt(s);
        String accountNo = "8631898000000323";
        AccountType accountType =  AccountType.INTEGRAL;
        String customerGrade = "0";
        riskService.cutomerTxnDataSend(paymentInfo,accountNo,accountType,customerGrade, null);
    }

    //@Test
    public void OrgTxnDataSend() {
        String s = "s";
        Long l = 11L;
        Date date = new Date();
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMerchantCode(s);
        paymentInfo.setSupplyOrgCode(s);
        paymentInfo.setChannel(s);
        paymentInfo.setBussinessType(s);
        paymentInfo.setInnerTxnType(s);
        paymentInfo.setAmount(l);
        paymentInfo.setTerminalNo(s);
        paymentInfo.setTerminalSeqNo(s);
        paymentInfo.setAcceptOrgCode(s);
        paymentInfo.setAcceptTxnSeqNo(s);
        paymentInfo.setAcceptOperatorNo(s);
        paymentInfo.setAcceptDate(s);
        paymentInfo.setAcceptTime(s);
        paymentInfo.setPayOrgCode(s);
        paymentInfo.setTxnType(TxnType.TXN_CONSUME);
        paymentInfo.setTxnDscpt(s);
        OrgType orgType = OrgType.ORG_TYPE_MERCHANT;

        riskService.OrgTxnDataSend(paymentInfo,orgType);
    }
}
