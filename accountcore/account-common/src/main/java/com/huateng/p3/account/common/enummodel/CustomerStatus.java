package com.huateng.p3.account.common.enummodel;

import lombok.Getter;
import lombok.Setter;

/**
 *  @ 客户状态
 * User: JamesTang
 * Date: 13-12-6
 * Time: 上午9:17
 * To change this template use File | Settings | File Templates.
 */
public enum CustomerStatus {



    CUSTOMER_ACTIVED("2", "激活"), 
    CUSTOMER_UNACTIVE("1", "未激活"),
    CUSTOMER_STATUS_NORMAL("1", "正常"),
    CUSTOMER_STATUS_LOSTED("2", "已挂失"),
    CUSTOMER_STATUS_FROZEN("3", "已冻结"),
    CUSTOMER_STATUS_LOCKED("4", "已锁定"),
    CUSTOMER_STATUS_CLOSED("9", "已销户");
    
    


    @Getter
    @Setter
    private String customerStatusCode;

    @Getter
    @Setter
    private String customerStatusDesc;

    CustomerStatus(String customerStatusCode, String customerStatusDesc) {
        this.customerStatusCode = customerStatusCode;
        this.customerStatusDesc = customerStatusDesc;
    }


}
