package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.ToString;
@ToString
public class TDictTxnCode implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5327459240010438580L;

	private String txnCode;

    private String transType;

    private String revCode;

    private String cancelCode;

    private String txnName;

    private String printName;

    private String txnFlag;

    private String feeType;

    private Long feeFixAmt;

    private BigDecimal feeRatio;

    private Long feeMin;

    private Long feeMax;

    private String txnAbstract;

    private String dcFlag;

    private String profitDcFlag;

    private String dcFlag2;

    private String profitDcFlag2;

    private String clearingFlag;

    private String allowMap;

    private String defaultAllowMap;

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getRevCode() {
        return revCode;
    }

    public void setRevCode(String revCode) {
        this.revCode = revCode;
    }

    public String getCancelCode() {
        return cancelCode;
    }

    public void setCancelCode(String cancelCode) {
        this.cancelCode = cancelCode;
    }

    public String getTxnName() {
        return txnName;
    }

    public void setTxnName(String txnName) {
        this.txnName = txnName;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getTxnFlag() {
        return txnFlag;
    }

    public void setTxnFlag(String txnFlag) {
        this.txnFlag = txnFlag;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Long getFeeFixAmt() {
        return feeFixAmt;
    }

    public void setFeeFixAmt(Long feeFixAmt) {
        this.feeFixAmt = feeFixAmt;
    }

    public BigDecimal getFeeRatio() {
        return feeRatio;
    }

    public void setFeeRatio(BigDecimal feeRatio) {
        this.feeRatio = feeRatio;
    }

    public Long getFeeMin() {
        return feeMin;
    }

    public void setFeeMin(Long feeMin) {
        this.feeMin = feeMin;
    }

    public Long getFeeMax() {
        return feeMax;
    }

    public void setFeeMax(Long feeMax) {
        this.feeMax = feeMax;
    }

    public String getTxnAbstract() {
        return txnAbstract;
    }

    public void setTxnAbstract(String txnAbstract) {
        this.txnAbstract = txnAbstract;
    }

    public String getDcFlag() {
        return dcFlag;
    }

    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }

    public String getProfitDcFlag() {
        return profitDcFlag;
    }

    public void setProfitDcFlag(String profitDcFlag) {
        this.profitDcFlag = profitDcFlag;
    }

    public String getDcFlag2() {
        return dcFlag2;
    }

    public void setDcFlag2(String dcFlag2) {
        this.dcFlag2 = dcFlag2;
    }

    public String getProfitDcFlag2() {
        return profitDcFlag2;
    }

    public void setProfitDcFlag2(String profitDcFlag2) {
        this.profitDcFlag2 = profitDcFlag2;
    }

    public String getClearingFlag() {
        return clearingFlag;
    }

    public void setClearingFlag(String clearingFlag) {
        this.clearingFlag = clearingFlag;
    }

    public String getAllowMap() {
        return allowMap;
    }

    public void setAllowMap(String allowMap) {
        this.allowMap = allowMap;
    }

    public String getDefaultAllowMap() {
        return defaultAllowMap;
    }

    public void setDefaultAllowMap(String defaultAllowMap) {
        this.defaultAllowMap = defaultAllowMap;
    }
}