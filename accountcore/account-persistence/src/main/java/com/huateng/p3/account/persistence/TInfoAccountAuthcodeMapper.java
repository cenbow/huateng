package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoAccountAuthcode;

public interface TInfoAccountAuthcodeMapper {
    int insert(TInfoAccountAuthcode record);

    int insertSelective(TInfoAccountAuthcode record);
}