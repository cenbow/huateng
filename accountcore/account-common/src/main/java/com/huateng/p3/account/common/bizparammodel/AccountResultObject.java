package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: huwenjie
 * Date: 14-1-22
 * Time: 下午6:10
 */
@ToString
public class AccountResultObject implements Serializable {
	
	private static final long serialVersionUID = -4029948603030523176L;

	/**
     * 账户号
     */
    @Getter
    @Setter
    private String accountNo;
    
    /**
     * 账户名
     */
    @Getter
    @Setter
    private String accountName;

    /**
     * 账户类型
     */
    @Getter
    @Setter
    private String type;


    /**
     * 账户状态
     */
    @Getter
    @Setter
    private String status;

    /**
     * 账户余额
     */
    @Getter
    @Setter
    private Long balance = 0l;

    /**
     * 账户上日余额
     */
    @Getter
    @Setter
    private Long lastDayBal = 0l;
    
    /**
     * 账户上月余额
     */
    @Getter
    @Setter
    private Long lastMonthBal = 0l;

    /**
     * 账户可用余额
     */
    @Getter
    @Setter
    private Long availableBalance = 0l;

    /**
     * 账户不可用余额
     */
    @Getter
    @Setter
    private Long unavailableBalance = 0l;
    
    /**
     * 账户可提现余额
     */
    @Getter
    @Setter
    private Long withdrawBalance = 0l;
    
    /**
     * 卡号
     */
    @Getter
    @Setter
    private String cardNo;
    
    /**
     * 卡类型
     */
    @Getter
    @Setter
    private String cardType;
    
    
}
