package com.huateng.p3.account.manager.inner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.huateng.p3.account.persistence.models.*;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.LoginInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityQuestionInfo;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.CustomerGrade;
import com.huateng.p3.account.common.enummodel.CustomerIsClosingAccount;
import com.huateng.p3.account.common.enummodel.CustomerRealname;
import com.huateng.p3.account.common.enummodel.CustomerStatus;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.RegisterType;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.common.util.DateUtil;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.persistence.TDictCodeMapper;
import com.huateng.p3.account.persistence.TInfoAccountMapper;
import com.huateng.p3.account.persistence.TInfoCustomerMapper;


/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-12-10
 */
@Component
@Slf4j
public class CustomerManager {


    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private TxnCheckService txnCheckService;
    
    @Autowired
    private TInfoCustomerMapper tInfoCustomerMapper;
    
    @Autowired
    private TInfoAccountMapper tInfoAccountMapper;
    
    @Autowired
    private AccountManager accountManager;
    
    @Autowired
    private OrgService orgService;
    
    @Autowired
    private SmsComponent smsComponent;
    // 最大错误输入
    private Long maxErrNum = 5l;
    // 锁定时间
    private int lockPeriod = 24;
    
    @Autowired
    private TDictCodeMapper tDictCodeMapper;


    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String synchronizeCustomerInfo(AccountInfo accountInfo,TInfoCustomer newCustomer,ManagerLog managerLog) throws BizException {      
    	// 得到账户信息
    	TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());       
    	// 得到客户信息
    	TInfoCustomer customer = customerService.findValidCustomerByCustomerNoForUpdate(account.getCustomerNo());		
    	String txnType = TxnInnerType.TXN_TYPE_100090.getTxnInnerType();
    	// 检查商户交易合法性
    	orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype());  	
    	// 用户等级设置
    	newCustomer.setCustomerGrade(determineGrade(newCustomer.getIsRealname(),newCustomer.getCustomerGrade(),customer.getRegisterType()));
    	// 同步客户数据
    	customerService.updateWithSynchronize(newCustomer,customer.getCustomerNo());		
    	accountService.updateAccountRealnameInfo(newCustomer,customer.getCustomerNo());
		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
		
    }
    
    /*
     * 销户
     */
    public String doCloseCustomer(TInfoCustomer customer,
    String cardNo, String cardType, ManagerLog managerLog){

    	TInfoCustomer oldCustomer = customerService.findValidCustomerByCustomerNoForUpdate(customer.getCustomerNo());
    	String txnType = TxnInnerType.TXN_TYPE_101020.getTxnInnerType();
    	oldCustomer.setCustomerStatus(CustomerStatus. CUSTOMER_STATUS_CLOSED.getCustomerStatusCode());
    	oldCustomer.setLastUpdateTime(DateTime.now().toDate());
		
		// 检查商户交易合法性
				
	
//				while(iter.hasNext())//加密数据
//				{
//					TInfoAccount taccount = iter.next();			
//					accountManager.lockAccount(taccount, managerLog,acceptOrg,customer);
//				}
				
				
				
	
				// 检查商户交易合法性
				TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype());
				

				if (!oldCustomer.getIdentifyType().equals(managerLog.getIdtype())
						||!TxnCheckService.checkPid(managerLog.getIdtype(), managerLog.getIdno(), oldCustomer.getIdentifyNo())
						|| !oldCustomer.getName().equals(managerLog.getName())) {
					// 证件类型证件号码错误或客户姓名错误
					throw new BizException( BussErrorCode.ERROR_CODE_200038.getErrorcode(), BussErrorCode.ERROR_CODE_200038.getErrorcode());
				}

				List<TInfoAccount> accounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(oldCustomer.getCustomerNo(), null);
				// 注销客户下属所有账户
				for (Iterator<TInfoAccount> i = accounts.iterator(); i.hasNext();) {
					TInfoAccount account = i.next();
					if (account.getType().equals(AccountType.FUND.getValue())) {
						accountService.closeAccountByAccountNoToInnerInvoke(account,managerLog, true);
					} else {
						accountService.closeAccountByAccountNoToInnerInvoke(account,managerLog,false);
					}
				}

				// 注销
				Timestamp txnTime = DateUtil.getTime();
				oldCustomer.setIsClosingAccount(CustomerIsClosingAccount.LABEL_TRUE.getStatus());
				oldCustomer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_CLOSED.getCustomerStatusCode());
				oldCustomer.setLastUpdateTime(txnTime);
				oldCustomer.setUnifyId("X_" + oldCustomer.getUnifyId() + "_"
						+ DateUtil.formatDate(txnTime, "yyyyMMdd"));
				if (oldCustomer.getEmailAddress() != null) {
					oldCustomer.setEmailAddress("X_" + oldCustomer.getEmailAddress() + "_"
							+ DateUtil.formatDate(txnTime, "yyyyMMdd"));
				}
				if (oldCustomer.getMobileNo() != null) {
					oldCustomer.setMobileNo("X_" + oldCustomer.getMobileNo() + "_"
							+ DateUtil.formatDate(txnTime, "yyyyMMdd"));
				}
				oldCustomer.setCloseTime(txnTime);
				tInfoCustomerMapper.closeCustomer(oldCustomer);

				// 清除内存对象
				oldCustomer = null;
    	return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }


    
    
    public String doLockCustomer(AccountInfo accountInfo,ManagerLog managerLog,boolean ischeck) throws BizException {
    	TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	TInfoCustomer customer = customerService.findValidCustomerByCustomerNoForUpdate(account.getCustomerNo());
    	String txnType = TxnInnerType.TXN_TYPE_100070.getTxnInnerType();
		// 用户证件类型、证件号码为“其他、手机号”时，则不校验用户信息匹配情况
		// 门户不需要做判断
    	if(ischeck){
    		txnCheckService.checkCustomerInfo(customer,managerLog);
        }
    	
		// 更新客户资料状态
		customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_LOCKED.getCustomerStatusCode());
		customer.setLastUpdateTime(DateTime.now().toDate());
		tInfoCustomerMapper.closeCustomer(customer);
		// 检查商户交易合法性
		TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype());
		List<TInfoAccount> accounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(customer.getCustomerNo(), null);
		Iterator<TInfoAccount> iter = accounts.iterator();
//		while(iter.hasNext())//加密数据
//		{
//			TInfoAccount taccount = iter.next();			
//			accountManager.lockAccount(taccount, managerLog,acceptOrg,customer);
//		}
    	return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    public String doUnlockCustomer(AccountInfo accountInfo,ManagerLog managerLog,boolean ischeck) throws BizException {
    	
    	TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	TInfoCustomer customer = customerService.findValidCustomerByCustomerNoForUpdate(account.getCustomerNo());
    	String txnType = TxnInnerType.TXN_TYPE_100080.getTxnInnerType();
    	// 检查商户交易合法性
    	TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype());
		// 用户证件类型、证件号码为“其他、手机号”时，则不校验用户信息匹配情况
		// 门户不需要做判断
    	if(ischeck){
    		txnCheckService.checkCustomerInfo(customer, managerLog);
        }
		// 更新客户资料状态
		customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode());
		customer.setLastUpdateTime(DateTime.now().toDate());
		tInfoCustomerMapper.closeCustomer(customer);			
		List<TInfoAccount> accounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(customer.getCustomerNo(), null);
		Iterator<TInfoAccount> iter = accounts.iterator();
//		while(iter.hasNext())//数据加密
//		{
//			TInfoAccount taccount = iter.next();
//			
//			accountManager.unLockAccount(taccount, managerLog,acceptOrg,customer);
//		}
    	return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String doUnCheckIdlockCustomer(AccountInfo accountInfo,ManagerLog managerLog) throws BizException {   	
		return this.doLockCustomer(accountInfo, managerLog,false);
    }
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String doCheckIdlockCustomer(AccountInfo accountInfo,ManagerLog managerLog) throws BizException {   	
		return this.doLockCustomer(accountInfo, managerLog,true);
    }
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String doCheckIdUnlockCustomer(AccountInfo accountInfo,ManagerLog managerLog) throws BizException {   	
		return this.doUnlockCustomer(accountInfo, managerLog,false);
    }
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String doUnCheckIdUnlockCustomer(AccountInfo accountInfo,ManagerLog managerLog) throws BizException {   	
		return this.doUnlockCustomer(accountInfo, managerLog,true);
    }
    
    
    //同步时判断账户级别
    private String determineGrade(String isRealName,String beforeGrade,String registerType) {

        	
        String grade = null;	
        // 根据客户实名状态设置客户账户等级
        if (!Strings.isNullOrEmpty(isRealName)) {
        	CustomerRealname customerRealname = CustomerRealname.explain(isRealName);
            switch (customerRealname) {
                case CUSTOMER_REALNAME_TRUE:
                    grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_PRIMARY.getCustomerGradeCode();
                    break;//初级实名
                case CUSTOMER_REALNAME_FALSE:
                    grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_NOREALNAME.getCustomerGradeCode();
                    break;//非实名
                case CUSTOMER_REALNAME_HIGH:
                    grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_SENIOR.getCustomerGradeCode();
                    break;//高级实名
                default:
                    grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_NOREALNAME.getCustomerGradeCode();
            }
        	       	
        }       
        
        return grade;
    }
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String doActivationCustomer(AccountInfo accountInfo, ManagerLog managerLog) throws BizException {
    	TInfoAccount fundAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNoForUpdate(fundAccount.getCustomerNo());
   	  	 //TODO 获取客户信息 通过 unifyId
        // 得到客户信息
        TInfoCustomer customer = customerService.findValidActivationCustomerByUnifyId(tInfoCustomer.getUnifyId());
        // 激活账户
        String initPinKey = activationCustomer(customer);
        // 获取需要激活account列表
        List<TInfoAccount> accountList = accountService.findAccountsAsListByCustomerNo(customer.getCustomerNo(), null);       
        // 循环激活account   加密密钥
        for (int i = 0; i < accountList.size(); i++) {
            accountManager.activeAccount(accountList.get(i), tInfoCustomer, managerLog ,fundAccount , initPinKey);
        }
        return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    // 激活账户
    private String activationCustomer(TInfoCustomer customer){
    	 // 填充客户相关信息
        customer.setActiveStatus(CustomerStatus.CUSTOMER_ACTIVED.getCustomerStatusCode());
        String initPinKey = StringUtil.generateRandomString(6);
        String pinKey = Hashing.md5().hashString(initPinKey, Charsets.UTF_8).toString();
        customer.setWebLoginPassword(pinKey);
        customer.setIvrPassword(pinKey);
        customer.setAccountQueryPassword(pinKey);
        customer.setCustomerStatus(TrueOrFalse.TRUE.getLablCode());
        customer.setActiveTime(DateTime.now().toDate());
        tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
        return initPinKey;
    	
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class , noRollbackFor = SubmitBizException.class)
    public String checkLoginPwd(AccountInfo accountInfo, LoginInfo loginInfo) throws BizException,SubmitBizException {
    	TInfoAccount fundAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNoForUpdate(fundAccount.getCustomerNo());
    	//校验客户登入信息
    	checkCustomerLogin(tInfoCustomer,loginInfo);
        //密码通过，更新客户信息
    	updateCustomerLogin(tInfoCustomer,loginInfo);
        return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class , noRollbackFor = SubmitBizException.class)
    public String resetLoginPasswd(AccountInfo accountInfo, ManagerLog managerLog) throws BizException,SubmitBizException {
    	TInfoAccount tInfoAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	TInfoCustomer customer = customerService.findValidCustomerByCustomerNoForUpdate(tInfoAccount.getCustomerNo());
    	managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101111.getTxnInnerType());
    	 customer.setWebLoginPassword(managerLog.getNewLoginPassword());

    	//受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),
        		managerLog.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        
        //重置登录密码
        String initPinKey =resetPwd(customer);
        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog,tInfoAccount,acceptOrg);
    	//短信通知结算
        smsComponent.acountManagerNotice(customer, tInfoAccount,tLogAccountManagement,acceptOrg ,null,null,initPinKey,managerLog.getBussinessType());
    
        return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    public String doModfiyLoginPasswd(AccountInfo accountInfo, ManagerLog managerLog, boolean... ispwdcheck) throws BizException,SubmitBizException {
    	// 检查新，旧 密码是否一致，重置没有原密码，肯定通过这个判断
    	if(managerLog.getNewLoginPassword().equals(managerLog.getOldLoginPassword())){
			throw new BizException(BussErrorCode.ERROR_CODE_200042.getErrorcode(),
					BussErrorCode.ERROR_CODE_200042.getErrordesc()); 
        }
    	TInfoAccount tInfoAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	TInfoCustomer customer = customerService.findValidCustomerByCustomerNoForUpdate(tInfoAccount.getCustomerNo());
    	managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101112.getTxnInnerType());
    	// 受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),
        		managerLog.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        // 检查原密码
        if (ispwdcheck.length > 0 && ispwdcheck[0]) {
        	if(!customer.getWebLoginPassword().equals(managerLog.getOldLoginPassword())){
        		 throw new SubmitBizException(BussErrorCode.ERROR_CODE_200132.getErrorcode(),
                         BussErrorCode.ERROR_CODE_200132.getErrordesc());
        	}
        }
        // 重置登录密码
        updatePwd(customer,managerLog);
        accountService.accountManInDb(managerLog,tInfoAccount,acceptOrg);    
        return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    
    public String doModfiyCustomerInfo(TInfoCustomer tInfoCustomer, ManagerLog managerLog) throws BizException,SubmitBizException {	
    	String txnType = TxnInnerType.TXN_TYPE_100090.getTxnInnerType();
    	
    	TInfoCustomer customer=customerService.findValidCustomerByUnifyId(tInfoCustomer.getUnifyId());
    	// 检查商户交易合法性
    	orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype());
    	tInfoCustomer.setCustomerNo(customer.getCustomerNo());
    	// 更新客户数据
    	customerService.updateByPrimaryKeySelective(tInfoCustomer);		
		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
  
    
    
    /**
     * 检验客户登入信息
     * @param customer
     * @param loginInfo
     */
    private void checkCustomerLogin(TInfoCustomer customer,LoginInfo loginInfo){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    	//检查黑名单身份证
    	customerService.checkCustomerBlack(customer);
    	//当前时间
    	loginInfo.setLoginTime(DateTime.now().toDate());
        // 错误输入次数
        Long pwdErrNum = customer.getPwdErrCount() == null ? (long) 0
                : customer.getPwdErrCount();
    	Date lockTimeLimit = customer.getLockTimeLimit();
    	
    	// 账户状态异常
    	if (!CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode().equals(customer.getCustomerStatus())) {
    		customer.setLastFailLoginIp(loginInfo.getLoginIp());
            customer.setLastFailLoginTime(loginInfo.getLoginTime());
            tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
    		
    		if(CustomerStatus.CUSTOMER_STATUS_LOCKED.getCustomerStatusCode().equals(customer.getCustomerStatus())){
    			if(Integer.parseInt(pwdErrNum+"")>=5){//已锁定 判断是否因为密码输入错误次数达到上限
    				//密码锁定
        	    	if(customer.getLockTimeLimit()!=null && loginInfo.getLoginTime().before(lockTimeLimit)){
        	    		customer.setLastFailLoginIp(loginInfo.getLoginIp());
        	            customer.setLastFailLoginTime(loginInfo.getLoginTime());
        	            tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
        	    		throw new SubmitBizException(BussErrorCode.ERROR_CODE_700012.getErrorcode(),
        	                    BussErrorCode.ERROR_CODE_700012.getErrordesc(),String.valueOf(format.format(customer.getLockTimeLimit())));
        	    		
        	    	}
    	    	}else{
    	    		throw new SubmitBizException(BussErrorCode.ERROR_CODE_700014.getErrorcode(), BussErrorCode.ERROR_CODE_700014.getErrordesc());
    	    	}
    			
    	    	
    		}else{
    			throw new SubmitBizException(BussErrorCode.ERROR_CODE_700014.getErrorcode(), BussErrorCode.ERROR_CODE_700014.getErrordesc());
    		}
            
        }

    	//密码不正确
    	if (!customer.getWebLoginPassword().equals(loginInfo.getLoginPassword())) {
    		 customer.setPwdErrCount(++pwdErrNum);
    		 customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode());
             // 最大错误输入
             if (pwdErrNum == maxErrNum) {
                 DateTime lockTime = new DateTime().plusHours(lockPeriod);
                 customer.setLockTimeLimit(lockTime.toDate());
                 customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_LOCKED.getCustomerStatusCode());
             }
             customer.setLastFailLoginIp(loginInfo.getLoginIp());
             customer.setLastFailLoginTime(loginInfo.getLoginTime());
             tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
             if(pwdErrNum>=2&&pwdErrNum<5){
            	 throw new SubmitBizException(BussErrorCode.ERROR_CODE_700023.getErrorcode(),
                         BussErrorCode.ERROR_CODE_700023.getErrordesc(),String.valueOf(maxErrNum - pwdErrNum));
             }else if(pwdErrNum>=6){
            	 customer.setPwdErrCount(Long.parseLong("1"));
            	 customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode());
            	 tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
            	 throw new SubmitBizException(BussErrorCode.ERROR_CODE_700001.getErrorcode(),
                         BussErrorCode.ERROR_CODE_700001.getErrordesc());
             }
             else{
            	 if(pwdErrNum==5){
            		 customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_LOCKED.getCustomerStatusCode());
                   	 tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
            		 throw new SubmitBizException(BussErrorCode.ERROR_CODE_700012.getErrorcode(),
                             BussErrorCode.ERROR_CODE_700012.getErrordesc(),String.valueOf(format.format(customer.getLockTimeLimit()))); 
            	 
            	 }
             throw new SubmitBizException(BussErrorCode.ERROR_CODE_700001.getErrorcode(),
                     BussErrorCode.ERROR_CODE_700001.getErrordesc());
            	 
             }
    	}else{
    		if(pwdErrNum>=5){
    		customer.setPwdErrCount(Long.parseLong("0"));
    		customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode());
       	 tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
    		}
    	}
    	
    }
    
    /**
     * 密码通过，更新客户信息
     * @param customer
     * @param loginInfo
     */
    private void updateCustomerLogin(TInfoCustomer customer,LoginInfo loginInfo){
    	 //清除登录失败记录
		 customer.setPwdErrCount(0l);
	     customer.setLockTimeLimit(null);
	     customer.setLastSuccessLoginIp(loginInfo.getLoginIp());
	     customer.setLastSuccessLoginTime(loginInfo.getLoginTime());
	     tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
    	
    }
    
    /**
     * 设置密保问题和答案
     * 
     * @param accountInfo
     * @param managerLog
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String changeSecrurityQuestionAndAnswer(AccountInfo accountInfo, ManagerLog managerLog ,SecurityQuestionInfo securityQuestionInfo){   	
    	if (Strings.isNullOrEmpty( securityQuestionInfo.getSecrurityQuestion()) || Strings.isNullOrEmpty( securityQuestionInfo.getSecrurityAnwser())
    			|| tDictCodeMapper.checkQuestion(securityQuestionInfo.getSecrurityQuestion().trim()) == 0
				|| securityQuestionInfo.getSecrurityAnwser().length() > 100) {
			//非法密保问题或答案
    		throw new BizException(BussErrorCode.ERROR_CODE_500206.getErrorcode(),
					BussErrorCode.ERROR_CODE_500206.getErrordesc());
		}
    	TInfoCustomer tInfoCustomer = customerService.getCustomerForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
       	tInfoCustomer.setQuestion(securityQuestionInfo.getSecrurityQuestion());
    	tInfoCustomer.setAnswer(securityQuestionInfo.getSecrurityAnwser());
    	tInfoCustomer.setLastUpdateTime(new DateTime().toDate());
    	tInfoCustomerMapper.updateByPrimaryKeySelective(tInfoCustomer);
		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    
    private String resetPwd(TInfoCustomer customer){
        String pinKey = Hashing.md5().hashString(customer.getWebLoginPassword(), Charsets.UTF_8).toString();
        customer.setWebLoginPassword(pinKey);
        customer.setIvrPassword(pinKey);
        customer.setAccountQueryPassword(pinKey);     
        customer.setLastUpdateTime(new DateTime().toDate());
        tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
        return customer.getWebLoginPassword();
    }

    
    private void updatePwd(TInfoCustomer customer ,ManagerLog managerLog){
  	    customer.setWebLoginPassword(managerLog.getNewLoginPassword());
        customer.setIvrPassword(managerLog.getNewLoginPassword());
        customer.setAccountQueryPassword(managerLog.getNewLoginPassword());     
        customer.setLastUpdateTime(new DateTime().toDate());
        tInfoCustomerMapper.updateByPrimaryKeySelective(customer);
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public String modfiyLoginPasswdWithoutOldPwd(AccountInfo accountInfo, ManagerLog managerLog) throws BizException,SubmitBizException {
    	return doModfiyLoginPasswd(accountInfo, managerLog);
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public String modfiyLoginPasswdWithOldPwd(AccountInfo accountInfo, ManagerLog managerLog) throws BizException,SubmitBizException {
    	return doModfiyLoginPasswd(accountInfo, managerLog, true);
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public String modfiyCustomerInfo(TInfoCustomer tInfoCustomer, ManagerLog managerLog) throws BizException,SubmitBizException {
    	return doModfiyCustomerInfo(tInfoCustomer, managerLog);
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public String LostCustomerByUnifyId(TInfoCustomer customers,ManagerLog managerLog) {
		// TODO Auto-generated method stub
		TInfoCustomer oldCustomer = (TInfoCustomer) tInfoCustomerMapper
				.findCustomerByUnifyIdForUpdate(customers.getUnifyId());
		String txnType = TxnInnerType.TXN_TYPE_101030.getTxnInnerType();
		txnCheckService.checkCustomerActivationTxn(oldCustomer);// 检查交易合法性
		if (!oldCustomer.getIdentifyType().equals(managerLog.getIdtype())
				||!TxnCheckService.checkPid(managerLog.getIdtype(), managerLog.getIdno(), oldCustomer.getIdentifyNo())
				|| !oldCustomer.getName().equals(managerLog.getName())) {
			// 证件类型证件号码错误或客户姓名错误
			throw new BizException(BussErrorCode.ERROR_CODE_200038.getErrorcode(), BussErrorCode.ERROR_CODE_200038.getErrorcode());
		}

		// 更新客户资料状态
		oldCustomer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_LOSTED.getCustomerStatusCode());
		oldCustomer.setLastUpdateTime(DateUtil.getTime());
		tInfoCustomerMapper.updateByPrimaryKey(oldCustomer);

		List<TInfoAccount> accounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(oldCustomer
				.getCustomerNo(),null);

		// 挂失客户下属所有账户
		for (Iterator<TInfoAccount> i = accounts.iterator(); i.hasNext();) {
			TInfoAccount account = i.next();
//			if (Constant.ACCOUNT_TYPE_BOND.equals(account.getType())) {
//				// 不挂失代金券账户
//				continue;
//			}
			accountService.lostAccountByAccountNo(account,managerLog);
		}

		// 清除内存对象
		oldCustomer = null;
		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
	}
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public String unLostCustomerByUnifyId(TInfoCustomer customers,ManagerLog managerLog) {
		// TODO Auto-generated method stub
    	TInfoCustomer oldCustomer = (TInfoCustomer) tInfoCustomerMapper
				.findCustomerByUnifyIdForUpdate(customers.getUnifyId());
		String txnType = TxnInnerType.TXN_TYPE_101040.getTxnInnerType();
		txnCheckService.checkCustomerActivationTxn(oldCustomer);// 检查交易合法性
		// 检查交易渠道和交易类型合法性
				txnCheckService.checkTxnType(txnType,TxnType.TXN_MGM.getTxnCode());
		if (!oldCustomer.getIdentifyType().equals(managerLog.getIdtype())
				||!TxnCheckService.checkPid(managerLog.getIdtype(), managerLog.getIdno(), oldCustomer.getIdentifyNo())
				|| !oldCustomer.getName().equals(managerLog.getName())) {
			// 证件类型证件号码错误或客户姓名错误
			throw new BizException(BussErrorCode.ERROR_CODE_200038.getErrorcode(), BussErrorCode.ERROR_CODE_200038.getErrorcode());
		}

		// 更新客户资料状态
				oldCustomer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode());
				oldCustomer.setLastUpdateTime(DateUtil.getTime());
				tInfoCustomerMapper.updateByPrimaryKey(oldCustomer);

				List<TInfoAccount> accounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(oldCustomer
						.getCustomerNo(),null);

		// 挂失客户下属所有账户
				for (Iterator<TInfoAccount> i = accounts.iterator(); i.hasNext();) {
					TInfoAccount account = i.next();
//					if (Constant.ACCOUNT_TYPE_BOND.equals(account.getType())) {
//						// 不挂失代金券账户
//						continue;
//					}
					accountService.unLostAccountByAccountNo(account,managerLog);
				}

		// 清除内存对象
				oldCustomer = null;
		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
	}
    
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
	propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
public String mobilePhoneBinding(TInfoCustomer customers,ManagerLog managerLog) {
    	TInfoCustomer oldCustomer = (TInfoCustomer) tInfoCustomerMapper
				.findCustomerByUnifyIdForUpdate(customers.getUnifyId());
    	String txnType = TxnInnerType.TXN_TYPE_10C010.getTxnInnerType();
    	txnCheckService.checkCustomerActivationTxn(oldCustomer);// 检查交易合法性
    	// 检查交易渠道和交易类型合法性
    			txnCheckService.checkTxnType(txnType,TxnType.TXN_MGM.getTxnCode());
    	//验证手机
    	if(!StringUtil.isNotEmpty(customers.getMobileNo())){
    		throw new BizException(BussErrorCode.ERROR_CODE_240002.getErrorcode(), BussErrorCode.ERROR_CODE_200038.getErrorcode());
    	}
    	
    	//验证手机号是否重复
    	if(customers.getMobileNo().equals(oldCustomer.getMobileNo())){
    		throw new BizException(BussErrorCode.ERROR_CODE_240001.getErrorcode(), BussErrorCode.ERROR_CODE_200038.getErrorcode());
    	}
    	
    	//验证开通方式是否为手机号
    	//if(RegisterType.CUSTOMER_REGISTER_TYPE_PHONE.getValue().equals(oldCustomer.getRegisterType())){
    		//throw new BizException(BussErrorCode.ERROR_CODE_240003.getErrorcode(), BussErrorCode.ERROR_CODE_240003.getErrorcode());
    //	}
    	
    	// 更新客户资料状态
    	oldCustomer.setMobileNo(customers.getMobileNo());
		oldCustomer.setLastUpdateTime(DateUtil.getTime());
		tInfoCustomerMapper.updateByPrimaryKey(oldCustomer);
		//清除内存对象
		oldCustomer=null;
    	return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }
    
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    	public String unMobilePhoneBinding(TInfoCustomer customers,ManagerLog managerLog) {
    	    	TInfoCustomer oldCustomer = (TInfoCustomer) tInfoCustomerMapper
    					.findCustomerByUnifyIdForUpdate(customers.getUnifyId());
    	    	String txnType = TxnInnerType.TXN_TYPE_10C010.getTxnInnerType();
    	    	txnCheckService.checkCustomerActivationTxn(oldCustomer);// 检查交易合法性
    	    	// 检查交易渠道和交易类型合法性
    	    			txnCheckService.checkTxnType(txnType,TxnType.TXN_MGM.getTxnCode());
    	    	
    	    	// 更新客户资料状态
    	    	oldCustomer.setMobileNo(customers.getMobileNo());
    			oldCustomer.setLastUpdateTime(DateUtil.getTime());
    			tInfoCustomerMapper.updateByPrimaryKey(oldCustomer);
    			//清除内存对象
    			oldCustomer=null;
    	    	return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    	    }

   
}
