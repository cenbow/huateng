package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogRefundApply implements Serializable{

	private static final long serialVersionUID = 8328325830410935018L;

	private String recordNo;

    private String orgCode;

    private String terminalNo;

    private String terminalSeqNo;

    private String acceptTxnDate;

    private String acceptTxnTime;

    private String accountNo;

    private Long txnAmount;

    private String lTxnSeqNo;

    private String refundSeqNo;

    private String status;

    private String remark;

    private Long resendNum;

    private String applyUid;

    private Date applyTime;

    private String orgCheckUid;

    private Date orgCheckTime;

    private String coreCheckUid;

    private Date coreCheckTime;

    private String acceptSeqNo;

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getAcceptTxnDate() {
        return acceptTxnDate;
    }

    public void setAcceptTxnDate(String acceptTxnDate) {
        this.acceptTxnDate = acceptTxnDate;
    }

    public String getAcceptTxnTime() {
        return acceptTxnTime;
    }

    public void setAcceptTxnTime(String acceptTxnTime) {
        this.acceptTxnTime = acceptTxnTime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Long getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(Long txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getlTxnSeqNo() {
        return lTxnSeqNo;
    }

    public void setlTxnSeqNo(String lTxnSeqNo) {
        this.lTxnSeqNo = lTxnSeqNo;
    }

    public String getRefundSeqNo() {
        return refundSeqNo;
    }

    public void setRefundSeqNo(String refundSeqNo) {
        this.refundSeqNo = refundSeqNo;
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

    public Long getResendNum() {
        return resendNum;
    }

    public void setResendNum(Long resendNum) {
        this.resendNum = resendNum;
    }

    public String getApplyUid() {
        return applyUid;
    }

    public void setApplyUid(String applyUid) {
        this.applyUid = applyUid;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getOrgCheckUid() {
        return orgCheckUid;
    }

    public void setOrgCheckUid(String orgCheckUid) {
        this.orgCheckUid = orgCheckUid;
    }

    public Date getOrgCheckTime() {
        return orgCheckTime;
    }

    public void setOrgCheckTime(Date orgCheckTime) {
        this.orgCheckTime = orgCheckTime;
    }

    public String getCoreCheckUid() {
        return coreCheckUid;
    }

    public void setCoreCheckUid(String coreCheckUid) {
        this.coreCheckUid = coreCheckUid;
    }

    public Date getCoreCheckTime() {
        return coreCheckTime;
    }

    public void setCoreCheckTime(Date coreCheckTime) {
        this.coreCheckTime = coreCheckTime;
    }

    public String getAcceptSeqNo() {
        return acceptSeqNo;
    }

    public void setAcceptSeqNo(String acceptSeqNo) {
        this.acceptSeqNo = acceptSeqNo;
    }
}