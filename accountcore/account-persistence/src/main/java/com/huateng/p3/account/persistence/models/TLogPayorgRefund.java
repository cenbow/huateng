package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogPayorgRefund implements Serializable {

	private static final long serialVersionUID = -4033211421249708486L;

	private String txnSeqNo;

    private String orgCode;

    private String outOrderId;

    private String oldTransDate;

    private Long refundAmount;

    private Date inputTime;

    private String inputUid;

    private Date endTime;

    private String status;

    private String remark;

    private String resvFld1;

    private String resvFld2;

    private String acceptTxn;

    private String acceptSeqNo;

    private Long batchNo;

    private String acceptOrgCode;

    private String acceptDate;

    private String acceptTime;

    private String supplyOrgCode;

    private String transferOrgCode;

    public String getTxnSeqNo() {
        return txnSeqNo;
    }

    public void setTxnSeqNo(String txnSeqNo) {
        this.txnSeqNo = txnSeqNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getOldTransDate() {
        return oldTransDate;
    }

    public void setOldTransDate(String oldTransDate) {
        this.oldTransDate = oldTransDate;
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getInputUid() {
        return inputUid;
    }

    public void setInputUid(String inputUid) {
        this.inputUid = inputUid;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getAcceptTxn() {
        return acceptTxn;
    }

    public void setAcceptTxn(String acceptTxn) {
        this.acceptTxn = acceptTxn;
    }

    public String getAcceptSeqNo() {
        return acceptSeqNo;
    }

    public void setAcceptSeqNo(String acceptSeqNo) {
        this.acceptSeqNo = acceptSeqNo;
    }

    public Long getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }

    public String getAcceptOrgCode() {
        return acceptOrgCode;
    }

    public void setAcceptOrgCode(String acceptOrgCode) {
        this.acceptOrgCode = acceptOrgCode;
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

    public String getSupplyOrgCode() {
        return supplyOrgCode;
    }

    public void setSupplyOrgCode(String supplyOrgCode) {
        this.supplyOrgCode = supplyOrgCode;
    }

    public String getTransferOrgCode() {
        return transferOrgCode;
    }

    public void setTransferOrgCode(String transferOrgCode) {
        this.transferOrgCode = transferOrgCode;
    }
}