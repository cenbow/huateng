package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TBatCutCtl;

public interface TBatCutCtlMapper {
    int deleteByPrimaryKey(Integer globalIdx);

    int insert(TBatCutCtl record);

    int insertSelective(TBatCutCtl record);

    TBatCutCtl selectByPrimaryKey(Integer globalIdx);

    int updateByPrimaryKeySelective(TBatCutCtl record);

    int updateByPrimaryKey(TBatCutCtl record);
}