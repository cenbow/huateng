package com.huateng.p3.account.common.bizparammodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

/**
 * 交易查询相关的出参
 * <p/>
 * Created by IntelliJ IDEA.
 * User: zhangwenjun
 * Date: 14-3-3
 */
@ToString
public class LogOnlinePaymentObject implements Serializable {

	private static final long serialVersionUID = 3284992492448317756L;
	/**
	 * 中心交易流水
	 */
    @Getter
    @Setter
	private String txnSeqNo;
	/**
	 * 账户号
	 */
    @Getter
    @Setter
    private String accountNo;
    /**
	 * 终端流水号
	 */
    @Getter
    @Setter
    private String terminalSeqNo;
    /**
     * 受理流水
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
     * 交易时间
     */
    @Getter
    @Setter
    private Date txnTime;
    
    /**
     * 交易金额
     */
    @Getter
    @Setter
    private long txnAmt;
    /**
     * 交易前金额
     */
    @Getter
    @Setter
    private Long beforeAmt;
    /**
     * 交易后金额
     */
    @Getter
    @Setter
    private Long afterAmt;
    /**
     * 内部交易类型
     */
    @Getter
    @Setter
    private String txnType;
    /**
     * 外部交易类型
     */
    @Getter
    @Setter
    private String businessType;
    /**
     * 交易渠道
     */
    @Getter
    @Setter
    private String txnChannel;
    
    /**
     * 出单机构
     */
    @Getter
    @Setter
    private String supplyOrgCode;
    
    /**
     * 受理机构
     */
    @Getter
    @Setter
    private String acceptOrgCode;
    
    /**
     * 支付机构
     */
    @Getter
    @Setter
    private String payOrgCode;
      
    /**
     * 商户名
     */
    @Getter
    @Setter
    private String merchantName;
    /**
     * 交易摘要
     */
    @Getter
    @Setter
    private String txnDscpt;
    /**
     * 总条数
     */
    @Getter
    @Setter
    private Integer total;
}
