package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

public class RiskCustomerTxntypeRule implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6508719437254060425L;

	private String recordNo;

    private String transType;

    private String acceptChannel;

    private String accountType;

    private String userGrade;
    
    private String mergerFlag;
    
    private Long transMaxAmt;
    
    private Long transMinAmt;

    private Long dayTransAmt;

    private Long dayTransTimes;

    private Long monthTransAmt;

    private Long monthTransTimes;

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

    private Date archiveTime;

    private String archivedFlag;

    private Long lRecordNo;

    private String remark;
    
    public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getAcceptChannel() {
		return acceptChannel;
	}

	public void setAcceptChannel(String acceptChannel) {
		this.acceptChannel = acceptChannel;
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

	public String getMergerFlag() {
		return mergerFlag;
	}

	public void setMergerFlag(String mergerFlag) {
		this.mergerFlag = mergerFlag;
	}

	public Long getTransMaxAmt() {
		return transMaxAmt;
	}

	public void setTransMaxAmt(Long transMaxAmt) {
		this.transMaxAmt = transMaxAmt;
	}

	public Long getTransMinAmt() {
		return transMinAmt;
	}

	public void setTransMinAmt(Long transMinAmt) {
		this.transMinAmt = transMinAmt;
	}

	public Long getDayTransAmt() {
		return dayTransAmt;
	}

	public void setDayTransAmt(Long dayTransAmt) {
		this.dayTransAmt = dayTransAmt;
	}

	public Long getDayTransTimes() {
		return dayTransTimes;
	}

	public void setDayTransTimes(Long dayTransTimes) {
		this.dayTransTimes = dayTransTimes;
	}

	public Long getMonthTransAmt() {
		return monthTransAmt;
	}

	public void setMonthTransAmt(Long monthTransAmt) {
		this.monthTransAmt = monthTransAmt;
	}

	public Long getMonthTransTimes() {
		return monthTransTimes;
	}

	public void setMonthTransTimes(Long monthTransTimes) {
		this.monthTransTimes = monthTransTimes;
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