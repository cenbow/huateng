package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午10:52
 */
public interface HubAccountManagerService {

    /**
     * 修改账户支付密码
     *
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> modfiyTxnPasswd(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;

    /**
     * 支付密码鉴权
     *
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> checkTxnPasswd(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;

    /**
     * 修改账户支付密码（无需原密码）
     *
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> modfiyTxnPasswdWithoutOldPwd(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;


    /**
     * 实名认证通过照片认证客户身份信息请求接口
     *
     * @param accountInfo
     * @param managerLog
     * @return
     */

    Response<String> authenticateCustomerIdentityApplyForSecurity(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;


    /**
     * 账户冻结
     *
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> freezeAccount(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;


    /**
     * 账户解冻
     *
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> unfreezeAccount(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;

}
