package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CMT
 * 
 */
public class TInfoAccountBankCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7787680138296111857L;
	private Long recordNo;
	private String customerNo;
	private String offlineAccountNo;
	private String fundAccountNo;
	private String bankCardNo;
	private String bankCode;
	private String bindingType;
	private String bindingFlag;
	private String bindingMehod;
	private Timestamp bindingTime;
	private Timestamp unbindingTime;
	private String bindingAcceptOrgCode;
	private String bingdingAcceptUid;
	private Timestamp bindingAcceptTime;
	private String unbindingAcceptOrgCode;
	private String unbingdingAcceptUid;
	private Timestamp unbindingAcceptTime;

	private String resvFld1;
	private String resvFld2;
	private String resvFld3;
	private String bankName;

	
	
	
	public Long getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Long recordNo) {
		this.recordNo = recordNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBindingType() {
		return bindingType;
	}

	public void setBindingType(String bindingType) {
		this.bindingType = bindingType;
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

	public Timestamp getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(Timestamp bindingTime) {
		this.bindingTime = bindingTime;
	}

	public Timestamp getUnbindingTime() {
		return unbindingTime;
	}

	public void setUnbindingTime(Timestamp unbindingTime) {
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

	public Timestamp getBindingAcceptTime() {
		return bindingAcceptTime;
	}

	public void setBindingAcceptTime(Timestamp bindingAcceptTime) {
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

	public Timestamp getUnbindingAcceptTime() {
		return unbindingAcceptTime;
	}

	public void setUnbindingAcceptTime(Timestamp unbindingAcceptTime) {
		this.unbindingAcceptTime = unbindingAcceptTime;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
