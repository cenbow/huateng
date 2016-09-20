package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * User: JamesTang
 * Date: 13-12-11
 * Time: 下午5:24
 */
public enum TrueFalseLabel {

    TRUE("1"), FALSE("2");

    @Getter
    private String lablCode;

    TrueFalseLabel(String lablCode) {
        this.lablCode = lablCode;
    }


}
