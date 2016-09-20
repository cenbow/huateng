package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户密保问题相关的入参
 * <p/>
 * Created by IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-7-16
 */
@ToString
public class SecurityResultObject implements Serializable {


	private static final long serialVersionUID = -36102262379446265L;
	
	/**
     * 电信产品号
     */
    @Getter
    @Setter
    private String productNo;
    
    /**
     * 账户号
     */
    @Getter
    @Setter
    private String accountNo;
    
    /**
     * 客户号
     */
    @Getter
    @Setter
    private String customerNo;
	
	/**
     * 密保问题
     */
    @Getter
    @Setter
    private String secrurityQuestion;
    
    /**
     * 密保问题内容
     */
    @Getter
    @Setter
    private String secrurityQuestionDesc;

    /**
     * 密保答案
     */
    @Getter
    @Setter
    private String secrurityAnwser;
    
    
    
}
