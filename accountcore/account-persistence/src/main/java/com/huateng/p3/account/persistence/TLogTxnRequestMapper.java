package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TLogTxnRequest;

public interface TLogTxnRequestMapper {
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogTxnRequest record);

    int insertSelective(TLogTxnRequest record);

    TLogTxnRequest selectByPrimaryKey(String txnSeqNo);

    int updateByPrimaryKeySelective(TLogTxnRequest record);

    int updateByPrimaryKey(TLogTxnRequest record);
}