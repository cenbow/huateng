package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TDictAreaCity;

public interface TDictAreaCityMapper {
    int deleteByPrimaryKey(String currentCode);

    int insert(TDictAreaCity record);

    int insertSelective(TDictAreaCity record);

    TDictAreaCity selectByPrimaryKey(String currentCode);
    
    TDictAreaCity queryAreaCityCodeByProductNo(String mobileNo);

    int updateByPrimaryKeySelective(TDictAreaCity record);

    int updateByPrimaryKey(TDictAreaCity record);
}