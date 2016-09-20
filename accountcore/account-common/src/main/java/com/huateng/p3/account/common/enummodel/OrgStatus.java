package com.huateng.p3.account.common.enummodel;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-10
 * Time: 下午8:11
 * To change this template use File | Settings | File Templates.
 */
public enum OrgStatus {
    ORG_STATUS_NORMAL("1");

    @Getter
    @Setter
    private String status;

    OrgStatus(String status) {
        this.status = status;
    }


}
