/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.category.service.CategoryService;
import com.aixforce.common.utils.BeanMapper;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.dto.RichItem;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.Spu;
import com.aixforce.user.model.User;
import com.aixforce.user.service.AccountService;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-07
 */
@Component
public class RichItems {
    private final CategoryService categoryService;
    private final AttributeService attributeService;
    private final SpuService spuService;
    private final AccountService accountService;

    @Autowired
    RichItems(CategoryService categoryService, AttributeService attributeService,
              SpuService spuService, AccountService accountService) {
        this.categoryService = categoryService;
        this.attributeService = attributeService;
        this.spuService = spuService;
        this.accountService = accountService;
    }

    public RichItem make(Item item){
        RichItem richItem = BeanMapper.map(item,RichItem.class);
        Long spuId = item.getSpuId();
        Spu spu = spuService.findById(spuId);
        Long categoryId = spu.getCategoryId();
        Set<Long> ancestors = categoryService.ancestorsOf(categoryId);
        List<RichAttribute> properties = attributeService.findSpuAttributesBy(spuId);
        richItem.setCategoryIds(ancestors);
        ImmutableSet.Builder<Long> builder = new ImmutableSet.Builder<Long>();
        for (RichAttribute property : properties) {
            builder = builder.add(property.getAttributeValueId());
        }
        richItem.setAttributeIds(builder.build());
        User seller = accountService.findUserById(item.getUserId());
        richItem.setSellerName(seller.getName());
        return richItem;
    }
}
