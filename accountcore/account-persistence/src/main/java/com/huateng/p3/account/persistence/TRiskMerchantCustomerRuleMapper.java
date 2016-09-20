package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TRiskMerchantCustomerRule;

import java.util.Map;

public interface TRiskMerchantCustomerRuleMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TRiskMerchantCustomerRule record);

    int insertSelective(TRiskMerchantCustomerRule record);

    TRiskMerchantCustomerRule selectByPrimaryKey(Long recordNo);

    int updateByPrimaryKeySelective(TRiskMerchantCustomerRule record);

    int updateByPrimaryKey(TRiskMerchantCustomerRule record);

    TRiskMerchantCustomerRule  findMerchantCustomerRisk(Map param);



}