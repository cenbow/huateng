package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TRiskCardCommonRule implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6437576910616745864L;

	private Long recordNo;

    private String ruleType;

    private String txnChannel;

    private String cardType;

    private String cardGrade;

    private String cardNo;

    private Long consumeTxnMaxNum;

    private Long consumeTxnMaxAmt;

    private Long consumeTxnMinAmt;

    private Long consumeTxnMaxAmtSum;

    private Long chargeTxnMaxNum;

    private Long chargeTxnMaxAmt;

    private Long chargeTxnMinAmt;

    private Long chargeTxnMaxAmtSum;

    private Long withdrawTxnMaxNum;

    private Long withdrawTxnMaxAmt;

    private Long withdrawTxnMinAmt;

    private Long withdrawTxnMaxAmtSum;

    private Long transferTxnMaxNum;

    private Long transferTxnMaxAmt;

    private Long transferTxnMinAmt;

    private Long transferTxnMaxAmtSum;

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

    private String resvFld2;

    private Date archiveTime;

    private String archivedFlag;

    private Long lRecordNo;

    private String remark;

    private Long monthMaxConsCnt;

    private Long monthMaxOutCnt;

    private Long monthMaxInCnt;

    private Long monthMaxCashCnt;

    private Long monthMaxChgCnt;

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

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardGrade() {
        return cardGrade;
    }

    public void setCardGrade(String cardGrade) {
        this.cardGrade = cardGrade;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getConsumeTxnMaxNum() {
        return consumeTxnMaxNum;
    }

    public void setConsumeTxnMaxNum(Long consumeTxnMaxNum) {
        this.consumeTxnMaxNum = consumeTxnMaxNum;
    }

    public Long getConsumeTxnMaxAmt() {
        return consumeTxnMaxAmt;
    }

    public void setConsumeTxnMaxAmt(Long consumeTxnMaxAmt) {
        this.consumeTxnMaxAmt = consumeTxnMaxAmt;
    }

    public Long getConsumeTxnMinAmt() {
        return consumeTxnMinAmt;
    }

    public void setConsumeTxnMinAmt(Long consumeTxnMinAmt) {
        this.consumeTxnMinAmt = consumeTxnMinAmt;
    }

    public Long getConsumeTxnMaxAmtSum() {
        return consumeTxnMaxAmtSum;
    }

    public void setConsumeTxnMaxAmtSum(Long consumeTxnMaxAmtSum) {
        this.consumeTxnMaxAmtSum = consumeTxnMaxAmtSum;
    }

    public Long getChargeTxnMaxNum() {
        return chargeTxnMaxNum;
    }

    public void setChargeTxnMaxNum(Long chargeTxnMaxNum) {
        this.chargeTxnMaxNum = chargeTxnMaxNum;
    }

    public Long getChargeTxnMaxAmt() {
        return chargeTxnMaxAmt;
    }

    public void setChargeTxnMaxAmt(Long chargeTxnMaxAmt) {
        this.chargeTxnMaxAmt = chargeTxnMaxAmt;
    }

    public Long getChargeTxnMinAmt() {
        return chargeTxnMinAmt;
    }

    public void setChargeTxnMinAmt(Long chargeTxnMinAmt) {
        this.chargeTxnMinAmt = chargeTxnMinAmt;
    }

    public Long getChargeTxnMaxAmtSum() {
        return chargeTxnMaxAmtSum;
    }

    public void setChargeTxnMaxAmtSum(Long chargeTxnMaxAmtSum) {
        this.chargeTxnMaxAmtSum = chargeTxnMaxAmtSum;
    }

    public Long getWithdrawTxnMaxNum() {
        return withdrawTxnMaxNum;
    }

    public void setWithdrawTxnMaxNum(Long withdrawTxnMaxNum) {
        this.withdrawTxnMaxNum = withdrawTxnMaxNum;
    }

    public Long getWithdrawTxnMaxAmt() {
        return withdrawTxnMaxAmt;
    }

    public void setWithdrawTxnMaxAmt(Long withdrawTxnMaxAmt) {
        this.withdrawTxnMaxAmt = withdrawTxnMaxAmt;
    }

    public Long getWithdrawTxnMinAmt() {
        return withdrawTxnMinAmt;
    }

    public void setWithdrawTxnMinAmt(Long withdrawTxnMinAmt) {
        this.withdrawTxnMinAmt = withdrawTxnMinAmt;
    }

    public Long getWithdrawTxnMaxAmtSum() {
        return withdrawTxnMaxAmtSum;
    }

    public void setWithdrawTxnMaxAmtSum(Long withdrawTxnMaxAmtSum) {
        this.withdrawTxnMaxAmtSum = withdrawTxnMaxAmtSum;
    }

    public Long getTransferTxnMaxNum() {
        return transferTxnMaxNum;
    }

    public void setTransferTxnMaxNum(Long transferTxnMaxNum) {
        this.transferTxnMaxNum = transferTxnMaxNum;
    }

    public Long getTransferTxnMaxAmt() {
        return transferTxnMaxAmt;
    }

    public void setTransferTxnMaxAmt(Long transferTxnMaxAmt) {
        this.transferTxnMaxAmt = transferTxnMaxAmt;
    }

    public Long getTransferTxnMinAmt() {
        return transferTxnMinAmt;
    }

    public void setTransferTxnMinAmt(Long transferTxnMinAmt) {
        this.transferTxnMinAmt = transferTxnMinAmt;
    }

    public Long getTransferTxnMaxAmtSum() {
        return transferTxnMaxAmtSum;
    }

    public void setTransferTxnMaxAmtSum(Long transferTxnMaxAmtSum) {
        this.transferTxnMaxAmtSum = transferTxnMaxAmtSum;
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
    
	public boolean isExistRiskRule() {
		// TODO 暂不实现
		return true;
	}
}