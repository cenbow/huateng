package com.huateng.p3.hub.accountcore.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用商品对象
 * User: dongpeiji
 * Date: 14-9-24
 * Time: 下午12:44
 */
public class CommonGoodsObject implements Serializable{

    /**
     * 商品名 外部使用
     */
    @Getter
    @Setter
    private String goodsName;

    /**
     * 商品序号 内部使用
     */
    @Getter
    @Setter
    private String goodsNo;

    /**
     * 类目名1
     */
    @Getter
    @Setter
    private String skuName1;

    /**
     * 类目属性1
     */
    @Getter
    @Setter
    private String skuAttributeName1;

    /**
     * 类目名2
     */
    @Getter
    @Setter
    private String skuName2;

    /**
     * 类目属性2
     */
    @Getter
    @Setter
    private String skuAttributeName2;

    /**
     *价格
     */
    @Getter
    @Setter
    private Long price;

    /**
     *数量
     */
    @Getter
    @Setter
    private Long number;

    /**
     * 库存商品
     */
    @Getter
    @Setter
    private List<GoodsStock> goodsStocks;



}
