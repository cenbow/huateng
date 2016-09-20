/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.model;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class AttributeValue implements Serializable {

    private static final long serialVersionUID = -4655559645832754435L;
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String value;

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof AttributeValue)){
            return false;
        }
        AttributeValue that = (AttributeValue) obj;
        return Objects.equal(value,that.value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("value",value).omitNullValues().toString();
    }
}
