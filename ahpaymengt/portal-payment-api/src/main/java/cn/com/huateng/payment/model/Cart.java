package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User: 董培基
 * Date: 14-11-14
 * Time: 下午3:26
 */
@ToString
public class Cart implements Serializable {

    private static final long serialVersionUID = 754179571020164141L;

    @Getter
    @Setter
    /*购物车序号*/
    private Long cartNo;

    @Getter
    @Setter
    /*统一帐号*/
    private String unifyId;

    @Getter
    @Setter
    /*商品属性序号*/
    private Long sku_no;

    @Getter
    @Setter
    /*商品名称*/
    private String goods_name;

    @Getter
    @Setter
    /*数量*/
    private Integer goods_sum;

    @Getter
    @Setter
    /*总价钱*/
    private int totalPrice;

    @Getter
    @Setter
    /*是否有效*/
    private String is_active;

    @Getter
    @Setter
    /*创建时间*/
    private Timestamp create_time;

    @Getter
    @Setter
    private int endIndex;

    @Getter
    @Setter
    private int startIndex;

}
