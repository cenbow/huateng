package com.huateng.p3.account.manager;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.AccountResultObject;
import com.huateng.p3.account.common.bizparammodel.LogOnlinePaymentObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.TxnQueryObj;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.manager.inner.QueryManager;
import com.huateng.p3.account.persistence.models.TInfoSubaccount;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-5
 * Time: 上午8:47
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class AccountQueryServiceImpl implements AccountQueryService {
    
    @Autowired
    private QueryManager queryManager;

	@Override
	public Response<List<TInfoSubaccount>> querysubaccount(AccountInfo accountInfo) {
		log.info("call querysubaccount,PARAMETER:{}",accountInfo);   
		Response<List<TInfoSubaccount>> res = new Response<List<TInfoSubaccount>>();
        try {
        	res.setResult(queryManager.getSubAccountList(accountInfo));
        	log.info("success to querysubaccount,PARAMETER:{}, RESULT:{}",accountInfo,res);
            return res;
        } catch (BizException ex) {
            res.setErrorCode(ex.getCode());
            res.setErrorMsg(ex.getMessage());
            log.info("failed to querysubaccount,PARAMETER:{}, RESULT:{}",accountInfo,res);
            return res;
        } catch (Exception ex) {
        	log.error("failed to querysubaccount,PARAMETER:{}, CAUSE:{}",accountInfo,Throwables.getStackTraceAsString(ex));
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return res;
        }
	}

	@Override
	public Response<List<AccountResultObject>> queryAccountBalance(AccountInfo accountInfo) {
		log.info("call queryAccountBalance,PARAMETER:{}",accountInfo);
		Response<List<AccountResultObject>> res = new Response<List<AccountResultObject>>();
        try {
        	res.setResult(queryManager.getAccountBalance(accountInfo));
        	log.info("success to queryAccountBalance,PARAMETER:{}, RESULT:{}",accountInfo,res);     
            return res;
        } catch (BizException ex) {
            res.setErrorCode(ex.getCode());
            res.setErrorMsg(ex.getMessage());
            log.info("failed to queryAccountBalance,PARAMETER:{}, RESULT:{}",accountInfo,res);     
            return res;
        } catch (Exception ex) {
        	log.error("failed to queryAccountBalance,PARAMETER:{}, CAUSE:{}",accountInfo,Throwables.getStackTraceAsString(ex));
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return res;
        }
	}

	@Override
	public Response<List<TRealnameApply>> queryRealnameAuthenticationStatusDetails(
			AccountInfo accountInfo, ManagerLog managerLog) {
		log.info("call queryRealnameAuthenticationStatusDetails,PARAMETER:{} {}",accountInfo,managerLog);  
		Response<List<TRealnameApply>> result = new Response<List<TRealnameApply>>();
		try
		{
			result.setResult(queryManager.doQueryRealnameAuthenticationStatusDetails(accountInfo, managerLog));
			log.info("success to queryRealnameAuthenticationStatusDetails,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,managerLog,result});
		}catch (BizException e) {
            result.setErrorCode(e.getCode());
            result.setErrorMsg(e.getMessage());     
            log.info("failed to queryRealnameAuthenticationStatusDetails,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo,managerLog,result});
        } catch (Exception e) {
        	log.error("failed to queryRealnameAuthenticationStatusDetails,PARAMETER:{} {}, CAUSE:{}",new Object[]{accountInfo,managerLog,Throwables.getStackTraceAsString(e)});
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            result.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());          
        }
        return result;
	}

	@Override
	public Response<List<LogOnlinePaymentObject>> queryDayTxn(
			AccountInfo accountInfo, TxnQueryObj txnQueryObj) {		
		log.info("call queryDayTxn,PARAMETER:{} {}",accountInfo, txnQueryObj);   
		Response<List<LogOnlinePaymentObject>> res = new Response<List<LogOnlinePaymentObject>>();
        try {
        	res.setResult(queryManager.getOnlinePaymentList(accountInfo, txnQueryObj));
        	log.info("success to queryDayTxn,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo, txnQueryObj, res});
            return res;
        } catch (BizException ex) {
            res.setErrorCode(ex.getCode());
            res.setErrorMsg(ex.getMessage());
            log.info("failed to queryDayTxn,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo, txnQueryObj, res});
            return res;
        } catch (Exception ex) {
        	log.error("failed to queryDayTxn,PARAMETER:{} {}, CAUSE:{}",new Object[]{accountInfo, txnQueryObj, Throwables.getStackTraceAsString(ex)});
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return res;
        }
	}

	@Override
	public Response<List<LogOnlinePaymentObject>> queryHisTxn(
			AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
		log.info("call queryHisTxn,PARAMETER:{} {}",accountInfo ,txnQueryObj);   
		Response<List<LogOnlinePaymentObject>> res = new Response<List<LogOnlinePaymentObject>>();
        try {
        	res.setResult(queryManager.getOnlinePaymentHisList(accountInfo, txnQueryObj));
        	log.info("success to queryHisTxn,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo, txnQueryObj, res});
            return res;
        } catch (BizException ex) {
            res.setErrorCode(ex.getCode());
            res.setErrorMsg(ex.getMessage());
            log.info("failed to queryHisTxn,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo, txnQueryObj, res});
            return res;
        } catch (Exception ex) {
        	log.error("failed to queryHisTxn,PARAMETER:{} {}, CAUSE:{}",new Object[]{accountInfo, txnQueryObj, Throwables.getStackTraceAsString(ex)});
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return res;
        }
	}

	@Override
	public Response<List<LogOnlinePaymentObject>> queryTxnBySeqNo(
			AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
		log.info("call queryTxnBySeqNo,PARAMETER:{} {}",accountInfo ,txnQueryObj);   
		Response<List<LogOnlinePaymentObject>> res = new Response<List<LogOnlinePaymentObject>>();
        try {
        	res.setResult(queryManager.getOnlinePayment(accountInfo, txnQueryObj));
        	log.info("success to queryTxnBySeqNo,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo, txnQueryObj, res});
            return res;
        } catch (BizException ex) {
            res.setErrorCode(ex.getCode());
            res.setErrorMsg(ex.getMessage());
            log.info("failed to queryTxnBySeqNo,PARAMETER:{} {}, RESULT:{}",new Object[]{accountInfo, txnQueryObj, res});
            return res;
        } catch (Exception ex) {
        	log.error("failed to queryTxnBySeqNo,PARAMETER:{} {}, CAUSE:{}",new Object[]{accountInfo, txnQueryObj, Throwables.getStackTraceAsString(ex)});
            res.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            res.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            return res;
        }
	}

	@Override
	public Response<Paging<TLogAccountPayment>> queryAccountPaymentRecord(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
		log.info("call queryChargeRecord,PARAMETER:{} {}",new Object[]{accountInfo, txnQueryObj});
		Response<Paging<TLogAccountPayment>> r = new Response<Paging<TLogAccountPayment>>();
        try {
        	//cacheComponent
        	Paging<TLogAccountPayment> result = queryManager.getAccountPaymentList(accountInfo, txnQueryObj);
        	r.setResult(result);
			log.info("success to queryChargeRecord,PARAMETER:{} {}, RESULT:{}", new Object[] { accountInfo, txnQueryObj, r });   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to queryChargeRecord,PARAMETER:{} {}, RESULT:{}",new Object[]{ accountInfo, txnQueryObj, r });  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackChargeCancel,PARAMETER:{} {}, CAUSE:{}",new Object[]{ accountInfo, txnQueryObj, Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackChargeCancel,PARAMETER:{} {}, CAUSE:{}",new Object[]{ accountInfo, txnQueryObj, Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackChargeCancel,PARAMETER:{} {}, CAUSE:{}",new Object[]{ accountInfo, txnQueryObj, Throwables.getStackTraceAsString(e)});
            return r;
        }
	}
	
	
	public Response<Paging<TLogAccountPayment>> queryPaymentDetails(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
		log.info("call queryChargeRecord,PARAMETER:{} {}",new Object[]{accountInfo, txnQueryObj});
		Response<Paging<TLogAccountPayment>> r = new Response<Paging<TLogAccountPayment>>();
        try {
        	//cacheComponent
        	Paging<TLogAccountPayment> result = queryManager.getAccountPaymentDetailList(accountInfo, txnQueryObj);
        	r.setResult(result);
			log.info("success to queryPaymentDetails,PARAMETER:{} {}, RESULT:{}", new Object[] { accountInfo, txnQueryObj, r });   
            return r;
        } catch (BizException e) {
            r.setErrorCode(e.getCode());
            r.setErrorMsg(e.getMessage());
            log.info("failed to queryPaymentDetails,PARAMETER:{} {}, RESULT:{}",new Object[]{ accountInfo, txnQueryObj, r });  
            return r;
        } catch (DataIntegrityViolationException e) {

            if (e.toString().contains("ORA-00001")) {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900003.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900003.getErrordesc());
            } else {
            	r.setErrorCode(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                r.setErrorMsg(BussErrorCode.ERROR_CODE_900007.getErrordesc());
            }
           
            log.error("failed to rollbackChargeCancel,PARAMETER:{} {}, CAUSE:{}",new Object[]{ accountInfo, txnQueryObj, Throwables.getStackTraceAsString(e)});
            return r;

        } catch (CannotAcquireLockException e) {

            r.setErrorCode(BussErrorCode.ERROR_CODE_900006.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_900006.getErrordesc());
            log.error("failed to rollbackChargeCancel,PARAMETER:{} {}, CAUSE:{}",new Object[]{ accountInfo, txnQueryObj, Throwables.getStackTraceAsString(e)});
            return r;
        }catch (Exception e) {
            r.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            r.setErrorMsg(BussErrorCode.ERROR_CODE_999999.getErrordesc());
            log.error("failed to rollbackChargeCancel,PARAMETER:{} {}, CAUSE:{}",new Object[]{ accountInfo, txnQueryObj, Throwables.getStackTraceAsString(e)});
            return r;
        }
	}
}
