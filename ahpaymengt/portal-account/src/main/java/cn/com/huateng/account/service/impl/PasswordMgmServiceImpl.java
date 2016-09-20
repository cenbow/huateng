package cn.com.huateng.account.service.impl;

import cn.com.huateng.account.model.TInfoAccount;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.account.util.ManagerLogCom;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.SecurityQuestionInfo;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.hub.accountcore.service.HubAccountManagerService;
import com.huateng.p3.hub.accountcore.service.HubCustomerQueryService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubCustomerManagerService;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.service.PasswordMgmService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.util.ValidationFieldUtil;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 登录密码修改、支付密码修改、支付密码重置、密保问题重置
 *
 * @author huwenjie
 * @date 2013-08-05
 */
@Service
public class PasswordMgmServiceImpl implements PasswordMgmService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordMgmServiceImpl.class);


    @Autowired
    private HubCustomerManagerService hubCustomerManagerService;

    @Autowired
    private SeqGeneratorService seqGeneratorService;


    @Autowired
    private HubCustomerQueryService hubCustomerQueryService;


    @Autowired
    private HubAccountManagerService hubAccountManagerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerLogCom managerLogCom;

    @Value("#{app.smsDetectAmt}")
    private long smsDetectAmt;

    @Value("#{app.idNoDetectAmt}")
    private long idNoDetectAmt;

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat
            .forPattern("yyyyMMddHHmmss");

    /**
     * 修改登录密码
     *
     * @param commonUser       公共用户对象 必填
     * @param oldLoginPassword 老登录密码 必填
     * @param newLoginPassword 新登录密码 必填
     * @return 如果操作成功, 则返回SUCCESS, 否则返回失败原因
     */
    @Override
    public Response<String> modifyLoginPasswd(CommonUser commonUser,
                                              String oldLoginPassword, String newLoginPassword) {
        // 必填判断
        Response<String> response = new Response<String>();
        if (commonUser == null || commonUser.getId() == null
                || Strings.isNullOrEmpty(oldLoginPassword)
                || Strings.isNullOrEmpty(newLoginPassword)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }
        // 格式判断
        if (!ValidationFieldUtil.passwordPattern.matcher(oldLoginPassword)
                .find()
                || !ValidationFieldUtil.passwordPattern
                .matcher(newLoginPassword).find()) {

            response.setErrorCode(BussErrorCode.ERROR_CODE_800002
                    .getErrorcode());
            return response;
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        managerLog.setNewLoginPassword(newLoginPassword);
        managerLog.setOldLoginPassword(oldLoginPassword);
        try {
            Response<String> code = hubCustomerManagerService.modfiyLoginPasswdWithOldPwd(accountInfo, managerLog);
            if (code.isSuccess()) {
                response.setResult("SUCCESS");
            } else {
                response.setErrorCode(code.getErrorCode());
            }
            return response;
        } catch (Exception e) {
            logger.error("fail to modify login password cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }

    @Override
    public Response<String> resetPayPasswdByQuestion(CommonUser commonUser,
                                                     String pwdQuestion, String pwdAnswer, String newPayPassword) {
        // 必填判断
        Response<String> response = new Response<String>();
        Response<CustomerResultObject> customerResultObjectResponse;
        if (commonUser == null || commonUser.getId() == null
                || Strings.isNullOrEmpty(pwdQuestion)
                || Strings.isNullOrEmpty(pwdAnswer)
                || Strings.isNullOrEmpty(newPayPassword)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }

        // 格式判断
        if (!ValidationFieldUtil.checkChar(newPayPassword)
                || !ValidationFieldUtil.checkCde(pwdAnswer)) {

            response.setErrorCode(BussErrorCode.ERROR_CODE_800002
                    .getErrorcode());
            return response;
        }
        try {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountKey(commonUser.getUnifyId());
            accountInfo.setKeyType(KeyType.UNIFY);
            accountInfo.setNewTxnPassword(newPayPassword);
            ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
            customerResultObjectResponse = hubCustomerQueryService.queryCustomerInfo(accountInfo);
            if (!customerResultObjectResponse.isSuccess()) {
                response.setErrorCode(customerResultObjectResponse.getErrorCode());
                response.setErrorMsg(customerResultObjectResponse.getErrorMsg());
                return response;
            }
            // 获取数据库中正确的密保，密保验证
            String question = customerResultObjectResponse.getResult().getQuestion();
            String answer = customerResultObjectResponse.getResult().getAnswer();
            if (!pwdAnswer.equals(answer) || !pwdQuestion.equals(question)) {
                response.setErrorCode(BussErrorCode.ERROR_CODE_700005
                        .getErrorcode());
                return response;
            }

            // 再调用一次验证
            Response<String> res = resetPayPasswdDetect(commonUser, "1");
            if (!res.isSuccess()) {

                response.setErrorCode(res.getErrorCode());
                return response;
            }
            // 验证手机验证码在前台,后台不判断了

            // 判断成功调用hessian接口
            response = hubAccountManagerService.modfiyTxnPasswdWithoutOldPwd(accountInfo, managerLog);
            if (response.isSuccess()) {
                logger.info("resetPayPasswdByQuestion success accountInfo:{} managerLog:{}", accountInfo, managerLog);

                return response;
            } else {
                logger.error("fail to resetPayPasswdByQuestion accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, response});
                return response;
            }


        } catch (Exception e) {
            logger.error("fail to resetPayPasswdByQuestion cause by {}", e.getMessage());
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }

    @Override
    public Response<String> resetPayPasswdBySms(CommonUser commonUser,
                                                String newPayPassword) {
        Response<String> response = new Response<String>();
        Response<CustomerResultObject> responseCus = new Response<CustomerResultObject>();
        if (commonUser == null || commonUser.getUnifyId() == null
                || Strings.isNullOrEmpty(newPayPassword)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }

        // 格式判断
        if (!ValidationFieldUtil.checkChar(newPayPassword)) {

            response.setErrorCode(BussErrorCode.ERROR_CODE_800002
                    .getErrorcode());
            return response;
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        accountInfo.setNewTxnPassword(newPayPassword);
        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        try {

            // 再调用一次验证
            Response<String> res = resetPayPasswdDetect(commonUser, "2");
            if (!res.isSuccess()) {

                response.setErrorCode(res.getErrorCode());
                return response;
            }


            response = hubAccountManagerService.modfiyTxnPasswdWithoutOldPwd(accountInfo, managerLog);
            if (response.isSuccess()) {
                logger.info("resetPayPasswdBySms success accountInfo:{} managerLog:{}", accountInfo, managerLog);

                return response;
            } else {
                logger.error("fail to resetPayPasswdBySms accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, response});
                return response;
            }
        } catch (Exception e) {
            logger.error("fail to resetPayPasswdBySms cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }

    @Override
    public Response<String> resetPayPasswdByIdNo(CommonUser bestpayUser,
                                                 String realName, String idNo, String newPayPassword) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<String> resetPasswdQuestionByTxnPwd(CommonUser commonUser,
                                                        String payPassword, String question, String answer) {
        // 必填判断
        Response<String> response = new Response<String>();
        if (Strings.isNullOrEmpty(commonUser.getUnifyId())
                || Strings.isNullOrEmpty(payPassword) || Strings.isNullOrEmpty(question) || Strings.isNullOrEmpty(answer)) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        //核心返回对象
        Response<CustomerResultObject> customerObjectResponse;
        //账户信息
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);

        //密保问题
        SecurityQuestionInfo securityQuestionInfo = new SecurityQuestionInfo();
        securityQuestionInfo.setSecrurityQuestion(question);
        securityQuestionInfo.setSecrurityAnwser(answer);

        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        managerLog.setFeeFlag(managerLog.getFeeFlag());
        managerLog.setFeeAmt(managerLog.getFeeAmt());
        managerLog.setInputTime(managerLog.getInputTime());
        managerLog.setCheckTime(managerLog.getCheckTime());
        accountInfo.setTxnPassword(Hashing.md5().hashString(payPassword, Charsets.UTF_8).toString());
        Response<String> checkTxnPasswordResponse;

        try {
            customerObjectResponse = hubCustomerQueryService.queryCustomerInfo(accountInfo);
            //查询客户信息
            if (customerObjectResponse.isSuccess()) {
                logger.info("queryCustomerInfo success accountInfo:{}", accountInfo);
            } else {
                response.setErrorCode(BussErrorCode.ERROR_CODE_700009
                        .getErrorcode());
                return response;
            }
            //验证支付密码
            checkTxnPasswordResponse = hubAccountManagerService.checkTxnPasswd(accountInfo, managerLog);
            if (checkTxnPasswordResponse.isSuccess()) {
                logger.info("checkTxnPassword success accountInfo:{} managerLog:{}", accountInfo, managerLog);
            } else {
                logger.error("fail to checkTxnPassword accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, checkTxnPasswordResponse.getErrorCode() + ":" + checkTxnPasswordResponse.getErrorMsg()});
                return new Response<String>(false, BussErrorCode.ERROR_CODE_200004.getErrorcode());
            }
            Response<String> questionResponse = hubCustomerManagerService.changeSecrurityQuestionAndAnswer(accountInfo, managerLog, securityQuestionInfo);
            if (questionResponse.isSuccess()) {
                logger.info("resetPasswdQuestionByTxnPwd success accountInfo:{} managerLog:{} securityQuestionInfo:{}", new Object[]{accountInfo, managerLog, securityQuestionInfo});
                return new Response<String>(true, "000000");
            } else {
                logger.error("fail to resetPasswdQuestionByTxnPwd accountInfo:{} managerLog:{} securityQuestionInfo:{} cause by {}", new Object[]{accountInfo, managerLog, securityQuestionInfo, questionResponse.getErrorCode() + ":" + questionResponse.getErrorMsg()});
                return new Response<String>(false, BussErrorCode.ERROR_CODE_200005.getErrorcode());
            }
        } catch (Exception e) {
            logger.error("fail to resetPasswdQuestionByTxnPwd cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }


    }

    @Override
    public Response<String> checkTxnPasswd(CommonUser commonUser,
                                           String payPassword) {
        Response<CustomerResultObject> customerObjectResponse;
        Response<String> response = new Response<String>();
        Response<String> checkTxnPasswordResponse;
        //账户信息
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        accountInfo.setTxnPassword(Hashing.md5().hashString(payPassword, Charsets.UTF_8).toString());

        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);

        try {
            customerObjectResponse = hubCustomerQueryService.queryCustomerInfo(accountInfo);

            //查询客户信息
            if (customerObjectResponse.isSuccess()) {
                logger.info("checkTxnPasswd success accountInfo:{}", accountInfo);
            } else {
                response.setErrorCode(BussErrorCode.ERROR_CODE_700009
                        .getErrorcode());
                return response;
            }
            //验证支付密码
            checkTxnPasswordResponse = hubAccountManagerService.checkTxnPasswd(accountInfo, managerLog);
            if (checkTxnPasswordResponse.isSuccess()) {
                logger.info("checkTxnPasswd success accountInfo:{} managerLog:{}", accountInfo, managerLog);
                return new Response<String>(true, "000000");
            } else {
                logger.error("fail to checkTxnPasswd accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, checkTxnPasswordResponse.getErrorCode() + ":" + checkTxnPasswordResponse.getErrorMsg()});
                return new Response<String>(false, BussErrorCode.ERROR_CODE_200004.getErrorcode());
            }
        } catch (Exception e) {
            logger.error("fail to checkTxnPasswd cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }


    @Override
    public Response<String> resetPasswdQuestionBySms(CommonUser bestpayUser,
                                                     String question, String answer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<String> resetTxnPasswd(CommonUser commonUser, String newPassword) {
        // 必填判断
        if (Strings.isNullOrEmpty(commonUser.getUnifyId())
                || Strings.isNullOrEmpty(newPassword)) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }

        // 格式判断
        if (!ValidationFieldUtil.mobilePattern.matcher(commonUser.getUnifyId()).find()) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800002
                    .getErrorcode());
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        Response<String> responce = new Response<String>();
        managerLog.setFeeFlag(managerLog.getFeeFlag());
        managerLog.setFeeAmt(managerLog.getFeeAmt());
        managerLog.setInputTime(managerLog.getInputTime());
        managerLog.setCheckTime(managerLog.getCheckTime());
        accountInfo.setNewTxnPassword(newPassword);
        try {
            responce = hubAccountManagerService.modfiyTxnPasswdWithoutOldPwd(accountInfo, managerLog);
            if (responce.isSuccess()) {
                logger.info("resetTxnPasswd success accountInfo:{} managerLog:{}", accountInfo, managerLog);
                return responce;
            } else {
                logger.error("fail to resetTxnPasswd accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, responce});
                return responce;
            }
        } catch (Exception e) {
            logger.error("fail to resetTxnPasswd cause by {}", e);
            responce.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return responce;
        }
    }

    @Override
    public Response<String> modfiyTxnPasswd(CommonUser commonUser, String newPassword, String oldPassword) {
        // 必填判断
        if (Strings.isNullOrEmpty(commonUser.getUnifyId())
                || Strings.isNullOrEmpty(newPassword) || Strings.isNullOrEmpty(oldPassword)) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }

        // 格式判断
        if (!ValidationFieldUtil.mobilePattern.matcher(commonUser.getUnifyId()).find()) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800002
                    .getErrorcode());
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);
        Response<String> responce = new Response<String>();
        managerLog.setFeeFlag(managerLog.getFeeFlag());
        managerLog.setFeeAmt(managerLog.getFeeAmt());
        managerLog.setInputTime(managerLog.getInputTime());
        managerLog.setCheckTime(managerLog.getCheckTime());
        accountInfo.setNewTxnPassword(newPassword);
        accountInfo.setTxnPassword(Hashing.md5().hashString(oldPassword, Charsets.UTF_8).toString());
        try {
            responce = hubAccountManagerService.modfiyTxnPasswd(accountInfo, managerLog);
            if (responce.isSuccess()) {
                logger.info("modfiyTxnPasswd success accountInfo:{} managerLog:{}", accountInfo, managerLog);

                return responce;
            } else {
                logger.error("fail to modfiyTxnPasswd accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, responce});
                return responce;
            }
        } catch (Exception e) {
            logger.error("fail to modfiyTxnPasswd cause by {}", e);
            responce.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return responce;
        }
    }


    /**
     * 密码修改重置判断
     *
     * @param commonUser 公共用户对象  必填
     * @param resetType  重置类型  必填 0(修改密码)  1(密保问题重置)  2(短信验证码重置) 3(身份证重置 )
     * @return 如果操作成功, 则返回SUCCESS, 否则返回失败原因
     */
    @Override
    public Response<String> resetPayPasswdDetect(CommonUser commonUser,
                                                 String resetType) {
        // 必填判断
        Response<String> response = new Response<String>();
        if (commonUser == null || commonUser.getUnifyId() == null
                || Strings.isNullOrEmpty(resetType)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }
        try {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountKey(commonUser.getUnifyId());
            accountInfo.setKeyType(KeyType.UNIFY);
            TInfoCustomer customer;
            Response<CustomerResultObject> responseTcus = new Response<CustomerResultObject>();
            // 验证联系手机号
            responseTcus = hubCustomerQueryService.queryCustomerInfo(accountInfo);

            if (responseTcus.isSuccess()) {
                logger.info("queryCustomerInfo success accountInfo:{} resetType:{}", accountInfo,resetType);
                //检查联系手机
                if (Strings.isNullOrEmpty(responseTcus.getResult().getMobilePhone())) {

                    response.setErrorCode(BussErrorCode.ERROR_CODE_700002
                            .getErrorcode());
                    return response;
                }
                if ("1".equals(resetType) || "2".equals(resetType) || "3".equals(resetType)) {
                    //检查是否有资金账户
                    if (Strings.isNullOrEmpty(responseTcus.getResult().getAccountNo())) {
                        response.setErrorCode(BussErrorCode.ERROR_CODE_700004
                                .getErrorcode());
                        return response;
                    }
                    //检查密保问题
                    if ("1".equals(resetType)) {
                        if (Strings.isNullOrEmpty(responseTcus.getResult().getQuestion())) {
                            response.setErrorCode(BussErrorCode.ERROR_CODE_700021.getErrorcode());
                            return response;
                        }
                    }

                    // 判断账户余额是否小于20元
                    if ("2".equals(resetType)) {
                        if (responseTcus.getResult().getBalance() >= smsDetectAmt) {
                            response.setErrorCode(BussErrorCode.ERROR_CODE_700007
                                    .getErrorcode());
                            return response;

                        }
                    }

                    if ("3".equals(resetType)) {
                        // 判断账户余额是否小于500元
                        if (responseTcus.getResult().getBalance() >= idNoDetectAmt) {
                            response.setErrorCode(BussErrorCode.ERROR_CODE_700018
                                    .getErrorcode());
                            return response;

                        }
                        //判断是否为实名用户
                        if (!("1".equals(responseTcus.getResult().getIsRealname())) && !("4".equals(responseTcus.getResult().getIsRealname()))) {
                            response.setErrorCode(BussErrorCode.ERROR_CODE_700020
                                    .getErrorcode());
                            return response;
                        }
                    }
                }

            } else {
                logger.error("fail to queryCustomerInfo accountInfo:{} cause by {}", new Object[]{accountInfo, responseTcus.getErrorCode() + ":" + responseTcus.getErrorMsg()});
                response.setErrorCode(responseTcus.getErrorCode() + ":" + responseTcus.getErrorMsg());
                return response;
            }
            logger.info("queryCustomerInfo success Result:{}", responseTcus.getResult().getMobilePhone());
            response.setResult(responseTcus.getResult().getMobilePhone());
            return response;

        } catch (Exception e) {
            logger.error("fail to resetPayPasswdDetect cause by {}", e.getMessage());
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }
}


