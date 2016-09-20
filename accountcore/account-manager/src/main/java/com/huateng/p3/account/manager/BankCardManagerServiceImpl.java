package com.huateng.p3.account.manager;



import java.sql.Timestamp;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.manager.inner.AccountManager;
import com.huateng.p3.account.manager.inner.BankCardManeger;
import com.huateng.p3.account.manager.inner.CustomerManager;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.component.Response;

/**
 * @author cmt
 * 
 */
@Service
@Slf4j
public class BankCardManagerServiceImpl implements BankCardManagerService {

	@Autowired
	private AccountManager accountManager;

	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private BankCardManeger bankCardManager;
	
	@Override
	public Response<String> bindBankCard(TInfoBankcard bankCard,String bindingType, TInfoCustomer customer,
			ManagerLog managerLog) {
		log.info("call bindBankCard,PARAMETER:{}  {}",
				customer,  managerLog);
		Response<String> result = new Response<String>();
		// 默认开通现金账户
		try {
			result.setResult(bankCardManager.doBindBankCard(bankCard,bindingType, customer, managerLog));
			log.info(
					"success to bindBankCard,PARAMETER:{} {} {}, RESULT:{}",
                    new Object[]{bankCard,customer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to bindBankCard,PARAMETER:{}  {}, RESULT:{}",
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
					"failed to bindBankCard,PARAMETER:{}  {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			return result;

		} catch (CannotAcquireLockException e) {

			result.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
			log.error(
					"failed to bindBankCard,PARAMETER:{}  {}, CAUSE:{}",
                    new Object[]{customer,  managerLog,
					Throwables.getStackTraceAsString(e)});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to bindBankCard,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
		
	}

	@Override
	public Response<String> unbindBankCardByBankCardNo(TInfoBankcard bankCard,
			TInfoCustomer customer, ManagerLog managerLog) {
		log.info("call bindBankCard,PARAMETER:{}  {}",
				customer,  managerLog);
		Response<String> result = new Response<String>();
		// 默认开通现金账户
		try {
			result.setResult(bankCardManager.unbindBankCardByBankCardNo(bankCard, customer, managerLog));
			log.info(
					"success to unbindBankCardByBankCardNo,PARAMETER:{} {} {}, RESULT:{}",
                    new Object[]{bankCard,customer, managerLog, result});
			return result;
		} catch (BizException e) {
			result.setErrorCode(e.getCode());
			result.setErrorMsg(e.getMessage());
			log.info(
					"failed to unbindBankCardByBankCardNo,PARAMETER:{}  {}, RESULT:{}",
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
					"failed to unbindBankCardByBankCardNo,PARAMETER:{}  {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			return result;

		} catch (CannotAcquireLockException e) {

			result.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
			log.error(
					"failed to unbindBankCardByBankCardNo,PARAMETER:{}  {}, CAUSE:{}",
                    new Object[]{customer,  managerLog,
					Throwables.getStackTraceAsString(e)});
			return result;
		} catch (Exception e) {
			log.error(
					"failed to unbindBankCardByBankCardNo,PARAMETER:{} {}, CAUSE:{}",
                    new Object[]{customer, managerLog,
					Throwables.getStackTraceAsString(e)});
			result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
			result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
			return result;
		}
	}


	

}
