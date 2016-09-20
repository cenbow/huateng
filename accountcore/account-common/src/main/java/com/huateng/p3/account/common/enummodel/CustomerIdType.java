package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum CustomerIdType {
    // 交易渠道
	CUSTOMER_ID_TYPE_IDCARD("1", "身份证"),
	CUSTOMER_ID_TYPE_OTHER("X", "其他");


    @Getter
    private String Code;

    @Getter
    private String Desc;

    CustomerIdType(String Code, String Desc) {
        this.Code = Code;
        this.Desc = Desc;

    }
}
