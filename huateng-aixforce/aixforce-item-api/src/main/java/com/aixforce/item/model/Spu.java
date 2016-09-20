/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.model;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class Spu implements Serializable {

    public static enum STATUS{
        ENABLED(1,"启用"),
        DISABLED(-1,"禁用");

        private final int value;
        private final String display;

        private STATUS(int value, String display) {
            this.value = value;
            this.display = display;
        }

        public int toNumber(){
            return value;
        }

        public static STATUS fromNumber(Integer number){
            for (STATUS status : STATUS.values()) {
                if(Objects.equal(number,status.toNumber())){
                    return status;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return display;
        }
    }

    private static final long serialVersionUID = -2199874156059488084L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long categoryId;

    @Getter
    @Setter
    @NotBlank
    private String name;


    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id)
                .add("categoryId",categoryId).add("name",name).omitNullValues().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Spu)){
            return false;
        }
        Spu that = (Spu) obj;
        return Objects.equal(categoryId,that.categoryId)&& Objects.equal(name,that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryId,name);
    }
}
