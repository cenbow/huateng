package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 交易流水类型
 * 
 * @author JamesTang
 */
public enum TxnSeqType {

	TRANS_SEQ_TYPE_NORMAL("1"), // 正常流水
	TRANS_SEQ_TYPE_CANCEL("2"), // 撤销流水
	TRANS_SEQ_TYPE_ROLLBACK("3"), // 冲正流水
	TRANS_SEQ_TYPE_REFUND("4"), // 退货流水
	TRANS_SEQ_TYPE_RECONCILIATION("5"), // 调账流水
	TRANS_SEQ_TYPE_FEE("6");// 手续费流水

	@Getter
	private String seqType;

	TxnSeqType(String txnSeqType) {
		this.seqType = txnSeqType;
	}

}
