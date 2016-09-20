package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TRiskCardCommonRule;

import java.util.Map;

public interface TRiskCardCommonRuleMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TRiskCardCommonRule record);

    int insertSelective(TRiskCardCommonRule record);

    TRiskCardCommonRule selectByPrimaryKey(Long recordNo);

    int updateByPrimaryKeySelective(TRiskCardCommonRule record);

    int updateByPrimaryKey(TRiskCardCommonRule record);

    TRiskCardCommonRule  findCardRisk(Map param);
}