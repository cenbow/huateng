package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * User: JamesTang
 * Date: 13-12-17
 * Time: 下午2:46
 */
public enum FeeType {

    FEE_TYPE_NONE("0"),// 不收取
    FEE_TYPE_FIX("1"), // 固定额
    FEE_TYPE_RATIO("2"); // 按比例   \

    @Getter
    private String feetype;

    FeeType(String feetype) {
        this.feetype = feetype;
    }


}
