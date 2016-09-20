package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: huwenjie
 * Date: 14-5-22
 * Time: 下午6:10
 */
@ToString(exclude={"txnPwd","loginPwd"})
public class OpenCustomerResultObject implements Serializable {

	private static final long serialVersionUID = 8767368587870212635L;

	/**
     * 客户号
     */
    @Getter
    @Setter
    private String customerNo;
    
    /**
     * 资金账户号
     */
    @Getter
    @Setter
    private String accountNo;
    
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

    
}
