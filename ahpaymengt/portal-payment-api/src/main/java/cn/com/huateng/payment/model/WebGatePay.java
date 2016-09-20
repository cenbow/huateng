package cn.com.huateng.payment.model;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User: 董培基
 * Date: 13-8-1
 * Time: 上午11:06
 */
@ToString
public class WebGatePay extends TPortOrderDetail {
    private static final long serialVersionUID = 4379873295896090594L;
    @Getter
    @Setter
    /*业务订单号*/
    private String orderNo;
    @Getter
    @Setter
    /*请求流水号 网关流水号*/
    private String orderReqTranSeq;

    @Getter
    @Setter
    /*请求子流水 合并支付使用*/
    private String subOrderReqTranSeq;

    @Getter
    @Setter
    /*总表订单号*/
    private String orderBaseNo;
    @Getter
    @Setter
    /*渠道号*/
    private String merchantId;
    @Getter
    @Setter
    /*网关支付流水 网关返回*/
    private String upTranSeq;
    @Getter
    @Setter
    /*缴费类型*/
    private String feeType;
    @Getter
    @Setter
    /*合并支付使用 支付级别*/
    private String level;
    @Getter
    @Setter
    /*统一编号*/
    private String unifyId;
    @Getter
    @Setter
    /*网关订单状态*/
    private String status;
    @Getter
    @Setter
    /*订单总金额 附加金额 + 产品金额*/
    private Long orderAmount;
    @Getter
    @Setter
    /*产品金额*/
    private Long productAmount;
    @Getter
    @Setter
    /*附加金额*/
    private Long attachAmount;
    @Getter
    @Setter
    /*币种*/
    private String curType;
    @Getter
    @Setter
    /*加密方式*/
    private String encodeType;
    @Getter
    @Setter
    /*创建时间*/
    private Date createTime;
    @Getter
    @Setter
    /*订单日期*/
    private String tranDate;
    @Getter
    @Setter
    /*交易类型代码*/
    private String businessCode;
    @Getter
    @Setter
    /*用户Ip*/
    private String txt1;
    @Getter
    @Setter
    /*备用1*/
    private String txt2;
    @Getter
    @Setter
    /*备用2*/
    private String txt3;
    @Getter
    @Setter
    /*退款code*/
    private String returnCode;
    @Getter
    @Setter
    /*退款信息*/
    private String returnInfo;
    @Getter
    @Setter
    /*是否对账*/
    private String reCiFlag;
    @Getter
    @Setter
    /*银行编码*/
    private String bankId;

    public WebGatePay() {
    }

    public WebGatePay(String orderSeq, String orderReqTranSeq) {
        this.orderReqTranSeq = orderReqTranSeq;
        if(!Strings.isNullOrEmpty(orderSeq)){
            this.orderNo = orderSeq;
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("orderNo", orderNo)
                .add("orderReqTranSeq", orderReqTranSeq)
                .add("subOrderReqTranSeq", subOrderReqTranSeq)
                .add("orderBaseNo", orderBaseNo)
                .add("merchantId", merchantId)
                .add("upTranSeq", upTranSeq)
                .add("feeType", feeType)
                .add("level", level)
                .add("unifyId", unifyId)
                .add("status", status)
                .add("orderAmount", orderAmount)
                .add("productAmount", productAmount)
                .add("attachAmount", attachAmount)
                .add("curType", curType)
                .add("encodeType", encodeType)
                .add("createTime", createTime)
                .add("tranDate", tranDate)
                .add("businessCode", businessCode)
                .add("txt1", txt1)
                .add("txt2", txt2)
                .add("txt3", txt3)
                .add("returnCode", returnCode)
                .add("returnInfo", returnInfo)
                .add("reCiFlag", reCiFlag)
                .add("bankId", bankId)
                .omitNullValues()
                .toString();
    }
}
