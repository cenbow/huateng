package com.huateng.p3.hub.accountcore.service;

import com.huateng.p3.account.common.bizparammodel.*;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;

/**
 * User: dongpeiji
 * Date: 14-9-12
 * Time: 上午10:55
 */
public interface HubCustomerManagerService {
    /**
     * 用户同步
     * @param tInfoCustomer
     * @param managerLog
     * @return
     */
    Response<String> synchronizeCustomerInfo(AccountInfo accountInfo ,TInfoCustomer tInfoCustomer, ManagerLog managerLog)throws Exception;




    /**
     *  开户
     * @param customer
     * @param managerLog
     * @return
     */
    Response<OpenCustomerResultObject>openCustomer(TInfoCustomer customer,ManagerLog managerLog)throws Exception;




    /**
     *  销户
     * @param customer
     * @param cardNo          绑卡专用          为空表示不开通脱机账户
     * @param cardType        绑卡专用
     * @param managerLog
     * @return
     */
    Response<String>closeCustomer(TInfoCustomer customer,String cardNo,String cardType,ManagerLog managerLog)throws Exception;



    /**
     * 客户锁定,不需要检测身份证	(门户)
     * @param accountInfo
     * @return
     */
    Response<String> unCheckIdlockCustomer(AccountInfo accountInfo,
                                           ManagerLog managerLog)throws Exception;

    /**
     * 客户锁定，需要检测身份证		(省内)
     * @param accountInfo
     * @return
     */
    Response<String> checkIdlockCustomer(AccountInfo accountInfo,
                                         ManagerLog managerLog)throws Exception;

    /**
     * 客户解锁,不需要检测身份证
     * @param accountInfo
     * @return
     */
    Response<String> unCheckIdUnlockCustomer(AccountInfo accountInfo,
                                             ManagerLog managerLog)throws Exception;
    /**
     * 客户解锁，需要检测身份证
     * @param accountInfo
     * @return
     */
    Response<String> checkIdUnlockCustomer(AccountInfo accountInfo,
                                           ManagerLog managerLog)throws Exception;

    /**
     * 开户激活接口
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> activationCustomer(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;

    /**
     * 用户登录 验证登录密码
     * @param accountInfo
     * @param loginInfo  登录密码  登录IP  渠道
     * @return
     */
    Response<String> loginCustomer(AccountInfo accountInfo ,LoginInfo loginInfo)throws Exception;

    /**
     * 重置用户登录密码
     * @param accountInfo
     * @param managerLog  其中acceptOrgCode acceptTxnSeqNo txnChannel acceptDate acceptTime需填写
     * @return
     */
    Response<String> resetLoginPasswd(AccountInfo accountInfo ,ManagerLog managerLog)throws Exception;

    /**
     * 修改账户登录密码（无需原密码）
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> modfiyLoginPasswdWithoutOldPwd(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;


    /**
     *设置密保问题和答案
     *
     * @param productNo
     * @param txnDscpt
     * @param acceptChannel
     * @param acceptSeqNo
     * @param acceptTime
     * @param cecrurityQuestion
     * @param secrurityAnwser
     * @return
     */
    Response<String> changeSecrurityQuestionAndAnswer(AccountInfo accountInfo, ManagerLog managerLog ,SecurityQuestionInfo securityQuestionInfo)throws Exception;

    /**
     * 修改账户登录密码（需原密码）
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> modfiyLoginPasswdWithOldPwd(AccountInfo accountInfo, ManagerLog managerLog)throws Exception;

    /**
     * 账户挂失
     * @param accountInfo
     * @param managerLog
     * @return
     */
    Response<String> LostCustomerByUnifyId(TInfoCustomer customer, ManagerLog managerLog)throws Exception;

    /**
     * 账户解挂
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String> unLostCustomerByUnifyId(TInfoCustomer customer, ManagerLog managerLog)throws Exception;

    /**
     * 手机绑定
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String>  mobilePhoneBinding(TInfoCustomer customer, ManagerLog managerLog)throws Exception;

    /**
     * 手机解绑
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String>  unMobilePhoneBinding(TInfoCustomer customer, ManagerLog managerLog)throws Exception;


    /**
     * 修改个人信息
     * @param customer
     * @param managerLog
     * @return
     */
    Response<String> modifyCustomerInfo(TInfoCustomer customer, ManagerLog managerLog)throws Exception;
}
