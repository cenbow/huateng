package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.RiskLogacnttxnPayment;

import org.apache.ibatis.annotations.Param;

public interface RiskLogacnttxnPaymentMapper {
    int deleteByPrimaryKey(String recordNo);

    int insert(RiskLogacnttxnPayment record);

    int insertSelective(RiskLogacnttxnPayment record);

    RiskLogacnttxnPayment selectByPrimaryKey(String recordNo);

    int updateByPrimaryKeySelective(RiskLogacnttxnPayment record);

    int updateByPrimaryKey(RiskLogacnttxnPayment record);

    RiskLogacnttxnPayment selectByTxnKey(@Param("accountNo")String accountNo,
            @Param("transBigtype") String transBigtype);
    
    RiskLogacnttxnPayment selectByTxnKeyForUpdate(@Param("accountNo")String accountNo,
            @Param("transBigtype") String transBigtype);
}