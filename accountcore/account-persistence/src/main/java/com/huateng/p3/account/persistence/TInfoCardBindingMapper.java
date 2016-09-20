package com.huateng.p3.account.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TInfoCardBinding;

public interface TInfoCardBindingMapper {


	int deleteByPrimaryKey(String cardbindid);

    int insert(TInfoCardBinding record);

    int insertSelective(TInfoCardBinding record);

    TInfoCardBinding selectByPrimaryKey(String cardbindid);

    int updateByPrimaryKeySelective(TInfoCardBinding record);

    int updateByPrimaryKey(TInfoCardBinding record);
   
    int updateAllCardNoDefault(@Param("productno") String productno,	
    							@Param("channel") String channel);

    int updateNewDefaultCard(@Param("productno") String productno,	
			@Param("channel") String channel);

    int  updateCardBinding(@Param("newProductNo") String newProductNo, @Param("oldProductNo") String oldProductNo);
    
    List<TInfoCardBinding> findCardBinding(@Param("merchantno") String merchantno,
								    		@Param("unifyId") String unifyId,
								    		@Param("pactno") String pactno,
								    		@Param("channel") String channel,
								    		@Param("cardstatus") String cardstatus,
								    		@Param("cvv2") String cvv2,
								    		@Param("bankCode") String bankCode,
								    		@Param("cardno") String cardno);


}