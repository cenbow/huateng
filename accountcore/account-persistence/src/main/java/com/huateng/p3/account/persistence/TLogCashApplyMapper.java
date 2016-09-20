package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TLogCashApply;
import com.huateng.p3.account.persistence.models.TLogCashApplyKey;

public interface TLogCashApplyMapper {
    int deleteByPrimaryKey(TLogCashApplyKey key);

    int insert(TLogCashApply record);

    int insertSelective(TLogCashApply record);

    TLogCashApply selectByPrimaryKey(TLogCashApplyKey key);

    TLogCashApply selectByTxnseqNo(String txnseqNo);

    int updateByPrimaryKeySelective(TLogCashApply record);

    int updateByPrimaryKey(TLogCashApply record);
}