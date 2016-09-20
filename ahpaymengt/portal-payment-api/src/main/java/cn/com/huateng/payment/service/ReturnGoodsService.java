package cn.com.huateng.payment.service;

import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.component.Response;

/**
 * 退货申请
 * User: 董培基
 * Date: 14-12-5
 * Time: 下午1:35
 */
public interface ReturnGoodsService {

    /**
     * 退货申请 根据 虚拟商品表 订单号 发起退货
     * @param orderNo 订单号
     * @param electronicNumber 退货电子单号
     * @param count 数量
     * @return 成功失败
     */
    Response<Boolean> returnGoodsApply(@ParamInfo("orderNo")String orderNo,@ParamInfo("electronicNumber")String electronicNumber,@ParamInfo("refundAmt")Long refundAmt,@ParamInfo("count")Long count);

}
