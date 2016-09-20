package com.huateng.p3.account.manager.inner;

import java.util.List;

import com.huateng.p3.account.persistence.*;
import com.huateng.p3.account.persistence.models.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.AccountResultObject;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.LogOnlinePaymentObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.SecurityResultObject;
import com.huateng.p3.account.common.bizparammodel.TxnQueryObj;
import com.huateng.p3.account.common.enummodel.BusinessOutterType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.common.enummodel.RiskBlackType;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.AccountResultGenComponent;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.component.CustomerResultGenComponent;
import com.huateng.p3.account.component.OnlinePaymentComponent;
import com.huateng.p3.account.component.SecurityResultGenComponent;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-11
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
@Component
@Slf4j
public class QueryManager {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TRealnameApplyMapper tRealnameApplyMapper;

    @Autowired
    private TInfoAccountMapper tInfoAccountMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private TxnCheckService txnCheckService;

    @Autowired
    private CustomerResultGenComponent customerResultGenComponent;

    @Autowired
    private AccountResultGenComponent accountResultGenComponent;

    @Autowired
    private TLogOnlinePaymentMapper tLogOnlinePaymentMapper;

    @Autowired
    private TLogOnlinePaymentHisMapper tLogOnlinePaymentHisMapper;

    @Autowired
    private TInfoOrgMapper orgMapper;

    @Autowired
    private OnlinePaymentComponent onlinePaymentComponent;

    @Autowired
    private RiskService riskService;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private TDictCodeMapper tDictCodeMapper;

    @Autowired
    private SecurityResultGenComponent securityResultGenComponent;

    @Autowired
    private TInfoCustomerMapper tInfoCustomerMapper;

    @Autowired
    private TInfoBankcardMapper tInfoBankcardMapper;
    
    @Autowired
    private TInfoAccountBankCardMapper tInfoAccountBankCardMapper;

    /**
     * 获取客户信息
     *
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public CustomerResultObject queryCustomerInfo(AccountInfo accountInfo) {
        // 得到账户信息
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        // 得到客户信息
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());
        // 设置高级实名的身份证
        customerService.setRealnameCustomer(tInfoCustomer);
        CustomerResultObject customerResultObject = customerResultGenComponent.genCustomerResultObject(account, tInfoCustomer, null, null);
        //获取积分账户余额
        TInfoAccount tInfoIntegralAccount = accountService.getIntegralAccount(tInfoCustomer.getCustomerNo(), KeyType.CUSTOMER);
        customerResultObject.setIntegral(tInfoIntegralAccount.getBalance());
        return customerResultObject;

    }


    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public SecurityResultObject queryCustomerSecurityInfo(AccountInfo accountInfo) {
        // 得到账户信息
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        // 得到客户信息
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());

        TDictCode tDictCode = tDictCodeMapper.querySecurityQuestionsbyCodeValue(tInfoCustomer.getQuestion());
        return securityResultGenComponent.genSecurityResultObject(account, tInfoCustomer, tDictCode);

    }


    /**
     * 子账户查询
     *
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<TInfoSubaccount> getSubAccountList(AccountInfo accountInfo) {
        TInfoAccount tInfoAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        return accountService.getSubAccountList(tInfoAccount);
    }

    /**
     * 账户余额查询
     *
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<AccountResultObject> getAccountBalance(AccountInfo accountInfo) {
        // 得到账户信息
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        // 得到客户信息
        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());

        txnCheckService.checkCustomerQueryBalance(customer);

        List<TInfoAccount> accounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(customer.getCustomerNo(), null);
        return accountResultGenComponent.genAccountListResultObject(accounts);

    }

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public CustomerResultObject doCheckCardHandingInfo(AccountInfo accountInfo) throws BizException {
        // 得到账户信息
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        // 得到客户信息
        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());
        // 设置高级实名的身份证
        customerService.setRealnameCustomer(customer);
        // 查询是否有绑卡信息
        TrueOrFalse cardHanding = customerService.findCardHanding(account.getCustomerNo());
        TrueOrFalse shortcutCard = customerService.findShortcutCard(customer.getUnifyId());

        return customerResultGenComponent.genCustomerResultObject(account, customer, cardHanding, shortcutCard);

    }

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<TRealnameApply> doQueryRealnameAuthenticationStatusDetails(AccountInfo accountInfo, ManagerLog managerLog) throws BizException {
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());
        txnCheckService.checkTxnType(TxnInnerType.CUSTOMER_REALNAME_STATUS_QUERY_TXN_TYPE.getTxnInnerType(), TxnType.TXN_MGM.getTxnCode());
        orgService.getValidOrg(managerLog.getAcceptOrgCode(), TxnInnerType.CUSTOMER_REALNAME_STATUS_QUERY_TXN_TYPE.getTxnInnerType(), null);
        return tRealnameApplyMapper.queryAuthenticationInfo(customer.getCustomerNo());

    }

    /**
     * 当日交易查询
     *
     * @param accountInfo
     * @param txnQueryObj
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<LogOnlinePaymentObject> getOnlinePaymentList(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
        // 检测账户信息
        TInfoAccount account = checkTxnQuery(accountInfo, txnQueryObj);
        //查询交易
        List<TLogOnlinePayment> tLogOnlinePay = tLogOnlinePaymentMapper.findTxnDayResult(account.getAccountNo(),
                txnQueryObj.getBusinessType(), txnQueryObj.getTxnChannel(), txnQueryObj.getAcceptTransSeqNo(),
                txnQueryObj.getSupplyOrgCode(), txnQueryObj.getTerminalNo(), (txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(), txnQueryObj.getPage() * txnQueryObj.getPageSize());
        Integer total = 0;
        //查询条数
        if (tLogOnlinePay.size() != 0) {
            total = tLogOnlinePaymentMapper.findTxnDayCount(account.getAccountNo(),
                    txnQueryObj.getBusinessType(), txnQueryObj.getTxnChannel(), txnQueryObj.getAcceptTransSeqNo(),
                    txnQueryObj.getSupplyOrgCode(), txnQueryObj.getTerminalNo(), (txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(), txnQueryObj.getPage() * txnQueryObj.getPageSize());
        }

        //封装返回结果
        return onlinePaymentComponent.genLogOnlinePaymentResultObject(tLogOnlinePay, txnQueryObj, total);
    }

    /**
     * 历史交易查询
     *
     * @param accountInfo
     * @param txnQueryObj
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<LogOnlinePaymentObject> getOnlinePaymentHisList(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
        // 检测账户信息
        TInfoAccount account = checkTxnQuery(accountInfo, txnQueryObj);


        List<TLogOnlinePaymentHis> tLogOnlinePayHis = tLogOnlinePaymentHisMapper.findTxnHisResult(account.getAccountNo(),
                txnQueryObj.getBusinessType(), txnQueryObj.getTxnChannel(), txnQueryObj.getAcceptTransSeqNo(),
                txnQueryObj.getSupplyOrgCode(), txnQueryObj.getTerminalNo(), txnQueryObj.getStartDate(), txnQueryObj.getEndDate(), (txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(), txnQueryObj.getPage() * txnQueryObj.getPageSize());
        Integer total = 0;
        //查询条数
        if (tLogOnlinePayHis.size() != 0) {
            total = tLogOnlinePaymentHisMapper.findTxnHisCount(account.getAccountNo(),
                    txnQueryObj.getBusinessType(), txnQueryObj.getTxnChannel(), txnQueryObj.getAcceptTransSeqNo(),
                    txnQueryObj.getSupplyOrgCode(), txnQueryObj.getTerminalNo(), txnQueryObj.getStartDate(), txnQueryObj.getEndDate(), (txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(), txnQueryObj.getPage() * txnQueryObj.getPageSize());
        }
        //封装返回结果
        return onlinePaymentComponent.genLogOnlinePaymentHisResultObject(tLogOnlinePayHis, txnQueryObj, total);

    }

    /**
     * 交易查询，当日历史都查询
     *
     * @param accountInfo
     * @param txnQueryObj
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<LogOnlinePaymentObject> getOnlinePayment(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
        // 获取账户信息
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        checkTxnQueryParam(txnQueryObj);
        //查询当日交易
        List<TLogOnlinePayment> tLogOnlinePay = tLogOnlinePaymentMapper.findTxnDayResult(account.getAccountNo(),
                txnQueryObj.getBusinessType(), txnQueryObj.getTxnChannel(), txnQueryObj.getAcceptTransSeqNo(),
                txnQueryObj.getSupplyOrgCode(), txnQueryObj.getTerminalNo(), (txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(), txnQueryObj.getPage() * txnQueryObj.getPageSize());

        //没有查询到 查询历史交易表
        if (tLogOnlinePay.size() == 0) {
            List<TLogOnlinePaymentHis> tLogOnlinePayHis = tLogOnlinePaymentHisMapper.findTxnHisResult(account.getAccountNo(),
                    txnQueryObj.getBusinessType(), txnQueryObj.getTxnChannel(), txnQueryObj.getAcceptTransSeqNo(),
                    txnQueryObj.getSupplyOrgCode(), txnQueryObj.getTerminalNo(), txnQueryObj.getStartDate(), txnQueryObj.getEndDate(), (txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(), txnQueryObj.getPage() * txnQueryObj.getPageSize());
            //封装返回结果
            return onlinePaymentComponent.genLogOnlinePaymentHisResultObject(tLogOnlinePayHis, txnQueryObj, null);
        }

        return onlinePaymentComponent.genLogOnlinePaymentResultObject(tLogOnlinePay, txnQueryObj, null);


    }


    private TInfoAccount checkTxnQuery(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
        TInfoAccount account = new TInfoAccount();
        //如果传入了账户信息，说明是账户查询
        if (accountInfo != null) {
            account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        }
        //如果没有传入账户信息，说明是商户查询
        if (accountInfo == null) {
            if (Strings.isNullOrEmpty(txnQueryObj.getSupplyOrgCode()) && Strings.isNullOrEmpty(txnQueryObj.getTerminalNo())) {
                throw new BizException(BussErrorCode.ERROR_CODE_500100.getErrorcode(),
                        BussErrorCode.ERROR_CODE_500100.getErrordesc());
            }
            //设置商户名
            TInfoOrg org = orgMapper.selectByOrgCode(txnQueryObj.getSupplyOrgCode());
            txnQueryObj.setMerchantName(org.getOrgFname());
        }

        return account;
    }

    /**
     * 查询密保问题列表
     *
     * @param managerLog
     * @return
     */
    public List<TDictCode> querySecurityQuestions(ManagerLog managerLog) {
        List<TDictCode> list = tDictCodeMapper.querySecurityQuestions();
        if (list.size() == 0) {
            //无匹配信息
            throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500101.getErrordesc());
        }
        return list;
    }
    
    /**
     * 查询国籍、职业等系统数据列表
     *
     * @param managerLog
     * @return
     */
    public List<TDictCode> querySecuritySysData(ManagerLog managerLog,String dictEng) {
        List<TDictCode> list = tDictCodeMapper.querySecuritySysData(dictEng);
        if (list.size() == 0) {
            //无匹配信息
            throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500101.getErrordesc());
        }
        return list;
    }

    public String doQueryRiskBlack(AccountInfo accountInfo, ManagerLog managerLog) {
        //受理机构检查
        orgService.getValidOrg(managerLog.getAcceptOrgCode(), TxnInnerType.TXN_TYPE_101231.getTxnInnerType(),
                OrgType.ORG_TYPE_ORG.getOrgtype());
        TInfoAccount tInfoAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        //检查黑名单客户
        customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        //检查黑名单银行卡
        riskService.queryRiskBlack(managerLog.getBankCardNo(), RiskBlackType.BLACK_TYPE_BANKCARD.getTypeCode());
        //检查黑名单身份证
        riskService.queryRiskBlack(managerLog.getIdno(), RiskBlackType.BLACK_TYPE_IDNO.getTypeCode());
        return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }

    private void checkTxnQueryParam(TxnQueryObj txnQueryObj) {

        //受理机构和流水号必填
        if (Strings.isNullOrEmpty(txnQueryObj.getSupplyOrgCode()) || Strings.isNullOrEmpty(txnQueryObj.getAcceptTransSeqNo())) {
            throw new BizException(BussErrorCode.ERROR_CODE_500100.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500100.getErrordesc());
        }

    }

    public String doCheckOrgPayment(ManagerLog managerLog) {
        //外部交易类型转内部交易类型
        String innerTxnType = cacheComponent.getInnerTxnType(managerLog.getBussinessType()).getInteriorTransCode();
        //受理机构检查
        orgService.getValidOrg(managerLog.getAcceptOrgCode(), innerTxnType,
                OrgType.ORG_TYPE_ORG.getOrgtype());
        return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }

    public TInfoCustomer queryMobilePhoneBinding(TInfoCustomer customer) {
        TInfoCustomer oldCustomer = tInfoCustomerMapper
                .findCustomerByUnifyIdForUpdate(customer.getUnifyId());
        if (oldCustomer == null) {
            throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500101.getErrordesc());
        }
        return oldCustomer;
    }

    public List<TInfoBankcard> queryBankCardBinding(TInfoBankcard tInfoBankcard){
	List<TInfoBankcard> newTInfoBankcard=null;
	 newTInfoBankcard =tInfoBankcardMapper.findBankCardBinding(tInfoBankcard);
	if(newTInfoBankcard==null||newTInfoBankcard.size()==0){
		throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
				BussErrorCode.ERROR_CODE_500101.getErrordesc());
	}
	return newTInfoBankcard;
}
    
    public List<TInfoAccountBankCard> queryAccountBankCardBinding(TInfoAccountBankCard tInfoAccountBankCard){
    	List<TInfoAccountBankCard> newTInfoBankcard=null;
    	 newTInfoBankcard =tInfoAccountBankCardMapper.findBankListCardByAccountNo(tInfoAccountBankCard.getFundAccountNo());
    	if(newTInfoBankcard==null||newTInfoBankcard.size()==0){
    		throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
    				BussErrorCode.ERROR_CODE_500101.getErrordesc());
    	}
    	return newTInfoBankcard;
    }

	@Autowired
	private TLogAccountPaymentMapper tLogAccountPaymentMapper;

	/**
	 * 充值交易查询
	 * 
	 * @param accountInfo
	 * @param paymentQueryObj
	 * @return
	 */
	@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
	public Paging<TLogAccountPayment> getAccountPaymentList(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
		// 检测账户信息
		TInfoAccount account = checkTxnQuery(accountInfo, txnQueryObj);
		transCodeConvert(txnQueryObj);

		List<TLogAccountPayment> tLogAccountPayment = tLogAccountPaymentMapper.findPaymentResult(
						account.getAccountNo(),
						txnQueryObj.getBusinessType(),
						txnQueryObj.getTxnType(),
						txnQueryObj.getTxnChannel(),
						txnQueryObj.getStartDate(),
						txnQueryObj.getEndDate(),
						(txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(),
						txnQueryObj.getPage() * txnQueryObj.getPageSize());
		Long total = 0l;
		// 查询条数
		if (tLogAccountPayment.size() != 0) {
			total = tLogAccountPaymentMapper.findPaymentCount(account.getAccountNo(), txnQueryObj.getBusinessType(),
					txnQueryObj.getTxnType(),
					txnQueryObj.getTxnChannel(),
					txnQueryObj.getStartDate(),
					txnQueryObj.getEndDate());
		}

		if (tLogAccountPayment == null || tLogAccountPayment.size() == 0) {
			throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
					BussErrorCode.ERROR_CODE_500101.getErrordesc());
		}
		Paging<TLogAccountPayment> r = new Paging<TLogAccountPayment>();
		r.setData(tLogAccountPayment);
		r.setTotal(total);
		return r;

	}

	/**
	 * 收支明细查询
	 * @param accountInfo
	 * @param txnQueryObj
	 * @return
	 */
	@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
	public Paging<TLogAccountPayment> getAccountPaymentDetailList(AccountInfo accountInfo, TxnQueryObj txnQueryObj) {
		// 检测账户信息
		TInfoAccount account = checkTxnQuery(accountInfo, txnQueryObj);

		List<TLogAccountPayment> tLogAccountPayment = tLogAccountPaymentMapper.findPaymentResult(
						account.getAccountNo(),
						txnQueryObj.getBusinessType(),
						txnQueryObj.getTxnType(),
						txnQueryObj.getTxnChannel(),
						txnQueryObj.getStartDate(),
						txnQueryObj.getEndDate(),
						(txnQueryObj.getPage() - 1) * txnQueryObj.getPageSize(),
						txnQueryObj.getPage() * txnQueryObj.getPageSize());
		Long total = 0l;
		// 查询条数
		if (tLogAccountPayment.size() != 0) {
			total = tLogAccountPaymentMapper.findPaymentCount(account.getAccountNo(), txnQueryObj.getBusinessType(),
					txnQueryObj.getTxnType(),
					txnQueryObj.getTxnChannel(),
					txnQueryObj.getStartDate(),
					txnQueryObj.getEndDate());
		}

		if (tLogAccountPayment == null || tLogAccountPayment.size() == 0) {
			throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
					BussErrorCode.ERROR_CODE_500101.getErrordesc());
		}
		Paging<TLogAccountPayment> r = new Paging<TLogAccountPayment>();
		r.setData(tLogAccountPayment);
		r.setTotal(total);
		return r;

	}
	
	
	/**
	 * 查询类型转内部交易类型
	 * 
	 * @param txnQueryObj
	 */
	private void transCodeConvert(TxnQueryObj txnQueryObj) {
		String businessType = "";
		switch (txnQueryObj.getQueryType()) {
		case TxnQueryObj.chargeQueryType:
			businessType = BusinessOutterType.BUS_TYPE_121010.getBusinessOutterType();// 查询类型转为充值
			break;
		case TxnQueryObj.cashQueryType:
			businessType = BusinessOutterType.BUS_TYPE_181000.getBusinessOutterType();// 查询类型转为提现
			break;
		default:
			break;
		}

		String innerTxnType = cacheComponent.getInnerTxnType(businessType).getInteriorTransCode();
		log.info("innerTxnType:{}; businessType:{};", new Object[] { innerTxnType, businessType });
		if (Strings.isNullOrEmpty(innerTxnType) || Strings.isNullOrEmpty(businessType)) {
            throw new BizException(BussErrorCode.ERROR_CODE_500100.getErrorcode(), BussErrorCode.ERROR_CODE_500100.getErrordesc());
        }
		txnQueryObj.setTxnType(innerTxnType);
		txnQueryObj.setBusinessType(businessType);
	}
}
