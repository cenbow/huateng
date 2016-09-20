package com.huateng.p3.account.manager;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.manager.inner.AccountManager;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-6
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class AccountManagerServiceImpl implements AccountManagerService {


    @Autowired
    private AccountManager accountManager;


    @Override
    public Response<String> modfiyTxnPasswd(AccountInfo accountInfo,
                                            ManagerLog managerLog) {
        log.info("call modfiyTxnPasswd,PARAMETER:{} {}", new Object[]{accountInfo, managerLog});
        Response<String> result = new Response<String>();
        try {
            accountManager.doModfiyTxnPasswd(accountInfo, managerLog);
            result.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            log.info("success to modfiyTxnPasswd,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to modfiyTxnPasswd,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        }catch (SubmitBizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to modfiyTxnPasswd,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (Exception e) {
            log.error("failed to modfiyTxnPasswd,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, Throwables.getStackTraceAsString(e)});
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }
    }


    @Override
    public Response<String> checkTxnPasswd(AccountInfo accountInfo,
                                           ManagerLog managerLog) {
        log.info("call checkTxnPasswd,PARAMETER:{} {}", new Object[]{accountInfo, managerLog});
        Response<String> result = new Response<String>();
        try {
            accountManager.doCheckTxnPasswd(accountInfo, managerLog);
            result.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            log.info("success to checkTxnPasswd,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to checkTxnPasswd,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (SubmitBizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to checkTxnPasswd,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        }catch (Exception e) {
            log.error("failed to checkTxnPasswd,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, Throwables.getStackTraceAsString(e)});
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }
    }


    @Override
    public Response<String> modfiyTxnPasswdWithoutOldPwd(
            AccountInfo accountInfo, ManagerLog managerLog) {
        log.info("call modfiyTxnPasswdWithoutOldPwd,PARAMETER:{} {}", new Object[]{accountInfo, managerLog});
        Response<String> result = new Response<String>();
        try {

            accountManager.doModfiyTxnPasswdWithoutOldPwd(accountInfo, managerLog);
            result.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            log.info("success to modfiyTxnPasswdWithoutOldPwd,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to modfiyTxnPasswdWithoutOldPwd,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
            return result;
        } catch (Exception e) {
            log.error("failed to modfiyTxnPasswdWithoutOldPwd,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, Throwables.getStackTraceAsString(e)});
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return result;
        }
    }


    @Override
    public Response<String> authenticateCustomerIdentityApplyForSecurity(
            AccountInfo accountInfo, ManagerLog managerLog) {
        log.info("call authenticateCustomerIdentityApplyForSecurity,PARAMETER:{} {}", new Object[]{accountInfo, managerLog});
        Response<String> result = new Response<String>();
        try {
            accountManager.doAuthenticateCustomerIdentityApplyForSecurity(accountInfo, managerLog);
            result.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            log.info("success to authenticateCustomerIdentityApplyForSecurity,PARAMETER:{} {}, RESULT:{}",new Object[]{ accountInfo, managerLog, result});
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to authenticateCustomerIdentityApplyForSecurity,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
        } catch (Exception e) {
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to authenticateCustomerIdentityApplyForSecurity,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, Throwables.getStackTraceAsString(e)});

        }
        return result;

    }

    @Override
    public Response<String> freezeAccount(AccountInfo accountInfo, ManagerLog managerLog) {
    	
    	log.info("call freezeAccount,PARAMETER:{} {}", new Object[]{accountInfo, managerLog});
        Response<String> result = new Response<String>();
        try {
            accountManager.dofreezeAccount(accountInfo, managerLog);
            result.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            log.info("success to freezeAccount,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to freezeAccount,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
        } catch (Exception e) {
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to freezeAccount,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, Throwables.getStackTraceAsString(e)});

        }
        return result;
    }

    @Override
    public Response<String> unfreezeAccount(AccountInfo accountInfo, ManagerLog managerLog) {
    	log.info("call unfreezeAccount,PARAMETER:{} {}", new Object[]{accountInfo, managerLog});
        Response<String> result = new Response<String>();
        try {
            accountManager.unfreezeAccount(accountInfo, managerLog);
            result.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            log.info("success to unfreezeAccount,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
        } catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());
            log.info("failed to unfreezeAccount,PARAMETER:{} {}, RESULT:{}", new Object[]{accountInfo, managerLog, result});
        } catch (Exception e) {
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to unfreezeAccount,PARAMETER:{} {}, CAUSE:{}", new Object[]{accountInfo, managerLog, Throwables.getStackTraceAsString(e)});

        }
        return result;
    }


}
