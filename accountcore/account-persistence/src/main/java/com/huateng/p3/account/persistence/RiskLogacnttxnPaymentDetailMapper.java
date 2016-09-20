package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.RiskLogacnttxnPaymentDetail;

import org.apache.ibatis.annotations.Param;

public interface RiskLogacnttxnPaymentDetailMapper {
    int deleteByPrimaryKey(String recordNo);

    int insert(RiskLogacnttxnPaymentDetail record);

    int insertSelective(RiskLogacnttxnPaymentDetail record);

    RiskLogacnttxnPaymentDetail selectByPrimaryKey(String recordNo);

    int updateByPrimaryKeySelective(RiskLogacnttxnPaymentDetail record);

    int updateByPrimaryKey(RiskLogacnttxnPaymentDetail record);

    RiskLogacnttxnPaymentDetail selectByTxnKey(@Param("accountNo")String accountNo,
                                         @Param("acceptChannel") String txnChannel,
                                         @Param("transType") String txnType);
    
    RiskLogacnttxnPaymentDetail selectByTxnKeyForUpdate(@Param("accountNo")String accountNo,
            @Param("acceptChannel") String txnChannel,
            @Param("transType") String txnType);
}