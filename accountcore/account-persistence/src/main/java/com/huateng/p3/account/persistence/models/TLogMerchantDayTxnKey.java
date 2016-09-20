package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TLogMerchantDayTxnKey implements Serializable {

	private static final long serialVersionUID = 1337822891245103488L;

	private String businessType;

    private String merchantCode;

    private String txnChannel;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getTxnChannel() {
        return txnChannel;
    }

    public void setTxnChannel(String txnChannel) {
        this.txnChannel = txnChannel;
    }
}