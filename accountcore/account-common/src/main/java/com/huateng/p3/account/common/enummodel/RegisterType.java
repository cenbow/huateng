package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-12-09
 */
public enum RegisterType {
    // 客户注册类型
    CUSTOMER_REGISTER_TYPE_PHONE("0"),     //手机号
    CUSTOMER_REGISTER_TYPE_SUTONG_CARD("1"), //速通卡账户同步开户
    CUSTOMER_REGISTER_TYPE_EMAIL("2"); //email
    @Getter
    private final String value;

    RegisterType(String value) {
        this.value = value;
    }

    public static RegisterType from(String type){
        for (RegisterType registerType : RegisterType.values()) {
            if(registerType.value == type){
                return registerType;
            }
        }
        return null;
    }
}
