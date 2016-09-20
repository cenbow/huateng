package cn.com.huateng.account.service.impl;

import cn.com.huateng.util.DateUtil;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.hub.accountcore.service.HubAccountManagerService;
import com.huateng.p3.hub.accountcore.service.HubBankCardManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoAccount;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.account.util.ManagerLogCom;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.util.ValidationFieldUtil;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.LoginInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityQuestionInfo;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubCustomerManagerService;
import com.huateng.p3.hub.accountcore.service.HubCustomerQueryService;

import java.util.List;


/**
 * 登录、个人信息查询
 *
 * @author dpj
 * @date 2014-04-09
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private HubCustomerManagerService hubCustomerManagerService;

    @Autowired
    private HubCustomerQueryService hubCustomerQueryService;
    
    @Autowired
    ManagerLogCom managerLogCom;

    @Autowired
    private HubAccountManagerService hubAccountManagerService;

    @Autowired
    private HubBankCardManagerService hubBankCardManagerService;



//    @Autowired
//    SuggestionsManager userServiceManager;
//
//
//

                /**
     * 用户登录
     *
     * @param unifyId  统一编号  必填
     * @param password 用户密码  必填
     * @param loginIp  登录IP地址  必填
     * @return 如果登录成功, 则返回该用户对象, 否则返回失败原因
     */
    @Override
    public Response<TInfoCustomer> login(String unifyId, String password, String loginIp) {
        TInfoCustomer customer;
        Response<TInfoCustomer> response = new Response<TInfoCustomer>();
        Response<String> responseStr = new Response<String>();
        Response<CustomerResultObject> responseCus = new Response<CustomerResultObject>();
        if (Strings.isNullOrEmpty(unifyId)
                || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(loginIp)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_800001
                    .getErrordesc());
            return response;
        }
        try {
            //调核心交换条件unifyId查找客户信息
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountKey(unifyId);
            accountInfo.setKeyType(KeyType.UNIFY);
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setLoginIp(loginIp);
            String loginPwd = Hashing.md5().hashString(password, Charsets.UTF_8).toString();
            loginInfo.setLoginPassword(loginPwd);

            responseStr = hubCustomerManagerService.loginCustomer(accountInfo, loginInfo);
            if (responseStr.isSuccess()) {
                logger.info("login success accountInfo:{} loginInfo:{}", accountInfo, loginInfo);
                responseCus = hubCustomerQueryService.queryCustomerInfo(accountInfo);
                if (responseCus.isSuccess()) {
                    customer = new TInfoCustomer();
                    customer.setCustomerNo(responseCus.getResult().getCustomerNo());
                    customer.setIdentifyNo(responseCus.getResult().getIdNo());
                    customer.setCityCode(responseCus.getResult().getCityCode());
                    customer.setLastSuccessLoginTime(responseCus.getResult().getLastSuccessLoginTime());

                    response.setResult(customer);
                    return response;
                } else {
                    logger.error("fail to queryCustomerInfo accountInfo:{} cause by {}", new Object[]{accountInfo, responseCus.getErrorCode() + ":" + responseCus.getErrorMsg()});
                    response.setErrorCode(responseCus.getErrorCode());
                    response.setErrorMsg(responseStr.getErrorMsg());
                    return response;
                }

            } else {
                logger.error("fail to login accountInfo:{} loginInfo:{} cause by {}", new Object[]{accountInfo, loginInfo, responseStr.getErrorCode() + ":" + responseStr.getErrorMsg()});
                response.setErrorCode(responseStr.getErrorCode());
                response.setErrorMsg(responseStr.getErrorMsg());
                return response;
            }

        } catch (Exception e) {
            logger.error("fail to login cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return response;
        }
    }
    

    

    /**
     * 根据客户号查询用户
     *
     * @param primaryKey 主键,这里是CUSTOMERID
     * @return 如果查询成功, 则返回该用户对象, 否则返回失败原因
     */
    @Override
    public Response<TInfoCustomer> findUserByPK(String primaryKey) {
        Response<TInfoCustomer> response = new Response<TInfoCustomer>();
        Response<CustomerResultObject> responseC = new Response<CustomerResultObject>();
        AccountInfo accountInfo=new AccountInfo();
        TInfoCustomer tInfoCustomer=new TInfoCustomer();
        //必填判断
        if (Strings.isNullOrEmpty(primaryKey)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }
        accountInfo.setAccountKey(primaryKey);
        accountInfo.setKeyType(KeyType.CUSTOMER);
        try {
            responseC=hubCustomerQueryService.queryCustomerInfo(accountInfo);
            if(responseC.isSuccess()){
                logger.info("findUserByPK success accountInfo:{}",accountInfo);

                tInfoCustomer.setCustomerNo(responseC.getResult().getCustomerNo());
                tInfoCustomer.setUnifyId(responseC.getResult().getUnifyId());
                tInfoCustomer.setName(responseC.getResult().getName());
                tInfoCustomer.setIdentifyType(responseC.getResult().getIdType());
                tInfoCustomer.setIdentifyNo(responseC.getResult().getIdNo());
                tInfoCustomer.setQuestion(responseC.getResult().getQuestion());
                tInfoCustomer.setAnswer(responseC.getResult().getAnswer());
                tInfoCustomer.setIsRealname(responseC.getResult().getIsRealname());
                tInfoCustomer.setCustomerGrade(responseC.getResult().getGrade());
                tInfoCustomer.setRegisterType(responseC.getResult().getRegisterType());
                tInfoCustomer.setRegisteDate(responseC.getResult().getRegisterTime());
                tInfoCustomer.setAreaCode(responseC.getResult().getAreaCode());
                tInfoCustomer.setCityCode(responseC.getResult().getCityCode());
                tInfoCustomer.setMobileNo(responseC.getResult().getMobilePhone());
                tInfoCustomer.setLastSuccessLoginTime(responseC.getResult().getLastSuccessLoginTime());


                response.setResult(tInfoCustomer);
                return response;
            }       else{
                logger.error("fail to findUserByPK accountInfo:{} cause by {}",new Object[]{accountInfo,responseC.getErrorCode()+":"+responseC.getErrorMsg()});
                response.setErrorCode(responseC.getErrorCode()+":"+responseC.getErrorMsg());
                return response;
            }

        } catch (Exception e) {
            logger.error("fail to findUserByPK cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        return response;

    }



    /**
     * 根据unifyId查询用户
     *
     * @return 如果查询成功, 则返回该用户对象, 否则返回失败原因
     */
    @Override
    public Response<TInfoCustomer> findUser(CommonUser commonUser) {
        Response<TInfoCustomer> response = new Response<TInfoCustomer>();
        Response<CustomerResultObject> responseC = new Response<CustomerResultObject>();
        AccountInfo accountInfo=new AccountInfo();
        TInfoCustomer tInfoCustomer=new TInfoCustomer();
        //必填判断
        if (Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        try {
            responseC=hubCustomerQueryService.queryCustomerInfo(accountInfo);
            if(responseC.isSuccess()){
                logger.info("findUser success accountInfo:{}",accountInfo);

                tInfoCustomer.setCustomerNo(responseC.getResult().getCustomerNo());
                tInfoCustomer.setUnifyId(responseC.getResult().getUnifyId());
                tInfoCustomer.setName(responseC.getResult().getName());
                tInfoCustomer.setIdentifyType(responseC.getResult().getIdType());
                tInfoCustomer.setIdentifyNo(responseC.getResult().getIdNo());
                tInfoCustomer.setQuestion(responseC.getResult().getQuestion());
                tInfoCustomer.setAnswer(responseC.getResult().getAnswer());
                tInfoCustomer.setIsRealname(responseC.getResult().getIsRealname());
                tInfoCustomer.setCustomerGrade(responseC.getResult().getGrade());
                tInfoCustomer.setRegisterType(responseC.getResult().getRegisterType());
                tInfoCustomer.setRegisteDate(responseC.getResult().getRegisterTime());
                tInfoCustomer.setAreaCode(responseC.getResult().getAreaCode());
                tInfoCustomer.setCityCode(responseC.getResult().getCityCode());
                tInfoCustomer.setMobileNo(responseC.getResult().getMobilePhone());
                tInfoCustomer.setContactAddress(responseC.getResult().getContactAddress());
                tInfoCustomer.setLastUpdateTime(responseC.getResult().getLastSuccessLoginTime());
                tInfoCustomer.setHomeTelephone(responseC.getResult().getHomeTelephone());
                tInfoCustomer.setOfficeTelephone(responseC.getResult().getOfficeTelephone());
                tInfoCustomer.setOtherTelephone(responseC.getResult().getOtherTelephone());
                tInfoCustomer.setPwdErrCount(responseC.getResult().getPwdErrCount());
                tInfoCustomer.setGender(responseC.getResult().getGender());
                if(Strings.isNullOrEmpty(responseC.getResult().getMobilePhone())){
                    tInfoCustomer.setMobileNo("");
                }

                response.setResult(tInfoCustomer);
                return response;
            }       else{
                logger.error("fail to findUser accountInfo:{} cause by {}",new Object[]{accountInfo,responseC.getErrorCode()+":"+responseC.getErrorMsg()});
                response.setErrorCode(responseC.getErrorCode()+":"+responseC.getErrorMsg());
                return response;
            }

        } catch (Exception e) {
            logger.error("fail to findUser cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        return response;

    }


    /**
     * 根据公共用户对象查询账户
     *
     * @param commonUser 公共用户对象
     * @return 如果查询成功, 则返回该用户的账户对象, 否则返回失败原因
     */
   /* @Override
    public Response<TInfoAccount> loadAccounts(CommonUser commonUser) {
        if (commonUser == null || commonUser.getId() == null) {
            return new Response<TInfoAccount>(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
        }
        TInfoAccount tInfoAccount = new TInfoAccount();
        // prepare params
        Map<String, Object> params = Maps.newHashMap();
        params.put("UNIFYID", commonUser.getUnifyId());
        params.put("ACCOUNTTYPE", "1"); // 1 资金账户
        params.put("ACCEPTORGCODE", "1" + "00" + areaCode + "000000"); //1-商户 00 统一支付平台 地区代码
        params.put("ACCEPTSEQNO", areaCode.substring(0, 3) + DateUtil.getDateYYYYMMddHHMMSSSSS());
        params.put("INPUTTIME", DateUtil.getDateYYYYMMddHHMMSS());
        String paramsStr = StringUtil.generateParams(params);
        AccountResponse result;
        try {
            result = accountHandle.call("账户查询", "100100", WebServiceFactory.ConfigKey.PROVINCEFRONT, paramsStr, AccountResponse.class);
            logger.info("queryAccount result:" + result);
        } catch (Exception e) {
            logger.error("query account error {}", e);
            return new Response<TInfoAccount>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        if (!result.isSuccess()) {
            return new Response<TInfoAccount>(result.getResponseCode());
        }

        return null;
            }
        }
        TInfoCustomer customer = tInfoCustomerMapper
                .findTInfoCustomerByUnifyId(commonUser.getUnifyId());
        tInfoAccount.setMobilePhone(customer.getMobileNo());
        tInfoAccount.setUnifyId(commonUser.getUnifyId());
        tInfoAccount.setEmailAddress(customer.getEmailAddress());
        return new Response<TInfoAccount>(tInfoAccount);
        return null;
    }     */


    /**
     * 根据公共用户对象查询用户
     *
     * @param commonUser 公共用户对象
     * @return 如果查询成功, 则返回该用户对象, 否则返回失败原因
     */
    public Response<TInfoCustomer> loadCustomer(CommonUser commonUser) {

        if (commonUser == null || commonUser.getId() == null) {

            return new Response<TInfoCustomer>(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
        }
        return findUserByPK(commonUser.getId().toString());
    }


    /**
     * 根据公共用户对象查询账户
     *
     * @param commonUser 公共用户对象
     * @return 如果查询成功, 则返回该用户的账户对象, 否则返回失败原因
     */
    @Override
    public Response<TInfoAccount> loadAccounts(CommonUser commonUser) {
        Response<TInfoAccount> response = new Response<TInfoAccount>();
        Response<CustomerResultObject> responseC = new Response<CustomerResultObject>();
        AccountInfo accountInfo = new AccountInfo();
        TInfoAccount tInfoAccount = new TInfoAccount();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        try {
            responseC = hubCustomerQueryService.queryCustomerInfo(accountInfo);
            if (responseC.isSuccess()) {
                logger.info("loadAccounts success accountInfo:{}", accountInfo);
                tInfoAccount.setBalance(responseC.getResult().getBalance());//账户余额
                tInfoAccount.setAvailableBalance(responseC.getResult().getAvailableBalance());//可用账户余额
                tInfoAccount.setStatus(responseC.getResult().getStatus());
                tInfoAccount.setAccountNo(responseC.getResult().getAccountNo());
                // tInfoAccount.setFundAccountNo();
                tInfoAccount.setPinkey(responseC.getResult().getInitPasswdModified());
                tInfoAccount.setName(responseC.getResult().getName());
                tInfoAccount.setUnifyId(responseC.getResult().getUnifyId());
                tInfoAccount.setMobilePhone(responseC.getResult().getMobilePhone());
                tInfoAccount.setRealname(responseC.getResult().getIsRealname());
                tInfoAccount.setIntegralAvailableBalance(responseC.getResult().getIntegral());
                tInfoAccount.setFrozenAmount(responseC.getResult().getFrozenAmount());
                response.setResult(tInfoAccount);
                return response;
            } else {
                logger.error("fail to loadAccounts accountInfo:{} cause by {}", new Object[]{accountInfo, responseC.getErrorCode() + ":" + responseC.getErrorMsg()});
                response.setErrorCode(responseC.getErrorCode() + ":" + responseC.getErrorMsg());
                return response;
            }

        } catch (Exception e) {
            logger.error("fail to loadAccounts cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        return response;
    }

    /**
     * 根据产品号查询用户
     *
     * @param unifyId 手机号码
     * @return 如果查询成功, 则返回该用户对象, 否则返回失败原因
     */
    public Response<TInfoCustomer> findUserByUnifyId(String unifyId) {
        TInfoCustomer customer;
        Response<TInfoCustomer> response = new Response<TInfoCustomer>();
        Response<CustomerResultObject> responseC = new Response<CustomerResultObject>();
        AccountInfo accountInfo=new AccountInfo();
        TInfoCustomer tInfoCustomer=new TInfoCustomer();
        //必填判断
        if (Strings.isNullOrEmpty(unifyId)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }
        accountInfo.setAccountKey(unifyId);
        accountInfo.setKeyType(KeyType.UNIFY);
        try {
            responseC=hubCustomerQueryService.queryCustomerInfo(accountInfo);
            if(responseC.isSuccess()){
                logger.info("findUserByPK success accountInfo:{}",accountInfo);

                tInfoCustomer.setCustomerNo(responseC.getResult().getCustomerNo());
                tInfoCustomer.setUnifyId(responseC.getResult().getUnifyId());
                tInfoCustomer.setName(responseC.getResult().getName());
                tInfoCustomer.setIdentifyType(responseC.getResult().getIdType());
                tInfoCustomer.setIdentifyNo(responseC.getResult().getIdNo());
                tInfoCustomer.setQuestion(responseC.getResult().getQuestion());
                tInfoCustomer.setAnswer(responseC.getResult().getAnswer());
                tInfoCustomer.setIsRealname(responseC.getResult().getIsRealname());
                tInfoCustomer.setCustomerGrade(responseC.getResult().getGrade());
                tInfoCustomer.setRegisterType(responseC.getResult().getRegisterType());
                tInfoCustomer.setRegisteDate(responseC.getResult().getRegisterTime());
                tInfoCustomer.setAreaCode(responseC.getResult().getAreaCode());
                tInfoCustomer.setCityCode(responseC.getResult().getCityCode());
                tInfoCustomer.setMobileNo(responseC.getResult().getMobilePhone());


                response.setResult(tInfoCustomer);
                return response;
            }       else{
                logger.error("fail to findUserByPK accountInfo:{} cause by {}",new Object[]{accountInfo,responseC.getErrorCode()+":"+responseC.getErrorMsg()});
                response.setErrorCode(responseC.getErrorCode()+":"+responseC.getErrorMsg());
                return response;
            }

        } catch (Exception e) {
            logger.error("fail to findUserByPK cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        return response;
    }

    /**
     * 修改个人信息
     *
     * @param commonUser    公共用户对象
     * @return 如果修改成功, 则返回该用户对象, 否则返回失败原因
     */
    public Response<String> modifyCustomerInfo(CommonUser commonUser,String gender,String homeTelephone,String officeTelephone,String otherTelephone,String contactAddress) {
        Response<String> response = new Response<String>();
        if (commonUser == null || commonUser.getUnifyId() == null || gender == null) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }


               com.huateng.p3.account.persistence.models.TInfoCustomer tInfoCustomer=new   com.huateng.p3.account.persistence.models.TInfoCustomer();
        tInfoCustomer.setLastUpdateTime(DateUtil.getDate());
        tInfoCustomer.setGender(gender);
        tInfoCustomer.setUnifyId(commonUser.getUnifyId());
        tInfoCustomer.setHomeTelephone(homeTelephone);
        tInfoCustomer.setOfficeTelephone(officeTelephone);
        tInfoCustomer.setOtherTelephone(otherTelephone);
        tInfoCustomer.setContactAddress(contactAddress);
        ManagerLog managerLog=managerLogCom.getManagerLog();

        try {
            response= hubCustomerManagerService.modifyCustomerInfo(tInfoCustomer,managerLog);
            if(response.isSuccess()){
                logger.info("modifyCustomerInfo success tInfoCustomer:{}  managerLog:{}",tInfoCustomer,managerLog);
            }else{
                logger.error("fail to modifyCustomerInfo tInfoCustomer:{} managerLog:{} cause by {}",new Object[]{tInfoCustomer,managerLog,response.getErrorCode()+":"+response.getErrorMsg()});
            }
        } catch (Exception e) {
            logger.error("fail to modifyCustomerInfo cause by {}", e);
        }

        return response;
    }

    /**
     * 修改 客户信息
     *
     * @param tInfoCustomer 用户对象
     * @return 如果修改成功, 则返回该用户对象, 否则返回失败原因
     */
    public Response<TInfoCustomer> modifyCustomer(TInfoCustomer tInfoCustomer) {
        Response<TInfoCustomer> response = new Response<TInfoCustomer>();
       /* if (null == tInfoCustomer || null == tInfoCustomer.getCustomerNo()) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }
        int res = tInfoCustomerMapper.updateCustomerInfo(tInfoCustomer);
        if (res == 1) {
            response.setResult(tInfoCustomer);
        } else {
            response.setErrorCode(BussErrorCode.ERROR_CODE_700009.getErrorcode());
        }*/
        return response;
    }

    /**
     * 找回登录密码
     *
     * @return 如果找回成功
     */
    @Override
    public Response<String> findPassWord(CommonUser commonUser,String newPassword) {
        if (Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            logger.info("找回密码 统一编号 为空");
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        if (!ValidationFieldUtil.checkEmail(commonUser.getUnifyId())&&!ValidationFieldUtil.checkMobile(commonUser.getUnifyId())) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800002.getErrorcode());}
        AccountInfo accountInfo=new AccountInfo();
        System.out.print(commonUser.getUnifyId());
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        ManagerLog managerLog=managerLogCom.getManagerLog(accountInfo);
        managerLog.setNewLoginPassword(newPassword);

        try {
        	Response<String> code=hubCustomerManagerService.resetLoginPasswd(accountInfo, managerLog);

        	if(code.isSuccess()){
        		 return new Response<String>(true, code.getResult());
                }else{
                	return new Response<String>(false, code.getErrorCode());
                }


            
        } catch (Exception e) {
            logger.error("fail to update customer unifyId is {} pinkey cause by {}", commonUser.getUnifyId(), e.getMessage());
            return new Response<String>(false, BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }

  
    }

    /**
     * 设置密保问题
     *
     * @param commonUser       账户Key
     * @param securityQuestion 密保问题
     * @param securityAnswer   密保问题答案
     * @return 如果修改成功, 则返回该用户对象, 否则返回失败原因
     */
    @Override
    public Response<String> changeSecurityQuestionAndAnswer(CommonUser commonUser, String securityQuestion, String securityAnswer) {
        Response<String> response = new Response<String>();
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        SecurityQuestionInfo securityQuestionInfo = new SecurityQuestionInfo();

        securityQuestionInfo.setSecrurityQuestion(securityQuestion);
        securityQuestionInfo.setSecrurityAnwser(securityAnswer);
        if (Strings.isNullOrEmpty(commonUser.getUnifyId()) || Strings.isNullOrEmpty(securityQuestion) || Strings.isNullOrEmpty(securityAnswer)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001.getErrorcode());
            return response;
        }
        try {
            response = hubCustomerManagerService.changeSecrurityQuestionAndAnswer(accountInfo, managerLog, securityQuestionInfo);
            if (response.isSuccess()) {
                logger.info("changeSecurityQuestionAndAnswer success accountInfo:{} securityQuestionInfo:{} managerLog{} ", new Object[]{accountInfo, securityQuestionInfo, managerLog});
                return new Response<String>(true, "000000");
            } else {
                logger.error("fail to changeSecurityQuestionAndAnswer accountInfo:{} securityQuestionInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, securityQuestionInfo, response.getErrorCode() + ":" + response.getErrorMsg()});
                return new Response<String>(false, response.getErrorCode() + ":" + response.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("failed to change security questions and answers caused by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }

    /**
     * 激活账户
     *
     * @param commonUser 账户Key
     * @return 如果修改成功, 则返回该用户对象, 否则返回失败原因
     */
    @Override
    public Response<String> activeCustomer(CommonUser commonUser) {
        Response<String> response = new Response<String>();

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        if (Strings.isNullOrEmpty(accountInfo.getAccountKey())) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001.getErrorcode());
            return response;
        }

        try {
            response = hubCustomerManagerService.activationCustomer(accountInfo, managerLog);
            if (response.isSuccess()) {
                logger.info("activeCustomer success accountInfo:{} managerLog:{}  ", new Object[]{accountInfo, managerLog});
                return new Response<String>(true, "000000");
            } else {
                logger.error("fail to changeSecurityQuestionAndAnswer accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, response.getErrorCode() + ":" + response.getErrorMsg()});
                return new Response<String>(false, response.getErrorCode() + ":" + response.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("failed to make customer active caused by {} ", e.getMessage());
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }

    }

    /**
     * 手机绑定 commonUser
     * @param mobileNo
     * @return
     */
    @Override
    public Response<String> mobilePhoneBinding( CommonUser commonUser, String mobileNo) {
        Response<String> response = new Response<String>();
        com.huateng.p3.account.persistence.models.TInfoCustomer customer = new com.huateng.p3.account.persistence.models.TInfoCustomer();

        customer.setMobileNo(mobileNo);
        customer.setUnifyId(commonUser.getUnifyId());
        ManagerLog managerLog=new  ManagerLog();

        try {
            response=hubCustomerManagerService.mobilePhoneBinding(customer,managerLog);

            if(response.isSuccess()){
                logger.info("mobilePhoneBinding success customer:{}",customer);

                return new Response<String>(true,"000000");
            }
            else{
                logger.error("fail to mobilePhoneBinding customer:{} cause by {}",new Object[]{customer,response.getErrorCode()+":"+response.getErrorMsg()});
                return new Response<String>(false,response.getErrorCode());
            }


        } catch (Exception e) {
            logger.error("fail to mobilePhoneBinding cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;

        }

    }


    /**
     * 手机解绑 commonUser
     * @return
     */
    @Override
    public Response<String> unMobilePhoneBinding( CommonUser commonUser,String payPwd) {
        Response<String> response = new Response<String>();
        com.huateng.p3.account.persistence.models.TInfoCustomer customer = new com.huateng.p3.account.persistence.models.TInfoCustomer();
        customer.setMobileNo("");
        customer.setUnifyId(commonUser.getUnifyId());


        //核心返回对象
        Response<CustomerResultObject> customerObjectResponse;
        //账户信息
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        accountInfo.setTxnPassword(Hashing.md5().hashString(payPwd, Charsets.UTF_8).toString());

        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        managerLog.setFeeFlag(managerLog.getFeeFlag());
        managerLog.setFeeAmt(managerLog.getFeeAmt());
        managerLog.setInputTime(managerLog.getInputTime());
        managerLog.setCheckTime(managerLog.getCheckTime());

        Response<String> checkTxnPasswordResponse;
        try {
            customerObjectResponse = hubCustomerQueryService.queryCustomerInfo(accountInfo);
            //查询客户信息
            if (customerObjectResponse.isSuccess())   {
                logger.info("queryCustomerInfo success accountInfo:{}",accountInfo);
            }else{
                response.setErrorCode(BussErrorCode.ERROR_CODE_700009
                        .getErrorcode());
                return response;
            }
            //验证支付密码
            checkTxnPasswordResponse = hubAccountManagerService.checkTxnPasswd(accountInfo,managerLog);
            if(checkTxnPasswordResponse.isSuccess()){
                logger.info("checkTxnPassword success accountInfo:{} managerLog:{}",accountInfo,managerLog);
            }else{
                logger.error("fail to checkTxnPassword accountInfo:{} managerLog:{} cause by {}",new Object[]{accountInfo, managerLog, checkTxnPasswordResponse.getErrorCode()+":"+checkTxnPasswordResponse.getErrorMsg()});
                return new Response<String>(false,BussErrorCode.ERROR_CODE_200004.getErrorcode());
            }


            response=hubCustomerManagerService.unMobilePhoneBinding(customer,managerLog);

            if(response.isSuccess()){
                logger.info("unMobilePhoneBinding success customer:{}",customer);

                return new Response<String>(true,"000000");
            }
            else{
                logger.error("fail to unMobilePhoneBinding customer:{} cause by {}",new Object[]{customer,response.getErrorCode()+":"+response.getErrorMsg()});
                return new Response<String>(false,response.getErrorCode());
            }


        } catch (Exception e) {
            logger.error("fail to unMobilePhoneBinding cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;

        }

    }



    /**
     * 手机绑定修改 commonUser
     * @param mobileNo
     * @return
     */
    @Override
    public Response<String> modifyMobilePhoneBinding( CommonUser commonUser, String mobileNo,String payPwd) {
        Response<String> response = new Response<String>();
        com.huateng.p3.account.persistence.models.TInfoCustomer customer = new com.huateng.p3.account.persistence.models.TInfoCustomer();

        customer.setMobileNo(mobileNo);
        customer.setUnifyId(commonUser.getUnifyId());

        //核心返回对象
        Response<CustomerResultObject> customerObjectResponse;
        //账户信息
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);

        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        managerLog.setFeeFlag(managerLog.getFeeFlag());
        managerLog.setFeeAmt(managerLog.getFeeAmt());
        managerLog.setInputTime(managerLog.getInputTime());
        managerLog.setCheckTime(managerLog.getCheckTime());
        accountInfo.setTxnPassword(Hashing.md5().hashString(payPwd, Charsets.UTF_8).toString());

        Response<String> checkTxnPasswordResponse;


        try {
            customerObjectResponse = hubCustomerQueryService.queryCustomerInfo(accountInfo);
            //查询客户信息
            if (customerObjectResponse.isSuccess())   {
                logger.info("queryCustomerInfo success accountInfo:{}",accountInfo);
            }else{
                response.setErrorCode(BussErrorCode.ERROR_CODE_700009
                        .getErrorcode());
                return response;
            }
            //验证支付密码
            checkTxnPasswordResponse = hubAccountManagerService.checkTxnPasswd(accountInfo,managerLog);
            if(checkTxnPasswordResponse.isSuccess()){
                logger.info("checkTxnPassword success accountInfo:{} managerLog:{}",accountInfo,managerLog);
            }else{
                logger.error("fail to checkTxnPassword accountInfo:{} managerLog:{} cause by {}",new Object[]{accountInfo, managerLog, checkTxnPasswordResponse.getErrorCode()+":"+checkTxnPasswordResponse.getErrorMsg()});
                return new Response<String>(false,BussErrorCode.ERROR_CODE_200004.getErrorcode());
            }


            response=hubCustomerManagerService.mobilePhoneBinding(customer,managerLog);

            if(response.isSuccess()){
                logger.info("modifyMobilePhoneBinding success customer:{}",customer);

                return new Response<String>(true,"000000");
            }
            else{
                logger.error("fail to modifyMobilePhoneBinding customer:{} cause by {}",new Object[]{customer,response.getErrorCode()+":"+response.getErrorMsg()});
                return new Response<String>(false,response.getErrorCode());
            }


        } catch (Exception e) {
            logger.error("fail to modifyMobilePhoneBinding cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;

        }

    }

    /**
     *     银行卡绑定
     * @param commonUser
     * @return
     */
    @Override
    public Response<String> bindBankCard(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("cardNo") String cardNo, @ParamInfo("name") String name, @ParamInfo("idNo") String idNo,@ParamInfo("mobileNo") String mobileNo,@ParamInfo("bankCode") String bankCode,@ParamInfo("cardType") String cardType) {
        Response<String> response = new Response<String>();
        com.huateng.p3.account.persistence.models.TInfoCustomer tInfoCustomer = new com.huateng.p3.account.persistence.models.TInfoCustomer();
        if (commonUser == null || commonUser.getUnifyId() == null) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }

        Response<TInfoCustomer> res=findUser(commonUser);
        if(res.isSuccess()){
            tInfoCustomer.setUnifyId(commonUser.getUnifyId());
            tInfoCustomer.setIsRealname(res.getResult().getIsRealname());
            tInfoCustomer.setIdentifyNo(idNo);
            tInfoCustomer.setRegisterType(res.getResult().getRegisterType());
            tInfoCustomer.setCustomerNo(res.getResult().getCustomerNo());
        }else{
            logger.error("fail to findUser commonUser:{} cause by {}",new Object[]{commonUser,res.getErrorCode()+":"+res.getErrorMsg()});
            response.setErrorCode(res.getErrorCode());
            return response;
        }

        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);

        ManagerLog managerLog=managerLogCom.getManagerLog(accountInfo);
        managerLog.setIdtype("1");
        managerLog.setIdno(idNo);
        managerLog.setName(res.getResult().getName());
        TInfoBankcard tInfoBankcard=new TInfoBankcard();
        tInfoBankcard.setBankCardNo(cardNo);
        tInfoBankcard.setIdentityNo(idNo);
        tInfoBankcard.setCardType(cardType);
        tInfoBankcard.setBankCode(bankCode);
        tInfoCustomer.setMobileNo(mobileNo);
        tInfoCustomer.setUnifyId(commonUser.getUnifyId());

        String bindingType="";




        try {
            response=hubBankCardManagerService.bindBankCard(tInfoBankcard,bindingType,tInfoCustomer,managerLog);

            if(response.isSuccess()){
                logger.info("bindBankCard success tInfoBankcard:{} bindingType:{} tInfoCustomer:{} managerLog:{}",new Object[]{tInfoBankcard,bindingType,tInfoCustomer,managerLog});

                return new Response<String>(true,"000000");
            }
            else{
                logger.error("fail to bindBankCard tInfoBankcard:{} bindingType:{} tInfoCustomer:{} managerLog:{} cause by {}", new Object[]{tInfoBankcard, bindingType, tInfoCustomer, managerLog, response.getErrorCode() + ":" + response.getErrorMsg()});
                return new Response<String>(false,response.getErrorCode());
            }


        } catch (Exception e) {
            logger.error("fail to bindBankCard cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;

        }
    }

    /**
     * 银行卡解绑
     * @return
     */
    @Override
    public Response<String> unBindBankCard(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("cardNo") String cardNo) {
        Response<String> response = new Response<String>();
        com.huateng.p3.account.persistence.models.TInfoCustomer tInfoCustomer = new com.huateng.p3.account.persistence.models.TInfoCustomer();


        Response<TInfoCustomer> res=findUser(commonUser);
        if(res.isSuccess()){
            tInfoCustomer.setUnifyId(commonUser.getUnifyId());
            tInfoCustomer.setIsRealname(res.getResult().getIsRealname());
            tInfoCustomer.setIdentifyNo(res.getResult().getIdentifyNo());
            tInfoCustomer.setRegisterType(res.getResult().getRegisterType());
            tInfoCustomer.setCustomerNo(res.getResult().getCustomerNo());
        }else{
            logger.error("fail to findUser commonUser:{} cause by {}",new Object[]{commonUser,res.getErrorCode()+":"+res.getErrorMsg()});
            response.setErrorCode(res.getErrorCode());
            return response;
        }

        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);

        ManagerLog managerLog=managerLogCom.getManagerLog(accountInfo);
        managerLog.setIdtype("1");
        managerLog.setIdno(res.getResult().getIdentifyNo());
        managerLog.setName(res.getResult().getName());
        TInfoBankcard tInfoBankcard=new TInfoBankcard();
        tInfoBankcard.setBankCardNo(cardNo);
        tInfoBankcard.setIdentityNo(res.getResult().getIdentifyNo());
        tInfoCustomer.setUnifyId(commonUser.getUnifyId());




        try {
            response=hubBankCardManagerService.unbindBankCardByBankCardNo(tInfoBankcard,tInfoCustomer,managerLog);

            if(response.isSuccess()){
                logger.info("unBindBankCard success tInfoBankcard:{} tInfoCustomer:{} managerLog:{}",new Object[]{tInfoBankcard,tInfoCustomer,managerLog});

                return new Response<String>(true,"000000");
            }
            else{
                logger.error("fail to unBindBankCard tInfoBankcard:{} tInfoCustomer:{} managerLog:{} cause by {}",new Object[]{tInfoBankcard,tInfoCustomer,managerLog,response.getErrorCode()+":"+response.getErrorMsg()});
                return new Response<String>(false,response.getErrorCode());
            }


        } catch (Exception e) {
            logger.error("fail to unBindBankCard cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;

        }
    }

    @Override
    public Response<Paging<TInfoAccountBankCard>> querytAccountBankCardBinding(@ParamInfo("commonUser") CommonUser commonUser) {
        Response<List<TInfoAccountBankCard>> response = new Response<List<TInfoAccountBankCard>>();
        TInfoAccountBankCard tInfoAccountBankCard=new TInfoAccountBankCard();
        try {
            Response<TInfoAccount> res=loadAccounts(commonUser);
            if(res.isSuccess()){
                tInfoAccountBankCard.setFundAccountNo(res.getResult().getAccountNo());
            }else{
                return new Response<Paging<TInfoAccountBankCard>>(res.getErrorCode());
            }


            response = hubCustomerQueryService.queryAccountBankCardBinding(tInfoAccountBankCard);
            if (response.isSuccess()) {
                logger.info("querytAccountBankCardBinding success TInfoAccountBankCard:{}", tInfoAccountBankCard);
                List<TInfoAccountBankCard> list=response.getResult();
                Paging<TInfoAccountBankCard> tPortOrderBasePagingList = new Paging<TInfoAccountBankCard>(Long.parseLong(list.size()+""), list);
                return new Response<Paging<TInfoAccountBankCard>>(tPortOrderBasePagingList);
            } else {
                logger.error("fail to querytBankCardBinding tInfoAccountBankCard:{} cause by {}", new Object[]{tInfoAccountBankCard, response.getErrorCode() + ":" + response.getErrorMsg()});
                return new Response<Paging<TInfoAccountBankCard>>(response.getErrorCode());
            }

        } catch (Exception e) {
            logger.error("fail to querytBankCardBinding cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        return new Response<Paging<TInfoAccountBankCard>>(response.getErrorCode());
    }


}
