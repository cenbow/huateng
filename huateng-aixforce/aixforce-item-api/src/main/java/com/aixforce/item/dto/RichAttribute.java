/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.dto;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-13
 */
public class RichAttribute implements Serializable {

    private static final long serialVersionUID = -2198395314076031980L;

    @Getter
    @Setter
    private Long belongId;

    @Getter
    @Setter
    private Long attributeKeyId;

    @Getter
    @Setter
    private String attributeKey;

    @Getter
    @Setter
    private Long attributeValueId;

    @Getter
    @Setter
    private String attributeValue;

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof RichAttribute)){
           return false;
        }
        RichAttribute that = (RichAttribute) obj;
        return Objects.equal(attributeKeyId, that.attributeKeyId)&&Objects.equal(attributeValueId,that.attributeValueId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attributeKeyId, attributeValueId);
    }
}
