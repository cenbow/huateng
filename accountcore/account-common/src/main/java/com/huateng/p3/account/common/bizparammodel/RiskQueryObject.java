package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 风控检查返回参数
 * <p/>
 * Created by James Tang.
 * User: James Tang
 * Date: 13-12-3
 */
@ToString
public class RiskQueryObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * 账号
     */
    @Getter
    @Setter
    private String accountNo;
    /**
     * 账户类型
     */
    @Getter
    @Setter
    private String type;
    /**
     * 账户等级
     */
    @Getter
    @Setter
    private String grade;
    
    /**
     * 渠道
     */
    @Getter
    @Setter
    private String channel;

    /**
     * 外部交易类型
     */
    @Getter
    @Setter
    private String bussinessType;

    /**
     * 内部交易类型
     */
    @Getter
    @Setter
    private String innerTxnType;
    
    /**
     * 可交易金额
     */
    @Getter
    @Setter
    private Long tradableAmount;
    
    
    /**
     * 可交易次数
     */
    @Getter
    @Setter
    private Long tradeableTimes;
    
   
}
