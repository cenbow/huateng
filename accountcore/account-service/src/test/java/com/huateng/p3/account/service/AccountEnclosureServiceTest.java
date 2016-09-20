//package com.huateng.p3.account.service;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import base.BaseAccountServiceSpringTest;
//
//import com.huateng.p3.account.common.bizparammodel.AccountInfo;
//import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
//import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
//import com.huateng.p3.account.common.enummodel.BussErrorCode;
//import com.huateng.p3.account.common.enummodel.KeyType;
//import com.huateng.p3.account.common.enummodel.TxnSeqType;
//import com.huateng.p3.account.common.enummodel.TxnType;
//import com.huateng.p3.component.Response;
//
///**
// * Created with IntelliJ IDEA.
// * User: jijiandong
// * Date: 14-5-9
// */
//public class AccountEnclosureServiceTest extends BaseAccountServiceSpringTest {
//
//
//    private Logger logger = LoggerFactory.getLogger(AccountEnclosureServiceTest.class);
//
//    @Autowired
//    AccountEnclosureService accountEnclosureService;
//
//    @Test
//    public void testAll() throws Exception {
//        
//  	testEnclosureInAccountOk();//圈存正确的参数
////    	testRollbackEnclosureInAccountOk();//圈存冲正正确的参数
////    	testEnclosureOutAccountOk();//圈提正确的参数
////    	testRollbackEnclosureOutAccountOk();//圈提冲正正确的参数
//    }
//
//
//
//   public void testEnclosureInAccountOk() throws Exception {
//	   PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("00431000");
//       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构，必须支持该交易
//       paymentInfo.setInnerTxnType("111010");//圈存
//       paymentInfo.setAmount(10l);
//       paymentInfo.setAcceptDate("20131220");
//       paymentInfo.setAcceptTime("164649");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("2013121800094790");//须每次都修改
//       paymentInfo.setBussinessType("111010");
//       paymentInfo.setChannel("08");
//       paymentInfo.setPayOrgCode("");
//       paymentInfo.setTerminalSeqNo("131218006900456");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//       paymentInfo.setTxnType(TxnType.TXN_ENCLOSURE);
//       paymentInfo.setTxnDscpt("圈存");
//       paymentInfo.setSupplyOrgCode("111310049001138");//商户，必须支持该交易
//       AccountInfo accountInfo = new AccountInfo();
//       accountInfo.setAccountKey("13892341238");
//       accountInfo.setKeyType(KeyType.UNIFY);
//       accountInfo.setTargetAccountKey("8899000008074172");//卡号
//       accountInfo.setTargetKeyType(KeyType.CARD);
//       //String newTxnPassword = en.encrypt(newPinkey, cardOrAccountNo);
//       accountInfo.setTxnPassword("49302AEC318123E0");//本地加密后的交易密码
//       Response<TxnResultObject> actual = accountEnclosureService.enclosureInAccount(paymentInfo, accountInfo);
//       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
//       Assert.assertEquals(true, actual.isSuccess());
//       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//    }   
//     
//   public void testRollbackEnclosureInAccountOk() throws Exception {
//	   PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("00431000");
//       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构，必须支持该交易
//       paymentInfo.setInnerTxnType("111012");//圈存冲正
//       paymentInfo.setAmount(10l);
//       paymentInfo.setAcceptDate("20131220");
//       paymentInfo.setAcceptTime("164649");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("2013121800094791");//须每次都修改
//       paymentInfo.setBussinessType("111012");//圈存冲正
//       paymentInfo.setChannel("08");
//       paymentInfo.setPayOrgCode("");
//       paymentInfo.setTerminalSeqNo("131218006900456");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//       paymentInfo.setTxnType(TxnType.TXN_ENCLOSURE);
//       paymentInfo.setTxnDscpt("圈存");
//       paymentInfo.setSupplyOrgCode("111310049001138");//商户，必须支持该交易
//       
//       PaymentInfo oldPaymentInfo = new PaymentInfo();       
//       oldPaymentInfo.setAcceptOrgCode("004310000040000");
//       oldPaymentInfo.setAcceptDate("20131220");
//       oldPaymentInfo.setAcceptTime("164649");      
//       oldPaymentInfo.setAcceptTxnSeqNo("2013121800094790"); //原订单的请求序列号
//       oldPaymentInfo.setMerchantCode("222222222222222");
//       oldPaymentInfo.setTerminalNo("00431000");    
//       oldPaymentInfo.setInnerTxnType("111010");//原交易类型，圈存
//       oldPaymentInfo.setAmount(10l);    
//       oldPaymentInfo.setAcceptOperatorNo(null);     
//       oldPaymentInfo.setBussinessType("111010");//原交易类型，圈存
//       oldPaymentInfo.setChannel("08");
//       oldPaymentInfo.setPayOrgCode("");
//       oldPaymentInfo.setTerminalSeqNo("131218006900456");
//       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//       oldPaymentInfo.setTxnType(TxnType.TXN_ENCLOSURE);
//       oldPaymentInfo.setTxnDscpt("圈存");
//       oldPaymentInfo.setSupplyOrgCode("111310049001138");
//       
//       AccountInfo accountInfo = new AccountInfo();
//       accountInfo.setAccountKey("13892341238");
//       accountInfo.setKeyType(KeyType.UNIFY);
//       accountInfo.setTargetAccountKey("8899000008074172");//卡号
//       accountInfo.setTargetKeyType(KeyType.CARD);
//       //String newTxnPassword = en.encrypt(newPinkey, cardOrAccountNo);
//       //accountInfo.setTxnPassword("49302AEC318123E0");//本地加密后的交易密码
//       Response<TxnResultObject> actual = accountEnclosureService.rollbackEnclosureInAccount(paymentInfo, oldPaymentInfo, accountInfo);
//       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
//       Assert.assertEquals(true, actual.isSuccess());
//       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//    }   
//   
//   public void testEnclosureOutAccountOk() throws Exception {
//	   PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("00431000");
//       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构，必须支持该交易
//       paymentInfo.setInnerTxnType("111020");//圈提
//       paymentInfo.setAmount(10l);
//       paymentInfo.setAcceptDate("20131220");
//       paymentInfo.setAcceptTime("164649");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("2013121800094792");//须每次都修改
//       paymentInfo.setBussinessType("111020");//圈提
//       paymentInfo.setChannel("08");
//       paymentInfo.setPayOrgCode("");
//       paymentInfo.setTerminalSeqNo("131218006900456");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//       paymentInfo.setTxnType(TxnType.TXN_ENCLOSURE);
//       paymentInfo.setTxnDscpt("圈存");
//       paymentInfo.setSupplyOrgCode("111310049001138");//商户，必须支持该交易
//       AccountInfo accountInfo = new AccountInfo();
//       accountInfo.setAccountKey("13892341238");
//       accountInfo.setKeyType(KeyType.UNIFY);
//       accountInfo.setTargetAccountKey("8899000008074172");//卡号
//       accountInfo.setTargetKeyType(KeyType.CARD);
//       //String newTxnPassword = en.encrypt(newPinkey, cardOrAccountNo);
//       //accountInfo.setTxnPassword("49302AEC318123E0");//本地加密后的交易密码
//       Response<TxnResultObject> actual = accountEnclosureService.enclosureOutAccount(paymentInfo, accountInfo);
//       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
//       Assert.assertEquals(true, actual.isSuccess());
//       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//    }   
//   
//   public void testRollbackEnclosureOutAccountOk() throws Exception {
//	   PaymentInfo paymentInfo = new PaymentInfo();
//       paymentInfo.setMerchantCode("222222222222222");
//       paymentInfo.setTerminalNo("00431000");
//       paymentInfo.setAcceptOrgCode("004310000040000");//受理机构，必须支持该交易
//       paymentInfo.setInnerTxnType("111022");//圈提冲正
//       paymentInfo.setAmount(10l);
//       paymentInfo.setAcceptDate("20131220");
//       paymentInfo.setAcceptTime("164649");
//       paymentInfo.setAcceptOperatorNo(null);
//       paymentInfo.setAcceptTxnSeqNo("2013121800094793");//须每次都修改
//       paymentInfo.setBussinessType("111022");//圈提冲正
//       paymentInfo.setChannel("08");
//       paymentInfo.setPayOrgCode("");
//       paymentInfo.setTerminalSeqNo("131218006900456");
//       paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//       paymentInfo.setTxnType(TxnType.TXN_ENCLOSURE);
//       paymentInfo.setTxnDscpt("圈存");
//       paymentInfo.setSupplyOrgCode("111310049001138");//商户，必须支持该交易
//       
//       PaymentInfo oldPaymentInfo = new PaymentInfo();       
//       oldPaymentInfo.setAcceptOrgCode("004310000040000");
//       oldPaymentInfo.setAcceptDate("20131220");
//       oldPaymentInfo.setAcceptTime("164649");      
//       oldPaymentInfo.setAcceptTxnSeqNo("2013121800094792"); //原订单的请求序列号
//       oldPaymentInfo.setMerchantCode("222222222222222");
//       oldPaymentInfo.setTerminalNo("00431000");    
//       oldPaymentInfo.setInnerTxnType("111020");//原交易类型，圈提
//       oldPaymentInfo.setAmount(10l);    
//       oldPaymentInfo.setAcceptOperatorNo(null);     
//       oldPaymentInfo.setBussinessType("111020");//原交易类型，圈提
//       oldPaymentInfo.setChannel("08");
//       oldPaymentInfo.setPayOrgCode("");
//       oldPaymentInfo.setTerminalSeqNo("131218006900456");
//       oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//       oldPaymentInfo.setTxnType(TxnType.TXN_ENCLOSURE);
//       oldPaymentInfo.setTxnDscpt("圈存");
//       oldPaymentInfo.setSupplyOrgCode("111310049001138");
//       
//       AccountInfo accountInfo = new AccountInfo();
//       accountInfo.setAccountKey("13892341238");
//       accountInfo.setKeyType(KeyType.UNIFY);
//       accountInfo.setTargetAccountKey("8899000008074172");//卡号
//       accountInfo.setTargetKeyType(KeyType.CARD);
//       //String newTxnPassword = en.encrypt(newPinkey, cardOrAccountNo);
//       //accountInfo.setTxnPassword("49302AEC318123E0");//本地加密后的交易密码
//       Response<TxnResultObject> actual = accountEnclosureService.rollbackEnclosureOutAccount(paymentInfo, oldPaymentInfo, accountInfo);
//       logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
//       Assert.assertEquals(true, actual.isSuccess());
//       logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//    }   
//}
//
