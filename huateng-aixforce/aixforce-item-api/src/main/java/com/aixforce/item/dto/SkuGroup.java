package com.aixforce.item.dto;

import com.aixforce.item.model.Sku;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-22
 */
public class SkuGroup implements Serializable {
    private static final long serialVersionUID = -675328626652187464L;

    @Getter
    private final Multimap<String,Map<String,String>> attributes;

    public SkuGroup(List<Sku> skus) {
        attributes = HashMultimap.create(2, skus.size());
        for (Sku sku : skus) {
            String attributeKey1 = sku.getAttributeKey1();
            if(!Strings.isNullOrEmpty(attributeKey1)){
                attributes.put(attributeKey1, ImmutableMap.<String, String>of(sku.getAttributeName1(),sku.getAttributeValue1()));
            }

            String attributeKey2 = sku.getAttributeKey2();
            if(!Strings.isNullOrEmpty(attributeKey2)){
                attributes.put(attributeKey2,ImmutableMap.<String, String>of(sku.getAttributeName2(),sku.getAttributeValue2()));
            }
        }
    }

}
