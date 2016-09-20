package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TRuleFeeCode;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRuleFeeCodeMapper {
    int insert(TRuleFeeCode record);

    int insertSelective(TRuleFeeCode record);

    List<TRuleFeeCode> selectFeeByCode(@Param("feeCode") String feecode,
                                        @Param("settlevel") String settlevel,
                                        @Param("txndate") String txndate);
}