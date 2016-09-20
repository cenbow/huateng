package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TRiskAreaOta;

public interface TRiskAreaOtaMapper {

	int insert(TRiskAreaOta record);

    int insertSelective(TRiskAreaOta record);
    
	TRiskAreaOta findAreaOtaByAreacode(String areaCode);
}
