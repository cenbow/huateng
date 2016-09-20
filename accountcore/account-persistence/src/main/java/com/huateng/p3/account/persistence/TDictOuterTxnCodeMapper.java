package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TDictOuterTxnCode;

public interface TDictOuterTxnCodeMapper {
    int deleteByPrimaryKey(String txnCode);

    int insert(TDictOuterTxnCode record);

    int insertSelective(TDictOuterTxnCode record);

    TDictOuterTxnCode selectByPrimaryKey(String txnCode);

    int updateByPrimaryKeySelective(TDictOuterTxnCode record);

    int updateByPrimaryKey(TDictOuterTxnCode record);
}