package cn.com.huateng.payment.service;

import cn.com.huateng.CommonUser;
import cn.com.huateng.payment.model.Goods;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.component.Response;

/**
 * User: 董培基
 * Date: 13-8-21
 * Time: 上午10:19
 */
public interface MallService {
    /**
     * 商城 购买商品入口
     * 下单 如果下单成功(包括获取卡，并且锁定卡 都成功) 则记录卡信息，返回网关对象
     *
     * @param good 商品对象
     * @return 网关对象
     */
    Response<SubmitWebGatePay> saveOrder(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("good") Goods good);

}
