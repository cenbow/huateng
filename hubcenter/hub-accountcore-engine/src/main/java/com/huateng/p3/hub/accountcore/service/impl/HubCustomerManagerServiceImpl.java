package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.*;
import com.huateng.p3.account.manager.CustomerManagerService;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubCustomerManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-9-15
 * Time: 上午9:51
 * To change this template use File | Settings | File Templates.
 */

@Service
public class HubCustomerManagerServiceImpl implements HubCustomerManagerService {
    @Autowired(required = false)
    private CustomerManagerService customerManagerService;

    private static final Logger logger = LoggerFactory.getLogger(HubCustomerManagerServiceImpl.class);

    @Override
    public Response<String> synchronizeCustomerInfo(AccountInfo accountInfo, TInfoCustomer tInfoCustomer, ManagerLog managerLog) throws Exception {
        logger.info("synchronizeCustomerInfo AccountInfo:{},TInfoCustomer:{},ManagerLog:{}", new Object[]{accountInfo.toString(),tInfoCustomer.toString(),managerLog.toString()});
        return customerManagerService.synchronizeCustomerInfo(accountInfo,tInfoCustomer,managerLog);
    }

    @Override
    public Response<OpenCustomerResultObject> openCustomer(TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("openCustomer customer:{},ManagerLog:{}", new Object[]{customer.toString(),managerLog.toString()});
        return customerManagerService.openCustomer(customer,managerLog);
     }

    @Override
    public Response<String> closeCustomer(TInfoCustomer customer, String cardNo, String cardType, ManagerLog managerLog) throws Exception {
        logger.info("closeCustomer customer:{},cardNo:{},cardType:{},ManagerLog:{}", new Object[]{customer.toString(),cardNo.toString(),cardType.toString(),managerLog.toString()});
        return customerManagerService.closeCustomer(customer,cardNo,cardType,managerLog);
     }

    @Override
    public Response<String> unCheckIdlockCustomer(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("unCheckIdlockCustomer accountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.unCheckIdlockCustomer(accountInfo,managerLog);
    }

    @Override
    public Response<String> checkIdlockCustomer(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("checkIdlockCustomer accountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.checkIdlockCustomer(accountInfo,managerLog);
    }

    @Override
    public Response<String> unCheckIdUnlockCustomer(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("unCheckIdUnlockCustomer accountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.unCheckIdUnlockCustomer(accountInfo,managerLog);
    }

    @Override
    public Response<String> checkIdUnlockCustomer(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("checkIdUnlockCustomer accountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.checkIdUnlockCustomer(accountInfo,managerLog);
    }

    @Override
    public Response<String> activationCustomer(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("activationCustomer accountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.activationCustomer(accountInfo,managerLog);
    }

    @Override
    public Response<String> loginCustomer(AccountInfo accountInfo, LoginInfo loginInfo) throws Exception {
        logger.info("loginCustomer accountInfo:{},loginInfo:{}", new Object[]{accountInfo.toString(),loginInfo.toString()});
        return customerManagerService.loginCustomer(accountInfo,loginInfo);
    }

    @Override
    public Response<String> resetLoginPasswd(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("resetLoginPasswd accountInfo:{},managerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.resetLoginPasswd(accountInfo,managerLog);
    }

    @Override
    public Response<String> modfiyLoginPasswdWithoutOldPwd(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("modfiyLoginPasswdWithoutOldPwd accountInfo:{},managerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.modfiyLoginPasswdWithoutOldPwd(accountInfo,managerLog);
    }

    @Override
    public Response<String> changeSecrurityQuestionAndAnswer(AccountInfo accountInfo, ManagerLog managerLog, SecurityQuestionInfo securityQuestionInfo) throws Exception {
        logger.info("changeSecrurityQuestionAndAnswer accountInfo:{},managerLog:{},securityQuestionInfo:{}", new Object[]{accountInfo.toString(),managerLog.toString(),securityQuestionInfo.toString()});
        return customerManagerService.changeSecrurityQuestionAndAnswer(accountInfo,managerLog,securityQuestionInfo);
    }

    @Override
    public Response<String> modfiyLoginPasswdWithOldPwd(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("modfiyLoginPasswdWithOldPwd accountInfo:{},managerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerManagerService.modfiyLoginPasswdWithOldPwd(accountInfo,managerLog);
    }

    @Override
    public Response<String> LostCustomerByUnifyId(TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("LostCustomerByUnifyId TInfoCustomer:{},managerLog:{}", new Object[]{customer.toString(),managerLog.toString()});
        return customerManagerService.LostCustomerByUnifyId(customer,managerLog);
    }

    @Override
    public Response<String> unLostCustomerByUnifyId(TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("unLostCustomerByUnifyId TInfoCustomer:{},managerLog:{}", new Object[]{customer.toString(),managerLog.toString()});
        return customerManagerService.unLostCustomerByUnifyId(customer,managerLog);
    }

    @Override
    public Response<String> mobilePhoneBinding(TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("mobilePhoneBinding TInfoCustomer:{},managerLog:{}", new Object[]{customer.toString(),managerLog.toString()});
        return customerManagerService.mobilePhoneBinding(customer,managerLog);
    }

    @Override
    public Response<String> unMobilePhoneBinding(TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("unMobilePhoneBinding TInfoCustomer:{},managerLog:{}", new Object[]{customer.toString(),managerLog.toString()});
        return customerManagerService.unMobilePhoneBinding(customer,managerLog);
    }

    @Override
    public Response<String> modifyCustomerInfo(TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("modifyCustomerInfo TInfoCustomer:{},managerLog:{}", new Object[]{customer.toString(),managerLog.toString()});
        return customerManagerService.modfiyCustomerInfo(customer,managerLog);
    }
}
