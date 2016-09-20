/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.model;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public class SpuAttribute implements Serializable {

    private static final long serialVersionUID = -1645514235835546612L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long spuId;

    @Getter
    @Setter
    private Long attributeKeyId;

    @Getter
    @Setter
    private Long attributeValueId;

    @Override
    public boolean equals(Object obj) {
        if(obj == null ||!(obj instanceof SpuAttribute)){
            return false;
        }
        SpuAttribute that = (SpuAttribute)obj;
        return Objects.equal(spuId,that.spuId) && Objects.equal(attributeKeyId, that.attributeKeyId)
                && Objects.equal(attributeValueId, that.attributeValueId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(spuId, attributeValueId, attributeKeyId);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("spuId",spuId)
                .add("attributeKeyId", attributeKeyId)
                .add("attributeValueId", attributeValueId).omitNullValues().toString();
    }
}
