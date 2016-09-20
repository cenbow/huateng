package com.huateng.p3.account.common.enummodel;

import lombok.Getter;
import lombok.Setter;

/**
 * 绑定卡数枚举
 * User: lizhongwei
 * Date: 14-09-04
 * Time: 下午17:56
 */
public enum AllowBindingCardMaxNum {



	CUSTOMER_ALLOW_BINDING_CARD_MAX_NUM(1),// 客户允许绑定的最多卡数
	ACCOUNT_ALLOW_BINDING_BANKCARD_MAX_NUM(3);//农村金融客户允许绑定的最多卡数

	 @Getter
	    private int AllowBidingCardMaxNum;

	 AllowBindingCardMaxNum(int AllowBidingCardMaxNum) {
	        this.AllowBidingCardMaxNum = AllowBidingCardMaxNum;
	    }

}
