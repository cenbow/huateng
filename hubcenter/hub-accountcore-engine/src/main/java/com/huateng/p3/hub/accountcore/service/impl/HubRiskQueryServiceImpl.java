package com.huateng.p3.hub.accountcore.service.impl;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskQueryObject;
import com.huateng.p3.account.risk.RiskQueryService;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubRiskQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-9-15
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HubRiskQueryServiceImpl implements HubRiskQueryService {
    @Autowired(required = false)
    private RiskQueryService riskQueryService;

    private static final Logger logger = LoggerFactory.getLogger(HubRiskQueryServiceImpl.class);

    @Override
    public Response<RiskQueryObject> queryAccountRisk(AccountInfo accountInfo, PaymentInfo paymentInfo) throws Exception {
        logger.info("queryAccountRisk accountInfo:{},PaymentInfo:{}", new Object[]{accountInfo.toString(),paymentInfo.toString()});
        return riskQueryService.queryAccountRisk(accountInfo,paymentInfo);
    }
}
