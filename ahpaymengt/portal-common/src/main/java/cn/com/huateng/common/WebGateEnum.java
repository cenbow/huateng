package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * User: dongpeiji
 * Date: 14-9-28
 * Time: 下午12:19
 */
public enum WebGateEnum implements BaseEnum{

    WebGate_TRANSCODE_0001("0001", "充值"),
    WebGate_TRANSCODE_0002("0002", "缴费"),
    WebGate_TRANSCODE_0003("0003", "交易退款"),
    WebGate_TRANSCODE_0004("0004", "交易冲正"),
    WebGate_TRANSCODE_0005("0005", "其他消费"),
    WebGate_BUSITYPE_01("0001000001","账户充值"),
    WebGate_BUSITYPE_02("0001","支付"),
    WebGate_BUSITYPE_03("0001000002","手机充值"),
    WebGate_BUSITYPE_04("0001000005","水电煤缴费"),
    WebGate_BUSICODE_01("01", "充值"),
    WebGate_BUSICODE_02("02", "缴费"),
    WebGate_BUSICODE_03("03", "交易退款"),
    WebGate_BUSICODE_04("04", "交易冲正"),
    WebGate_BUSICODE_05("05", "其他消费");

    private final String value;

    private final String display;

    private WebGateEnum(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String explain(String value) {
        for (WebGateEnum obj : WebGateEnum.values()) {
            if (Objects.equal(obj.value, value)) {
                return obj.display;
            }
        }
        return null;
    }
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    public static Map<String, String> explainAll() {
        Map<String, String> map = Maps.newHashMap();
        for (WebGateEnum obj : WebGateEnum.values()) {
            map.put(obj.value,obj.display);
        }
        return map;
    }
}
