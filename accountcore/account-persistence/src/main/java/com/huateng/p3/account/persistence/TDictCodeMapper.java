package com.huateng.p3.account.persistence;

import java.util.List;

import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.account.persistence.models.TDictCodeKey;

public interface TDictCodeMapper {
    int deleteByPrimaryKey(TDictCodeKey key);

    int insert(TDictCode record);

    int insertSelective(TDictCode record);

    TDictCode selectByPrimaryKey(TDictCodeKey key);

    int updateByPrimaryKeySelective(TDictCode record);

    int updateByPrimaryKey(TDictCode record);
    
    List<TDictCode> querySecurityQuestions();
    
    List<TDictCode> querySecuritySysData(String dictEng);
    
    TDictCode querySecurityQuestionsbyCodeValue(String codeValue);
    
    int checkQuestion(String value);
}