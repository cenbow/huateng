package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TbPosInfo;
import com.huateng.p3.account.persistence.models.TbPosInfoKey;

import java.util.Map;

public interface TbPosInfoMapper {

	int deleteByPrimaryKey(TbPosInfoKey key);

    int insert(TbPosInfo record);

    int insertSelective(TbPosInfo record);

    TbPosInfo selectByPrimaryKey(TbPosInfoKey key);

    int updateByPrimaryKeySelective(TbPosInfo record);

    int updateByPrimaryKey(TbPosInfo record);


    TbPosInfo findPinkeyFromEncrypt(Map param);
}