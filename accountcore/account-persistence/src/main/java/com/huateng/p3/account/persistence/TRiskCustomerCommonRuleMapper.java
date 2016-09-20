package com.huateng.p3.account.persistence;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;

public interface TRiskCustomerCommonRuleMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TRiskCustomerCommonRule record);

    int insertSelective(TRiskCustomerCommonRule record);

  
    TRiskCustomerCommonRule findPhoneNumPayAccountRisk(Map param);

    TRiskCustomerCommonRule selectByPrimaryKey(Long recordNo);

    TRiskCustomerCommonRule findCustomerSelfDefineAccountRisk(@Param("acceptChannel")String acceptChannel,
												            @Param("accountNo")String accountNo,
												            @Param("accountType")String accountType,
												            @Param("resvFld1")String resvFld1,
												            @Param("currentDate")Date currentDate);

    TRiskCustomerCommonRule  findAccountGeneralRisk (@Param("acceptChannel")String acceptChannel,
                                                      @Param("accountType")String accountType,
                                                      @Param("grade")String grade,
                                                      @Param("currentDate")Date currentDate);

    int sychronyzeCustomerSelfDefineAccountRisk(@Param("txnChannel")String txnChannel,
                                                @Param("accountNo")String accountNo,
                                                @Param("accountType")String accountType,
                                                @Param("grade")char grade,
                                                @Param("resvFld1")String resvFld1);

    int updateByPrimaryKeySelective(TRiskCustomerCommonRule record);
    
    int updateCustomerSelfDefineAccountRisk(TRiskCustomerCommonRule record);

    int updateByPrimaryKey(TRiskCustomerCommonRule record);

    int insertNewCustomerRiskRule(TRiskCustomerCommonRule record);


}