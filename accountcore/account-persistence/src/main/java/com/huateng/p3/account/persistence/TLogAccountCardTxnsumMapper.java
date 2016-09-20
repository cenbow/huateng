package com.huateng.p3.account.persistence;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TLogAccountCardTxnsum;
import com.huateng.p3.account.persistence.models.TLogAccountCardTxnsumKey;

public interface TLogAccountCardTxnsumMapper {
    int deleteByPrimaryKey(TLogAccountCardTxnsumKey key);

    int insert(TLogAccountCardTxnsum record);

    int insertSelective(TLogAccountCardTxnsum record);

    TLogAccountCardTxnsum selectByPrimaryKey(TLogAccountCardTxnsumKey key);

    int updateByPrimaryKeySelective(TLogAccountCardTxnsum record);

    int updateByPrimaryKey(TLogAccountCardTxnsum record);

    TLogAccountCardTxnsum findToCheck(@Param("txnChannel")String txnChannel,
                                      @Param("type")String type,
                                      @Param("accountId")String accountId);
}