package com.huateng.p3.account.manager;


import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;



import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.SecurityQuestionInfo;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
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
public class CustomerManagerServiceTest extends TestCase {

	public Logger logger = LoggerFactory
			.getLogger(CustomerManagerServiceTest.class);

	@Autowired
	CustomerManagerService customerManagerService;
	
	@Autowired
	BankCardManagerService bankCardManagerService;

	@Test
	@Rollback(false)
    public void testAll() throws Exception {
        
		//openCustomer();				//开户
		//activeProductProperty();	    //激活某产品属性
		//unActiveProductProperty();	//关闭某产品属性
		//lockCustomer();				//客户锁定
		//unlockCustomer();			    //客户解锁
		//setTxnStatus();				//设置交易状态
		//lockCustomer();					//客户锁定
		//activationCustomer();       //激活账户
		//querySecurityQuestions();
		//querySecurityQuestionAndAnswer();
		//changeSecrurityQuestionAndAnswer();//设置密保问题和答案
		//modfiyLoginPasswdWithoutOldPwd();//修改账户登录密码(无需原密码)
//		modfiyLoginPasswdWithOldPwd();
		//closeCustomer();//销户
		resetLoginPasswd();//重置登录密码
		//LostCustomerByUnifyId();//账户挂失
		//unLostCustomerByUnifyId();//账户解挂
		//bindBankCard();//银行卡绑定
		//unBindBankCard();//银行卡解绑
		//mobilePhoneBinding();//手机绑定
		//mobilePhoneBinding();//手机绑定变更
		//unMobilePhoneBinding();//手机解绑
		//modifyCustomerInfo();//修改个人信息
		
    }
	
	public void unMobilePhoneBinding(){
		TInfoCustomer customer = new TInfoCustomer();
		customer.setMobileNo("");
		customer.setUnifyId("123456@qq.com");
		ManagerLog managerLog = new ManagerLog();
		 Response<String> actual = customerManagerService.unMobilePhoneBinding(customer, managerLog);
	        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
	        Assert.assertEquals(true, actual.isSuccess());
	        logger.info("成功" + actual.getResult());
	}
	
	
	public void mobilePhoneBinding(){
		TInfoCustomer customer = new TInfoCustomer();
		customer.setMobileNo("13524124277");
		customer.setUnifyId("13524124277");
		ManagerLog managerLog = new ManagerLog();
		 Response<String> actual = customerManagerService.mobilePhoneBinding(customer, managerLog);
	        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
	        Assert.assertEquals(true, actual.isSuccess());
	        logger.info("成功" + actual.getResult());
	}
	
	public void unBindBankCard(){
		TInfoBankcard bankCard=new TInfoBankcard();
		bankCard.setBankCardNo("6214830213034019");
		bankCard.setBankCode("");
		bankCard.setIdentityNo("433126201301010017");
		String bindingType="";//绑定类型
		ManagerLog managerLog = new ManagerLog();
		managerLog.setTxnChannel("02");
		managerLog.setIdtype("1");
		managerLog.setIdno("433126201411260019");
		managerLog.setName("李中伟");
		managerLog.setAcceptOrgCode("001999999020000");
		//managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		managerLog.setIdtype("1");
		managerLog.setInputTime(DateTime.now().toDate());
		managerLog.setCheckTime(DateTime.now().toDate());
		TInfoCustomer customer = new TInfoCustomer();
		customer.setIsRealname("4");
		//customer.setIsRequestCertificate("0");
		customer.setRegisterType("0");//注册类型
		customer.setMobileNo("13524124299");
		customer.setUnifyId("13524124299");
		//customer.setGender("1");
		customer.setIdentifyType("1");
		customer.setIdentifyNo("433126201411260019");
		//customer.setApanage("001340100000000");
		//customer.setAreaCode("340000");
		//customer.setCityCode("340400");
		//customer.setContactAddress("淮南高gao家湾");
		//customer.setCustomerGrade("1");
		//customer.setCustomerType("1");
		//customer.setNationality("cn");
		//customer.setProfession("软件工程师");
		//customer.setIdentifyExpiredDate("20140828");
		customer.setCustomerNo("8630554000000109");
        Response<String> actual = bankCardManagerService.unbindBankCardByBankCardNo(bankCard, customer, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}
	
	public void bindBankCard(){
		TInfoBankcard bankCard=new TInfoBankcard();
		bankCard.setBankCardNo("6214830213034019");
		bankCard.setBankCode("");
		bankCard.setIdentityNo("433126201301010017");
		String bindingType="";//绑定类型
		ManagerLog managerLog = new ManagerLog();
		managerLog.setTxnChannel("02");
		managerLog.setIdtype("1");
		managerLog.setIdno("433126201411260019");
		managerLog.setName("李中伟");
		managerLog.setAcceptOrgCode("001999999020000");
		//managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		managerLog.setIdtype("1");
		managerLog.setInputTime(DateTime.now().toDate());
		managerLog.setCheckTime(DateTime.now().toDate());
		TInfoCustomer customer = new TInfoCustomer();
		customer.setIsRealname("4");
		//customer.setIsRequestCertificate("0");
		customer.setRegisterType("0");//注册类型
		customer.setMobileNo("13524124299");
		customer.setUnifyId("13524124299");
		//customer.setGender("1");
		customer.setIdentifyType("1");
		customer.setIdentifyNo("433126201411260019");
		//customer.setApanage("001340100000000");
		//customer.setAreaCode("340000");
		//customer.setCityCode("340400");
		//customer.setContactAddress("淮南高gao家湾");
		//customer.setCustomerGrade("1");
		//customer.setCustomerType("1");
		//customer.setNationality("cn");
		//customer.setProfession("软件工程师");
		//customer.setIdentifyExpiredDate("20140828");
		customer.setCustomerNo("8630554000000109");
        Response<String> actual = bankCardManagerService.bindBankCard(bankCard, bindingType, customer, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}
	
	public void unLostCustomerByUnifyId(){
		ManagerLog managerLog = new ManagerLog();
		managerLog.setTxnChannel("02");
		managerLog.setIdtype("");
		managerLog.setIdno("310120198811187893");
		managerLog.setName("dd4dd");
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		managerLog.setIdtype("6");
		managerLog.setInputTime(DateTime.now().toDate());
		managerLog.setCheckTime(DateTime.now().toDate());
		TInfoCustomer customer = new TInfoCustomer();
		customer.setIsRealname("3");
		customer.setName("dd4dd");  
		customer.setIsRequestCertificate("0");
		customer.setRegisterType("1");//注册类型
		customer.setMobileNo("13524124279");
		customer.setUnifyId("13524124279");
		customer.setGender("1");
		customer.setIdentifyType("6");
		customer.setIdentifyNo("310120198811187893");
		customer.setApanage("001340100000000");
		customer.setAreaCode("340000");
		customer.setCityCode("340400");
		customer.setContactAddress("淮南高gao家湾");
		customer.setCustomerGrade("1");
		customer.setCustomerType("1");
		customer.setNationality("cn");
		customer.setProfession("软件工程师");
		customer.setIdentifyExpiredDate("20140828");
		customer.setCustomerNo("8630554000000081");
        Response<String> actual = customerManagerService.unLostCustomerByUnifyId(customer, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}
	

	public void LostCustomerByUnifyId(){
		ManagerLog managerLog = new ManagerLog();
		managerLog.setTxnChannel("02");
		managerLog.setIdtype("");
		managerLog.setIdno("310120198811187893");
		managerLog.setName("dd4dd");
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		managerLog.setIdtype("6");
		managerLog.setInputTime(DateTime.now().toDate());
		managerLog.setCheckTime(DateTime.now().toDate());
		TInfoCustomer customer = new TInfoCustomer();
		customer.setIsRealname("3");
		customer.setName("dd4dd");  
		customer.setIsRequestCertificate("0");
		customer.setRegisterType("1");//注册类型
		customer.setMobileNo("13524124279");
		customer.setUnifyId("13524124279");
		customer.setGender("1");
		customer.setIdentifyType("6");
		customer.setIdentifyNo("310120198811187893");
		customer.setApanage("001340100000000");
		customer.setAreaCode("340000");
		customer.setCityCode("340400");
		customer.setContactAddress("淮南高gao家湾");
		customer.setCustomerGrade("1");
		customer.setCustomerType("1");
		customer.setNationality("cn");
		customer.setProfession("软件工程师");
		customer.setIdentifyExpiredDate("20140828");
		customer.setCustomerNo("8630554000000081");
        Response<String> actual = customerManagerService.LostCustomerByUnifyId(customer, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}
	
	public void resetLoginPasswd(){
		 ManagerLog managerLog = new ManagerLog();
	        //交易类型
	        managerLog.setInnerTxnType("11111");
	        //受理机构码
	        managerLog.setAcceptOrgCode("001999999020000");
	        //交易渠道
	        managerLog.setTxnChannel("02");
	        //受理机构流水号
	        managerLog.setAcceptTxnSeqNo("39462");
	        //受理人
	        managerLog.setInputUid("虎");
	        //受理时间，格式yyyyMMddHHmmss
	        managerLog.setInputTime(DateTime.now().toDate());
	        //授权人
	        managerLog.setCheckUid("虎");
	        //授权时间，格式yyyyMMddHHmmss
	        managerLog.setCheckTime(DateTime.now().toDate());

	        //授权人
	        //4806DF31535485B0F831C54E62C66B35
//	        managerLog.setOldLoginPassword("4806DF31535485B0F831C54E62C66B35");
	        //授权人
	        managerLog.setNewLoginPassword("123456");
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124299");
        accountInfo.setKeyType(KeyType.UNIFY);
		Response<String> actual = customerManagerService.resetLoginPasswd(accountInfo, managerLog);
	    logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
	    Assert.assertEquals(true, actual.isSuccess());
	    logger.info("成功" + actual.getResult());
	}
	
	
public void closeCustomer(){
	TInfoCustomer customer = new TInfoCustomer();
	customer.setIsRealname("3");
	customer.setName("bfmssss");  
	customer.setIsRequestCertificate("0");
	customer.setRegisterType("1");//注册类型
	customer.setMobileNo("13524124279");
	customer.setUnifyId("13524124279");
	customer.setGender("1");
	customer.setIdentifyType("6");
	customer.setIdentifyNo("310120198811187893");
	customer.setApanage("001340100000000");
	customer.setAreaCode("340000");
	customer.setCityCode("340400");
	customer.setContactAddress("淮南高gao家湾");
	customer.setCustomerGrade("1");
	customer.setCustomerType("1");
	customer.setNationality("cn");
	customer.setProfession("软件工程师");
	customer.setIdentifyExpiredDate("20140828");
	customer.setCustomerNo("8630554000000069");
	String cardNo = "8899000008075714";
	String cardType = "1";
	ManagerLog managerLog = new ManagerLog();
	managerLog.setMerchantCode("222222222222222");
	managerLog.setTerminalNo("99999999");
	managerLog.setAcceptOrgCode("001999999020000");
	managerLog.setAcceptTxnSeqNo("38461");
	managerLog.setTxnChannel("02");
	managerLog.setInputTime(DateTime.now().toDate());
	managerLog.setCheckTime(DateTime.now().toDate());
	managerLog.setTxnDscpt("非电信手机用户开户申请");
	managerLog.setAcceptDate("20140213");
	managerLog.setAcceptTime("101201");
	managerLog.setIdtype("6");
	managerLog.setIdno("310120198811187893");
	managerLog.setName("dd4dd");
	Response<String> actual = customerManagerService.closeCustomer(customer,cardNo,cardType,managerLog);
    logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
    Assert.assertEquals(true, actual.isSuccess());
    logger.info("成功" + actual.getResult());
}

	public void unlockCustomer() {
		ManagerLog managerLog = new ManagerLog();
		managerLog.setTxnChannel("02");
		managerLog.setIdtype("");
		managerLog.setIdno("43022619870228252X");
		managerLog.setName("gfs");
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124279");
        accountInfo.setKeyType(KeyType.UNIFY);
        Response<String> actual = customerManagerService.checkIdUnlockCustomer(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
		
	}

	public void lockCustomer() {
		ManagerLog managerLog = new ManagerLog();
		managerLog.setTxnChannel("02");
		managerLog.setIdtype("");
		managerLog.setIdno("43022619870228252X");
		managerLog.setName("gfs");
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124279");
        accountInfo.setKeyType(KeyType.UNIFY);
        Response<String> actual = customerManagerService.unCheckIdlockCustomer(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
		
	}


	
	public void openCustomer() {
		TInfoCustomer customer = new TInfoCustomer();
		customer.setIsRealname("3");
		customer.setName("赛亚人2");  
		customer.setIsRequestCertificate("0");
		customer.setRegisterType("0");//注册类型
		customer.setMobileNo("123123123");
		customer.setUnifyId("123123123");
		customer.setGender("1");
		customer.setIdentifyType("6");
		customer.setIdentifyNo("310120198811187893");
		customer.setApanage("001340100000000");
		customer.setAreaCode("340000");
		customer.setCityCode("340400");
		customer.setContactAddress("地球");
		customer.setCustomerGrade("1");
		customer.setCustomerType("1");
		customer.setNationality("cn");
		customer.setProfession("打手");
		customer.setIdentifyExpiredDate("20140828");
		String cardNo = "8899000008075714";
		String cardType = "1";
		ManagerLog managerLog = new ManagerLog();
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("99999999");
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setAcceptTxnSeqNo("38461");
		managerLog.setTxnChannel("02");
		managerLog.setInputTime(DateTime.now().toDate());
		managerLog.setCheckTime(DateTime.now().toDate());
		managerLog.setTxnDscpt("非电信手机用户开户申请");
		managerLog.setAcceptDate("20140213");
		managerLog.setAcceptTime("101201");
		Response<OpenCustomerResultObject> actual = customerManagerService.openCustomer(customer, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult().getLoginPwd()+"|"+actual.getResult().getTxnPwd());
		
		
	}

    
    public void activationCustomer() {
    	AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("8630554000000069");
		accountInfo.setKeyType(KeyType.CUSTOMER);

        ManagerLog managerLog = new ManagerLog();
        //交易描述
        managerLog.setTxnDscpt("开户激活");
        //受理机构码
        managerLog.setAcceptOrgCode("001999999020000");
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

        Response<String> result = customerManagerService.activationCustomer(accountInfo, managerLog);
        logger.info("失败时，错误代码" + result.getErrorCode() + "错误原因：" + BussErrorCode.explain(result.getErrorCode()));      
        Assert.assertEquals(true, result.isSuccess());
        logger.info("成功" + result.getResult());
        logger.info(result.getResult());

    }
    
   
    
   
    
    public void changeSecrurityQuestionAndAnswer() {
    	AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("13524124279");
		accountInfo.setKeyType(KeyType.UNIFY);
		
        ManagerLog managerLog = new ManagerLog();
        //交易描述
        managerLog.setTxnDscpt("设置密保问题和答案");
        //受理机构码
        managerLog.setAcceptOrgCode("001999999020000");
        //交易渠道
        managerLog.setTxnChannel("02");
        //受理机构流水号
        managerLog.setAcceptTxnSeqNo("39461");
        //受理人
        managerLog.setInputUid("伟");
        //受理时间，格式yyyyMMddHHmmss
        managerLog.setInputTime(DateTime.now().toDate());
        //授权人
        managerLog.setCheckUid("伟");
        //授权时间，格式yyyyMMddHHmmss
        managerLog.setCheckTime(DateTime.now().toDate());
        managerLog.setInputTime(DateTime.now().toDate());
        SecurityQuestionInfo securityQuestionInfo = new SecurityQuestionInfo();
        securityQuestionInfo.setSecrurityQuestion("1");
        securityQuestionInfo.setSecrurityAnwser("伟哥ge");
        Response<String> actual = customerManagerService.changeSecrurityQuestionAndAnswer(accountInfo, managerLog,securityQuestionInfo);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}
    
    public void modfiyLoginPasswdWithoutOldPwd() {
    	AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("13524124277");
		accountInfo.setKeyType(KeyType.UNIFY);
		
        ManagerLog managerLog = new ManagerLog();
        //交易类型
        managerLog.setInnerTxnType("11111");
        //受理机构码
        managerLog.setAcceptOrgCode("001999999020000");
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

        //授权人
        //4806DF31535485B0F831C54E62C66B35
//        managerLog.setOldLoginPassword("4806DF31535485B0F831C54E62C66B35");
        //授权人
        managerLog.setNewLoginPassword("6");
        Response<String> actual = customerManagerService.modfiyLoginPasswdWithoutOldPwd(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}
    
    public void modfiyLoginPasswdWithOldPwd() {
    	AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountKey("13279144444");
		accountInfo.setKeyType(KeyType.UNIFY);
		
        ManagerLog managerLog = new ManagerLog();
        //受理机构码
        managerLog.setAcceptOrgCode("001999999020000");
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
        managerLog.setNewLoginPassword("4806DF31535485B0F831C54E62C66B35");
        //授权人
        managerLog.setOldLoginPassword("6");
        Response<String> actual = customerManagerService.modfiyLoginPasswdWithOldPwd(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}
    
    
    public void modifyCustomerInfo(){
    	TInfoCustomer tInfoCustomer=new TInfoCustomer();
    	tInfoCustomer.setContactAddress("火星");
    	tInfoCustomer.setUnifyId("13524124299");
    	
    	ManagerLog managerLog=new ManagerLog();
    	managerLog.setAcceptOrgCode("001999999020000");
    	Response<String> res=customerManagerService.modfiyCustomerInfo(tInfoCustomer, managerLog);
    	logger.info("失败时，错误代码" + res.getErrorCode() + "错误原因：" + BussErrorCode.explain(res.getErrorCode()));      
        Assert.assertEquals(true, res.isSuccess());
        logger.info("成功" + res.getResult());
    }
}
