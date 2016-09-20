package cn.com.huateng.web.controller.mall;

import cn.com.huateng.CommonUser;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import cn.com.huateng.payment.service.CartService;
import cn.com.huateng.web.util.HttpUtil;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.model.redis.Page;
import com.aixforce.user.base.UserUtil;
import com.aixforce.web.misc.MessageSources;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-11-18
 * Time: 下午2:07
 */
@Controller
@RequestMapping("/api/cart")
public class Cart {
    private final static Logger log = LoggerFactory.getLogger(Cart.class);

    private final Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();

    @Autowired
    private MessageSources messageSources;

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemService itemService;


    @RequestMapping(value = "/addCart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addCart(@RequestParam("skuId") Long skuId, @RequestParam("quantity") Integer quantity) {
        try {
            CommonUser commonUser = UserUtil.getCurrentUser();
            Sku sku = itemService.findSkuById(skuId);
            Item item = itemService.findById(sku.getItemId());
            cn.com.huateng.payment.model.Cart cart = new cn.com.huateng.payment.model.Cart();
            cart.setUnifyId(commonUser.getUnifyId());
            cart.setGoods_name(item.getName());
            cart.setSku_no(skuId);
            cart.setGoods_sum(quantity);
            Response<String> response = cartService.addCart(cart);
            if (response.isSuccess()) {
                log.info("unifyId = {} success to add cart sku id = {}", commonUser.getUnifyId(), skuId);
                return "000000";
            } else {
                log.error("unifyId = {} fail to add cart sku id = {} cause by {}", commonUser.getUnifyId(), skuId, BussErrorCode.explain(response.getErrorCode()));
                return BussErrorCode.explain(response.getErrorCode());
            }
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, JsonResponseException.class);
            log.error("failed to add cart and its properties for sku id = {},cause:{}", skuId, e);
            throw new JsonResponseException(500, messageSources.get("cart.add.fail"));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateCart(@PathVariable Long id, @RequestParam("goods_num") Integer goods_num) {
        try {
            Response<cn.com.huateng.payment.model.Cart> cartResponse = cartService.getCartByCartNo(id);
            cn.com.huateng.payment.model.Cart cart;
            if (cartResponse.isSuccess()) {
                log.info("success to get cart info cartNo:{}", id);
                cart = cartResponse.getResult();
            } else {
                return BussErrorCode.explain(cartResponse.getErrorCode());
            }
            cart.setGoods_sum(goods_num);
            Response<String> response = cartService.updateCart(cart);
            if (response.isSuccess()) {
                log.info("unifyId = {} success to update cart sku id = {}", cart.getUnifyId(), cart.getSku_no());
                return "000000";
            } else {
                log.error("unifyId = {} fail to update cart sku id = {} cause by {}", cart.getUnifyId(), cart.getSku_no(), BussErrorCode.explain(response.getErrorCode()));
                return BussErrorCode.explain(response.getErrorCode());
            }
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, JsonResponseException.class);
            log.error("failed to update cart and its properties for cart no = {},cause:{}", id, e);
            throw new JsonResponseException(500, messageSources.get("cart.update.fail"));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        try {
            Response<String> stringResponse = cartService.deleteCart(id);
            if (stringResponse.isSuccess()) {
                log.info("删除 购物车 成功 cartNo : {}", id);
                return "000000";
            } else {
                return BussErrorCode.explain(stringResponse.getErrorCode());
            }
        } catch (Exception e) {
            log.error("failed to delete cart cartNo = {},cause:{}", id, e);
            throw new JsonResponseException(500, messageSources.get("cart.delete.fail"));
        }
    }

    @RequestMapping(value = "/patchDelete", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchDelete(@RequestParam("ids") String ids) {
        try {
            Iterable<String> parts = splitter.split(ids);
            if (Iterables.isEmpty(parts)) {
                log.warn("no cart need to delete,return directly");
                return messageSources.get("cart.ids.empty");
            }
            List<Long> lists = Lists.newArrayList();
            for (String str : parts) {
                lists.add(Long.parseLong(str));
            }
            Response<String> stringResponse = cartService.patchDeleteCart(lists);
            if (stringResponse.isSuccess()) {
                log.info("批量删除 购物车 成功 cartNos : {}", ids);
                return "000000";
            } else {
                return BussErrorCode.explain(stringResponse.getErrorCode());
            }
        } catch (Exception e) {
            log.error("failed to patchDelete cart cartNos = {},cause:{}", ids, e);
            throw new JsonResponseException(500, messageSources.get("cart.delete.fail"));
        }
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String createOrder(@RequestParam("ids") String ids, HttpServletRequest request, Map<String, Object> context) {
        CommonUser commonUser = UserUtil.getCurrentUser();
        Iterable<String> parts = splitter.split(ids);
        if (Iterables.isEmpty(parts)) {
            log.warn("no cart need to delete,return directly");
            return messageSources.get("cart.ids.empty");
        }
        List<Long> lists = Lists.newArrayList();
        for (String str : parts) {
            lists.add(Long.parseLong(str));
        }
        String requestIp = HttpUtil.getIpAddr(request);
        Response<SubmitWebGatePay> stringResponse = cartService.createOrder(lists, requestIp, commonUser.getUnifyId());
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
