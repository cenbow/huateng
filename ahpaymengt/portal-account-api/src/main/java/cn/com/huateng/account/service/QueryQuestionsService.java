package cn.com.huateng.account.service;

import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.component.Response;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.

 /**
 * 查询密保问题列表

 * @return TDictCode中 codeValue为密保问题编号 codeDesc为密保问题内容
 */
 public interface QueryQuestionsService {
    Response<List<TDictCode>> querySecurityQuestions()throws Exception;
}
