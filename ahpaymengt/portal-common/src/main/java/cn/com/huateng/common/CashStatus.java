package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Desc:
 * Author: huwenjie
 * Date: 2013/8/13
 */
public enum CashStatus  implements BaseEnum{

	CASH_STATUS_1("1", "已申请"),
	CASH_STATUS_2("2", "已撤销"),
	CASH_STATUS_3("3", "已受理"),
	CASH_STATUS_4("4", "已提现"),
	CASH_STATUS_5("5", "提现失败，已入账"),
	CASH_STATUS_6("6", "受理失败"),
	CASH_STATUS_7("7", "提现失败，无法入账"),
	CASH_STATUS_8("8", "银行已拒绝"),
	CASH_STATUS_9("9", "银行拒绝，撤销失败"),
	CASH_STATUS_A("A", "已提现，扣款失败");
	
 

    private final String value;

    private final String display;

    private CashStatus(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String explain(String value) {
        for (CashStatus obj : CashStatus.values()) {
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
		Map<String, String> map =Maps.newHashMap();
		for (CashStatus obj : CashStatus.values()) {
            map.put(obj.value,obj.display);
        }
        return map;
	}
}
