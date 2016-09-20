package cn.com.huateng.payment.model;


import lombok.ToString;

import java.io.Serializable;

@ToString
public class SubmitWebGatePay implements Serializable {

    private static final long serialVersionUID = 754179571020164141L;

    private String payUrl;

    private String merchantId; //商户号

    private String unifyId;//客户统一编号

    private String orderId;  //订单号

    private String traceSeqNo;   // 订单请求交易流水号

    private String upTranSeq; //网关支付返回流水

    private String orderBaseNo;//总表订单号

    private String traceTime; //交易时间

    private String transCode; // 交易代码

    private String feeType;//交易类型

    private Long orderAmt;  //订单金额

    private Long realAmt;   //实际金额

    private Long attachAmt; //附加金额

    private String curType;         //币种

    private String encMode;      // 加密方式

    private String pgUrl;     //前台返回地址

    private String bgUrl;  //后台返回地址

    private String attach;       //商户附加信息

    private String busiType;         //业务类型

    private String busiCode;         //业务标识

    private String tmNum;//终端号码

    private String customerId;//客户标识

    private String mac;//MAC校验域

    private String gmToverTime;//订单关闭时间 超过此时间后订单不能支付，格式yyyy-MM-dd HH:mm:ss

    private String clientIp;//客户端IP

    private String resv1;

    private String resv2;

    private String resv3;

    private String returnCode;

    private String returnInfo;

    private String upReqTranSeq;

    private String upBankTranSeq;

    private String Sign;

    private String bankId;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Long getAttachAmt() {
        return attachAmt;
    }

    public void setAttachAmt(Long attachAmt) {
        this.attachAmt = attachAmt;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getUpReqTranSeq() {
        return upReqTranSeq;
    }

    public void setUpReqTranSeq(String upReqTranSeq) {
        this.upReqTranSeq = upReqTranSeq;
    }

    public String getUpBankTranSeq() {
        return upBankTranSeq;
    }

    public void setUpBankTranSeq(String upBankTranSeq) {
        this.upBankTranSeq = upBankTranSeq;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getUpTranSeq() {
        return upTranSeq;
    }

    public void setUpTranSeq(String upTranSeq) {
        this.upTranSeq = upTranSeq;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getOrderBaseNo() {
        return orderBaseNo;
    }

    public void setOrderBaseNo(String orderBaseNo) {
        this.orderBaseNo = orderBaseNo;
    }

    public String getUnifyId() {
        return unifyId;
    }

    public void setUnifyId(String unifyId) {
        this.unifyId = unifyId;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTraceSeqNo() {
        return traceSeqNo;
    }

    public void setTraceSeqNo(String traceSeqNo) {
        this.traceSeqNo = traceSeqNo;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public Long getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(Long orderAmt) {
        this.orderAmt = orderAmt;
    }

    public Long getRealAmt() {
        return realAmt;
    }

    public void setRealAmt(Long realAmt) {
        this.realAmt = realAmt;
    }

    public String getCurType() {
        return curType;
    }

    public void setCurType(String curType) {
        this.curType = curType;
    }

    public String getEncMode() {
        return encMode;
    }

    public void setEncMode(String encMode) {
        this.encMode = encMode;
    }

    public String getPgUrl() {
        return pgUrl;
    }

    public void setPgUrl(String pgUrl) {
        this.pgUrl = pgUrl;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getTmNum() {
        return tmNum;
    }

    public void setTmNum(String tmNum) {
        this.tmNum = tmNum;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getGmToverTime() {
        return gmToverTime;
    }

    public void setGmToverTime(String gmToverTime) {
        this.gmToverTime = gmToverTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getResv1() {
        return resv1;
    }

    public void setResv1(String resv1) {
        this.resv1 = resv1;
    }

    public String getResv2() {
        return resv2;
    }

    public void setResv2(String resv2) {
        this.resv2 = resv2;
    }

    public String getResv3() {
        return resv3;
    }

    public void setResv3(String resv3) {
        this.resv3 = resv3;
    }
}
