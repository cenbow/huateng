package com.huateng.p3.account.persistence;


import java.util.List;

import com.huateng.p3.account.persistence.models.TInfoHanbind;

public interface TInfoHanbindMapper {
    int insert(TInfoHanbind record) ;

	int insertSelective(TInfoHanbind record) ;

    int  debindInfoBankHan(String productNo);
    
    List<TInfoHanbind> getBankHan(String customerNo);
	
}