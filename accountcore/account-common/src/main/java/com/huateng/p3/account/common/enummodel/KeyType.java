package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-12-3
 */
public enum KeyType {
    CUSTOMER("4","客户号"), UNIFY("3","统一帐号"), ACCOUNT("1","账户号"), CARD("2","卡号"), ORGCODE("5","机构号");
    @Getter
    private final String code;
    
    @Getter
    private final String value;

    KeyType(String code,String value) {
    	this.code = code;
        this.value = value;
    }

    public static KeyType from(String code){
        for (KeyType keyType : KeyType.values()) {
            if(keyType.code == code){
                return keyType;
            }
        }
        return null;
    }
}
