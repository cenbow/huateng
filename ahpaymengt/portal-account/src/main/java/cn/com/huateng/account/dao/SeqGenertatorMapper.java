package cn.com.huateng.account.dao;

import cn.com.huateng.account.model.SeqTableName;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * User: 董培基
 * Date: 13-7-30
 * Time: 下午7:22
 */
@Repository
public class SeqGenertatorMapper extends SqlSessionDaoSupport {


    public Long getNextValue(String seqName) {
    	SeqTableName seqTableName = new SeqTableName();
    	seqTableName.setSeqName(seqName);
        return getSqlSession().selectOne("SeqGenertator.getNextValue",seqTableName);
    }
}
