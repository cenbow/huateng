package com.huateng.p3.account.common.bizparammodel;

import lombok.Getter;
import lombok.Setter;

/**
 * 特殊交易类型分控查询条件的参数
 * User: JamesTang
 * Date: 14-2-18
 * Time: 下午8:17
 */
public class RiskTxnTypeRuleQry {
	/**
	 * 交易类型
	 */
    @Getter
    @Setter
    private String txnType;
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
