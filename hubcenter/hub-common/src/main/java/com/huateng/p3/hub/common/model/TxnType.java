package com.huateng.p3.hub.common.model;

import com.google.common.base.Objects;

import lombok.Getter;

public enum TxnType {
    // 交易大类
    TXN_MGM("TXN_MGM", '0'),
    /**
     * 圈存
     */

    TXN_ENCLOSURE("TXN_ENCLOSURE", '1'),
    /**
     * 充值
     */
    TXN_CHARGE("TXN_CHARGE", '2'),
    /**
     * 消费
     */
    TXN_CONSUME("TXN_CONSUME", '3'),
    /**
     * 转账（出）
     */
    TXN_TRANSFER("TXN_TRANSFER", '4'),
    /**
     * 转账（入）
     */
    TXN_TRANSFER_END("TXN_TRANSFER_END", 'A'),
    /**
     * 预授权
     */
    TXN_PREAUTH("TXN_PREAUTH", '5'),

    /**
     * 预授权完成
     */
    TXN_PREAUTH_END("TXN_PREAUTH_END", 'E'),
    /**
     * 退货
     */
    TXN_REFUND("TXN_REFUND", '6'),
    /**
     * 调账
     */
    TXN_RECONCILIATION("TXN_RECONCILIATION", '7'),

    /**
     * 提现申请
     */
    TXN_CASH("TXN_CASH", '8'),
    /**
     * 运营提现申请
     */
    TXN_CASH_FROCE("TXN_CASH_FROCE", '9'),

    /**
     * 提现完成
     */
    TXN_CASH_END("TXN_CASH_END", 'C'),

    TXN_OTHER("TXN_OTHER", 'X');

    @Getter
    private char txnCode;



    @Getter
    private String txnDesc;

    TxnType(String txnDesc, char txnCode) {
        this.txnCode = txnCode;
        this.txnDesc = txnDesc;

    }
    
    public static TxnType explain(char txnCode) {
        for (TxnType txnType : TxnType.values()) {
            if (Objects.equal(txnCode, txnType.txnCode)) {
                return txnType;
            }
        }
        return null;
    }


}
