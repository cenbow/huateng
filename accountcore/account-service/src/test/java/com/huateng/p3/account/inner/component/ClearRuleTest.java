package com.huateng.p3.account.inner.component;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.component.ClearRuleCacheService;
import com.huateng.p3.account.persistence.TLogOnlinePaymentMapper;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;

/**
 * User: JamesTang
 * Date: 14-7-18
 * Time: 下午1:32
 */
public class ClearRuleTest extends BaseAccountServiceSpringTest {

    @Autowired
    private ClearRuleCacheService clearRuleCacheService;

    @Autowired
    private TLogOnlinePaymentMapper tLogOnlinePaymentMapper;

    @Test
    public void feeV1test() {
        String txnlogid="00000000000471667028";

        TLogOnlinePayment tLogOnlinePayment = tLogOnlinePaymentMapper.selectByPrimaryKey(txnlogid);

        //Assert.assertEquals(tLogOnlinePayment, null);

        Assert.assertNotNull(tLogOnlinePayment);

        BigDecimal fee1 = clearRuleCacheService.calcRuleFeeLv1(tLogOnlinePayment);

        System.out.println(fee1);

        BigDecimal fee2 = clearRuleCacheService.calcRuleFeeLv2(tLogOnlinePayment);

        System.out.println(fee2);
    }


}