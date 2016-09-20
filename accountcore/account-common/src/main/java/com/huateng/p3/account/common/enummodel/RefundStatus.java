package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * User: JamesTang
 * Date: 13-12-13
 * Time: 下午1:57
 */
public enum RefundStatus {
	REFUND_STATUS_APPLY("1", "申请"), 
	REFUND_STATUS_REVOKE("2", "商户撤回"), 
	REFUND_STATUS_FIRST_PASS("3", "一审通过"),
	REFUND_STATUS_REFUSAL("4", "中心拒绝"),
	REFUND_STATUS_SECOND_PASS("5", "二审通过"),
	REFUND_STATUS_SUCCESS("6", "处理成功");
	
	@Getter
	private String refundStatusCode;
	@Getter
	private String refundStatusDesc;
	

	private RefundStatus(String refundStatusCode, String refundStatusDesc) {
		this.refundStatusCode = refundStatusCode;
		this.refundStatusDesc = refundStatusDesc;
	}
}
