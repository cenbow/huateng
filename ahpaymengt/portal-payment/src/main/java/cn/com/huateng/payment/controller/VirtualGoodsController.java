package cn.com.huateng.payment.controller;

import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.OrderStatus;
import cn.com.huateng.payment.dao.TPortOrderBaseMapper;
import cn.com.huateng.payment.dao.VirtualGoodsMapper;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.model.VirtualGoods;
import cn.com.huateng.util.DateUtil;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.models.CommonGoodsObject;
import com.huateng.p3.hub.accountcore.models.GoodsStock;
import com.huateng.p3.hub.accountcore.service.HubCommonGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * User: 董培基
 * Date: 14-12-1
 * Time: 下午2:59
 */
@Slf4j
@Component
public class VirtualGoodsController {

    @Autowired
    private VirtualGoodsMapper virtualGoodsMapper;

    @Autowired
    private TPortOrderBaseMapper tPortOrderBaseMapper;

    @Autowired
    private HubCommonGoodsService hubCommonGoodsService;

    @Autowired
    private ItemService itemService;

    public void recharge(String orderId, String traceTime, String upTranSeq, String bankId) {
        Strings.nullToEmpty(orderId);
        Strings.nullToEmpty(traceTime);
        Strings.nullToEmpty(upTranSeq);
        Strings.nullToEmpty(bankId);
        try {
            log.info("======== VirtualGoods recharge：通用虚拟商品网关销帐 start订单号:" + orderId);
            VirtualGoods virtualGoods = virtualGoodsMapper.findVirtualGoods(new VirtualGoods(orderId));
            if (virtualGoods == null) {
                log.info("销帐 订单号" + orderId + " 不存在此订单，此为无效订单 销帐停止");
                return;
            }
            virtualGoods.setStatus(OrderStatus.ORDER_STATUS_2.getValue());
            virtualGoodsMapper.updateVirtualGoods(virtualGoods);
            CommonGoodsObject commonGoodsObject = new CommonGoodsObject();
            commonGoodsObject.setGoodsName(virtualGoods.getGoodsName());
            Sku sku = itemService.findSkuById(virtualGoods.getSkuNo());
            commonGoodsObject.setNumber(virtualGoods.getNumber());
            commonGoodsObject.setSkuName1(sku.getAttributeKey1());
            commonGoodsObject.setSkuAttributeName1(sku.getAttributeName1());
            commonGoodsObject.setSkuName2(sku.getAttributeKey2());
            commonGoodsObject.setSkuAttributeName2(sku.getAttributeName2());
            commonGoodsObject.setPrice(sku.getPrice().longValue());
            //获取电子号码
            Response<List<GoodsStock>> goodsStockResponse = hubCommonGoodsService.getCommonProductList(commonGoodsObject);
            String status;
            if (goodsStockResponse.isSuccess()) {//成功获取电子号码
                List<GoodsStock> goodsStocks = goodsStockResponse.getResult();
                List<String> stockIds = Lists.newArrayList();
                for (GoodsStock goodsStock : goodsStocks) {
                    stockIds.add(goodsStock.getStockId());
                }
                Iterator<String> iterator = stockIds.iterator();
                virtualGoods.setStatus(OrderStatus.ORDER_STATUS_5.getValue());
                status = OrderStatus.ORDER_STATUS_5.getValue();
                virtualGoods.setElectronicNumber(Joiner.on(",").join(iterator));
            } else {
                status = OrderStatus.ORDER_STATUS_6.getValue();
                virtualGoods.setStatus(OrderStatus.ORDER_STATUS_6.getValue());
                log.error("获取通用商品电子号码失败 原因:{}", goodsStockResponse.getErrorCode() + goodsStockResponse.getErrorMsg());
            }
            virtualGoodsMapper.updateVirtualGoods(virtualGoods);
            //查询订单总表记录
            TPortOrderBase tPortOrderBaseVo = new TPortOrderBase(orderId);
            List<TPortOrderBase> orderBaseVo = tPortOrderBaseMapper.findTPortOrderBaseByOrderTranReq(tPortOrderBaseVo);
            log.info("订单总表查询,订单号:" + tPortOrderBaseVo.getOrderSeq() + "\t 业务订单号:" + tPortOrderBaseVo.getOrderReqTranSeq() + "订单总表记录数:" + orderBaseVo.size());
            //更改订单总表信息
            if (orderBaseVo.size() == 1) {
                TPortOrderBase orderBVo = orderBaseVo.get(0);
                orderBVo.setStatus(status);//成功或者失败
                tPortOrderBaseMapper.updateTPortOrderBase(orderBVo);
            }
        } catch (Exception e) {
            log.error("failed to recharge virtualGoods{}", e);
        }
    }

}
