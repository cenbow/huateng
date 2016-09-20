package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TLogChannelRefundApply implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3194725323554243630L;

	private String id;

    private String thirdOrderNo;

    private String createTime;

    private String refundeFile;

    private String state;

    private String refundeFileTime;

    private Long totalAmt;

    private Long txnAmt;

    private String no;

    private String totalNo;

    private String refundeFlag;

    private String transDate;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String resvFld4;

    private String resvFld5;

    private String oldTxnSeqNo;

    private String refundSeqNo;

    private String payOrgCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRefundeFile() {
        return refundeFile;
    }

    public void setRefundeFile(String refundeFile) {
        this.refundeFile = refundeFile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRefundeFileTime() {
        return refundeFileTime;
    }

    public void setRefundeFileTime(String refundeFileTime) {
        this.refundeFileTime = refundeFileTime;
    }

    public Long getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Long totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Long getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(Long txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(String totalNo) {
        this.totalNo = totalNo;
    }

    public String getRefundeFlag() {
        return refundeFlag;
    }

    public void setRefundeFlag(String refundeFlag) {
        this.refundeFlag = refundeFlag;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
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

    public String getOldTxnSeqNo() {
        return oldTxnSeqNo;
    }

    public void setOldTxnSeqNo(String oldTxnSeqNo) {
        this.oldTxnSeqNo = oldTxnSeqNo;
    }

    public String getRefundSeqNo() {
        return refundSeqNo;
    }

    public void setRefundSeqNo(String refundSeqNo) {
        this.refundSeqNo = refundSeqNo;
    }

    public String getPayOrgCode() {
        return payOrgCode;
    }

    public void setPayOrgCode(String payOrgCode) {
        this.payOrgCode = payOrgCode;
    }
}