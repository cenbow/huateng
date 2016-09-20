package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TRuleFeeCode implements Serializable{

	private static final long serialVersionUID = -5785405821087774163L;

	private Long recordNo;

    private String settleLv;

    private Long feeCode;

    private String feeName;

    private String feeType;

    private Long txnCountStep;

    private Short txnAmountStep;

    private BigDecimal percentage;

    private Short amt;

    private Short bottomAmt;

    private Short ceilingAmt;

    private String accuracyFlag;

    private String useFlag;

    private String effectiveDate;

    private String validDate;

    private String createUid;

    private Date createTime;

    private String checkUid;

    private String checkFlag;

    private Date checkTime;

    private String lastModifyUid;

    private Date lastModifyTime;

    private String resvFld1;

    private String resvFld2;

    private Date archiveTime;

    private String archivedFlag;

    private Long lRecordNo;

    private String remark;

    private String calcType;

    public Long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Long recordNo) {
        this.recordNo = recordNo;
    }

    public String getSettleLv() {
        return settleLv;
    }

    public void setSettleLv(String settleLv) {
        this.settleLv = settleLv;
    }

    public Long getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(Long feeCode) {
        this.feeCode = feeCode;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Long getTxnCountStep() {
        return txnCountStep;
    }

    public void setTxnCountStep(Long txnCountStep) {
        this.txnCountStep = txnCountStep;
    }

    public Short getTxnAmountStep() {
        return txnAmountStep;
    }

    public void setTxnAmountStep(Short txnAmountStep) {
        this.txnAmountStep = txnAmountStep;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Short getAmt() {
        return amt;
    }

    public void setAmt(Short amt) {
        this.amt = amt;
    }

    public Short getBottomAmt() {
        return bottomAmt;
    }

    public void setBottomAmt(Short bottomAmt) {
        this.bottomAmt = bottomAmt;
    }

    public Short getCeilingAmt() {
        return ceilingAmt;
    }

    public void setCeilingAmt(Short ceilingAmt) {
        this.ceilingAmt = ceilingAmt;
    }

    public String getAccuracyFlag() {
        return accuracyFlag;
    }

    public void setAccuracyFlag(String accuracyFlag) {
        this.accuracyFlag = accuracyFlag;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
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

    public String getCheckUid() {
        return checkUid;
    }

    public void setCheckUid(String checkUid) {
        this.checkUid = checkUid;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
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

    public String getCalcType() {
        return calcType;
    }

    public void setCalcType(String calcType) {
        this.calcType = calcType;
    }
}