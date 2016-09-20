package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

public class RiskLogacnttxnPayment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1530063351459065956L;

	private String recordNo;

    private String accountNo;

    private String transBigtype;

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

    public String gettransBigtype() {
        return transBigtype;
    }

    public void settransBigtype(String transBigtype) {
        this.transBigtype = transBigtype;
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

    public String getlastTransDate() {
        return lastTransDate;
    }

    public void setlastTransDate(String lastTransDate) {
        this.lastTransDate = lastTransDate;
    }

    public String getlastTransMonth() {
        return lastTransMonth;
    }

    public void setlastTransMonth(String lastTransMonth) {
        this.lastTransMonth = lastTransMonth;
    }
}