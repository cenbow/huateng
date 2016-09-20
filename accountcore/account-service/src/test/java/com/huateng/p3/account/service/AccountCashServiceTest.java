package com.huateng.p3.account.service;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA. User: huwenjie Date: 13-12-18 Time: 下午3:33 o
 * change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
public class AccountCashServiceTest extends BaseAccountServiceSpringTest {

	private Logger logger = LoggerFactory
			.getLogger(AccountCashServiceTest.class);
	@Autowired
	AccountCashService accountCashService;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Test
	public void testAll() throws Exception {

		 testPreAuthApply(1);
		//testPreAuthApplycancel(1);
		 //testPreAuthApplyCommit(1,"2001101020000114090732000017035");
		//testCashCommitFail(2, "2001101020000114090732000017035");

	}

	public void testPreAuthApply(long cashAmt) {
		PaymentInfo paymentInfo = new PaymentInfo();	
		paymentInfo.setMerchantCode("222222222222222");
		paymentInfo.setTerminalNo("00431000");
		paymentInfo.setAcceptOrgCode("004310000040000");
		paymentInfo.setInnerTxnType("181000");
		paymentInfo.setBussinessType("181000");
		paymentInfo.setAmount(cashAmt);
		paymentInfo.setAcceptDate("20131219");
		paymentInfo.setAcceptTime("164649");
		paymentInfo.setAcceptOperatorNo(null);
//		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
		String acceptTxnSeqNo = "131218006900456";
		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
		paymentInfo.setChannel("08");
		paymentInfo.setPayOrgCode(null);
		paymentInfo.setTerminalSeqNo("131218006900456");
		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
		paymentInfo.setTxnType(TxnType.TXN_CASH);
		paymentInfo.setTxnDscpt("提现申请");
		paymentInfo.setSupplyOrgCode("111310049001138");
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("18018354882");
		accountInfo.setKeyType(KeyType.UNIFY);
		accountInfo.setTxnPassword("9F8751A660837FFB");
		Response<TxnResultObject> actual = accountCashService.cashApply(
				paymentInfo, accountInfo);
		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
				+ BussErrorCode.explain(actual.getErrorCode()));
		Assert.assertEquals(true, actual.isSuccess());
		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());

		// accountCashService.cashFailComplete()

	}

	public void testPreAuthApplycancel(long cancelCashAmt) {

		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setMerchantCode("222222222222222");
		paymentInfo.setTerminalNo("00431000");
		paymentInfo.setAcceptOrgCode("004310000040000");
		paymentInfo.setInnerTxnType("181000");
		paymentInfo.setBussinessType("181000");
		paymentInfo.setAmount(cancelCashAmt);
		paymentInfo.setAcceptDate("20131219");
		paymentInfo.setAcceptTime("164649");
		paymentInfo.setAcceptOperatorNo(null);
		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
		paymentInfo.setChannel("08");
		paymentInfo.setPayOrgCode(null);
		paymentInfo.setTerminalSeqNo("131218006900456");
		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
		paymentInfo.setTxnType(TxnType.TXN_CASH);
		paymentInfo.setTxnDscpt("提现申请撤销");
		paymentInfo.setSupplyOrgCode("111310049001138");

		PaymentInfo oldPaymentInfo = new PaymentInfo();
		oldPaymentInfo.setAcceptOrgCode("004310000040000");
		oldPaymentInfo.setAcceptDate("20131219");
		oldPaymentInfo.setAcceptTime("164649");
		oldPaymentInfo.setAmount(1L);
		oldPaymentInfo.setAcceptTxnSeqNo("131218006900456");

		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("18018354882");
		accountInfo.setKeyType(KeyType.UNIFY);

		Response<TxnResultObject> actual = accountCashService.cashApplyCancel(
				paymentInfo, oldPaymentInfo, accountInfo);
		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
				+ BussErrorCode.explain(actual.getErrorCode()));
		Assert.assertEquals(true, actual.isSuccess());
		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());

	}

	public void testPreAuthApplyCommit(long cashCommitAmt, String acceptSeqNo) {
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setMerchantCode("222222222222222");
		paymentInfo.setTerminalNo("00431000");
		paymentInfo.setAcceptOrgCode("004310000040000");
		paymentInfo.setInnerTxnType("181010");
		paymentInfo.setBussinessType("181010");
		paymentInfo.setAmount(cashCommitAmt);
		paymentInfo.setAcceptDate("20131219");
		paymentInfo.setAcceptTime("164649");
		paymentInfo.setAcceptOperatorNo(null);
		String acceptTxnSeqNo = acceptSeqNo;// "2001101020000113104639000016620";//sequenceGenerator.generatorPreAuthTxnSeq();
		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
		paymentInfo.setChannel("08");
		paymentInfo.setPayOrgCode("");
		paymentInfo.setTerminalSeqNo("131218006900456");
		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
		paymentInfo.setTxnType(TxnType.TXN_CASH_END);
		paymentInfo.setTxnDscpt("提现申请确认");
		paymentInfo.setSupplyOrgCode("111310049001138");

		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("18018354882");
		accountInfo.setKeyType(KeyType.UNIFY);
		Response<TxnResultObject> actual = accountCashService.cashComplete(
				accountInfo,paymentInfo );
		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
				+ BussErrorCode.explain(actual.getErrorCode()));
		Assert.assertEquals(true, actual.isSuccess());
		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());
	}

	public void testCashCommitFail(long cashCommitFailAmt,
			String failTxnAcceptSeqNo) {
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setMerchantCode("222222222222222");
		paymentInfo.setTerminalNo("00431000");
		paymentInfo.setAcceptOrgCode("004310000040000");
		paymentInfo.setInnerTxnType("181011");
		paymentInfo.setBussinessType("181011");
		paymentInfo.setAmount(cashCommitFailAmt);
		paymentInfo.setAcceptDate("20131219");
		paymentInfo.setAcceptTime("164649");
		paymentInfo.setAcceptOperatorNo(null);
		String acceptTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
		log.debug("acceptTxnSeqNo:" + acceptTxnSeqNo);
		paymentInfo.setAcceptTxnSeqNo(acceptTxnSeqNo);
		paymentInfo.setChannel("08");
		paymentInfo.setPayOrgCode("");
		paymentInfo.setTerminalSeqNo("131218006900456");
		paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
		paymentInfo.setTxnType(TxnType.TXN_CASH);
		paymentInfo.setTxnDscpt("提现申请撤销");
		paymentInfo.setSupplyOrgCode("111310049001138");

		PaymentInfo oldPaymentInfo = new PaymentInfo();
		oldPaymentInfo.setAcceptOrgCode("004310000040000");
		oldPaymentInfo.setAcceptDate("20131219");
		oldPaymentInfo.setAcceptTime("164649");
		oldPaymentInfo.setAmount(1L);
		oldPaymentInfo.setAcceptTxnSeqNo(failTxnAcceptSeqNo);
		log.debug("Fail seqNo is: {}", failTxnAcceptSeqNo);

		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("18018354882");
		accountInfo.setKeyType(KeyType.UNIFY);

		Response<TxnResultObject> actual = accountCashService.cashFailComplete(
				paymentInfo, oldPaymentInfo, accountInfo);
		logger.info("交易信失败时，错误代码" + actual.getErrorCode() + "错误原因："
				+ BussErrorCode.explain(actual.getErrorCode()));
		Assert.assertEquals(true, actual.isSuccess());
		logger.info("交易成功，交易流水号为" + actual.getResult().getTxnSeqNo());

	}

}
