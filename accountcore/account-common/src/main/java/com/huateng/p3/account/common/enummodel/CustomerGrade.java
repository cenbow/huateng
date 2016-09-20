package com.huateng.p3.account.common.enummodel;

import lombok.Getter;
import lombok.Setter;

/**
 *  @ 客户等级
 * User: huwenjie
 * Date: 14-1-9
 * Time: 下午5:17
 * To change this template use File | Settings | File Templates.
 */
public enum CustomerGrade {



	CUSTOMER_ACCOUNT_GRADE_GENERAL("1", "普通客户"),
	CUSTOMER_ACCOUNT_GRADE_NOREALNAME("3", "非实名用户"),
	CUSTOMER_ACCOUNT_GRADE_PRIMARY("4", "初级实名用户"),
	CUSTOMER_ACCOUNT_GRADE_SENIOR("5", "高级实名用户"),
	CUSTOMER_ACCOUNT_GRADE_ENTERPRISE("2", "企业用户");


    @Getter
    @Setter
    private String customerGradeCode;

    @Getter
    @Setter
    private String customerGradeDesc;

    CustomerGrade(String customerGradeCode, String customerGradeDesc) {
        this.customerGradeCode = customerGradeCode;
        this.customerGradeDesc = customerGradeDesc;
    }


}
