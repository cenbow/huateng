package com.huateng.p3.account.persistence;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TInfoOrg;

public interface TInfoOrgMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TInfoOrg record);

    int insertSelective(TInfoOrg record);

    TInfoOrg selectByPrimaryKey(Long recordNo);
    
    TInfoOrg selectByOrgCode(String orgCode);

    int updateByPrimaryKeySelective(TInfoOrg record);

    int updateByPrimaryKey(TInfoOrg record);

    TInfoOrg findBlackMerchant(@Param("merchantCode")String merchantCode,
                               @Param("currentDate")Date currentDate);


    String  selectMchntType (@Param("supplyOrgCode")String supplyorgcode);
}