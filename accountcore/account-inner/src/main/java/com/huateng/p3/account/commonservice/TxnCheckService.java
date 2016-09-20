package com.huateng.p3.account.commonservice;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.AccountCommunicationStatus;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.CardStatus;
import com.huateng.p3.account.common.enummodel.CustomerIdType;
import com.huateng.p3.account.common.enummodel.CustomerRealname;
import com.huateng.p3.account.common.enummodel.CustomerStatus;
import com.huateng.p3.account.common.enummodel.CustomerGrade;
import com.huateng.p3.account.common.enummodel.OrgStatus;
import com.huateng.p3.account.common.enummodel.RegisterType;
import com.huateng.p3.account.common.enummodel.RiskBlackType;
import com.huateng.p3.account.common.enummodel.TrueFalseLabel;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.PidUtil;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoAccountenterprise;
import com.huateng.p3.account.persistence.models.TInfoCard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;
import com.huateng.p3.account.persistence.models.TParaMerchantRefundRule;
import com.huateng.p3.account.persistence.models.TRiskAreaOta;
import com.huateng.p3.account.persistence.models.TRiskBlackManage;
import com.google.common.base.Strings;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.component.PinkeyComponent;
import com.huateng.p3.account.component.SecurityComponent;

/**
 * User: JamesTang
 * Date: 13-12-6
 * Time: 上午8:52
 */
@Component
public class TxnCheckService {
	
	@Value("${accountMaxExtendTimes}")
    private int accountMaxExtendTimes; 
    /**
     * 需要消费可提现余额的交易类型集合
     */
    public static Set<String> withdrawbalanceTxnSet = new HashSet<String>();

    @Value("${maxpreauthDays}")
    private int maxpreauthDays;

    /**
     * 可提现充值交易类型集合
     */
    public static Set<String> withDrawBalanceChargeTxnSet = new HashSet<String>();
    
    
    /**
     * 不需要检查的交易类型集合
     */
    public static Set<String> noCheckTxnSet = new HashSet<String>();
    
    
    /**
     * 不需要总风控的交易类型集合
     */
    public static Set<String> noAllRiskTxnSet = new HashSet<String>();
    
    static {
        withdrawbalanceTxnSet.add(TxnInnerType.TXN_TYPE_141020.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.TXN_TYPE_141B20.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.TXN_TYPE_131B10.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.TXN_TYPE_131160.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.TXN_TYPE_131190.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.XTYPE_PREDRAW_END.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.XTYPE_PREDRAW.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.XTYPE_TRANSFER_PREDRAW.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.XTYPE_TRANSFER_PREDRAW_END.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.XTYPE_NEW_PREDRAW_END.getTxnInnerType());
        withdrawbalanceTxnSet.add(TxnInnerType.XTYPE_NEW_PREDRAW.getTxnInnerType());
        

        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121010.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121050.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121011.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121013.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121B10.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121200.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_12M010.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121130.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121190.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_141010.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_141B10.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121020.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_721010.getTxnInnerType());
        withDrawBalanceChargeTxnSet.add(TxnInnerType.TXN_TYPE_121120.getTxnInnerType());
        

               
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121190.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121061.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_270010.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_270020.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121090.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121013.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_261010.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_261020.getTxnInnerType());       
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121100.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121060.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121062.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121063.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121064.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121065.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121066.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121067.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121068.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121069.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121071.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121072.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121073.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121074.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121075.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_121160.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_761010.getTxnInnerType());
        noCheckTxnSet.add(TxnInnerType.TXN_TYPE_761020.getTxnInnerType());
        
        
        
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121190.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121061.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_270010.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_270020.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121090.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121013.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_261010.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_261020.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121100.getTxnInnerType());               
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121060.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121062.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121063.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121064.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121065.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121066.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121067.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121068.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121069.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121071.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121072.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121073.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121074.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121075.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_121160.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_131160.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_131B10.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.TXN_TYPE_131190.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.XTYPE_TRANSFER_PREDRAW.getTxnInnerType());
        noAllRiskTxnSet.add(TxnInnerType.XTYPE_TRANSFER_PREDRAW_END.getTxnInnerType());

    }


    @Autowired
    private
    SecurityComponent securityComponent;

    @Autowired
    private CacheComponent cacheComponent;
    
    @Autowired (required = false)
    private
    PinkeyComponent pinkeyComponent;
    
    /**
     * @param tInfoCustomer
     */
    public void checkCustomerPaymentTxn(TInfoCustomer tInfoCustomer) {
        if (null == tInfoCustomer) {
            throw new BizException(BussErrorCode.ERROR_CODE_200010.getErrorcode(), BussErrorCode.ERROR_CODE_200010.getErrordesc());
            // 指定客户不存在
        }
        if (null != tInfoCustomer.getBlacklistFlag()) { //只要这个字段不是null就进来。。。
            throw new BizException(BussErrorCode.ERROR_CODE_200016.getErrorcode(), BussErrorCode.ERROR_CODE_200016.getErrordesc());
            // 客户黑名单
        }
        if (!CustomerStatus.CUSTOMER_ACTIVED.getCustomerStatusCode().equals(tInfoCustomer.getActiveStatus())) {
            // 客户未激活
            throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(), BussErrorCode.ERROR_CODE_200011.getErrordesc());
        }
    }
    
    /**
     * @param customer
     */
    public void checkCustomerPaymentTxn(TInfoAccountenterprise customer) {
        if (null == customer) {
            throw new BizException(BussErrorCode.ERROR_CODE_200010.getErrorcode(), BussErrorCode.ERROR_CODE_200010.getErrordesc());
            // 指定客户不存在
        }
        if (!CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode().equals(customer.getStatus())) {
        	// 客户状态不为正常
            throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(), BussErrorCode.ERROR_CODE_200011.getErrordesc());
        }
        if (!CustomerStatus.CUSTOMER_ACTIVED.getCustomerStatusCode().equals(customer.getActiveStatus())) {
            // 客户未激活
            throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(), BussErrorCode.ERROR_CODE_200011.getErrordesc());
        }
    }
    

    /**
     * @param tInfoCustomer
     */
    public void checkCustomerActivationTxn(TInfoCustomer tInfoCustomer) {
        if (null == tInfoCustomer) {
            throw new BizException(BussErrorCode.ERROR_CODE_200010.getErrorcode(), BussErrorCode.ERROR_CODE_200010.getErrordesc());
            // 指定客户不存在
        }
        if (null != tInfoCustomer.getBlacklistFlag()) { //只要这个字段不是null就进来。。。
            throw new BizException(BussErrorCode.ERROR_CODE_200016.getErrorcode(), BussErrorCode.ERROR_CODE_200016.getErrordesc());
            // 客户黑名单
        }
        if (!CustomerStatus.CUSTOMER_ACTIVED.getCustomerStatusCode().equals(tInfoCustomer.getActiveStatus())) {
            // 客户未激活
            throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(), BussErrorCode.ERROR_CODE_200011.getErrordesc());
        }
    }

    /**
     * 预授权确认交易校验
     *
     * @param apply
     * @param completeAmount
     * @return
     */
    public void checkPreAuthComplete(TLogPreauthApply apply,
                                     long completeAmount) {
        if (apply == null) {
            // 原预授权记录不存在
            throw new BizException(BussErrorCode.ERROR_CODE_300000.getErrorcode(), BussErrorCode.ERROR_CODE_300000.getErrordesc());
        }
        if (apply.getAuthEndDate() != null) {
            // 原预授权记录已完成
            throw new BizException(BussErrorCode.ERROR_CODE_300005.getErrorcode(),
                    BussErrorCode.ERROR_CODE_300005.getErrordesc());
        }
        if (TrueFalseLabel.TRUE.getLablCode().equals(apply.getCancelFlag())) {
            // 原预授权记录已撤销
            throw new BizException(BussErrorCode.ERROR_CODE_300001.getErrorcode(),
                    BussErrorCode.ERROR_CODE_300001.getErrordesc());
        }
        if (TrueFalseLabel.TRUE.getLablCode().equals(apply.getRevsalFlag())) {
            // 原预授权记录已冲正
            throw new BizException(BussErrorCode.ERROR_CODE_300002.getErrorcode(),
                    BussErrorCode.ERROR_CODE_300002.getErrordesc());
        }
        if (apply.getAuthAmt() < completeAmount) {
            // 原预授权记录小于完成金额
            throw new BizException(BussErrorCode.ERROR_CODE_300003.getErrorcode(),
                    BussErrorCode.ERROR_CODE_300003.getErrordesc());
        }

        DateTime dt = new DateTime(apply.getTxnTime());
        if (dt.plusDays(maxpreauthDays).isBeforeNow()) {
            // 原预授权记录超期
            throw new BizException(BussErrorCode.ERROR_CODE_300004.getErrorcode(),
                    BussErrorCode.ERROR_CODE_300004.getErrordesc());
        }
    }

    /**
     *  外部交易类型转内部交易类型
     * @param paymentInfo
     * @param txnType
     * @param txnSeqType
     */
    public void txnIniCheck(PaymentInfo paymentInfo, TxnType txnType, TxnSeqType... txnSeqType) {
        String innerTxnType = cacheComponent.getInnerTxnType(paymentInfo.getBussinessType()).getInteriorTransCode();
        paymentInfo.setInnerTxnType(innerTxnType);
        paymentInfo.setTxnDate(DateTime.now().toDate());
        //设一个消费大类
        paymentInfo.setTxnType(txnType);
        paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        if (txnSeqType.length > 0) {
            paymentInfo.setTxnSeqType(txnSeqType[0]);
        }
        checkTxnType(paymentInfo.getInnerTxnType(), paymentInfo.getTxnType().getTxnCode());
    }
    
    
    /**不校验交易类型，写死交易类型的方法，如转账，圈存。
     * @param paymentInfo
     * @param txnType
     * @param txnSeqType
     */
    public void txnIniNoCheck(PaymentInfo paymentInfo,String innerTxnType, TxnType txnType, TxnSeqType... txnSeqType) {
        paymentInfo.setInnerTxnType(innerTxnType);
        paymentInfo.setBussinessType(innerTxnType);
        paymentInfo.setTxnDate(DateTime.now().toDate());
        //设一个消费大类
        paymentInfo.setTxnType(txnType);
        paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        if (txnSeqType.length > 0) {
            paymentInfo.setTxnSeqType(txnSeqType[0]);
        }
        
    }

    /**
     * 交易的合法性。
     *
     * @param txnType
     * @param txnTypeLabel
     */
    public void checkTxnType(String txnType, char txnTypeLabel) {
        if (txnType == null || txnType.trim().equals("")
                || txnType.charAt(1) != txnTypeLabel) {
            throw new BizException(BussErrorCode.ERROR_CODE_900001.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900001.getErrordesc());
        }
    }

    /**
     * 客户查询校验
     * @param customer
     */
    public void checkCustomerQueryBalance(TInfoCustomer customer) {
    	// 状态不为正常、冻结、锁定，不能查询余额
    	if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(customer.getCustomerStatus())
				&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(customer
						.getCustomerStatus())
				&& !AccountStatus.ACCOUNT_STATUS_FROZEN.getStatus().equals(customer
						.getCustomerStatus())) {
    		throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200011.getErrordesc());
    		
    	}
    }
    
    /**
     * 客户手机号检查
     * @param customer
     */
    public void checkTeleMobilePhone(TInfoCustomer customer) {
	    	if (customer.getMobileNo() == null || customer.getMobileNo().trim().length() != 11) {
				// 非法电信手机号
	    		throw new BizException(BussErrorCode.ERROR_CODE_200025.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200025.getErrordesc());
			}
    }
    
    
    
    /**
     * 卡交易检验
     * @param card
     * @param innerTxnType
     */
    public void checkCardManagerTxn(TInfoCard card, String innerTxnType) {
		if (TxnInnerType.TXN_TYPE_102000.getTxnInnerType().equals(innerTxnType)
				|| TxnInnerType.TXN_TYPE_101010.getTxnInnerType().equals(innerTxnType)
				|| TxnInnerType.TXN_TYPE_101170.getTxnInnerType().equals(innerTxnType)) {
			// 检查开卡
			if (!CardStatus.CARD_STATUS_ACTIVE.getCardStatusCode().equals(card.getStatus())) {
				// 状态不为已启用，不能开户或绑定
				throw new BizException(BussErrorCode.ERROR_CODE_200005.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200005.getErrordesc());
			}
			return;
		}
		if (TxnInnerType.TXN_TYPE_101180.getTxnInnerType().equals(innerTxnType)) {
			// 检查卡解绑
			if (!CardStatus.CARD_STATUS_ACTIVE.getCardStatusCode().equals(card.getStatus())) {
				// 状态不为正常，不能解绑
				throw new BizException(BussErrorCode.ERROR_CODE_200005.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200005.getErrordesc());
			}
			return;
		}
		if (TxnInnerType.TXN_TYPE_101190.getTxnInnerType().equals(innerTxnType)) {
			// 检查挂失
			if (!CardStatus.CARD_STATUS_ACTIVE.getCardStatusCode().equals(card.getStatus())) {
				// 状态不为正常，不能挂失
				throw new BizException(BussErrorCode.ERROR_CODE_200005.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200005.getErrordesc());
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_101200.getTxnInnerType().equals(innerTxnType)) {
			// 检查解挂
			if (!CardStatus.CARD_STATUS_LOSTED.getCardStatusCode().equals(card.getStatus())) {
				// 状态不为挂失，不能解挂
				throw new BizException(BussErrorCode.ERROR_CODE_200005.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200005.getErrordesc());
			}
			return;
		} 
		//针对圈存转入和圈提转出交易校验
		if (TxnInnerType.TXN_TYPE_112010.getTxnInnerType().equals(innerTxnType)
				|| TxnInnerType.TXN_TYPE_112020.getTxnInnerType().equals(innerTxnType)) {
			// 检查开卡状态
			if (!CardStatus.CARD_STATUS_ACTIVE.getCardStatusCode().equals(card.getStatus())) {
			// 状态不为已启用，不能开户或绑定
				throw new BizException(BussErrorCode.ERROR_CODE_200005.getErrorcode(),
				        BussErrorCode.ERROR_CODE_200005.getErrordesc());
			}
			if (card.getForbiddenTxn() != null
					&& card.getForbiddenTxn().indexOf(innerTxnType + ",") != -1) {
				//卡片禁止该交易
				throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200027.getErrordesc());
			}
			return;
		} 
    }
    
    /**
     * 账户管理类交易检验
     * @param account
     * @param managerLog
     */
    public void checkAccountManagerTxn(TInfoAccount account, ManagerLog managerLog) {
    	 if (account == null) {
             // 指定账户不存在
             throw new BizException(BussErrorCode.ERROR_CODE_200013.getErrorcode(),
                     BussErrorCode.ERROR_CODE_200013.getErrordesc());
         }
         String newEncryptMsg = securityComponent.generateEncryptedMsg(account);
         String oldEncryptMsg = account.getEncryptedMsg();
         if (!newEncryptMsg.equals(oldEncryptMsg)) {
             // 账户关键数据密文报文不一致
             throw new BizException(BussErrorCode.ERROR_CODE_200040.getErrorcode(),
                     BussErrorCode.ERROR_CODE_200040.getErrordesc());
         }  //连接加密机
         if (account.getForbiddenTxn() != null
                 && account.getForbiddenTxn().indexOf(managerLog.getInnerTxnType() + ",") != -1) {
             // 账户禁止该交易
             throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                     BussErrorCode.ERROR_CODE_200027.getErrordesc());

         }
         
         // 账户已过期
         if (DateTime.now().toDate().after(account.getExpiredDate())) {
             throw new BizException(BussErrorCode.ERROR_CODE_200015.getErrorcode(),
                     BussErrorCode.ERROR_CODE_200015.getErrordesc());
         }
         
         if (!TxnInnerType.TXN_TYPE_101230.getTxnInnerType().equals(managerLog.getInnerTxnType())
  				&& account.getPasswdErrLockTime() != null
                  && account.getPasswdErrLockTime().after(DateTime.now().toDate())) {
         	  // 密码错误次数超限被锁定，并尚未到解锁时间
              String fmtDatestr = new DateTime(account.getPasswdErrLockTime()).toString(DateTimeFormat.forPattern("yyyy年MM月dd日HH时mm分"));  //使用自定义的日期格式化当期日期             
              throw new BizException(BussErrorCode.ERROR_CODE_200023.getErrorcode(),
                      BussErrorCode.ERROR_CODE_200023.getErrordesc(),fmtDatestr);
         }
         
		if (TxnInnerType.TXN_TYPE_101120.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_102110.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_103110.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查关闭账户
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account
							.getStatus())) {
				// 状态不为正常或挂失，不能关闭
		        throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			return;
		}
		if (TxnInnerType.TXN_TYPE_101020.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_102030.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_10A020.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_10B020.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_103020.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			
			if (!AccountType.INTEGRAL.getValue().equals(account.getType())) {
				// 非脱机账户销户
				if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())
						&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account
								.getStatus())) {
					// 状态不为未激活或正常或挂失，不能销账户
					throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
		                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
		
				}
			 }
			// 检查销户
			if (AccountType.FUND.getValue().equals(account.getType())){
//					|| AccountType.BSTCARD.getValue().equals(account.getType())
//					|| AccountType.TYCARD.getValue().equals(account.getType())) {
				
				if (account.getBalance() != 0) {
					// 如果余额不为0，不能销户
					throw new BizException(BussErrorCode.ERROR_CODE_200009.getErrorcode(),
		                     BussErrorCode.ERROR_CODE_200009.getErrordesc());
				}
				if (account.getFrozenAmount() != 0) {
					// 如果冻结金额不为0，不能销户
					throw new BizException(BussErrorCode.ERROR_CODE_200018.getErrorcode(),
		                     BussErrorCode.ERROR_CODE_200018.getErrordesc());
				}
			} 
			return;
		}
		if (TxnInnerType.TXN_TYPE_101140.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_101150.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_102130.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_102140.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_10A030.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_10B030.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			if (AccountType.FUND.getValue().equals(account.getType())){
//					|| AccountType.BSTCARD.getValue().equals(account.getType())
//					|| AccountType.TYCARD.getValue().equals(account.getType())) {
				// 资金账户销户
				if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())
						&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account
								.getStatus())) {
					// 状态不为未激活或正常或挂失，不能销账户
					throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
		                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
		
				}
				if (account.getFrozenAmount() != 0) {
					// 如果冻结金额不为0，不能销户
					throw new BizException(BussErrorCode.ERROR_CODE_200018.getErrorcode(),
		                     BussErrorCode.ERROR_CODE_200018.getErrordesc());
				}
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_101050.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查冻结
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account
							.getStatus())) {
				// 状态不为正常或挂失，不能冻结
				throw new BizException(BussErrorCode.ERROR_CODE_500200.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_500200.getErrordesc());
	
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_101060.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查解冻
			if (!AccountStatus.ACCOUNT_STATUS_FROZEN.getStatus().equals(account.getStatus())) {
				// 状态不为冻结，不能解冻
				throw new BizException(BussErrorCode.ERROR_CODE_500201.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_500201.getErrordesc());
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_101030.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查是否已经挂失，如果已经挂失，直接返回
			if (AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account.getStatus())) {
				return;
			}
			// 检查挂失
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
				// 状态不为正常，不能挂失
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
//			if (AccountType.BOND.getValue().equals(account.getType())) {
//				// 不能挂失代金券账户
//				throw new BizException(BussErrorCode.ERROR_CODE_200069.getErrorcode(),
//	                     BussErrorCode.ERROR_CODE_200069.getErrordesc());
//				
//			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_101040.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查是否已经解挂，如果已经解挂，直接返回成功
			if (AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
				return;
			}
			// 检查解挂
			if (!AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account.getStatus())) {
				// 状态不为挂失，不能解挂
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			return;
		}
		if (TxnInnerType.TXN_TYPE_101070.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查锁定
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account
							.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_FROZEN.getStatus().equals(account
							.getStatus())) {
				// 状态不为正常、冻结或挂失，不能锁定
				throw new BizException(BussErrorCode.ERROR_CODE_500202.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_500202.getErrordesc());
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_101080.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查解锁
			if (!AccountStatus.ACCOUNT_STATUS_LOCKED.getStatus().equals(account.getStatus())) {
				// 状态不为锁定，不能解锁
				throw new BizException(BussErrorCode.ERROR_CODE_500203.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_500203.getErrordesc());
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_101090.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
				// 状态不为正常，不能修改交易密码
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			return;
		}
		if (TxnInnerType.TXN_TYPE_101110.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查重置交易密码
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
				// 状态不为正常，不能重置交易密码
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_100100.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查查询余额
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_FROZEN.getStatus().equals(account
							.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_LOCKED.getStatus().equals(account
							.getStatus())) {
				// 状态不为正常、冻结、锁定，不能查询余额
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_100090.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查修改账户信息
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
				// 状态不为正常，不能查询余额
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			return;
		} 
		if (TxnInnerType.TXN_TYPE_10A160.getTxnInnerType().equals(managerLog.getInnerTxnType())
				|| TxnInnerType.TXN_TYPE_10B160.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 账户延期
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
				// 状态不为正常，不能延期
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			if (account.getExtendCount() >= accountMaxExtendTimes) {
				throw new BizException(BussErrorCode.ERROR_CODE_200029.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200029.getErrordesc());
			}
			return;
		} 
		
		if (TxnInnerType.TXN_TYPE_101131.getTxnInnerType().equals(managerLog.getInnerTxnType())) {
			// 检查开通交费助手
			if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
				// 状态不为正常，不能开通交费助手
				throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
	                     BussErrorCode.ERROR_CODE_200007.getErrordesc());
			}
			return;
		} 
		
    	
    }

    /**
     * @param account
     * @param paymentInfo
     */
    public void checkAccountPaymentTxn(TInfoAccount account, PaymentInfo paymentInfo,boolean... isRollOutAccount) {

        char txn = paymentInfo.getTxnType().getTxnCode();
        if (paymentInfo.getAmount() <= 0) {
            throw new BizException(BussErrorCode.ERROR_CODE_200021.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200021.getErrordesc());
        }
    /*    if (txnFeeAmount < 0) {
            throw new BizException(BussErrorCode.ERROR_CODE_200021.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200021.getErrordesc());
        }*/
        if (account == null) {
            // 指定账户不存在
            throw new BizException(BussErrorCode.ERROR_CODE_200013.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200013.getErrordesc());
        }
       String newEncryptMsg = securityComponent.generateEncryptedMsg(account);
       String oldEncryptMsg = account.getEncryptedMsg();
//       if (!newEncryptMsg.equals(oldEncryptMsg)) {
//           // 账户关键数据密文报文不一致
//            throw new BizException(BussErrorCode.ERROR_CODE_200040.getErrorcode(),
//                   BussErrorCode.ERROR_CODE_200040.getErrordesc());
//        }
        if (account.getForbiddenTxn() != null
                && account.getForbiddenTxn().indexOf(paymentInfo.getInnerTxnType() + ",") != -1) {
            // 账户禁止该交易
            throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200027.getErrordesc());

        }
        if (account.getForbiddenChannel() != null
                && account.getForbiddenChannel().indexOf(paymentInfo.getChannel() + ",") != -1) {
            // 账户禁止该交易
            throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200027.getErrordesc());
        }
        
        if (AccountStatus.ACCOUNT_STATUS_UNACTIVE.getStatus().equals(account.getStatus())) {
            // 账户状态为未激活，不能进行任何支付交易
            throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200007.getErrordesc());
        }
        // 账户已过期
        if (DateTime.now().toDate().after(account.getExpiredDate())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200015.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200015.getErrordesc());
        }
        // 返利类特殊交易类型直接过
        if (TxnCheckService.noCheckTxnSet.contains(paymentInfo.getInnerTxnType())) {
        	return;
        }
        //转入账户不需要检查支付密码是否被锁定
     if(isRollOutAccount.length > 0 && isRollOutAccount[0]==false){   
        
    }else{
    	if (account.getPasswdErrLockTime() != null
                && account.getPasswdErrLockTime().after(DateTime.now().toDate())) {
        	String fmtDatestr = new DateTime(account.getPasswdErrLockTime()).toString(DateTimeFormat.forPattern("yyyy年MM月dd日HH时mm分"));  //使用自定义的日期格式化当期日期            
            throw new BizException(BussErrorCode.ERROR_CODE_200023.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200023.getErrordesc(),fmtDatestr);
        }
    }

        if (txn == TxnType.TXN_CHARGE.getTxnCode() || txn == TxnType.TXN_TRANSFER_END.getTxnCode()) {
        	
        	// 检查资金帐户充值
            if ("1".equals(paymentInfo.getInnerTxnType().substring(0, 1))
                    && !paymentInfo.getInnerTxnType().substring(2, 3).equals(account.getType())) {
                // 账户类型和交易代码中账户类型不一致，不能充值
                throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200027.getErrordesc());
            }
        	
            // 充值
            if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())
                    && !AccountStatus.ACCOUNT_STATUS_FROZEN.getStatus().equals(account
                    .getStatus())) {
                throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200007.getErrordesc());
                // 账户状态不为正常或冻结，不能充值
            }
            if (AccountCommunicationStatus.COMM_STATUS_FORCED_STOP.getStatus().equals(account.getCommStatus())) {
                // 通信设备状态为被动拆机，不能充值
                throw new BizException(BussErrorCode.ERROR_CODE_200008.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200008.getErrordesc());
            }
        }
        //消费、预授权、转账交易执行下述方法
        if (TxnType.TXN_CONSUME.getTxnCode() == txn || TxnType.TXN_PREAUTH.getTxnCode() == txn
                || TxnType.TXN_TRANSFER.getTxnCode() == txn ) {
        	//转账需为实名账户
        	if(TxnType.TXN_TRANSFER.getTxnCode() == txn){
        		
        		if ((!account.getIsRealname().equals(
        				CustomerRealname.CUSTOMER_REALNAME_HIGH.getCustomerRealnameCode()))
        				&& (!account.getIsRealname().equals(
        						CustomerRealname.CUSTOMER_REALNAME_TRUE.getCustomerRealnameCode()))) {
        			
        			throw new BizException(BussErrorCode.ERROR_CODE_200137.getErrorcode(),
                            BussErrorCode.ERROR_CODE_200137.getErrordesc());
        		}
        	}        	       	
            // 检查资金帐户消费
            if ("1".equals(paymentInfo.getInnerTxnType().substring(0, 1))
                    && !paymentInfo.getInnerTxnType().substring(2, 3).equals(account.getType())) {
                // 账户类型和交易代码中账户类型不一致，不能联机消费
                throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200027.getErrordesc());
            }
            if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
                // 资金账户状态不为正常，不能消费
                throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200007.getErrordesc());
            }
            if (account.getAvailableBalance() < paymentInfo.getAmount()) {
                // 可用余额小于交易金额，不能消费
                throw new BizException(BussErrorCode.ERROR_CODE_200020.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200020.getErrordesc());
            }

            if (withdrawbalanceTxnSet.contains(paymentInfo.getInnerTxnType())) {
                if (account.getWithdrawBalance() < paymentInfo.getAmount()) {
                    // 可提现余额小于交易金额，不能消费
                    throw new BizException(BussErrorCode.ERROR_CODE_200013.getErrorcode(),
                            BussErrorCode.ERROR_CODE_200013.getErrordesc());
                }
            }
        }
        if (TxnType.TXN_RECONCILIATION.getTxnCode() == txn) {
            if ("1".equals(paymentInfo.getInnerTxnType().substring(0, 1))
                    && !paymentInfo.getInnerTxnType().substring(2, 3).equals(account.getType())) {
                // 账户类型和交易代码中账户类型不一致，不能联机消费
                throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200027.getErrordesc());
            }
            if (account.getAvailableBalance() < paymentInfo.getAmount()) {

                // 可用余额小于交易金额，不能消费
                throw new BizException(BussErrorCode.ERROR_CODE_200020.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200020.getErrordesc());

            }
        }

        if (TxnType.TXN_CASH.getTxnCode() == txn) {
            // 检查资金帐户提现申请
            if ("1".equals(paymentInfo.getInnerTxnType().substring(0, 1))
                    && !paymentInfo.getInnerTxnType().substring(2, 3).equals(account.getType())) {
                // 账户类型和交易代码中账户类型不一致，不能提现申请
                throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200027.getErrordesc());
            }
            if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
                // 资金账户状态不为正常，不能提现申请
                throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200027.getErrordesc());
            }
            if (account.getAvailableBalance() < paymentInfo.getAmount()) {
                // 可用余额小于提现金额和提现手续费，不能提现申请
                throw new BizException(BussErrorCode.ERROR_CODE_200020.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200020.getErrordesc());
            }
            if (account.getWithdrawBalance() < paymentInfo.getAmount()) {
                // 金额大于可用提现额度，不能提现
                throw new BizException(BussErrorCode.ERROR_CODE_200126.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200126.getErrordesc());
            }
        }
        if (TxnType.TXN_CASH_FROCE.getTxnCode() == txn) {
            // 检查资金帐户运用强制提现申请
            if ("1".equals(paymentInfo.getInnerTxnType().substring(0, 1))
                    && !paymentInfo.getInnerTxnType().substring(2, 3).equals(account.getType())) {
                // 账户类型和交易代码中账户类型不一致，不能提现申请
                throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200027.getErrordesc());
            }
            if (account.getAvailableBalance() < paymentInfo.getAmount()) {
                // 可用余额小于提现金额和提现手续费，不能提现申请
                throw new BizException(BussErrorCode.ERROR_CODE_200020.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200020.getErrordesc());
            }
            if (account.getWithdrawBalance() < paymentInfo.getAmount()) {
                // 金额大于可用提现额度，不能提现
                throw new BizException(BussErrorCode.ERROR_CODE_200126.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200126.getErrordesc());
            }
        }
        if (TxnType.TXN_PREAUTH_END.getTxnCode() == txn) {
            // 检查资金帐户预授权完成
            if ("1".equals(paymentInfo.getInnerTxnType().substring(0, 1))
                    && !paymentInfo.getInnerTxnType().substring(2, 3).equals(account.getType())) {
                // 账户类型和交易代码中账户类型不一致，不能预授权完成
                throw new BizException(BussErrorCode.ERROR_CODE_200027.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200027.getErrordesc());
            }
            if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
                // 资金账户状态不为正常，不能预授权完成
                throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200007.getErrordesc());
            }
        }
        // 通过检查*/
    }

    /**
     * 机构检查
     *
     * @param org
     * @param txnType
     * @param orgType
     */
    public void checkOrgPayment(TInfoOrg org, String txnType,
                                String orgType,boolean... isNoCheck) {
        if (org == null) {
            // 机构不存在
            throw new BizException(BussErrorCode.ERROR_CODE_220006.getErrorcode(),
                    BussErrorCode.ERROR_CODE_220006.getErrordesc());
        }          
        if (org.getBlackLabel() != null) {
            // 机构黑名单
            throw new BizException(BussErrorCode.ERROR_CODE_220008.getErrorcode(),
                    BussErrorCode.ERROR_CODE_220008.getErrordesc());
        }
        if (orgType != null && !org.getOrgType().equals(orgType)) {
            throw new BizException(BussErrorCode.ERROR_CODE_220009.getErrorcode(),
                    BussErrorCode.ERROR_CODE_220009.getErrordesc());
        }
        if (!OrgStatus.ORG_STATUS_NORMAL.getStatus().equals(org.getOrgStatus())) {
            // 机构状态不为正常
            throw new BizException(BussErrorCode.ERROR_CODE_220007.getErrorcode(),
                    BussErrorCode.ERROR_CODE_220007.getErrordesc());
        }
        //冲正交易不需要检验交易类型权限
        if (isNoCheck.length > 0 && isNoCheck[0]) {       	
        	return;
        }    
        if (org.getAllowTrans() == null
                || org.getAllowTrans().indexOf(txnType + ",") == -1) {
            // 机构无权进行该交易
            throw new BizException(BussErrorCode.ERROR_CODE_220024.getErrorcode(),
                    BussErrorCode.ERROR_CODE_220024.getErrordesc());
        }      
    }
    /**
	 * 用户证件类型、证件号码为“其他、手机号”时，则不校验用户信息匹配情况
	 * 
	 * @param customer
	 * @param managerLog
	 * @return
	 */
	public void checkCustomerInfo(TInfoCustomer customer,
			ManagerLog managerLog) {
		
		String telePhone = customer.getMobileNo();
		if (telePhone == null || "".equals(telePhone)) {
			//telePhone = customer.getProductNo();
		}
		//判断高级实名信息，暂不需要
		//customerService.setRealnameCustomer(customer);
		if (!"X".equals(managerLog.getIdtype().toUpperCase()) || !telePhone.equals(managerLog.getIdno())) {
			if (!customer.getIdentifyType().equals(managerLog.getIdtype())
					|| !checkPid(managerLog.getIdtype(), managerLog.getIdno(), customer.getIdentifyNo())
					|| !customer.getName().equals(managerLog.getName())) {
				// 证件类型证件号码错误或客户姓名错误
				throw new BizException(BussErrorCode.ERROR_CODE_200038.getErrorcode(),
		                    BussErrorCode.ERROR_CODE_200038.getErrordesc());
			}
		}
		
	}
	public static boolean checkPid(String pidType, String oldPid, String newPid) {
		if (oldPid == null || newPid == null) {
			return false;
		}
		if (CustomerIdType.CUSTOMER_ID_TYPE_IDCARD.getCode().equals(pidType)) {
			if (PidUtil.convertPid(oldPid).equals(PidUtil.convertPid(newPid))) {
				return true;
			} else {
				return false;
			}
		} else {
			return oldPid.equals(newPid);
		}
	}

    
    /**
     *  查询余额，需检查客户状态
     * @param customer
     */
    public void checkCustomerManager(TInfoCustomer customer) {
    	
    	if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(customer.getCustomerStatus())
				&& !AccountStatus.ACCOUNT_STATUS_FROZEN.getStatus().equals(customer.getCustomerStatus())
				&& !AccountStatus.ACCOUNT_STATUS_LOCKED.getStatus().equals(customer.getCustomerStatus())) {
			// 状态不为正常、冻结、锁定，不能查询余额
			throw new BizException(BussErrorCode.ERROR_CODE_200011.getErrorcode(),
					BussErrorCode.ERROR_CODE_200011.getErrordesc());
		}
    }
    
    
    /**
     *  检查商户退货规则
     * @param refundRule
     * @param paymentInfo
     * @param oldLog
     */
    public void checkMerchantRefundRule(TParaMerchantRefundRule refundRule ,PaymentInfo paymentInfo,TLogOnlinePaymentHis oldLog) {
    	if (refundRule == null) {
			throw new BizException(BussErrorCode.ERROR_CODE_410009.getErrorcode(),
					BussErrorCode.ERROR_CODE_410009.getErrordesc());
		}
    	
    	if (refundRule.getTypeBitmap().substring(0, 1)
				.equals(TrueOrFalse.FALSE.getLablCode())
				&& oldLog.getTransAmount() <= oldLog.getReturnAmount() + paymentInfo.getAmount()) {
			// 判断是否允许全部退货
    		throw new BizException(BussErrorCode.ERROR_CODE_410006.getErrorcode(),
					BussErrorCode.ERROR_CODE_410006.getErrordesc());
		}
		if (refundRule.getTypeBitmap().substring(1, 2)
				.equals(TrueOrFalse.FALSE.getLablCode())
				&& !oldLog.getTransAmount().equals(paymentInfo.getAmount()) ) {
			// 判断是否允许部分退货
			throw new BizException(BussErrorCode.ERROR_CODE_410007.getErrorcode(),
					BussErrorCode.ERROR_CODE_410007.getErrordesc());
		}
		if (refundRule.getTypeBitmap().substring(2, 3)
				.equals(TrueOrFalse.FALSE.getLablCode())
				&& TrueOrFalse.TRUE.getLablCode().equals(oldLog.getReturnFlag())) {
			// 判断是否允许多次退货
			throw new BizException(BussErrorCode.ERROR_CODE_410008.getErrorcode(),
					BussErrorCode.ERROR_CODE_410008.getErrordesc());
		}
    	
    	
    }  
    
    /**
     *  检查快捷消费参数
     * @param consumePaymentInfo
     * @param chargePaymentInfo
     */
    public void quickConsumecheck(PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo) {
    	 if (!consumePaymentInfo.getAmount().equals(chargePaymentInfo.getAmount())) {
             throw new BizException(BussErrorCode.ERROR_CODE_500030.getErrorcode(),
                     BussErrorCode.ERROR_CODE_500030.getErrordesc());
         }
         if (!consumePaymentInfo.getAcceptOrgCode().equals(chargePaymentInfo.getAcceptOrgCode())) {
             throw new BizException(BussErrorCode.ERROR_CODE_500031.getErrorcode(),
                     BussErrorCode.ERROR_CODE_500031.getErrordesc());
         }
         if (!consumePaymentInfo.getSupplyOrgCode().equals(chargePaymentInfo.getSupplyOrgCode())) {
             throw new BizException(BussErrorCode.ERROR_CODE_500031.getErrorcode(),
                     BussErrorCode.ERROR_CODE_500031.getErrordesc());
         }
    	
    	
    }

    public void checkRiskBlackManage(TRiskBlackManage tRiskBlackManage, String blackType) {
        // 银行卡黑名单
        if (RiskBlackType.BLACK_TYPE_BANKCARD.getTypeCode().equals(blackType) && null != tRiskBlackManage) {
            throw new BizException(BussErrorCode.ERROR_CODE_200150.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200150.getErrordesc());
        }
        // 身份证黑名单
        if (RiskBlackType.BLACK_TYPE_IDNO.getTypeCode().equals(blackType) && null != tRiskBlackManage) {
            throw new BizException(BussErrorCode.ERROR_CODE_200151.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200151.getErrordesc());
        }
    }

	/*
	 * 现金账户的客户号校验
	 */
	public void checkCustomerNo(String customerNo, String targetCustomerNo){
		if (Strings.isNullOrEmpty(customerNo) || Strings.isNullOrEmpty(targetCustomerNo) || !customerNo.equals(targetCustomerNo) ){
			throw new BizException(BussErrorCode.ERROR_CODE_200051.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200051.getErrordesc());
    	}
	}
	
	/*
	 * 校验指定地区是否关闭了OTA功能
	 */
	public void checkAreaOta(String areacode){
		TRiskAreaOta areaOta = cacheComponent.getAreaOtaByAreacode(areacode);
		if (areaOta != null && TrueOrFalse.FALSE.getLablCode().equals(areaOta.getOtaFlag())){
			throw new BizException(BussErrorCode.ERROR_CODE_220026.getErrorcode(),
                    BussErrorCode.ERROR_CODE_220026.getErrordesc());
		}
	}
	
	/**
	 * 检验卡TAC
	 * 
	 * @param paymentInfo
	 * @param tInfoOfflineAccount
	 */
	public void checkCardTAC(PaymentInfo paymentInfo, TInfoAccount tInfoOfflineAccount){
		String tacTxnAmount = StringUtil.fillLeft(
				Integer.toHexString((int) paymentInfo.getAmount().longValue()), '0', 8).toUpperCase();
		String tacTxnType = "06";
		String tacLabel = tacTxnAmount + tacTxnType + paymentInfo.getTerminalNo()
				+ paymentInfo.getTerminalSeqNo() + paymentInfo.getAcceptDate() + paymentInfo.getAcceptTime();
		boolean b = pinkeyComponent.checkTac(tInfoOfflineAccount.getCardNo(), paymentInfo.getCardTAC(), tacLabel);
		if (!b) {
			throw new BizException(BussErrorCode.ERROR_CODE_210008.getErrorcode(),
                    BussErrorCode.ERROR_CODE_210008.getErrordesc());
		}
	}
	
	/**
	 * 检查用户实名等级是否高于省份的设置
	 * 
	 * @param userGrade
	 * @param tmpGrade
	 * @return true：用户的实名等级不低于省份的实名等级；false：用户的实名等级不正确或者低于省份的实名等级
	 */
    public boolean txnAuthCheck(String userGrade, String tmpGrade) {
    	boolean result = true;
    	// 省份未设置用户实名等级认为所有用户实名等级均通过
    	// 账户的实名等级未设置或者实名等级不正确不通过
    	if(Strings.isNullOrEmpty(userGrade) || getCustomerGradeIndex(userGrade) == -1 ||
    			(!Strings.isNullOrEmpty(tmpGrade) && getCustomerGradeIndex(tmpGrade) == -1) ||
    			getCustomerGradeIndex(userGrade) < getCustomerGradeIndex(tmpGrade)){
    		result = false;
    	}
    	return result;
    }
    
    /**
     * 查询实名认证等级在枚举中的序号
     * 
     * @param gradeCode
     * @return
     */
    public int getCustomerGradeIndex(String gradeCode){
    	int index = -1;
    	if(!Strings.isNullOrEmpty(gradeCode)){
    		for(int i=0 ; i < CustomerGrade.values().length ; i++){
    			if(gradeCode.equals(CustomerGrade.values()[i].getCustomerGradeCode())){
    				index = i;
    				break;
    			}
    		}
    	}
    	return index;
    }
    
}
