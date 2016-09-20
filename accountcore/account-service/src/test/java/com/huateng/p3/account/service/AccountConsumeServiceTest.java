package com.huateng.p3.account.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

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
 * Date: 13-12-18
 * Time: 下午3:33
 * o change this template use File | Settings | File Templates.
 */
public class AccountConsumeServiceTest extends BaseAccountServiceSpringTest {


    private Logger logger = LoggerFactory.getLogger(AccountConsumeServiceTest.class);

    @Autowired
    AccountConsumeService accountConsumeService;

    @Test
    public void testAll() throws Exception {
        //testConsumeFailAllNull();//参数传null
        //testConsumeFailAllEmpty();//参数传空对象
        testConsumeOk();//有密消费正确的参数消费
        //testConsumeWithoutPwdOk();//无密消费正确的参数消费
        //testCancelConsumeOk();//消费撤销正确的参数消费
    	//testRollbackConsumeOk();//消费冲正正确的参数消费
        //testRollbackCancelConsumeOk();//消费撤销冲正正确的参数消费
    	//testOfflineConsumeOk();//脱机消费正确的参数


    }


    /**
     * @throws Exception
     */
    public void testConsumeFailAllNull() throws Exception {
        Response<TxnResultObject> actual = accountConsumeService.consume(null, null);
        Assert.assertEquals(false, actual.isSuccess());
        Assert.assertEquals("999999", actual.getErrorCode());

        logger.info("交易信息，支付信息为空时返回，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
    }

    public void testConsumeFailAllEmpty() throws Exception {
        PaymentInfo paymentInfo = new PaymentInfo();
        AccountInfo accountInfo = new AccountInfo();
        Response<TxnResultObject> actual = accountConsumeService.consume(paymentInfo, accountInfo);
        Assert.assertEquals(false, actual.isSuccess());
        Assert.assertEquals("999999", actual.getErrorCode());
        logger.info("交易信息，支付信息为空对象时返回，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
    }

   public void testConsumeOk() throws Exception {
	   PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("00431000");
       paymentInfo.setAcceptOrgCode("004310000040000");      
       paymentInfo.setAmount(1l);
       paymentInfo.setAcceptDate("20131219");
       paymentInfo.setAcceptTime("164649");
       paymentInfo.setAcceptTxnSeqNo("2013121800094733");
       paymentInfo.setBussinessType("131010");
       paymentInfo.setChannel("08");
       paymentInfo.setTerminalSeqNo("131218006900456");
       paymentInfo.setTxnDscpt("test银联商务测试消费");
       paymentInfo.setSupplyOrgCode("111310049001138");
       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("18159737747");
       accountInfo.setKeyType(KeyType.UNIFY);
       accountInfo.setTxnPassword("96e79218965eb72c92a549dd5a330112");
       Response<TxnResultObject> actual = accountConsumeService.consume(paymentInfo, accountInfo);
       logger.info("交易失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
    }
   
   public void testConsumeWithoutPwdOk() throws Exception {
       PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("00431000");
       paymentInfo.setAcceptOrgCode("004310000040000");
       paymentInfo.setAmount(1l);
       paymentInfo.setAcceptDate("20131216");
       paymentInfo.setAcceptTime("164649");
       paymentInfo.setAcceptOperatorNo(null);
       paymentInfo.setAcceptTxnSeqNo("2013122600094733");
       paymentInfo.setBussinessType("C4100C");
       paymentInfo.setChannel("08");
       paymentInfo.setTerminalSeqNo("131218006900456");
       paymentInfo.setTxnDscpt("无密支付");
       paymentInfo.setSupplyOrgCode("111310049001138");
       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("13524124277");
       accountInfo.setKeyType(KeyType.UNIFY);
       Response<TxnResultObject> actual = accountConsumeService.consumeWithoutPwd(paymentInfo, accountInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }
   
   
   public void testCancelConsumeOk() throws Exception {
       PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("00431000");
       paymentInfo.setAcceptOrgCode("004310000040000");
       paymentInfo.setInnerTxnType("131012");
       paymentInfo.setAmount(1l);
       paymentInfo.setAcceptDate("20131216");
       paymentInfo.setAcceptTime("164650");
       paymentInfo.setAcceptOperatorNo(null);
       paymentInfo.setAcceptTxnSeqNo("2013122600094729");
       paymentInfo.setBussinessType("C4100C");
       paymentInfo.setChannel("08");
       paymentInfo.setPayOrgCode("");
       paymentInfo.setTerminalSeqNo("131218006900456");
       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
       paymentInfo.setTxnType(TxnType.TXN_CONSUME);
       paymentInfo.setTxnDscpt("消费撤销");
       paymentInfo.setSupplyOrgCode("111310049001138");
       
       
       PaymentInfo oldPaymentInfo = new PaymentInfo();       
       oldPaymentInfo.setAcceptOrgCode("004310000040000");
       oldPaymentInfo.setAcceptDate("20131216");
       oldPaymentInfo.setAcceptTime("164650");      
       oldPaymentInfo.setAcceptTxnSeqNo("2013122600094721");
     
       
       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("13524124277");
       accountInfo.setKeyType(KeyType.UNIFY);
       Response<TxnResultObject> actual = accountConsumeService.cancelConsume(paymentInfo, oldPaymentInfo, accountInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }
   
   public void testRollbackConsumeOk() throws Exception {
       PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("00431000");
       paymentInfo.setAcceptOrgCode("004310000040000");
       paymentInfo.setInnerTxnType("131011");
       paymentInfo.setAmount(1l);
       paymentInfo.setAcceptDate("20131216");
       paymentInfo.setAcceptTime("164650");
       paymentInfo.setAcceptOperatorNo(null);
       paymentInfo.setAcceptTxnSeqNo("2013122600094721");
       paymentInfo.setBussinessType("C4100C");
       paymentInfo.setChannel("08");
       paymentInfo.setPayOrgCode("");
       paymentInfo.setTerminalSeqNo("131218006900456");
       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
       paymentInfo.setTxnType(TxnType.TXN_CONSUME);
       paymentInfo.setTxnDscpt("消费冲正");
       paymentInfo.setSupplyOrgCode("111310049001138");
       PaymentInfo oldPaymentInfo = new PaymentInfo();       
       oldPaymentInfo.setAcceptOrgCode("004310000040000");
       oldPaymentInfo.setAcceptDate("20131216");
       oldPaymentInfo.setAcceptTime("164649");      
       oldPaymentInfo.setAcceptTxnSeqNo("2013122600094733");      
       oldPaymentInfo.setMerchantCode("222222222222222");
       oldPaymentInfo.setTerminalNo("00431000");    
       oldPaymentInfo.setInnerTxnType("131010");
       oldPaymentInfo.setAmount(1l);    
       oldPaymentInfo.setAcceptOperatorNo(null);     
       oldPaymentInfo.setBussinessType("C4100C");
       oldPaymentInfo.setChannel("08");
       oldPaymentInfo.setPayOrgCode("");
       oldPaymentInfo.setTerminalSeqNo("131218006900456");
       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
       oldPaymentInfo.setTxnType(TxnType.TXN_CONSUME);
       oldPaymentInfo.setTxnDscpt("消费");
       oldPaymentInfo.setSupplyOrgCode("111310049001138");
       
     
       
       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("13524124277");
       accountInfo.setKeyType(KeyType.UNIFY);
       Response<TxnResultObject> actual = accountConsumeService.rollbackConsume(paymentInfo, oldPaymentInfo, accountInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }
   
   public void testRollbackCancelConsumeOk() throws Exception {
//       PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("XJ000002");
//       paymentInfo.setAcceptOrgCode("004110000010000");
//       paymentInfo.setInnerTxnType("131013");
//       paymentInfo.setAmount(1l);
//       paymentInfo.setAcceptDate("20140528");
//       paymentInfo.setAcceptTime("142800");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("2013122600094888");
//       paymentInfo.setBussinessType("B41017");
//       paymentInfo.setChannel("01");
//       paymentInfo.setPayOrgCode("");
//       paymentInfo.setTerminalSeqNo("447777");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
//       paymentInfo.setTxnType(TxnType.TXN_CONSUME);
//       paymentInfo.setTxnDscpt("天翼碰碰消费撤销冲正");
//       paymentInfo.setSupplyOrgCode("111310049001257");
//       
//       
//       PaymentInfo oldPaymentInfo = new PaymentInfo();
//       oldPaymentInfo.setAcceptOrgCode("004110000010000");
//       oldPaymentInfo.setAcceptDate("20140528");
//       oldPaymentInfo.setAcceptTime("142800");      
//       oldPaymentInfo.setAcceptTxnSeqNo("213088");      
//       oldPaymentInfo.setMerchantCode("222222222222222");
//       oldPaymentInfo.setTerminalNo("TY000010");    
//       oldPaymentInfo.setInnerTxnType("131041");
//       oldPaymentInfo.setAmount(1l);    
//       oldPaymentInfo.setAcceptOperatorNo(null);     
//       oldPaymentInfo.setBussinessType("B4100V");
//       oldPaymentInfo.setChannel("01");
//       oldPaymentInfo.setPayOrgCode("001999900000000");
//       oldPaymentInfo.setTerminalSeqNo("000011");
//       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
//       oldPaymentInfo.setTxnType(TxnType.TXN_CONSUME);
//       oldPaymentInfo.setTxnDscpt("天翼碰碰消费撤销");
//       oldPaymentInfo.setSupplyOrgCode("111110049000019");
       
//	   PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("xinj2565");
//       paymentInfo.setAcceptOrgCode("004310000040000");
//       paymentInfo.setInnerTxnType("131100");
//       paymentInfo.setAmount(759l);
//       paymentInfo.setAcceptDate("20140512");
//       paymentInfo.setAcceptTime("145512");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("2013122600094721");
//       paymentInfo.setBussinessType("B41016");
//       paymentInfo.setChannel("01");
//       paymentInfo.setPayOrgCode("001999900000000");
//       paymentInfo.setTerminalSeqNo("447321");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
//       paymentInfo.setTxnType(TxnType.TXN_CONSUME);
//       paymentInfo.setTxnDscpt("新疆测试消费撤销冲正");
//       paymentInfo.setSupplyOrgCode("111650049000003");
//       
//       
//       PaymentInfo oldPaymentInfo = new PaymentInfo();
//       oldPaymentInfo.setAcceptOrgCode("002650000000000");
//       oldPaymentInfo.setAcceptDate("20140512");
//       oldPaymentInfo.setAcceptTime("145512");      
//       oldPaymentInfo.setAcceptTxnSeqNo("899340");      
//       oldPaymentInfo.setMerchantCode("222222222222222");
//       oldPaymentInfo.setTerminalNo("YL000002");    
//       oldPaymentInfo.setInnerTxnType("131100");
//       oldPaymentInfo.setAmount(759l);    
//       oldPaymentInfo.setAcceptOperatorNo(null);     
//       oldPaymentInfo.setBussinessType("B41016");
//       oldPaymentInfo.setChannel("01");
//       oldPaymentInfo.setPayOrgCode("001999900000000");
//       oldPaymentInfo.setTerminalSeqNo("447321");
//       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
//       oldPaymentInfo.setTxnType(TxnType.TXN_CONSUME);
//       oldPaymentInfo.setTxnDscpt("新疆测试消费撤销");
//       oldPaymentInfo.setSupplyOrgCode("111650049000003");
       PaymentInfo paymentInfo = new PaymentInfo();
       paymentInfo.setMerchantCode("222222222222222");
       paymentInfo.setTerminalNo("XJ000002");
       paymentInfo.setAcceptOrgCode("004310000040000");
       paymentInfo.setInnerTxnType("131103");
       paymentInfo.setAmount(1l);
       paymentInfo.setAcceptDate("20131216");
       paymentInfo.setAcceptTime("164649");
       paymentInfo.setAcceptOperatorNo(null);
       paymentInfo.setAcceptTxnSeqNo("201312260006666");
       paymentInfo.setBussinessType("B41017");
       paymentInfo.setChannel("01");
       paymentInfo.setPayOrgCode("001999900000000");
       paymentInfo.setTerminalSeqNo("010196");
       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
       paymentInfo.setTxnType(TxnType.TXN_CONSUME);
       paymentInfo.setTxnDscpt("银联商务测试消费撤销冲正");
       paymentInfo.setSupplyOrgCode("111310049001138");
       
       
       PaymentInfo oldPaymentInfo = new PaymentInfo();
       oldPaymentInfo.setAcceptOrgCode("004310000040000");
       oldPaymentInfo.setAcceptDate("20131216");
       oldPaymentInfo.setAcceptTime("164649");      
       oldPaymentInfo.setAcceptTxnSeqNo("2013122600094733");      
       oldPaymentInfo.setMerchantCode("222222222222222");
       oldPaymentInfo.setTerminalNo("YL000002");    
       oldPaymentInfo.setInnerTxnType("131101");
       oldPaymentInfo.setAmount(1l);    
       oldPaymentInfo.setAcceptOperatorNo(null);     
       oldPaymentInfo.setBussinessType("B41017");
       oldPaymentInfo.setChannel("01");
       oldPaymentInfo.setPayOrgCode("001999900000000");
       oldPaymentInfo.setTerminalSeqNo("010196");
       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
       oldPaymentInfo.setTxnType(TxnType.TXN_CONSUME);
       oldPaymentInfo.setTxnDscpt("银联商务测试消费撤销");
       oldPaymentInfo.setSupplyOrgCode("111310049001138");
       
       AccountInfo accountInfo = new AccountInfo();
       accountInfo.setAccountKey("13524124277");
//       accountInfo.setAccountKey("18960815029");
       accountInfo.setKeyType(KeyType.UNIFY);
       Response<TxnResultObject> actual = accountConsumeService.rollbackConsumeCancel(paymentInfo, oldPaymentInfo, accountInfo);
       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
       Assert.assertEquals(true, actual.isSuccess());
       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
   }
   
   public void testOfflineConsumeOk() throws Exception {
	   
	PaymentInfo paymentInfo = new PaymentInfo();
    paymentInfo.setMerchantCode("222222222222222");
    paymentInfo.setTerminalNo("00431000");
    paymentInfo.setAcceptOrgCode("004310000040000");
    paymentInfo.setInnerTxnType("132010");
    paymentInfo.setAmount(1l);
    paymentInfo.setAcceptDate("20131216");
    paymentInfo.setAcceptTime("164649");
    paymentInfo.setAcceptOperatorNo(null);
    paymentInfo.setAcceptTxnSeqNo("2013121800099999");
    paymentInfo.setBussinessType("132010");
    paymentInfo.setChannel("08");
    paymentInfo.setPayOrgCode("");
    paymentInfo.setTerminalSeqNo("131218006900456");
    paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
    paymentInfo.setTxnType(TxnType.TXN_CONSUME);
    paymentInfo.setTxnDscpt("测试用脱机消费");
    paymentInfo.setSupplyOrgCode("111310049001138");
    paymentInfo.setCardCnt("10000");
    paymentInfo.setCardTAC("000000000000000");
    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setAccountKey("0000000000003016");
    accountInfo.setKeyType(KeyType.CARD);
//     Response<TxnResultObject> actual = accountConsumeService.offlineConsume(accountInfo, paymentInfo);
//     logger.info("交易失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
//     Assert.assertEquals(true, actual.isSuccess());
//     logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
 }

}

