package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TInfoBondMerchantKey;

public interface TInfoBondMerchantMapper {
    int deleteByPrimaryKey(TInfoBondMerchantKey key);

    int insert(TInfoBondMerchantKey record);

    int insertSelective(TInfoBondMerchantKey record);
}