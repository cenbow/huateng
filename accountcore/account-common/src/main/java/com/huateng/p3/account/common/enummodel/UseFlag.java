package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum UseFlag {
	USE_FLAG_OPEN("启用","1"),
	USE_FLAG_CLOSE("停用","2");
	@Getter
	private String flag;
	@Getter
	private String desc;
	UseFlag(String desc,String flag)
	{
		this.desc = desc;
		this.flag = flag;
	}
}
