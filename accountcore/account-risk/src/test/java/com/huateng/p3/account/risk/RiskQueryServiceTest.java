package com.huateng.p3.account.risk;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountRiskSpringTest;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskQueryObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.risk.RiskQueryService;
import com.huateng.p3.component.Response;


/**
 * User: JamesTang
 * Date: 14-2-12
 * Time: 下午5:10
 */
public class RiskQueryServiceTest extends BaseAccountRiskSpringTest {

    @Autowired
    RiskQueryService riskQueryService;
    
    private Logger logger = LoggerFactory
			.getLogger(RiskQueryServiceTest.class);

    @Test
    public void testQueryAccountRisk() {
    	PaymentInfo paymentInfo = new PaymentInfo();
    	paymentInfo.setAcceptTxnSeqNo("21312312313");
        paymentInfo.setBussinessType("C4100C");       
        paymentInfo.setChannel("02");
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("18018354882");
        accountInfo.setKeyType(KeyType.UNIFY);
        Response<RiskQueryObject> actual=riskQueryService.queryAccountRisk(accountInfo, paymentInfo);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());

    }
}
