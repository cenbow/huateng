package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TLogCashApplyKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3452101459028669142L;

	private String txnDate;

    private String txnSeqNo;

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnSeqNo() {
        return txnSeqNo;
    }

    public void setTxnSeqNo(String txnSeqNo) {
        this.txnSeqNo = txnSeqNo;
    }
}