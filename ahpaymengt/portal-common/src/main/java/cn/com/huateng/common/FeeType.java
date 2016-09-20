package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 7/30/13 10:27 AM
 */
public enum FeeType implements BaseEnum{

    MonthTicket("01", "月票"),
    ReChargeCard("02", "充值卡"),
    LABEL("03", "标签"),
    TRANSFER("04","转账"),
    VIRTUALGOODS("05","通用虚拟商品");


    private final String value;

    private final String display;

    private FeeType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static FeeType fromNumber(int number) {
        for (FeeType feeType : FeeType.values()) {
            if (Objects.equal(feeType.value, String.valueOf(number))) {
                return feeType;
            }
        }
        return null;
    }
    
    public static Map<String, String> explainAll() {
		Map<String, String> map =Maps.newHashMap();
		for (FeeType obj : FeeType.values()) {
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
