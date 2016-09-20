package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TLogChannelRefundApply;

public interface TLogChannelRefundApplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(TLogChannelRefundApply record);

    int insertSelective(TLogChannelRefundApply record);

    TLogChannelRefundApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TLogChannelRefundApply record);

    int updateByPrimaryKey(TLogChannelRefundApply record);
}