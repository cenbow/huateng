package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User: 董培基
 * Date: 14-12-5
 * Time: 下午12:44
 */
@ToString
public class SaleReturnApply implements Serializable {

    private static final long serialVersionUID = 754179571020164141L;

    /**
     * 退货流水号
     */
    @Getter
    @Setter
    private String recordNo;

    /**
     * 订单号
     */
    @Getter
    @Setter
    private String orderNo;

    /**
     * 商品名称
     */
    @Getter
    @Setter
    private String goodsName;


    /**
     * 统一帐号
     */
    @Getter
    @Setter
    private String unifyId;

    /**
     * 退货电子单号
     */
    @Getter
    @Setter
    private String electronicNumber;

    /**
     * 状态
     */
    @Getter
    @Setter
    private String status;

    /**
     * 商品skuNo
     */
    @Getter
    @Setter
    private Long skuNo;

    /**
     * 退货金额
     */
    @Getter
    @Setter
    private Long refundAmt;

    /**
     * 交易时间
     */
    @Getter
    @Setter
    private Timestamp txnTime;

    /**
     * 数量
     */
    @Getter
    @Setter
    private Long count;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Timestamp createTime;

    /**
     * 受理标识
     */
    @Getter
    @Setter
    private String checkFlag;

    /**
     * 受理员工号
     */
    @Getter
    @Setter
    private String checkUid;

    /**
     * 受理时间
     */
    @Getter
    @Setter
    private Timestamp checkTime;

    /**
     * 备注
     */
    @Getter
    @Setter
    private String remark;

    public SaleReturnApply() {

    }

    public SaleReturnApply(String orderNo) {
        this.orderNo = orderNo;
    }
}
