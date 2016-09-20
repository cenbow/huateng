package cn.com.huateng.common;

import com.google.common.base.Objects;

/**
 * User: 董培基
 * Date: 13-11-29
 * Time: 上午3:51
 */
public enum MarKetRebateEnum {


    MARKET_REBATE_22("22", "C1100O","131010"),
    MARKET_REBATE_other("other", "C4000P","131010");

    private final String value;

    private final String outTxnType;

    private final String txnType;

    private MarKetRebateEnum(String value, String outTxnType,String txnType) {
        this.value = value;
        this.outTxnType = outTxnType;
        this.txnType = txnType;
    }

    public static String explainOutTxnType(String value) {
        for (MarKetRebateEnum obj : MarKetRebateEnum.values()) {
            if (Objects.equal(obj.value, value)) {
                return obj.outTxnType;
            }
        }
        return null;
    }

    public static String explainTxnType(String value) {
        for (MarKetRebateEnum obj : MarKetRebateEnum.values()) {
            if (Objects.equal(obj.value, value)) {
                return obj.txnType;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }


    public String getOutTxnType() {
        return outTxnType;
    }

    public String getTxnType() {
        return txnType;
    }
}
