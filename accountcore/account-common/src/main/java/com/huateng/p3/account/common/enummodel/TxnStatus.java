package com.huateng.p3.account.common.enummodel;

public enum TxnStatus {
	
	TXN_LABL_NORMAL("001", "正常"), TXN_LABL_ARREARS("002", "欠费");

	private String txnStatusCode;
	private String txnStatusDesc;
	public String getTxnStatusCode() {
		return txnStatusCode;
	}
	public void setTxnStatusCode(String txnStatusCode) {
		this.txnStatusCode = txnStatusCode;
	}
	public String getTxnStatusDesc() {
		return txnStatusDesc;
	}
	public void setTxnStatusDesc(String txnStatusDesc) {
		this.txnStatusDesc = txnStatusDesc;
	}

	private TxnStatus(String txnStatusCode, String txnStatusDesc) {
		this.txnStatusCode = txnStatusCode;
		this.txnStatusDesc = txnStatusDesc;
	}
	

}
