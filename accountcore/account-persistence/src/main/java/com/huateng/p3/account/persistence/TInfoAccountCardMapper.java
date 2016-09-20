package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoAccountCard;

import java.util.List;

public interface TInfoAccountCardMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TInfoAccountCard record);

    int insertSelective(TInfoAccountCard record);

    TInfoAccountCard selectByPrimaryKey(Long recordNo);

    int updateByPrimaryKeySelective(TInfoAccountCard record);

    TInfoAccountCard  findAccountCardByCardNo(String cardNo);
    TInfoAccountCard  findAccountCardByAccountNo(String accountNo);

    int updateByPrimaryKey(TInfoAccountCard record);

    List<String> findLinkCardNo(String accountNo);

    int checkClosedCustomerByCardNo(String cardNo);

    int  unbindingCardByOfflineAccountNo(TInfoAccountCard record);
}