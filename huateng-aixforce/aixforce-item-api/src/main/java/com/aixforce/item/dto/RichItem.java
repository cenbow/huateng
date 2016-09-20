/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.dto;

import com.aixforce.item.model.Item;
import com.aixforce.item.model.Sku;
import lombok.Getter;
import lombok.Setter;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-07
 */
public class RichItem extends Item {
    private static final long serialVersionUID = 3145869110713888943L;
    /**non-persisted properties**/
    @Getter
    @Setter
    private Iterable<Long> categoryIds;

    @Getter
    @Setter
    private Iterable<Long> attributeIds;

    @Getter
    @Setter
    private String sellerName;

    @Getter
    @Setter
    private Iterable<Sku> skus;

}
