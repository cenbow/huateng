package cn.com.huateng.mall.service;

import cn.com.huateng.mall.model.ConfirmItem;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.component.Response;

/**
 * 订单相关
 *
 * 提供确认订单、生成订单的动作
 *
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-22
 */
public interface OrderService {
    /**
     * 组装供订单确认用的商品对象
     *
     * @param skuId skuId
     * @param count 数量
     * @return 供确认的商品对象
     */
    Response<ConfirmItem> confirm(@ParamInfo("skuId") Long skuId, @ParamInfo("count") Integer count);
}
