package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 客户实名认证国政通返回状态枚举
 * User: huwenjie
 * Date: 14-1-26
 * Time: 上午12:32
 * To change this template use File | Settings | File Templates.
 */
public  enum  CustomerRealnameStatus {
 
    //实名制国政通返回状态
    CUSTOMER_REALNAME_STATUS_SUCCESS("0","成功"),
    CUSTOMER_REALNAME_STATUS_APPLYING("1","申请中"),
    CUSTOMER_REALNAME_STATUS_FAILURE("2","失败");

    @Getter
    private String customerRealnameStatusCode;
    @Getter
    private String customerRealnameStatusDesc;

    private CustomerRealnameStatus(String customerRealnameStatusCode, String customerRealnameStatusDesc) {
        this.customerRealnameStatusCode = customerRealnameStatusCode;
        this.customerRealnameStatusDesc = customerRealnameStatusDesc;
    }

    
}
