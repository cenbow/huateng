package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TRiskMerchantCustomerRule implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2348941994745169848L;

	private Long recordNo;

    private String merchantCode;

    private String txnChannel;

    private String txnType;

    private String accountType;

    private String userGrade;

    private Short maxTxnAmt;

    private Short dayTxnNum;

    private Short dayTxnAmt;

    private Short monthTxnNum;

    private Short monthTxnAmt;

    private Short rsvdAmt1;

    private Short rsvdAmt2;

    private Short rsvdAmt3;

    private Short rsvdAmt4;

    private Short rsvdAmt5;

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

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public Short getMaxTxnAmt() {
        return maxTxnAmt;
    }

    public void setMaxTxnAmt(Short maxTxnAmt) {
        this.maxTxnAmt = maxTxnAmt;
    }

    public Short getDayTxnNum() {
        return dayTxnNum;
    }

    public void setDayTxnNum(Short dayTxnNum) {
        this.dayTxnNum = dayTxnNum;
    }

    public Short getDayTxnAmt() {
        return dayTxnAmt;
    }

    public void setDayTxnAmt(Short dayTxnAmt) {
        this.dayTxnAmt = dayTxnAmt;
    }

    public Short getMonthTxnNum() {
        return monthTxnNum;
    }

    public void setMonthTxnNum(Short monthTxnNum) {
        this.monthTxnNum = monthTxnNum;
    }

    public Short getMonthTxnAmt() {
        return monthTxnAmt;
    }

    public void setMonthTxnAmt(Short monthTxnAmt) {
        this.monthTxnAmt = monthTxnAmt;
    }

    public Short getRsvdAmt1() {
        return rsvdAmt1;
    }

    public void setRsvdAmt1(Short rsvdAmt1) {
        this.rsvdAmt1 = rsvdAmt1;
    }

    public Short getRsvdAmt2() {
        return rsvdAmt2;
    }

    public void setRsvdAmt2(Short rsvdAmt2) {
        this.rsvdAmt2 = rsvdAmt2;
    }

    public Short getRsvdAmt3() {
        return rsvdAmt3;
    }

    public void setRsvdAmt3(Short rsvdAmt3) {
        this.rsvdAmt3 = rsvdAmt3;
    }

    public Short getRsvdAmt4() {
        return rsvdAmt4;
    }

    public void setRsvdAmt4(Short rsvdAmt4) {
        this.rsvdAmt4 = rsvdAmt4;
    }

    public Short getRsvdAmt5() {
        return rsvdAmt5;
    }

    public void setRsvdAmt5(Short rsvdAmt5) {
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