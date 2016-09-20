package com.huateng.p3.account.clearservice;

import lombok.Getter;
import lombok.Setter;

import com.huateng.p3.account.persistence.models.TRuleCapitalFee;

/**
 * User: JamesTang
 * Date: 14-7-9
 * Time: 下午6:15
 */
public class CapitalFeeMatcher {

    @Setter
    @Getter
    private String capitalFeeRuleKey;

    @Setter
    @Getter
    private TRuleCapitalFee tRuleCapitalFee;

}
