package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TLogOfflinePaymentMapper;
import com.huateng.p3.account.persistence.models.TLogOfflinePayment;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TLogOfflinePaymentTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TLogOfflinePaymentTest.class);

    @Autowired
    private TLogOfflinePaymentMapper tLogOfflinePaymentMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TLogOfflinePayment tLogOfflinePayment = new TLogOfflinePayment();
        String s = "a";
        Date date = new Date();
        Long l = 11L;
        tLogOfflinePayment.setTxnSeqNo(s);
        tLogOfflinePayment.setSettleDate(s);
        tLogOfflinePayment.setTxnTime(date);
        tLogOfflinePayment.setTxnMonth(11);
        tLogOfflinePayment.setBusinessType(s);
        tLogOfflinePayment.setTxnType(s);
        tLogOfflinePayment.setTxnDscpt(s);
        tLogOfflinePayment.setTxnChannel(s);
        tLogOfflinePayment.setAcceptOrgCode(s);
        tLogOfflinePayment.setAcceptOrgType(s);
        tLogOfflinePayment.setAcceptTransSeqNo(s);
        tLogOfflinePayment.setAcceptTransDate(s);
        tLogOfflinePayment.setAcceptTransTime(s);
        tLogOfflinePayment.setTransferOrgCode(s);
        tLogOfflinePayment.setTransferOrgType(s);
        tLogOfflinePayment.setTransferTransSeqNo(s);
        tLogOfflinePayment.setTransferTransDate(s);
        tLogOfflinePayment.setTransferTransTime(s);
        tLogOfflinePayment.setPayOrgCode(s);
        tLogOfflinePayment.setPayOrgType(s);
        tLogOfflinePayment.setPayTransSeqNo(s);
        tLogOfflinePayment.setPayTransDate(s);
        tLogOfflinePayment.setPayTransTime(s);
        tLogOfflinePayment.setSupplyOrgCode(s);
        tLogOfflinePayment.setSupplyOrgType(s);
        tLogOfflinePayment.setSupplyTransSeqNo(s);
        tLogOfflinePayment.setSupplyTransDate(s);
        tLogOfflinePayment.setSupplyTransTime(s);
        tLogOfflinePayment.setTxnAmt(l);
        tLogOfflinePayment.setBeforeAmt(l);
        tLogOfflinePayment.setAfterAmt(l);
        tLogOfflinePayment.setFeeInFlag(s);
        tLogOfflinePayment.setProfitInFlag(s);
        tLogOfflinePayment.setFeeAmt(l);
        tLogOfflinePayment.setProfitAmt(l);
        tLogOfflinePayment.setpFeeCode(l);
        tLogOfflinePayment.setpFeeAmt(l);
        tLogOfflinePayment.setAcceptFeeCode(l);
        tLogOfflinePayment.setAcceptFeeAmt(l);
        tLogOfflinePayment.setAcceptFeeFlag(s);
        tLogOfflinePayment.setTransferFeeCode(l);
        tLogOfflinePayment.setTransferFeeAmt(l);
        tLogOfflinePayment.setTransferFeeFlag(s);
        tLogOfflinePayment.setPayFeeCode(l);
        tLogOfflinePayment.setPayFeeAmt(l);
        tLogOfflinePayment.setPayFeeFlag(s);
        tLogOfflinePayment.setSupplyFeeCode(l);
        tLogOfflinePayment.setSupplyFeeAmt(l);
        tLogOfflinePayment.setSupplyFeeFlag(s);
        tLogOfflinePayment.setCardSn(s);
        tLogOfflinePayment.setCardCnt(l);
        tLogOfflinePayment.setTxnTac(s);
        tLogOfflinePayment.setTacCheckLabel(s);
        tLogOfflinePayment.setCardDptAmt(l);
        tLogOfflinePayment.setCardExpDate(s);
        tLogOfflinePayment.setBatchFileId(s);
        tLogOfflinePayment.setUploadFlag(s);
        tLogOfflinePayment.setUploadPkgNo(s);
        tLogOfflinePayment.setInnerCardNo(s);
        tLogOfflinePayment.setOuterCardNo(s);
        tLogOfflinePayment.setCardMediaType(s);
        tLogOfflinePayment.setCardBrandType(s);
        tLogOfflinePayment.setAccountType(s);
        tLogOfflinePayment.setAccountNo(s);
        tLogOfflinePayment.setAreaCode(s);
        tLogOfflinePayment.setCityCode(s);
        tLogOfflinePayment.setTerminalNo(s);
        tLogOfflinePayment.setTerminalSeqNo(s);
        tLogOfflinePayment.setTerminalCommSeqNo(s);
        tLogOfflinePayment.setTerminalOperNo(s);
        tLogOfflinePayment.setBatchNo(l);
        tLogOfflinePayment.setTransSeqType(s);
        tLogOfflinePayment.setAcceptMatchFlag(s);
        tLogOfflinePayment.setTransferMatchFlag(s);
        tLogOfflinePayment.setPayMatchFlag(s);
        tLogOfflinePayment.setSupplyMatchFlag(s);
        tLogOfflinePayment.setPaymentObjNo(s);
        tLogOfflinePayment.setPaymentObjType(s);
        tLogOfflinePayment.setlAcceptTransSeqNo(s);
        tLogOfflinePayment.setlAcceptTransDate(s);
        tLogOfflinePayment.setlAcceptTransTime(s);
        tLogOfflinePayment.setlTransferTransSeqNo(s);
        tLogOfflinePayment.setlTransferTransDate(s);
        tLogOfflinePayment.setlTransferTransTime(s);
        tLogOfflinePayment.setlPayTransSeqNo(s);
        tLogOfflinePayment.setlPayTransDate(s);
        tLogOfflinePayment.setlPayTransTime(s);
        tLogOfflinePayment.setlSupplyTransSeqNo(s);
        tLogOfflinePayment.setlSupplyTransDate(s);
        tLogOfflinePayment.setlSupplyTransTime(s);
        tLogOfflinePayment.setPsamCardNo(s);
        tLogOfflinePayment.setPreAuthSeqNo(s);
        tLogOfflinePayment.setAcceptProcFlag(s);
        tLogOfflinePayment.setTransferProcFlag(s);
        tLogOfflinePayment.setPayProcFlag(s);
        tLogOfflinePayment.setSupplyProcFlag(s);
        tLogOfflinePayment.setAcceptRespCode(s);
        tLogOfflinePayment.setTransferRespCode(s);
        tLogOfflinePayment.setPayRespCode(s);
        tLogOfflinePayment.setSupplyCode(s);
        tLogOfflinePayment.setTxnFinTs(date);
        tLogOfflinePayment.setTxnToTs(date);
        tLogOfflinePayment.setIsClearing(s);
        tLogOfflinePayment.setClearingId(s);
        tLogOfflinePayment.setResendNum(l);
        tLogOfflinePayment.setSettleDate(s);
        tLogOfflinePayment.setSettleDay(s);
        tLogOfflinePayment.setRevsalFlag(s);
        tLogOfflinePayment.setRevsalTxnSeqNo(s);
        tLogOfflinePayment.setCancelFlag(s);
        tLogOfflinePayment.setCancelTxnSeqNo(s);
        tLogOfflinePayment.setlTxnSeqNo(s);
        tLogOfflinePayment.setReturnFlag(s);
        tLogOfflinePayment.setReturnCount(l);
        tLogOfflinePayment.setReturnAmt(l);
        tLogOfflinePayment.setDubiousFlag(s);
        tLogOfflinePayment.setBatchDate(s);
        tLogOfflinePayment.setBatchFlag(s);
        tLogOfflinePayment.setAcceptSettleAmt(l);
        tLogOfflinePayment.setTransferSettleAmt(l);
        tLogOfflinePayment.setPaySettleAmt(l);
        tLogOfflinePayment.setSupplySettleAmt(l);
        tLogOfflinePayment.setTransferInOrgCode(s);
        tLogOfflinePayment.setInAccountNo(s);
        tLogOfflinePayment.setTransferOutOrgCode(s);
        tLogOfflinePayment.setOutAccountNo(s);
        tLogOfflinePayment.setResvFld1(s);
        tLogOfflinePayment.setResvFld2(s);
        tLogOfflinePayment.setResvFld3(s);
        tLogOfflinePayment.setResvFld4(s);
        tLogOfflinePayment.setResvFld5(s);
        tLogOfflinePayment.setCustomerNo(s);
        tLogOfflinePayment.setProductNo(s);
        tLogOfflinePayment.setCustomerAreaCode(s);
        tLogOfflinePayment.setCustomerCityCode(s);
        int i = tLogOfflinePaymentMapper.insert(tLogOfflinePayment);
        System.out.print("----------------------------插入TLogOfflinePayment返回码:--:" + i);
    }


}
