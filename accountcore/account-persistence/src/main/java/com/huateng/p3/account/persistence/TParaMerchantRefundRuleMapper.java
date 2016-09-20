package com.huateng.p3.account.persistence;


import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TParaMerchantRefundRule;

public interface TParaMerchantRefundRuleMapper {
    int deleteByPrimaryKey(Short recordNo);

    int insert(TParaMerchantRefundRule record);

    int insertSelective(TParaMerchantRefundRule record);

    TParaMerchantRefundRule selectByPrimaryKey(Short recordNo);

    int updateByPrimaryKeySelective(TParaMerchantRefundRule record);

    int updateByPrimaryKey(TParaMerchantRefundRule record);

    TParaMerchantRefundRule  findMerchantRefundRule(@Param("merchantCode")String merchantCode,
                                                    @Param("currentDate")Date currentDate);
}