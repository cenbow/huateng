package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

public enum CustomerIsClosingAccount {
	LABEL_TRUE("1"),   // 是
	LABEL_FALSE("0");      // 否
	    // 是否銷戶
	    @Getter
	    private String status;

	    CustomerIsClosingAccount(String status) {
	        this.status = status;
	    }

}
