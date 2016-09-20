package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户登录相关的入参
 * <p/>
 * Created by IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-3-31
 */
@ToString
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1357520237253548782L;

    /**
     * 登录密码   MD5加密
     */
    @Getter
    @Setter
    private String loginPassword;
    
    /**
     * 登录Ip
     */
    @Getter
    @Setter
    private String loginIp;
    
    /**
     * 受理渠道
     */
    @Getter
    @Setter
    private String txnChannel;
    
    /**
     * 登录时间  外部不需要填写
     */
    @Getter
    @Setter
    private Date loginTime;
    
    
    
}
