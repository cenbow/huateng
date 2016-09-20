package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * User: 董培基
 * Date: 14-11-19
 * Time: 下午9:07
 */
@ToString
public class VirtualGoods extends TPortOrderDetail implements Serializable {
    /*订单号*/
    @Getter
    @Setter
    private String orderNo;

    /*商品名称*/
    @Getter
    @Setter
    private String goodsName;

    /*sku编号*/
    @Getter
    @Setter
    private Long skuNo;

    /*订单金额*/
    @Getter
    @Setter
    private Long orderAmt;

    /*交易时间*/
    @Getter
    @Setter
    private Timestamp txnTime;

    /*数量*/
    @Getter
    @Setter
    private Long number;

    /*创建时间*/
    @Getter
    @Setter
    private Timestamp createTime;

    /*统一帐号*/
    @Getter
    @Setter
    private String unifyId;

    /*销帐返回电子号*/
    @Getter
    @Setter
    private String electronicNumber;

    /*状态*/
    @Getter
    @Setter
    private String status;

    /*是否可以退货标识*/
    @Getter
    @Setter
    private String isOrNotReturnGoods = "0";

    /*退货状态*/
    @Getter
    @Setter
    private boolean isReturningGoods;

    /*退货列表信息*/
    @Getter
    @Setter
    private List<SaleReturnApply> saleReturnApplies;

    public VirtualGoods(){

    }
    public VirtualGoods(String orderNo){
        this.orderNo =  orderNo;
    }

}
