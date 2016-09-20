package com.huateng.p3.account.manager;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.LoginInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.ProductPropertyTxnStatus;
import com.huateng.p3.account.common.bizparammodel.SecurityQuestionInfo;
import com.huateng.p3.account.common.enummodel.ProductProperty;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TPortSuggestions;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerManagerService {

    /**
     * 用户同步
     * @param tInfoCustomer
     * @param managerLog
     * @return
     */
    Response<String>synchronizeCustomerInfo(AccountInfo accountInfo ,TInfoCustomer tInfoCustomer, ManagerLog managerLog);


    
    
    /**
     *  开户
     * @param customer
     * @param managerLog
     * @return
     */
    Response<OpenCustomerResultObject>openCustomer(TInfoCustomer customer,ManagerLog managerLog);

    

    
    /**
     *  销户
     * @param customer
     * @param cardNo          绑卡专用          为空表示不开通脱机账户
     * @param cardType        绑卡专用
     * @param managerLog
     * @return
     */
    Response<String>closeCustomer(TInfoCustomer customer,String cardNo,String cardType,ManagerLog managerLog);
        


    /**
     * 客户锁定,不需要检测身份证	(门户)
     * @param accountInfo
     * @return
     */
	Response<String> unCheckIdlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog);
	
    /**
     * 客户锁定，需要检测身份证		(省内)
     * @param accountInfo
     * @return
     */
	Response<String> checkIdlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog);

    /**
     * 客户解锁,不需要检测身份证
     * @param accountInfo
     * @return
     */
	Response<String> unCheckIdUnlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog);
    /**
     * 客户解锁，需要检测身份证
     * @param accountInfo
     * @return
     */
	Response<String> checkIdUnlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog);

    /**
     * 开户激活接口
     * @param accountInfo
     * @param managerLog
     * @return 
     */
    Response<String> activationCustomer(AccountInfo accountInfo, ManagerLog managerLog);
    
    /**
     * 用户登录 验证登录密码 
     * @param accountInfo
     * @param loginInfo  登录密码  登录IP  渠道
     * @return
     */
    Response<String> loginCustomer(AccountInfo accountInfo ,LoginInfo loginInfo);
    
    /**
     * 重置用户登录密码 
     * @param accountInfo
     * @param managerLog  其中acceptOrgCode acceptTxnSeqNo txnChannel acceptDate acceptTime需填写
     * @return
     */
    Response<String> resetLoginPasswd(AccountInfo accountInfo ,ManagerLog managerLog);
    
    /**
     * 修改账户登录密码（无需原密码）
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> modfiyLoginPasswdWithoutOldPwd(AccountInfo accountInfo, ManagerLog managerLog);


    /**
     * 设置密保问题和答案
     * @param accountInfo
     * @param managerLog
     * @param securityQuestionInfo
     * @return
     */
	Response<String> changeSecrurityQuestionAndAnswer(AccountInfo accountInfo, ManagerLog managerLog ,SecurityQuestionInfo securityQuestionInfo);
	
	/**
     * 修改账户登录密码（需原密码）
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> modfiyLoginPasswdWithOldPwd(AccountInfo accountInfo, ManagerLog managerLog);

    /**
     * 账户挂失
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String> LostCustomerByUnifyId(TInfoCustomer customer, ManagerLog managerLog);
    
    /**
     * 账户解挂
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String> unLostCustomerByUnifyId(TInfoCustomer customer, ManagerLog managerLog);
   
    /**
     * 手机绑定
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String>  mobilePhoneBinding(TInfoCustomer customer, ManagerLog managerLog);
    
    /**
     * 手机解绑
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String>  unMobilePhoneBinding(TInfoCustomer customer, ManagerLog managerLog);

    /**
     * 修改个人信息
     * @param tInfoCustomer
     * @param managerLog
     * @return
     */
    Response<String> modfiyCustomerInfo(TInfoCustomer tInfoCustomer, ManagerLog managerLog);
}