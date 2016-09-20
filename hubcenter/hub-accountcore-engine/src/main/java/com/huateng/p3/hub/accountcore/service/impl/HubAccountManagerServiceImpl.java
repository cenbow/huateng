package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.manager.AccountManagerService;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubAccountManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午11:01
 */
@Service
public class HubAccountManagerServiceImpl implements HubAccountManagerService {

    @Autowired(required = false)
    private AccountManagerService accountManagerService;

    private static final Logger logger = LoggerFactory.getLogger(HubAccountManagerServiceImpl.class);

    @Override
    public Response<String> modfiyTxnPasswd(AccountInfo accountInfo, ManagerLog managerLog) throws Exception{
        logger.info("modfiyTxnPasswd AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(), managerLog.toString()});
        return accountManagerService.modfiyTxnPasswd(accountInfo, managerLog);
    }

    @Override
    public Response<String> checkTxnPasswd(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("checkTxnPasswd AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(), managerLog.toString()});
        return accountManagerService.checkTxnPasswd(accountInfo,managerLog);
    }

    @Override
    public Response<String> modfiyTxnPasswdWithoutOldPwd(AccountInfo accountInfo, ManagerLog managerLog) {
        logger.info("modfiyTxnPasswdWithoutOldPwd AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(), managerLog.toString()});
        return accountManagerService.modfiyTxnPasswdWithoutOldPwd(accountInfo,managerLog);
    }

    @Override
    public Response<String> authenticateCustomerIdentityApplyForSecurity(AccountInfo accountInfo, ManagerLog managerLog) {
        logger.info("authenticateCustomerIdentityApplyForSecurity AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(), managerLog.toString()});
        return accountManagerService.authenticateCustomerIdentityApplyForSecurity(accountInfo,managerLog);
    }

    @Override
    public Response<String> freezeAccount(AccountInfo accountInfo, ManagerLog managerLog) {
        logger.info("freezeAccount AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(), managerLog.toString()});
        return accountManagerService.freezeAccount(accountInfo,managerLog);
    }

    @Override
    public Response<String> unfreezeAccount(AccountInfo accountInfo, ManagerLog managerLog) {
        logger.info("unfreezeAccount AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(), managerLog.toString()});
        return accountManagerService.unfreezeAccount(accountInfo,managerLog);
    }
}
