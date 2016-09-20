package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TInfoBankcard implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2510188720553703052L;

	private String bankCardNo;

    private String innerCardNo;

    private String bankAccountNo;

    private String status;

    private String identityNo;

    private String realName;

    private String identityType;

    private String bankCode;

    private String openBankId;

    private String province;

    private String city;

    private String subbank;

    private String creditlogo;

    private String pubFlag;

    private Long cardVersion;

    private Date cardValidTime;

    private Date createTime;

    private Date lastUpdateTime;

    private String digest;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String cardType;

    private String singid;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getInnerCardNo() {
        return innerCardNo;
    }

    public void setInnerCardNo(String innerCardNo) {
        this.innerCardNo = innerCardNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getOpenBankId() {
        return openBankId;
    }

    public void setOpenBankId(String openBankId) {
        this.openBankId = openBankId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSubbank() {
        return subbank;
    }

    public void setSubbank(String subbank) {
        this.subbank = subbank;
    }

    public String getCreditlogo() {
        return creditlogo;
    }

    public void setCreditlogo(String creditlogo) {
        this.creditlogo = creditlogo;
    }

    public String getPubFlag() {
        return pubFlag;
    }

    public void setPubFlag(String pubFlag) {
        this.pubFlag = pubFlag;
    }

    public Long getCardVersion() {
        return cardVersion;
    }

    public void setCardVersion(Long cardVersion) {
        this.cardVersion = cardVersion;
    }

    public Date getCardValidTime() {
        return cardValidTime;
    }

    public void setCardValidTime(Date cardValidTime) {
        this.cardValidTime = cardValidTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
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

    public String getResvFld3() {
        return resvFld3;
    }

    public void setResvFld3(String resvFld3) {
        this.resvFld3 = resvFld3;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getSingid() {
        return singid;
    }

    public void setSingid(String singid) {
        this.singid = singid;
    }
}