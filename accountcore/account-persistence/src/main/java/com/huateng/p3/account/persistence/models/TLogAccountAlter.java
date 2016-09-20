package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogAccountAlter implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3366084575140646661L;

	private Long recordNo;

    private String txnChannel;

    private String txnType;

    private String txnDscpt;

    private String acceptOrgCode;

    private String acceptTransSeqNo;

    private String acceptTransDate;

    private String acceptTransTime;

    private String accountType;

    private String customerNo;

    private String accountNo;

    private String txnAmount;

    private Date txnTime;

    private String mobilePhone;

    private String email;

    private String remark;

    private String remark2;

    private String rsvdText1;

    private String rsvdText2;

    private String status;

    private String feeAmt;

    private String rsvdText3;

    private String rsvdText4;

    private String rsvdText5;

    private String rsvdText6;

    public Long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Long recordNo) {
        this.recordNo = recordNo;
    }

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
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

    public String getAcceptOrgCode() {
        return acceptOrgCode;
    }

    public void setAcceptOrgCode(String acceptOrgCode) {
        this.acceptOrgCode = acceptOrgCode;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Date getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(Date txnTime) {
        this.txnTime = txnTime;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRsvdText1() {
        return rsvdText1;
    }

    public void setRsvdText1(String rsvdText1) {
        this.rsvdText1 = rsvdText1;
    }

    public String getRsvdText2() {
        return rsvdText2;
    }

    public void setRsvdText2(String rsvdText2) {
        this.rsvdText2 = rsvdText2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getRsvdText3() {
        return rsvdText3;
    }

    public void setRsvdText3(String rsvdText3) {
        this.rsvdText3 = rsvdText3;
    }

    public String getRsvdText4() {
        return rsvdText4;
    }

    public void setRsvdText4(String rsvdText4) {
        this.rsvdText4 = rsvdText4;
    }

    public String getRsvdText5() {
        return rsvdText5;
    }

    public void setRsvdText5(String rsvdText5) {
        this.rsvdText5 = rsvdText5;
    }

    public String getRsvdText6() {
        return rsvdText6;
    }

    public void setRsvdText6(String rsvdText6) {
        this.rsvdText6 = rsvdText6;
    }
}