package com.huateng.p3.account.persistence;


import java.util.List;

import com.huateng.p3.account.persistence.models.TRealnameApply;

public interface TRealnameApplyMapper {
    int deleteByPrimaryKey(Long tSeq);

    int insert(TRealnameApply record);

    int insertSelective(TRealnameApply record);

    TRealnameApply selectByPrimaryKey(Long tSeq);

    int updateByPrimaryKeySelective(TRealnameApply record);

    int updateByPrimaryKey(TRealnameApply record);

    TRealnameApply queryAuthenticationSuccessInfo(String customerNo);

    /**
     * 查询实名申请中的用户
     * @param customerNO
     * @return
     */
    List<TRealnameApply>  queryAuthenticationApplyInfo(String customerNO);
    /**
     * 查询所有非归档实名记录
     * @param customerNO
     * @return
     */
    List<TRealnameApply>  queryAuthenticationInfo(String customerNO);
    
    int getRealNameUsedIdNoCount(String idNo);

    int  updateAuthenticationInfo(String customerNo);






}