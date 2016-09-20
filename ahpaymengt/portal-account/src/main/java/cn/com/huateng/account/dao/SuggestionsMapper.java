package cn.com.huateng.account.dao;

import cn.com.huateng.account.model.TPortSuggestions;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 14-10-24
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SuggestionsMapper extends SqlSessionDaoSupport {


    public List<TPortSuggestions> querySuggestions(TPortSuggestions tPortSuggestions) {
        return getSqlSession().selectList("TPortSuggestionsMapper.selectByPrimaryKey", tPortSuggestions);
    }

    public TPortSuggestions querySuggestionsDetail(TPortSuggestions tPortSuggestions) {
        return getSqlSession().selectOne("TPortSuggestionsMapper.selectById", tPortSuggestions);
    }

    public Integer suggestions(TPortSuggestions tPortSuggestions) {
        return (Integer) getSqlSession().insert
                ("TPortSuggestionsMapper.insert", tPortSuggestions);
    }
    public Long  findCountTPortSuggestions(TPortSuggestions tPortSuggestions) {
        return getSqlSession().selectOne("TPortSuggestionsMapper.selectCountByPrimaryKey", tPortSuggestions);
    }
}
