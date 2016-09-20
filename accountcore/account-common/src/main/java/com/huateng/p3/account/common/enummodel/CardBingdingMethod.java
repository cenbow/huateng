package com.huateng.p3.account.common.enummodel;

import lombok.Getter;

/**
 * 卡绑定类型枚举
 * User: huwenjie
 * Date: 14-1-26
 * Time: 上午9:17
 * To change this template use File | Settings | File Templates.
 */
public enum CardBingdingMethod {



	CARD_BINGDING_METHOD_MASTER("1", "主卡绑定"),
	CARD_BINGDING_METHOD_SLAVE("2", "副卡绑定");
	
    @Getter
    private String cardBingdingMethodCode;

    @Getter
    private String cardBingdingMethodDesc;

    CardBingdingMethod(String cardBingdingMethodCode, String cardBingdingMethodDesc) {
        this.cardBingdingMethodCode = cardBingdingMethodCode;
        this.cardBingdingMethodDesc = cardBingdingMethodDesc;
    }


}
