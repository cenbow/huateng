/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.ItemDetail;
import com.aixforce.item.model.Sku;
import com.aixforce.user.base.BaseUser;

import java.util.List;
import java.util.Map;

public interface ItemService {

    Map<String,Object> detail(Long itemId);

    void create(Item item,ItemDetail itemDetail,List<Sku> skus);

    void update(Item item,ItemDetail itemDetail,List<Sku> skus);

    /**
     * 减少库存
     * @param skuId sku id
     * @param itemId item id
     * @param quantity 数量
     */
    void decrementStock(Long skuId, Long itemId, Integer quantity);

    /**
     * 增加库存
     * @param skuId sku id
     * @param itemId item id
     * @param quantity  sku id
     */
    void incrementStock(Long skuId, Long itemId, Integer quantity);

    void bulkUpdateStatus(Long userId,Integer status,List<Long> ids);

    Item findById(@ParamInfo("id")Long id);

    List<Item> findByIds(@ParamInfo("ids")List<Long> ids);

    void delete(Long userId,Long id);

    ItemDetail findDetailBy(@ParamInfo("itemId")Long itemId);

    Map<String,Object> findWithDetailsById(@ParamInfo("itemId")Long itemId);

    Sku findSkuById(@ParamInfo("skuId")Long skuId);

    List<RichAttribute> attributesOf(@ParamInfo("itemId")Long itemId);

    /**
     * 卖家后台商品列表,用于管理店铺内商品
     * @param baseUser 系统注入的用户
     * @param pageNo 起始页码
     * @param size   返回条数
     * @param params 搜索参数
     * @return 商品列表
     */
    Paging<Item> sellerItems(@ParamInfo("baseUser")BaseUser baseUser, @ParamInfo("pageNo")Integer pageNo,
                             @ParamInfo("size")Integer size, @ParamInfo("params")Map<String, String> params);

    /**
     * 全量dump搜索引擎
     */
    void fullDump();

    /**
     * 增量dump搜索引擎
     *
     * @param interval 间隔时间,以分钟计算
     */
    void deltaDump(Integer interval);
}
