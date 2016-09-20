package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TDictAreaCity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 211639594786930019L;

	private String currentCode;

    private String teleCode;

    private String currentName;

    private String parenetCode;

    private String codeFlag;

    private String mobileHCity;

    public String getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    public String getTeleCode() {
        return teleCode;
    }

    public void setTeleCode(String teleCode) {
        this.teleCode = teleCode;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getParenetCode() {
        return parenetCode;
    }

    public void setParenetCode(String parenetCode) {
        this.parenetCode = parenetCode;
    }

    public String getCodeFlag() {
        return codeFlag;
    }

    public void setCodeFlag(String codeFlag) {
        this.codeFlag = codeFlag;
    }

    public String getMobileHCity() {
        return mobileHCity;
    }

    public void setMobileHCity(String mobileHCity) {
        this.mobileHCity = mobileHCity;
    }
}