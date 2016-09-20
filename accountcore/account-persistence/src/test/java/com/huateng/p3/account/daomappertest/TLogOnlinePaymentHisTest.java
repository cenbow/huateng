package com.huateng.p3.account.daomappertest;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogOnlinePaymentHisMapper;
import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;

/**
 * 少一个查询语句findFeeOnlineLogByNormalLog ，以后再加。
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogOnlinePaymentHisTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogOnlinePaymentHisTest.class);

    @Autowired
    private TLogOnlinePaymentHisMapper tLogOnlinePaymentHisMapper;

    

    /**
     int updateHisReturnFlagByTablename(Map para);
     TLogOnlinePaymentHis findOnlineLogHisByTablename(Map param);
     TLogOnlinePaymentHis findOnlineLogHisByTablenameAndTxnno(Map param);
     String findOrgMonthTxnSum(Map param);
     TLogOnlinePaymentHis findOnlineLogHisByTablenameinlclueNoClean(Map param);
    */
    @Test
    public void testupdateHisReturnFlagByTablename(){
        String returnFlag = "a";
        Long returnCount = 22L;
        Long returnAmt = 22L;
        String txnSeqNo = "a";
        int i = tLogOnlinePaymentHisMapper.updateHisReturnFlagByTablename(returnFlag, returnCount, returnAmt, txnSeqNo , "t_log_online_payment_his");
        System.out.println("-------------testupdateHisReturnFlagByTablename 返回结果--------------------" + i);
    }
    @Test
    public void testfindOnlineLogHisByTablename(){
        String acceptTransSeqNo = "a";
        String acceptTransDate = "a";
        String acceptTransTime = "a";
        String accountNo = "a";
        String terminalSeqNo = "a";
        String acceptOrgCode = "a";
        List<TLogOnlinePaymentHis> logOnlinePaymentHis= tLogOnlinePaymentHisMapper.findOnlineLogHisByTablename(acceptTransSeqNo,acceptTransDate,acceptTransTime,accountNo,terminalSeqNo, acceptOrgCode, "t_log_online_payment_his");
       System.out.println("-------testfindOnlineLogHisByTablename 返回结果------------" + logOnlinePaymentHis.get(0));
    }

    @Test
    public void testfindOnlineLogHisByTablenameAndTxnno(){
        String txnSeqNo = "a";
        List<TLogOnlinePaymentHis> logOnlinePaymentHis=  tLogOnlinePaymentHisMapper.findOnlineLogHisByTablenameAndTxnno(txnSeqNo, "t_log_online_payment_his");
        System.out.println("-------testfindOnlineLogHisByTablenameAndTxnno 返回结果------------" + logOnlinePaymentHis.get(0));
    }

    @Test
    public void testfindOnlineLogHisByTablenameinlclueNoClean(){
        String acceptTransSeqNo = "a";
        String acceptTransDate = "a";
        String acceptTransTime = "a";
        String accountNo = "a";
        String terminalSeqNo = "a";
        String acceptOrgCode = "a";
        List<TLogOnlinePaymentHis> logOnlinePaymentHis= tLogOnlinePaymentHisMapper.findOnlineLogHisByTablenameinlclueNoClean(acceptTransSeqNo,acceptTransDate,acceptTransTime,accountNo,terminalSeqNo, acceptOrgCode, "t_log_online_payment_his");
        System.out.println("-------testfindOnlineLogHisByTablename 返回结果------------" + logOnlinePaymentHis.get(0));
    }
}
