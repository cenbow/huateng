package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TInfoAccountCard implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 449322245928449315L;

	private Long recordNo;

    private String customerNo;

    private String offlineAccountNo;

    private String fundAccountNo;

    private String cardNo;

    private String cardType;

    private String bindingFlag;

    private String bindingMehod;

    private Date bindingTime;

    private Date unbindingTime;

    private String bindingAcceptOrgCode;

    private String bingdingAcceptUid;

    private Date bindingAcceptTime;

    private String unbindingAcceptOrgCode;

    private String unbingdingAcceptUid;

    private Date unbindingAcceptTime;

    private String areaCode;

    private String cityCode;

    public Long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Long recordNo) {
        this.recordNo = recordNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getOfflineAccountNo() {
        return offlineAccountNo;
    }

    public void setOfflineAccountNo(String offlineAccountNo) {
        this.offlineAccountNo = offlineAccountNo;
    }

    public String getFundAccountNo() {
        return fundAccountNo;
    }

    public void setFundAccountNo(String fundAccountNo) {
        this.fundAccountNo = fundAccountNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBindingFlag() {
        return bindingFlag;
    }

    public void setBindingFlag(String bindingFlag) {
        this.bindingFlag = bindingFlag;
    }

    public String getBindingMehod() {
        return bindingMehod;
    }

    public void setBindingMehod(String bindingMehod) {
        this.bindingMehod = bindingMehod;
    }

    public Date getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(Date bindingTime) {
        this.bindingTime = bindingTime;
    }

    public Date getUnbindingTime() {
        return unbindingTime;
    }

    public void setUnbindingTime(Date unbindingTime) {
        this.unbindingTime = unbindingTime;
    }

    public String getBindingAcceptOrgCode() {
        return bindingAcceptOrgCode;
    }

    public void setBindingAcceptOrgCode(String bindingAcceptOrgCode) {
        this.bindingAcceptOrgCode = bindingAcceptOrgCode;
    }

    public String getBingdingAcceptUid() {
        return bingdingAcceptUid;
    }

    public void setBingdingAcceptUid(String bingdingAcceptUid) {
        this.bingdingAcceptUid = bingdingAcceptUid;
    }

    public Date getBindingAcceptTime() {
        return bindingAcceptTime;
    }

    public void setBindingAcceptTime(Date bindingAcceptTime) {
        this.bindingAcceptTime = bindingAcceptTime;
    }

    public String getUnbindingAcceptOrgCode() {
        return unbindingAcceptOrgCode;
    }

    public void setUnbindingAcceptOrgCode(String unbindingAcceptOrgCode) {
        this.unbindingAcceptOrgCode = unbindingAcceptOrgCode;
    }

    public String getUnbingdingAcceptUid() {
        return unbingdingAcceptUid;
    }

    public void setUnbingdingAcceptUid(String unbingdingAcceptUid) {
        this.unbingdingAcceptUid = unbingdingAcceptUid;
    }

    public Date getUnbindingAcceptTime() {
        return unbindingAcceptTime;
    }

    public void setUnbindingAcceptTime(Date unbindingAcceptTime) {
        this.unbindingAcceptTime = unbindingAcceptTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}