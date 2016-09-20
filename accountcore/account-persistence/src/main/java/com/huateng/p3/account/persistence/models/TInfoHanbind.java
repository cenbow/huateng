package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TInfoHanbind implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8773003268334568256L;

	private String merCode;

	private String intTxnTm;

	private String productNo;

	private String cardBindId;

	private String cardType;

	private String cardNo;

	private String bindingName;

	private String status;

	private String tacitly;

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getIntTxnTm() {
		return intTxnTm;
	}

	public void setIntTxnTm(String intTxnTm) {
		this.intTxnTm = intTxnTm;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getCardBindId() {
		return cardBindId;
	}

	public void setCardBindId(String cardBindId) {
		this.cardBindId = cardBindId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBindingName() {
		return bindingName;
	}

	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTacitly() {
		return tacitly;
	}

	public void setTacitly(String tacitly) {
		this.tacitly = tacitly;
	}
}