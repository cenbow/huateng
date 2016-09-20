package cn.com.huateng.account.manager;

import cn.com.huateng.account.dao.SuggestionsMapper;
import cn.com.huateng.account.model.TPortSuggestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 14-10-24
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */

@Component
public class SuggestionsManager {

    @Autowired
    SuggestionsMapper suggestionsMapper;

    public List<TPortSuggestions> querySuggestions(TPortSuggestions tPortSuggestions) {
        return suggestionsMapper.querySuggestions(tPortSuggestions);
    }

    public TPortSuggestions querySuggestionsDetail(TPortSuggestions tPortSuggestions) {
        return suggestionsMapper.querySuggestionsDetail(tPortSuggestions);
    }

    @Transactional
    public Integer suggestions(TPortSuggestions tPortSuggestions) {
        return suggestionsMapper.suggestions(tPortSuggestions);
    }

    public Long  findCountTPortSuggestions(TPortSuggestions tPortSuggestions) {
        return  suggestionsMapper.findCountTPortSuggestions(tPortSuggestions);
    }
}
