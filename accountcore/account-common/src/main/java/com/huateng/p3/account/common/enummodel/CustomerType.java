package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum CustomerType {
    // 客户类型
	CUSTOMER_TYPE_PERSON("1", "个人"),
	CUSTOMER_TYPE_UNIT("2", "单位"),
	CUSTOMER_TYPE_BST_PERSON("A", "百事购卡"),
	CUSTOMER_TYPE_TYCARD_PERSON("B", "天翼支付卡");


    @Getter
    private String Code;

    @Getter
    private String Desc;

    CustomerType(String Code, String Desc) {
        this.Code = Code;
        this.Desc = Desc;

    }
}
