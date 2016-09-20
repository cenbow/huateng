package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TDictTxnErrcode;

public interface TDictTxnErrcodeMapper {
    int deleteByPrimaryKey(String errcode);

    int insert(TDictTxnErrcode record);

    int insertSelective(TDictTxnErrcode record);

    TDictTxnErrcode selectByPrimaryKey(String errcode);

    int updateByPrimaryKeySelective(TDictTxnErrcode record);

    int updateByPrimaryKey(TDictTxnErrcode record);
}