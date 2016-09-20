package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

public class TInfoMobileH implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6353036257818028636L;

	private String mobileHCode;

    private String teleCode;

    private String cityName;

    private String carrierName;

    private String provTelecode;

    private String lastUpdateTime;

    public String getMobileHCode() {
        return mobileHCode;
    }

    public void setMobileHCode(String mobileHCode) {
        this.mobileHCode = mobileHCode;
    }

    public String getTeleCode() {
        return teleCode;
    }

    public void setTeleCode(String teleCode) {
        this.teleCode = teleCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getProvTelecode() {
        return provTelecode;
    }

    public void setProvTelecode(String provTelecode) {
        this.provTelecode = provTelecode;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}