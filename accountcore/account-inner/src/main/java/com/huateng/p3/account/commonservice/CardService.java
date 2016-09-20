package com.huateng.p3.account.commonservice;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.persistence.TInfoCardMapper;
import com.huateng.p3.account.persistence.models.TInfoCard;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-1-26
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CardService {
    @Autowired
    private TInfoCardMapper tInfoCardMapper;

    public TInfoCard findCardByCardNo(String cardNo) {
    	TInfoCard card = tInfoCardMapper.selectByPrimaryKey(cardNo);
    	//卡片不存在
    	if (card == null) {
            throw new BizException(BussErrorCode.ERROR_CODE_200004.getErrorcode());
        }
    	// 账户已过期
        if (DateTime.now().toDate().after(card.getValidTime())) {
			// 卡片已过期
			throw new BizException(BussErrorCode.ERROR_CODE_210007.getErrorcode(),
                    BussErrorCode.ERROR_CODE_210007.getErrordesc());
		}
        return card;
    }
   
}
