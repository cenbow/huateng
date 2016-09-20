package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Desc:
 * Author: huwenjie
 * Date: 2013/8/12
 */
public enum RegisterType implements BaseEnum{

    CUSTOMER_REGISTER_TYPE_PHONE("0","手机号"),     //手机号
    CUSTOMER_REGISTER_TYPE_SUTONG_CARD("1","速通卡账户同步开户"), //速通卡账户同步开户
    CUSTOMER_REGISTER_TYPE_EMAIL("2","email"); //email
 

    private final String value;

    private final String display;

    private RegisterType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String explain(String value) {
        for (RegisterType obj : RegisterType.values()) {
            if (Objects.equal(obj.value, value)) {
                return obj.display;
            }
        }
        return null;
    }
    
    public static Map<String, String> explainAll() {
		Map<String, String> map =Maps.newHashMap();
		for (RegisterType obj : RegisterType.values()) {
            map.put(obj.value,obj.display);
        }
        return map;
	}

    public String getValue() {
        return value;
    }

   
    public String getDisplay() {
        return display;
    }
}
