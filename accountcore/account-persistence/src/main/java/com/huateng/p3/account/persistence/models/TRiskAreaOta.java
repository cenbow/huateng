package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TRiskAreaOta implements Serializable{

	private static final long serialVersionUID = -5107883712134962033L;
	private String areaCode;
	private String otaFlag;
	private String inputUid;
	private Date inputTime;
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getOtaFlag() {
		return otaFlag;
	}
	public void setOtaFlag(String otaFlag) {
		this.otaFlag = otaFlag;
	}
	public String getInputUid() {
		return inputUid;
	}
	public void setInputUid(String inputUid) {
		this.inputUid = inputUid;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	
	
}
