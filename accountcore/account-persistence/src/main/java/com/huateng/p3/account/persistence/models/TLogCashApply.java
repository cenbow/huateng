package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogCashApply extends TLogCashApplyKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7864921007923590395L;

	private String txnType;

    private String txnDscpt;

    private Date txnTime;

    private Date endTime;

    private String customerNo;

    private String fundAccountNo;

    private String bankName;

    private String branchBankName;

    private String provCode;

    private String cityCode;

    private String realName;

    private String bankAccountNo;

    private String txnChannel;

    private String acceptOrgCode;

    private String acceptOrgType;

    private String acceptSeqNo;

    private String acceptDate;

    private String acceptTime;

    private Long drawAmt;

    private String status;

    private String remark;

    private String resvFld1;

    private String resvFld2;

    private String payOrgCode;

    private String supplyOrgCode;

    private Long feeAmt;

    private String failFlag;

    private Date failTime;

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

    public Date getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(Date txnTime) {
        this.txnTime = txnTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getFundAccountNo() {
        return fundAccountNo;
    }

    public void setFundAccountNo(String fundAccountNo) {
        this.fundAccountNo = fundAccountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
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

    public String getAcceptSeqNo() {
        return acceptSeqNo;
    }

    public void setAcceptSeqNo(String acceptSeqNo) {
        this.acceptSeqNo = acceptSeqNo;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Long getDrawAmt() {
        return drawAmt;
    }

    public void setDrawAmt(Long drawAmt) {
        this.drawAmt = drawAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getPayOrgCode() {
        return payOrgCode;
    }

    public void setPayOrgCode(String payOrgCode) {
        this.payOrgCode = payOrgCode;
    }

    public String getSupplyOrgCode() {
        return supplyOrgCode;
    }

    public void setSupplyOrgCode(String supplyOrgCode) {
        this.supplyOrgCode = supplyOrgCode;
    }

    public Long getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(Long feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getFailFlag() {
        return failFlag;
    }

    public void setFailFlag(String failFlag) {
        this.failFlag = failFlag;
    }

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }
}