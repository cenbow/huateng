package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum Carrieroperator {
    // 运营商
	CARRIEROPERATOR_DX("dx", "电信"),
	CARRIEROPERATOR_YD("yd", "移动"),
	CARRIEROPERATOR_LT("lt", "联通");

    @Getter
    private String Code;

    @Getter
    private String Desc;

    Carrieroperator(String Code, String Desc) {
        this.Code = Code;
        this.Desc = Desc;
    }
}
