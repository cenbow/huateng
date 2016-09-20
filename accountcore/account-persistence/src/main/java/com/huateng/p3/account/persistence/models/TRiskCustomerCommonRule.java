package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TRiskCustomerCommonRule implements Serializable{

	private static final long serialVersionUID = -2342685218143100294L;
	/*
 * 风险控制参数
 */
    private boolean existRiskRule;
    private Long recordNo;

    private String ruleType;

    private String acceptChannel;

    private String accountType;

    private String userGrade;

    private String accountNo;

    private Long consumeTransMaxNum;

    private Long consumeTransMaxAmt;

    private Long consumeTransMinAmt;

    private Long consumeTransMaxAmtSum;

    private Long chargeTransMaxNum;

    private Long chargeTransMaxAmt;

    private Long chargeTransMinAmt;

    private Long chargeTransMaxAmtSum;

    private Long withdrawTransMaxNum;

    private Long withdrawTransMaxAmt;

    private Long withdrawTransMinAmt;

    private Long withdrawTransMaxAmtSum;

    private Long transferTransMaxNum;

    private Long transferTransMaxAmt;

    private Long transferTransMinAmt;

    private Long transferTransMaxAmtSum;

    private Long monthMaxConsAmt;

    private Long monthMaxOutAmt;

    private Long monthMaxInAmt;

    private Long monthMaxCashAmt;

    private Long monthMaxChgAmt;

    private Long rsvdNum1;

    private Long rsvdAmt1;

    private Long rsvdNum2;

    private Long rsvdAmt2;

    private Long rsvdNum3;

    private Long rsvdAmt3;

    private Long rsvdNum4;

    private Long rsvdAmt4;

    private Long rsvdNum5;

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

    private String transType;

    private Date archiveTime;

    private String archivedFlag;

    private Long lRecordNo;

    private String remark;

    private Long monthMaxConsCnt;

    private Long monthMaxOutCnt;

    private Long monthMaxInCnt;

    private Long monthMaxCashCnt;

    private Long monthMaxChgCnt;

    private Long transferInMaxNum;

    private Long transferInMaxAmt;

    private Long transferInMinAmt;

    private Long transferInMaxAmtSum;

	public boolean isExistRiskRule() {
		return existRiskRule;
	}

	public void setExistRiskRule(boolean existRiskRule) {
		this.existRiskRule = existRiskRule;
	}

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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getConsumeTransMaxNum() {
		return consumeTransMaxNum;
	}

	public void setConsumeTransMaxNum(Long consumeTransMaxNum) {
		this.consumeTransMaxNum = consumeTransMaxNum;
	}

	public Long getConsumeTransMaxAmt() {
		return consumeTransMaxAmt;
	}

	public void setConsumeTransMaxAmt(Long consumeTransMaxAmt) {
		this.consumeTransMaxAmt = consumeTransMaxAmt;
	}

	public Long getConsumeTransMinAmt() {
		return consumeTransMinAmt;
	}

	public void setConsumeTransMinAmt(Long consumeTransMinAmt) {
		this.consumeTransMinAmt = consumeTransMinAmt;
	}

	public Long getConsumeTransMaxAmtSum() {
		return consumeTransMaxAmtSum;
	}

	public void setConsumeTransMaxAmtSum(Long consumeTransMaxAmtSum) {
		this.consumeTransMaxAmtSum = consumeTransMaxAmtSum;
	}

	public Long getChargeTransMaxNum() {
		return chargeTransMaxNum;
	}

	public void setChargeTransMaxNum(Long chargeTransMaxNum) {
		this.chargeTransMaxNum = chargeTransMaxNum;
	}

	public Long getChargeTransMaxAmt() {
		return chargeTransMaxAmt;
	}

	public void setChargeTransMaxAmt(Long chargeTransMaxAmt) {
		this.chargeTransMaxAmt = chargeTransMaxAmt;
	}

	public Long getChargeTransMinAmt() {
		return chargeTransMinAmt;
	}

	public void setChargeTransMinAmt(Long chargeTransMinAmt) {
		this.chargeTransMinAmt = chargeTransMinAmt;
	}

	public Long getChargeTransMaxAmtSum() {
		return chargeTransMaxAmtSum;
	}

	public void setChargeTransMaxAmtSum(Long chargeTransMaxAmtSum) {
		this.chargeTransMaxAmtSum = chargeTransMaxAmtSum;
	}

	public Long getWithdrawTransMaxNum() {
		return withdrawTransMaxNum;
	}

	public void setWithdrawTransMaxNum(Long withdrawTransMaxNum) {
		this.withdrawTransMaxNum = withdrawTransMaxNum;
	}

	public Long getWithdrawTransMaxAmt() {
		return withdrawTransMaxAmt;
	}

	public void setWithdrawTransMaxAmt(Long withdrawTransMaxAmt) {
		this.withdrawTransMaxAmt = withdrawTransMaxAmt;
	}

	public Long getWithdrawTransMinAmt() {
		return withdrawTransMinAmt;
	}

	public void setWithdrawTransMinAmt(Long withdrawTransMinAmt) {
		this.withdrawTransMinAmt = withdrawTransMinAmt;
	}

	public Long getWithdrawTransMaxAmtSum() {
		return withdrawTransMaxAmtSum;
	}

	public void setWithdrawTransMaxAmtSum(Long withdrawTransMaxAmtSum) {
		this.withdrawTransMaxAmtSum = withdrawTransMaxAmtSum;
	}

	public Long getTransferTransMaxNum() {
		return transferTransMaxNum;
	}

	public void setTransferTransMaxNum(Long transferTransMaxNum) {
		this.transferTransMaxNum = transferTransMaxNum;
	}

	public Long getTransferTransMaxAmt() {
		return transferTransMaxAmt;
	}

	public void setTransferTransMaxAmt(Long transferTransMaxAmt) {
		this.transferTransMaxAmt = transferTransMaxAmt;
	}

	public Long getTransferTransMinAmt() {
		return transferTransMinAmt;
	}

	public void setTransferTransMinAmt(Long transferTransMinAmt) {
		this.transferTransMinAmt = transferTransMinAmt;
	}

	public Long getTransferTransMaxAmtSum() {
		return transferTransMaxAmtSum;
	}

	public void setTransferTransMaxAmtSum(Long transferTransMaxAmtSum) {
		this.transferTransMaxAmtSum = transferTransMaxAmtSum;
	}

	public Long getMonthMaxConsAmt() {
		return monthMaxConsAmt;
	}

	public void setMonthMaxConsAmt(Long monthMaxConsAmt) {
		this.monthMaxConsAmt = monthMaxConsAmt;
	}

	public Long getMonthMaxOutAmt() {
		return monthMaxOutAmt;
	}

	public void setMonthMaxOutAmt(Long monthMaxOutAmt) {
		this.monthMaxOutAmt = monthMaxOutAmt;
	}

	public Long getMonthMaxInAmt() {
		return monthMaxInAmt;
	}

	public void setMonthMaxInAmt(Long monthMaxInAmt) {
		this.monthMaxInAmt = monthMaxInAmt;
	}

	public Long getMonthMaxCashAmt() {
		return monthMaxCashAmt;
	}

	public void setMonthMaxCashAmt(Long monthMaxCashAmt) {
		this.monthMaxCashAmt = monthMaxCashAmt;
	}

	public Long getMonthMaxChgAmt() {
		return monthMaxChgAmt;
	}

	public void setMonthMaxChgAmt(Long monthMaxChgAmt) {
		this.monthMaxChgAmt = monthMaxChgAmt;
	}

	public Long getRsvdNum1() {
		return rsvdNum1;
	}

	public void setRsvdNum1(Long rsvdNum1) {
		this.rsvdNum1 = rsvdNum1;
	}

	public Long getRsvdAmt1() {
		return rsvdAmt1;
	}

	public void setRsvdAmt1(Long rsvdAmt1) {
		this.rsvdAmt1 = rsvdAmt1;
	}

	public Long getRsvdNum2() {
		return rsvdNum2;
	}

	public void setRsvdNum2(Long rsvdNum2) {
		this.rsvdNum2 = rsvdNum2;
	}

	public Long getRsvdAmt2() {
		return rsvdAmt2;
	}

	public void setRsvdAmt2(Long rsvdAmt2) {
		this.rsvdAmt2 = rsvdAmt2;
	}

	public Long getRsvdNum3() {
		return rsvdNum3;
	}

	public void setRsvdNum3(Long rsvdNum3) {
		this.rsvdNum3 = rsvdNum3;
	}

	public Long getRsvdAmt3() {
		return rsvdAmt3;
	}

	public void setRsvdAmt3(Long rsvdAmt3) {
		this.rsvdAmt3 = rsvdAmt3;
	}

	public Long getRsvdNum4() {
		return rsvdNum4;
	}

	public void setRsvdNum4(Long rsvdNum4) {
		this.rsvdNum4 = rsvdNum4;
	}

	public Long getRsvdAmt4() {
		return rsvdAmt4;
	}

	public void setRsvdAmt4(Long rsvdAmt4) {
		this.rsvdAmt4 = rsvdAmt4;
	}

	public Long getRsvdNum5() {
		return rsvdNum5;
	}

	public void setRsvdNum5(Long rsvdNum5) {
		this.rsvdNum5 = rsvdNum5;
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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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

	public Long getMonthMaxConsCnt() {
		return monthMaxConsCnt;
	}

	public void setMonthMaxConsCnt(Long monthMaxConsCnt) {
		this.monthMaxConsCnt = monthMaxConsCnt;
	}

	public Long getMonthMaxOutCnt() {
		return monthMaxOutCnt;
	}

	public void setMonthMaxOutCnt(Long monthMaxOutCnt) {
		this.monthMaxOutCnt = monthMaxOutCnt;
	}

	public Long getMonthMaxInCnt() {
		return monthMaxInCnt;
	}

	public void setMonthMaxInCnt(Long monthMaxInCnt) {
		this.monthMaxInCnt = monthMaxInCnt;
	}

	public Long getMonthMaxCashCnt() {
		return monthMaxCashCnt;
	}

	public void setMonthMaxCashCnt(Long monthMaxCashCnt) {
		this.monthMaxCashCnt = monthMaxCashCnt;
	}

	public Long getMonthMaxChgCnt() {
		return monthMaxChgCnt;
	}

	public void setMonthMaxChgCnt(Long monthMaxChgCnt) {
		this.monthMaxChgCnt = monthMaxChgCnt;
	}

	public Long getTransferInMaxNum() {
		return transferInMaxNum;
	}

	public void setTransferInMaxNum(Long transferInMaxNum) {
		this.transferInMaxNum = transferInMaxNum;
	}

	public Long getTransferInMaxAmt() {
		return transferInMaxAmt;
	}

	public void setTransferInMaxAmt(Long transferInMaxAmt) {
		this.transferInMaxAmt = transferInMaxAmt;
	}

	public Long getTransferInMinAmt() {
		return transferInMinAmt;
	}

	public void setTransferInMinAmt(Long transferInMinAmt) {
		this.transferInMinAmt = transferInMinAmt;
	}

	public Long getTransferInMaxAmtSum() {
		return transferInMaxAmtSum;
	}

	public void setTransferInMaxAmtSum(Long transferInMaxAmtSum) {
		this.transferInMaxAmtSum = transferInMaxAmtSum;
	}

}