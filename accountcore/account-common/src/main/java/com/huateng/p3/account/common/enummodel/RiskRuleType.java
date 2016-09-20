package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum RiskRuleType {
    // 交易渠道
	RISK_RULE_TYPE_NORMAL("总风控类型", "1"),
	RISK_RULE_TYPE_SELF("单条风控", "2");
	 @Getter
    private String typeCode;

    @Getter
    private String typeDesc;

    RiskRuleType(String typeDesc, String typeCode) {
        this.typeCode = typeCode;
        this.typeDesc = typeDesc;

    }
}
