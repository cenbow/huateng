package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TRuleCapitalFee;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRuleCapitalFeeMapper {
    int insert(TRuleCapitalFee record);

    int insertSelective(TRuleCapitalFee record);

    List<TRuleCapitalFee> findRuleCapitalFee(@Param("settlelv") String settleLv,
                                             @Param("supplyOrgCode") String supplyOrgCode,
                                             @Param("acceptOrgCode") String acceptOrgCode,
                                             @Param("txnChannel") String txnChannel,
                                             @Param("payOrgCode") String payOrgCode,
                                             @Param("txnType") String txnType,
                                             @Param("accountType") String accountType,
                                             @Param("mchntCode") String mchntCode,
                                             @Param("lastDate") String txndate);


}