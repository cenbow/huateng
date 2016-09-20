package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum ArchivedFlag {
	ARCHIV_FLAG_UNARCHIV("未归档","1"),
	ARCHIV_FLAG_ARCHIVED("已归档","2");
	@Getter
	private String flag;
	@Getter
	private String desc;
	ArchivedFlag(String desc,String flag)
	{
		this.desc = desc;
		this.flag = flag;
	}
}
