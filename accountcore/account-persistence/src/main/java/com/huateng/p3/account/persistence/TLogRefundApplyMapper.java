package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TLogRefundApply;

public interface TLogRefundApplyMapper {
    int deleteByPrimaryKey(String recordNo);

    int insert(TLogRefundApply record);

    int insertSelective(TLogRefundApply record);

    TLogRefundApply selectByPrimaryKey(String recordNo);

    TLogRefundApply selectByrefundapplySeq(String refundapplySeq);

    int updateByPrimaryKeySelective(TLogRefundApply record);

    int updateByPrimaryKey(TLogRefundApply record);


}