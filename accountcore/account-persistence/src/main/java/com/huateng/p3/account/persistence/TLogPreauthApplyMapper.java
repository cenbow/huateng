package com.huateng.p3.account.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TLogPreauthApply;

public interface TLogPreauthApplyMapper {
    int deleteByPrimaryKey(String txnSeqNo);

    int insert(TLogPreauthApply record);

    int insertSelective(TLogPreauthApply record);

    TLogPreauthApply selectByPrimaryKey(String txnSeqNo);

     List<TLogPreauthApply>  findOldPreAuthApply(@Param("transSeqType")String transSeqType,
                                                 @Param("acceptOrgCode")String acceptOrgCode,
                                                 @Param("acceptTransSeqNo")String acceptTransSeqNo,
                                                 @Param("acceptTransDate")String acceptTransDate,
                                                 @Param("acceptTransTime")String acceptTransTime);
    TLogPreauthApply findPreAuthApply(@Param("preAuthNo")String preAuthNo,
                                      @Param("supplyOrgCode")String supplyOrgCode,
                                      @Param("accountNo")String accountNo,
                                      @Param("transSeqType")String transSeqType);

    int updateByPrimaryKeySelective(TLogPreauthApply record);

    int updateByPrimaryKey(TLogPreauthApply record);


}