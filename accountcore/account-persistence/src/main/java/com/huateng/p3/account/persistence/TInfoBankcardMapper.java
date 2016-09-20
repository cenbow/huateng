package com.huateng.p3.account.persistence;



import java.util.List;

import com.huateng.p3.account.persistence.models.TInfoBankcard;

public interface TInfoBankcardMapper {
    int deleteByPrimaryKey(String bankCardNo);

    int insert(TInfoBankcard record);

    int insertSelective(TInfoBankcard record);

    TInfoBankcard selectByPrimaryKey(String bankCardNo);

    int updateByPrimaryKeySelective(TInfoBankcard record);

    int updateByPrimaryKey(TInfoBankcard record);
    
    /**
     * 查询银行卡绑定信息
     * @param tInfoBankCard
     * @return
     */
    List<TInfoBankcard> findBankCardBinding(TInfoBankcard tInfoBankCard);
    
    
}