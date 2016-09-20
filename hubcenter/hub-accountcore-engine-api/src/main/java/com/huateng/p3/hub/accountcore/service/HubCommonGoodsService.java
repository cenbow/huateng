package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.models.CommonGoodsObject;
import com.huateng.p3.hub.accountcore.models.GoodsStock;

import java.util.List;

/**
 * 通用商品信息接口
 * User: dongpeiji
 * Date: 14-9-24
 * Time: 下午12:05
 */
public interface HubCommonGoodsService {

    /**
     * 获取通用产品库存
     * @return  true 有 false 没有
     */
    public Response<List<GoodsStock>> getCommonProductList(CommonGoodsObject commonGoodsObject);
}
