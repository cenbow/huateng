package cn.com.huateng.account.service;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TPortSuggestions;
import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.huateng.p3.component.Response;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 14-10-29
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public interface SuggestionsService {
    /**
     * 投诉建议
     * @return 成功或失败
     */
    Response<String> suggestions(@ParamInfo("tPortSuggestions")TPortSuggestions tPortSuggestions);

    /**
     * 投诉查询
     * @param commonUser
     * @return 投诉列表
     */

    Response<Paging<TPortSuggestions>> querySuggestions(@ParamInfo("commonUser")CommonUser commonUser,
                                                      @ParamInfo("startDate")String startDate,
                                                      @ParamInfo("endDate") String endDate,
                                                      @ParamInfo("startIndex") Integer startIndex,
                                                      @ParamInfo("pageSize") Integer pageSize);


    /**
     * 投诉明细
     * @param commonUser
     * @param id
     * @return
     */
    Response<TPortSuggestions> querySuggestionsDetail(@ParamInfo("commonUser")CommonUser commonUser, @ParamInfo("id")String id);
}
