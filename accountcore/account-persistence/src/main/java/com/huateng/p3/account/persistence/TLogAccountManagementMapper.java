package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TLogAccountManagement;

public interface TLogAccountManagementMapper {
    int deleteByPrimaryKey(String transSerialNo);

    int insert(TLogAccountManagement record);

    int insertSelective(TLogAccountManagement record);

    TLogAccountManagement selectByPrimaryKey(String transSerialNo);

    int updateByPrimaryKeySelective(TLogAccountManagement record);

    int updateByPrimaryKey(TLogAccountManagement record);
}