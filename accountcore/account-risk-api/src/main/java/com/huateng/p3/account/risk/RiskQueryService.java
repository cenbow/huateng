package com.huateng.p3.account.risk;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskQueryObject;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-3-20
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public interface RiskQueryService {

    /**
     * 查询用户可交易金额
     *
     * @param accountInfo
     * @param paymentInfo 只需传入外部交易类型以及渠道，流水号
     * @return
     */
    Response<RiskQueryObject> queryAccountRisk(AccountInfo accountInfo, PaymentInfo paymentInfo);

   
}
