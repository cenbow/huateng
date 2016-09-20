package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * @author huwenjie
 */
public enum AccountName {
	DEFAULT_ACCOUNT_NAME_FUND("我的资金子账户"),
	DEFAULT_ACCOUNT_NAME_INTEGRAL("我的积分子账户");

    @Getter
    private String accountName;

    AccountName(String accountName) {
        this.accountName = accountName;
    }


}
