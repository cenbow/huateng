package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.MarketTxntypeCfg;

public interface MarketTxntypeCfgMapper {
    int deleteByPrimaryKey(String markettxntypeid);

    int insert(MarketTxntypeCfg record);

    int insertSelective(MarketTxntypeCfg record);

    MarketTxntypeCfg selectByPrimaryKey(String markettxntypeid);

    int updateByPrimaryKeySelective(MarketTxntypeCfg record);

    int updateByPrimaryKey(MarketTxntypeCfg record);
}