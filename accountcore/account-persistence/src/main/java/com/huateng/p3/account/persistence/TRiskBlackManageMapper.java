package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TRiskBlackManage;

import org.apache.ibatis.annotations.Param;

public interface TRiskBlackManageMapper {
    int deleteByPrimaryKey(Long recordNo);

    int insert(TRiskBlackManage record);

    int insertSelective(TRiskBlackManage record);

    TRiskBlackManage selectByPrimaryKey(Long recordNo);

    int updateByPrimaryKeySelective(TRiskBlackManage record);

    int updateByPrimaryKey(TRiskBlackManage record);

    TRiskBlackManage queryRiskBlack(@Param("blackCode") String blackCode,
                                    @Param("blackType") String blackType);

}