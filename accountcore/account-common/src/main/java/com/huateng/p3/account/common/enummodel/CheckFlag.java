package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum CheckFlag {
	CHECK_FLAG_UNCHECK("未审核","1"),
	USE_FLAG_CHECKED("已审核","2");
	@Getter
	private String flag;
	@Getter
	private String desc;
	CheckFlag(String desc,String flag)
	{
		this.desc = desc;
		this.flag = flag;
	}
}
