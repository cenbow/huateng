package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * User: 董培基
 * Date: 14-11-24
 * Time: 下午3:58
 */
public enum PayType implements BaseEnum {

    SinglePay("0", "单笔支付"),
    MultiPay("1","合并支付");

    private final String value;

    private final String display;

    private PayType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static PayType fromNumber(int number) {
        for (PayType payType : PayType.values()) {
            if (Objects.equal(payType.value, String.valueOf(number))) {
                return payType;
            }
        }
        return null;
    }

    public static Map<String, String> explainAll() {
        Map<String, String> map = Maps.newHashMap();
        for (PayType obj : PayType.values()) {
            map.put(obj.value,obj.display);
        }
        return map;
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
