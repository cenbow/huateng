package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogAccountCardTxnsumMapper;
import com.huateng.p3.account.persistence.models.TLogAccountCardTxnsum;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogAccountCardTxnsumTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogAccountCardTxnsumTest.class);

    @Autowired
    private TLogAccountCardTxnsumMapper tLogAccountCardTxnsumMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogAccountCardTxnsum tLogAccountCardTxnsum = new TLogAccountCardTxnsum();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        int num = 1;

        tLogAccountCardTxnsum.setType(s);
        tLogAccountCardTxnsum.setTxnChannel(s);
        tLogAccountCardTxnsum.setAccountId(s);
        tLogAccountCardTxnsum.setLastTxnTime(date);
        tLogAccountCardTxnsum.setLastTxnDate(s);
        tLogAccountCardTxnsum.setDaySumConsAmt(l);
        tLogAccountCardTxnsum.setDaySumConsCnt(num);
        tLogAccountCardTxnsum.setDaySumOutAmt(l);
        tLogAccountCardTxnsum.setDaySumOutCnt(num);
        tLogAccountCardTxnsum.setDaySumInAmt(l);
        tLogAccountCardTxnsum.setDaySumInCnt(num);
        tLogAccountCardTxnsum.setDaySumCashAmt(l);
        tLogAccountCardTxnsum.setDaySumCashCnt(num);
        tLogAccountCardTxnsum.setDaySumChgAmt(l);
        tLogAccountCardTxnsum.setDaySumChgCnt(num);
        tLogAccountCardTxnsum.setMonthSumConsAmt(l);
        tLogAccountCardTxnsum.setMonthSumOutAmt(l);
        tLogAccountCardTxnsum.setMonthSumInAmt(l);
        tLogAccountCardTxnsum.setMonthSumCashAmt(l);
        tLogAccountCardTxnsum.setMonthSumChgAmt(l);
        tLogAccountCardTxnsum.setMonthSumConsCnt(l);
        tLogAccountCardTxnsum.setMonthSumOutCnt(l);
        tLogAccountCardTxnsum.setMonthSumInCnt(l);
        tLogAccountCardTxnsum.setMonthSumCashCnt(l);
        tLogAccountCardTxnsum.setMonthSumChgCnt(l);
        tLogAccountCardTxnsum.setRsvdAmt1(l);
        tLogAccountCardTxnsum.setRsvdAmt2(l);
        tLogAccountCardTxnsum.setRsvdText1(s);
        tLogAccountCardTxnsum.setRsvdText2(s);

        int i = tLogAccountCardTxnsumMapper.insert(tLogAccountCardTxnsum);
        System.out.print("----------------------------插入TLogAccountCardTxnsum返回码:--:" + i);
    }

    /**
     * 感觉sql语句有问题，等下问密力
     * TLogAccountCardTxnsum findToCheck(Map para);
     */
    @Test
    public void testfindToCheck(){
        String s = "a";
        String txnChannel = s;
        String type = s;
        String accountId = s;
        TLogAccountCardTxnsum t = tLogAccountCardTxnsumMapper.findToCheck(txnChannel,type,accountId);
        System.out.println("--------TLogAccountCardTxnsum 返回的 ---------------"+t.getDaySumCashAmt());
    }
}
