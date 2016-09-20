package cn.com.huateng.common;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Desc:
 * Author: huwenjie
 * Date: 2013/8/12
 */
public enum TxnChannel implements BaseEnum{
    CHANNEL_02("02", "网上"),  //目前只支持网上 web
	CHANNEL_08("08", "手机客户端"),
    CHANNEL_03("03", "短信"),
    CHANNEL_04("04", "IVR"),
    CHANNEL_07("07", "营业厅"),
    CHANNEL_18("18", "手机收银台"),
    CHANNEL_10("10", "TSM"),
    CHANNEL_01("01", "POS"),
    CHANNEL_99("99", "默认渠道"),
    CHANNEL_06("06", "WAP"),
    CHANNEL_09("09", "代扣"),
    CHANNEL_05("05", "OTA"),
    CHANNEL_12("12", "添益宝"),
    CHANNEL_00("00", "控制台");


    private final String value;

    private final String display;

    private TxnChannel(String value, String display) {
        this.value = value;
        this.display = display;
    }
   
    public static String explain(String value) {
        for (TxnChannel obj : TxnChannel.values()) {
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
		Map<String, String> map =Maps.newHashMap();
		for (TxnChannel obj : TxnChannel.values()) {
            map.put(obj.value,obj.display);
        }
        return map;
	}
}
