package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;
import java.util.Date;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude={"txnPwd","loginPwd"})
public class SmsSendInfo implements Serializable{

	private static final long serialVersionUID = -2709072142784748208L;
	/**
	 * 交易渠道
	 */
	@Getter
    @Setter
	private String txnChannel;
	/**
	 * 交易代码
	 */
	@Getter
    @Setter
    private String txnType;
    /**
     * 交易描述
     */
    @Getter
    @Setter
    private String txnDscpt;
    /**
     * 受理机构
     */
    @Getter
    @Setter
    private String acceptOrgCode;
    /**
     * 受理流水号
     */
    @Getter
    @Setter
    private String acceptTransSeqNo;
    /**
     * 受理日期
     */
    @Getter
    @Setter
    private String acceptTransDate;
    /**
     * 受理时间
     */
    @Getter
    @Setter
    private String acceptTransTime;
    /**
     * 账户类型
     */
    @Getter
    @Setter
    private String accountType;
    /**
     * 客户编号
     */
    @Getter
    @Setter
    private String customerNo;
    /**
     * 账户编号
     */
    @Getter
    @Setter
    private String accountNo;
    /**
     * 交易金额
     */
    @Getter
    @Setter
    private String txnAmount;
    /**
     * 交易时间
     */
    @Getter
    @Setter
    private Date txnTime;
    /**
     * 手机号码
     */
    @Getter
    @Setter
    private String mobilePhone;
    /**
     * 电子邮箱
     */
    @Getter
    @Setter
    private String email;
    /**
     * 初始支付密码	
     */
    @Getter
    @Setter
    private String txnPwd;
    /**
     * 初始登陆密码
     */
    @Getter
    @Setter
    private String loginPwd;
    /**
     * 账户余额
     */
    @Getter
    @Setter
    private String balance;
    /**
     * 商户名
     */
    @Getter
    @Setter
    private String merchantName;
    /**
     * 手续费
     */
    @Getter
    @Setter
    private String feeAmt;
    /**
     * 短信业务类型
     */
    @Getter
    @Setter
    private String bussinessType;
    
    /**
     * 前端商户(网关前端商户,用来发送短信内容用)
     */
    @Getter
    @Setter
    private String frontOrgCode;

}