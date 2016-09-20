package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * User: 董培基
 * Date: 13-8-21
 * Time: 上午10:55
 */
public class Goods implements Serializable {

    private static final long serialVersionUID = -2315931347100045747L;

    @Getter
    @Setter
    /*商品名称*/
    private String goodsName;

    @Getter
    @Setter
    /*商品编号*/
    private String goodsNo;

    @Getter
    @Setter
    /*数量*/
    private Integer number;

    @Getter
    @Setter
    /*商品单价*/
    private Long singleAmount;

    @Getter
    @Setter
    /*商品总价*/
    private Long allAmount;

    @Getter
    @Setter
    /*请求IP*/
    private String requestIp;








}
