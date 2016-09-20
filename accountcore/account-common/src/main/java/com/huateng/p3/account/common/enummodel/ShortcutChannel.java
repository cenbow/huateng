package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 绑卡业务类型
 * 
 * @author huwenjie
 * 
 */
public enum ShortcutChannel {
	ONEKEYPAY("01", "一键付"),
	HYBANK("02","翰银"),
	TYACCOUNTBANK("03","天翼账户绑卡"),
	SHORTCUT("04","快捷绑卡"),
	CASHBANK("05","提现绑卡"),
	CREDITCARD("06","信用卡绑卡"),
	OTHERSCARD("07","非本人转账绑卡");
	@Getter
	private String value;
	@Getter
	private String desc;

	ShortcutChannel(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}

