package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityResultObject;
import com.huateng.p3.account.manager.CustomerQueryService;
import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubCustomerQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-9-15
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HubCustomerQueryServiceImpl implements HubCustomerQueryService {
    @Autowired(required = false)
    private CustomerQueryService customerQueryService;

    private static final Logger logger = LoggerFactory.getLogger(HubCustomerQueryServiceImpl.class);
    @Override
    public Response<CustomerResultObject> queryCustomerInfo(AccountInfo accountInfo) throws Exception {
        logger.info("queryCustomerInfo AccountInfo:{}", new Object[]{accountInfo.toString()});
        return  customerQueryService.queryCustomerInfo(accountInfo);
    }

    @Override
    public Response<CustomerResultObject> queryCheckCardHandingInfo(AccountInfo accountInfo) throws Exception {
        logger.info("queryCheckCardHandingInfo AccountInfo:{}", new Object[]{accountInfo.toString()});
        return customerQueryService.queryCheckCardHandingInfo(accountInfo);
    }

    @Override
    public Response<SecurityResultObject> queryCustomerSecurityInfo(AccountInfo accountInfo) throws Exception {
        logger.info("queryCustomerSecurityInfo AccountInfo:{}", new Object[]{accountInfo.toString()});
        return customerQueryService.queryCustomerSecurityInfo(accountInfo);
    }

    @Override
    public Response<String> queryRiskBlack(AccountInfo accountInfo, ManagerLog managerLog) throws Exception {
        logger.info("queryRiskBlack AccountInfo:{},ManagerLog:{}", new Object[]{accountInfo.toString(),managerLog.toString()});
        return customerQueryService.queryRiskBlack(accountInfo,managerLog);
    }

    @Override
    public Response<List<TDictCode>> querySecurityQuestions(ManagerLog managerLog) throws Exception {
        logger.info("querySecurityQuestions ManagerLog:{}", new Object[]{managerLog.toString()});
        return customerQueryService.querySecurityQuestions(managerLog);
    }

    @Override
    public Response<List<TDictCode>> querySecuritySysData(ManagerLog managerLog, String dictEng) throws Exception {
        logger.info("querySecuritySysData ManagerLog:{} dictEng:{}", new Object[]{managerLog.toString(),dictEng});
        return customerQueryService.querySecuritySysData(managerLog,dictEng);
    }

    @Override
    public Response<TInfoCustomer> queryMobilePhoneBinding(TInfoCustomer customer) throws Exception {
        logger.info("queryMobilePhoneBinding TInfoCustomer:{}", new Object[]{customer.toString()});
        return customerQueryService.queryMobilePhoneBinding(customer);
    }

    @Override
    public Response<List<TInfoBankcard>> queryBankCardBinding(TInfoBankcard tInfoBankcard) {
        logger.info("queryBankCardBinding TInfoBankcard:{}", new Object[]{tInfoBankcard.toString()});
        return   customerQueryService.queryBankCardBinding(tInfoBankcard);
    }

    @Override
    public Response<List<TInfoAccountBankCard>> queryAccountBankCardBinding(TInfoAccountBankCard tInfoAccountBankCard) {
        logger.info("queryAccountBankCardBinding TInfoAccountBankCard:{}", new Object[]{tInfoAccountBankCard.toString()});
        return   customerQueryService.queryAccountBankCardBinding(tInfoAccountBankCard);
    }
}
