package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoAccountenterpriseOrg;

public interface TInfoAccountenterpriseOrgMapper {
    int deleteByPrimaryKey(String orgCode);

    int insert(TInfoAccountenterpriseOrg record);

    int insertSelective(TInfoAccountenterpriseOrg record);

    TInfoAccountenterpriseOrg selectByPrimaryKey(String orgCode);

    int updateByPrimaryKeySelective(TInfoAccountenterpriseOrg record);

    int updateByPrimaryKey(TInfoAccountenterpriseOrg record);
}