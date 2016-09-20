package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogReconciliation implements Serializable{

	private static final long serialVersionUID = 7943688046705573309L;

	private Long recordNo;

    private String applyOrgCode;

    private String reason;

    private String acceptOrgCode;

    private String transferOrgCode;

    private String inPayOrgCode;

    private String outPayOrgCode;

    private String supplyOrgCode;

    private String terminalNo;

    private String terminalSeqNo;

    private String acceptTxnDate;

    private String acceptTxnTime;

    private Long txnAmount;

    private String inAccountNo;

    private String inSeqNo;

    private String outAccountNo;

    private String outSeqNo;

    private String status;

    private String lInSeqNo;

    private String lOutSeqNo;

    private String remark;

    private Long resendNum;

    private String applyUid;

    private Date applyTime;

    private String orgCheckUid;

    private Date orgCheckTime;

    private String coreCheckUid;

    private Date coreCheckTime;

    private String txnChannel;

    private String inTxnType;

    private String outTxnType;

    public Long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Long recordNo) {
        this.recordNo = recordNo;
    }

    public String getApplyOrgCode() {
        return applyOrgCode;
    }

    public void setApplyOrgCode(String applyOrgCode) {
        this.applyOrgCode = applyOrgCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAcceptOrgCode() {
        return acceptOrgCode;
    }

    public void setAcceptOrgCode(String acceptOrgCode) {
        this.acceptOrgCode = acceptOrgCode;
    }

    public String getTransferOrgCode() {
        return transferOrgCode;
    }

    public void setTransferOrgCode(String transferOrgCode) {
        this.transferOrgCode = transferOrgCode;
    }

    public String getInPayOrgCode() {
        return inPayOrgCode;
    }

    public void setInPayOrgCode(String inPayOrgCode) {
        this.inPayOrgCode = inPayOrgCode;
    }

    public String getOutPayOrgCode() {
        return outPayOrgCode;
    }

    public void setOutPayOrgCode(String outPayOrgCode) {
        this.outPayOrgCode = outPayOrgCode;
    }

    public String getSupplyOrgCode() {
        return supplyOrgCode;
    }

    public void setSupplyOrgCode(String supplyOrgCode) {
        this.supplyOrgCode = supplyOrgCode;
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

    public Long getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(Long txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getInAccountNo() {
        return inAccountNo;
    }

    public void setInAccountNo(String inAccountNo) {
        this.inAccountNo = inAccountNo;
    }

    public String getInSeqNo() {
        return inSeqNo;
    }

    public void setInSeqNo(String inSeqNo) {
        this.inSeqNo = inSeqNo;
    }

    public String getOutAccountNo() {
        return outAccountNo;
    }

    public void setOutAccountNo(String outAccountNo) {
        this.outAccountNo = outAccountNo;
    }

    public String getOutSeqNo() {
        return outSeqNo;
    }

    public void setOutSeqNo(String outSeqNo) {
        this.outSeqNo = outSeqNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getlInSeqNo() {
        return lInSeqNo;
    }

    public void setlInSeqNo(String lInSeqNo) {
        this.lInSeqNo = lInSeqNo;
    }

    public String getlOutSeqNo() {
        return lOutSeqNo;
    }

    public void setlOutSeqNo(String lOutSeqNo) {
        this.lOutSeqNo = lOutSeqNo;
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

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
    }

    public String getInTxnType() {
        return inTxnType;
    }

    public void setInTxnType(String inTxnType) {
        this.inTxnType = inTxnType;
    }

    public String getOutTxnType() {
        return outTxnType;
    }

    public void setOutTxnType(String outTxnType) {
        this.outTxnType = outTxnType;
    }
}