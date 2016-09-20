package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TRiskMerchantCommonRule;

import java.util.Map;

public interface TRiskMerchantCommonRuleMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TRiskMerchantCommonRule record);

    int insertSelective(TRiskMerchantCommonRule record);

    TRiskMerchantCommonRule selectByPrimaryKey(Long recordNo);

    int updateByPrimaryKeySelective(TRiskMerchantCommonRule record);

    int updateByPrimaryKey(TRiskMerchantCommonRule record);

    TRiskMerchantCommonRule   findMerchantRisk(Map param);

}