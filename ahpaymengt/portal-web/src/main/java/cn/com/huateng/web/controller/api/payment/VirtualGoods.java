package cn.com.huateng.web.controller.api.payment;

import cn.com.huateng.CommonUser;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import cn.com.huateng.payment.service.VirtualGoodsService;
import cn.com.huateng.web.util.HttpUtil;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.model.redis.Page;
import com.aixforce.user.base.UserUtil;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-11-27
 * Time: 下午12:23
 */
@Controller
@RequestMapping("/api/payment")
public class VirtualGoods {
    private final static Logger log = LoggerFactory.getLogger(VirtualGoods.class);

    @Autowired
    private VirtualGoodsService virtualGoodsService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String createOrder(@RequestParam("skuId") String skuId, @RequestParam("count") Long count, HttpServletRequest request, Map<String, Object> context) {
        CommonUser commonUser = UserUtil.getCurrentUser();
        String requestIp = HttpUtil.getIpAddr(request);
        Response<SubmitWebGatePay> stringResponse = virtualGoodsService.createOrder(skuId, count, requestIp, commonUser.getUnifyId());
        if (stringResponse.isSuccess()) {
            context.put("payInfo", stringResponse.getResult());
            setContextPath("/layout/sutongpay/payment/webgate", context);
            return "/layout/sutongpay/payment/webgate";
        } else {
            String errorCode = stringResponse.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            log.error("createOrder(unifyId={},customerNo={},) failed, errorCode={}", commonUser.getUnifyId(), commonUser.getId(), errorCode);
            throw new JsonResponseException(500, message);
        }
    }

    private void setContextPath(String path, Map<String, Object> context) {
        Page page = new Page();
        page.setPath(path);
        context.put(RenderConstants.PAGE, page);
    }
}
