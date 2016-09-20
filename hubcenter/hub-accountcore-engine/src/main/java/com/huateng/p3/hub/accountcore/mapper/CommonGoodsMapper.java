package com.huateng.p3.hub.accountcore.mapper;


import com.huateng.p3.hub.accountcore.models.CommonGoodsObject;
import com.huateng.p3.hub.accountcore.models.GoodsStock;

import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-12-2
 * Time: 下午4:48
 */
public interface CommonGoodsMapper {
    /**
     * 获取商品 类别 属性 价格 集合
     * @return  商品列表集合
     */
    public List<String> getCommonGoodsObject(CommonGoodsObject commonGoodsObject);

    /**
     * 随机获取商品库存信息
     */
    public List<GoodsStock> randomGetGoodsStock(Map map);

    /**
     * 批量更新库存状态
     * @param stockIds 库存id
     */
    public void patchUpdateGoodsStock(List<String> stockIds);
}
