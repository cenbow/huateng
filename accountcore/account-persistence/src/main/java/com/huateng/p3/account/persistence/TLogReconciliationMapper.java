package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TLogReconciliation;

public interface TLogReconciliationMapper {
    int deleteByPrimaryKey(String recordNo);

    int insert(TLogReconciliation record);

    int insertSelective(TLogReconciliation record);

    TLogReconciliation selectByPrimaryKey(String recordNo);

    int updateByPrimaryKeySelective(TLogReconciliation record);

    int updateByPrimaryKey(TLogReconciliation record);
}