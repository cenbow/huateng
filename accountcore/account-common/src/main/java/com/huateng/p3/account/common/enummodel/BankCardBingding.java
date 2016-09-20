package com.huateng.p3.account.common.enummodel;

import lombok.Getter;
import lombok.Setter;

/**
 * 银行卡转入转出类型枚举
 * User: lizhongwei
 * Date: 14-09-05
 * Time: 下午14:56
 */
public enum BankCardBingding {



	BANK_CARD_BINGDING_TYPE_IN("1"),// 银行卡转入类型
	BANK_CARD_BINGDING_TYPE_OUT ("2"),//银行卡转出类型
	
	BANK_CARD_BINGDING_FLAG_BIND("1");//银行卡已经绑定

	 @Getter
	    private String BankCardBingding;

	 BankCardBingding(String BankCardBingding) {
	        this.BankCardBingding = BankCardBingding;
	    }

}
