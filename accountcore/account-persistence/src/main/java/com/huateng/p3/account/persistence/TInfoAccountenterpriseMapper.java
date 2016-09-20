package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoAccountenterprise;

public interface TInfoAccountenterpriseMapper {
    int deleteByPrimaryKey(String enterpriseCustomerno);

    int insert(TInfoAccountenterprise record);

    int insertSelective(TInfoAccountenterprise record);

    TInfoAccountenterprise selectByPrimaryKey(String enterpriseCustomerno);
    
    TInfoAccountenterprise findCustomerByCustomerNo(String enterpriseCustomerno);
    
    TInfoAccountenterprise findCustomerByCustomerNoForUpdate(String enterpriseCustomerno);

    int updateByPrimaryKeySelective(TInfoAccountenterprise record);

    int updateByPrimaryKey(TInfoAccountenterprise record);
}