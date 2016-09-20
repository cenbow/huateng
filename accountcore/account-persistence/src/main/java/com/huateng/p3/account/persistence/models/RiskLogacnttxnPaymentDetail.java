package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

public class RiskLogacnttxnPaymentDetail implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1796341440025169551L;

	private String recordNo;

    private String accountNo;

    private String transBigtype;

    private String transType;

    private String acceptChannel;

    private Long daySumAmt;

    private Long daySumTimes;

    private Long monthSumAmt;

    private Long monthSumTimes;

    private String lastTransDate;

    private String lastTransMonth;

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getTransBigtype() {
		return transBigtype;
	}

	public void setTransBigtype(String transBigtype) {
		this.transBigtype = transBigtype;
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

	public Long getDaySumAmt() {
		return daySumAmt;
	}

	public void setDaySumAmt(Long daySumAmt) {
		this.daySumAmt = daySumAmt;
	}

	public Long getDaySumTimes() {
		return daySumTimes;
	}

	public void setDaySumTimes(Long daySumTimes) {
		this.daySumTimes = daySumTimes;
	}

	public Long getMonthSumAmt() {
		return monthSumAmt;
	}

	public void setMonthSumAmt(Long monthSumAmt) {
		this.monthSumAmt = monthSumAmt;
	}

	public Long getMonthSumTimes() {
		return monthSumTimes;
	}

	public void setMonthSumTimes(Long monthSumTimes) {
		this.monthSumTimes = monthSumTimes;
	}

	public String getLastTransDate() {
		return lastTransDate;
	}

	public void setLastTransDate(String lastTransDate) {
		this.lastTransDate = lastTransDate;
	}

	public String getLastTransMonth() {
		return lastTransMonth;
	}

	public void setLastTransMonth(String lastTransMonth) {
		this.lastTransMonth = lastTransMonth;
	}



}