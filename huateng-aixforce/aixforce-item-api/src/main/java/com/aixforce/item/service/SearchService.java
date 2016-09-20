/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.aixforce.item.dto.FacetSearchResult;
import com.aixforce.item.model.Item;
import com.aixforce.user.base.BaseUser;

import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-10
 */
public interface SearchService {

    /**
     * 全网搜索
     *
     * @param pageNo 起始页码
     * @param size   返回条数
     * @param params 搜索参数
     * @return 搜索结果
     */
    FacetSearchResult facetSearchItem(@ParamInfo("pageNo")int pageNo, @ParamInfo("size")int size, @ParamInfo("params")Map<String, String> params);

    /**
     * 店铺内搜索,用于管理店铺内商品
     *
     * @param baseUser 系统注入的用户
     * @param pageNo   起始页码
     * @param size     返回条数
     * @param params   搜索参数
     * @return 搜索结果
     */
    Paging<Item> searchInShop(@ParamInfo("baseUser")BaseUser baseUser, @ParamInfo("pageNo")int pageNo, @ParamInfo("size")int size, @ParamInfo("params")Map<String, String> params);

    /**
     * 店铺内搜索,用于搜索店铺内已上架的商品,一个用户只有一个店铺
     *
     * @param seller 店铺的卖家,系统注入
     * @param pageNo   起始页码
     * @param size     返回条数
     * @param params   搜索参数
     * @return 搜索结果
     */
    Paging<Item> searchOnShelfItemsInShop(@ParamInfo("seller")BaseUser seller, @ParamInfo("pageNo")int pageNo, @ParamInfo("size")int size, @ParamInfo("params")Map<String, String> params);
}
