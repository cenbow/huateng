package com.huateng.p3.account.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TInfoProductProperty;

public interface TInfoProductPropertyMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(TInfoProductProperty record);

    int insertSelective(TInfoProductProperty record);

    TInfoProductProperty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TInfoProductProperty record);

    int updateByPrimaryKey(TInfoProductProperty record);

    TInfoProductProperty  queryProductPropertyRecordByConditions(@Param("productNo")String productNo,
                                                                 @Param("productCode")String productCode,
                                                                 @Param("productStatus")String productStatus,
                                                                 @Param("txnStatus")String txnStatus);

    List<TInfoProductProperty> queryProductPropertyRecordByConditionsByProduct(String productNo);
}