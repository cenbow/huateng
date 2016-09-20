package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * User: 董培基
 * Date: 13-7-30
 * Time: 上午11:43
 */
public class PublicFee extends TPortOrderDetail {


    private static final long serialVersionUID = -4705076862646194842L;
    @Getter
    @Setter
    /*内部订单号*/
    private String orderNo;
    @Getter
    @Setter
    private String customerNo;
    @Getter
    @Setter
    /*客户账户号*/
    private String accountNo;
    @Getter
    @Setter
    /*申请时间*/
    private Date txnTime;
    @Getter
    @Setter
    /*缴费单位商户号*/
    private String merchantCode;
    @Getter
    @Setter
    /*缴费地区*/
    private String areaCode;
    @Getter
    @Setter
    /*缴费地区中文*/
    private String areaName;
    @Getter
    @Setter
    /*缴费城市*/
    private String cityCode;
    @Getter
    @Setter
    /*缴费城市中文*/
    private String cityName;
    @Getter
    @Setter
    /*缴费单位*/
    private String receiptOrg;
    @Getter
    @Setter
    /*缴费项目类别*/
    private String feeProjectType;
    @Getter
    @Setter
    /*缴费项目*/
    private String feeProject;
    @Getter
    @Setter
    /*条形码/账户号/手机号*/
    private String barCode;
    @Getter
    @Setter
    /*销账单号类型*/
    private String feeAccount;
    @Getter
    @Setter
    /*缴费金额*/
    private Long txnAmt;
    @Getter
    @Setter
    /*处理状态*/
    private String status;
    @Getter
    @Setter
    /*完成时间*/
    private Date endTime;
    @Getter
    @Setter
    /*第三方代码*/
    private String resvFld1;
    @Getter
    @Setter
    /*失败错误代码*/
    private String resvFld2;
    @Getter
    @Setter
    /*账期*/
    private String accountMonth;
    @Getter
    @Setter
    /*抄次*/
    private String batchNo;
    @Getter
    @Setter
    /*合同号*/
    private String contractNo;
    @Getter
    @Setter
    /*预留字段1*/
    private String text1;
    @Getter
    @Setter
    /*预留字段2*/
    private String text2;
    @Getter
    @Setter
    /*预留字段3*/
    private String text3;
    @Getter
    @Setter
    /*预留字段4*/
    private String text4;
    @Getter
    @Setter
    /*预留字段5*/
    private String text5;
    @Getter
    @Setter
    private String text6;
    @Getter
    @Setter
    private String text7;
    @Getter
    @Setter
    private Long productAmt;
    @Getter
    @Setter
    private String activityType;
    @Getter
    @Setter
    /*滞纳金*/
    private Long delayAmt;
    @Getter
    @Setter
    /*统一编号*/
    private String unifyId;
    @Getter
    @Setter
    private Long poroffsersAmt;

    @Getter
    @Setter
    private String refoundStatus;
    @Getter
    @Setter
    private String feeModeType;
    @Getter
    @Setter
    private String cashBackStatus;
    @Getter
    @Setter
    /*用户IP*/
    private String requestIp;
    @Getter
    @Setter
    /*用户渠道*/
    private String fromType;

    public PublicFee() {

    }

    public PublicFee(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "PublicFee{" +
                "orderNo='" + orderNo + '\'' +
                ", customerNo='" + customerNo + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", txnTime=" + txnTime +
                ", merchantCode='" + merchantCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", receiptOrg='" + receiptOrg + '\'' +
                ", feeProjectType='" + feeProjectType + '\'' +
                ", feeProject='" + feeProject + '\'' +
                ", barCode='" + barCode + '\'' +
                ", feeAccount='" + feeAccount + '\'' +
                ", txnAmt=" + txnAmt +
                ", status='" + status + '\'' +
                ", endTime=" + endTime +
                ", resvFld1='" + resvFld1 + '\'' +
                ", resvFld2='" + resvFld2 + '\'' +
                ", accountMonth='" + accountMonth + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", text3='" + text3 + '\'' +
                ", text4='" + text4 + '\'' +
                ", text5='" + text5 + '\'' +

                ", delayAmt=" + delayAmt +

                ", productAmt=" + productAmt +
                ", activityType='" + activityType + '\'' +
                ", unifyId='" + unifyId + '\'' +
                ", poroffsersAmt=" + poroffsersAmt +
                ", text6='" + text6 + '\'' +
                ", text7='" + text7 + '\'' +
                ", refoundStatus='" + refoundStatus + '\'' +
                ", feeModeType='" + feeModeType + '\'' +
                ", cashBackStatus='" + cashBackStatus + '\'' +
                '}';
    }
}
