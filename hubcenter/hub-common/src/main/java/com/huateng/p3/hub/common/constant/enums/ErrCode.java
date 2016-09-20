
package com.huateng.p3.hub.common.constant.enums;


/**
 * @author cmt
 * @version 2014-1-21 下午11:14:05 
 */
public enum ErrCode {

	ERR_CAN_NOT_DUMP("73", "上一次批处理尚未完成，暂时不能dump数据");

	private String code;
	private String value;

	ErrCode(String code, String value) {
		this.code = code;
		this.value = value;
	}

	@Override
	public String toString() {
		return this.code;
	}

	public String getCode() {
		return this.code;
	}
	public String getValue() {
		return this.value;
	}

	public static ErrCode valueByCode(String code) {
		for (ErrCode enu : ErrCode.values()) {
			if (enu.toString().equals(code)) {
				return enu;
			}
		}
		return null;
	}

}
