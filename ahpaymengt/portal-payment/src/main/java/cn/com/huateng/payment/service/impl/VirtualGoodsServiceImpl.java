package cn.com.huateng.payment.service.impl;

import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.CodeGenerator;
import cn.com.huateng.common.FeeType;
import cn.com.huateng.common.OrderStatus;
import cn.com.huateng.payment.manage.VirtualGoodsManage;
import cn.com.huateng.payment.model.Cart;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.model.VirtualGoods;
import cn.com.huateng.payment.service.VirtualGoodsService;
import cn.com.huateng.payment.service.WebGatePayService;
import cn.com.huateng.util.DateUtil;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.google.common.base.Strings;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: 董培基
 * Date: 14-11-27
 * Time: 下午1:16
 */
@Service
public class VirtualGoodsServiceImpl implements VirtualGoodsService{

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private VirtualGoodsManage virtualGoodsManage;


    @Value("#{app.virtualGoodsSeq}")
    private String virtualGoodsSeq;

    @Value("#{app.orderBaseSeq}")
    private String orderBaseSeq;


    @Autowired
    private SeqGeneratorService seqGeneratorService;

    @Autowired
    private WebGatePayService webGatePayService;

    /**
     * 创建 虚拟商品订单
     * @param skuId  商品sku属性
     * @param count   商品数量
     * @param requestIp  ip 地址
     * @param unifyId    统一帐号
     * @return   网关对象
     */
    @Override
    public Response<SubmitWebGatePay> createOrder(String skuId,Long count,String requestIp, String unifyId) {
        //必填判断
        if (Strings.isNullOrEmpty(skuId)||null==count||Strings.isNullOrEmpty(unifyId)) {
            return new Response<>(BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {
            Sku sku = itemService.findSkuById(Long.parseLong(skuId));
            Item item = itemService.findById(sku.getItemId());
            VirtualGoods virtualGoods = new VirtualGoods();
            String virtualGoodSeq = CodeGenerator.generateSeqNo(seqGeneratorService.generateOrderNo(virtualGoodsSeq));
            virtualGoods.setOrderNo(virtualGoodSeq);
            virtualGoods.setCreateTime(DateUtil.getTime());
            virtualGoods.setGoodsName(item.getName());
            virtualGoods.setSkuNo(sku.getId());
            virtualGoods.setNumber(count);
            virtualGoods.setStatus(OrderStatus.ORDER_STATUS_1.getValue());//待支付
            virtualGoods.setOrderAmt(count * sku.getPrice().longValue());
            virtualGoods.setUnifyId(unifyId);
            virtualGoods.setTxnTime(DateUtil.getTime());
            TPortOrderBase tPortOrderBase = new TPortOrderBase();
            tPortOrderBase.setOrderSeq(CodeGenerator.generateSeqNo(seqGeneratorService.generateOrderNo(orderBaseSeq)));
            tPortOrderBase.setOrderReqTranSeq(virtualGoodSeq);
            tPortOrderBase.setUnifyId(unifyId);
            tPortOrderBase.setStatus(OrderStatus.ORDER_STATUS_1.getValue());
            tPortOrderBase.setCreateTime(DateUtil.getDate());
            tPortOrderBase.setFeeType(FeeType.VIRTUALGOODS.getValue());
            tPortOrderBase.setOrderAmount(count * sku.getPrice().longValue());
            virtualGoodsManage.insertVirtualGoods(virtualGoods, tPortOrderBase);

            //构建调用网关 业务对象
            SubmitWebGatePay submitWebGatePay = new SubmitWebGatePay();
            submitWebGatePay.setFeeType(FeeType.VIRTUALGOODS.getValue());
            submitWebGatePay.setUnifyId(unifyId);
            submitWebGatePay.setClientIp(requestIp);
            submitWebGatePay.setOrderId(virtualGoodSeq);
            submitWebGatePay.setOrderBaseNo(tPortOrderBase.getOrderSeq());
            //取网关调用对象
            Response<SubmitWebGatePay> submitWebGatePayResponse = webGatePayService.orderPay(submitWebGatePay);
            if (submitWebGatePayResponse.isSuccess()) {
                SubmitWebGatePay submitWebGatePay1 = submitWebGatePayResponse.getResult();
                return new Response<>(submitWebGatePay1);
            } else {
                return new Response<>(BussErrorCode.ERROR_CODE_400000.getErrorcode());
            }
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to query cart{}", e);
            return new Response<>(errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }
}
