package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoSubaccount;

import java.util.List;

public interface TInfoSubaccountMapper {
    int deleteByPrimaryKey(String accountNo);

    int insert(TInfoSubaccount record);

    int insertSelective(TInfoSubaccount record);

    TInfoSubaccount selectByPrimaryKey(String accountNo);

    int updateByPrimaryKeySelective(TInfoSubaccount record);

    int updateByPrimaryKey(TInfoSubaccount record);

    List<TInfoSubaccount> getSubaccountinfoByMainaccno(String mainAccountNo);
}