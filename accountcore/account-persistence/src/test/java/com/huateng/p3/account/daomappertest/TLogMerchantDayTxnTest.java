package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogMerchantDayTxnMapper;
import com.huateng.p3.account.persistence.models.TLogMerchantDayTxn;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogMerchantDayTxnTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogMerchantDayTxnTest.class);

    @Autowired
    private TLogMerchantDayTxnMapper tLogMerchantDayTxnMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogMerchantDayTxn logMerchantDayTxn = new TLogMerchantDayTxn();
        Date date = new Date();
        String s = "a";
        long l = 11L;
        logMerchantDayTxn.setTxnChannel(s);
        logMerchantDayTxn.setLastTxnDate(s);
        logMerchantDayTxn.setSumTxnAmt(l);
        logMerchantDayTxn.setBusinessType(s);
        logMerchantDayTxn.setMerchantCode(s);
        int i = tLogMerchantDayTxnMapper.insert(logMerchantDayTxn);
        System.out.print("----------------------------插入TLogMerchantDayTxn返回码:--:" + i);
    }
    /**
    int updateMerchantDayTxnAmt(Map paramap);

    int updateMerchantDayTxnAmtChgDate(Map paraMap);
     */
    @Test
    public void testupdateMerchantDayTxnAmt(){
        String s = "a";
       String lastTxnDate = s;
       Long txnAmt = 21L;
        String merchantCode = s;
        String txnChannel = s;
        String businessType =s;
       int i = tLogMerchantDayTxnMapper.updateMerchantDayTxnAmt(lastTxnDate,txnAmt,merchantCode,txnChannel,businessType);
       System.out.println("--------------------updateMerchantDayTxnAmt 返回内容 ："+i);
    }

    @Test
    public void testupdateMerchantDayTxnAmtChgDate(){
        String s = "a";
        String lastTxnDate = s;
        Long txnAmt = 11L;
        String merchantCode = s;
        String txnChannel = s;
        String businessType =s;
        int i = tLogMerchantDayTxnMapper.updateMerchantDayTxnAmtChgDate(lastTxnDate,txnAmt,merchantCode,txnChannel,businessType);
        System.out.println("--------------------updateMerchantDayTxnAmtChgDate 返回内容 ："+i);
    }
}
