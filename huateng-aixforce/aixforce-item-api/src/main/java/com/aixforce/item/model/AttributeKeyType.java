package com.aixforce.item.model;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-02-02
 */
public enum AttributeKeyType {
    SPU(0,"SPU属性"),
    SKU(1,"SKU属性");

    private final int value;

    private final String display;

    private AttributeKeyType(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public int toNumber(){
        return value;
    }

    public static AttributeKeyType fromNumber(int value){
        for (AttributeKeyType attributeKeyType : AttributeKeyType.values()) {
            if(attributeKeyType.value == value){
                return attributeKeyType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "AttributeKeyType{" +
                "value=" + value +
                ", display='" + display + '\'' +
                '}';
    }
}
