package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.service.AccountTransferService;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubAccountTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-29
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HubAccountTransferServiceImpl implements HubAccountTransferService {

    private static final Logger logger = LoggerFactory.getLogger(HubAccountTransferServiceImpl.class);

    @Autowired
    private AccountTransferService accountTransferService;
    @Override
    public Response<TxnResultObject> transfer(PaymentInfo paymentInfo, AccountInfo accountInfo) throws Exception {
        logger.info("transfer AccountInfo:{},PaymentInfo:{}", new Object[]{accountInfo.toString(), paymentInfo.toString()});
        return accountTransferService.transfer(paymentInfo,accountInfo);
    }

    @Override
    public Response<TxnResultObject> transferCheck(PaymentInfo paymentInfo, AccountInfo accountInfo) throws Exception {
        logger.info("transferCheck AccountInfo:{},PaymentInfo:{}", new Object[]{accountInfo.toString(), paymentInfo.toString()});
        return accountTransferService.transferCheck(paymentInfo,accountInfo);
    }
}
