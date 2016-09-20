package com.huateng.p3.account.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;

public interface TLogOnlinePaymentHisMapper {
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogOnlinePaymentHis record);

    int insertSelective(TLogOnlinePaymentHis record);

    TLogOnlinePaymentHis selectByPrimaryKey(String transSerialNo);

    int updateByPrimaryKeySelective(TLogOnlinePaymentHis record);

    int updateByPrimaryKey(TLogOnlinePaymentHis record);

    int updateHisReturnFlagByTablename(@Param("returnFlag")String returnFlag,
                                    @Param("returnCount")Long returnCount,
                                    @Param("returnAmount")Long returnAmt,
                                    @Param("transSerialNo") String txnSeqNo,
                                    @Param("tableName") String tableName);

    List<TLogOnlinePaymentHis> findOnlineLogHisByTablename( @Param("acceptOrgSerialNo")String acceptTransSeqNo,
                                                  @Param("acceptOrgTransDate")String acceptTransDate,
                                                  @Param("acceptOrgTransTime")String acceptTransTime,
                                                  @Param("accountNo")String accountNo,
                                                  @Param("terminalSerialNo")String terminalSeqNo,
                                                  @Param("acceptOrgCode")String acceptOrgCode,
                                                  @Param("tableName") String tableName);

    List<TLogOnlinePaymentHis> findOnlineLogHisByTablenameAndTxnno(@Param("transSerialNo")String txnSeqNo,
            @Param("tableName") String tableName);

    String findOrgMonthTxnSum(@Param("accountNo")String accountNo,
                              @Param("supplyOrgCode")String supplyOrgCode,
                              @Param("transTime")String txnTime,
                              @Param("transType")String txnType);

    List<TLogOnlinePaymentHis> findOnlineLogHisByTablenameinlclueNoClean( @Param("acceptOrgSerialNo")String acceptTransSeqNo,
                                                                    @Param("acceptOrgTransDate")String acceptTransDate,
                                                                    @Param("acceptOrgTransTime")String acceptTransTime,
                                                                    @Param("accountNo")String accountNo,
                                                                    @Param("terminalSerialNo")String terminalSeqNo,
                                                                    @Param("acceptOrgCode")String acceptOrgCode,
                                                                    @Param("tableName") String tableName);
    
    List<TLogOnlinePaymentHis> findTxnHisResult(@Param("accountNo")String accountNo,
            @Param("extBusinessType")String businessType,
            @Param("acceptChannel")String txnChannel,
            @Param("acceptOrgSerialNo")String acceptTransSeqNo,
            @Param("supplyOrgCode")String supplyOrgCode,
            @Param("terminalNo")String terminalNo,
            @Param("startDate")Date startDate,
            @Param("endDate")Date endDate,
            @Param("startIndex")Integer startIndex,
            @Param("endIndex")Integer endIndex);
    
    Integer findTxnHisCount(@Param("accountNo")String accountNo,
            @Param("extBusinessType")String businessType,
            @Param("acceptChannel")String txnChannel,
            @Param("acceptOrgSerialNo")String acceptTransSeqNo,
            @Param("supplyOrgCode")String supplyOrgCode,
            @Param("terminalNo")String terminalNo,
            @Param("startDate")Date startDate,
            @Param("endDate")Date endDate,
            @Param("startIndex")Integer startIndex,
            @Param("endIndex")Integer endIndex);
            
    
    
}