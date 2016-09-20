package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TParaAccountExtendFee implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7719541801961012058L;

	private Long recordNo;

    private String accountType;

    private String extendPeriod;

    private String feeType;

    private Long feeFixAmt;

    private Long feeRatio;

    private Long feeMin;

    private Long feeMax;

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

    private Short lRecordNo;

    private String remark;

    public Long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Long recordNo) {
        this.recordNo = recordNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getExtendPeriod() {
        return extendPeriod;
    }

    public void setExtendPeriod(String extendPeriod) {
        this.extendPeriod = extendPeriod;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Long getFeeFixAmt() {
        return feeFixAmt;
    }

    public void setFeeFixAmt(Long feeFixAmt) {
        this.feeFixAmt = feeFixAmt;
    }

    public Long getFeeRatio() {
        return feeRatio;
    }

    public void setFeeRatio(Long feeRatio) {
        this.feeRatio = feeRatio;
    }

    public Long getFeeMin() {
        return feeMin;
    }

    public void setFeeMin(Long feeMin) {
        this.feeMin = feeMin;
    }

    public Long getFeeMax() {
        return feeMax;
    }

    public void setFeeMax(Long feeMax) {
        this.feeMax = feeMax;
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

    public Short getlRecordNo() {
        return lRecordNo;
    }

    public void setlRecordNo(Short lRecordNo) {
        this.lRecordNo = lRecordNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}