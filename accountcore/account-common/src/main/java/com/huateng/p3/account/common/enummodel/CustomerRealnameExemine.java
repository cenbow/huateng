package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 客户实名认证审核状态枚举
 * User: huwenjie
 * Date: 14-1-26
 * Time: 上午12:32
 * To change this template use File | Settings | File Templates.
 */
public  enum  CustomerRealnameExemine {
	
    //实名制审核状态
    CUSTOMER_REALNAME_EXAMINE_BEFORE("1","未审核"),
    CUSTOMER_REALNAME_EXAMINE_PASS("2","审核通过"),
    CUSTOMER_REALNAME_EXAMINE_NOPASS("3","审核未通过");
    @Getter
    private String customerRealnameExemineCode;
    @Getter
    private String customerRealnameExemineDesc;

    private CustomerRealnameExemine(String customerRealnameExemineCode, String customerRealnameExemineDesc) {
        this.customerRealnameExemineCode = customerRealnameExemineCode;
        this.customerRealnameExemineDesc = customerRealnameExemineDesc;
    }

    
}
