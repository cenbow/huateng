package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TLogAccountPayment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 110717644292629978L;

	private String txnSeqNo;

    private String settleDate;

    private String customerNo;

    private String accountNo;

    private String accountType;

    private String innerCardNo;

    private Object txnTime;

    private Integer txnMonth;

    private String businessType;

    private String txnType;

    private String txnDscpt;

    private String txnChannel;

    private String acceptOrgCode;

    private String acceptOrgType;

    private String acceptTransDate;

    private String acceptTransTime;

    private Long txnAmt;

    private Long beforeAmt;

    private Long afterAmt;

    private String areaCode;

    private String cityCode;

    private String transSeqType;

    private String paymentObjNo;

    private String paymentObjType;

    private String txnLabel;

    private String clearingTxnSeqNo;

    private String revsalFlag;

    private String cancelFlag;

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

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getInnerCardNo() {
        return innerCardNo;
    }

    public void setInnerCardNo(String innerCardNo) {
        this.innerCardNo = innerCardNo;
    }

    public Object getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(Object txnTime) {
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

    public String getTxnLabel() {
        return txnLabel;
    }

    public void setTxnLabel(String txnLabel) {
        this.txnLabel = txnLabel;
    }

    public String getClearingTxnSeqNo() {
        return clearingTxnSeqNo;
    }

    public void setClearingTxnSeqNo(String clearingTxnSeqNo) {
        this.clearingTxnSeqNo = clearingTxnSeqNo;
    }

    public String getRevsalFlag() {
        return revsalFlag;
    }

    public void setRevsalFlag(String revsalFlag) {
        this.revsalFlag = revsalFlag;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
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