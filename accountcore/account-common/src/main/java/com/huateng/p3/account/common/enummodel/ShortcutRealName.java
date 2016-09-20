package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 绑卡业务类型
 * 
 * @author huwenjie
 * 
 */
public enum ShortcutRealName {
	YES("0", "实名"), NO("1", "不实名");
    @Getter
    private String value;
    @Getter
    private String desc;

    ShortcutRealName(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    @Override
    public String toString() {
        return this.value;
    }
}

