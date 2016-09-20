package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogAccountCardTxnsum extends TLogAccountCardTxnsumKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1902181086253950955L;

	private Date lastTxnTime;

    private String lastTxnDate;

    private Long daySumConsAmt;

    private Integer daySumConsCnt;

    private Long daySumOutAmt;

    private Integer daySumOutCnt;

    private Long daySumInAmt;

    private Integer daySumInCnt;

    private Long daySumCashAmt;

    private Integer daySumCashCnt;

    private Long daySumChgAmt;

    private Integer daySumChgCnt;

    private Long monthSumConsAmt;

    private Long monthSumOutAmt;

    private Long monthSumInAmt;

    private Long monthSumCashAmt;

    private Long monthSumChgAmt;

    private Long monthSumConsCnt;

    private Long monthSumOutCnt;

    private Long monthSumInCnt;

    private Long monthSumCashCnt;

    private Long monthSumChgCnt;

    private Long rsvdAmt1;

    private Long rsvdAmt2;

    private String rsvdText1;

    private String rsvdText2;

    public Date getLastTxnTime() {
        return lastTxnTime;
    }

    public void setLastTxnTime(Date lastTxnTime) {
        this.lastTxnTime = lastTxnTime;
    }

    public String getLastTxnDate() {
        return lastTxnDate;
    }

    public void setLastTxnDate(String lastTxnDate) {
        this.lastTxnDate = lastTxnDate;
    }

    public Long getDaySumConsAmt() {
        return daySumConsAmt;
    }

    public void setDaySumConsAmt(Long daySumConsAmt) {
        this.daySumConsAmt = daySumConsAmt;
    }

    public Integer getDaySumConsCnt() {
        return daySumConsCnt;
    }

    public void setDaySumConsCnt(Integer daySumConsCnt) {
        this.daySumConsCnt = daySumConsCnt;
    }

    public Long getDaySumOutAmt() {
        return daySumOutAmt;
    }

    public void setDaySumOutAmt(Long daySumOutAmt) {
        this.daySumOutAmt = daySumOutAmt;
    }

    public Integer getDaySumOutCnt() {
        return daySumOutCnt;
    }

    public void setDaySumOutCnt(Integer daySumOutCnt) {
        this.daySumOutCnt = daySumOutCnt;
    }

    public Long getDaySumInAmt() {
        return daySumInAmt;
    }

    public void setDaySumInAmt(Long daySumInAmt) {
        this.daySumInAmt = daySumInAmt;
    }

    public Integer getDaySumInCnt() {
        return daySumInCnt;
    }

    public void setDaySumInCnt(Integer daySumInCnt) {
        this.daySumInCnt = daySumInCnt;
    }

    public Long getDaySumCashAmt() {
        return daySumCashAmt;
    }

    public void setDaySumCashAmt(Long daySumCashAmt) {
        this.daySumCashAmt = daySumCashAmt;
    }

    public Integer getDaySumCashCnt() {
        return daySumCashCnt;
    }

    public void setDaySumCashCnt(Integer daySumCashCnt) {
        this.daySumCashCnt = daySumCashCnt;
    }

    public Long getDaySumChgAmt() {
        return daySumChgAmt;
    }

    public void setDaySumChgAmt(Long daySumChgAmt) {
        this.daySumChgAmt = daySumChgAmt;
    }

    public Integer getDaySumChgCnt() {
        return daySumChgCnt;
    }

    public void setDaySumChgCnt(Integer daySumChgCnt) {
        this.daySumChgCnt = daySumChgCnt;
    }

    public Long getMonthSumConsAmt() {
        return monthSumConsAmt;
    }

    public void setMonthSumConsAmt(Long monthSumConsAmt) {
        this.monthSumConsAmt = monthSumConsAmt;
    }

    public Long getMonthSumOutAmt() {
        return monthSumOutAmt;
    }

    public void setMonthSumOutAmt(Long monthSumOutAmt) {
        this.monthSumOutAmt = monthSumOutAmt;
    }

    public Long getMonthSumInAmt() {
        return monthSumInAmt;
    }

    public void setMonthSumInAmt(Long monthSumInAmt) {
        this.monthSumInAmt = monthSumInAmt;
    }

    public Long getMonthSumCashAmt() {
        return monthSumCashAmt;
    }

    public void setMonthSumCashAmt(Long monthSumCashAmt) {
        this.monthSumCashAmt = monthSumCashAmt;
    }

    public Long getMonthSumChgAmt() {
        return monthSumChgAmt;
    }

    public void setMonthSumChgAmt(Long monthSumChgAmt) {
        this.monthSumChgAmt = monthSumChgAmt;
    }

    public Long getMonthSumConsCnt() {
        return monthSumConsCnt;
    }

    public void setMonthSumConsCnt(Long monthSumConsCnt) {
        this.monthSumConsCnt = monthSumConsCnt;
    }

    public Long getMonthSumOutCnt() {
        return monthSumOutCnt;
    }

    public void setMonthSumOutCnt(Long monthSumOutCnt) {
        this.monthSumOutCnt = monthSumOutCnt;
    }

    public Long getMonthSumInCnt() {
        return monthSumInCnt;
    }

    public void setMonthSumInCnt(Long monthSumInCnt) {
        this.monthSumInCnt = monthSumInCnt;
    }

    public Long getMonthSumCashCnt() {
        return monthSumCashCnt;
    }

    public void setMonthSumCashCnt(Long monthSumCashCnt) {
        this.monthSumCashCnt = monthSumCashCnt;
    }

    public Long getMonthSumChgCnt() {
        return monthSumChgCnt;
    }

    public void setMonthSumChgCnt(Long monthSumChgCnt) {
        this.monthSumChgCnt = monthSumChgCnt;
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

    public String getRsvdText1() {
        return rsvdText1;
    }

    public void setRsvdText1(String rsvdText1) {
        this.rsvdText1 = rsvdText1;
    }

    public String getRsvdText2() {
        return rsvdText2;
    }

    public void setRsvdText2(String rsvdText2) {
        this.rsvdText2 = rsvdText2;
    }
}