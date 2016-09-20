package com.huateng.p3.account.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 13-12-27
 * Time: 上午9:33
 * o change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:account-service.xml")
public class AccountTransferServiceTest {


    private Logger logger = LoggerFactory.getLogger(AccountTransferServiceTest.class);

    @Autowired
    AccountTransferService accountTransferService;

    @Test
    public void testAll() throws Exception {
        
        
    	testTransferOk();//充值正确的参数消费
       


    }



   public void testTransferOk() throws Exception {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMerchantCode("222222222222222");//
        paymentInfo.setTerminalNo("00431000");
        //paymentInfo.setAcceptOrgCode("001999999020000");
        paymentInfo.setAcceptOrgCode("004310000040000");
        paymentInfo.setInnerTxnType("121010");
        paymentInfo.setAmount(Long.parseLong("1"));
        paymentInfo.setAcceptDate("20131219");
        paymentInfo.setAcceptTime("164649");
        paymentInfo.setAcceptOperatorNo(null);
        paymentInfo.setAcceptTxnSeqNo("201402110009473");/////
        paymentInfo.setChannel("02");
        paymentInfo.setPayOrgCode("03");
        paymentInfo.setTerminalSeqNo("131218006900456");//////
        paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        paymentInfo.setTxnType(TxnType.TXN_TRANSFER);
        paymentInfo.setTxnDscpt("转账");
        paymentInfo.setSupplyOrgCode("111310049001138");
        
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124299");
        accountInfo.setKeyType(KeyType.UNIFY);
        accountInfo.setTargetAccountKey("784631759@qq.com");
        accountInfo.setTargetKeyType(KeyType.UNIFY);
        accountInfo.setTxnPassword("96e79218965eb72c92a549dd5a330112");
        Response<TxnResultObject> actual = accountTransferService.transfer(paymentInfo, accountInfo);
        logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("交易成功，转出交易流水号为" + actual.getResult().getTxnSeqNo());
        logger.info("交易成功，转入交易流水号为" + actual.getResult().getTargetTxnSeqNo());
    }   
      
   

}

