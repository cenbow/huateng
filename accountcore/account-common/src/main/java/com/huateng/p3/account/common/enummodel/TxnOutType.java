package com.huateng.p3.account.common.enummodel;

import com.google.common.base.Objects;

import lombok.Getter;

public enum TxnOutType {
	
	//管理类
	OUT_TXN_TYPE_E71008("E71008", "退货外部交易类型"),
	OUT_TXN_TYPE_F8101J("F8101J", "正调外部交易类型"),
	OUT_TXN_TYPE_F8101K("F8101K", "反调外部交易类型"),
	OUT_TXN_TYPE_F0001L("F0001L", "开通理财外部交易类型"),
	OUT_TXN_TYPE_F9101M("F9101M", "资金账户网银提现申请外部交易类型"),
	OUT_TXN_TYPE_F9101N("F9101N", "资金账户网银提现撤销外部交易类型"),
	OUT_TXN_TYPE_F9101O("F9101O", "资金账户网银提现外部交易类型"),
	OUT_TXN_TYPE_F9101P("F9101P", "资金账户网银提现失败外部交易类型"),
	OUT_TXN_TYPE_F11021("F11021", "资金帐户带余额销户(主动)外部交易类型"),
	OUT_TXN_TYPE_F11022("F11022", "支付密码冻结外部交易类型"),
	OUT_TXN_TYPE_F3100K("F3100K", "营销系统返利充值");
	
    @Getter
    private String txnOutType;

    @Getter
    private String txnOutTypeDesc;

    TxnOutType(String txnOutType, String txnOutTypeDesc) {
    	this.txnOutType = txnOutType;
    	this.txnOutTypeDesc = txnOutTypeDesc;
    }
    
    
    public static String explain(String txnOutType) {
        for (TxnOutType txnType : TxnOutType.values()) {
            if (Objects.equal(txnOutType, txnType.txnOutType)) {
                return txnType.txnOutTypeDesc;
            }
        }
        return txnOutType;
    }
}
