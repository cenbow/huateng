package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TRiskMerchantCommonRule implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7413760922745651239L;

	private Long recordNo;

    private String ruleType;

    private String merchantType;

    private String merchantCode;

    private String txnChannel;

    private String businessType;

    private String terminalNo;

    private Long maxTxnAmt;

    private Long maxSumTxnAmt;

    private Long rsvdAmt1;

    private Long rsvdAmt2;

    private Long rsvdAmt3;

    private Long rsvdAmt4;

    private Long rsvdAmt5;

    private String useFlag;

    private Date effectiveDate;

    private Date validDate;

    private String createUid;

    private Date createTime;

    private String checkFlag;

    private String checkUid;

    private Date checkTime;

    private String lastModifyUid;

    private Date lastModifyTime;

    private String resvFld1;

    private String resvFld2;

    private Date archiveTime;

    private String archivedFlag;

    private Long lRecordNo;

    private String remark;

    public Long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Long recordNo) {
        this.recordNo = recordNo;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public Long getMaxTxnAmt() {
        return maxTxnAmt;
    }

    public void setMaxTxnAmt(Long maxTxnAmt) {
        this.maxTxnAmt = maxTxnAmt;
    }

    public Long getMaxSumTxnAmt() {
        return maxSumTxnAmt;
    }

    public void setMaxSumTxnAmt(Long maxSumTxnAmt) {
        this.maxSumTxnAmt = maxSumTxnAmt;
    }

    public Long getRsvdAmt1() {
        return rsvdAmt1;
    }

    public void setRsvdAmt1(Long rsvdAmt1) {
        this.rsvdAmt1 = rsvdAmt1;
    }

    public Long getRsvdAmt2() {
        return rsvdAmt2;
    }

    public void setRsvdAmt2(Long rsvdAmt2) {
        this.rsvdAmt2 = rsvdAmt2;
    }

    public Long getRsvdAmt3() {
        return rsvdAmt3;
    }

    public void setRsvdAmt3(Long rsvdAmt3) {
        this.rsvdAmt3 = rsvdAmt3;
    }

    public Long getRsvdAmt4() {
        return rsvdAmt4;
    }

    public void setRsvdAmt4(Long rsvdAmt4) {
        this.rsvdAmt4 = rsvdAmt4;
    }

    public Long getRsvdAmt5() {
        return rsvdAmt5;
    }

    public void setRsvdAmt5(Long rsvdAmt5) {
        this.rsvdAmt5 = rsvdAmt5;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getCheckUid() {
        return checkUid;
    }

    public void setCheckUid(String checkUid) {
        this.checkUid = checkUid;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getLastModifyUid() {
        return lastModifyUid;
    }

    public void setLastModifyUid(String lastModifyUid) {
        this.lastModifyUid = lastModifyUid;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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

    public Date getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }

    public String getArchivedFlag() {
        return archivedFlag;
    }

    public void setArchivedFlag(String archivedFlag) {
        this.archivedFlag = archivedFlag;
    }

    public Long getlRecordNo() {
        return lRecordNo;
    }

    public void setlRecordNo(Long lRecordNo) {
        this.lRecordNo = lRecordNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}