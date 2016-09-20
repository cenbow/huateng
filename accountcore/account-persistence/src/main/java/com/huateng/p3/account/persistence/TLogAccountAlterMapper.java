package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TLogAccountAlter;

public interface TLogAccountAlterMapper {
    int deleteByPrimaryKey(Short recordNo);

    int insert(TLogAccountAlter record);

    int insertSelective(TLogAccountAlter record);

    TLogAccountAlter selectByPrimaryKey(Short recordNo);

    int updateByPrimaryKeySelective(TLogAccountAlter record);

    int updateByPrimaryKey(TLogAccountAlter record);
}