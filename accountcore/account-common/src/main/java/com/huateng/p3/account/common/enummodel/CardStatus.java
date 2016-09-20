package com.huateng.p3.account.common.enummodel;

import lombok.Getter;
import lombok.Setter;

/**
 * 卡状态枚举
 * User: huwenjie
 * Date: 14-1-26
 * Time: 上午9:17
 * To change this template use File | Settings | File Templates.
 */
public enum CardStatus {



	CARD_STATUS_UNISSURE("1", "未发行"),
	CARD_STATUS_INACTIVE("2", "未启用"),
	CARD_STATUS_ACTIVE("3", "已启用"),
	CARD_STATUS_LOSTED("4", "挂失"),
	CARD_STATUS_STOP("5", "停用"),
	CARD_STATUS_LOCKED("6", "锁定/黑名单"),
	CARD_STATUS_FROZEN("7", "冻结"),
	CARD_STATUS_CLOSED("9", "注销");
    


    @Getter
    @Setter
    private String cardStatusCode;

    @Getter
    @Setter
    private String cardStatusDesc;

    CardStatus(String cardStatusCode, String cardStatusDesc) {
        this.cardStatusCode = cardStatusCode;
        this.cardStatusDesc = cardStatusDesc;
    }


}
