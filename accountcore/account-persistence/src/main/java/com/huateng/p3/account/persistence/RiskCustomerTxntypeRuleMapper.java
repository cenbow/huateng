package com.huateng.p3.account.persistence;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.RiskCustomerTxntypeRule;

public interface RiskCustomerTxntypeRuleMapper {
    int deleteByPrimaryKey(String recordNo);

    int insert(RiskCustomerTxntypeRule record);

    int insertSelective(RiskCustomerTxntypeRule record);

    RiskCustomerTxntypeRule selectByPrimaryKey(String recordNo);

    int updateByPrimaryKeySelective(RiskCustomerTxntypeRule record);

    int updateByPrimaryKey(RiskCustomerTxntypeRule record);


    RiskCustomerTxntypeRule selectTxnTypeRiskRule(@Param("acceptChannel")String txnChannel,
                                                  @Param("transType")String txnType,
                                                  @Param("accountType")String accountType,
                                                  @Param("userGrade")String Grade);
}