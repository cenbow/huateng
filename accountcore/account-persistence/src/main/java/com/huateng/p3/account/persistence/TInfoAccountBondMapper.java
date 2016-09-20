package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TInfoAccountBond;

public interface TInfoAccountBondMapper {
    int deleteByPrimaryKey(Short recordNo);

    int insert(TInfoAccountBond record);

    int insertSelective(TInfoAccountBond record);

    TInfoAccountBond selectByPrimaryKey(Short recordNo);

    int updateByPrimaryKeySelective(TInfoAccountBond record);

    int updateByPrimaryKey(TInfoAccountBond record);
}