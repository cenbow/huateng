package cn.com.huateng.payment.service;

import cn.com.huateng.payment.model.SubmitWebGatePay;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.component.Response;

/**
 * 网关 支付
 * User: 董培基
 * Date: 13-8-1
 * Time: 上午11:02
 */
public interface WebGatePayService {

    /**
     * 账户充值 网关
     *
     * @return 调用网关支付对象
     */
    Response<SubmitWebGatePay> accountRecharge(@ParamInfo("submitWebGetPay") SubmitWebGatePay submitWebGetPay);
    /**
     *  网关支付  保存订单
     *
     * @param submitWebGetPay    网关业务对象
     * @return      如果下单成功，则返回msg 缴费成功 and flag  0
     */
    Response<SubmitWebGatePay> orderPay(@ParamInfo("submitWebGetPay") SubmitWebGatePay submitWebGetPay);

    /**
     *  回调订单 1 效验mac 修改订单信息
     *
     * @param submitWebGetPay 网关业务对象
     * @return 返回成功失败
     */
    Response<Boolean> webGateBack(@ParamInfo("submitWebGetPay") SubmitWebGatePay submitWebGetPay);

      /**
     * 账户充值 1 效验mac  2 修改订单状态
     *
     * @param submitWebGetPay 网关 支付对象
     * @return 成功 失败
     */
    Response<Boolean> webGateAccountRechargeBack(@ParamInfo("submitWebGetPay") SubmitWebGatePay submitWebGetPay);
}