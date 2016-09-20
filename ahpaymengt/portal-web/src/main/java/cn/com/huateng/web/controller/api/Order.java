package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.OrderStatus;
import cn.com.huateng.payment.model.TPortOrderDetail;
import cn.com.huateng.payment.model.VirtualGoods;
import cn.com.huateng.payment.service.PaymentService;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Objects;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;

/**
 * User: dongpeiji
 * Date: 14-10-16
 * Time: 下午12:55
 */
@Controller
@RequestMapping("/api/order")
public class Order {

    private final static Logger log = LoggerFactory.getLogger(Order.class);

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String orderQueryDetail(@RequestParam("feeType") String feeType, @RequestParam("orderNo") String orderNo,
                                   Map<String, Object> context, @RequestParam("orderSeq") String orderSeq,
                                   @RequestParam("orderAmount") Long orderAmount) {
        CommonUser commonUser = UserUtil.getCurrentUser();
        String url = "";
        context.put("_USER_", commonUser);
        Response<TPortOrderDetail> tPortOrderDetail = null;
        Response<TInfoCustomer> tInfoCustomerResponse = userService.loadCustomer(commonUser);
        tPortOrderDetail = paymentService.orderQueryDetail(commonUser, orderNo, feeType);

        if (tPortOrderDetail.isSuccess() && tInfoCustomerResponse.isSuccess()) {
            switch (feeType) {
                case "01":
                    url = "/layout/sutongpay/order/orderDetail/publicFee";
                    break;
                case "02":
                    url = "/layout/sutongpay/order/orderDetail/publicFee";
                    break;
                case "03":
                    url = "/layout/sutongpay/order/orderDetail/publicFee";
                    break;
                case "05":
                    url = "/layout/sutongpay/order/orderDetail/publicFee";
                    break;
                case "1001":
                    url = "/layout/sutongpay/order/orderDetail/accountCharge";
                    break;

            }
            context.put("customer", tInfoCustomerResponse.getResult());
            context.put("orderSeq", orderSeq);
            context.put("_USER_", commonUser);
            context.put("orderdetail", tPortOrderDetail.getResult());
            context.put("orderAmount", orderAmount);
            return url;
        } else {
            String errorCode = tPortOrderDetail.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            log.error("---------订单明细出错start-------------------");
            log.error("orderQueryDetail(orderNo={}) failed, message {}", commonUser.getId(), message);
            log.error("---------订单明细出错end-------------------");
            throw new JsonResponseException(500, message);
        }


    }


}
