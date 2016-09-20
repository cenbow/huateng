package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TRiskBlackMerchant;

public interface TRiskBlackMerchantMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TRiskBlackMerchant record);

    int insertSelective(TRiskBlackMerchant record);

    TRiskBlackMerchant selectByPrimaryKey(Long recordNo);

    int updateByPrimaryKeySelective(TRiskBlackMerchant record);

    int updateByPrimaryKey(TRiskBlackMerchant record);


}