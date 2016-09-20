/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.category.model;

import com.aixforce.common.model.Indexable;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class Category implements Indexable{

    public static enum Type{
        FRONTEND(1,"前台类目"),
        BACKEND(2,"后台类目");

        private final int value;
        private final String display;

        private Type(int value, String display) {
            this.value = value;
            this.display = display;
        }

        public int toNumber(){
            return value;
        }

        public static Type fromNumber(int value){
            for (Type type : Type.values()) {
                if(Objects.equal(type.toNumber(),value)){
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString(){
            return display;
        }
    }

    private static final long serialVersionUID = 5820167959035518573L;
    @Getter
    @Setter
    private Long   id;

    @NotBlank(message = "类目名称不能为空")
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer type; //frontend or backend

    @NotNull(message = "parentId不能为空")
    @Getter
    @Setter
    private Long parentId; //if parentId=0,then it's a root

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Category)){
            return false;
        }
        Category that = (Category) obj;
        return Objects.equal(name,that.name)&& Objects.equal(parentId,that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name,parentId);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("name", name)
                .add("type", type).add("parentId",parentId).omitNullValues().toString();
    }
}
