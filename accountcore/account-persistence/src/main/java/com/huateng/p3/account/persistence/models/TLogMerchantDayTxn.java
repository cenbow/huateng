package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TLogMerchantDayTxn extends TLogMerchantDayTxnKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -951758195823355278L;

	private String lastTxnDate;

    private Long sumTxnAmt;

    public String getLastTxnDate() {
        return lastTxnDate;
    }

    public void setLastTxnDate(String lastTxnDate) {
        this.lastTxnDate = lastTxnDate;
    }

    public Long getSumTxnAmt() {
        return sumTxnAmt;
    }

    public void setSumTxnAmt(Long sumTxnAmt) {
        this.sumTxnAmt = sumTxnAmt;
    }
}