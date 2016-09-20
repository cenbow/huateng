package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * @author Jame Tang
 */
public enum AccountStatus {
    ACCOUNT_STATUS_UNACTIVE("0"),   // 未激活
    ACCOUNT_STATUS_NORMAL("1"),      // 正常
    ACCOUNT_STATUS_LOSTED("2"),  // 已挂失
    ACCOUNT_STATUS_FROZEN("3"), // 已冻结
    ACCOUNT_STATUS_LOCKED("4"),// 已锁定
    ACCOUNT_STATUS_CLOSED("9"); // 已销户

    @Getter
    private String status;

    AccountStatus(String status) {
        this.status = status;
    }


}
