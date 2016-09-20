package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Desc:
 * Author: huwenjie
 * Date: 2013/8/6
 */
public enum CustomerIdType implements BaseEnum{

	CUSTOMER_ID_TYPE_1("1", "身份证"),
	CUSTOMER_ID_TYPE_2("2", "护照"),
	CUSTOMER_ID_TYPE_3("3", "军官证"),
	CUSTOMER_ID_TYPE_4("4", "士兵证"),
	CUSTOMER_ID_TYPE_5("5", "回乡证"),
	CUSTOMER_ID_TYPE_6("6", "临时身份证"),
	CUSTOMER_ID_TYPE_7("7", "户口簿"),
	CUSTOMER_ID_TYPE_8("8", "警官证"),
	CUSTOMER_ID_TYPE_9("9", "台胞证"),
	CUSTOMER_ID_TYPE_A("A", "营业执照"),
	CUSTOMER_ID_TYPE_X("X", "其他");

 

    private final String value;

    private final String display;

    private CustomerIdType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String explain(String value) {
        for (CustomerIdType obj : CustomerIdType.values()) {
            if (Objects.equal(obj.value, value)) {
                return obj.display;
            }
        }
        return null;
    }
    
    public static Map<String, String> explainAll() {
		Map<String, String> map =Maps.newHashMap();
		for (CustomerIdType obj : CustomerIdType.values()) {
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
