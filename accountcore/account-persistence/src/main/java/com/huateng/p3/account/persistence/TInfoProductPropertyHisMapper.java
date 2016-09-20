package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TInfoProductPropertyHis;

public interface TInfoProductPropertyHisMapper {
    int deleteByPrimaryKey(String id);

    int insert(TInfoProductPropertyHis record);

    int insertSelective(TInfoProductPropertyHis record);

    TInfoProductPropertyHis selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TInfoProductPropertyHis record);

    int updateByPrimaryKey(TInfoProductPropertyHis record);
}