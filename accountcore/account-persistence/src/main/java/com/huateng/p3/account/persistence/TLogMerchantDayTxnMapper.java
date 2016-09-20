package com.huateng.p3.account.persistence;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TLogMerchantDayTxn;
import com.huateng.p3.account.persistence.models.TLogMerchantDayTxnKey;

public interface TLogMerchantDayTxnMapper {
    int deleteByPrimaryKey(TLogMerchantDayTxnKey key);

    int insert(TLogMerchantDayTxn record);

    int insertSelective(TLogMerchantDayTxn record);

    TLogMerchantDayTxn selectByPrimaryKey(TLogMerchantDayTxnKey key);

    int updateByPrimaryKeySelective(TLogMerchantDayTxn record);

    int updateByPrimaryKey(TLogMerchantDayTxn record);

    int updateMerchantDayTxnAmt(@Param("lastTxnDate")String lastTxnDate,
                                @Param("txnAmt")Long txnAmt,
                                @Param("merchantCode")String merchantCode,
                                @Param("txnChannel")String txnChannel,
                                @Param("businessType")String businessType);

    int updateMerchantDayTxnAmtChgDate(@Param("lastTxnDate")String lastTxnDate,
                                       @Param("txnAmt")Long txnAmt,
                                       @Param("merchantCode")String merchantCode,
                                       @Param("txnChannel")String txnChannel,
                                       @Param("businessType")String businessType);

}