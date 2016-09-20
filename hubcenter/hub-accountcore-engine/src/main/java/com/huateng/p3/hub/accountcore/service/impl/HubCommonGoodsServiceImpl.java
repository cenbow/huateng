package com.huateng.p3.hub.accountcore.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.mapper.CommonGoodsMapper;
import com.huateng.p3.hub.accountcore.models.CommonGoodsObject;
import com.huateng.p3.hub.accountcore.models.GoodsStock;
import com.huateng.p3.hub.accountcore.service.HubCommonGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-12-2
 * Time: 下午4:42
 */
@Slf4j
@Service
public class HubCommonGoodsServiceImpl implements HubCommonGoodsService {

    @Autowired
    private CommonGoodsMapper commonGoodsMapper;

    @Override
    public Response<List<GoodsStock>> getCommonProductList(CommonGoodsObject commonGoodsObject) {
        if (null == commonGoodsObject || Strings.isNullOrEmpty(commonGoodsObject.getGoodsName())
                || Strings.isNullOrEmpty(commonGoodsObject.getSkuName1()) || Strings.isNullOrEmpty(commonGoodsObject.getSkuAttributeName1())
                || null == commonGoodsObject.getPrice()) {
            log.error("非空对象参数 {}", commonGoodsObject);
            return new Response<List<GoodsStock>>("必选参数不能为空");
        }
        List<String> commonGoodsList = commonGoodsMapper.getCommonGoodsObject(commonGoodsObject);
        if (commonGoodsList.size() == 1) {  //只有一条记录才是正确的
            //获取并锁定库存
            List<GoodsStock> goodsStocks = findGoodsStockForUpdate(ImmutableMap.of("relationId", commonGoodsList.get(0), "number", commonGoodsObject.getNumber()));
            return new Response<List<GoodsStock>>(goodsStocks);
        } else {
            return new Response<List<GoodsStock>>("商品不存在或者不唯一");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private List<GoodsStock> findGoodsStockForUpdate(Map map) {
        List<GoodsStock> goodsStocks = commonGoodsMapper.randomGetGoodsStock(map);
        List<String> stockIds = Lists.newArrayList();
        for (GoodsStock goodsStock : goodsStocks) {
            stockIds.add(goodsStock.getStockId());
        }
        commonGoodsMapper.patchUpdateGoodsStock(stockIds);
        return goodsStocks;
    }
}
