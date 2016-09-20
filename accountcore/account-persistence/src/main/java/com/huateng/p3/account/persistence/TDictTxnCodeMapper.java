package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TDictTxnCode;

public interface TDictTxnCodeMapper {
    int deleteByPrimaryKey(String txnCode);

    int insert(TDictTxnCode record);

    int insertSelective(TDictTxnCode record);

    TDictTxnCode selectByPrimaryKey(String txnCode);

    int updateByPrimaryKeySelective(TDictTxnCode record);

    int updateByPrimaryKey(TDictTxnCode record);
}