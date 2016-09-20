package com.huateng.p3.account.common.enummodel;

import com.google.common.base.Objects;

import lombok.Getter;

/**
 * 客户实名认证枚举
 * User: wangshushuang
 * Date: 13-12-12
 * Time: 上午6:32
 * To change this template use File | Settings | File Templates.
 */
public  enum  CustomerRealname {

    //客户实名认证
    CUSTOMER_REALNAME_TRUE("1", "实名认证"),
    CUSTOMER_REALNAME_DYNAMIC ("2", "动态实名认证"),
    
    CUSTOMER_REALNAME_FALSE("3","未实名认证"),
    CUSTOMER_REALNAME_HIGH ("4","高级实名认证");


    @Getter
    private final String customerRealnameCode;
    @Getter
    private final String customerRealnameDesc;

    private CustomerRealname(String customerRealnameCode, String customerRealnameDesc) {
        this.customerRealnameCode = customerRealnameCode;
        this.customerRealnameDesc = customerRealnameDesc;
    }
    
    public static CustomerRealname explain(String customerRealnameCode) {
        for (CustomerRealname customerRealname : CustomerRealname.values()) {
            if (Objects.equal(customerRealnameCode, customerRealname.getCustomerRealnameCode())) {
                return customerRealname;
            }
        }
        return null;
    }
    
}
