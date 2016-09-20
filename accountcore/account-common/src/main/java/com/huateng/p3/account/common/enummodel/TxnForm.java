package com.huateng.p3.account.common.enummodel;

public enum TxnForm {
	
	TXN_LABL_ONLINE("1", "联机交易标识"), TXN_LABL_OFFLINE("2", "脱机交易标识");

	private String txnformlabel;
	private String txnformcode;

	public String getTxnformlabel() {
		return txnformlabel;
	}

	public String getTxnformcode() {
		return txnformcode;
	}

	TxnForm(String strcode, String label) {
		this.txnformcode = strcode;
		this.txnformlabel = label;
	}

}
