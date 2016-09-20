package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TAccountBanktransAmtCnt;

public interface TAccountBanktransAmtCntMapper {
    int deleteByPrimaryKey(String accountNo);

    int insert(TAccountBanktransAmtCnt record);

    int insertSelective(TAccountBanktransAmtCnt record);

    TAccountBanktransAmtCnt selectByPrimaryKey(String accountNo);

    int updateByPrimaryKeySelective(TAccountBanktransAmtCnt record);

    int updateByPrimaryKey(TAccountBanktransAmtCnt record);
}