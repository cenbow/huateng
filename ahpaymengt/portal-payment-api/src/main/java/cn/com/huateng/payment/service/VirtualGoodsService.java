package cn.com.huateng.payment.service;

import cn.com.huateng.payment.model.SubmitWebGatePay;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.component.Response;

import java.util.List;

/**
 * 虚拟商品下订单
 * User: 董培基
 * Date: 14-11-27
 * Time: 下午12:46
 */
public interface VirtualGoodsService {

    Response<SubmitWebGatePay> createOrder(@ParamInfo("skuId") String skuId, @ParamInfo("count") Long count, @ParamInfo("requestIp") String requestIp, @ParamInfo("unifyId") String unifyId);
}
