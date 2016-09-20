package com.huateng.p3.account.common.bizparammodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @date 2013-11-07
 * @author huwenjie
 * @copyright bestpay 理财模块需要队列参数
 */
@ToString
public class BankingCoreMq {
	@Getter
	@Setter
	private String bestpayAccount;
	@Getter
	@Setter
	private String transAmount;
	@Getter
	@Setter
	private String orderNo;
	@Getter
	@Setter
	private String txnType;
	@Getter
	@Setter
	private String outBusinessType;
	@Getter
	@Setter
	private String oldOrderNo;
    @Getter
    @Setter
    private Long availableBalance;
}
