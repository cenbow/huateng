package com.huateng.p3.account.persistence.models;


import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogAccountManagement implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5332694131633248479L;

	private String transSerialNo;

    private Date transTime;

    private Integer transMonth;

    private String customerNo;

    private String accountNo;

    private String accountType;

    private String innerCardNo;

    private String transType;

    private String transMemo;

    private String beforeStatus;

    private String afterStatus;

    private String areaCode;

    private String cityCode;

    private String orgCode;

    private String acceptChannel;

    private String feeFlag;

    private Long feeAmount;

    private String acceptDate;

    private String acceptTime;

    private String acceptOrgSerialNo;

    private String remark;

    private String inputUid;

    private Date inputTime;

    private String checkUid;

    private Date checkTime;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String resvFld4;

    private String resvFld5;

	public String getTransSerialNo() {
		return transSerialNo;
	}

	public void setTransSerialNo(String transSerialNo) {
		this.transSerialNo = transSerialNo;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public Integer getTransMonth() {
		return transMonth;
	}

	public void setTransMonth(Integer transMonth) {
		this.transMonth = transMonth;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getInnerCardNo() {
		return innerCardNo;
	}

	public void setInnerCardNo(String innerCardNo) {
		this.innerCardNo = innerCardNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransMemo() {
		return transMemo;
	}

	public void setTransMemo(String transMemo) {
		this.transMemo = transMemo;
	}

	public String getBeforeStatus() {
		return beforeStatus;
	}

	public void setBeforeStatus(String beforeStatus) {
		this.beforeStatus = beforeStatus;
	}

	public String getAfterStatus() {
		return afterStatus;
	}

	public void setAfterStatus(String afterStatus) {
		this.afterStatus = afterStatus;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAcceptChannel() {
		return acceptChannel;
	}

	public void setAcceptChannel(String acceptChannel) {
		this.acceptChannel = acceptChannel;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public Long getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getAcceptOrgSerialNo() {
		return acceptOrgSerialNo;
	}

	public void setAcceptOrgSerialNo(String acceptOrgSerialNo) {
		this.acceptOrgSerialNo = acceptOrgSerialNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCheckUid() {
		return checkUid;
	}

	public void setCheckUid(String checkUid) {
		this.checkUid = checkUid;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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

	public String getResvFld4() {
		return resvFld4;
	}

	public void setResvFld4(String resvFld4) {
		this.resvFld4 = resvFld4;
	}

	public String getResvFld5() {
		return resvFld5;
	}

	public void setResvFld5(String resvFld5) {
		this.resvFld5 = resvFld5;
	}

	
    
   }