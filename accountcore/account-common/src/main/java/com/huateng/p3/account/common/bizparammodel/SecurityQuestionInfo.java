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
public class SecurityQuestionInfo implements Serializable {


	private static final long serialVersionUID = -36100262379446265L;

	/**
     * 密保问题
     */
    @Getter
    @Setter
    private String secrurityQuestion;
    

    /**
     * 密保答案
     */
    @Getter
    @Setter
    private String secrurityAnwser;
    
    
    
}
