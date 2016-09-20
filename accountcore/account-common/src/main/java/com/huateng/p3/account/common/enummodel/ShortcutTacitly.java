package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 绑卡业务类型
 * 
 * @author huwenjie
 * 
 */
public enum ShortcutTacitly {
	YES("Y", "默认卡"), NO("N", "非默认卡");
    @Getter
    private String value;
    @Getter
    private String desc;

    ShortcutTacitly(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    @Override
    public String toString() {
        return this.value;
    }
}

