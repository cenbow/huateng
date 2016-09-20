//package com.huateng.p3.account.service;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import base.BaseAccountServiceSpringTest;
//
//import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
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
// * Created with IntelliJ IDEA. User: huwenjie Date: 13-12-18 Time: 下午3:33 o
// * change this template use File | Settings | File Templates.
// */
//@Slf4j
//@Service
//public class AccountPreAuthServiceTest extends BaseAccountServiceSpringTest {
//
//	private Logger logger = LoggerFactory
//			.getLogger(AccountPreAuthServiceTest.class);
//	@Autowired
//	AccountPreAuthService accountPreAuthService;
//
//	@Autowired
//	SequenceGenerator sequenceGenerator;
//
//	@Test
//	public void testAll() throws Exception {
//
//		 testPreAuthApply(1000);	//预授权申请 
////		 testRollbackPreAuthApply(1000);	//预授权申请冲正
////		 testPreAuthApplycancel(1000L);	//预授权申请撤销
////		 testPreAuthApplyCommit(1000, "2001101020000624182730000025427");		//预授权申请完成
////		 testCashCommitFail(2, "2001101020000114090732000017035");
////		 testRollbackPreAuthApplyCancel(1000L);		//预授权申请撤销冲正
////		 testCancelPreAuthApplyCommit("2001101020000624182730000025427");	//预授权申请完成撤销
////		 testRollbackCancelPreAuthApplyCommit();	//预授权申请完成撤销冲正
////		 testRollbackPreAuthApplyCommit();	//预授权申请完成冲正
//		 
//	}
//
//	public void testPreAuthApply(long cashAmt) {
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151010");
//		paymentInfo.setBussinessType("151010");
//		paymentInfo.setAmount(cashAmt);
//		paymentInfo.setAcceptDate("20140616");
//		paymentInfo.setAcceptTime("104649");
//		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
//		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
//		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH);
//		paymentInfo.setTxnDscpt("预授权申请");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//		accountInfo.setTxnPassword("9F8751A660837FFB");
//		Response<TxnResultObject> actual = accountPreAuthService.preAuthApply(
//				paymentInfo, accountInfo);
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//
//	}
//	
//	public void testRollbackPreAuthApply(long cashAmt){
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151010");
//		paymentInfo.setBussinessType("151010");
//		paymentInfo.setAmount(cashAmt);
//		paymentInfo.setAcceptDate("20140616");
//		paymentInfo.setAcceptTime("104649");
//		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
//		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
//		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH);
//		paymentInfo.setTxnDscpt("预授权申请冲正");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//		
//		
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//		
//		PaymentInfo oldPaymentInfo = new PaymentInfo();
//		oldPaymentInfo.setAcceptOrgCode("004310000040000");
//		oldPaymentInfo.setAcceptDate("20140616");
//		oldPaymentInfo.setAcceptTime("104649");
//		oldPaymentInfo.setAmount(cashAmt);
//		oldPaymentInfo.setAcceptTxnSeqNo("2001101020000624172625000025126");
//		
//		Response<TxnResultObject> actual = accountPreAuthService.rollbackPreAuthApply(paymentInfo, oldPaymentInfo, accountInfo);
//		
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//	}
//
//	public void testPreAuthApplycancel(Long cancelCashAmt) {
//
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151011");
//		paymentInfo.setBussinessType("151011");
//		paymentInfo.setAmount(cancelCashAmt);
//		paymentInfo.setAcceptDate("20140619");
//		paymentInfo.setAcceptTime("104649");
//		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
//		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
//		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH);
//		paymentInfo.setTxnDscpt("预授权申请撤销");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//
//		PaymentInfo oldPaymentInfo = new PaymentInfo();
//		oldPaymentInfo.setAcceptOrgCode("004310000040000");
//		oldPaymentInfo.setAcceptDate("20140616");
//		oldPaymentInfo.setAcceptTime("104649");
//		oldPaymentInfo.setAmount(cancelCashAmt);
//		oldPaymentInfo.setAcceptTxnSeqNo("2001101020000624162118000024827");
//
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//
//		Response<TxnResultObject> actual = accountPreAuthService.cancelPreAuthApply(paymentInfo,
//				oldPaymentInfo, accountInfo);
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//
//	}
//
//	public void testPreAuthApplyCommit(long cashCommitAmt, String acceptSeqNo) {
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151020");
//		paymentInfo.setBussinessType("151020");
//		paymentInfo.setAmount(cashCommitAmt);
//		paymentInfo.setAcceptDate("20140619");
//		paymentInfo.setAcceptTime("100000");
//		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = acceptSeqNo;// "2001101020000113104639000016620";//sequenceGenerator.generatorPreAuthTxnSeq();
//		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
//		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
//		paymentInfo.setTxnDscpt("预授权完成");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//		
//		Response<TxnResultObject> actual = accountPreAuthService.preAuthComplete(paymentInfo, null, accountInfo);
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//	}
//
//	/*public void testCashCommitFail(long cashCommitFailAmt,
//			String failTxnAcceptSeqNo) {
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("181011");
//		paymentInfo.setBussinessType("181011");
//		paymentInfo.setAmount(cashCommitFailAmt);
//		paymentInfo.setAcceptDate("20131219");
//		paymentInfo.setAcceptTime("164649");
//		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
//		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
//		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//		paymentInfo.setTxnType(TxnType.TXN_CASH);
//		paymentInfo.setTxnDscpt("提现申请撤销");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//
//		PaymentInfo oldPaymentInfo = new PaymentInfo();
//		oldPaymentInfo.setAcceptOrgCode("004310000040000");
//		oldPaymentInfo.setAcceptDate("20131219");
//		oldPaymentInfo.setAcceptTime("164649");
//		oldPaymentInfo.setAmount(1L);
//		oldPaymentInfo.setAcceptTxnSeqNo(failTxnAcceptSeqNo);
//		log.debug("Fail seqNo is: {}", failTxnAcceptSeqNo);
//
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18018354882");
//		accountInfo.setKeyType(KeyType.UNIFY);
//
//		Response<TxnResultObject> actual = accountCashService.cashFailComplete(
//				paymentInfo, oldPaymentInfo, accountInfo);
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//
//	}*/
//	
//	public void testRollbackPreAuthApplyCancel(long cashAmt){
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151013");
//		paymentInfo.setBussinessType("151013");
//		paymentInfo.setAmount(cashAmt);
//		paymentInfo.setAcceptDate("20140616");
//		paymentInfo.setAcceptTime("104649");
//		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
//		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
//		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH);
//		paymentInfo.setTxnDscpt("预授权申请撤销冲正");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//		
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//		
//		PaymentInfo oldPaymentInfo = new PaymentInfo();
//		oldPaymentInfo.setAcceptOrgCode("004310000040000");
//		oldPaymentInfo.setAcceptDate("20140619");
//		oldPaymentInfo.setAcceptTime("104649");
//		oldPaymentInfo.setAcceptTxnSeqNo("2001101020000624162323000024829");
//		
//		Response<TxnResultObject> actual = accountPreAuthService.rollbackCancelPreAuthApply(paymentInfo, oldPaymentInfo, accountInfo);
//		
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//	}
//
//	public void testCancelPreAuthApplyCommit(String acceptSeqNo) {
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151021");
//		paymentInfo.setBussinessType("151021");
//		paymentInfo.setAmount(1000L);
//		paymentInfo.setAcceptDate("20140619");
//		paymentInfo.setAcceptTime("174649");
//		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
//		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
//		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
//		paymentInfo.setTxnDscpt("预授权完成撤销");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//		
//		PaymentInfo oldPaymentInfo = new PaymentInfo();
//		oldPaymentInfo.setAcceptDate("20140619");
//		oldPaymentInfo.setAcceptTime("100000");
//		oldPaymentInfo.setAcceptOrgCode("004310000040000");
//		oldPaymentInfo.setAmount(1000L);
//		oldPaymentInfo.setSupplyOrgCode("111310049001138");
//		oldPaymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
//		oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//		oldPaymentInfo.setAcceptTxnSeqNo(acceptSeqNo);
//		
//		Response<TxnResultObject> actual = accountPreAuthService.cancelPreAuthComplete(paymentInfo, oldPaymentInfo, accountInfo);
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//	}
//
//	public void testRollbackCancelPreAuthApplyCommit() {
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151023");
//		paymentInfo.setBussinessType("151023");
//		paymentInfo.setAmount(1000L);
//		paymentInfo.setAcceptDate("20140617");
//		paymentInfo.setAcceptTime("174649");
//		paymentInfo.setAcceptOperatorNo(null);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
//		paymentInfo.setTxnDscpt("预授权完成撤销冲正");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//		paymentInfo.setAcceptTxnSeqNo(sequenceGenerator.generatorPreAuthTxnSeq());
//
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//		
//		PaymentInfo oldPaymentInfo = new PaymentInfo();
//		oldPaymentInfo.setAcceptDate("20140619");
//		oldPaymentInfo.setAcceptTime("174649");
//		oldPaymentInfo.setAcceptOrgCode("004310000040000");
//		oldPaymentInfo.setAmount(1000L);
//		oldPaymentInfo.setSupplyOrgCode("111310049001138");
//		oldPaymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
//		oldPaymentInfo.setInnerTxnType("151021");
//		oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
//		oldPaymentInfo.setAcceptTxnSeqNo("2001101020000624183125000025429");
//		
//		Response<TxnResultObject> actual = accountPreAuthService.rollbackCancelPreAuthComplete(paymentInfo, oldPaymentInfo, accountInfo);
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//	}
//
//	public void testRollbackPreAuthApplyCommit() {
//		PaymentInfo paymentInfo = new PaymentInfo();
//		paymentInfo.setMerchantCode("222222222222222");
//		paymentInfo.setTerminalNo("00431000");
//		paymentInfo.setAcceptOrgCode("004310000040000");
//		paymentInfo.setInnerTxnType("151022");
//		paymentInfo.setBussinessType("151022");
//		paymentInfo.setAmount(1000L);
//		paymentInfo.setAcceptDate("20140618");
//		paymentInfo.setAcceptTime("104649");
//		paymentInfo.setAcceptOperatorNo(null);
//		paymentInfo.setChannel("08");
//		paymentInfo.setPayOrgCode("");
//		paymentInfo.setTerminalSeqNo("131218006900456");
//		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
//		paymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
//		paymentInfo.setTxnDscpt("预授权完成冲正");
//		paymentInfo.setSupplyOrgCode("111310049001138");
//		paymentInfo.setAcceptTxnSeqNo(sequenceGenerator.generatorPreAuthTxnSeq());
//
//		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccountKey("18906915756");
//		accountInfo.setKeyType(KeyType.UNIFY);
//		
//		PaymentInfo oldPaymentInfo = new PaymentInfo();
//		oldPaymentInfo.setAcceptDate("20140619");
//		oldPaymentInfo.setAcceptTime("100000");
//		oldPaymentInfo.setAcceptOrgCode("004310000040000");
//		oldPaymentInfo.setAmount(1000L);
//		oldPaymentInfo.setSupplyOrgCode("111310049001138");
//		oldPaymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
//		oldPaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
//		oldPaymentInfo.setAcceptTxnSeqNo("2001101020000624173312000025230");
//		
//		Response<TxnResultObject> actual = accountPreAuthService.rollbackPreAuthComplete(paymentInfo, oldPaymentInfo, accountInfo);
//		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
//				+ BussErrorCode.explain(actual.getErrorCode()));
//		Assert.assertEquals(true, actual.isSuccess());
//		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
//	}
//	
//}
