package com.huateng.p3.hub.txnengine.service;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-11
 * Time: 下午3:59
 */
public interface HubCustomerManagerService {
    /**
     *  开户
     * @param customer
     * @param managerLog
     * @return
     */
    Response<OpenCustomerResultObject> openCustomer(TInfoCustomer customer,ManagerLog managerLog);
}
