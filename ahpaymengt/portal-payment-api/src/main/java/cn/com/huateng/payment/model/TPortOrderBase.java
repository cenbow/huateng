package cn.com.huateng.payment.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
@ToString
public class TPortOrderBase implements Serializable {
    @Getter
    @Setter
    private String orderSeq;
    @Getter
    @Setter
    private String orderReqTranSeq;
    @Getter
    @Setter
    private String merchantId;
    @Getter
    @Setter
    private String upTranSeq;
    @Getter
    @Setter
    private Long orderAmount;
    @Getter
    @Setter
    private Long productAmount;
    @Getter
    @Setter
    private Long attachAmount;
    @Getter
    @Setter
    private Date tranDate;
    @Getter
    @Setter
    private String curType;
    @Getter
    @Setter
    private String encodeType;
    @Getter
    @Setter
    private String retnCode;
    @Getter
    @Setter
    private String retnInfo;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private String feeType;
    @Getter
    @Setter
    private Date createTime;
    @Getter
    @Setter
    private String txnType;
    @Getter
    @Setter
    private String txtn1;
    @Getter
    @Setter
    private String txtn2;
    @Getter
    @Setter
    private String txtn3;
    @Getter
    @Setter
    private String unifyId;
    @Getter
    @Setter
    private String bankId;
    @Getter
    @Setter
    private Date startDate;
    @Getter
    @Setter
    private Date endDate;
    @Getter
    @Setter
    private int endIndex;

    @Getter
    @Setter
    private Long beforeAmt;// 账户交易前余额
    @Getter
    @Setter
    private Long afterAmt;// 账户交易后余额

    @Getter
    @Setter
    private int startIndex;

    public TPortOrderBase(String orderId){
        this.orderReqTranSeq = orderId;
    }

    public TPortOrderBase(){

    }

}