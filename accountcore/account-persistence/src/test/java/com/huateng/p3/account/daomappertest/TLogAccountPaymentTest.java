package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogAccountPaymentMapper;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogAccountPaymentTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogAccountPaymentTest.class);

    @Autowired
    private TLogAccountPaymentMapper logAccountPaymentMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogAccountPayment logAccountPayment = new TLogAccountPayment();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        logAccountPayment.setClearingTxnSeqNo(s);
        logAccountPayment.setTxnLabel(s);
        logAccountPayment.setTxnSeqNo(s);
        logAccountPayment.setTxnType(s);
        logAccountPayment.setTxnDscpt(s);
        logAccountPayment.setTxnTime(date);
        logAccountPayment.setCustomerNo(s);
        logAccountPayment.setCityCode(s);
        logAccountPayment.setTxnChannel(s);
        logAccountPayment.setAcceptOrgCode(s);
        logAccountPayment.setAcceptOrgType(s);
        logAccountPayment.setResvFld1(s);
        logAccountPayment.setResvFld2(s);
        int i = logAccountPaymentMapper.insert(logAccountPayment);
        System.out.print("----------------------------插入TLogAccountPayment返回码:--:" + i);
    }

    /**
     * TLogAccountPayment findByClearingSeqNo(Map paramap);
     */
    @Test
    public void testfindByClearingSeqNo(){
        String s = "a";
        String clearingSeqNo = s;
        String txnLabel = s;
        TLogAccountPayment  logAccountPayment = logAccountPaymentMapper.findByClearingSeqNo(clearingSeqNo,txnLabel);
        System.out.println("------------------testselectByTxnseqNo 返回代码 ------------"+logAccountPayment.getTxnSeqNo());
    }
}
