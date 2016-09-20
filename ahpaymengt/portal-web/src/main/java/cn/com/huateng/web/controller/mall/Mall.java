package cn.com.huateng.web.controller.mall;

import cn.com.huateng.CommonUser;
import cn.com.huateng.common.GoodsCardType;
import cn.com.huateng.payment.model.Goods;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import cn.com.huateng.payment.service.MallService;
import cn.com.huateng.web.util.HttpUtil;
import com.aixforce.annotations.ParamInfo;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.aixforce.user.base.UserUtil;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;


/**
 * User: 董培基
 * Date: 13-8-29
 * Time: 上午11:23
 */
@RequestMapping("/api/mall")
public class Mall {

    private static final Logger log = LoggerFactory.getLogger(Mall.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private MallService mallService;

    private Set<MallItem> mallList;

   /* @RequestMapping(value = "/querymax", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String queryMax(@RequestParam("skuId") Long skuId, @RequestParam("quantity") Integer quantity) {
        CommonUser commonUser = UserUtil.getCurrentUser();

        Sku sku = itemService.findSkuById(skuId);
        Item item = itemService.findById(sku.getItemId());
        mallList = Sets.newHashSet();
        try {
            Resources.readLines(Resources.getResource("/mall_list"), Charsets.UTF_8, new LineProcessor<Void>() {
                @Override
                public boolean processLine(String line) throws IOException {
                    if (!Strings.isNullOrEmpty(line)) {
                        Iterable<String> parts = Splitter.on(':').trimResults().split(line);
                        checkState(Iterables.size(parts) == 3, "illegal mall_list configuration [%s]", line);
                        String mallName = Iterables.get(parts, 0);
                        String rules = Iterables.get(parts, 1);
                        String feeType = Iterables.get(parts, 2);
                        mallList.add(new MallItem(mallName, rules, feeType));
                    }
                    return true;
                }

                @Override
                public Void getResult() {
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("读取商城产品配置文件失败 cause by {}", e.getMessage());
            return "error";
        }
        for (MallItem mallItem : mallList) {
            if (Objects.equal(item.getName(), mallItem.mallName)) {
                Response<Integer> integerResponse = mallService.findDatePayCount(bestpayUser.getProductNo(), mallItem.feeType);
                if (integerResponse.isSuccess()) {
                    quantity = quantity + integerResponse.getResult();
                    if (quantity > Long.parseLong(mallItem.rules)) {
                        return "max:" + Long.parseLong(mallItem.rules);
                    }
                } else {
                    log.error("读取该用户当天购买数量失败 cause by {}", BussErrorCode.explain(integerResponse.getErrorCode()));
                    return "error";
                }
                break;
            }
        }
        return "ok";
    }


    */
    @RequestMapping(value = "/pay", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String mallPay(@ParamInfo("skuId") Long skuId, @ParamInfo("count") Integer count
            , Map<String, Object> context, HttpServletRequest request) {
        CommonUser commonUser =UserUtil.getCurrentUser();

        Sku sku = itemService.findSkuById(skuId);
        Item item = itemService.findById(sku.getItemId());
        Goods good = new Goods();
        if ("充值卡".equals(item.getName())) {
            good.setGoodsName(GoodsCardType.ReChargeCard.getDesc());
            good.setGoodsNo(GoodsCardType.ReChargeCard.getCardType());
        } else if ("标签".equals(item.getName())) {
            good.setGoodsName(GoodsCardType.LABEL.getDesc());
            good.setGoodsNo(GoodsCardType.LABEL.getCardType());
        } else if ("月票".equals(item.getName())) {
            good.setGoodsName(GoodsCardType.MonthTicket.getDesc());
            good.setGoodsNo(GoodsCardType.MonthTicket.getCardType());
        }
        good.setSingleAmount(sku.getPrice().longValue());
        good.setAllAmount(sku.getPrice().longValue() * count);
        good.setNumber(count);
        good.setRequestIp(HttpUtil.getIpAddr(request));
        Response<SubmitWebGatePay> sResponse = mallService.saveOrder(commonUser, good);
        if (sResponse.isSuccess()) {
            itemService.decrementStock(skuId,sku.getItemId(),count);
            context.put("payInfo", sResponse.getResult());
            return "/layout/sutongpay/payment/webgate";
        } else {
            String errorCode = sResponse.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            log.error("saveOrder(unifyId={},customerNo={},) failed, errorCode={}", commonUser.getUnifyId(), commonUser.getId(), errorCode);
            throw new JsonResponseException(500, message);
        }
    }

    public static class MallItem {
        public String mallName;

        public String rules;

        public String feeType;

        public MallItem(String mallName, String rules, String feeType) {
            this.mallName = mallName;
            this.rules = rules;
            this.feeType = feeType;
        }


    }
}
