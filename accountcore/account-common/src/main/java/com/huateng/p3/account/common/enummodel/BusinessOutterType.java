package com.huateng.p3.account.common.enummodel;

import com.google.common.base.Objects;

import lombok.Getter;

public enum BusinessOutterType {
	
	//管理类
	BUS_TYPE_121010("121010", "资金账户网银充值"),
	BUS_TYPE_181000("181000", "提现申请");
	

    @Getter
    private String businessOutterType;

    @Getter
    private String businessOutterTypeDesc;

    BusinessOutterType(String businessOutterType, String businessOutterTypeDesc) {
    	this.businessOutterType = businessOutterType;
    	this.businessOutterTypeDesc = businessOutterTypeDesc;
    }
    
    
    public static String explain(String businessOutterType) {
        for (BusinessOutterType busType : BusinessOutterType.values()) {
            if (Objects.equal(businessOutterType, busType.businessOutterType)) {
                return busType.businessOutterTypeDesc;
            }
        }
        return businessOutterType;
    }
}
