package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogDubiousTxn implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7218568728232218103L;

	private String txnSeqNo;

    private String settleDate;

    private Date txnTime;

    private Integer txnMonth;

    private String businessType;

    private String txnType;

    private String txnDscpt;

    private String txnChannel;

    private String acceptOrgCode;

    private String acceptOrgType;

    private String acceptTransSeqNo;

    private String acceptTransDate;

    private String acceptTransTime;

    private String transferOrgCode;

    private String transferOrgType;

    private String transferTransSeqNo;

    private String transferTransDate;

    private String transferTransTime;

    private String payOrgCode;

    private String payOrgType;

    private String payTransSeqNo;

    private String payTransDate;

    private String payTransTime;

    private String supplyOrgCode;

    private String supplyOrgType;

    private String supplyTransSeqNo;

    private String supplyTransDate;

    private String supplyTransTime;

    private Long txnAmt;

    private Long beforeAmt;

    private Long afterAmt;

    private String feeInFlag;

    private String profitInFlag;

    private Long feeAmt;

    private Long profitAmt;

    private Long pFeeCode;

    private Long pFeeAmt;

    private Long acceptFeeCode;

    private Long acceptFeeAmt;

    private String acceptFeeFlag;

    private Long transferFeeCode;

    private Long transferFeeAmt;

    private String transferFeeFlag;

    private Long payFeeCode;

    private Long payFeeAmt;

    private String payFeeFlag;

    private Long supplyFeeCode;

    private Long supplyFeeAmt;

    private String supplyFeeFlag;

    private String cardSn;

    private Long cardCnt;

    private String txnTac;

    private String tacCheckLabel;

    private Long cardDptAmt;

    private String cardExpDate;

    private String batchFileId;

    private String uploadFlag;

    private String uploadPkgNo;

    private String innerCardNo;

    private String outerCardNo;

    private String cardMediaType;

    private String cardBrandType;

    private String accountType;

    private String accountNo;

    private String areaCode;

    private String cityCode;

    private String terminalNo;

    private String terminalSeqNo;

    private String terminalCommSeqNo;

    private String terminalOperNo;

    private String batchNo;

    private String transSeqType;

    private String acceptMatchFlag;

    private String transferMatchFlag;

    private String payMatchFlag;

    private String supplyMatchFlag;

    private String paymentObjNo;

    private String paymentObjType;

    private String lAcceptTransSeqNo;

    private String lAcceptTransDate;

    private String lAcceptTransTime;

    private String lTransferTransSeqNo;

    private String lTransferTransDate;

    private String lTransferTransTime;

    private String lPayTransSeqNo;

    private String lPayTransDate;

    private String lPayTransTime;

    private String lSupplyTransSeqNo;

    private String lSupplyTransDate;

    private String lSupplyTransTime;

    private String psamCardNo;

    private String preAuthSeqNo;

    private String acceptProcFlag;

    private String transferProcFlag;

    private String payProcFlag;

    private String supplyProcFlag;

    private String acceptRespCode;

    private String transferRespCode;

    private String payRespCode;

    private String supplyCode;

    private Date txnFinTs;

    private Date txnToTs;

    private String isClearing;

    private String clearingId;

    private Long resendNum;

    private String settleMonth;

    private String settleDay;

    private String revsalFlag;

    private String revsalTxnSeqNo;

    private String cancelFlag;

    private String cancelTxnSeqNo;

    private String lTxnSeqNo;

    private String returnFlag;

    private Long returnCount;

    private Long returnAmt;

    private String dubiousFlag;

    private String batchDate;

    private String batchFlag;

    private Long acceptSettleAmt;

    private Long transferSettleAmt;

    private Long paySettleAmt;

    private Long supplySettleAmt;

    private String transferInOrgCode;

    private String inAccountNo;

    private String transferOutOrgCode;

    private String outAccountNo;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String resvFld4;

    private String resvFld5;

    private String errorCode;

    private String remark;

    public String getTxnSeqNo() {
        return txnSeqNo;
    }

    public void setTxnSeqNo(String txnSeqNo) {
        this.txnSeqNo = txnSeqNo;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public Date getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(Date txnTime) {
        this.txnTime = txnTime;
    }

    public Integer getTxnMonth() {
        return txnMonth;
    }

    public void setTxnMonth(Integer txnMonth) {
        this.txnMonth = txnMonth;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnDscpt() {
        return txnDscpt;
    }

    public void setTxnDscpt(String txnDscpt) {
        this.txnDscpt = txnDscpt;
    }

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
    }

    public String getAcceptOrgCode() {
        return acceptOrgCode;
    }

    public void setAcceptOrgCode(String acceptOrgCode) {
        this.acceptOrgCode = acceptOrgCode;
    }

    public String getAcceptOrgType() {
        return acceptOrgType;
    }

    public void setAcceptOrgType(String acceptOrgType) {
        this.acceptOrgType = acceptOrgType;
    }

    public String getAcceptTransSeqNo() {
        return acceptTransSeqNo;
    }

    public void setAcceptTransSeqNo(String acceptTransSeqNo) {
        this.acceptTransSeqNo = acceptTransSeqNo;
    }

    public String getAcceptTransDate() {
        return acceptTransDate;
    }

    public void setAcceptTransDate(String acceptTransDate) {
        this.acceptTransDate = acceptTransDate;
    }

    public String getAcceptTransTime() {
        return acceptTransTime;
    }

    public void setAcceptTransTime(String acceptTransTime) {
        this.acceptTransTime = acceptTransTime;
    }

    public String getTransferOrgCode() {
        return transferOrgCode;
    }

    public void setTransferOrgCode(String transferOrgCode) {
        this.transferOrgCode = transferOrgCode;
    }

    public String getTransferOrgType() {
        return transferOrgType;
    }

    public void setTransferOrgType(String transferOrgType) {
        this.transferOrgType = transferOrgType;
    }

    public String getTransferTransSeqNo() {
        return transferTransSeqNo;
    }

    public void setTransferTransSeqNo(String transferTransSeqNo) {
        this.transferTransSeqNo = transferTransSeqNo;
    }

    public String getTransferTransDate() {
        return transferTransDate;
    }

    public void setTransferTransDate(String transferTransDate) {
        this.transferTransDate = transferTransDate;
    }

    public String getTransferTransTime() {
        return transferTransTime;
    }

    public void setTransferTransTime(String transferTransTime) {
        this.transferTransTime = transferTransTime;
    }

    public String getPayOrgCode() {
        return payOrgCode;
    }

    public void setPayOrgCode(String payOrgCode) {
        this.payOrgCode = payOrgCode;
    }

    public String getPayOrgType() {
        return payOrgType;
    }

    public void setPayOrgType(String payOrgType) {
        this.payOrgType = payOrgType;
    }

    public String getPayTransSeqNo() {
        return payTransSeqNo;
    }

    public void setPayTransSeqNo(String payTransSeqNo) {
        this.payTransSeqNo = payTransSeqNo;
    }

    public String getPayTransDate() {
        return payTransDate;
    }

    public void setPayTransDate(String payTransDate) {
        this.payTransDate = payTransDate;
    }

    public String getPayTransTime() {
        return payTransTime;
    }

    public void setPayTransTime(String payTransTime) {
        this.payTransTime = payTransTime;
    }

    public String getSupplyOrgCode() {
        return supplyOrgCode;
    }

    public void setSupplyOrgCode(String supplyOrgCode) {
        this.supplyOrgCode = supplyOrgCode;
    }

    public String getSupplyOrgType() {
        return supplyOrgType;
    }

    public void setSupplyOrgType(String supplyOrgType) {
        this.supplyOrgType = supplyOrgType;
    }

    public String getSupplyTransSeqNo() {
        return supplyTransSeqNo;
    }

    public void setSupplyTransSeqNo(String supplyTransSeqNo) {
        this.supplyTransSeqNo = supplyTransSeqNo;
    }

    public String getSupplyTransDate() {
        return supplyTransDate;
    }

    public void setSupplyTransDate(String supplyTransDate) {
        this.supplyTransDate = supplyTransDate;
    }

    public String getSupplyTransTime() {
        return supplyTransTime;
    }

    public void setSupplyTransTime(String supplyTransTime) {
        this.supplyTransTime = supplyTransTime;
    }

    public Long getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(Long txnAmt) {
        this.txnAmt = txnAmt;
    }

    public Long getBeforeAmt() {
        return beforeAmt;
    }

    public void setBeforeAmt(Long beforeAmt) {
        this.beforeAmt = beforeAmt;
    }

    public Long getAfterAmt() {
        return afterAmt;
    }

    public void setAfterAmt(Long afterAmt) {
        this.afterAmt = afterAmt;
    }

    public String getFeeInFlag() {
        return feeInFlag;
    }

    public void setFeeInFlag(String feeInFlag) {
        this.feeInFlag = feeInFlag;
    }

    public String getProfitInFlag() {
        return profitInFlag;
    }

    public void setProfitInFlag(String profitInFlag) {
        this.profitInFlag = profitInFlag;
    }

    public Long getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(Long feeAmt) {
        this.feeAmt = feeAmt;
    }

    public Long getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(Long profitAmt) {
        this.profitAmt = profitAmt;
    }

    public Long getpFeeCode() {
        return pFeeCode;
    }

    public void setpFeeCode(Long pFeeCode) {
        this.pFeeCode = pFeeCode;
    }

    public Long getpFeeAmt() {
        return pFeeAmt;
    }

    public void setpFeeAmt(Long pFeeAmt) {
        this.pFeeAmt = pFeeAmt;
    }

    public Long getAcceptFeeCode() {
        return acceptFeeCode;
    }

    public void setAcceptFeeCode(Long acceptFeeCode) {
        this.acceptFeeCode = acceptFeeCode;
    }

    public Long getAcceptFeeAmt() {
        return acceptFeeAmt;
    }

    public void setAcceptFeeAmt(Long acceptFeeAmt) {
        this.acceptFeeAmt = acceptFeeAmt;
    }

    public String getAcceptFeeFlag() {
        return acceptFeeFlag;
    }

    public void setAcceptFeeFlag(String acceptFeeFlag) {
        this.acceptFeeFlag = acceptFeeFlag;
    }

    public Long getTransferFeeCode() {
        return transferFeeCode;
    }

    public void setTransferFeeCode(Long transferFeeCode) {
        this.transferFeeCode = transferFeeCode;
    }

    public Long getTransferFeeAmt() {
        return transferFeeAmt;
    }

    public void setTransferFeeAmt(Long transferFeeAmt) {
        this.transferFeeAmt = transferFeeAmt;
    }

    public String getTransferFeeFlag() {
        return transferFeeFlag;
    }

    public void setTransferFeeFlag(String transferFeeFlag) {
        this.transferFeeFlag = transferFeeFlag;
    }

    public Long getPayFeeCode() {
        return payFeeCode;
    }

    public void setPayFeeCode(Long payFeeCode) {
        this.payFeeCode = payFeeCode;
    }

    public Long getPayFeeAmt() {
        return payFeeAmt;
    }

    public void setPayFeeAmt(Long payFeeAmt) {
        this.payFeeAmt = payFeeAmt;
    }

    public String getPayFeeFlag() {
        return payFeeFlag;
    }

    public void setPayFeeFlag(String payFeeFlag) {
        this.payFeeFlag = payFeeFlag;
    }

    public Long getSupplyFeeCode() {
        return supplyFeeCode;
    }

    public void setSupplyFeeCode(Long supplyFeeCode) {
        this.supplyFeeCode = supplyFeeCode;
    }

    public Long getSupplyFeeAmt() {
        return supplyFeeAmt;
    }

    public void setSupplyFeeAmt(Long supplyFeeAmt) {
        this.supplyFeeAmt = supplyFeeAmt;
    }

    public String getSupplyFeeFlag() {
        return supplyFeeFlag;
    }

    public void setSupplyFeeFlag(String supplyFeeFlag) {
        this.supplyFeeFlag = supplyFeeFlag;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public Long getCardCnt() {
        return cardCnt;
    }

    public void setCardCnt(Long cardCnt) {
        this.cardCnt = cardCnt;
    }

    public String getTxnTac() {
        return txnTac;
    }

    public void setTxnTac(String txnTac) {
        this.txnTac = txnTac;
    }

    public String getTacCheckLabel() {
        return tacCheckLabel;
    }

    public void setTacCheckLabel(String tacCheckLabel) {
        this.tacCheckLabel = tacCheckLabel;
    }

    public Long getCardDptAmt() {
        return cardDptAmt;
    }

    public void setCardDptAmt(Long cardDptAmt) {
        this.cardDptAmt = cardDptAmt;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public String getBatchFileId() {
        return batchFileId;
    }

    public void setBatchFileId(String batchFileId) {
        this.batchFileId = batchFileId;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getUploadPkgNo() {
        return uploadPkgNo;
    }

    public void setUploadPkgNo(String uploadPkgNo) {
        this.uploadPkgNo = uploadPkgNo;
    }

    public String getInnerCardNo() {
        return innerCardNo;
    }

    public void setInnerCardNo(String innerCardNo) {
        this.innerCardNo = innerCardNo;
    }

    public String getOuterCardNo() {
        return outerCardNo;
    }

    public void setOuterCardNo(String outerCardNo) {
        this.outerCardNo = outerCardNo;
    }

    public String getCardMediaType() {
        return cardMediaType;
    }

    public void setCardMediaType(String cardMediaType) {
        this.cardMediaType = cardMediaType;
    }

    public String getCardBrandType() {
        return cardBrandType;
    }

    public void setCardBrandType(String cardBrandType) {
        this.cardBrandType = cardBrandType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getTerminalSeqNo() {
        return terminalSeqNo;
    }

    public void setTerminalSeqNo(String terminalSeqNo) {
        this.terminalSeqNo = terminalSeqNo;
    }

    public String getTerminalCommSeqNo() {
        return terminalCommSeqNo;
    }

    public void setTerminalCommSeqNo(String terminalCommSeqNo) {
        this.terminalCommSeqNo = terminalCommSeqNo;
    }

    public String getTerminalOperNo() {
        return terminalOperNo;
    }

    public void setTerminalOperNo(String terminalOperNo) {
        this.terminalOperNo = terminalOperNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTransSeqType() {
        return transSeqType;
    }

    public void setTransSeqType(String transSeqType) {
        this.transSeqType = transSeqType;
    }

    public String getAcceptMatchFlag() {
        return acceptMatchFlag;
    }

    public void setAcceptMatchFlag(String acceptMatchFlag) {
        this.acceptMatchFlag = acceptMatchFlag;
    }

    public String getTransferMatchFlag() {
        return transferMatchFlag;
    }

    public void setTransferMatchFlag(String transferMatchFlag) {
        this.transferMatchFlag = transferMatchFlag;
    }

    public String getPayMatchFlag() {
        return payMatchFlag;
    }

    public void setPayMatchFlag(String payMatchFlag) {
        this.payMatchFlag = payMatchFlag;
    }

    public String getSupplyMatchFlag() {
        return supplyMatchFlag;
    }

    public void setSupplyMatchFlag(String supplyMatchFlag) {
        this.supplyMatchFlag = supplyMatchFlag;
    }

    public String getPaymentObjNo() {
        return paymentObjNo;
    }

    public void setPaymentObjNo(String paymentObjNo) {
        this.paymentObjNo = paymentObjNo;
    }

    public String getPaymentObjType() {
        return paymentObjType;
    }

    public void setPaymentObjType(String paymentObjType) {
        this.paymentObjType = paymentObjType;
    }

    public String getlAcceptTransSeqNo() {
        return lAcceptTransSeqNo;
    }

    public void setlAcceptTransSeqNo(String lAcceptTransSeqNo) {
        this.lAcceptTransSeqNo = lAcceptTransSeqNo;
    }

    public String getlAcceptTransDate() {
        return lAcceptTransDate;
    }

    public void setlAcceptTransDate(String lAcceptTransDate) {
        this.lAcceptTransDate = lAcceptTransDate;
    }

    public String getlAcceptTransTime() {
        return lAcceptTransTime;
    }

    public void setlAcceptTransTime(String lAcceptTransTime) {
        this.lAcceptTransTime = lAcceptTransTime;
    }

    public String getlTransferTransSeqNo() {
        return lTransferTransSeqNo;
    }

    public void setlTransferTransSeqNo(String lTransferTransSeqNo) {
        this.lTransferTransSeqNo = lTransferTransSeqNo;
    }

    public String getlTransferTransDate() {
        return lTransferTransDate;
    }

    public void setlTransferTransDate(String lTransferTransDate) {
        this.lTransferTransDate = lTransferTransDate;
    }

    public String getlTransferTransTime() {
        return lTransferTransTime;
    }

    public void setlTransferTransTime(String lTransferTransTime) {
        this.lTransferTransTime = lTransferTransTime;
    }

    public String getlPayTransSeqNo() {
        return lPayTransSeqNo;
    }

    public void setlPayTransSeqNo(String lPayTransSeqNo) {
        this.lPayTransSeqNo = lPayTransSeqNo;
    }

    public String getlPayTransDate() {
        return lPayTransDate;
    }

    public void setlPayTransDate(String lPayTransDate) {
        this.lPayTransDate = lPayTransDate;
    }

    public String getlPayTransTime() {
        return lPayTransTime;
    }

    public void setlPayTransTime(String lPayTransTime) {
        this.lPayTransTime = lPayTransTime;
    }

    public String getlSupplyTransSeqNo() {
        return lSupplyTransSeqNo;
    }

    public void setlSupplyTransSeqNo(String lSupplyTransSeqNo) {
        this.lSupplyTransSeqNo = lSupplyTransSeqNo;
    }

    public String getlSupplyTransDate() {
        return lSupplyTransDate;
    }

    public void setlSupplyTransDate(String lSupplyTransDate) {
        this.lSupplyTransDate = lSupplyTransDate;
    }

    public String getlSupplyTransTime() {
        return lSupplyTransTime;
    }

    public void setlSupplyTransTime(String lSupplyTransTime) {
        this.lSupplyTransTime = lSupplyTransTime;
    }

    public String getPsamCardNo() {
        return psamCardNo;
    }

    public void setPsamCardNo(String psamCardNo) {
        this.psamCardNo = psamCardNo;
    }

    public String getPreAuthSeqNo() {
        return preAuthSeqNo;
    }

    public void setPreAuthSeqNo(String preAuthSeqNo) {
        this.preAuthSeqNo = preAuthSeqNo;
    }

    public String getAcceptProcFlag() {
        return acceptProcFlag;
    }

    public void setAcceptProcFlag(String acceptProcFlag) {
        this.acceptProcFlag = acceptProcFlag;
    }

    public String getTransferProcFlag() {
        return transferProcFlag;
    }

    public void setTransferProcFlag(String transferProcFlag) {
        this.transferProcFlag = transferProcFlag;
    }

    public String getPayProcFlag() {
        return payProcFlag;
    }

    public void setPayProcFlag(String payProcFlag) {
        this.payProcFlag = payProcFlag;
    }

    public String getSupplyProcFlag() {
        return supplyProcFlag;
    }

    public void setSupplyProcFlag(String supplyProcFlag) {
        this.supplyProcFlag = supplyProcFlag;
    }

    public String getAcceptRespCode() {
        return acceptRespCode;
    }

    public void setAcceptRespCode(String acceptRespCode) {
        this.acceptRespCode = acceptRespCode;
    }

    public String getTransferRespCode() {
        return transferRespCode;
    }

    public void setTransferRespCode(String transferRespCode) {
        this.transferRespCode = transferRespCode;
    }

    public String getPayRespCode() {
        return payRespCode;
    }

    public void setPayRespCode(String payRespCode) {
        this.payRespCode = payRespCode;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public Date getTxnFinTs() {
        return txnFinTs;
    }

    public void setTxnFinTs(Date txnFinTs) {
        this.txnFinTs = txnFinTs;
    }

    public Date getTxnToTs() {
        return txnToTs;
    }

    public void setTxnToTs(Date txnToTs) {
        this.txnToTs = txnToTs;
    }

    public String getIsClearing() {
        return isClearing;
    }

    public void setIsClearing(String isClearing) {
        this.isClearing = isClearing;
    }

    public String getClearingId() {
        return clearingId;
    }

    public void setClearingId(String clearingId) {
        this.clearingId = clearingId;
    }

    public Long getResendNum() {
        return resendNum;
    }

    public void setResendNum(Long resendNum) {
        this.resendNum = resendNum;
    }

    public String getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(String settleMonth) {
        this.settleMonth = settleMonth;
    }

    public String getSettleDay() {
        return settleDay;
    }

    public void setSettleDay(String settleDay) {
        this.settleDay = settleDay;
    }

    public String getRevsalFlag() {
        return revsalFlag;
    }

    public void setRevsalFlag(String revsalFlag) {
        this.revsalFlag = revsalFlag;
    }

    public String getRevsalTxnSeqNo() {
        return revsalTxnSeqNo;
    }

    public void setRevsalTxnSeqNo(String revsalTxnSeqNo) {
        this.revsalTxnSeqNo = revsalTxnSeqNo;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getCancelTxnSeqNo() {
        return cancelTxnSeqNo;
    }

    public void setCancelTxnSeqNo(String cancelTxnSeqNo) {
        this.cancelTxnSeqNo = cancelTxnSeqNo;
    }

    public String getlTxnSeqNo() {
        return lTxnSeqNo;
    }

    public void setlTxnSeqNo(String lTxnSeqNo) {
        this.lTxnSeqNo = lTxnSeqNo;
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }

    public Long getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Long returnCount) {
        this.returnCount = returnCount;
    }

    public Long getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(Long returnAmt) {
        this.returnAmt = returnAmt;
    }

    public String getDubiousFlag() {
        return dubiousFlag;
    }

    public void setDubiousFlag(String dubiousFlag) {
        this.dubiousFlag = dubiousFlag;
    }

    public String getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(String batchDate) {
        this.batchDate = batchDate;
    }

    public String getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(String batchFlag) {
        this.batchFlag = batchFlag;
    }

    public Long getAcceptSettleAmt() {
        return acceptSettleAmt;
    }

    public void setAcceptSettleAmt(Long acceptSettleAmt) {
        this.acceptSettleAmt = acceptSettleAmt;
    }

    public Long getTransferSettleAmt() {
        return transferSettleAmt;
    }

    public void setTransferSettleAmt(Long transferSettleAmt) {
        this.transferSettleAmt = transferSettleAmt;
    }

    public Long getPaySettleAmt() {
        return paySettleAmt;
    }

    public void setPaySettleAmt(Long paySettleAmt) {
        this.paySettleAmt = paySettleAmt;
    }

    public Long getSupplySettleAmt() {
        return supplySettleAmt;
    }

    public void setSupplySettleAmt(Long supplySettleAmt) {
        this.supplySettleAmt = supplySettleAmt;
    }

    public String getTransferInOrgCode() {
        return transferInOrgCode;
    }

    public void setTransferInOrgCode(String transferInOrgCode) {
        this.transferInOrgCode = transferInOrgCode;
    }

    public String getInAccountNo() {
        return inAccountNo;
    }

    public void setInAccountNo(String inAccountNo) {
        this.inAccountNo = inAccountNo;
    }

    public String getTransferOutOrgCode() {
        return transferOutOrgCode;
    }

    public void setTransferOutOrgCode(String transferOutOrgCode) {
        this.transferOutOrgCode = transferOutOrgCode;
    }

    public String getOutAccountNo() {
        return outAccountNo;
    }

    public void setOutAccountNo(String outAccountNo) {
        this.outAccountNo = outAccountNo;
    }

    public String getResvFld1() {
        return resvFld1;
    }

    public void setResvFld1(String resvFld1) {
        this.resvFld1 = resvFld1;
    }

    public String getResvFld2() {
        return resvFld2;
    }

    public void setResvFld2(String resvFld2) {
        this.resvFld2 = resvFld2;
    }

    public String getResvFld3() {
        return resvFld3;
    }

    public void setResvFld3(String resvFld3) {
        this.resvFld3 = resvFld3;
    }

    public String getResvFld4() {
        return resvFld4;
    }

    public void setResvFld4(String resvFld4) {
        this.resvFld4 = resvFld4;
    }

    public String getResvFld5() {
        return resvFld5;
    }

    public void setResvFld5(String resvFld5) {
        this.resvFld5 = resvFld5;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}