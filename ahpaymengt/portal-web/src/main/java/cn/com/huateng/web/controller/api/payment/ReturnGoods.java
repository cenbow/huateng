package cn.com.huateng.web.controller.api.payment;

import cn.com.huateng.CommonUser;
import cn.com.huateng.payment.service.ReturnGoodsService;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.user.base.UserUtil;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-12-5
 * Time: 下午2:43
 */
@Controller
@RequestMapping("/api/payment")
public class ReturnGoods {

    private final static Logger log = LoggerFactory.getLogger(ReturnGoods.class);

    @Autowired
    private ReturnGoodsService returnGoodsService;

    @RequestMapping(value = "/returnGoods", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String returnGoods(@RequestParam("orderNo") String orderNo, @RequestParam("count") Long count, @RequestParam("electronicNumber") String electronicNumber, @RequestParam("refundAmt") Long refundAmt) {
        CommonUser user = UserUtil.getCurrentUser();
        Response<Boolean> booleanResponse = returnGoodsService.returnGoodsApply(orderNo, electronicNumber, refundAmt, count);
        if (booleanResponse.isSuccess()) {
            log.info("退货成功 orderNo:{}", orderNo);
            return "000000";
        } else {
            log.error("failed to returnGoods, errorCode={}, unifyId={}",
                    booleanResponse.getErrorCode(), user.getUnifyId());
            throw new JsonResponseException(500, booleanResponse.getErrorMsg());
        }
    }
}
