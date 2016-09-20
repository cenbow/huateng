/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.model;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class AttributeKey implements Serializable {

    private static final long serialVersionUID = -1308784085096445491L;

    public enum ValueType{
        ENUM_ABLE(1,"可枚举"),
        NOT_ENUM(0,"不可枚举");

        private final int value;

        private final String display;

        private ValueType(int value, String display) {
            this.value = value;
            this.display = display;
        }

        public int toNumber() {
            return value;
        }

        @Override
        public String toString(){
            return display;
        }

        public static ValueType fromNumber(int number){
            for (ValueType valueType : ValueType.values()) {
                if(Objects.equal(valueType.value,number)){
                    return valueType;
                }
            }
            return null;
        }
    }

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    private Integer valueType;//0:不可枚举 1:可枚举

    @Override
    public int hashCode() {
        return Objects.hashCode(name,valueType);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof AttributeKey)){
            return false;
        }
        AttributeKey that = (AttributeKey) obj;
        return Objects.equal(name,that.name)&&Objects.equal(valueType,that.valueType);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("name",name)
               .add("valueType",valueType).toString();
    }
}
