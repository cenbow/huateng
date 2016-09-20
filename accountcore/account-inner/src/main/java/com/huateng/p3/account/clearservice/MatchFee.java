package com.huateng.p3.account.clearservice;

/**
 * Created with IntelliJ IDEA.
 * User: houyue
 * Date: 14-2-11
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public enum MatchFee {
        COMPLETE_MATCH("完全匹配"),

        DEFAULT_ACCOUNT_TYPE("通用账户匹配"),

        DEFAULT_CHANNEL("默认受理渠道匹配"),

        DEFAULT_MCHNT_TYPE("默认商户类型匹配"),

        DEFAULT_PAY_ORG("默认支付机构代码匹配"),

        DEFAULT_ACCEPT_ORG ("默认受理机构匹配"),

        DEFAULT_SUPPLY_ORG ("默认出单机构匹配");

        private final String value;

        MatchFee(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
}
