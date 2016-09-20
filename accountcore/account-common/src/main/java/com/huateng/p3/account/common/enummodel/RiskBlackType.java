package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * Created by 夏海虎 on 2014/3/25.
 */
public enum RiskBlackType {

    BLACK_TYPE_BANKCARD("1", "银行卡"),
    BLACK_TYPE_IDNO("2", "身份证");

    @Getter
    private String typeCode;

    @Getter
    private String typeDesc;

    RiskBlackType(String typeCode, String typeDesc) {
        this.typeCode = typeCode;
        this.typeDesc = typeDesc;
    }
}
