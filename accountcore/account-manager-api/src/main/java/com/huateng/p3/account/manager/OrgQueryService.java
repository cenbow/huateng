package com.huateng.p3.account.manager;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.component.Response;


/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-3-24
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public interface OrgQueryService {
	/**
     * 受理机构交易权限鉴权
     * 
     * @param managerLog  只需要填写 acceptOrgCode   受理机构   bussinessType 外部交易类型  核心转为内部交易类型判断
     * @return 
     */
    Response<String> checkOrgPayment(ManagerLog managerLog);
    
	
}