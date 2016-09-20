package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogPreauthApply implements Serializable{

	private static final long serialVersionUID = -7422508020538352447L;

	private String txnSeqNo;

    private String settleDate;

    private Date txnTime;

    private String preAuthNo;

    private Date authEndDate;

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

    private String acceptMatchFlag;

    private String transferMatchFlag;

    private Long authAmt;

    private Long authEndAmt = 0l;

    private String txnCurrency;

    private String innerCardNo;

    private String outerCardNo;

    private String cardMediaType;

    private String cardBrandType;

    private String accountType;

    private String accountNo;

    private String areaCode;

    private String cityCode;

    private String psamNo;

    private String terminalNo;

    private String terminalSeqNo;

    private String terminalOperNo;

    private Long batchNo = 0l;

    private String transSeqType;

    private String paymentObjNo;

    private String paymentObjType;

    private String lAcceptTransSeqNo;

    private String lAcceptTransDate;

    private String lAcceptTransTime;

    private String acceptProcFlag;

    private String transferProcFlag;

    private String acceptRespCode;

    private String transferRespCode;

    private Date transFinTs;

    private Date transToTs;

    private String revsalFlag;

    private String revsalTxnSeqNo;

    private String cancelFlag;

    private String cancelTxnSeqNo;

    private String lTxnSeqNo;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String resvFld4;

    private String resvFld5;

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

    public String getPreAuthNo() {
        return preAuthNo;
    }

    public void setPreAuthNo(String preAuthNo) {
        this.preAuthNo = preAuthNo;
    }

    public Date getAuthEndDate() {
        return authEndDate;
    }

    public void setAuthEndDate(Date authEndDate) {
        this.authEndDate = authEndDate;
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

    public Long getAuthAmt() {
        return authAmt;
    }

    public void setAuthAmt(Long authAmt) {
        this.authAmt = authAmt;
    }

    public Long getAuthEndAmt() {
        return authEndAmt;
    }

    public void setAuthEndAmt(Long authEndAmt) {
        this.authEndAmt = authEndAmt;
    }

    public String getTxnCurrency() {
        return txnCurrency;
    }

    public void setTxnCurrency(String txnCurrency) {
        this.txnCurrency = txnCurrency;
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

    public String getPsamNo() {
        return psamNo;
    }

    public void setPsamNo(String psamNo) {
        this.psamNo = psamNo;
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

    public String getTerminalOperNo() {
        return terminalOperNo;
    }

    public void setTerminalOperNo(String terminalOperNo) {
        this.terminalOperNo = terminalOperNo;
    }

    public Long getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }

    public String getTransSeqType() {
        return transSeqType;
    }

    public void setTransSeqType(String transSeqType) {
        this.transSeqType = transSeqType;
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

    public Date getTransFinTs() {
        return transFinTs;
    }

    public void setTransFinTs(Date transFinTs) {
        this.transFinTs = transFinTs;
    }

    public Date getTransToTs() {
        return transToTs;
    }

    public void setTransToTs(Date transToTs) {
        this.transToTs = transToTs;
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
}