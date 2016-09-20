package com.huateng.p3.account.inner;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.CustomerStatus;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-30
 * Time: 上午11:43
 * To change this template use File | Settings | File Templates.
 */
public class TxnCheckServiceTest extends BaseAccountServiceSpringTest{

    @Autowired
    private TxnCheckService txnCheckService;

    @Test
    public void checkCustomerPaymentTxn(){
        String s = "s";
        Long l = 11L;
        Date date = new Date();
        TInfoCustomer tInfoCustomer = new TInfoCustomer();
        tInfoCustomer.setCustomerNo(s);
        tInfoCustomer.setUnifyId(s);
        tInfoCustomer.setIsRealname(s);
        tInfoCustomer.setName(s);
        tInfoCustomer.setIsRequestCertificate(s);
        tInfoCustomer.setRegisterType(s);
        tInfoCustomer.setMobileNo(s);
        tInfoCustomer.setEmailAddress(s);
        tInfoCustomer.setUserName(s);
        tInfoCustomer.setNickName(s);
        tInfoCustomer.setWebLoginPassword(s);
        tInfoCustomer.setIvrPassword(s);
        tInfoCustomer.setAccountQueryPassword(s);
        tInfoCustomer.setGender(s);
        tInfoCustomer.setIdentifyType(s);
        tInfoCustomer.setIdentifyNo(s);
        tInfoCustomer.setHomeTelephone(s);
        tInfoCustomer.setOfficeTelephone(s);
        tInfoCustomer.setOtherTelephone(s);
        tInfoCustomer.setApanage(s);
        tInfoCustomer.setAreaCode(s);
        tInfoCustomer.setCityCode(s);
        tInfoCustomer.setContactAddress(s);
        tInfoCustomer.setContactZipcode(s);
        tInfoCustomer.setOrganizationAddress(s);
        tInfoCustomer.setOrganizationZipcode(s);
        tInfoCustomer.setPwdErrCount(l);
        tInfoCustomer.setLockTimeLimit(date);
        tInfoCustomer.setOrganizationCode(s);
        tInfoCustomer.setQuestion(s);
        tInfoCustomer.setAnswer(s);
        tInfoCustomer.setActiveAddress(s);
        tInfoCustomer.setActiveCode(s);
        tInfoCustomer.setActiveStatus(CustomerStatus.CUSTOMER_ACTIVED.getCustomerStatusCode());
        tInfoCustomer.setRegisteDate(date);
        tInfoCustomer.setIsClosingAccount(s);
        tInfoCustomer.setRegisteOrgCode(s);
        tInfoCustomer.setRegisteUid(s);
        tInfoCustomer.setManager(s);
        tInfoCustomer.setLastSuccessLoginTime(date);
        tInfoCustomer.setLastSuccessLoginIp(s);
        tInfoCustomer.setLastFailLoginTime(date);
        tInfoCustomer.setLastFailLoginIp(s);
        tInfoCustomer.setLastUpdateTime(date);
        tInfoCustomer.setCustomerStatus(s);
        tInfoCustomer.setCloseTime(date);
        tInfoCustomer.setActiveTimeLimit(date);
        tInfoCustomer.setActiveTime(date);
        tInfoCustomer.setCustomerGrade(s);
        tInfoCustomer.setCustomerType(s);
        tInfoCustomer.setNationality(s);
        tInfoCustomer.setProfession(s);
        tInfoCustomer.setIdentifyExpiredDate(s);
        txnCheckService.checkCustomerPaymentTxn(tInfoCustomer);
    }

    @Test
    public void txnIniCheck(){
        String s = "s";
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
        TxnType txnType = TxnType.TXN_CONSUME;
        TxnSeqType txnSeqType = TxnSeqType.TRANS_SEQ_TYPE_NORMAL;
        txnCheckService.txnIniCheck(paymentInfo, txnType, txnSeqType);
    }

    @Test
    public void checkAccountPaymentTxn(){
        String s = "s";
        Long l = 11L;
        Date date = new Date();
        TInfoAccount account = new TInfoAccount();
        account.setAccountNo("8631898000000323");
        account.setAccountName(s);
        account.setCustomerNo(s);
        account.setOpenDate(date);
        account.setCloseDate(date);
        account.setExpiredDate(date);
        account.setPayPasswd(s);
        account.setInitPasswd(s);
        account.setInitPasswdModified(s);
        account.setPasswdErrNum(l);
        account.setPasswdErrLockTime(date);
        account.setStatus(s);
        account.setCommStatus(s);
        account.setType(s);
        account.setGrade(s);
        account.setIsRealname(s);
        account.setApanage(s);
        account.setOrganizationCode(s);
        account.setAreaCode(s);
        account.setCityCode(s);
        account.setOpenChannel(s);
        account.setBalance(l);
        account.setLastDayBal(l);
        account.setLastMonthBal(l);
        account.setAvailableBalance(l);
        account.setUnavailableBalance(l);
        account.setWithdrawBalance(l);
        account.setFrozenAmount(l);
        account.setPreauthorizedAmt(l);
        account.setLastTxnDate(s);
        account.setDaySumConsAmt(l);
        account.setDaySumConsCnt(1);
        account.setDaySumOutAmt(l);
        account.setDaySumOutCnt(1);
        account.setDaySumInAmt(l);
        account.setDaySumInCnt(1);
        account.setDaySumCashAmt(l);
        account.setDaySumCashCnt(1);
        account.setDaySumDepositAmt(l);
        account.setDaySumDepositCnt(1);
        account.setMonthSumConsAmt(l);
        account.setMonthSumOutAmt(l);
        account.setMonthSumInAmt(l);
        account.setMonthSumCashAmt(l);
        account.setMonthSumChgAmt(l);
        account.setIsAllowedClose(s);
        account.setIsCloseRtnCash(s);
        account.setEncryptedMsg(s);
        account.setLastUpdateTime(date);
        account.setLastTxnTime(date);
        account.setRsvdAmt1(l);
        account.setRsvdAmt2(l);
        account.setRsvdText1(s);
        account.setRsvdText2(s);
        account.setMonthSumConsCnt(l);
        account.setMonthSumOutCnt(l);
        account.setMonthSumInCnt(l);
        account.setMonthSumCashCnt(l);
        account.setMonthSumChgCnt(l);
        account.setMasterAccountNo(s);
        account.setForbiddenTxn(s);
        account.setExtendCount(l);
        account.setForbiddenChannel(s);
        account.setCardNo(s);
        account.setCardType(s);
        account.setCardBindingMethod(s);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMerchantCode(s);
        paymentInfo.setSupplyOrgCode(s);
        paymentInfo.setChannel(s);
        paymentInfo.setBussinessType(s);
        paymentInfo.setInnerTxnType("aa");
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
        txnCheckService.checkAccountPaymentTxn(account, paymentInfo);
    }


}
