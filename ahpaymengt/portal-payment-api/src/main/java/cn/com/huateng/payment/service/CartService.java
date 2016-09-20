package cn.com.huateng.payment.service;

import cn.com.huateng.CommonUser;
import cn.com.huateng.payment.model.Cart;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.component.Response;

import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-11-14
 * Time: 下午3:08
 */
public interface CartService {

    /**
     * 购物车列表 内容
     * @param commonUser  用户
     * @return  购物车列表
     */
    Response<Paging<Map<String,Object>>> cartList(@ParamInfo("commonUser")CommonUser commonUser,@ParamInfo("startIndex")Integer startIndex, @ParamInfo("pageSize") Integer pageSize,@ParamInfo("ids")String ids);

    /**
     * 通过购物车序号获取购物车列信息
     * @param cartNo  购物车序号
     * @return  购物车列信息
     */
    Response<Cart> getCartByCartNo(@ParamInfo("cartNo")Long cartNo);
    /**
     * 添加 购物车
     * @param cart 购物车信息
     * @return 成功失败
     */
    Response<String> addCart(@ParamInfo("cart")Cart cart);

    /**
     * 修改 购物车
     * @param cart 购物车信息
     * @return 成功失败
     */
    Response<String> updateCart(@ParamInfo("cart")Cart cart);

    /**
     * 通过购物车序号删除购物车列信息
     * @param cartNo  购物车序号
     * @return  成功或者失败
     */
    Response<String> deleteCart(@ParamInfo("cartNo")Long cartNo);

    /**
     * 通过购物车序号批量删除购物车列信息
     * @param lists  购物车序号列表
     * @return  成功或者失败
     */
    Response<String> patchDeleteCart(@ParamInfo("lists")List<Long> lists);

    /**
     *创建 购物车订单
     * @param lists  购物车序号
     * @return  订单号
     */
    Response<SubmitWebGatePay> createOrder(@ParamInfo("lists")List<Long> lists,@ParamInfo("requestIp")String requestIp,@ParamInfo("unifyId")String unifyId);

}
