package com.huateng.p3.account.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-2-25
 * Time: 上午9:33
 * o change this template use File | Settings | File Templates.
 */
public class AccountRefundServiceTest extends BaseAccountServiceSpringTest {


    private Logger logger = LoggerFactory.getLogger(AccountRefundServiceTest.class);

    @Autowired
    AccountRefundService accountRefundService;

    @Test
    public void testAll() throws Exception {
        
    	//testRefundApplyCheckOk();//退货校验正确的参数消费
    	testRefundApplyOk();//退货申请正确的参数消费
//    	testRefundNoApplyOk();//无审批直接退货正确的参数消费

    }



   public void testRefundApplyCheckOk() throws Exception {
        PaymentInfo paymentInfo = new PaymentInfo();       
        paymentInfo.setAmount(1l);
        PaymentInfo oldPaymentInfo = new PaymentInfo();       
        oldPaymentInfo.setAcceptOrgCode("004310000040000");
        oldPaymentInfo.setAcceptDate("20130913");
        oldPaymentInfo.setAcceptTime("083315");      
        oldPaymentInfo.setAcceptTxnSeqNo("2013091300084127");      
        Response<TxnResultObject> actual = accountRefundService.refundApplyCheck(paymentInfo, oldPaymentInfo);
        logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
    }   
   
   public void testRefundApplyOk() throws Exception {
       PaymentInfo paymentInfo = new PaymentInfo();       
       paymentInfo.setAmount(1l);
       PaymentInfo oldPaymentInfo = new PaymentInfo();       
       oldPaymentInfo.setAcceptOrgCode("004310000040000");
       oldPaymentInfo.setAcceptDate("20130913");
       oldPaymentInfo.setAcceptTime("083315");      
       oldPaymentInfo.setAcceptTxnSeqNo("2013091300084127");      
       Response<TxnResultObject> actual = accountRefundService.refundApply(paymentInfo, oldPaymentInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }   
   
   
   
   public void testRefundNoApplyOk() throws Exception {
       PaymentInfo paymentInfo = new PaymentInfo();       
       paymentInfo.setAmount(1l);
       PaymentInfo oldPaymentInfo = new PaymentInfo();       
       oldPaymentInfo.setAcceptOrgCode("004310000040000");
       oldPaymentInfo.setAcceptDate("20130913");
       oldPaymentInfo.setAcceptTime("083315");      
       oldPaymentInfo.setAcceptTxnSeqNo("2013091300084127");      
       Response<TxnResultObject> actual = accountRefundService.refundNoApplyAudit(paymentInfo, oldPaymentInfo);
       
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }   
      
      
  

}

