package com.aixforce.item.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-01
 */
public class AttributeDto {
    @Getter
    @Setter
    private Long attributeKeyId;

    @Getter
    @Setter
    private Integer type;

    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    private Boolean isSku;

    public AttributeDto() {
    }

    public AttributeDto(Long keyId,Integer type,String value,Boolean isSku){
        this.attributeKeyId = keyId;
        this.type = type;
        this.value = value;
        this.isSku = isSku;
    }
}