package com.huateng.p3.account.common.enummodel;

public enum SeqPlatType {

	SEQ_TRANA_ACCOUNT_CODE("2001", "账户交易核心"), SEQ_MANAGER_ACCOUNT_CODE("2002",
			"账户管理核心"), SEQ_ENTERPRISE_CODE("2003", "预存款核心"), SEQ_CARD_CODE(
			"2004", "卡核心"), SEQ_BILL_CODE("2006", "销帐核心");
	private String platcode;
	private String platname;

	public String getPlatcode() {
		return platcode;
	}

	public String getPlatname() {
		return platname;
	}

	SeqPlatType(String platcode, String platname) {
		this.platcode = platcode;
		this.platname = platname;
	}

}
