package com.huateng.p3.account.persistence.models;


import java.io.Serializable;

import lombok.ToString;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
@ToString
public class TLogAccountTxnPayment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6553265295184052648L;

	private String accountNo;

    private String txnType;

    private Long daySumAmt;

    private Integer daySumTimes;

    private String lastTxnDate;

    private Long monthSumAmt;

    private Integer monthSumTimes;

    private String lastTxnMonth;

    private Integer rsvdNum1;

    private Long rsvdAmt1;

    private Integer rsvdNum2;

    private Long rsvdAmt2;

    private String txnChannel;

    public TLogAccountTxnPayment(String accountNo, String txnType, String txnChannel) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");//自定义日期格式
        this.accountNo = accountNo;
        this.txnType = txnType;
        this.txnChannel = txnChannel;
        this.daySumAmt = 0L;
        this.daySumTimes = 0;
        this.lastTxnDate =   DateTime.now().toString(fmt); //使用自定义的日期格式化当期日期     DateUtil.getDateyyyyMMdd();
        this.monthSumAmt = 0L;
        this.monthSumTimes = 0;
        fmt = DateTimeFormat.forPattern("yyyyMM");//自定义日期格式
        this.lastTxnMonth =  DateTime.now().toString(fmt);
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public Long getDaySumAmt() {
        return daySumAmt;
    }

    public void setDaySumAmt(Long daySumAmt) {
        this.daySumAmt = daySumAmt;
    }

    public Integer getDaySumTimes() {
        return daySumTimes;
    }

    public void setDaySumTimes(Integer daySumTimes) {
        this.daySumTimes = daySumTimes;
    }

    public String getLastTxnDate() {
        return lastTxnDate;
    }

    public void setLastTxnDate(String lastTxnDate) {
        this.lastTxnDate = lastTxnDate;
    }

    public Long getMonthSumAmt() {
        return monthSumAmt;
    }

    public void setMonthSumAmt(Long monthSumAmt) {
        this.monthSumAmt = monthSumAmt;
    }

    public Integer getMonthSumTimes() {
        return monthSumTimes;
    }

    public void setMonthSumTimes(Integer monthSumTimes) {
        this.monthSumTimes = monthSumTimes;
    }

    public String getLastTxnMonth() {
        return lastTxnMonth;
    }

    public void setLastTxnMonth(String lastTxnMonth) {
        this.lastTxnMonth = lastTxnMonth;
    }

    public Integer getRsvdNum1() {
        return rsvdNum1;
    }

    public void setRsvdNum1(Integer rsvdNum1) {
        this.rsvdNum1 = rsvdNum1;
    }

    public Long getRsvdAmt1() {
        return rsvdAmt1;
    }

    public void setRsvdAmt1(Long rsvdAmt1) {
        this.rsvdAmt1 = rsvdAmt1;
    }

    public Integer getRsvdNum2() {
        return rsvdNum2;
    }

    public void setRsvdNum2(Integer rsvdNum2) {
        this.rsvdNum2 = rsvdNum2;
    }

    public Long getRsvdAmt2() {
        return rsvdAmt2;
    }

    public void setRsvdAmt2(Long rsvdAmt2) {
        this.rsvdAmt2 = rsvdAmt2;
    }

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
    }
}