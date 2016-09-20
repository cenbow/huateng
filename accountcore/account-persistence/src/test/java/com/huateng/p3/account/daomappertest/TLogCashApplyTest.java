package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogCashApplyMapper;
import com.huateng.p3.account.persistence.models.TLogCashApply;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogCashApplyTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogCashApplyTest.class);

    @Autowired
    private TLogCashApplyMapper logCashApplyMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogCashApply logCashApply = new TLogCashApply();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        logCashApply.setTxnDate(s);
        logCashApply.setTxnSeqNo(s);
        logCashApply.setTxnType(s);
        logCashApply.setTxnDscpt(s);
        logCashApply.setTxnTime(date);
        logCashApply.setEndTime(date);
        logCashApply.setCustomerNo(s);
        logCashApply.setFundAccountNo(s);
        logCashApply.setBankName(s);
        logCashApply.setBranchBankName(s);
        logCashApply.setProvCode(s);
        logCashApply.setCityCode(s);
        logCashApply.setRealName(s);
        logCashApply.setBankAccountNo(s);
        logCashApply.setTxnChannel(s);
        logCashApply.setAcceptOrgCode(s);
        logCashApply.setAcceptOrgType(s);
        logCashApply.setAcceptSeqNo(s);
        logCashApply.setAcceptDate(s);
        logCashApply.setAcceptTime(s);
        logCashApply.setDrawAmt(l);
        logCashApply.setStatus(s);
        logCashApply.setRemark(s);
        logCashApply.setResvFld1(s);
        logCashApply.setResvFld2(s);
        logCashApply.setPayOrgCode(s);
        logCashApply.setSupplyOrgCode(s);
        logCashApply.setFeeAmt(l);
        logCashApply.setFailFlag(s);
        logCashApply.setFailTime(date);
        int i = logCashApplyMapper.insert(logCashApply);
        System.out.print("----------------------------插入TLogCashApply返回码:--:" + i);
    }

    @Test
    public void testselectByTxnseqNo(){
        String txnSeqNo = "a";
        TLogCashApply  logCashApply = logCashApplyMapper.selectByTxnseqNo(txnSeqNo);
        System.out.println("------------------testselectByTxnseqNo 返回代码 ------------"+logCashApply.getTxnSeqNo());
    }
}
