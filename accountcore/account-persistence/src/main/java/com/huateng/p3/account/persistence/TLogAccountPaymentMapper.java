package com.huateng.p3.account.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TLogAccountPayment;

public interface TLogAccountPaymentMapper {
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogAccountPayment record);

    int insertSelective(TLogAccountPayment record);

    TLogAccountPayment selectByPrimaryKey(String txnSeqNo);

    int updateByPrimaryKeySelective(TLogAccountPayment record);

    int updateByPrimaryKey(TLogAccountPayment record);

    TLogAccountPayment findByClearingSeqNo(@Param("clearingSeqNo")String clearingSeqNo,
                                           @Param("txnLabel")String txnLabel);

    List<TLogAccountPayment> findPaymentResult(@Param("accountNo")String accountNo,
            @Param("businessType")String businessType,
            @Param("txnType")String txnType,
            @Param("txnChannel")String txnChannel,
            @Param("startDate")Date startDate,
            @Param("endDate")Date endDate,
            @Param("startIndex")Integer startIndex,
            @Param("endIndex")Integer endIndex);

    Long findPaymentCount(@Param("accountNo")String accountNo,
            @Param("businessType")String businessType,
            @Param("txnType")String txnType,
            @Param("txnChannel")String txnChannel,
            @Param("startDate")Date startDate,
            @Param("endDate")Date endDate);
}