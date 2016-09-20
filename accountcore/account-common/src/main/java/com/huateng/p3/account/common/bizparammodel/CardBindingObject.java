package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.huateng.p3.account.common.enummodel.ShortcutCardStatus;

@ToString(exclude={"cardnoReal","ids"})
public class CardBindingObject implements Serializable{
	private static final long serialVersionUID = -8453270626678975504L;

	/**
	 * 银行绑定编码 相当于网关的cardBindingID
	 */
	@Getter
    @Setter
	private String pactno;
	/**
	 * 绑定类型格式  02银行卡 01翼支付账户
	 */
	@Getter
    @Setter
    private String bindingtype;
    /**
     * 卡类型 01 借记卡 02信用卡
     */   
	@Getter
    @Setter
    private String cartype;
    /**
     * 商户号
     */
	@Getter
    @Setter
    private String merchantno;
    /**
     * 翼支付产品号
     */
	@Getter
    @Setter
    private String productno;
    /**
     * 用户名
     */
	@Getter
    @Setter
    private String username;
    /**
     * 身份证
     */
	@Getter
    @Setter
    private String ids;
    /**
     * 银行卡号 （加密）
     */
	@Getter
    @Setter
    private String cardno;
	/**
     * 银行卡号 （明文）,不入库,不输出日志,只用来判断银行卡是否黑名单
     */
	@Getter
    @Setter
    private String cardnoReal;
    /**
     * 卡末4位 信用卡为cvv （加密）
     */
	@Getter
    @Setter
    private String cvv2;
    /**
     * 卡有效期 （加密）
     */
	@Getter
    @Setter
    private String cardvalidity;
    /**
     * 绑定银行名称
     */
	@Getter
    @Setter
    private String bingdingname;
    /**
     * 渠道ShortcutChannel枚举  
     * 01 一键付 02 翰银 03 天翼账户(已废弃) 04 快捷支付 05 提现卡 06信用卡还款 07 非本人转账绑卡
     * 支持多渠道查询，以,分隔 例如02,04
     */
	@Getter
    @Setter
    private String channel;
    /**
     * 银行编码
     */
	@Getter
    @Setter
    private String bankCode;
    /**
     * 出单机构 天翼账户用
     */
	@Getter
    @Setter
    private String supplyOrgCode;
	/**
	 * 绑卡状态 ShortcutCardStatus枚举 
	 * 解绑与查询时使用  00绑定 01解绑  02关闭
	 */
	@Getter
    @Setter
	private String cardstatus = ShortcutCardStatus.NOTUSED.getValue();
	
	/**
	 * 绑卡时是否需要实名  0需要实名
	 */
	@Getter
    @Setter
	private String realName;
	
	/**
	 * 银行预留手机号
	 */
	@Getter
    @Setter
	private String bankProductNo;
	
	/**
	 * 对外银行编码
	 */
	@Getter
    @Setter
	private String orgBankCode;
	
	/**
	 * 开户行省份
	 */
	@Getter
    @Setter
	private String province;
	
	/**
	 * 开户行所在地区
	 */
	@Getter
    @Setter
	private String city;
	
	/**
	 * 开户支行
	 */
	@Getter
    @Setter
	private String subBank;
	
	/**
	 * 开户行联行网点号
	 */
	@Getter
    @Setter
	private String subBankCode;
	
	/**
	 * 公私标识
	 */
	@Getter
    @Setter
	private String ppFlag;
	
	/**
	 * 卡折标识
	 */
	@Getter
    @Setter
	private String cardFlag;
}