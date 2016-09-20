/**
 * 
 */
package com.huateng.p3.hub.common.constant.enums;

/**
 * 日志类型常量
 * @author cmt
 *
 */
public enum LogType {
	

	/**
	 * 日志
	 */
	LogSystemClear("logSystem.delete","日志-清空"),
	;
	
	String name;
	String value;
	
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}

	LogType(String name,String value){
		this.name=name;
		this.value=value;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public static String getValue(String name){
		LogType [] types=LogType.values();
		for (LogType logTypeConstant : types) {
			if (logTypeConstant.name.equals(name))
				return logTypeConstant.value;
		}
		return "";
	}
}
