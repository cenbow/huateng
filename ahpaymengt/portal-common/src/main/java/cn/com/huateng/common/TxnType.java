package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * User: dongpeiji
 * Date: 14-9-29
 * Time: 下午3:46
 */
public enum TxnType implements BaseEnum{
    RECHARGE("1001", "账户充值"),
    PAY("1002", "账户支付");

    private final String value;

    private final String display;

    private TxnType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static TxnType fromNumber(int number) {
        for (TxnType txnType : TxnType.values()) {
            if (Objects.equal(txnType.value, String.valueOf(number))) {
                return txnType;
            }
        }
        return null;
    }

    public static Map<String, String> explainAll() {
        Map<String, String> map = Maps.newHashMap();
        for (TxnType obj : TxnType.values()) {
            map.put(obj.value,obj.display);
        }
        return map;
    }

    public String getTxnCode() {
        return value;
    }

    @Override
    public String toString() {
        return display;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDisplay() {
        return display;
    }
}
