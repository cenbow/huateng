package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum TxnChannel {
    // 交易渠道
	TXN_CHANNEL_CORE("中心渠道", "00"),
	TXN_CHANNEL_PORTAL("网上渠道", "02"),
	TXN_CHANNEL_BusOffice("营业厅渠道", "07"),
	TXN_CHANNEL_MOBILE("手机客户端", "08"),
	TXN_CHANNEL_All("所有渠道", "99");


    @Getter
    private String txnCode;

    @Getter
    private String txnDesc;

    TxnChannel(String txnDesc, String txnCode) {
        this.txnCode = txnCode;
        this.txnDesc = txnDesc;

    }
}
