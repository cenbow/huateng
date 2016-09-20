package com.huateng.p3.account.manager.inner;



import java.sql.Timestamp;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.AllowBindingCardMaxNum;
import com.huateng.p3.account.common.enummodel.BankCardBingding;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.CardBingdingMethod;
import com.huateng.p3.account.common.enummodel.CustomerIdType;
import com.huateng.p3.account.common.enummodel.CustomerRealname;
import com.huateng.p3.account.common.enummodel.CustomerStatus;
import com.huateng.p3.account.common.enummodel.RegisterType;
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.DateUtil;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.manager.BankCardManagerService;
import com.huateng.p3.account.persistence.TInfoAccountBankCardMapper;
import com.huateng.p3.account.persistence.TInfoAccountMapper;
import com.huateng.p3.account.persistence.TInfoBankcardMapper;
import com.huateng.p3.account.persistence.TInfoCustomerMapper;
import com.huateng.p3.account.persistence.TLogAccountManagementMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogAccountManagement;
import com.huateng.p3.component.Response;
import com.mchange.v1.cachedstore.CachedStore.Manager;

/**
 * @author cmt
 * 
 */
@Component
@Slf4j
public class BankCardManeger {

	@Autowired
    private TInfoCustomerMapper tInfoCustomerMapper;

	@Autowired
    private TxnCheckService txnCheckService;
	
	@Autowired
	private OrgService orgService;
	
	@Autowired
    private CacheComponent cacheComponent;
	
	@Autowired
	private TInfoAccountMapper tInfoAccountMapper;
	    
	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	private TInfoBankcardMapper tInfoBankcardMapper;
	
	@Autowired
	private TInfoAccountBankCardMapper tInfoAccountBankCardMapper;
	
	@Autowired
	private TLogAccountManagementMapper tLogAccountManagementMapper; 
	
	@Autowired
    private SequenceGenerator sequenceGenerator;
	
	 @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
	    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String doBindBankCard(TInfoBankcard bankCard ,String bindingType,TInfoCustomer customer,ManagerLog manager) {
		String txnType = TxnInnerType.TXN_TYPE_101B70.getTxnInnerType();
		TInfoCustomer oldCustomer = (TInfoCustomer) tInfoCustomerMapper
				.findCustomerByUnifyIdForUpdate(customer.getUnifyId());

		if (oldCustomer == null) {
			// 客户不存在
			throw new BizException(BussErrorCode.ERROR_CODE_200010.getErrorcode(), BussErrorCode.ERROR_CODE_200010.getErrorcode());
		}
		// 验证是否是翼支付实名用户
		if (CustomerRealname.CUSTOMER_REALNAME_TRUE.getCustomerRealnameCode().equals(customer.getIsRealname())) {
			
			throw new BizException(BussErrorCode.ERROR_CODE_200137.getErrorcode(), BussErrorCode.ERROR_CODE_200137.getErrorcode());
		}
		
		
		if (!oldCustomer.getIdentifyType().equals(manager.getIdtype())
				|| !TxnCheckService.checkPid(manager.getIdtype(), manager.getIdno(), customer.getIdentifyNo())) {
			// 证件类型和证件号码不一致
			throw new BizException(BussErrorCode.ERROR_CODE_200038.getErrorcode(), BussErrorCode.ERROR_CODE_200038.getErrorcode());
		}
		


		if (oldCustomer.getBlacklistFlag() != null) {
			// 客户黑名单
			throw new BizException(BussErrorCode.ERROR_CODE_200016.getErrorcode(), BussErrorCode.ERROR_CODE_200016.getErrorcode());
		}
		if (!CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode().equals(oldCustomer
				.getCustomerStatus())) {
			// 客户状态不为正常，不能开户
			throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(), BussErrorCode.ERROR_CODE_200011.getErrorcode());
		}

		// 检查注册时填写的手机号
//		if (!oldCustomer.getRegisterType().equals(
//				cus.CUSTOMER_REGISTER_TYPE_VIRTUAL))//虚拟账号
			txnCheckService.checkTeleMobilePhone(oldCustomer);

		txnCheckService.checkCustomerActivationTxn(oldCustomer);// 检查交易合法性

		// 检查交易渠道和交易类型合法性
		txnCheckService.checkTxnType(txnType,TxnType.TXN_MGM.getTxnCode());

		// 检查受理机构状态权限
		 TInfoOrg acceptOrg = cacheComponent.getOrgObj(manager.getAcceptOrgCode());
		txnCheckService.checkOrgPayment(acceptOrg,txnType,acceptOrg.getOrgType(),false);// 检查商户交易合法性
//		if (!checkResult.startsWith(BussErrorCode.ERROR_CODE_000000.getErrorcode())) {
//			throw new BizException(checkResult.substring(0, 6), checkResult.substring(0, 6));
//		}
		String bankCardNo = bankCard.getBankCardNo();
		// 验证证件号码（身份证）是否正确
		if (CustomerIdType.CUSTOMER_ID_TYPE_IDCARD.getCode().equals(manager.getIdtype())) {
			// TODO
		}
		// 验证绑定银行卡号是否正确
		if (bankCardNo != null) {
			// TODO
		} else {
			throw new BizException(BussErrorCode.ERROR_CODE_230004.getErrorcode(), BussErrorCode.ERROR_CODE_230004.getErrorcode());
		}

		Timestamp txnTime = DateUtil.getTime();

		/*
		 * 唯一资金账户绑定
		 */
		TInfoAccountBankCard accountbankCard = tInfoAccountBankCardMapper.findBankCardByBankCardNo(bankCardNo);
		if (accountbankCard != null || tInfoAccountBankCardMapper.checkClosedBankCardNo(bankCardNo)!=0) {
			// 卡号已被绑定
			throw new BizException(BussErrorCode.ERROR_CODE_230001.getErrorcode(), BussErrorCode.ERROR_CODE_230001.getErrorcode());
		}
		List<TInfoAccount> fundAccounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(
				oldCustomer.getCustomerNo(), AccountType.FUND.getValue());
		TInfoAccount fundAccount = null;
		if (fundAccounts == null || fundAccounts.isEmpty()) {
			// 资金账户不存在
			throw new BizException(txnType, BussErrorCode.ERROR_CODE_200013.getErrorcode());
		} else if (fundAccounts.size() > AllowBindingCardMaxNum.CUSTOMER_ALLOW_BINDING_CARD_MAX_NUM.getAllowBidingCardMaxNum()) {
			// 超多最大允许绑定卡数
			throw new BizException(BussErrorCode.ERROR_CODE_200006.getErrorcode(), BussErrorCode.ERROR_CODE_200006.getErrorcode());
		} else {
			fundAccount = (TInfoAccount) fundAccounts.get(0);
			String accountNo = fundAccount.getAccountNo();
			
			
			// 最多绑3张卡
			List<TInfoAccountBankCard> accountBankCardList = tInfoAccountBankCardMapper
					.findBankListCardByAccountNo(accountNo);
			if (accountBankCardList != null
					&& accountBankCardList.size() >= AllowBindingCardMaxNum.ACCOUNT_ALLOW_BINDING_BANKCARD_MAX_NUM.getAllowBidingCardMaxNum()) {

				throw new BizException(BussErrorCode.ERROR_CODE_230002.getErrorcode(), BussErrorCode.ERROR_CODE_230002.getErrorcode());
			}
			// 最多绑一张主卡
			String bingdingMethod = CardBingdingMethod.CARD_BINGDING_METHOD_MASTER.getCardBingdingMethodCode();
			for (TInfoAccountBankCard card : accountBankCardList) {
				String method = card.getBindingMehod();
				if (CardBingdingMethod.CARD_BINGDING_METHOD_MASTER.getCardBingdingMethodCode().equals(method)) {
					bingdingMethod = CardBingdingMethod.CARD_BINGDING_METHOD_SLAVE.getCardBingdingMethodCode();
					break;
				}
			}
			
			TInfoAccountBankCard accountCard = new TInfoAccountBankCard();
			accountCard.setCustomerNo(oldCustomer.getCustomerNo());
			accountCard.setFundAccountNo(fundAccount.getAccountNo());
			accountCard.setOfflineAccountNo(null);
			accountCard.setBankCardNo(bankCardNo);
			accountCard.setBankCode(bankCard.getBankCode());
			
			accountCard.setBindingType(bindingType);
			//默认为转入转出卡
			if ("".equals(StringUtil.toTrim(bindingType))) {
				accountCard.setBindingType(BankCardBingding.BANK_CARD_BINGDING_TYPE_IN.getBankCardBingding() + ","
						+ BankCardBingding.BANK_CARD_BINGDING_TYPE_OUT.getBankCardBingding());
			
			}

			accountCard.setBindingFlag(BankCardBingding.BANK_CARD_BINGDING_FLAG_BIND.getBankCardBingding());
			accountCard.setBindingMehod(bingdingMethod);
			accountCard.setBindingTime(txnTime);
			accountCard.setBindingAcceptOrgCode(manager.getAcceptOrgCode());
			accountCard.setBindingAcceptTime(DateUtil.toMillTime(manager.getInputTime()));
			accountCard.setBingdingAcceptUid("");
			accountCard.setResvFld1("");
			accountCard.setResvFld2("");
			accountCard.setResvFld3("");
			TInfoBankcard	card = (TInfoBankcard) tInfoBankcardMapper.selectByPrimaryKey(bankCardNo);
			if (card == null) {
				card = bankCard;
				tInfoBankcardMapper.insert(bankCard);
			}else
			{
				tInfoBankcardMapper.insert(bankCard);
			}
			tInfoAccountBankCardMapper.insert(accountCard);
//			if(false){
//				String posPinkey = (String) pinkeyDao.findPinkey(
//						Constant.VIRTUAL_TERMINAL_NO,
//						Constant.VIRTUAL_MERCHANT_CODE).get("POS_PINKEY");
//				// 插入OTA密码转换表
////				String initPasswd = PinkeyClient.getInstance().decryptTxnPasswd(
////						fundAccount.getTxnPasswd(), accountNo);
//				if (initPasswd == null) {
//					// 密码加密失败
//					throw new BusinessException(txnType, Constant.ERROR_CODE_900000);
//				}
//				String otaPasswd = PinkeyClient.getInstance().encryptOtaPin(
//						bankCardNo, initPasswd);
//				String ocxPasswd = PinkeyClient.getInstance().convertOcx(posPinkey,
//						initPasswd, bankCardNo);
//				if (otaPasswd == null || ocxPasswd == null) {
//					// 密码加密失败
//					throw new BizException(txnType, BussErrorCode.ERROR_CODE_900000.getErrorcode());
//				}
//				bankCardDao.insertBankOtaPasswd(bankCardNo, otaPasswd, ocxPasswd,
//						Constant.BANK_CARD_OTAPWD_TYPE);
//			}
		}

		// 记录账户管理日志
		TLogAccountManagementLog(fundAccount,manager,customer,acceptOrg);

//		// 处理账户变动通知
//		this.handleAccountAlterLog(customer, fundAccount, log, acceptOrg, null,
//				fundAccount.getInitPasswd(), null);

		// 清除对象内存
		fundAccount = null;
		//log = null;
		bankCard = null;

		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
	}

	 @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
	    		propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String unbindBankCardByBankCardNo(TInfoBankcard bankCard ,TInfoCustomer customer,ManagerLog manager) {
		String txnType = TxnInnerType.TXN_TYPE_101B80.getTxnInnerType();
		TInfoCustomer oldCustomer = (TInfoCustomer) tInfoCustomerMapper
				.findCustomerByUnifyIdForUpdate(customer.getUnifyId());
		if (oldCustomer == null) {
			// 客户不存在
			throw new BizException(BussErrorCode.ERROR_CODE_200010.getErrorcode(), BussErrorCode.ERROR_CODE_200010.getErrorcode());
		}
		
		// 验证转入转出是否是翼支付实名用户
		oldCustomer.getIsRealname();
		 if (CustomerRealname.CUSTOMER_REALNAME_TRUE.getCustomerRealnameCode().equals(oldCustomer.getIsRealname())) {
				
				throw new BizException(BussErrorCode.ERROR_CODE_200137.getErrorcode(), BussErrorCode.ERROR_CODE_200137.getErrorcode());
			}
		
		 if (!oldCustomer.getIdentifyType().equals(manager.getIdtype())
					|| !TxnCheckService.checkPid(manager.getIdtype(), manager.getIdno(), oldCustomer.getIdentifyNo())) {
				// 证件类型和证件号码不一致
				throw new BizException(BussErrorCode.ERROR_CODE_200038.getErrorcode(), BussErrorCode.ERROR_CODE_200038.getErrorcode());
			}
			


			if (oldCustomer.getBlacklistFlag() != null) {
				// 客户黑名单
				throw new BizException(BussErrorCode.ERROR_CODE_200016.getErrorcode(), BussErrorCode.ERROR_CODE_200016.getErrorcode());
			}
			if (!CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode().equals(oldCustomer
					.getCustomerStatus())) {
				// 客户状态不为正常，不能开户
				throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(), BussErrorCode.ERROR_CODE_200011.getErrorcode());
			}

		// 检查注册时填写的手机号
//		if (!customer.getRegisterType().equals(
//				Constant.CUSTOMER_REGISTER_TYPE_VIRTUAL))
			txnCheckService.checkTeleMobilePhone(oldCustomer);

			txnCheckService.checkCustomerActivationTxn(oldCustomer);// 检查交易合法性

			// 检查交易渠道和交易类型合法性
			txnCheckService.checkTxnType(txnType,TxnType.TXN_MGM.getTxnCode());

			// 检查受理机构状态权限
			 TInfoOrg acceptOrg = cacheComponent.getOrgObj(manager.getAcceptOrgCode());
			txnCheckService.checkOrgPayment(acceptOrg,txnType,acceptOrg.getOrgType(),false);// 检查商户交易合法性
//			if (!checkResult.startsWith(BussErrorCode.ERROR_CODE_000000.getErrorcode())) {
//				throw new BizException(checkResult.substring(0, 6), checkResult.substring(0, 6));
//			}
			String bankCardNo = bankCard.getBankCardNo();
			// 验证证件号码（身份证）是否正确
			if (CustomerIdType.CUSTOMER_ID_TYPE_IDCARD.getCode().equals(manager.getIdtype())) {
				// TODO
			}
			// 验证绑定银行卡号是否正确
			if (bankCardNo != null) {
				// TODO
			} else {
				throw new BizException(BussErrorCode.ERROR_CODE_230004.getErrorcode(), BussErrorCode.ERROR_CODE_230004.getErrorcode());
			}
			Timestamp txnTime = DateUtil.getTime();
			List<TInfoAccount> fundAccounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(
					oldCustomer.getCustomerNo(), AccountType.FUND.getValue());
			TInfoAccount fundAccount = null;
			
		
		if (fundAccounts == null || fundAccounts.isEmpty()) {
			// 资金账户不存在
			throw new BizException( BussErrorCode.ERROR_CODE_200013.getErrorcode(), BussErrorCode.ERROR_CODE_200013.getErrorcode());
		} else if (fundAccounts.size() > AllowBindingCardMaxNum.CUSTOMER_ALLOW_BINDING_CARD_MAX_NUM.getAllowBidingCardMaxNum()) {
			// 超多最大允许绑定卡数
			throw new BizException(BussErrorCode.ERROR_CODE_200006.getErrorcode(), BussErrorCode.ERROR_CODE_200006.getErrorcode());
		} else {
			fundAccount = (TInfoAccount) fundAccounts.get(0);
			String accountNo = fundAccount.getAccountNo();

			// 判断此卡是否已经绑定
			List<TInfoAccountBankCard> bankCardList = tInfoAccountBankCardMapper
					.findBankListCardByAccountNo(accountNo);
			if (bankCardList == null || bankCardList.isEmpty()) {

				throw new BizException(BussErrorCode.ERROR_CODE_230003.getErrorcode(), BussErrorCode.ERROR_CODE_230003.getErrorcode());
			}
			TInfoAccountBankCard accountBankCard = tInfoAccountBankCardMapper
					.findBankCardByBankCardNo(bankCardNo);
			if (1 == bankCardList.size()
					|| CardBingdingMethod.CARD_BINGDING_METHOD_SLAVE.getCardBingdingMethodCode().equals(accountBankCard
							.getBindingMehod())) {
				accountBankCard.setUnbindingTime(txnTime);
				accountBankCard.setUnbindingAcceptTime( new Timestamp(manager.getInputTime().getTime()));
				accountBankCard.setUnbindingAcceptOrgCode(manager.getAcceptOrgCode());
				accountBankCard.setUnbingdingAcceptUid("");
				tInfoAccountBankCardMapper.unbindBankCardByBankCardNo(accountBankCard);

			} else {
				// 主卡解绑,副卡改成主卡.

				tInfoAccountBankCardMapper.unbindBankCardByBankCardNo(accountBankCard);

				for (TInfoAccountBankCard card : bankCardList) {
					if (!bankCardNo.equals(card.getBankCardNo())) {
						accountBankCard.setUnbindingTime(txnTime);
						accountBankCard.setUnbindingAcceptTime( new Timestamp(manager.getInputTime().getTime()));
						accountBankCard.setUnbindingAcceptOrgCode(manager.getAcceptOrgCode());
						accountBankCard.setUnbingdingAcceptUid("");
						accountBankCard.setBindingMehod(CardBingdingMethod.CARD_BINGDING_METHOD_MASTER.getCardBingdingMethodCode());
						tInfoAccountBankCardMapper
								.updateBankCardBingdingMethod(accountBankCard);
						break;
					}

				}
			}

		}

		// 记录账户管理日志
		TLogAccountManagementLog(fundAccount,manager,customer,acceptOrg);

		// 处理账户变动通知
//		this.handleAccountAlterLog(customer, fundAccount, log, acceptOrg, null,
//				fundAccount.getInitPasswd(), null);

		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
	}

public void TLogAccountManagementLog(TInfoAccount fundAccount,ManagerLog manager,TInfoCustomer customer,TInfoOrg acceptOrg){
	String beforeStatus = fundAccount.getStatus(); 
	String afterStatus = AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus();
	String txnSeqNo = sequenceGenerator.generateAccountMgmTxnSeq(); 
	
	
	TLogAccountManagement tLogAccountManagement = new TLogAccountManagement();
   	   tLogAccountManagement.setAcceptDate(manager.getAcceptDate());
   	   tLogAccountManagement.setAcceptTime(manager.getAcceptTime());
   	   tLogAccountManagement.setAcceptOrgSerialNo(manager.getAcceptTxnSeqNo());
   	   tLogAccountManagement.setAccountNo(fundAccount.getAccountNo());
   	   tLogAccountManagement.setAccountType(fundAccount.getType());
   	   tLogAccountManagement.setAfterStatus(afterStatus);
   	   tLogAccountManagement.setBeforeStatus(manager.getBeforeStatus());
   	   Timestamp tsCheckTime = new Timestamp(manager.getCheckTime().getTime());//转换为timeStamp
   	   tLogAccountManagement.setCheckTime(tsCheckTime);//CheckTime
   	   tLogAccountManagement.setCheckUid(manager.getCheckUid());
   	   tLogAccountManagement.setCustomerNo(customer.getCustomerNo());
   	   tLogAccountManagement.setFeeAmount(manager.getFeeAmt());
   	   tLogAccountManagement.setFeeFlag(manager.getFeeFlag());
   	   Timestamp tsInputTime = new Timestamp(manager.getInputTime().getTime());//转换为timeStamp
   	   tLogAccountManagement.setInputTime(tsInputTime);//inputtime
   	   tLogAccountManagement.setInputUid(manager.getInputUid());
   	   tLogAccountManagement.setOrgCode(acceptOrg.getOrgCode());
   	   tLogAccountManagement.setAcceptChannel(manager.getTxnChannel());
   	   //Timestamp tsTransTime = new Timestamp(tInfoAccount.getLastUpdateTime().getTime());//转换为timeStamp
   	   tLogAccountManagement.setTransTime(null);//transTime
   	   tLogAccountManagement.setTransMemo(manager.getTxnDscpt());
   	   tLogAccountManagement.setTransMonth(Integer.parseInt(new DateTime(tLogAccountManagement.getTransTime()).toString("yyyyMM")));
   	   tLogAccountManagement.setTransSerialNo(sequenceGenerator.generateAccountMgmTxnSeq());	   
   	   tLogAccountManagement.setTransType(manager.getInnerTxnType());
   	   tLogAccountManagementMapper.insert(tLogAccountManagement);
}
	
}
