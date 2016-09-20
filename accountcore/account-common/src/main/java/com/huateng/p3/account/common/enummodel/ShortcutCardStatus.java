package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 绑卡业务类型
 * 
 * @author huwenjie
 * 
 */
public enum ShortcutCardStatus {
    NOTUSED("00", "绑定"), 
    INVALID("01", "解绑"), 
    CLOSED("02", "关闭"); 
    @Getter
    private String value;
    @Getter
    private String desc;

    ShortcutCardStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    @Override
    public String toString() {
        return this.value;
    }
}

