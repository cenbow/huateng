package com.huateng.p3.account.persistence;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TLogOnlinePayment;

public interface TLogOnlinePaymentMapper {
    int deleteByPrimaryKey(String txnSerialNo);

    int insert(TLogOnlinePayment record);

    int insertSelective(TLogOnlinePayment record);

    TLogOnlinePayment selectByPrimaryKey(String transSerialNo);

    int updateByPrimaryKeySelective(TLogOnlinePayment record);

    int updateByPrimaryKey(TLogOnlinePayment record);

    List<TLogOnlinePayment> findOldNormalOnlineLog( @Param("acceptOrgSerialNo")String acceptTransSeqNo,
            @Param("acceptOrgTransDate")String acceptTransDate,
            @Param("acceptOrgTransTime")String acceptTransTime,
            @Param("accountNo")String accountNo,
            @Param("acceptOrgCode")String acceptOrgCode);

    TLogOnlinePayment findFeeOnlineLogByNormalLog(
            String normalTxnSeqNo);

    String findOrgCurrentDayTxnSum(@Param("accountNo")String accountNo,
                                   @Param("supplyOrgCode")String supplyOrgCode,
                                   @Param("transTime")Date txnTime,
                                   @Param("transType")String txnType);
    
    List<TLogOnlinePayment> findTxnDayResult(@Param("accountNo")String accountNo,
						            @Param("extBusinessType")String businessType,
						            @Param("acceptChannel")String txnChannel,
						            @Param("acceptOrgSerialNo")String acceptTransSeqNo,
						            @Param("supplyOrgCode")String supplyOrgCode,
						            @Param("terminalNo")String terminalNo,
						            @Param("startIndex")Integer startIndex,
						            @Param("endIndex")Integer endIndex);
    
    Integer findTxnDayCount(@Param("accountNo")String accountNo,
            @Param("extBusinessType")String businessType,
            @Param("acceptChannel")String txnChannel,
            @Param("acceptOrgSerialNo")String acceptTransSeqNo,
            @Param("supplyOrgCode")String supplyOrgCode,
            @Param("terminalNo")String terminalNo,
            @Param("startIndex")Integer startIndex,
            @Param("endIndex")Integer endIndex);


}