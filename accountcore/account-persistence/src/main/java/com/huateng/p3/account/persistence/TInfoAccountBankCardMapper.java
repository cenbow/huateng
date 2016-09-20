package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;

import java.util.List;

public interface TInfoAccountBankCardMapper {

    int insert(TInfoAccountBankCard tInfoAccountBankCard);
    TInfoAccountBankCard  findBankCardByBankCardNo(String backCardNo);
    List<TInfoAccountBankCard> findBankListCardByAccountNo(String fundAccountNo);
    int checkClosedBankCardNo(String bankCardNo);
    int unbindBankCardByBankCardNo(TInfoAccountBankCard tInfoAccountBankCard);
    int updateBankCardBingdingMethod(TInfoAccountBankCard tInfoAccountBankCard);
}