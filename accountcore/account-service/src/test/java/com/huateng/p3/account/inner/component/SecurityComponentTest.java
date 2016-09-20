package com.huateng.p3.account.inner.component;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.component.SecurityComponent;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TbPosInfo;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 13-12-18
 * Time: 下午3:33
 * o change this template use File | Settings | File Templates.
 */
public class SecurityComponentTest extends BaseAccountServiceSpringTest {


    private Logger logger = LoggerFactory.getLogger(SecurityComponentTest.class);

    @Autowired
    private SecurityComponent securityComponent;
    @Autowired
    private AccountService accountService;

    @Test
    public void testTxnPwdCheck() throws Exception{
    	
    	TInfoCustomer customer = new TInfoCustomer();
    	customer.setMobileNo("18018354882");
    	
    	AccountInfo accountInfo = new AccountInfo();
    	accountInfo.setAccountKey("18018354882");
    	accountInfo.setKeyType(KeyType.UNIFY);
    	accountInfo.setTxnPassword("9F8751A660837FFB");
    	TInfoAccount account = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	
    	PaymentInfo paymentInfo = new PaymentInfo();
    	paymentInfo.setMerchantCode("222222222222222");
    	paymentInfo.setTerminalNo("00431000");
    	paymentInfo.setAcceptOrgCode("004310000040000");
    	//TbPosInfo tbPosInfo = securityComponent.getTbPosInfo(paymentInfo.getMerchantCode(), paymentInfo.getTerminalNo(), paymentInfo.getAcceptOrgCode());                
    	account =securityComponent.txnPwdCheck(customer, account , accountInfo);
    	Assert.assertEquals(Long.valueOf(0), account.getPasswdErrNum());
    	logger.info("密码验证通过");
    }
    
    

}

