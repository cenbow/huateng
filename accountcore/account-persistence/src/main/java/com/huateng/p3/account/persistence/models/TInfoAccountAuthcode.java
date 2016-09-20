package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TInfoAccountAuthcode implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9145932022143554838L;

	private Long recordNo;

    private String customerNo;

    private String accountNo;

    private String merchantCode;

    private String businessType;

    private String authCode;

    private String bizNo;

    private String authFlag;

    private Date validDate;

    private Date authTime;

    private Date unauthTime;

    private String authAcceptOrgCode;

    private String authAcceptUid;

    private Date authAcceptTime;

    private String unauthAcceptOrgCode;

    private String unauthAcceptUid;

    private Date unauthAcceptTime;

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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public Date getUnauthTime() {
        return unauthTime;
    }

    public void setUnauthTime(Date unauthTime) {
        this.unauthTime = unauthTime;
    }

    public String getAuthAcceptOrgCode() {
        return authAcceptOrgCode;
    }

    public void setAuthAcceptOrgCode(String authAcceptOrgCode) {
        this.authAcceptOrgCode = authAcceptOrgCode;
    }

    public String getAuthAcceptUid() {
        return authAcceptUid;
    }

    public void setAuthAcceptUid(String authAcceptUid) {
        this.authAcceptUid = authAcceptUid;
    }

    public Date getAuthAcceptTime() {
        return authAcceptTime;
    }

    public void setAuthAcceptTime(Date authAcceptTime) {
        this.authAcceptTime = authAcceptTime;
    }

    public String getUnauthAcceptOrgCode() {
        return unauthAcceptOrgCode;
    }

    public void setUnauthAcceptOrgCode(String unauthAcceptOrgCode) {
        this.unauthAcceptOrgCode = unauthAcceptOrgCode;
    }

    public String getUnauthAcceptUid() {
        return unauthAcceptUid;
    }

    public void setUnauthAcceptUid(String unauthAcceptUid) {
        this.unauthAcceptUid = unauthAcceptUid;
    }

    public Date getUnauthAcceptTime() {
        return unauthAcceptTime;
    }

    public void setUnauthAcceptTime(Date unauthAcceptTime) {
        this.unauthAcceptTime = unauthAcceptTime;
    }
}