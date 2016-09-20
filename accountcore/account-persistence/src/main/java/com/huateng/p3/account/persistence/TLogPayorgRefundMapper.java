package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TLogPayorgRefund;

public interface TLogPayorgRefundMapper {
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogPayorgRefund record);

    int insertSelective(TLogPayorgRefund record);

    TLogPayorgRefund selectByPrimaryKey(String txnSeqNo);

    int updateByPrimaryKeySelective(TLogPayorgRefund record);

    int updateByPrimaryKey(TLogPayorgRefund record);
}