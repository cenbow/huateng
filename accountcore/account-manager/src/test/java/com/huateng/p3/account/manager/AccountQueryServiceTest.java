package com.huateng.p3.account.manager;


import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.AccountResultObject;
import com.huateng.p3.account.common.bizparammodel.LogOnlinePaymentObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.TxnQueryObj;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.persistence.models.TInfoSubaccount;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA. User: huwenjie Date: 14-01-03 Time: ����15:33 o
 * change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:account-manager.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AccountQueryServiceTest extends TestCase {

	private Logger logger = LoggerFactory
			.getLogger(AccountQueryServiceTest.class);

	@Autowired
	AccountQueryService accountQueryService;

	@Test
    public void testAll() throws Exception {
        
		//querysubaccount();	
//		queryAccountBalance();//账户余额查询
		queryRealnameAuthenticationStatusDetails();
		//queryHisTxn();
	//	queryDayTxn();
		//queryTxnBySeqNo();
		queryAccountPaymentRecord();
    }
	
	
	public void queryTxnBySeqNo() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631021000561956");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		TxnQueryObj txnQueryObj =new TxnQueryObj();
		txnQueryObj.setSupplyOrgCode("111110049000019");
		txnQueryObj.setAcceptTransSeqNo("213084");
		
		Response<List<LogOnlinePaymentObject>> actual = accountQueryService.queryTxnBySeqNo(accountInfo , txnQueryObj);
		
		
		logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
        
	
		
	}
	
	public void queryHisTxn() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631027000008883");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		
		Response<List<LogOnlinePaymentObject>> actual = accountQueryService.queryHisTxn(accountInfo , new TxnQueryObj());
		
		
		logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
        
	
		
	}
	
	public void queryDayTxn() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631027000008883");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		
		Response<List<LogOnlinePaymentObject>> actual = accountQueryService.queryDayTxn(accountInfo , new TxnQueryObj());
		
		logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
        
		// TODO Auto-generated method stub
		
	}
	


	public void queryAccountBalance() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631554000000069");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		
		Response<List<AccountResultObject>> actual = accountQueryService.queryAccountBalance(accountInfo);
		
		logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
        
		// TODO Auto-generated method stub
		
	}


	public void querysubaccount() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631027000008883");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		
		Response<List<TInfoSubaccount>> actual = accountQueryService.querysubaccount(accountInfo);
		
		logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
        
	}
		
	public void queryRealnameAuthenticationStatusDetails()
	{
		AccountInfo accountInfo = new AccountInfo();
//        accountInfo.setAccountKey("18018354882");//没有实名
        accountInfo.setAccountKey("18910162811");//已经实名
        accountInfo.setKeyType(KeyType.UNIFY);
		
		ManagerLog managerLog = new ManagerLog();
		Response<List<TRealnameApply>> actual = accountQueryService.queryRealnameAuthenticationStatusDetails(accountInfo,managerLog);
		logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}

	public void queryAccountPaymentRecord(){
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("13524124279");//18018354882
		accountInfo.setKeyType(KeyType.UNIFY);

		TxnQueryObj queryObj = new TxnQueryObj();
		queryObj.setQueryType(TxnQueryObj.chargeQueryType);
		queryObj.setPageSize(2);
		Response<Paging<TLogAccountPayment>> accPayment = accountQueryService.queryAccountPaymentRecord(accountInfo , queryObj);
		
		logger.info("失败时，错误代码" + accPayment.getErrorCode() + "错误原因：" + BussErrorCode.explain(accPayment.getErrorCode()));     
		
        Assert.assertEquals(true, accPayment.isSuccess());
        logger.info("成功" + accPayment.getResult());
        logger.info("查询总条数:" + accPayment.getResult().getTotal());
        for (TLogAccountPayment payment : accPayment.getResult().getData()) {
        	logger.info("查询到流水号:" + payment.getTxnSeqNo());
		}
	}
}
