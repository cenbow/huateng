package com.huateng.p3.account.persistence;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TInfoTxnAuth;

public interface TInfoTxnAuthMapper {
    int deleteByPrimaryKey(String seqNo);

    int insert(TInfoTxnAuth record);

    int insertSelective(TInfoTxnAuth record);

    TInfoTxnAuth selectByPrimaryKey(String seqNo);

    int updateByPrimaryKeySelective(TInfoTxnAuth record);

    int updateByPrimaryKey(TInfoTxnAuth record);
    
    TInfoTxnAuth selectByAreaCodeTxnType(@Param("areaCode")String areaCode, @Param("txnType")String txnType);
}