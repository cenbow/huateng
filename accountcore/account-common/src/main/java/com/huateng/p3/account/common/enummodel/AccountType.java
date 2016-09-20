package com.huateng.p3.account.common.enummodel;

import com.google.common.base.Objects;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 12/3/13 10:13 AM
 */
public enum AccountType {
    FUND("1"), INTEGRAL("2");
    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public  String getValue() {
        return value;
    }
    public static AccountType explain(String value) {
        for (AccountType accountType : AccountType.values()) {
            if (Objects.equal(value, accountType.getValue())) {
                return accountType;
            }
        }
        return null;
    }
}
