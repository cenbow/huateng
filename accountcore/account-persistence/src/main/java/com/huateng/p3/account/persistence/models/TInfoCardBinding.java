package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TInfoCardBinding implements Serializable{
	
	private static final long serialVersionUID = -5451744384959948L;
	/**
	 * 个人账户绑卡ID
	 */
    private String cardbindid;
    /**
	 * 银行绑定编码 相当于网关的cardBindingID
	 */
    private String pactno;
    /**
	 * 绑定类型格式  02银行卡 01翼支付账户
	 */
    private String bindingtype;
    /**
     * 卡类型 01 借记卡 02信用卡
     */   
    private String cartype;
    /**
     * 账户号
     */
    private String accountid;
    /**
     * 商户号
     */
    private String merchantno;
    /**
     * 翼支付产品号
     */
    private String productno;
    /**
     * 绑卡、解绑最后更新时间
     */
    private String inttxntm;
    /**
     * 用户名
     */
    private String username;
    /**
     * 身份证
     */
    private String ids;
    /**
     * 银行卡号
     */
    private String cardno;
    /**
     * 卡末4位 信用卡为cvv
     */
    private String cvv2;
    /**
     * 卡有效期
     */
    private String cardvalidity;
    /**
     * 绑定银行名称
     */
    private String bingdingname;
    /**
	 * 绑卡状态   00绑定 01解绑  02关闭
	 */
    private String cardstatus;
    /**
     * 默认卡 Y为默认卡
     */
    private String tacitly;
    /**
     * 渠道ShortcutChannel枚举  
     * 01 一键付 02 翰银 03 天翼账户(已废弃) 04 快捷支付 05 提现卡 06信用卡还款 07 非本人转账绑卡
     * 支持多渠道查询，以,分隔 例如02,04
     */
    private String channel;
    /**
     * 一键付开通翼支付状态
     */
    private String opencusstatus;
    /**
     * 一键付产品号
     */
    private String productNo;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 出单机构 天翼账户用
     */
    private String supplyOrgCode;
    /**
     * 日交易金额（银行卡风控字段）
     */
    private Long dayAmt;
    /**
     * 月交易金额（银行卡风控字段）
     */
    private Long monthAmt;
    /**
     * 最后交易时间（银行卡风控字段）
     */
    private Date lastUpdateTime;
    
	/**
	 * 银行预留手机号
	 */
	private String bankProductNo;
	
	/**
	 * 对外银行编码
	 */
	private String orgBankCode;
	
	/**
	 * 开户行省份
	 */
	private String province;
	
	/**
	 * 开户行所在地区
	 */
	private String city;
	
	/**
	 * 开户支行
	 */
	private String subBank;
	
	/**
	 * 开户行联行网点号
	 */
	private String subBankCode;
	
	/**
	 * 公私标识
	 */
	private String ppFlag;
	
	/**
	 * 卡折标识
	 */
	private String cardFlag;

    public String getCardbindid() {
        return cardbindid;
    }

    public void setCardbindid(String cardbindid) {
        this.cardbindid = cardbindid;
    }

    public String getPactno() {
        return pactno;
    }

    public void setPactno(String pactno) {
        this.pactno = pactno;
    }

    public String getBindingtype() {
        return bindingtype;
    }

    public void setBindingtype(String bindingtype) {
        this.bindingtype = bindingtype;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getMerchantno() {
        return merchantno;
    }

    public void setMerchantno(String merchantno) {
        this.merchantno = merchantno;
    }

    public String getProductno() {
        return productno;
    }

    public void setProductno(String productno) {
        this.productno = productno;
    }

    public String getInttxntm() {
        return inttxntm;
    }

    public void setInttxntm(String inttxntm) {
        this.inttxntm = inttxntm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getCardvalidity() {
        return cardvalidity;
    }

    public void setCardvalidity(String cardvalidity) {
        this.cardvalidity = cardvalidity;
    }

    public String getBingdingname() {
        return bingdingname;
    }

    public void setBingdingname(String bingdingname) {
        this.bingdingname = bingdingname;
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus;
    }

    public String getTacitly() {
        return tacitly;
    }

    public void setTacitly(String tacitly) {
        this.tacitly = tacitly;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOpencusstatus() {
        return opencusstatus;
    }

    public void setOpencusstatus(String opencusstatus) {
        this.opencusstatus = opencusstatus;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSupplyOrgCode() {
        return supplyOrgCode;
    }

    public void setSupplyOrgCode(String supplyOrgCode) {
        this.supplyOrgCode = supplyOrgCode;
    }

    public Long getDayAmt() {
        return dayAmt;
    }

    public void setDayAmt(Long dayAmt) {
        this.dayAmt = dayAmt;
    }

    public Long getMonthAmt() {
        return monthAmt;
    }

    public void setMonthAmt(Long monthAmt) {
        this.monthAmt = monthAmt;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

	public String getBankProductNo() {
		return bankProductNo;
	}

	public void setBankProductNo(String bankProductNo) {
		this.bankProductNo = bankProductNo;
	}

	public String getOrgBankCode() {
		return orgBankCode;
	}

	public void setOrgBankCode(String orgBankCode) {
		this.orgBankCode = orgBankCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubBank() {
		return subBank;
	}

	public void setSubBank(String subBank) {
		this.subBank = subBank;
	}

	public String getSubBankCode() {
		return subBankCode;
	}

	public void setSubBankCode(String subBankCode) {
		this.subBankCode = subBankCode;
	}

	public String getPpFlag() {
		return ppFlag;
	}

	public void setPpFlag(String ppFlag) {
		this.ppFlag = ppFlag;
	}

	public String getCardFlag() {
		return cardFlag;
	}

	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
    
    
}