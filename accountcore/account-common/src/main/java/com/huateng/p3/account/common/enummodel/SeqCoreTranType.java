package com.huateng.p3.account.common.enummodel;

public enum SeqCoreTranType {

	TRANS_SEQ_TYPE_NORMAL("102", "普通交易流水"), TRANS_SEQ_TYPE_CANCEL("103", "撤销交易流水"),

	TRANS_SEQ_TYPE_ROLLBACK("105", "冲正交易流水"), TRANS_SEQ_TYPE_REFUND("106", "退款交易流水"),

	TRANS_SEQ_TYPE_RECONCILIATION("108", "调账交易流水"), TRANS_SEQ_TYPE_FEE("109", "费用交易流水");

	SeqCoreTranType(String trancoreseqtypecode, String trancoreseqtypename) {
		this.trancoreseqtypecode = trancoreseqtypecode;
		this.trancoreseqtypename = trancoreseqtypename;

	}

	private String trancoreseqtypecode;
	private String trancoreseqtypename;

	public String getTrancoreseqtypecode() {
		return trancoreseqtypecode;
	}

	public String getTrancoreseqtypename() {
		return trancoreseqtypename;
	}

}
