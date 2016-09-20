package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 *  @ 客户性别
 * User: huwenjie
 * Date: 14-1-26
 * Time: 下午5:17
 * To change this template use File | Settings | File Templates.
 */
public enum CustomerGender {



	CUSTOMER_GENDER_MALE("1", "男"),
	CUSTOMER_GENDER_FEMALE("2", "女"),
	CUSTOMER_GENDER_UNPOINT("9", "未知");
	
    @Getter
    private String customerGenderCode;

    @Getter
    private String customerGenderDesc;

    CustomerGender(String customerGenderCode, String customerGenderDesc) {
        this.customerGenderCode = customerGenderCode;
        this.customerGenderDesc = customerGenderDesc;
    }


}
