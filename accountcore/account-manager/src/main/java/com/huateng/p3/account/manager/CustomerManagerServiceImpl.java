package com.huateng.p3.account.manager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.LoginInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.SecurityQuestionInfo;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.manager.inner.AccountManager;
import com.huateng.p3.account.manager.inner.CustomerManager;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA. User: JamesTang Date: 13-12-6 Time: 下午3:52 To
 * change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class CustomerManagerServiceImpl implements CustomerManagerService {

	@Autowired
	private AccountManager accountManager;

	@Autowired
	private CustomerManager customerManager;

	@Override
	public Response<String> synchronizeCustomerInfo(AccountInfo accountInfo,
			TInfoCustomer tInfoCustomer, ManagerLog managerLog) {
		log.info("call synchronizeCustomerInfowithquestion,PARAMETER:{} {} {}",
                new Object[]{accountInfo, tInfoCustomer, managerLog});
		Response<String> r = new Response<String>();
		try {
			r.setResult(customerManager.synchronizeCustomerInfo(accountInfo,
					tInfoCustomer, managerLog));
			log.info(
					"success to synchronizeCustomerInfowithquestion,PARAMETER:{} {} {}, RESULT:{}",
                    new Object[]{accountInfo, tInfoCustomer, managerLog, r});
		} catch (BizException e) {
			r.setErrorCode(e.getCode());
			r.setErrorMsg(e.getMessage());
			log.info(
					"failed to synchronizeCustomerInfowithquestion,PARAMETER:{} {} {}, RESULT:{}",
                    new Object[]{accountInfo, tInfoCustomer, managerLog, r});
		} catch (Exception e) {
			log.error(
					"failed to synchronizeCustomerInfowithquestion,PARAMETER:{} {} {}, CAUSE:{}",
                    new Object[]{accountInfo, tInfoCustomer, managerLog,
					Throwables.getStackTraceAsString(e)});
			r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return r;
	}

	@Override
	public Response<OpenCustomerResultObject> openCustomer(
			TInfoCustomer customer,ManagerLog managerLog) {
		log.info("call openCustomer,PARAMETER:{}  {}",
				customer,  managerLog);
		Response<OpenCustomerResultObject> result = new Response<OpenCustomerResultObject>();
		// 默认开通现金账户
		try {
			result.setResult(accountManager.doOpenCustomer(customer,
					managerLog));
			log.info(
					"success to openCustomer,PARAMETER:{}  {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to openCustomer,PARAMETER:{}  {}, RESULT:{}",
                    new Object[]{customer,  managerLog, result});
			return result;
		} catch (DataIntegrityViolationException e) {

			if (e.toString().contains("ORA-00001")) {
				result.setErrorCode(BussErrorCode.ERROR_CODE_900003
						.getErrorcode());
				result.setErrorMsg(BussErrorCode.ERROR_CODE_900003
						.getErrordesc());
			} else {
				result.setErrorCode(BussErrorCode.ERROR_CODE_900007
						.getErrorcode());
				result.setErrorMsg(BussErrorCode.ERROR_CODE_900007
						.getErrordesc());
			}

			log.error(
					"failed to openCustomer,PARAMETER:{}  {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			return result;

		} catch (CannotAcquireLockException e) {

			result.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
			log.error(
					"failed to openCustomer,PARAMETER:{}  {}, CAUSE:{}",
                    new Object[]{customer,  managerLog,
					Throwables.getStackTraceAsString(e)});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to openCustomer,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
	}

	@Override
	public Response<String> closeCustomer(TInfoCustomer customer,
			String cardNo, String cardType, ManagerLog managerLog) {
		log.info("call checkIdlockCustomer,PARAMETER:{}{}{}{}", new Object[]{customer, cardNo,
				cardType, managerLog});
		Response<String> r = new Response<String>();
		try {
			r.setResult(customerManager.doCloseCustomer(customer, cardNo,
					cardType, managerLog));
			log.info(
					"success to checkIdlockCustomer,PARAMETER:{}{}{}{}, RESULT:{}",
                    new Object[]{customer, cardNo, cardType, managerLog, r});
		} catch (BizException e) {
			r.setErrorCode(e.getCode());
			r.setErrorMsg(e.getMessage());
			log.info("failed to doCloseCustomer,PARAMETER:{}{}{}{}, RESULT:{}",
                    new Object[]{customer, cardNo, cardType, managerLog, r});
		} catch (Exception e) {
			log.error("failed to doCloseCustomer,PARAMETER:{}{}{}{}, CAUSE:{}",
                    new Object[]{customer, cardNo, cardType, managerLog,
					Throwables.getStackTraceAsString(e)});
			r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return r;
	}

	@Override
	public Response<String> checkIdlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog) {
		log.info("call checkIdlockCustomer,PARAMETER:{} {}", accountInfo,
				managerLog);
		Response<String> r = new Response<String>();
		try {
			r.setResult(customerManager.doLockCustomer(accountInfo, managerLog,
					true));
			log.info(
					"success to checkIdlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (BizException e) {
			r.setErrorCode(e.getCode());
			r.setErrorMsg(e.getMessage());
			log.info(
					"failed to checkIdlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (Exception e) {
			log.error(
					"failed to checkIdlockCustomer,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return r;
	}

	@Override
	public Response<String> unCheckIdlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog) {
		log.info("call unCheckIdlockCustomer,PARAMETER:{} {}", accountInfo,
				managerLog);
		Response<String> r = new Response<String>();
		try {
			r.setResult(customerManager.doUnCheckIdlockCustomer(accountInfo,
					managerLog));
			log.info(
					"success to unCheckIdlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (BizException e) {
			r.setErrorCode(e.getCode());
			r.setErrorMsg(e.getMessage());
			log.info(
					"failed to unCheckIdlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (Exception e) {
			log.error(
					"failed to unCheckIdlockCustomer,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return r;
	}

	@Override
	public Response<String> checkIdUnlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog) {
		log.info("call checkIdUnlockCustomer,PARAMETER:{} {}", accountInfo,
				managerLog);
		Response<String> r = new Response<String>();
		try {
			r.setResult(customerManager.doCheckIdUnlockCustomer(accountInfo,
					managerLog));
			log.info(
					"success to checkIdUnlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (BizException e) {
			r.setErrorCode(e.getCode());
			r.setErrorMsg(e.getMessage());
			log.info(
					"failed to checkIdUnlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (Exception e) {
			log.error(
					"failed to checkIdUnlockCustomer,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return r;
	}

	@Override
	public Response<String> unCheckIdUnlockCustomer(AccountInfo accountInfo,
			ManagerLog managerLog) {
		log.info("call unCheckIdUnlockCustomer,PARAMETER:{} {}", accountInfo,
				managerLog);
		Response<String> r = new Response<String>();
		try {
			r.setResult(customerManager.doUnCheckIdUnlockCustomer(accountInfo,
					managerLog));
			log.info(
					"success to unCheckIdUnlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (BizException e) {
			r.setErrorCode(e.getCode());
			r.setErrorMsg(e.getMessage());
			log.info(
					"failed to unCheckIdUnlockCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, r});
		} catch (Exception e) {
			log.error(
					"failed to unCheckIdUnlockCustomer,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return r;
	}

	@Override
	public Response<String> activationCustomer(AccountInfo accountInfo,
			ManagerLog managerLog) {
		log.info("call activationCustomer,PARAMETER:{} {}", accountInfo,
				managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.doActivationCustomer(
					accountInfo, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to activationCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info("failed to activationCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
		} catch (Exception e) {
			log.error("failed to activationCustomer,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return result;
	}
	@Override
	public Response<String> loginCustomer(AccountInfo accountInfo,
			LoginInfo loginInfo) {
		log.info("call loginCustomer,PARAMETER:{} {}", accountInfo, loginInfo);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.checkLoginPwd(accountInfo,
					loginInfo);
			result.setResult(resultCode);
			log.info("success to loginCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, loginInfo, result});
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info("failed to loginCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, loginInfo, result});
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info("failed to loginCustomer,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, loginInfo, result});
		} catch (Exception e) {
			log.error("failed to loginCustomer,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, loginInfo, Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return result;
	}

	@Override
	public Response<String> resetLoginPasswd(AccountInfo accountInfo,
			ManagerLog managerLog) {
		log.info("call resetLoginPasswd,PARAMETER:{} {}", accountInfo,
				managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.resetLoginPasswd(accountInfo,
					managerLog);
			result.setResult(resultCode);
			log.info("success to resetLoginPasswd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info("failed to resetLoginPasswd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info("failed to resetLoginPasswd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error("failed to resetLoginPasswd,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
	}

	@Override
	public Response<String> modfiyLoginPasswdWithoutOldPwd(
			AccountInfo accountInfo, ManagerLog managerLog) {
		log.info("call modfiyLoginPasswdWithoutOldPwd,PARAMETER:{} {}",
				accountInfo, managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.modfiyLoginPasswdWithoutOldPwd(
					accountInfo, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to modfiyLoginPasswdWithoutOldPwd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to modfiyLoginPasswdWithoutOldPwd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to modfiyLoginPasswdWithoutOldPwd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to modfiyLoginPasswdWithoutOldPwd,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}

	}

	@Override
	public Response<String> changeSecrurityQuestionAndAnswer(
			AccountInfo accountInfo, ManagerLog managerLog,
			SecurityQuestionInfo securityQuestionInfo) {
		log.info("call changeSecrurityQuestionAndAnswer, parameter:{} {} {}",
                new Object[]{accountInfo, managerLog, securityQuestionInfo});
		Response<String> r = new Response<String>();
		try {
			r.setResult(customerManager.changeSecrurityQuestionAndAnswer(
                    accountInfo, managerLog, securityQuestionInfo));
			log.info(
					"success to changeSecrurityQuestionAndAnswer, parameter:{} {} {}, result:{}",
                    new Object[]{accountInfo, managerLog, securityQuestionInfo, r});
		} catch (BizException e) {
			r.setErrorCode(e.getCode());
			r.setErrorMsg(e.getMessage());
			log.info(
					"failed to changeSecrurityQuestionAndAnswer, parameter:{} {} {}, result:{}",
                    new Object[]{accountInfo, managerLog, securityQuestionInfo, r});
		} catch (Exception e) {
			log.error(
					"failed to changeSecrurityQuestionAndAnswer, parameter:{} {} {}, cause:{}",
                    new Object[]{accountInfo, managerLog, securityQuestionInfo,
					Throwables.getStackTraceAsString(e)});
			r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
		}
		return r;
	}

	@Override
	public Response<String> modfiyLoginPasswdWithOldPwd(
			AccountInfo accountInfo, ManagerLog managerLog) {
		log.info("call modfiyLoginPasswdWithOldPwd,PARAMETER:{} {}",
				accountInfo, managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.modfiyLoginPasswdWithOldPwd(
					accountInfo, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to modfiyLoginPasswdWithOldPwd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to modfiyLoginPasswdWithOldPwd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to modfiyLoginPasswdWithOldPwd,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{accountInfo, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to modfiyLoginPasswdWithOldPwd,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{accountInfo, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}

	}
	@Override
	public Response<String> LostCustomerByUnifyId(TInfoCustomer customer,
			ManagerLog managerLog) {
		log.info("call LostCustomerByUnifyId,PARAMETER:{} {}",
				customer, managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.LostCustomerByUnifyId(customer, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to LostCustomerByUnifyId,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to LostCustomerByUnifyId,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to LostCustomerByUnifyId,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to LostCustomerByUnifyId,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
	}
	
	@Override
	public Response<String> unLostCustomerByUnifyId(TInfoCustomer customer,
			ManagerLog managerLog) {
		log.info("call unLostCustomerByUnifyId,PARAMETER:{} {}",
				customer, managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.unLostCustomerByUnifyId(customer, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to unLostCustomerByUnifyId,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to unLostCustomerByUnifyId,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to unLostCustomerByUnifyId,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to unLostCustomerByUnifyId,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
	}

	@Override
	public Response<String> mobilePhoneBinding(TInfoCustomer customer,
			ManagerLog managerLog) {
		log.info("call mobilePhoneBinding,PARAMETER:{} {}",
				customer, managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.mobilePhoneBinding(customer, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to mobilePhoneBinding,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to mobilePhoneBinding,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to mobilePhoneBinding,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to mobilePhoneBinding,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
	}
	
	
	@Override
	public Response<String> unMobilePhoneBinding(TInfoCustomer customer,
			ManagerLog managerLog) {
		log.info("call unMobilePhoneBinding,PARAMETER:{} {}",
				customer, managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.unMobilePhoneBinding(customer, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to unMobilePhoneBinding,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to unMobilePhoneBinding,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to unMobilePhoneBinding,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{customer, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to unMobilePhoneBinding,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
	}

	@Override
	public Response<String> modfiyCustomerInfo(
			TInfoCustomer tInfoCustomer, ManagerLog managerLog) {
		log.info("call modfiyCustomerInfo,PARAMETER:{} {}",
				tInfoCustomer, managerLog);
		Response<String> result = new Response<String>();
		try {
			String resultCode = customerManager.modfiyCustomerInfo(
					tInfoCustomer, managerLog);
			result.setResult(resultCode);
			log.info(
					"success to modfiyCustomerInfo,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{tInfoCustomer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to modfiyCustomerInfo,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{tInfoCustomer, managerLog, result});
			return result;
		} catch (SubmitBizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to modfiyCustomerInfo,PARAMETER:{} {}, RESULT:{}",
                    new Object[]{tInfoCustomer, managerLog, result});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to modfiyCustomerInfo,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{tInfoCustomer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}

	}

}
