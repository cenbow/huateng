package com.huateng.p3.account.common.enummodel;

public enum SeqPlatBussKind {

	SEQ_TRANA_TYPE("10", "交易"), SEQ_MANAGER_TYPE("20", "管理"), SEQ_ORDER_TYPE(
			"30", "订单"), SEQ_OTHER_TYPE("40", "其他");

	private String seqkingcode;
	private String seqkingname;

	SeqPlatBussKind(String seqkingcode, String seqkingname) {
		this.seqkingcode = seqkingcode;
		this.seqkingname = seqkingname;
	}

	public String getSeqkingcode() {
		return seqkingcode;
	}

	public String getSeqkingname() {
		return seqkingname;
	}

}
