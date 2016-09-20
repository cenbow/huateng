package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogDubiousTxnMapper;
import com.huateng.p3.account.persistence.models.TLogDubiousTxn;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogDubiousTxnTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogDubiousTxnTest.class);

    @Autowired
    private TLogDubiousTxnMapper tLogDubiousTxnMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogDubiousTxn logDubiousTxn = new TLogDubiousTxn();
        Date date = new Date();
        String s = "a";
        Long l = 11L;

        logDubiousTxn.setTxnSeqNo(s);
        logDubiousTxn.setSettleDate(s);
        logDubiousTxn.setTxnTime(date);
        logDubiousTxn.setTxnMonth(11);
        logDubiousTxn.setBusinessType(s);
        logDubiousTxn.setTxnType(s);
        logDubiousTxn.setTxnDscpt(s);
        logDubiousTxn.setTxnChannel(s);
        logDubiousTxn.setAcceptOrgCode(s);
        logDubiousTxn.setAcceptOrgType(s);
        logDubiousTxn.setAcceptTransSeqNo(s);
        logDubiousTxn.setAcceptTransDate(s);
        logDubiousTxn.setAcceptTransTime(s);
        logDubiousTxn.setTransferOrgCode(s);
        logDubiousTxn.setTransferOrgType(s);
        logDubiousTxn.setTransferTransSeqNo(s);
        logDubiousTxn.setTransferTransDate(s);
        logDubiousTxn.setTransferTransTime(s);
        logDubiousTxn.setPayOrgCode(s);
        logDubiousTxn.setPayOrgType(s);
        logDubiousTxn.setPayTransSeqNo(s);
        logDubiousTxn.setPayTransDate(s);
        logDubiousTxn.setPayTransTime(s);
        logDubiousTxn.setSupplyOrgCode(s);
        logDubiousTxn.setSupplyOrgType(s);
        logDubiousTxn.setSupplyTransSeqNo(s);
        logDubiousTxn.setSupplyTransDate(s);
        logDubiousTxn.setSupplyTransTime(s);
        logDubiousTxn.setTxnAmt(l);
        logDubiousTxn.setBeforeAmt(l);
        logDubiousTxn.setAfterAmt(l);
        logDubiousTxn.setFeeInFlag(s);
        logDubiousTxn.setProfitInFlag(s);
        logDubiousTxn.setFeeAmt(l);
        logDubiousTxn.setProfitAmt(l);
        logDubiousTxn.setpFeeCode(l);
        logDubiousTxn.setpFeeAmt(l);
        logDubiousTxn.setAcceptFeeCode(l);
        logDubiousTxn.setAcceptFeeAmt(l);
        logDubiousTxn.setAcceptFeeFlag(s);
        logDubiousTxn.setTransferFeeCode(l);
        logDubiousTxn.setTransferFeeAmt(l);
        logDubiousTxn.setTransferFeeFlag(s);
        logDubiousTxn.setPayFeeCode(l);
        logDubiousTxn.setPayFeeAmt(l);
        logDubiousTxn.setPayFeeFlag(s);
        logDubiousTxn.setSupplyFeeCode(l);
        logDubiousTxn.setSupplyFeeAmt(l);
        logDubiousTxn.setSupplyFeeFlag(s);
        logDubiousTxn.setCardSn(s);
        logDubiousTxn.setCardCnt(l);
        logDubiousTxn.setTxnTac(s);
        logDubiousTxn.setTacCheckLabel(s);
        logDubiousTxn.setCardDptAmt(l);
        logDubiousTxn.setCardExpDate(s);
        logDubiousTxn.setBatchFileId(s);
        logDubiousTxn.setUploadFlag(s);
        logDubiousTxn.setUploadPkgNo(s);
        logDubiousTxn.setInnerCardNo(s);
        logDubiousTxn.setOuterCardNo(s);
        logDubiousTxn.setCardMediaType(s);
        logDubiousTxn.setCardBrandType(s);
        logDubiousTxn.setAccountType(s);
        logDubiousTxn.setAccountNo(s);
        logDubiousTxn.setAreaCode(s);
        logDubiousTxn.setCityCode(s);
        logDubiousTxn.setTerminalNo(s);
        logDubiousTxn.setTerminalSeqNo(s);
        logDubiousTxn.setTerminalCommSeqNo(s);
        logDubiousTxn.setTerminalOperNo(s);
        logDubiousTxn.setBatchNo(s);
        logDubiousTxn.setTransSeqType(s);
        logDubiousTxn.setAcceptMatchFlag(s);
        logDubiousTxn.setTransferMatchFlag(s);
        logDubiousTxn.setPayMatchFlag(s);
        logDubiousTxn.setSupplyMatchFlag(s);
        logDubiousTxn.setPaymentObjNo(s);
        logDubiousTxn.setPaymentObjType(s);
        logDubiousTxn.setlAcceptTransSeqNo(s);
        logDubiousTxn.setlAcceptTransDate(s);
        logDubiousTxn.setlAcceptTransTime(s);
        logDubiousTxn.setlTransferTransSeqNo(s);
        logDubiousTxn.setlTransferTransDate(s);
        logDubiousTxn.setlTransferTransTime(s);
        logDubiousTxn.setlPayTransSeqNo(s);
        logDubiousTxn.setlPayTransDate(s);
        logDubiousTxn.setlPayTransTime(s);
        logDubiousTxn.setlSupplyTransSeqNo(s);
        logDubiousTxn.setlSupplyTransDate(s);
        logDubiousTxn.setlSupplyTransTime(s);
        logDubiousTxn.setPsamCardNo(s);
        logDubiousTxn.setPreAuthSeqNo(s);
        logDubiousTxn.setAcceptProcFlag(s);
        logDubiousTxn.setTransferProcFlag(s);
        logDubiousTxn.setPayProcFlag(s);
        logDubiousTxn.setSupplyProcFlag(s);
        logDubiousTxn.setAcceptRespCode(s);
        logDubiousTxn.setTransferRespCode(s);
        logDubiousTxn.setPayRespCode(s);
        logDubiousTxn.setSupplyCode(s);
        logDubiousTxn.setTxnFinTs(date);
        logDubiousTxn.setTxnToTs(date);
        logDubiousTxn.setIsClearing(s);
        logDubiousTxn.setClearingId(s);
        logDubiousTxn.setResendNum(l);
        logDubiousTxn.setSettleMonth(s);
        logDubiousTxn.setSettleDay(s);
        logDubiousTxn.setRevsalFlag(s);
        logDubiousTxn.setRevsalTxnSeqNo(s);
        logDubiousTxn.setCancelFlag(s);
        logDubiousTxn.setCancelTxnSeqNo(s);
        logDubiousTxn.setlTxnSeqNo(s);
        logDubiousTxn.setReturnFlag(s);
        logDubiousTxn.setReturnCount(l);
        logDubiousTxn.setReturnAmt(l);
        logDubiousTxn.setDubiousFlag(s);
        logDubiousTxn.setBatchDate(s);
        logDubiousTxn.setBatchFlag(s);
        logDubiousTxn.setAcceptSettleAmt(l);
        logDubiousTxn.setTransferSettleAmt(l);
        logDubiousTxn.setPaySettleAmt(l);
        logDubiousTxn.setSupplySettleAmt(l);
        logDubiousTxn.setTransferInOrgCode(s);
        logDubiousTxn.setInAccountNo(s);
        logDubiousTxn.setTransferOutOrgCode(s);
        logDubiousTxn.setOutAccountNo(s);
        logDubiousTxn.setResvFld1(s);
        logDubiousTxn.setResvFld2(s);
        logDubiousTxn.setResvFld3(s);
        logDubiousTxn.setResvFld4(s);
        logDubiousTxn.setResvFld5(s);
        logDubiousTxn.setErrorCode(s);
        logDubiousTxn.setRemark(s);
        int i = tLogDubiousTxnMapper.insert(logDubiousTxn);
        System.out.print("----------------------------插入TLogDubiousTxn返回码:--:" + i);
    }

}
