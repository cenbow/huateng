package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TLogDubiousTxn;

public interface TLogDubiousTxnMapper {
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogDubiousTxn record);

    int insertSelective(TLogDubiousTxn record);

    TLogDubiousTxn selectByPrimaryKey(String txnSeqNo);

    int updateByPrimaryKeySelective(TLogDubiousTxn record);

    int updateByPrimaryKey(TLogDubiousTxn record);
}