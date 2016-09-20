package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-6
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public enum OrgDefaultCode {

    GROUP_AREA_CODE ("北京速通科技地区代码","999900"),

    GROUP_ORG_CODE("北京速通科技机构号码", "001999900000000"),

    GROUP_ORG_CODE_CASH("北京速通科技机构号码(提现)", "001999900000000"),

    PROVINCE_POSP_MERCHANT_CODE("省POSP虚拟商户号", "333333333333333"),

    WEBGATE_MERCHANT_CODE("GROUP_ORG_CODE", "004110000000000"),

    PROVINCE_ORG_PROV_MERCHANT_CODE("省机构密钥虚拟商户号", "222222222222222"),

    VIRTUAL_MERCHANT_CODE("网站类虚拟商户号", "999999999999999"),

    VIRTUAL_TERMINAL_NO("网站类虚拟终端号", "99999999"),

    PROVINCE_ORG_PROV_SEQNO("省机构密钥虚拟商户号", "111");


    @Getter
    private String orgCode;

    @Getter
    private String orgDesc;

    OrgDefaultCode(String orgDesc, String orgCode) {
        this.orgCode = orgCode;
        this.orgDesc = orgDesc;
    }


}
