package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

import com.google.common.base.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public enum ProductProperty {
    PERMNENT_NO_PAUSE("M0001","交费助手"),
    OFFLINE_SHORTCUT("H0002","线下快捷"),
    FINANCE_ACCOUNT("B0001","添益宝"),
    PUBLIC_PLATFORM("Y0001","易信公众平台"),
    OFFLINE_ACCOUNT("H0001","线下号码支付");

    @Getter
    private final String code;

    @Getter
    private final String name;

    ProductProperty(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public static String explain(String code) {
        for (ProductProperty productProperty : ProductProperty.values()) {
            if (Objects.equal(code, productProperty.code)) {
                return productProperty.name;
            }
        }
        return code;
    }
}
