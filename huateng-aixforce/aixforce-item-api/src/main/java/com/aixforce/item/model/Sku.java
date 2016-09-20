package com.aixforce.item.model;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-02-01
 */
public class Sku implements Serializable {
    private static final long serialVersionUID = -2189348280769719400L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long itemId;

    /*@Getter
    @Setter
    private String outerId; //对应外部的skuId,比如京东的skuId*/

    @Getter
    @Setter
    private Integer price;

    @Getter
    @Setter
    private Integer stock;

    @Getter
    @Setter
    private String image;

    @Getter
    @Setter
    private String attributeKey1;

    @Getter
    @Setter
    private String attributeName1;

    @Getter
    @Setter
    private String attributeValue1;

    @Getter
    @Setter
    private String attributeKey2;

    @Getter
    @Setter
    private String attributeName2;

    @Getter
    @Setter
    private String attributeValue2;

    @Override
    public int hashCode() {
        return Objects.hashCode(itemId, attributeName1,attributeValue1, attributeName2, attributeValue2);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null ||!(o instanceof Sku)){
            return false;
        }
        Sku that = (Sku)o;

        return Objects.equal(itemId,that.itemId)&&Objects.equal(attributeName1,that.attributeName1)
                &&Objects.equal(attributeName2,that.attributeName2)
                &&Objects.equal(attributeValue1,that.attributeValue1)&&Objects.equal(attributeValue2,that.attributeValue2);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("itemId",itemId).add("price",price)
                .add("stock", stock).add("image",image).add("attributeName1", attributeName1)
                .add("attributeValue1",attributeValue1).add("attributeName2", attributeName2).add("attributeValue2",attributeValue2)
                .omitNullValues().toString();
    }
}
