package com.huateng.p3.hub.persistence.mapper;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.hub.persistence.model.TLogOnlinePayment;


public interface TLogOnlinePaymentMapper extends BaseMapper<TLogOnlinePayment>{
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogOnlinePayment record);

    int insertSelective(TLogOnlinePayment record);

    TLogOnlinePayment selectByPrimaryKey(String txnSeqNo);

    int updateByPrimaryKeySelective(TLogOnlinePayment record);

    int updateByPrimaryKey(TLogOnlinePayment record);

    List<TLogOnlinePayment> findOldNormalOnlineLog( @Param("acceptTransSeqNo")String acceptTransSeqNo,
            @Param("acceptTransDate")String acceptTransDate,
            @Param("acceptTransTime")String acceptTransTime,
            @Param("accountNo")String accountNo,
            @Param("acceptOrgCode")String acceptOrgCode);

    TLogOnlinePayment findFeeOnlineLogByNormalLog(
            String normalTxnSeqNo);

    String findOrgCurrentDayTxnSum(@Param("accountNo")String accountNo,
                                   @Param("supplyOrgCode")String supplyOrgCode,
                                   @Param("txnTime")Date txnTime,
                                   @Param("txnType")String txnType);
    
    List<TLogOnlinePayment> findTxnDayResult(@Param("accountNo")String accountNo,
						            @Param("businessType")String businessType,
						            @Param("txnChannel")String txnChannel,
						            @Param("acceptTransSeqNo")String acceptTransSeqNo,
						            @Param("supplyOrgCode")String supplyOrgCode,
						            @Param("terminalNo")String terminalNo,
						            @Param("startIndex")Integer startIndex,
						            @Param("endIndex")Integer endIndex);
    
    Integer findTxnDayCount(@Param("accountNo")String accountNo,
            @Param("businessType")String businessType,
            @Param("txnChannel")String txnChannel,
            @Param("acceptTransSeqNo")String acceptTransSeqNo,
            @Param("supplyOrgCode")String supplyOrgCode,
            @Param("terminalNo")String terminalNo,
            @Param("startIndex")Integer startIndex,
            @Param("endIndex")Integer endIndex);


}