package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * User: JamesTang
 * Date: 13-12-11
 * Time: 下午5:24
 */
public enum TxnLabel {

    TXN_LABL_ONLINE("1"), TXN_LABL_OFFLINE("2");

    @Getter
    private String lablCode;

    TxnLabel(String lablCode) {
        this.lablCode = lablCode;
    }


}
