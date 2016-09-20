package com.huateng.p3.account.persistence;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TParaAccountBal;

public interface TParaAccountBalMapper {
    int deleteByPrimaryKey(Short recordNo);

    int insert(TParaAccountBal record);

    int insertSelective(TParaAccountBal record);

    TParaAccountBal selectByPrimaryKey(Short recordNo);

    int updateByPrimaryKeySelective(TParaAccountBal record);

    int updateByPrimaryKey(TParaAccountBal record);

    TParaAccountBal findAccountRiskCfg(@Param("accountType")String accountType,
                                       @Param("accountGrade")String accountGrade,
                                       @Param("currentDate")Date currentDate);


}