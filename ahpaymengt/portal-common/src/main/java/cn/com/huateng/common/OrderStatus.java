package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * User: dongpeiji
 * Date: 14-9-29
 * Time: 下午3:51
 */
public enum OrderStatus implements BaseEnum {

    ORDER_STATUS_1("1", "已申请"),
    ORDER_STATUS_2("2", "支付成功"),
    ORDER_STATUS_3("3", "支付失败"),
    ORDER_STATUS_4("4", "订单已受理"),
    ORDER_STATUS_5("5", "受理成功"),
    ORDER_STATUS_6("6", "受理失败"),
    ORDER_STATUS_7("7", "退款成功"),
    ORDER_STATUS_8("8", "退款失败"),
    ORDER_STATUS_9("9", "充值成功"),
    ORDER_STATUS_10("10", "充值失败"),
    ORDER_STATUS_11("0", "退货待审核"),  //审核标志使用
    ORDER_STATUS_12("1", "审核已通过"),
    ORDER_STATUS_13("2", "驳回");



    private final String value;

    private final String display;

    private OrderStatus(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String explain(String value) {
        for (OrderStatus obj : OrderStatus.values()) {
            if (Objects.equal(obj.value, value)) {
                return obj.display;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }


    public String getDisplay() {
        return display;
    }

    public static Map<String, String> explainAll() {
        Map<String, String> map = Maps.newHashMap();
        for (OrderStatus obj : OrderStatus.values()) {
            map.put(obj.value,obj.display);
        }
        return map;
    }
}
