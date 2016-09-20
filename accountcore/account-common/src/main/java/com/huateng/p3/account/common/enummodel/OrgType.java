package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-10
 * Time: 下午8:29
 * To change this template use File | Settings | File Templates.
 */
public enum OrgType {

    ORG_TYPE_MERCHANT("1"),
    ORG_TYPE_ORG("0");

    @Getter
    private String orgtype;

    OrgType(String orgtype) {
        this.orgtype = orgtype;
    } ;


   /* // 机构类型
    public final static String ORG_TYPE_MERCHANT = "1";// 商户
    public final static String ORG_TYPE_ORG = "0";// 机构*/


}
