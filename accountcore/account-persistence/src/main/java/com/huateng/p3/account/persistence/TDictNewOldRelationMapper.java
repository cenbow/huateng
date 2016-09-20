package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TDictNewOldRelation;

import java.util.List;

public interface TDictNewOldRelationMapper {
    int deleteByPrimaryKey(String newTableName);

    int insert(TDictNewOldRelation record);

    int insertSelective(TDictNewOldRelation record);

    TDictNewOldRelation selectByPrimaryKey(String newTableName);

    int updateByPrimaryKeySelective(TDictNewOldRelation record);

    int updateByPrimaryKey(TDictNewOldRelation record);

    List<TDictNewOldRelation>  selectByOldTableName(String oldtable);  //t_online_payment_his


}