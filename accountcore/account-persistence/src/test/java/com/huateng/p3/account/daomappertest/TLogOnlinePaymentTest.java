package com.huateng.p3.account.daomappertest;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogOnlinePaymentMapper;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;

/**
 * 少一个查询语句findFeeOnlineLogByNormalLog ，以后再加。
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TLogOnlinePaymentTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogOnlinePaymentTest.class);

    @Autowired
    private TLogOnlinePaymentMapper tLogOnlinePaymentMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogOnlinePayment logOnlinePayment = new TLogOnlinePayment();
        String s = "a";
        Date date = new Date();
        Long l = 11L;
        logOnlinePayment.setTransSerialNo(s);
        logOnlinePayment.setSettlementDate(s);
        logOnlinePayment.setTransTime(date);
        logOnlinePayment.setTransMonth(11);
        logOnlinePayment.setExtBusinessType(s);
        logOnlinePayment.setInteriorTransType(s);
        logOnlinePayment.setTransMemo(s);
        logOnlinePayment.setAcceptChannel(s);
        logOnlinePayment.setAcceptOrgCode(s);
        logOnlinePayment.setAcceptOrgType(s);
        logOnlinePayment.setAcceptOrgSerialNo(s);
        logOnlinePayment.setAcceptOrgTransDate(s);
        logOnlinePayment.setAcceptOrgTransTime(s);
        logOnlinePayment.setTransferOrgCode(s);
        logOnlinePayment.setTransferOrgType(s);
        logOnlinePayment.setTransferOrgSerialNo(s);
        logOnlinePayment.setTransferOrgTransDate(s);
        logOnlinePayment.setTransferOrgTransTime(s);
        logOnlinePayment.setPayOrgCode(s);
        logOnlinePayment.setPayOrgType(s);
        logOnlinePayment.setPayOrgSerialNo(s);
        logOnlinePayment.setPayOrgTransDate(s);
        logOnlinePayment.setPayOrgTransTime(s);
        logOnlinePayment.setSupplyOrgCode(s);
        logOnlinePayment.setSupplyOrgType(s);
        logOnlinePayment.setSupplyOrgSerialNo(s);
        logOnlinePayment.setSupplyOrgTransDate(s);
        logOnlinePayment.setSupplyOrgTransTime(s);
        logOnlinePayment.setTransAmount(l);
        logOnlinePayment.setBeforeTransAmount(l);
        logOnlinePayment.setAfterTransAmount(l);
        logOnlinePayment.setFeeInFlag(s);
        logOnlinePayment.setProfitInFlag(s);
        logOnlinePayment.setFeeAmount(l);
        logOnlinePayment.setProfitAmount(l);
        logOnlinePayment.setpFeeCode(l);
        logOnlinePayment.setpFeeAmount(l);
        logOnlinePayment.setAcceptFeeCode(l);
        logOnlinePayment.setAcceptFeeAmount(l);
        logOnlinePayment.setAcceptFeeFlag(s);
        logOnlinePayment.setTransferFeeCode(l);
        logOnlinePayment.setTransferFeeAmount(l);
        logOnlinePayment.setTransferFeeFlag(s);
        logOnlinePayment.setPayFeeCode(l);
        logOnlinePayment.setPayFeeAmount(l);
        logOnlinePayment.setPayFeeFlag(s);
        logOnlinePayment.setSupplyFeeCode(l);
        logOnlinePayment.setSupplyFeeAmount(l);
        logOnlinePayment.setSupplyFeeFlag(s);
        logOnlinePayment.setCardSerialNo(s);
        logOnlinePayment.setCardCount(l);
        logOnlinePayment.setTransTac(s);
        logOnlinePayment.setTacCheckLabel(s);
        logOnlinePayment.setCardDptAmount(l);
        logOnlinePayment.setCardExpDate(s);
        logOnlinePayment.setBatchFileId(s);
        logOnlinePayment.setUploadFlag(s);
        logOnlinePayment.setUploadPkgNo(s);
        logOnlinePayment.setInnerCardNo(s);
        logOnlinePayment.setOuterCardNo(s);
        logOnlinePayment.setCardMediaType(s);
        logOnlinePayment.setCardBrandType(s);
        logOnlinePayment.setAccountType(s);
        logOnlinePayment.setAccountNo(s);
        logOnlinePayment.setAreaCode(s);
        logOnlinePayment.setCityCode(s);
        logOnlinePayment.setTerminalNo(s);
        logOnlinePayment.setTerminalSerialNo(s);
        logOnlinePayment.setTerminalCommSerialNo(s);
        logOnlinePayment.setTerminalOperNo(s);
        logOnlinePayment.setBatchNo(l);
        logOnlinePayment.setTransSerialType(s);
        logOnlinePayment.setTransAcceptMatchFlag(s);
        logOnlinePayment.setTransTransferMatchFlag(s);
        logOnlinePayment.setTransPayMatchFlag(s);
        logOnlinePayment.setTransSupplyMatchFlag(s);
        logOnlinePayment.setPaymentObjNo(s);
        logOnlinePayment.setPaymentObjType(s);
        logOnlinePayment.setlAcceptOrgSerialNo(s);
        logOnlinePayment.setlAcceptOrgTransDate(s);
        logOnlinePayment.setlAcceptOrgTransTime(s);
        logOnlinePayment.setlTransferOrgSerialNo(s);
        logOnlinePayment.setlTransferOrgTransDate(s);
        logOnlinePayment.setlTransferOrgTransTime(s);
        logOnlinePayment.setlPayTransSerialNo(s);
        logOnlinePayment.setlPayOrgTransDate(s);
        logOnlinePayment.setlPayOrgTransTime(s);
        logOnlinePayment.setlSupplyOrgSerialNo(s);
        logOnlinePayment.setlSupplyOrgTransDate(s);
        logOnlinePayment.setlSupplyOrgTransTime(s);
        logOnlinePayment.setPsamCardNo(s);
        logOnlinePayment.setPreAuthSerialNo(s);
        logOnlinePayment.setAcceptProcFlag(s);
        logOnlinePayment.setTransferProcFlag(s);
        logOnlinePayment.setPayProcFlag(s);
        logOnlinePayment.setSupplyProcFlag(s);
        logOnlinePayment.setAcceptRespCode(s);
        logOnlinePayment.setTransferRespCode(s);
        logOnlinePayment.setPayRespCode(s);
        logOnlinePayment.setSupplyCode(s);
        logOnlinePayment.setTransFinTs(date);
        logOnlinePayment.setTransToTs(date);
        logOnlinePayment.setIsClearing(s);
        logOnlinePayment.setClearingId(s);
        logOnlinePayment.setResendNum(l);
        logOnlinePayment.setSettlementDate(s);
        logOnlinePayment.setSettlementDay(s);
        logOnlinePayment.setRevsalFlag(s);
        logOnlinePayment.setRevsalTxnSerialNo(s);
        logOnlinePayment.setCancelFlag(s);
        logOnlinePayment.setCancelTxnSerialNo(s);
        logOnlinePayment.setlTransSerialNo(s);
        logOnlinePayment.setReturnFlag(s);
        logOnlinePayment.setReturnCount(l);
        logOnlinePayment.setReturnAmount(l);
        logOnlinePayment.setDubiousFlag(s);
        logOnlinePayment.setBatchDate(s);
        logOnlinePayment.setBatchFlag(s);
        logOnlinePayment.setAcceptSettleAmount(l);
        logOnlinePayment.setTransferSettleAmount(l);
        logOnlinePayment.setPaySettleAmount(l);
        logOnlinePayment.setSupplySettleAmount(l);
        logOnlinePayment.setTransferInOrgCode(s);
        logOnlinePayment.setInAccountNo(s);
        logOnlinePayment.setTransferOutOrgCode(s);
        logOnlinePayment.setOutAccountNo(s);
        logOnlinePayment.setResvFld1(s);
        logOnlinePayment.setResvFld2(s);
        logOnlinePayment.setResvFld3(s);
        logOnlinePayment.setResvFld4(s);
        logOnlinePayment.setResvFld5(s);
        logOnlinePayment.setCustomerNo(s);
        logOnlinePayment.setProductNo(s);
        logOnlinePayment.setCustomerAreaCode(s);
        logOnlinePayment.setCustomerCityCode(s);
        int i = tLogOnlinePaymentMapper.insert(logOnlinePayment);
        System.out.print("----------------------------tLogOnlinePaymentMapper:--:" + i);
    }

    /**
    List<TLogOnlinePayment> findOldNormalOnlineLog(Map<String, String> mapparam);

    TLogOnlinePayment findFeeOnlineLogByNormalLog(
            String normalTxnSeqNo);

    String findOrgCurrentDayTxnSum(Map param);
    */
     @Test
    public void testfindOldNormalOnlineLog(){
        String acceptTransSeqNo = "a";
        String acceptTransDate = "a";
        String acceptTransTime = "a";
        String accountNo = "a";
        String acceptOrgCode = "a";
        List<TLogOnlinePayment> list = tLogOnlinePaymentMapper.findOldNormalOnlineLog(acceptTransSeqNo,acceptTransDate,acceptTransTime,accountNo, acceptOrgCode);
       System.out.println("-------findOldNormalOnlineLog 返回结果------------"+list.size());
    }
    @Test
    public void testfindOrgCurrentDayTxnSum(){

        String accountNo = "a";
        String supplyOrgCode = "a";
        Date txnTime = new Date();
        String txnType = "a";
        String str = tLogOnlinePaymentMapper.findOrgCurrentDayTxnSum(accountNo,supplyOrgCode,txnTime,txnType);
        System.out.print("-------findOrgCurrentDayTxnSum 返回结果------------"+str);
    }

    @Test
    public void testfindFeeOnlineLogByNormalLog(){
        String normalTxnSeqNo = "a";
        TLogOnlinePayment logOnlinePayment = tLogOnlinePaymentMapper.findFeeOnlineLogByNormalLog(normalTxnSeqNo);
        System.out.print("-------findFeeOnlineLogByNormalLog 返回结果------------"+logOnlinePayment.getAcceptFeeAmount()+ logOnlinePayment.getAccountNo());
    }
}
