package cn.com.huateng.payment.service.impl;

import cn.com.huateng.CommonUser;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.FeeType;
import cn.com.huateng.common.GoodsCardType;
import cn.com.huateng.payment.model.Goods;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import cn.com.huateng.payment.service.MallService;
import cn.com.huateng.payment.service.WebGatePayService;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: dongpeiji
 * Date: 14-11-11
 * Time: 下午3:51
 */
public class MallServiceImpl implements MallService {
    private static final Logger log = LoggerFactory.getLogger(MallServiceImpl.class);


    @Autowired
    private WebGatePayService webGatePayService;

    /**
     * 商城 购买商品入口
     * 下单 如果下单成功(包括获取卡，并且锁定卡 都成功) 则记录卡信息，返回网关对象
     *
     * @param good 商品对象
     * @return 网关对象
     */
    @Override
    public Response<SubmitWebGatePay> saveOrder(CommonUser commonUser, Goods good) {
        try {
            //参数效验
            checkNotNull(good, BussErrorCode.ERROR_CODE_800001.getErrorcode());
            checkNotNull(commonUser, BussErrorCode.ERROR_CODE_800001.getErrorcode());
            checkNotNull(commonUser.getAccountNo(), BussErrorCode.ERROR_CODE_800001.getErrorcode());
            checkNotNull(commonUser.getUnifyId(), BussErrorCode.ERROR_CODE_800001.getErrorcode());
            checkNotNull(commonUser.getId(), BussErrorCode.ERROR_CODE_800001.getErrorcode());
        } catch (NullPointerException e) {
            log.error("check arguments for mall save order,", e.getMessage());
            return new Response<SubmitWebGatePay>(e.getMessage());
        }
        String returnCode = "000000";
        //构建调用网关 业务对象
        SubmitWebGatePay submitWebGatePay = new SubmitWebGatePay();
        try {
            if (good.getGoodsNo().equals(GoodsCardType.MonthTicket.getCardType())) {
                submitWebGatePay.setFeeType(FeeType.MonthTicket.getValue());
                //TODO 锁卡
                //returnCode = cardHandle.handleCard(good, commonUser, GoodsCardType.Card_11888.getCardType(), submitWebGatePay);
            } else if (good.getGoodsNo().equals(GoodsCardType.LABEL.getCardType())) {
                submitWebGatePay.setFeeType(FeeType.LABEL.getValue());
                //returnCode = cardHandle.handleCard(good, commonUser, GoodsCardType.Card_TianYi.getCardType(), submitWebGatePay);
            } else if (good.getGoodsNo().equals(GoodsCardType.ReChargeCard.getCardType())) {
                submitWebGatePay.setFeeType(FeeType.ReChargeCard.getValue());
                //returnCode = cardHandle.handle3GCard(good, commonUser, submitWebGatePay);
            } else {
                log.error("-----商品不存在-----");
                return new Response<SubmitWebGatePay>(BussErrorCode.ERROR_CODE_600001.getErrorcode());
            }
            if ("000000".equals(returnCode)) {
                submitWebGatePay.setUnifyId(commonUser.getUnifyId());
                submitWebGatePay.setClientIp(good.getRequestIp());
                submitWebGatePay.setOrderAmt(good.getAllAmount());
                //取网关调用对象
                Response<SubmitWebGatePay> submitWebGatePayResponse = webGatePayService.orderPay(submitWebGatePay);
                if (submitWebGatePayResponse.isSuccess()) {
                    SubmitWebGatePay submitWebGatePay1 = submitWebGatePayResponse.getResult();
                    return new Response<SubmitWebGatePay>(submitWebGatePay1);
                } else {
                    return new Response<SubmitWebGatePay>(BussErrorCode.ERROR_CODE_400000.getErrorcode());
                }
            } else {
                return new Response<SubmitWebGatePay>(returnCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response<SubmitWebGatePay>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
    }

}
