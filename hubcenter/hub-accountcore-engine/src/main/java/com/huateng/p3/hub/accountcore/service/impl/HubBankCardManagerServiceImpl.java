package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.manager.BankCardManagerService;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubBankCardManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-9-12
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HubBankCardManagerServiceImpl implements HubBankCardManagerService {

    @Autowired(required = false)
    private BankCardManagerService bankCardManagerService;

    private static final Logger logger = LoggerFactory.getLogger(HubBankCardManagerServiceImpl.class);

    @Override
    public Response<String> bindBankCard(TInfoBankcard bankCard, String bindingType, TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("bindBankCard TInfoBankcard:{},bindingType:{},TInfoCustomer:{},ManagerLog:{}", new Object[]{bankCard.toString(),bindingType,customer.toString(),managerLog.toString()});
        return bankCardManagerService.bindBankCard(bankCard,bindingType,customer,managerLog);
    }

    @Override
    public Response<String> unbindBankCardByBankCardNo(TInfoBankcard bankCard, TInfoCustomer customer, ManagerLog managerLog) throws Exception {
        logger.info("unbindBankCardByBankCardNo TInfoBankcard:{},TInfoCustomer:{},ManagerLog:{}", new Object[]{bankCard.toString(),customer.toString(),managerLog.toString()});
        return bankCardManagerService.unbindBankCardByBankCardNo(bankCard,customer,managerLog);

    }
}
