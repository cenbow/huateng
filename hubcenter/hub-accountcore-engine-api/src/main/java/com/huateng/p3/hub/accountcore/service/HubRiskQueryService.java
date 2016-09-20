package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskQueryObject;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 下午12:39
 */
public interface HubRiskQueryService {
    /**
     * 查询用户可交易金额
     *
     * @param accountInfo
     * @param paymentInfo 只需传入外部交易类型以及渠道，流水号
     * @return
     */
    Response<RiskQueryObject> queryAccountRisk(AccountInfo accountInfo, PaymentInfo paymentInfo)throws Exception;


}
