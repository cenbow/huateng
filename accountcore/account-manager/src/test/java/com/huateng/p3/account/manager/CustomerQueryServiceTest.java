package com.huateng.p3.account.manager;


import java.util.List;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;


/**
 * Created with IntelliJ IDEA. User: huwenjie Date: 14-01-03 Time: 下午15:33 o
 * change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:account-manager.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@Slf4j
public class CustomerQueryServiceTest extends TestCase{

	
	@Autowired
	CustomerQueryService customerQueryService;
	
	

	@Test
	@Rollback(false)
    public void testAll() throws Exception {
		//queryCustomerInfo();
		//queryCheckCardHandingInfo(); //重置支付密码鉴权
		//queryProductPropertyInfo(); 	//查询是否开通某产品属性
		//queryCustomerGrade(); //查询用户实名等级
		//queryCustomerSecurityInfo(); //查询用户安全问题
		//querySecurityQuestions();//查询密保问题
		//querySecuritySysData();//查询系统数据 如国籍 职业
		//querytMobilePhoneBinding();//手机绑定查询
		//querytBankCardBinding();//银行卡绑定查询
		querytAccountBankCardBinding();//查询客户绑定的银行卡
    }

	public void querytBankCardBinding(){
		TInfoBankcard tInfoBankCard=new TInfoBankcard();
		tInfoBankCard.setIdentityNo("410711198211181519");
		tInfoBankCard.setOpenBankId("中国农业银行");
		 Response<List<TInfoBankcard>> actual = customerQueryService.queryBankCardBinding(tInfoBankCard);
	        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
	        Assert.assertEquals(true, actual.isSuccess());
	        log.info("成功查询" + actual.getResult().size()+"条记录");
	        log.info("成功" + actual.getResult());
	}
	
	public void querytAccountBankCardBinding(){
		TInfoAccountBankCard tInfoBankCard=new TInfoAccountBankCard();
		tInfoBankCard.setFundAccountNo("8631554000000109");
		 Response<List<TInfoAccountBankCard>> actual = customerQueryService.queryAccountBankCardBinding(tInfoBankCard);
	        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
	        Assert.assertEquals(true, actual.isSuccess());
	        log.info("成功查询" + actual.getResult().size()+"条记录");
	        log.info("成功" + actual.getResult());
	}

	public void querytMobilePhoneBinding(){
		TInfoCustomer customer = new TInfoCustomer();
		customer.setMobileNo("13524124277");
		customer.setUnifyId("13524124277");
		 Response<TInfoCustomer> actual = customerQueryService.queryMobilePhoneBinding(customer);
	        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
	        Assert.assertEquals(true, actual.isSuccess());
	        log.info("成功" + actual.getResult());
	}

	public void queryCheckCardHandingInfo() {
		
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631027000008883");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		
		Response<CustomerResultObject> actual = customerQueryService.queryCheckCardHandingInfo(accountInfo);

        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
        Assert.assertEquals(true, actual.isSuccess());
        log.info("成功" + actual.getResult());
        
	}
	
	
	public void queryCustomerSecurityInfo() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631027000008883");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		
		Response<SecurityResultObject> actual = customerQueryService.queryCustomerSecurityInfo(accountInfo);

        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
        Assert.assertEquals(true, actual.isSuccess());
        log.info("成功" + actual.getResult());
        
	}

	

	public void queryCustomerInfo() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8631027000008883");
		accountInfo.setKeyType(KeyType.ACCOUNT);
		
		Response<CustomerResultObject> actual = customerQueryService.queryCustomerInfo(accountInfo);

        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));
        Assert.assertEquals(true, actual.isSuccess());
        log.info("成功" + actual.getResult());
        
	}

    public void queryRiskBlack() {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("8631027000008883");
        accountInfo.setKeyType(KeyType.ACCOUNT);

        ManagerLog managerLog = new ManagerLog();
        managerLog.setAcceptTxnSeqNo("111333");
        managerLog.setAcceptOrgCode("001999900000000");
        managerLog.setBankCardNo("1");
        managerLog.setIdno("1");
        Response<String> result = customerQueryService.queryRiskBlack(accountInfo, managerLog);
        log.info("queryRiskBlack RESULT: {}", result);
    }
    
    
    public void querySecurityQuestions() {
        ManagerLog managerLog = new ManagerLog();
        //交易描述
        managerLog.setTxnDscpt("开户激活");
        //受理机构码
        managerLog.setAcceptOrgCode("001999900000000");
        //交易渠道
        managerLog.setTxnChannel("02");
        //受理机构流水号
        managerLog.setAcceptTxnSeqNo("39461");
        //受理人
        managerLog.setInputUid("虎");
        //受理时间，格式yyyyMMddHHmmss
        managerLog.setInputTime(DateTime.now().toDate());
        //授权人
        managerLog.setCheckUid("虎");
        //授权时间，格式yyyyMMddHHmmss
        managerLog.setCheckTime(DateTime.now().toDate());
        Response<List<TDictCode>> actual = customerQueryService.querySecurityQuestions(managerLog);
        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        log.info("成功" + actual.getResult());
	}
    
    
    public void querySecuritySysData() {
        ManagerLog managerLog = new ManagerLog();
        
        String dictEng="profession";//国籍nationality  密码提示问题passwordQuestion 职业profession
        
        Response<List<TDictCode>> actual = customerQueryService.querySecuritySysData(managerLog,dictEng);
        log.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        log.info("成功" + actual.getResult().size());
	}
    

	
}
