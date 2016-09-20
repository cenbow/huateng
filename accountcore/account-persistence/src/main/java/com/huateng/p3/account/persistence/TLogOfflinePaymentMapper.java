package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TLogOfflinePayment;

public interface TLogOfflinePaymentMapper {
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogOfflinePayment record);

    int insertSelective(TLogOfflinePayment record);

    TLogOfflinePayment selectByPrimaryKey(String txnSeqNo);

    int updateByPrimaryKeySelective(TLogOfflinePayment record);

    int updateByPrimaryKey(TLogOfflinePayment record);
}