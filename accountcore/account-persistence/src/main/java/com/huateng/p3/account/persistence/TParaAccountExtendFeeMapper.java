package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TParaAccountExtendFee;

public interface TParaAccountExtendFeeMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TParaAccountExtendFee record);

    int insertSelective(TParaAccountExtendFee record);

    TParaAccountExtendFee selectByPrimaryKey(Long recordNo);

    int updateByPrimaryKeySelective(TParaAccountExtendFee record);

    int updateByPrimaryKey(TParaAccountExtendFee record);
}