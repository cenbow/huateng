package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TInfoCard;

public interface TInfoCardMapper {
    int deleteByPrimaryKey(String innerCardNo);

    int insert(TInfoCard record);

    int insertSelective(TInfoCard record);

    TInfoCard selectByPrimaryKey(String innerCardNo);


    int updateByPrimaryKeySelective(TInfoCard record);

    int updateByPrimaryKey(TInfoCard record);
}