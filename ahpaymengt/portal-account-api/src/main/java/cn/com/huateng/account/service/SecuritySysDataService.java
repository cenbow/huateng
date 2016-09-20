package cn.com.huateng.account.service;

import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.component.Response;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 14-10-14
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
 public interface SecuritySysDataService {

    /**
     *查询国籍、职业等系统数据列表
     * @param dictEng
     * @return   TDictCode中 codeValue为编号 codeDesc为内容
     * @throws Exception
     */
    Response<List<TDictCode>> querySecuritySysData(String dictEng)throws Exception;
}
