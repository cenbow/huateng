package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TSmsTempSend;

public interface TSmsTempSendMapper {
    int deleteByPrimaryKey(long recordNo);

    int insert(TSmsTempSend record);

    int insertSelective(TSmsTempSend record);

    TSmsTempSend selectByPrimaryKey(long recordNo);

    int updateByPrimaryKeySelective(TSmsTempSend record);

    int updateByPrimaryKey(TSmsTempSend record);
}