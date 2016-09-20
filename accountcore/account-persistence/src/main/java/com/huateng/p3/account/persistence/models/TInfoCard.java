package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TInfoCard implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7638867612535771181L;

	private String innerCardNo;

    private String issueOrgCode;

    private String outerCardNo;

    private String cardSsn;

    private String cardMediaType;

    private String cardBarnd;

    private Date cardMadeTime;

    private Date soldTime;

    private Short foregift;

    private Short initialAmt;

    private Short balance;

    private String appange;

    private String status;

    private String cityCode;

    private String areaCode;

    private String authCode;

    private Long cardVersion;

    private Date validTime;

    private Long consumeCounter;

    private Long chargeCounter;

    private String tar;

    private Date lastUpdateTime;

    private Date lastTxnTime;

    private String forbiddenTxn;

    private String issuedBatchNo;

    public String getInnerCardNo() {
        return innerCardNo;
    }

    public void setInnerCardNo(String innerCardNo) {
        this.innerCardNo = innerCardNo;
    }

    public String getIssueOrgCode() {
        return issueOrgCode;
    }

    public void setIssueOrgCode(String issueOrgCode) {
        this.issueOrgCode = issueOrgCode;
    }

    public String getOuterCardNo() {
        return outerCardNo;
    }

    public void setOuterCardNo(String outerCardNo) {
        this.outerCardNo = outerCardNo;
    }

    public String getCardSsn() {
        return cardSsn;
    }

    public void setCardSsn(String cardSsn) {
        this.cardSsn = cardSsn;
    }

    public String getCardMediaType() {
        return cardMediaType;
    }

    public void setCardMediaType(String cardMediaType) {
        this.cardMediaType = cardMediaType;
    }

    public String getCardBarnd() {
        return cardBarnd;
    }

    public void setCardBarnd(String cardBarnd) {
        this.cardBarnd = cardBarnd;
    }

    public Date getCardMadeTime() {
        return cardMadeTime;
    }

    public void setCardMadeTime(Date cardMadeTime) {
        this.cardMadeTime = cardMadeTime;
    }

    public Date getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(Date soldTime) {
        this.soldTime = soldTime;
    }

    public Short getForegift() {
        return foregift;
    }

    public void setForegift(Short foregift) {
        this.foregift = foregift;
    }

    public Short getInitialAmt() {
        return initialAmt;
    }

    public void setInitialAmt(Short initialAmt) {
        this.initialAmt = initialAmt;
    }

    public Short getBalance() {
        return balance;
    }

    public void setBalance(Short balance) {
        this.balance = balance;
    }

    public String getAppange() {
        return appange;
    }

    public void setAppange(String appange) {
        this.appange = appange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Long getCardVersion() {
        return cardVersion;
    }

    public void setCardVersion(Long cardVersion) {
        this.cardVersion = cardVersion;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Long getConsumeCounter() {
        return consumeCounter;
    }

    public void setConsumeCounter(Long consumeCounter) {
        this.consumeCounter = consumeCounter;
    }

    public Long getChargeCounter() {
        return chargeCounter;
    }

    public void setChargeCounter(Long chargeCounter) {
        this.chargeCounter = chargeCounter;
    }

    public String getTar() {
        return tar;
    }

    public void setTar(String tar) {
        this.tar = tar;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastTxnTime() {
        return lastTxnTime;
    }

    public void setLastTxnTime(Date lastTxnTime) {
        this.lastTxnTime = lastTxnTime;
    }

    public String getForbiddenTxn() {
        return forbiddenTxn;
    }

    public void setForbiddenTxn(String forbiddenTxn) {
        this.forbiddenTxn = forbiddenTxn;
    }

    public String getIssuedBatchNo() {
        return issuedBatchNo;
    }

    public void setIssuedBatchNo(String issuedBatchNo) {
        this.issuedBatchNo = issuedBatchNo;
    }
}