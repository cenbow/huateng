package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

public class TBatCutCtl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4384460051398186026L;

	private Short globalIdx;

    private String lastDate;

    private String lastClearingId;

    private String currDate;

    private String currClearingId;

    private String currMon;

    private String currYear;

    private String cutFlag;

    private String orderCutStat;

    private Date orderCutTime;

    private String dayCutStat;

    private Date dayCutTime;

    private String batFlag;

    private Short batStat;

    private String monFlag;

    private String yearFlag;

    private String newFlag;

    private String dayBatStep;

    private String monBatStep;

    private String yearBatStep;

    private Short cutNodNum;

    private Short cutInterval;

    private String rsvdFld1;

    private String rsvdFld2;

    public Short getGlobalIdx() {
        return globalIdx;
    }

    public void setGlobalIdx(Short globalIdx) {
        this.globalIdx = globalIdx;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastClearingId() {
        return lastClearingId;
    }

    public void setLastClearingId(String lastClearingId) {
        this.lastClearingId = lastClearingId;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getCurrClearingId() {
        return currClearingId;
    }

    public void setCurrClearingId(String currClearingId) {
        this.currClearingId = currClearingId;
    }

    public String getCurrMon() {
        return currMon;
    }

    public void setCurrMon(String currMon) {
        this.currMon = currMon;
    }

    public String getCurrYear() {
        return currYear;
    }

    public void setCurrYear(String currYear) {
        this.currYear = currYear;
    }

    public String getCutFlag() {
        return cutFlag;
    }

    public void setCutFlag(String cutFlag) {
        this.cutFlag = cutFlag;
    }

    public String getOrderCutStat() {
        return orderCutStat;
    }

    public void setOrderCutStat(String orderCutStat) {
        this.orderCutStat = orderCutStat;
    }

    public Date getOrderCutTime() {
        return orderCutTime;
    }

    public void setOrderCutTime(Date orderCutTime) {
        this.orderCutTime = orderCutTime;
    }

    public String getDayCutStat() {
        return dayCutStat;
    }

    public void setDayCutStat(String dayCutStat) {
        this.dayCutStat = dayCutStat;
    }

    public Date getDayCutTime() {
        return dayCutTime;
    }

    public void setDayCutTime(Date dayCutTime) {
        this.dayCutTime = dayCutTime;
    }

    public String getBatFlag() {
        return batFlag;
    }

    public void setBatFlag(String batFlag) {
        this.batFlag = batFlag;
    }

    public Short getBatStat() {
        return batStat;
    }

    public void setBatStat(Short batStat) {
        this.batStat = batStat;
    }

    public String getMonFlag() {
        return monFlag;
    }

    public void setMonFlag(String monFlag) {
        this.monFlag = monFlag;
    }

    public String getYearFlag() {
        return yearFlag;
    }

    public void setYearFlag(String yearFlag) {
        this.yearFlag = yearFlag;
    }

    public String getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(String newFlag) {
        this.newFlag = newFlag;
    }

    public String getDayBatStep() {
        return dayBatStep;
    }

    public void setDayBatStep(String dayBatStep) {
        this.dayBatStep = dayBatStep;
    }

    public String getMonBatStep() {
        return monBatStep;
    }

    public void setMonBatStep(String monBatStep) {
        this.monBatStep = monBatStep;
    }

    public String getYearBatStep() {
        return yearBatStep;
    }

    public void setYearBatStep(String yearBatStep) {
        this.yearBatStep = yearBatStep;
    }

    public Short getCutNodNum() {
        return cutNodNum;
    }

    public void setCutNodNum(Short cutNodNum) {
        this.cutNodNum = cutNodNum;
    }

    public Short getCutInterval() {
        return cutInterval;
    }

    public void setCutInterval(Short cutInterval) {
        this.cutInterval = cutInterval;
    }

    public String getRsvdFld1() {
        return rsvdFld1;
    }

    public void setRsvdFld1(String rsvdFld1) {
        this.rsvdFld1 = rsvdFld1;
    }

    public String getRsvdFld2() {
        return rsvdFld2;
    }

    public void setRsvdFld2(String rsvdFld2) {
        this.rsvdFld2 = rsvdFld2;
    }
}