package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoMobileH;

public interface TInfoMobileHMapper {
    int deleteByPrimaryKey(String mobileHCode);

    int insert(TInfoMobileH record);

    int insertSelective(TInfoMobileH record);

    TInfoMobileH selectByPrimaryKey(String mobileHCode);

    int updateByPrimaryKeySelective(TInfoMobileH record);

    int updateByPrimaryKey(TInfoMobileH record);
}