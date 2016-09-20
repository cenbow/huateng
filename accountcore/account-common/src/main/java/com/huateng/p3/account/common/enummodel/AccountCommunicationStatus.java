package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * @author Jame Tang
 */
public enum AccountCommunicationStatus {
    COMM_STATUS_STOP("3"),   // 停机
    COMM_STATUS_REUSE("4"),      // 复机
    COMM_STATUS_FORCED_STOP("x");  // 被动拆机
    // 通信设备状态
    @Getter
    private String status;

    AccountCommunicationStatus(String status) {
        this.status = status;
    }


}
