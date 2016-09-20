package com.huateng.p3.account.common.bizparammodel;

import lombok.Getter;
import lombok.Setter;

/**
 * 交易总类分控查询条件的参数
 * User: huwenjie
 * Date: 14-4-9
 * Time: 上午8:17
 */
public class RiskAllRuleQry {

	/** 
     * 交易渠道
     */
    @Getter
    @Setter
    private String txnChannel;
    /**
     * 账号类型
     */
    @Getter
    @Setter
    private String accountType;
    /**
     * 用户等级
     */
    @Getter
    @Setter
    private String userGrade;


}
