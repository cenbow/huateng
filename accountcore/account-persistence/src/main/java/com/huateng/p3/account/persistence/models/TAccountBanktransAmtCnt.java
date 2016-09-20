package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TAccountBanktransAmtCnt implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8451358712543481856L;

	private String accountNo;

    private String customerNo;

    private String rule;

    private Long banktransMaxNum;

    private Long banktransMaxAmtSum;

    private Long monthBanktransMaxAmt;

    private Long monthBanktransMaxCnt;

    private Long yearBanktransMaxAmt;

    private Long yearBanktransMaxCnt;

    private String resvFld1;

    private String resvFld2;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Long getBanktransMaxNum() {
        return banktransMaxNum;
    }

    public void setBanktransMaxNum(Long banktransMaxNum) {
        this.banktransMaxNum = banktransMaxNum;
    }

    public Long getBanktransMaxAmtSum() {
        return banktransMaxAmtSum;
    }

    public void setBanktransMaxAmtSum(Long banktransMaxAmtSum) {
        this.banktransMaxAmtSum = banktransMaxAmtSum;
    }

    public Long getMonthBanktransMaxAmt() {
        return monthBanktransMaxAmt;
    }

    public void setMonthBanktransMaxAmt(Long monthBanktransMaxAmt) {
        this.monthBanktransMaxAmt = monthBanktransMaxAmt;
    }

    public Long getMonthBanktransMaxCnt() {
        return monthBanktransMaxCnt;
    }

    public void setMonthBanktransMaxCnt(Long monthBanktransMaxCnt) {
        this.monthBanktransMaxCnt = monthBanktransMaxCnt;
    }

    public Long getYearBanktransMaxAmt() {
        return yearBanktransMaxAmt;
    }

    public void setYearBanktransMaxAmt(Long yearBanktransMaxAmt) {
        this.yearBanktransMaxAmt = yearBanktransMaxAmt;
    }

    public Long getYearBanktransMaxCnt() {
        return yearBanktransMaxCnt;
    }

    public void setYearBanktransMaxCnt(Long yearBanktransMaxCnt) {
        this.yearBanktransMaxCnt = yearBanktransMaxCnt;
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

}