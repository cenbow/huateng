package com.huateng.p3.account.commonservice;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.ServiceHelper.TxnFeeCaculator;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.CustomerIdType;
import com.huateng.p3.account.common.enummodel.CustomerRealnameExemine;
import com.huateng.p3.account.common.enummodel.CustomerRealnameStatus;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.RiskBlackType;
import com.huateng.p3.account.common.enummodel.TrueFalseLabel;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnChannel;
import com.huateng.p3.account.common.enummodel.TxnForm;
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnLabel;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.Base64DealImageUtil;
import com.huateng.p3.account.common.util.BeanMapper;
import com.huateng.p3.account.common.util.DateUtil;
import com.huateng.p3.account.common.util.PidUtil;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.component.SecurityComponent;
import com.huateng.p3.account.persistence.TDictNewOldRelationMapper;
import com.huateng.p3.account.persistence.TInfoAccountMapper;
import com.huateng.p3.account.persistence.TInfoSubaccountMapper;
import com.huateng.p3.account.persistence.TLogAccountManagementMapper;
import com.huateng.p3.account.persistence.TLogAccountPaymentMapper;
import com.huateng.p3.account.persistence.TLogCashApplyMapper;
import com.huateng.p3.account.persistence.TLogDubiousTxnMapper;
import com.huateng.p3.account.persistence.TLogOfflinePaymentMapper;
import com.huateng.p3.account.persistence.TLogOnlinePaymentHisMapper;
import com.huateng.p3.account.persistence.TLogOnlinePaymentMapper;
import com.huateng.p3.account.persistence.TRealnameApplyMapper;
import com.huateng.p3.account.persistence.models.TDictNewOldRelation;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TInfoSubaccount;
import com.huateng.p3.account.persistence.models.TLogAccountManagement;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.account.persistence.models.TLogCashApply;
import com.huateng.p3.account.persistence.models.TLogDubiousTxn;
import com.huateng.p3.account.persistence.models.TLogOfflinePayment;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;
import com.huateng.p3.account.persistence.models.TRealnameApply;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */

@Component
public class AccountService {
	
    @Value("${storeImageDirectory}")
    private String storeImageDirectory; 
        
    @Autowired
    private TxnFeeCaculator txnFeeCaculator;

    @Autowired
    private TInfoAccountMapper tInfoAccountMapper;

    @Autowired
    private TLogOnlinePaymentMapper tLogOnlinePaymentMapper;
    
    @Autowired
    private TLogOnlinePaymentHisMapper tLogOnlinePaymentHisMapper;

    @Autowired
    private TLogAccountPaymentMapper tLogAccountPaymentMapper;
    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private SecurityComponent securityComponent;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private TInfoSubaccountMapper tInfoSubaccountMapper;
    
    @Autowired
    private TLogAccountManagementMapper tLogAccountManagementMapper;
    
    @Autowired
    private TRealnameApplyMapper  tRealnameApplyMapper;
    
    @Autowired
    private TDictNewOldRelationMapper  tDictNewOldRelationMapper;
    
    @Autowired
    private TLogCashApplyMapper  tLogCashApplyMapper;   
    
    @Autowired
    private RiskService riskService;
    
    @Autowired
    private TxnCheckService txnCheckService;
    
    @Autowired
    private OrgService orgService;
    /**
     * 快捷实名申请人
     */
    private String SHORTCUT = "shortcut";

    @Autowired
    private TLogOfflinePaymentMapper tLogOfflinePaymentMapper;
    
    @Autowired
    private TLogDubiousTxnMapper tLogDubiousTxnMapper;

    public TInfoAccount getAccountForUpdate(String accountKey, KeyType keyType) {
        TInfoAccount account = null;// 交易主账户
        switch (keyType) {
            case ACCOUNT:
                account = tInfoAccountMapper.findFundAccountByAccountNoForUpdate(accountKey);
                break;
            case UNIFY:
                account = tInfoAccountMapper.findFundAccountByUnifyIdForUpdate(accountKey);
                break;
            case CARD:
                account = tInfoAccountMapper.findFundAccountByCardNoForUpdate(accountKey);
                break;
            case CUSTOMER:
                account = tInfoAccountMapper.findFundAccountByCustomerNoForUpdate(accountKey);
                break;
            case ORGCODE:
                account = tInfoAccountMapper.findFundAccountByOrgCodeForUpdate(accountKey);
                break;
        }
        if (null == account) {
            throw new BizException(BussErrorCode.ERROR_CODE_200130.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200130.getErrordesc());
        }
        return account;
    }
    


    public TInfoAccount getAccount(String accountKey, KeyType keyType) {
        TInfoAccount account = null;// 交易主账户
        switch (keyType) {
            case ACCOUNT:
                account = tInfoAccountMapper.findFundAccountByAccountNo(accountKey);
                break;
            case UNIFY:
                account = tInfoAccountMapper.findFundAccountByUnifyId(accountKey);
                break;
            case CARD:
                account = tInfoAccountMapper.findFundAccountByCardNo(accountKey);
                break;
            case CUSTOMER:
                account = tInfoAccountMapper.findFundAccountByCustomerNo(accountKey);
                break;
            case ORGCODE:
                account = tInfoAccountMapper.findFundAccountByOrgCode(accountKey);
                break;
        }
        if (null == account) {
            throw new BizException(BussErrorCode.ERROR_CODE_200130.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200130.getErrordesc());
        }
        return account;
    }

    public TInfoAccount getIntegralAccount(String accountKey, KeyType keyType) {
        TInfoAccount account = null;// 交易主账户
        switch (keyType) {
            case CUSTOMER:
                account = tInfoAccountMapper.findIntegralAccountByCustomerNo(accountKey);
                break;
        }
        if (null == account) {
            throw new BizException(BussErrorCode.ERROR_CODE_200090.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200090.getErrordesc());
        }
        return account;
    }

    public List<TInfoAccount> findAccountsAsListByCustomerNo(String customerNo, String type){
    	
    	List<TInfoAccount> accountList = tInfoAccountMapper.findAccountsAsListByCustomerNo(customerNo, type);
        if (null == accountList || accountList.isEmpty()) {
            throw new BizException(null, BussErrorCode.ERROR_CODE_200013.getErrorcode());
            
        }
        return accountList;
    }
    


    /**
     * 查找提现记录
     * @param transSeqNo
     * @return
     */
    public TLogCashApply selectTLogCashApplyByPk(String transSeqNo){
    	
    	TLogCashApply logCashApply = tLogCashApplyMapper.selectByTxnseqNo(transSeqNo);
        if (logCashApply==null) {
            throw new BizException(null, BussErrorCode.ERROR_CODE_200013.getErrorcode());
            
        }
        return logCashApply;
    }
    
    /**
     * 插入体现记录
     */
    public void insertTlogCash(TLogCashApply record){   	
    	 tLogCashApplyMapper.insert(record);
    }
    /**
     * 更新体现记录
     */
    public void updateTLogCashApply(TLogCashApply record){   	
   	 tLogCashApplyMapper.updateByPrimaryKeySelective(record);
   }
    
    

    public int increaseTxnPasswdErrorNum(TInfoAccount account) {
        return tInfoAccountMapper.increaseTxnPasswdErrorNum(account.getPasswdErrNum(), account.getPasswdErrLockTime(),
                account.getAccountNo());
    }

    public TInfoAccountMapper getTInfoAccountMapper() {
        return tInfoAccountMapper;
    }

    /**
     * 账户交易入清结算
     */
    public TLogOnlinePayment accountClearInDb(TInfoOrg supplyOrg, TInfoOrg acceptOrg, PaymentInfo paymentInfo,
                                              PaymentInfo oldPaymentInfo, TInfoCustomer tInfoCustomer, TInfoAccount account, String txnSeqNo,
                                              String oldOnlineTxnSeqNo, String isClearing, boolean... isIncrease) {

        if (Strings.isNullOrEmpty(paymentInfo.getTxnDscpt())) {
            String txnDscpt =
                    supplyOrg.getOrgFname()
                            + cacheComponent.getTxnTypeObj(paymentInfo.getInnerTxnType()).getTxnName();
            paymentInfo.setTxnDscpt(txnDscpt);
        }
        TLogOnlinePayment tLogOnlinePayment = new TLogOnlinePayment();
        tLogOnlinePayment.setTransSerialNo(txnSeqNo);
        tLogOnlinePayment.setAcceptOrgCode(paymentInfo.getAcceptOrgCode());
        tLogOnlinePayment.setAcceptOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());// CodeGenerator.extractOrgType(paymentInfo.getAcceptOrgCode()));
        tLogOnlinePayment.setAreaCode(supplyOrg.getAreaCode());
        tLogOnlinePayment.setCityCode(supplyOrg.getCityCode());
        tLogOnlinePayment.setTransSerialType(paymentInfo.getTxnSeqType().getSeqType());
        tLogOnlinePayment.setAcceptOrgSerialNo(paymentInfo.getAcceptTxnSeqNo());
        tLogOnlinePayment.setAcceptOrgTransDate(paymentInfo.getAcceptDate());
        tLogOnlinePayment.setAcceptOrgTransTime(paymentInfo.getAcceptTime());
        tLogOnlinePayment.setPayOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());
        
        if(account != null){
	        tLogOnlinePayment.setAccountNo(account.getAccountNo());
	        tLogOnlinePayment.setAccountType(account.getType());
	        tLogOnlinePayment.setPayOrgCode(Strings.isNullOrEmpty(paymentInfo.getPayOrgCode())?account.getApanage():paymentInfo.getPayOrgCode());
	        
	        tLogOnlinePayment.setBeforeTransAmount(account.getBalance());
	        //TODO 增值交易 这里有问题 add by huwenjie 暂时使用新参数判断 需改造成交易类型判断借贷关系
	        if (isIncrease.length > 0 && isIncrease[0]) {
	            tLogOnlinePayment.setAfterTransAmount(account.getBalance() + paymentInfo.getAmount());
	        } else {
	            tLogOnlinePayment.setAfterTransAmount(account.getBalance() - paymentInfo.getAmount());
	            
	            if (TxnCheckService.withdrawbalanceTxnSet.contains(paymentInfo.getInnerTxnType())) {
	                
	                tLogOnlinePayment.setResvFld1(paymentInfo.getAmount().toString());
	                
	            } else {
	            	tLogOnlinePayment.setResvFld1(account.getAvailableBalance() - paymentInfo.getAmount() < account
	                        .getWithdrawBalance() ? String.valueOf(account.getWithdrawBalance() - account.getAvailableBalance() + paymentInfo.getAmount())
	                        : "0");
	            }
	           
	            
	        }
        }
        tLogOnlinePayment.setExtBusinessType(paymentInfo.getBussinessType());
        tLogOnlinePayment.setInteriorTransType(paymentInfo.getInnerTxnType());
        tLogOnlinePayment.setResendNum(Long.valueOf(0));
        tLogOnlinePayment.setTransAmount(paymentInfo.getAmount());
        tLogOnlinePayment.setAcceptChannel(paymentInfo.getChannel());
        tLogOnlinePayment.setTransMemo(paymentInfo.getTxnDscpt());
        tLogOnlinePayment.setTransTime(paymentInfo.getTxnDate());
        tLogOnlinePayment.setTransMonth(Integer.valueOf(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMM")));
        tLogOnlinePayment.setSupplyOrgCode(supplyOrg.getOrgCode());
        tLogOnlinePayment.setSupplyOrgType(OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        tLogOnlinePayment.setIsClearing(isClearing);
        tLogOnlinePayment.setTerminalNo(paymentInfo.getTerminalNo());
        String terminalseq = Strings.isNullOrEmpty(paymentInfo.getTerminalSeqNo()) ? paymentInfo.getAcceptTxnSeqNo() : paymentInfo.getTerminalSeqNo();
        tLogOnlinePayment.setTerminalCommSerialNo(terminalseq);
        if( tInfoCustomer !=null){
	        tLogOnlinePayment.setCustomerNo(tInfoCustomer.getCustomerNo());
	        tLogOnlinePayment.setCustomerAreaCode(tInfoCustomer.getAreaCode());
	        tLogOnlinePayment.setCustomerCityCode(tInfoCustomer.getCityCode());
        }
        tLogOnlinePayment.setlTransSerialNo(oldOnlineTxnSeqNo);
        if (null != oldPaymentInfo) {
            tLogOnlinePayment.setlAcceptOrgTransDate(oldPaymentInfo.getAcceptDate());
            tLogOnlinePayment.setlAcceptOrgSerialNo(oldPaymentInfo.getAcceptTxnSeqNo());
            tLogOnlinePayment.setlAcceptOrgTransTime(oldPaymentInfo.getAcceptTime());            
            //退货标示
            tLogOnlinePayment.setSupplyFeeFlag(paymentInfo.getSupplyFeeFlag());
            tLogOnlinePayment.setPayFeeFlag(paymentInfo.getPayFeeFlag());
        }
        
        tLogOnlinePaymentMapper.insert(tLogOnlinePayment);
        return tLogOnlinePayment;
    }

   public TLogAccountManagement accountManInDb(ManagerLog managerLog,TInfoAccount tInfoAccount,TInfoOrg acceptOrg)
   {
	   if (Strings.isNullOrEmpty(managerLog.getTxnDscpt())) {
           String txnDscpt =
        		   acceptOrg.getOrgFname()
                           + cacheComponent.getTxnTypeObj(managerLog.getInnerTxnType()).getTxnName();
           managerLog.setTxnDscpt(txnDscpt);
       }	
	 
	   TLogAccountManagement tLogAccountManagement = new TLogAccountManagement();
	   tLogAccountManagement.setAcceptDate(managerLog.getAcceptDate());
	   tLogAccountManagement.setAcceptTime(managerLog.getAcceptTime());
	   tLogAccountManagement.setAcceptOrgSerialNo(managerLog.getAcceptTxnSeqNo());
	   tLogAccountManagement.setAccountNo(tInfoAccount.getAccountNo());
	   tLogAccountManagement.setAccountType(tInfoAccount.getType());
	   tLogAccountManagement.setAfterStatus(tInfoAccount.getStatus());
	   tLogAccountManagement.setBeforeStatus(managerLog.getBeforeStatus());
	   Timestamp tsCheckTime = new Timestamp(managerLog.getCheckTime().getTime());//转换为timeStamp
	   tLogAccountManagement.setCheckTime(tsCheckTime);//CheckTime
	   tLogAccountManagement.setCheckUid(managerLog.getCheckUid());
	   tLogAccountManagement.setCustomerNo(tInfoAccount.getCustomerNo());
	   tLogAccountManagement.setFeeAmount(managerLog.getFeeAmt());
	   tLogAccountManagement.setFeeFlag(managerLog.getFeeFlag());
	   Timestamp tsInputTime = new Timestamp(managerLog.getInputTime().getTime());//转换为timeStamp
	   tLogAccountManagement.setInputTime(tsInputTime);//inputtime
	   tLogAccountManagement.setInputUid(managerLog.getInputUid());
	   tLogAccountManagement.setOrgCode(acceptOrg.getOrgCode());
	   tLogAccountManagement.setAcceptChannel(managerLog.getTxnChannel());
	   //Timestamp tsTransTime = new Timestamp(tInfoAccount.getLastUpdateTime().getTime());//转换为timeStamp
	   tLogAccountManagement.setTransTime(null);//transTime
	   tLogAccountManagement.setTransMemo(managerLog.getTxnDscpt());
	   tLogAccountManagement.setTransMonth(Integer.parseInt(new DateTime(tLogAccountManagement.getTransTime()).toString("yyyyMM")));
	   tLogAccountManagement.setTransSerialNo(sequenceGenerator.generateAccountMgmTxnSeq());	   
	   tLogAccountManagement.setTransType(managerLog.getInnerTxnType());
	   tLogAccountManagementMapper.insert(tLogAccountManagement);
	   return tLogAccountManagement;
   }


    /*
    * 插入账户支付交易日志，根据是否增值设置最后一个参数true
    */
    public void accounTxnInDb(PaymentInfo paymentInfo, TInfoAccount account,
                              TInfoOrg acceptOrg, String cleaningTxnSeqNo, boolean... isIncrease) {
        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogAccountPayment tLogAccountPayment = new TLogAccountPayment();
        tLogAccountPayment.setAcceptOrgCode(paymentInfo.getAcceptOrgCode());
        tLogAccountPayment.setAcceptOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());
        tLogAccountPayment.setAccountNo(account.getAccountNo());
        tLogAccountPayment.setAccountType(account.getType());
        tLogAccountPayment.setBeforeAmt(account.getBalance());
        if (isIncrease.length > 0 && isIncrease[0]) {
        	tLogAccountPayment.setAfterAmt(account.getBalance() + paymentInfo.getAmount());
        }
        else{
        	tLogAccountPayment.setAfterAmt(account.getBalance() - paymentInfo.getAmount());
        }
        tLogAccountPayment.setAreaCode(acceptOrg.getAreaCode());
        tLogAccountPayment.setCityCode(acceptOrg.getCityCode());
        tLogAccountPayment.setBusinessType(paymentInfo.getBussinessType());
        tLogAccountPayment.setTransSeqType(paymentInfo.getTxnSeqType().getSeqType());
        tLogAccountPayment.setTxnAmt(paymentInfo.getAmount());
        tLogAccountPayment.setTxnChannel(paymentInfo.getChannel());
        tLogAccountPayment.setTxnDscpt(paymentInfo.getTxnDscpt());
        tLogAccountPayment.setTxnSeqNo(txnSeqNo);
        tLogAccountPayment.setTxnTime(DateTime.now().toDate());
        tLogAccountPayment.setTxnMonth(Integer.parseInt(DateTime.now().toString("yyyyMM")));
        tLogAccountPayment.setTxnType(paymentInfo.getInnerTxnType());
        tLogAccountPayment.setClearingTxnSeqNo(cleaningTxnSeqNo);
        tLogAccountPayment.setCustomerNo(account.getCustomerNo());
        tLogAccountPayment.setAcceptTransDate(paymentInfo.getAcceptDate());
        tLogAccountPayment.setAcceptTransTime(paymentInfo.getAcceptTime());
        //tLogAccountPayment.setInnerCardNo(cardNo);
        tLogAccountPayment.setTxnLabel(TxnLabel.TXN_LABL_ONLINE.getLablCode());
        String terminalseq = Strings.isNullOrEmpty(paymentInfo.getTerminalSeqNo()) ? paymentInfo.getAcceptTxnSeqNo() : paymentInfo.getTerminalSeqNo();
        tLogAccountPayment.setResvFld1(paymentInfo.getAcceptTxnSeqNo());
        tLogAccountPayment.setResvFld2(terminalseq);
        tLogAccountPaymentMapper.insert(tLogAccountPayment);
    }

    /**
     * 账户变动入库 减值
     */
    public Long accountDecreaseAlterInDb(TInfoAccount account, PaymentInfo paymentInfo,String oldTxnType,boolean... isRollbackFake) {
        //伪造冲正不需要资金变动
        if (isRollbackFake.length > 0 && isRollbackFake[0]) {
            return account.getBalance();
        }
        // 重置老核心风控
     	resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
        account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setBalance(account.getBalance() - paymentInfo.getAmount());
        account.setAvailableBalance(account.getAvailableBalance() - paymentInfo.getAmount());

        // 根据内部交易类型判断是否动用可提现余额，增加撤消冲正时的原交易的内部交易类型判断
        if (TxnCheckService.withdrawbalanceTxnSet.contains(paymentInfo.getInnerTxnType()) || TxnCheckService.withDrawBalanceChargeTxnSet.contains(oldTxnType)
        		|| TxnCheckService.withdrawbalanceTxnSet.contains(oldTxnType)) {
            //转账时需处理是否存在主子关系
            if (!account.isExistMainSubRelation()) {
                // 如果存在主子账户关系,转出账户的可提现余额不变动
                // 减去提现额度
            	account.setWithdrawBalance(account.getWithdrawBalance()
                        - paymentInfo.getAmount() >0 ?account.getWithdrawBalance()
                                - paymentInfo.getAmount():0);
               
            }
        } else {
            account.setWithdrawBalance(account.getAvailableBalance() < account
                    .getWithdrawBalance() ? account.getAvailableBalance()
                    : account.getWithdrawBalance());
        }
        //TODO  加密机OK 需要开启
        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }

    /**
     * 提现确认数据库变动   *
     *
     * @param account
     * @param paymentInfo
     * @return
     */
    public Long accountCashCommitAlterInDb(TInfoAccount account, PaymentInfo paymentInfo) {
          /*
		 * 更新账户信息
		 */
    	// 重置老核心风控
    	resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
        account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setBalance(account.getBalance() - paymentInfo.getAmount());
        account.setFrozenAmount(account.getFrozenAmount() - paymentInfo.getAmount());
        account.setUnavailableBalance(account.getUnavailableBalance()
                - paymentInfo.getAmount());

        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 更新账户关键数据密文报文
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }

    ;

    /**
     * 提现申请数据库变动
     *
     * @param account
     * @param paymentInfo
     * @return
     */
    public Long cashAccountAlter(TInfoAccount account, PaymentInfo paymentInfo) {
    	// 重置老核心风控
    	resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
    	account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setAvailableBalance(account.getAvailableBalance() - paymentInfo.getAmount());
        
        if (TxnCheckService.withdrawbalanceTxnSet.contains(paymentInfo.getInnerTxnType())) {         
           account.setWithdrawBalance(account.getWithdrawBalance()
                        - paymentInfo.getAmount());            
        } else {
            account.setWithdrawBalance(account.getAvailableBalance() < account
                    .getWithdrawBalance() ? account.getAvailableBalance()
                    : account.getWithdrawBalance());
        }
        account.setUnavailableBalance(account.getUnavailableBalance()
                + paymentInfo.getAmount());
        account.setFrozenAmount(account.getFrozenAmount() + paymentInfo.getAmount());
        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 更新账户关键数据密文报文
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }

    /**
     * 提现申请撤销账户变动
     *
     * @param account
     * @param paymentInfo
     * @return
     */
    public Long cashApplyCancelAccountAlter(TInfoAccount account, PaymentInfo paymentInfo ,String oldTxnType) {
    	// 重置老核心风控
    	resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
    	account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setAvailableBalance(account.getAvailableBalance() + paymentInfo.getAmount());
        //退货冲正撤销时原交易为扣可提现余额，退货时直接加上可提现余额
        if (TxnCheckService.withdrawbalanceTxnSet.contains(oldTxnType)) {
        	// 加上提现额度
            account.setWithdrawBalance(account.getWithdrawBalance()
                    + paymentInfo.getAmount());
        	
        }
       
        account.setUnavailableBalance(account.getUnavailableBalance()
                - paymentInfo.getAmount());
        account.setFrozenAmount(account.getFrozenAmount() - paymentInfo.getAmount());
        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 更新账户关键数据密文报文
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }
    
    
    
    /**
     * 预授权确认数据库变动   *
     *
     * @param account
     * @param paymentInfo
     * @return
     */
    public Long accountPreAuthCommitAlterInDb(TInfoAccount account, PaymentInfo paymentInfo , TLogPreauthApply oldPreauthApply) {
         /*
		 * 更新账户信息
		 */
    	// 重置老核心风控
    	resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
        account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setBalance(account.getBalance() - paymentInfo.getAmount());
		account.setAvailableBalance(account.getAvailableBalance()
				+ oldPreauthApply.getAuthAmt() - paymentInfo.getAmount());
        account.setFrozenAmount(account.getFrozenAmount() - oldPreauthApply.getAuthAmt());
        account.setPreauthorizedAmt(account.getPreauthorizedAmt()
                - oldPreauthApply.getAuthAmt());
        account.setWithdrawBalance(account.getAvailableBalance() < account
                .getWithdrawBalance() ? account.getAvailableBalance()
                : account.getWithdrawBalance());

        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 更新账户关键数据密文报文
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }

    ;

    /**
     * 预授权申请数据库变动
     *
     * @param account
     * @param paymentInfo
     * @return
     */
    public Long preAuthAccountAlter(TInfoAccount account, PaymentInfo paymentInfo) {
    	// 重置老核心风控
    	resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
    	account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setAvailableBalance(account.getAvailableBalance() - paymentInfo.getAmount());
        account.setPreauthorizedAmt(account.getPreauthorizedAmt()
                + paymentInfo.getAmount());
        account.setFrozenAmount(account.getFrozenAmount() + paymentInfo.getAmount());
        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 更新账户关键数据密文报文
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }

    /**
     * 预授权申请撤销账户变动
     *
     * @param account
     * @param paymentInfo
     * @return
     */
    public Long preAuthApplyCancelAccountAlter(TInfoAccount account, PaymentInfo paymentInfo ,String oldTxnType) {
    	// 重置老核心风控
    	resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
    	account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setAvailableBalance(account.getAvailableBalance() + paymentInfo.getAmount());
        
        account.setPreauthorizedAmt(account.getPreauthorizedAmt()
                - paymentInfo.getAmount());
        account.setFrozenAmount(account.getFrozenAmount() - paymentInfo.getAmount());
        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));       // 更新账户关键数据密文报文
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }

    /**
     * 交易手续费计算入清结算和交易日志
     *
     * @param paymentInfo
     * @param tInfoAccount
     * @param tInfoCustomer
     * @param acceptOrg
     * @param merchantOrg
     * @return
     */
    public TLogOnlinePayment feeCatulatorInDb(PaymentInfo paymentInfo, TInfoAccount tInfoAccount, TInfoCustomer tInfoCustomer,
                                 TInfoOrg acceptOrg, TInfoOrg merchantOrg, String oldTxnSeqNo) {

        long fee = txnFeeCaculator.calculateTxnFee(paymentInfo.getInnerTxnType(), paymentInfo.getAmount());
        if (fee <= 0) {
            return null;
        }
        //手续费需要一个新的paymentInfo，不修改原来的。
        PaymentInfo feePaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, feePaymentInfo);     
        feePaymentInfo.setAmount(fee);
        feePaymentInfo.setInnerTxnType(TxnInnerType.TXN_TYPE_280010.getTxnInnerType());
        feePaymentInfo.setBussinessType(TxnInnerType.TXN_TYPE_280010.getTxnInnerType());
        feePaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_FEE);
        feePaymentInfo.setTxnType(TxnType.TXN_CONSUME);      
        String txnfeeSeqNo = sequenceGenerator.generatorTxnSeqNo();
        //清结算记录
        TLogOnlinePayment tFeeLogOnlinePayment =accountClearInDb(merchantOrg, acceptOrg, feePaymentInfo, null,
                tInfoCustomer, tInfoAccount, txnfeeSeqNo, oldTxnSeqNo, TrueFalseLabel.TRUE.getLablCode());
        //交易库
        accounTxnInDb(feePaymentInfo, tInfoAccount, acceptOrg, txnfeeSeqNo);
        //账户表更新
        accountDecreaseAlterInDb(tInfoAccount, feePaymentInfo ,null);
        return tFeeLogOnlinePayment;

    }

    /**
     * 取消 交易手续费
     *
     * @param paymentInfo
     * @param tInfoAccount
     * @param tInfoCustomer
     * @param acceptOrg
     * @param merchantOrg
     * @return
     */
    public TLogOnlinePayment cancelFeeCatulatorInDb(PaymentInfo paymentInfo, TInfoAccount tInfoAccount, TInfoCustomer tInfoCustomer,
                                       TInfoOrg acceptOrg, TInfoOrg merchantOrg, String oldNormalTxnSeqNo) {

        TLogOnlinePayment oldFeeOnlicePayment = tLogOnlinePaymentMapper.findFeeOnlineLogByNormalLog(oldNormalTxnSeqNo);
                       
        if (null == oldFeeOnlicePayment) {
            return null;
        }
        //构造原交易paymentInfo
        PaymentInfo oldFeepaymentInfo = new PaymentInfo();        
        oldFeepaymentInfo.setAcceptDate(oldFeeOnlicePayment.getAcceptOrgTransDate());
        oldFeepaymentInfo.setAcceptTxnSeqNo(oldFeeOnlicePayment.getAcceptOrgSerialNo());
        oldFeepaymentInfo.setAcceptTime(oldFeeOnlicePayment.getAcceptOrgTransTime());     
        //手续费需要一个新的paymentInfo，不修改原来的。
        PaymentInfo cancelFeePaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, cancelFeePaymentInfo);     
    	cancelFeePaymentInfo.setAmount(oldFeeOnlicePayment.getTransAmount());
    	cancelFeePaymentInfo.setInnerTxnType(TxnInnerType.TXN_TYPE_280020.getTxnInnerType());
    	cancelFeePaymentInfo.setBussinessType(TxnInnerType.TXN_TYPE_280020.getTxnInnerType());
    	cancelFeePaymentInfo.setTxnType(TxnType.TXN_CHARGE);
    	cancelFeePaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_FEE);
        String cancelTxnfeeSeqNo = sequenceGenerator.generatorTxnCancelSeqNo();
        //手续费清算撤销
        oldFeeOnlicePayment.setCancelFlag(TrueFalseLabel.TRUE.getLablCode());
        oldFeeOnlicePayment.setCancelTxnSerialNo(cancelTxnfeeSeqNo);
        tLogOnlinePaymentMapper.updateByPrimaryKeySelective(oldFeeOnlicePayment); 
        //手续费交易日志撤销
        cancelOldLogAccountPayment(oldFeeOnlicePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
                     
        TLogOnlinePayment tCancelFeeLogOnlinePayment =accountClearInDb(merchantOrg, acceptOrg, cancelFeePaymentInfo, oldFeepaymentInfo,
                tInfoCustomer, tInfoAccount, cancelTxnfeeSeqNo, oldFeeOnlicePayment.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode(), true);
        //交易库
        accounTxnInDb(cancelFeePaymentInfo, tInfoAccount, acceptOrg, cancelTxnfeeSeqNo);
        //账户表更新
        accountIncreaseAlterInDb(tInfoAccount, cancelFeePaymentInfo , null , null);
        return tCancelFeeLogOnlinePayment;
    }
    
    
    /**
     * 冲正 交易手续费
     *
     * @param paymentInfo
     * @param tInfoAccount
     * @param tInfoCustomer
     * @param acceptOrg
     * @param merchantOrg
     * @param oldNormalTxnSeqNo
     * @return
     */
    public TLogOnlinePayment rollbackFeeCatulatorInDb(PaymentInfo paymentInfo, TInfoAccount tInfoAccount, TInfoCustomer tInfoCustomer,
                                       TInfoOrg acceptOrg, TInfoOrg merchantOrg, String oldNormalTxnSeqNo) {

        TLogOnlinePayment oldFeeOnlicePayment = tLogOnlinePaymentMapper.findFeeOnlineLogByNormalLog(oldNormalTxnSeqNo);
                       
        if (null == oldFeeOnlicePayment) {
            return null;
        }
        //构造原交易paymentInfo
        PaymentInfo oldFeepaymentInfo = new PaymentInfo();        
        oldFeepaymentInfo.setAcceptDate(oldFeeOnlicePayment.getAcceptOrgTransDate());
        oldFeepaymentInfo.setAcceptTxnSeqNo(oldFeeOnlicePayment.getAcceptOrgSerialNo());
        oldFeepaymentInfo.setAcceptTime(oldFeeOnlicePayment.getAcceptOrgTransTime());     
        //手续费需要一个新的paymentInfo，不修改原来的。
        PaymentInfo rollbackFeePaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, rollbackFeePaymentInfo);     
    	rollbackFeePaymentInfo.setAmount(oldFeeOnlicePayment.getTransAmount());
    	rollbackFeePaymentInfo.setInnerTxnType(TxnInnerType.TXN_TYPE_280020.getTxnInnerType());
    	rollbackFeePaymentInfo.setBussinessType(TxnInnerType.TXN_TYPE_280020.getTxnInnerType());
    	rollbackFeePaymentInfo.setTxnType(TxnType.TXN_CHARGE);
    	rollbackFeePaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_FEE);
        String rollbackTxnfeeSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        //手续费清算冲正
        oldFeeOnlicePayment.setRevsalFlag(TrueFalseLabel.TRUE.getLablCode());
        oldFeeOnlicePayment.setRevsalTxnSerialNo(rollbackTxnfeeSeqNo);
        tLogOnlinePaymentMapper.updateByPrimaryKeySelective(oldFeeOnlicePayment);       
        //手续费交易日志冲正
        rollbackOldLogAccountPayment(oldFeeOnlicePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);        
        
        TLogOnlinePayment tCancelFeeLogOnlinePayment =accountClearInDb(merchantOrg, acceptOrg, rollbackFeePaymentInfo, oldFeepaymentInfo,
                tInfoCustomer, tInfoAccount, rollbackTxnfeeSeqNo, oldFeeOnlicePayment.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode(), true);
        //交易库
        accounTxnInDb(rollbackFeePaymentInfo, tInfoAccount, acceptOrg, rollbackTxnfeeSeqNo);
        //账户表更新
        accountIncreaseAlterInDb(tInfoAccount, rollbackFeePaymentInfo , null ,null);
        return tCancelFeeLogOnlinePayment;
    }


    /**
     * 账户变动入库增值
     */
    public Long accountIncreaseAlterInDb(TInfoAccount account, PaymentInfo paymentInfo, String oldTxnType,String withdrawBalanceStr , boolean... isRollbackFake) {
        //伪造冲正不需要资金变动
        if (isRollbackFake.length > 0 && isRollbackFake[0]) {
            return account.getBalance();
        }
        // 重置老核心风控
        resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
        account.setLastTxnTime(paymentInfo.getTxnDate());
        String oldLastTxnDate = account.getLastTxnDate();
        String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                : account.getLastTxnDate().substring(0, 6);
        account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
        if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
            account.setLastDayBal(account.getBalance());
        }
        if (!currentTxnMonth.equals(oldLastTxnMonth)) {
            account.setLastMonthBal(account.getBalance());
        }
        account.setPasswdErrNum(Long.valueOf(0));
        account.setBalance(account.getBalance() + paymentInfo.getAmount());
        account.setAvailableBalance(account.getAvailableBalance() + paymentInfo.getAmount());
        Long withdrawBalanceAdd = 0l;
        //可提现判断
        if(!Strings.isNullOrEmpty(withdrawBalanceStr)){
	        withdrawBalanceAdd = Long.parseLong(withdrawBalanceStr) < paymentInfo.getAmount() ? Long.parseLong(withdrawBalanceStr): paymentInfo.getAmount();
	    }else{      
	        //退货冲正撤销时原交易为扣可提现余额，退货时直接加上可提现余额；增加撤消冲正时的原交易的内部交易类型判断
	        if (TxnCheckService.withdrawbalanceTxnSet.contains(oldTxnType) || TxnCheckService.withDrawBalanceChargeTxnSet.contains(paymentInfo.getInnerTxnType())
	        		|| TxnCheckService.withDrawBalanceChargeTxnSet.contains(oldTxnType)) {
	        	// 加上提现额度
	        	//转账时需处理是否存在主子关系
	            if (!account.isExistMainSubRelation()) {
	                // 如果存在主子账户关系,转出账户的可提现余额不变动             
	                // 加上提现额度
	            	withdrawBalanceAdd = paymentInfo.getAmount();
	                
	            }
	        }
        }
 
        account.setWithdrawBalance(account.getWithdrawBalance()
                + withdrawBalanceAdd);
        
        // 更新账户关键数据密文报文 //TODO 加密机OK 需开启
        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));//   Account.generateEncryptedMsg(account, txnType))
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        return account.getBalance();
    }

    /**
     * 撤销原交易检测
     */
    private TLogOnlinePayment oldLogOnlinePaymentCancelCheck(TInfoAccount account, PaymentInfo paymentInfo, List<TLogOnlinePayment> oldOnlinePaymentLsts) {
        if (oldOnlinePaymentLsts.size() > 1) {
            throw new BizException(BussErrorCode.ERROR_CODE_900005.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900005.getErrordesc());
        }
        TLogOnlinePayment tLogOnlinePayment = oldOnlinePaymentLsts.get(0);
        if (!tLogOnlinePayment.getTransAmount().equals(paymentInfo.getAmount())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200030.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200030.getErrordesc());
        }
        if (!tLogOnlinePayment.getAccountNo().equals(account.getAccountNo())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200031.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200031.getErrordesc());
        }
        if (TrueFalseLabel.TRUE.getLablCode().equals(tLogOnlinePayment.getCancelFlag())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200080.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200080.getErrordesc());
        }
        if (TrueFalseLabel.TRUE.getLablCode().equals(tLogOnlinePayment.getRevsalFlag())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200083.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200083.getErrordesc());
        }
        //消费的撤销需判断原交易类型与撤销类型是否匹配
        if(TxnType.TXN_CONSUME.equals(paymentInfo.getTxnType()) ){
	        if (!sequenceGenerator.convertNormalTxnType(paymentInfo.getInnerTxnType())
	                .equals(tLogOnlinePayment.getInteriorTransType())) {
	            throw new BizException(BussErrorCode.ERROR_CODE_200082.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200082.getErrordesc());
	        }
        }
        return tLogOnlinePayment;

    }
    
    /**
     * 冲正原交易检测
     */
    private TLogOnlinePayment oldLogOnlinePaymentRollbackCheck(TInfoAccount account, PaymentInfo paymentInfo, List<TLogOnlinePayment> oldOnlinePaymentLsts) {
        if (oldOnlinePaymentLsts.size() > 1) {
            throw new BizException(BussErrorCode.ERROR_CODE_900005.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900005.getErrordesc());
        }
        TLogOnlinePayment tLogOnlinePayment = oldOnlinePaymentLsts.get(0);
        if (!tLogOnlinePayment.getTransAmount().equals(paymentInfo.getAmount())) {
            throw new BizException(BussErrorCode.ERROR_CODE_500032.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500032.getErrordesc());
        }
        if (!tLogOnlinePayment.getAccountNo().equals(account.getAccountNo())) {
            throw new BizException(BussErrorCode.ERROR_CODE_500033.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500033.getErrordesc());
        }
        if (TrueFalseLabel.TRUE.getLablCode().equals(tLogOnlinePayment.getCancelFlag())) {
        	// 如果原正交易已有撤销标识，不处理本次冲正请求
			throw new BizException(BussErrorCode.ERROR_CODE_200084.getErrorcode(),
					BussErrorCode.ERROR_CODE_200084.getErrordesc());
        }
        if (TrueFalseLabel.TRUE.getLablCode().equals(tLogOnlinePayment.getRevsalFlag())) {
        	throw new BizException(BussErrorCode.ERROR_CODE_200041.getErrorcode(),
					BussErrorCode.ERROR_CODE_200041.getErrordesc());
        }
        
        return tLogOnlinePayment;

    }
    
    
    
    
    /**
     * 退货原交易检测
     */
    private TLogOnlinePaymentHis oldLogOnlinePaymentReturnCheck(PaymentInfo paymentInfo, List<TLogOnlinePaymentHis> oldOnlinePaymentLsts) {
        if (oldOnlinePaymentLsts.size() > 1) {
            throw new BizException(BussErrorCode.ERROR_CODE_900005.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900005.getErrordesc());
        }
        TLogOnlinePaymentHis tLogOnlinePaymentHis = oldOnlinePaymentLsts.get(0);
        if (TrueFalseLabel.TRUE.getLablCode().equals(tLogOnlinePaymentHis.getCancelFlag())) {
            throw new BizException(BussErrorCode.ERROR_CODE_410004.getErrorcode(),
                    BussErrorCode.ERROR_CODE_410004.getErrordesc());
        }
        if (TrueFalseLabel.TRUE.getLablCode().equals(tLogOnlinePaymentHis.getRevsalFlag())) {
            throw new BizException(BussErrorCode.ERROR_CODE_410003.getErrorcode(),
                    BussErrorCode.ERROR_CODE_410003.getErrordesc());
        }
        
        if (TrueFalseLabel.FALSE.getLablCode().equals(tLogOnlinePaymentHis.getDubiousFlag())) {
            throw new BizException(BussErrorCode.ERROR_CODE_410010.getErrorcode(),
                    BussErrorCode.ERROR_CODE_410010.getErrordesc());
        }
        
        if (tLogOnlinePaymentHis.getTransAmount() < tLogOnlinePaymentHis.getReturnAmount() + paymentInfo.getAmount()) {
            throw new BizException(BussErrorCode.ERROR_CODE_410005.getErrorcode(),
                    BussErrorCode.ERROR_CODE_410005.getErrordesc());
        }
        
        
        return tLogOnlinePaymentHis;

    }

    /**
     * 找出当日原始清算信息打上撤消标志
     *
     * @param account
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    public TLogOnlinePayment cancelOldLogOnlinePayment(TInfoAccount account, PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, String cancelTxnSeqNo) {
        //   查找当日原联机正交易日志
        List<TLogOnlinePayment> oldOnlinePaymentLsts = tLogOnlinePaymentMapper.findOldNormalOnlineLog(
                oldPaymentInfo.getAcceptTxnSeqNo(), oldPaymentInfo.getAcceptDate(),
                oldPaymentInfo.getAcceptTime(), account.getAccountNo(), oldPaymentInfo.getAcceptOrgCode());

        if ((null == oldOnlinePaymentLsts) || (oldOnlinePaymentLsts.isEmpty())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200081.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200081.getErrordesc());
        }
        TLogOnlinePayment tLogOnlinePayment = oldLogOnlinePaymentCancelCheck(account, paymentInfo, oldOnlinePaymentLsts);
        tLogOnlinePayment.setCancelFlag(TrueFalseLabel.TRUE.getLablCode());
        tLogOnlinePayment.setCancelTxnSerialNo(cancelTxnSeqNo);
        tLogOnlinePaymentMapper.updateByPrimaryKeySelective(tLogOnlinePayment);
        return tLogOnlinePayment;
    }

    /**
     * 找出当日原始清算信息打上冲正标志
     *
     * @param account
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param txnResultObject 用来记录是否为伪造的冲正
     * @param isIncrease      判断是否是充值的冲正 add by huwenjie 需改造
     * @return
     */
    public TLogOnlinePayment rollbackOldLogOnlinePayment(TInfoOrg supplyOrg, TInfoOrg acceptOrg, PaymentInfo paymentInfo,
                                                         PaymentInfo oldPaymentInfo, TInfoCustomer tInfoCustomer,
                                                         TInfoAccount account, String rollbackTxnSeqNo, String isClearing, TxnResultObject txnResultObject, boolean... isIncrease) {
        //   查找当日原联机正交易日志
        List<TLogOnlinePayment> oldOnlinePaymentLsts = tLogOnlinePaymentMapper.findOldNormalOnlineLog(
                oldPaymentInfo.getAcceptTxnSeqNo(), oldPaymentInfo.getAcceptDate(),
                oldPaymentInfo.getAcceptTime(), account.getAccountNo(), oldPaymentInfo.getAcceptOrgCode());

        if ((null == oldOnlinePaymentLsts) || (oldOnlinePaymentLsts.isEmpty())) {
            String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
            //冲正交易无法找到原来交易就是造一条数据
            TLogOnlinePayment tLogOnlinePayment = this.accountClearInDb(supplyOrg, acceptOrg, oldPaymentInfo, null, tInfoCustomer,
                    account, txnSeqNo, null, isClearing, isIncrease);
            accounTxnInDb(oldPaymentInfo, account, acceptOrg, tLogOnlinePayment.getTransSerialNo(),isIncrease);
            oldOnlinePaymentLsts = Lists.newArrayList();
            oldOnlinePaymentLsts.add(tLogOnlinePayment);
            txnResultObject.setRollbackFake(true);

            //伪造一条的资金变化，需要判断是消费冲正，还是充值冲正 add by huwenjie,临时解决，需根据交易类型改造,
            //余额修改,用来冲正时记录清算表正确的BeforeAmt与AfterAmt
            if (isIncrease.length > 0 && isIncrease[0]) {
                account.setBalance(account.getBalance() + paymentInfo.getAmount());
            } else {
                account.setBalance(account.getBalance() - paymentInfo.getAmount());
            }

        }
        TLogOnlinePayment tLogOnlinePayment = oldLogOnlinePaymentRollbackCheck(account, paymentInfo, oldOnlinePaymentLsts);
        tLogOnlinePayment.setRevsalFlag(TrueFalseLabel.TRUE.getLablCode());
        tLogOnlinePayment.setRevsalTxnSerialNo(rollbackTxnSeqNo);
        tLogOnlinePaymentMapper.updateByPrimaryKeySelective(tLogOnlinePayment);
        return tLogOnlinePayment;
    }
    
    
    /**
     * 找出历史原始清算信息打上退货标志
     *
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param cancelTxnSeqNo
     * @return
     */
    public TLogOnlinePaymentHis returnOldLogOnlinePayment(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, String cancelTxnSeqNo) {
    	
        TLogOnlinePaymentHis tLogOnlinePaymentHis = findOldLogOnlinePayment(paymentInfo , oldPaymentInfo);

        tLogOnlinePaymentHisMapper.updateHisReturnFlagByTablename(TrueOrFalse.TRUE.getLablCode(),tLogOnlinePaymentHis.getReturnCount()+1,tLogOnlinePaymentHis.getReturnAmount()+paymentInfo.getAmount(),tLogOnlinePaymentHis.getTransSerialNo(),tLogOnlinePaymentHis.getTableName());
        return tLogOnlinePaymentHis;
    }
    
    
    
    /**
     * 找出历史原始清算信息
     *
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    public TLogOnlinePaymentHis findOldLogOnlinePayment( PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo) {
    	// 查询t_log_online_payment_his拆分后对应的新旧表名关系
    	List<TDictNewOldRelation> tableRelationList = tDictNewOldRelationMapper.selectByOldTableName(
    																	"t_log_online_payment_his");
    	List<TLogOnlinePaymentHis> oldOnlinePaymentLsts = null;
    	String tableName = null;
    	
    	for (TDictNewOldRelation obj : tableRelationList) {
    		// 清算日期在交易日期之前，直接跳过此次循环
			if (Integer.parseInt(obj.getEndDate()) < Integer.parseInt(oldPaymentInfo.getAcceptDate())) {
				continue;
			}  		
			tableName = obj.getNewTableName();
    		//   查找当日原联机正交易日志
    		oldOnlinePaymentLsts = tLogOnlinePaymentHisMapper.findOnlineLogHisByTablename(
                    oldPaymentInfo.getAcceptTxnSeqNo(), oldPaymentInfo.getAcceptDate(),
                    oldPaymentInfo.getAcceptTime(), null , oldPaymentInfo.getTerminalSeqNo(), oldPaymentInfo.getAcceptOrgCode(),tableName);   		
    		if (null != oldOnlinePaymentLsts && oldOnlinePaymentLsts.size() > 0) {
    			break;
    		}
    	
    	}
        if ((null == oldOnlinePaymentLsts) || (oldOnlinePaymentLsts.isEmpty())) {
            throw new BizException(BussErrorCode.ERROR_CODE_410002.getErrorcode(),
                    BussErrorCode.ERROR_CODE_410002.getErrordesc());
        }
        TLogOnlinePaymentHis tLogOnlinePaymentHis = oldLogOnlinePaymentReturnCheck(paymentInfo, oldOnlinePaymentLsts);
        tLogOnlinePaymentHis.setTableName(tableName);
        
        return tLogOnlinePaymentHis;
    }

    /**
     * 交易日志撤销
     */
    public void cancelOldLogAccountPayment(String cancelOnlinePaymentTxnSeqNo, TxnForm txnForm) {
        TLogAccountPayment tLogAccountPayment = tLogAccountPaymentMapper.findByClearingSeqNo(cancelOnlinePaymentTxnSeqNo,
                TxnForm.TXN_LABL_ONLINE.getTxnformcode());
        if (null == tLogAccountPayment) {
            return;
        }
        tLogAccountPayment.setCancelFlag(TrueFalseLabel.TRUE.getLablCode());
        tLogAccountPaymentMapper.updateByPrimaryKeySelective(tLogAccountPayment);
    }
    
    
    


    /**
     * 交易日志冲正
     */
    public void rollbackOldLogAccountPayment(String rollbackOnlinePaymentTxnSeqNo, TxnForm txnForm) {
        TLogAccountPayment tLogAccountPayment = tLogAccountPaymentMapper.findByClearingSeqNo(rollbackOnlinePaymentTxnSeqNo,
        		txnForm.getTxnformcode());
        if (null == tLogAccountPayment) {
            return;
        }
        tLogAccountPayment.setRevsalFlag(TrueFalseLabel.TRUE.getLablCode());
        tLogAccountPaymentMapper.updateByPrimaryKeySelective(tLogAccountPayment);
    }

    /**
     * 查询是否存在主子关系账户
     */
    public boolean checkSubaccountinfoByMainaccno(TInfoAccount tInfoAccount, TInfoAccount tTargetInfoAccount) {
    	try{
    		tInfoSubaccountMapper.getSubaccountinfoByMainaccno(tInfoAccount.getAccountNo());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        List<TInfoSubaccount> subAccountList = tInfoSubaccountMapper.getSubaccountinfoByMainaccno(tInfoAccount.getAccountNo());
        if (null != subAccountList && subAccountList.size() > 0) {
            for (TInfoSubaccount tInfoSubaccount : subAccountList) {
                if (tTargetInfoAccount.getAccountNo().equals(
                        tInfoSubaccount.getAccountNo())) {
                    // 转入和转出账户之间存在主子账户关系
                    tInfoAccount.setExistMainSubRelation(true);
                    tTargetInfoAccount.setExistMainSubRelation(true);
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<TInfoSubaccount> getSubAccountList(TInfoAccount tInfoAccount) {
    	List<TInfoSubaccount> subAccountList = tInfoSubaccountMapper.getSubaccountinfoByMainaccno(tInfoAccount.getAccountNo());
    	if (null == subAccountList || subAccountList.size() == 0) {
    		throw new BizException(BussErrorCode.ERROR_CODE_200090.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200090.getErrordesc());
    	}
    	return subAccountList;
    }
    public void tRealnameApplyInDb(TInfoCustomer customer,ManagerLog managerLog) {
    	//检查黑名单身份证
        riskService.queryRiskBlack(managerLog.getIdno(), RiskBlackType.BLACK_TYPE_IDNO.getTypeCode());
		//处理图片
		// 获取索引值
		String tSeq = sequenceGenerator.generateAuthenticationInfoSeqVal();
		// 重命名照片名，使照片名唯一
		String idPic = tSeq + ".jpg";
		// 保存照片到目录中，如果存储失败，直接返回结果
		if (!Base64DealImageUtil.writeImageToFile(storeImageDirectory,idPic, managerLog.getPhoto())) {
			throw new BizException(BussErrorCode.ERROR_CODE_200136.getErrorcode(),BussErrorCode.ERROR_CODE_200136.getErrordesc());
		}
		//加入表
		Date nowDate = DateTime.now().toDate();
		TRealnameApply tRealnameApply = new TRealnameApply();
		tRealnameApply.setSerialNo(Long.valueOf(tSeq));
		tRealnameApply.setCustomerNo(customer.getCustomerNo());
		tRealnameApply.setProductNo(customer.getUnifyId());
		tRealnameApply.setName(managerLog.getName());
		tRealnameApply.setGrade(customer.getCustomerGrade());
		tRealnameApply.setTransType(TxnInnerType.CUSTOMER_REALNAME_REQUEST_TXN_TYPE.getTxnInnerType());
		tRealnameApply.setCreateTime(nowDate);
		tRealnameApply.setInputUid(managerLog.getInputUid());
		tRealnameApply.setInputDate(nowDate);
		tRealnameApply.setIdType(managerLog.getIdtype());
		tRealnameApply.setIdNo(managerLog.getIdno());
		tRealnameApply.setStatus(CustomerRealnameStatus.CUSTOMER_REALNAME_STATUS_SUCCESS.getCustomerRealnameStatusCode());
		tRealnameApply.setCheckFlag(CustomerRealnameExemine.CUSTOMER_REALNAME_EXAMINE_PASS.getCustomerRealnameExemineCode());
		tRealnameApply.setArchivedFlag(TrueFalseLabel.TRUE.getLablCode());
		tRealnameApply.setIdPic(idPic);
		tRealnameApply.setAcceptChannel(managerLog.getTxnChannel());
		tRealnameApply.setNationality(managerLog.getNationality());
		tRealnameApply.setAddress(managerLog.getAddress());
		tRealnameApply.setProfession(managerLog.getProfession());
		tRealnameApply.setCertExpiryDate(managerLog.getCertExpiryDate());
		tRealnameApply.setGender(managerLog.getGender());
		tRealnameApplyMapper.insertSelective(tRealnameApply);

    }
    
    /**
     * 快捷绑卡直接实名
     * @param customer
     * @param name 实名姓名
     * @param idno 实名身份证
     */
    public void tRealnameApplyInDbNoCheck(TInfoCustomer customer,String  name, String idno) {
        
		// 获取索引值
		String tSeq = sequenceGenerator.generateAuthenticationInfoSeqVal();   			
		//加入表
		Date nowDate = DateTime.now().toDate();
		TRealnameApply tRealnameApply = new TRealnameApply();
		tRealnameApply.setSerialNo(Long.valueOf(tSeq));
		tRealnameApply.setCustomerNo(customer.getCustomerNo());
		tRealnameApply.setName(name);
		tRealnameApply.setGrade(customer.getCustomerGrade());
		tRealnameApply.setTransType(TxnInnerType.CUSTOMER_REALNAME_REQUEST_TXN_TYPE.getTxnInnerType());
		tRealnameApply.setCreateTime(nowDate);
		tRealnameApply.setInputUid(SHORTCUT);
		tRealnameApply.setInputDate(nowDate);
		tRealnameApply.setCheckTime(nowDate);
		tRealnameApply.setCheckUid(SHORTCUT);
		tRealnameApply.setIdType(CustomerIdType.CUSTOMER_ID_TYPE_IDCARD.getCode());
		tRealnameApply.setIdNo(PidUtil.convertPid(idno));
		tRealnameApply.setStatus(CustomerRealnameStatus.CUSTOMER_REALNAME_STATUS_SUCCESS.getCustomerRealnameStatusCode());
		tRealnameApply.setCheckFlag(CustomerRealnameExemine.CUSTOMER_REALNAME_EXAMINE_PASS.getCustomerRealnameExemineCode());
		tRealnameApply.setArchivedFlag(TrueFalseLabel.TRUE.getLablCode());
		tRealnameApply.setAcceptChannel(TxnChannel.TXN_CHANNEL_PORTAL.getTxnCode());
		tRealnameApply.setNationality(customer.getNationality());
		tRealnameApply.setAddress(customer.getContactAddress());
		tRealnameApply.setProfession(customer.getProfession());
		tRealnameApply.setCertExpiryDate(customer.getIdentifyExpiredDate());
		tRealnameApplyMapper.insertSelective(tRealnameApply);

    }
    
    
    public void updateAccountStatus(TInfoAccount tInfoAccount,String status,Date updateTime)
    {
		tInfoAccount.setStatus(status);
		tInfoAccount.setLastUpdateTime(updateTime);
		tInfoAccountMapper.updateAccountStatus(tInfoAccount);
    }
    
    public void activaAccountStatus(TInfoAccount tInfoAccount)
    {
    	tInfoAccount.setStatus(AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus());
        tInfoAccount.setLastUpdateTime(DateTime.now().toDate());
        tInfoAccount.setIsAllowedClose(TrueOrFalse.TRUE.getLablCode());
        tInfoAccount.setIsCloseRtnCash(TrueOrFalse.TRUE.getLablCode());
		tInfoAccountMapper.updateAccountStatus(tInfoAccount);
    }
    
    public int updateAccountRealnameInfo(TInfoCustomer newCustomer ,String customerNo){
    	//全部为空 没有相关账户信息需要更新，直接返回
    	if(Strings.isNullOrEmpty(newCustomer.getAreaCode()) &&  Strings.isNullOrEmpty(newCustomer.getCityCode())
    			&&  Strings.isNullOrEmpty(newCustomer.getIsRealname())&&  Strings.isNullOrEmpty(newCustomer.getCustomerGrade())){
    		return 0;
    	}
    	TInfoAccount newAccount = new TInfoAccount();
    	newAccount.setLastUpdateTime(DateTime.now().toDate());
    	newAccount.setCustomerNo(customerNo);
    	newAccount.setAreaCode(newCustomer.getAreaCode());
    	newAccount.setCityCode(newCustomer.getCityCode());
    	newAccount.setIsRealname(newCustomer.getIsRealname());
    	newAccount.setGrade(newCustomer.getCustomerGrade());
    	return tInfoAccountMapper.updateAccountRealnameInfo(newAccount);
    	
    }
    
    
    /**
	 * 老核心交易风控字段重置为0
	 * 
	 * @param account
	 * @return
	 */
	private static void resetAccountPaymentRisk(TInfoAccount account ,DateTime txnDate) {
		if (!txnDate.toString("yyyyMMdd").equals(
				account.getLastTxnDate())) {
			// 交易日期和账户中最后交易日期不一致，自动将账户中累积日交易信息清零
			account.setDaySumCashAmt(0l);
			account.setDaySumCashCnt(0);
			account.setDaySumDepositAmt(0l);
			account.setDaySumDepositCnt(0);
			account.setDaySumConsAmt(0l);
			account.setDaySumConsCnt(0);
			account.setDaySumInAmt(0l);
			account.setDaySumInCnt(0);
			account.setDaySumOutAmt(0l);
			account.setDaySumOutCnt(0);
		}
		String lastTxnMonth = (account.getLastTxnDate() == null ? null
				: account.getLastTxnDate().substring(0, 6));
		if (!txnDate.toString("yyyyMM").equals(
				lastTxnMonth)) {
			// 交易月和账户中最后交易月不一致，自动将账户中累积月交易信息清零
			account.setMonthSumConsAmt(0l);
			account.setMonthSumOutAmt(0l);
			account.setMonthSumInAmt(0l);
			account.setMonthSumCashAmt(0l);
			account.setMonthSumChgAmt(0l);
			account.setMonthSumConsCnt(0l);
			account.setMonthSumOutCnt(0l);
			account.setMonthSumInCnt(0l);
			account.setMonthSumCashCnt(0l);
			account.setMonthSumChgCnt(0l);
		}

	}
	
	public void setOldTxnType(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo){
		oldPaymentInfo.setBussinessType(sequenceGenerator.convertNormalTxnType(paymentInfo.getInnerTxnType()));
		oldPaymentInfo.setAmount(paymentInfo.getAmount());
		oldPaymentInfo.setChannel(paymentInfo.getChannel());
	}
	
	
	public void setOldCancelTxnType(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo){
		oldPaymentInfo.setBussinessType(sequenceGenerator.convertCancelTxnType(paymentInfo.getInnerTxnType()));
		oldPaymentInfo.setAmount(paymentInfo.getAmount());
		oldPaymentInfo.setChannel(paymentInfo.getChannel());
	}
	
	
	
	/**
     * 找出撤销交易，进行冲正处理
     *
     * @param account
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    public TLogOnlinePayment rollbackCancelOldLogOnlinePayment(TInfoOrg supplyOrg, TInfoOrg acceptOrg, PaymentInfo paymentInfo,
            PaymentInfo oldPaymentInfo, TInfoCustomer tInfoCustomer,
            TInfoAccount account, String rollbackTxnSeqNo, String isClearing, TxnResultObject txnResultObject, boolean... isIncrease) {
    	// 查找该撤销冲正交易对应要冲正的撤销交易
        List<TLogOnlinePayment> oldOnlinePaymentLsts = tLogOnlinePaymentMapper.findOldNormalOnlineLog(
                oldPaymentInfo.getAcceptTxnSeqNo(), oldPaymentInfo.getAcceptDate(),
                oldPaymentInfo.getAcceptTime(), account.getAccountNo(), oldPaymentInfo.getAcceptOrgCode());
        if ((null == oldOnlinePaymentLsts) || (oldOnlinePaymentLsts.isEmpty())) {
            String txnSeqNo = sequenceGenerator.generatorTxnCancelSeqNo();
            // 撤销冲正交易无法找到原撤销交易就是造一条交易记录
            TLogOnlinePayment tLogOnlinePayment = this.accountClearInDb(supplyOrg, acceptOrg, oldPaymentInfo, null, tInfoCustomer,
                    account, txnSeqNo, null, isClearing, isIncrease);
            // 伪造一条账户支付交易记录
            accounTxnInDb(oldPaymentInfo, account, acceptOrg, tLogOnlinePayment.getTransSerialNo(),isIncrease);
            oldOnlinePaymentLsts = Lists.newArrayList();;
            oldOnlinePaymentLsts.add(tLogOnlinePayment);
            // 将伪造交易标识置为真
            txnResultObject.setRollbackFake(true);
            
            // 伪造一条的资金变化，需要判断是消费冲正，还是充值冲正 add by huwenjie,临时解决，需根据交易类型改造,
            // 余额修改,用来冲正时记录清算表正确的BeforeAmt与AfterAmt
            if (isIncrease.length > 0 && isIncrease[0]) {
                account.setBalance(account.getBalance() + paymentInfo.getAmount());
            } else {
                account.setBalance(account.getBalance() - paymentInfo.getAmount());
            }
        }
        
        // 将撤销交易冲正标识置为冲正
        TLogOnlinePayment tLogOnlinePayment = oldLogOnlinePaymentRollbackCheck(account, paymentInfo, oldOnlinePaymentLsts);
        tLogOnlinePayment.setRevsalFlag(TrueFalseLabel.TRUE.getLablCode());
        tLogOnlinePayment.setRevsalTxnSerialNo(rollbackTxnSeqNo);
        tLogOnlinePaymentMapper.updateByPrimaryKeySelective(tLogOnlinePayment);
        return tLogOnlinePayment;
    }
    
    /**
     * 查询原交易清除撤销信息（撤销冲正）
     *
     * @param account
     * @param paymentInfo
     * @param oldPaymentInfo
     * @return
     */
    public TLogOnlinePayment rollbackCancelOriginalLogPayment(TLogOnlinePayment cancelLogOnlinePayment) {
    	// 通过撤销交易中的lTxnSeqNo查询到原交易
    	TLogOnlinePayment originalTLogOnlinePayment = tLogOnlinePaymentMapper.selectByPrimaryKey(cancelLogOnlinePayment.getlTransSerialNo());
    	// 伪造撤消交易时通过伪造的撤消查询不到原交易，原交易不需要处理
    	if(null == originalTLogOnlinePayment){
    		return null;
    	}
    	// 清除原交易中的撤销标识
    	originalTLogOnlinePayment.setCancelFlag("");
    	originalTLogOnlinePayment.setCancelTxnSerialNo("");
        tLogOnlinePaymentMapper.updateByPrimaryKeySelective(originalTLogOnlinePayment);
        // 清除原账户支付交易的撤销标识
        TLogAccountPayment tLogAccountPayment = tLogAccountPaymentMapper.findByClearingSeqNo(originalTLogOnlinePayment.getTransSerialNo(),
        		TxnLabel.TXN_LABL_ONLINE.getLablCode());
        if(null != tLogAccountPayment){
        	tLogAccountPayment.setCancelFlag("");
        	tLogAccountPaymentMapper.updateByPrimaryKey(tLogAccountPayment);
        }
        return originalTLogOnlinePayment;
    }
    
    /**
     * 撤销冲正交易手续费
     *
     * @param paymentInfo
     * @param tInfoAccount
     * @param tInfoCustomer
     * @param acceptOrg
     * @param merchantOrg
     * @param oldNormalTxnSeqNo
     * @return
     */
    public TLogOnlinePayment rollbackCancelFeeCatulatorInDb(PaymentInfo paymentInfo, TInfoAccount tInfoAccount, TInfoCustomer tInfoCustomer,
                                       TInfoOrg acceptOrg, TInfoOrg merchantOrg, TLogOnlinePayment cancelLogOnlinePayment) {

        TLogOnlinePayment oldCancelFeeOnlicePayment = tLogOnlinePaymentMapper.findFeeOnlineLogByNormalLog(cancelLogOnlinePayment.getTransSerialNo());

        if (null == oldCancelFeeOnlicePayment) {
            return null;
        }
        // 构造原交易paymentInfo
        PaymentInfo oldFeepaymentInfo = new PaymentInfo();
        oldFeepaymentInfo.setAcceptDate(oldCancelFeeOnlicePayment.getAcceptOrgTransDate());
        oldFeepaymentInfo.setAcceptTxnSeqNo(oldCancelFeeOnlicePayment.getAcceptOrgSerialNo());
        oldFeepaymentInfo.setAcceptTime(oldCancelFeeOnlicePayment.getAcceptOrgTransTime());
        // 手续费需要一个新的paymentInfo，不修改原来的。
        PaymentInfo rollbackCancelFeePaymentInfo = new PaymentInfo(); // 复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, rollbackCancelFeePaymentInfo);
    	rollbackCancelFeePaymentInfo.setAmount(oldCancelFeeOnlicePayment.getTransAmount());
    	rollbackCancelFeePaymentInfo.setInnerTxnType(TxnInnerType.TXN_TYPE_280030.getTxnInnerType());
    	rollbackCancelFeePaymentInfo.setBussinessType(TxnInnerType.TXN_TYPE_280030.getTxnInnerType());
    	rollbackCancelFeePaymentInfo.setTxnDscpt(TxnInnerType.TXN_TYPE_280030.getTxnInnerTypeDesc());
    	rollbackCancelFeePaymentInfo.setTxnType(TxnType.TXN_CHARGE);
    	rollbackCancelFeePaymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_FEE);
        String rollbackTxnfeeSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        // 手续费清算冲正
        oldCancelFeeOnlicePayment.setRevsalFlag(TrueFalseLabel.TRUE.getLablCode());
        oldCancelFeeOnlicePayment.setRevsalTxnSerialNo(rollbackTxnfeeSeqNo);
        tLogOnlinePaymentMapper.updateByPrimaryKeySelective(oldCancelFeeOnlicePayment);
        // 查找原始手续费收费交易，找到清除撤销标识
        TLogOnlinePayment oldFeeOnlicePayment = tLogOnlinePaymentMapper.findFeeOnlineLogByNormalLog(cancelLogOnlinePayment.getlTransSerialNo());
        if(null != oldFeeOnlicePayment){
        	oldFeeOnlicePayment.setCancelFlag("");
        	oldFeeOnlicePayment.setCancelTxnSerialNo("");
        	tLogOnlinePaymentMapper.updateByPrimaryKeySelective(oldFeeOnlicePayment);
        	// 将原手续费账户支付消费交易 的撤销标识清除
            TLogAccountPayment oldFeeAccountPayment = tLogAccountPaymentMapper.findByClearingSeqNo(oldFeeOnlicePayment.getTransSerialNo(),
            		TxnLabel.TXN_LABL_ONLINE.getLablCode());
            if(null != oldFeeAccountPayment){
            	oldFeeAccountPayment.setCancelFlag("");
            	tLogAccountPaymentMapper.updateByPrimaryKeySelective(oldFeeAccountPayment);
            }
        }
        // 手续费交易日志冲正
        rollbackOldLogAccountPayment(oldCancelFeeOnlicePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        // 手续费撤销冲正交易日志
        TLogOnlinePayment tCancelFeeLogOnlinePayment =accountClearInDb(merchantOrg, acceptOrg, rollbackCancelFeePaymentInfo, oldFeepaymentInfo,
                tInfoCustomer, tInfoAccount, rollbackTxnfeeSeqNo, oldCancelFeeOnlicePayment.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode());
        // 交易库
        accounTxnInDb(rollbackCancelFeePaymentInfo, tInfoAccount, acceptOrg, rollbackTxnfeeSeqNo);
        // 账户表更新
        accountDecreaseAlterInDb(tInfoAccount, rollbackCancelFeePaymentInfo, null);
        return tCancelFeeLogOnlinePayment;
    }
    
    /**
     * 脱机账户交易入清结算
     * 
     * @param supplyOrg
     * @param acceptOrg
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param tInfoCustomer
     * @param account
     * @param txnSeqNo
     * @param oldOnlineTxnSeqNo
     * @param isClearing
     * @param isIncrease
     * @return
     */
    public TLogOfflinePayment offlineAccountClearInDb(TInfoOrg supplyOrg, TInfoOrg acceptOrg, PaymentInfo paymentInfo,
                                              PaymentInfo oldPaymentInfo, TInfoCustomer tInfoCustomer, TInfoAccount account, String txnSeqNo,
                                              String oldOnlineTxnSeqNo, String isClearing, boolean... isIncrease) {

        if (Strings.isNullOrEmpty(paymentInfo.getTxnDscpt())) {
            String txnDscpt =
                    supplyOrg.getOrgFname()
                            + cacheComponent.getTxnTypeObj(paymentInfo.getInnerTxnType()).getTxnName();
            paymentInfo.setTxnDscpt(txnDscpt);
        }
        TLogOfflinePayment tLogOfflinePayment = new TLogOfflinePayment();
        tLogOfflinePayment.setTxnSeqNo(txnSeqNo);
        tLogOfflinePayment.setAcceptOrgCode(paymentInfo.getAcceptOrgCode());
        tLogOfflinePayment.setAcceptOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());// CodeGenerator.extractOrgType(paymentInfo.getAcceptOrgCode()));
        tLogOfflinePayment.setAreaCode(supplyOrg.getAreaCode());
        tLogOfflinePayment.setCityCode(supplyOrg.getCityCode());
        tLogOfflinePayment.setTransSeqType(paymentInfo.getTxnSeqType().getSeqType());
        tLogOfflinePayment.setAcceptTransSeqNo(paymentInfo.getAcceptTxnSeqNo());
        tLogOfflinePayment.setAcceptTransDate(paymentInfo.getAcceptDate());
        tLogOfflinePayment.setAcceptTransTime(paymentInfo.getAcceptTime());
        tLogOfflinePayment.setPayOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());
        
        if(account != null){
        	tLogOfflinePayment.setAccountNo(account.getAccountNo());
        	tLogOfflinePayment.setAccountType(account.getType());
        	tLogOfflinePayment.setPayOrgCode(Strings.isNullOrEmpty(paymentInfo.getPayOrgCode())?account.getApanage():paymentInfo.getPayOrgCode());
	        
        	tLogOfflinePayment.setBeforeAmt(account.getBalance());
	        //TODO 增值交易 这里有问题 add by huwenjie 暂时使用新参数判断 需改造成交易类型判断借贷关系
	        if (isIncrease.length > 0 && isIncrease[0]) {
	        	tLogOfflinePayment.setAfterAmt(account.getBalance() + paymentInfo.getAmount());
	        } else {
	        	tLogOfflinePayment.setAfterAmt(account.getBalance() - paymentInfo.getAmount());
	        }
	        tLogOfflinePayment.setInnerCardNo(account.getCardNo());
        }
        tLogOfflinePayment.setBusinessType(paymentInfo.getBussinessType());
        tLogOfflinePayment.setTxnType(paymentInfo.getInnerTxnType());
        tLogOfflinePayment.setResendNum(Long.valueOf(0));
        tLogOfflinePayment.setTxnAmt(paymentInfo.getAmount());
        tLogOfflinePayment.setTxnChannel(paymentInfo.getChannel());
        tLogOfflinePayment.setTxnDscpt(paymentInfo.getTxnDscpt());
        tLogOfflinePayment.setTxnTime(paymentInfo.getTxnDate());
        tLogOfflinePayment.setTxnMonth(Integer.valueOf(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMM")));
        tLogOfflinePayment.setSupplyOrgCode(supplyOrg.getOrgCode());
        tLogOfflinePayment.setSupplyOrgType(OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        tLogOfflinePayment.setIsClearing(isClearing);
        tLogOfflinePayment.setTerminalNo(paymentInfo.getTerminalNo());
        String terminalseq = Strings.isNullOrEmpty(paymentInfo.getTerminalSeqNo()) ? paymentInfo.getAcceptTxnSeqNo() : paymentInfo.getTerminalSeqNo();
        tLogOfflinePayment.setTerminalSeqNo(terminalseq);
        if( tInfoCustomer !=null){
        	tLogOfflinePayment.setCustomerNo(tInfoCustomer.getCustomerNo());
        	tLogOfflinePayment.setCustomerAreaCode(tInfoCustomer.getAreaCode());
        	tLogOfflinePayment.setCustomerCityCode(tInfoCustomer.getCityCode());
        }
        tLogOfflinePayment.setlTxnSeqNo(oldOnlineTxnSeqNo);
        if (null != oldPaymentInfo) {
        	tLogOfflinePayment.setlAcceptTransDate(oldPaymentInfo.getAcceptDate());
        	tLogOfflinePayment.setlAcceptTransSeqNo(oldPaymentInfo.getAcceptTxnSeqNo());
        	tLogOfflinePayment.setlAcceptTransTime(oldPaymentInfo.getAcceptTime());            
            //退货标示
        	tLogOfflinePayment.setSupplyFeeFlag(paymentInfo.getSupplyFeeFlag());
        	tLogOfflinePayment.setPayFeeFlag(paymentInfo.getPayFeeFlag());
        }

        tLogOfflinePayment.setCardCnt(Long.valueOf(paymentInfo.getCardCnt(), 16));
        tLogOfflinePayment.setTxnTac(paymentInfo.getCardTAC());
        tLogOfflinePaymentMapper.insert(tLogOfflinePayment);
        return tLogOfflinePayment;
    }
    
    /**
     * 脱机账户交易入可疑交易
     */
    public TLogDubiousTxn offlineAccountDubiousTxn(TInfoOrg supplyOrg, TInfoOrg acceptOrg, PaymentInfo paymentInfo,
                                              PaymentInfo oldPaymentInfo, TInfoCustomer tInfoCustomer, TInfoAccount account, String txnSeqNo,
                                              String oldOnlineTxnSeqNo, String isClearing, String errCode, String errMessage, boolean... isIncrease) {
    	if (Strings.isNullOrEmpty(paymentInfo.getTxnDscpt())) {
            String txnDscpt =
                    supplyOrg.getOrgFname()
                            + cacheComponent.getTxnTypeObj(paymentInfo.getInnerTxnType()).getTxnName();
            paymentInfo.setTxnDscpt(txnDscpt);
        }
    	TLogDubiousTxn tLogDubiousTxn = new TLogDubiousTxn();
        tLogDubiousTxn.setTxnSeqNo(txnSeqNo);
        tLogDubiousTxn.setAcceptOrgCode(paymentInfo.getAcceptOrgCode());
        tLogDubiousTxn.setAcceptOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());// CodeGenerator.extractOrgType(paymentInfo.getAcceptOrgCode()));
        if(supplyOrg != null){
        	tLogDubiousTxn.setAreaCode(supplyOrg.getAreaCode());
        	tLogDubiousTxn.setCityCode(supplyOrg.getCityCode());
        	tLogDubiousTxn.setSupplyOrgCode(supplyOrg.getOrgCode());
        }
        tLogDubiousTxn.setTransSeqType(paymentInfo.getTxnSeqType().getSeqType());
        tLogDubiousTxn.setAcceptTransSeqNo(paymentInfo.getAcceptTxnSeqNo());
        tLogDubiousTxn.setAcceptTransDate(paymentInfo.getAcceptDate());
        tLogDubiousTxn.setAcceptTransTime(paymentInfo.getAcceptTime());
        tLogDubiousTxn.setPayOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());
        if(account != null){
        	tLogDubiousTxn.setAccountNo(account.getAccountNo());
        	tLogDubiousTxn.setAccountType(account.getType());
        	tLogDubiousTxn.setPayOrgCode(Strings.isNullOrEmpty(paymentInfo.getPayOrgCode())?account.getApanage():paymentInfo.getPayOrgCode());
	        
        	tLogDubiousTxn.setBeforeAmt(account.getBalance());
	        //TODO 增值交易 这里有问题 add by huwenjie 暂时使用新参数判断 需改造成交易类型判断借贷关系
	        if (isIncrease.length > 0 && isIncrease[0]) {
	        	tLogDubiousTxn.setAfterAmt(account.getBalance() + paymentInfo.getAmount());
	        } else {
	        	tLogDubiousTxn.setAfterAmt(account.getBalance() - paymentInfo.getAmount());
	        }
	        tLogDubiousTxn.setInnerCardNo(account.getCardNo());
        }
        tLogDubiousTxn.setBusinessType(paymentInfo.getBussinessType());
        tLogDubiousTxn.setTxnType(paymentInfo.getInnerTxnType());
        tLogDubiousTxn.setResendNum(Long.valueOf(0));
        tLogDubiousTxn.setTxnAmt(paymentInfo.getAmount());
        tLogDubiousTxn.setTxnChannel(paymentInfo.getChannel());
        tLogDubiousTxn.setTxnDscpt(paymentInfo.getTxnDscpt());
        tLogDubiousTxn.setTxnTime(paymentInfo.getTxnDate());
        tLogDubiousTxn.setTxnMonth(Integer.valueOf(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMM")));
        tLogDubiousTxn.setSupplyOrgType(OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        tLogDubiousTxn.setIsClearing(isClearing);
        tLogDubiousTxn.setTerminalNo(paymentInfo.getTerminalNo());
        String terminalseq = Strings.isNullOrEmpty(paymentInfo.getTerminalSeqNo()) ? paymentInfo.getAcceptTxnSeqNo() : paymentInfo.getTerminalSeqNo();
        tLogDubiousTxn.setTerminalSeqNo(terminalseq);
        tLogDubiousTxn.setlTxnSeqNo(oldOnlineTxnSeqNo);
        if (null != oldPaymentInfo) {
        	tLogDubiousTxn.setlAcceptTransDate(oldPaymentInfo.getAcceptDate());
        	tLogDubiousTxn.setlAcceptTransSeqNo(oldPaymentInfo.getAcceptTxnSeqNo());
        	tLogDubiousTxn.setlAcceptTransTime(oldPaymentInfo.getAcceptTime());            
            //退货标示
        	tLogDubiousTxn.setSupplyFeeFlag(paymentInfo.getSupplyFeeFlag());
        	tLogDubiousTxn.setPayFeeFlag(paymentInfo.getPayFeeFlag());
        }
        tLogDubiousTxn.setCardCnt(Long.valueOf(paymentInfo.getCardCnt(), 16));
        tLogDubiousTxn.setTxnTac(paymentInfo.getCardTAC());
        tLogDubiousTxn.setErrorCode(errCode);
        tLogDubiousTxn.setRemark(errCode + ":" + errMessage);
        tLogDubiousTxnMapper.insert(tLogDubiousTxn);
        return tLogDubiousTxn;
    }
    
    /**
     * 脱机账户账户支付交易日志，根据是否增值设置最后一个参数true
     * 
     * @param paymentInfo
     * @param account
     * @param acceptOrg
     * @param cleaningTxnSeqNo
     * @param isIncrease
     */
	public void offlineAccounTxnInDb(PaymentInfo paymentInfo, TInfoAccount account, TInfoOrg supplyOrg,
	                          TInfoOrg acceptOrg, String cleaningTxnSeqNo, boolean... isIncrease) {
		String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
		TLogAccountPayment tLogAccountPayment = new TLogAccountPayment();
		tLogAccountPayment.setAcceptOrgCode(paymentInfo.getAcceptOrgCode());
		tLogAccountPayment.setAcceptOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());
		tLogAccountPayment.setAccountNo(account.getAccountNo());
		tLogAccountPayment.setAccountType(account.getType());
		tLogAccountPayment.setBeforeAmt(account.getBalance());
		if (isIncrease.length > 0 && isIncrease[0]) {
			tLogAccountPayment.setAfterAmt(account.getBalance() + paymentInfo.getAmount());
		}
		else{
			tLogAccountPayment.setAfterAmt(account.getBalance() - paymentInfo.getAmount());
		}
		tLogAccountPayment.setAreaCode(acceptOrg.getAreaCode());
		tLogAccountPayment.setCityCode(acceptOrg.getCityCode());
		tLogAccountPayment.setBusinessType(paymentInfo.getBussinessType());
		tLogAccountPayment.setTransSeqType(paymentInfo.getTxnSeqType().getSeqType());
		tLogAccountPayment.setTxnAmt(paymentInfo.getAmount());
		tLogAccountPayment.setTxnChannel(paymentInfo.getChannel());
		tLogAccountPayment.setTxnDscpt(paymentInfo.getTxnDscpt());
		tLogAccountPayment.setTxnSeqNo(txnSeqNo);
		tLogAccountPayment.setTxnTime(DateTime.now().toDate());
		tLogAccountPayment.setTxnMonth(Integer.parseInt(DateTime.now().toString("yyyyMM")));
		tLogAccountPayment.setTxnType(paymentInfo.getInnerTxnType());
		tLogAccountPayment.setClearingTxnSeqNo(cleaningTxnSeqNo);
		tLogAccountPayment.setCustomerNo(account.getCustomerNo());
		tLogAccountPayment.setAcceptTransDate(paymentInfo.getAcceptDate());
		tLogAccountPayment.setAcceptTransTime(paymentInfo.getAcceptTime());
		tLogAccountPayment.setInnerCardNo(account.getCardNo());
		tLogAccountPayment.setTxnLabel(TxnLabel.TXN_LABL_OFFLINE.getLablCode());
		String terminalseq = Strings.isNullOrEmpty(paymentInfo.getTerminalSeqNo()) ?
				paymentInfo.getAcceptTxnSeqNo() : paymentInfo.getTerminalSeqNo();
		tLogAccountPayment.setResvFld1(paymentInfo.getAcceptTxnSeqNo());
		tLogAccountPayment.setResvFld2(terminalseq);
		tLogAccountPaymentMapper.insert(tLogAccountPayment);
	}

     /**
      * 脱机账户变动入库 减值
      * 
      * @param account
      * @param paymentInfo
      * @param isRollbackFake
      * @return
      */
     public Long offlineAccountDecreaseAlterInDb(TInfoAccount account, PaymentInfo paymentInfo, boolean... isRollbackFake) {
         account.setLastTxnTime(paymentInfo.getTxnDate());
         String oldLastTxnDate = account.getLastTxnDate();
         String oldLastTxnMonth = account.getLastTxnDate() == null ? null
                 : account.getLastTxnDate().substring(0, 6);
         account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
         String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
         if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
             account.setLastDayBal(account.getBalance());
         }
         if (!currentTxnMonth.equals(oldLastTxnMonth)) {
             account.setLastMonthBal(account.getBalance());
         }
         account.setPasswdErrNum(Long.valueOf(0));
         account.setBalance(account.getBalance() - paymentInfo.getAmount());
         account.setAvailableBalance(account.getAvailableBalance() - paymentInfo.getAmount());
         account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));
         tInfoAccountMapper.updateByPrimaryKeySelective(account);
         return account.getBalance();
     }

     /**
      * 预授权完成撤销账户变动
      * 
      * @param account
      * @param paymentInfo
      * @return
      */
     public Long preAuthCompleteCancelAccountAlter(TInfoAccount account, PaymentInfo paymentInfo, TLogPreauthApply preAuthApplylog) {
    	 resetAccountPaymentRisk(account, new DateTime(paymentInfo.getTxnDate()));
    	 account.setLastTxnTime(paymentInfo.getTxnDate());
	     String oldLastTxnDate = account.getLastTxnDate();
	     String oldLastTxnMonth = account.getLastTxnDate() == null ? null
	             : account.getLastTxnDate().substring(0, 6);
	     account.setLastTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
	     String currentTxnMonth = account.getLastTxnDate().substring(0, 6);
	     if (!account.getLastTxnDate().equals(oldLastTxnDate)) {
	         account.setLastDayBal(account.getBalance());
	     }
	     if (!currentTxnMonth.equals(oldLastTxnMonth)) {
	         account.setLastMonthBal(account.getBalance());
	     }
	     account.setPasswdErrNum(Long.valueOf(0));
	     account.setBalance(account.getBalance() + paymentInfo.getAmount());
	     if(preAuthApplylog == null){
		     account.setAvailableBalance(account.getAvailableBalance() + paymentInfo.getAmount());
	     }else{
	    	 account.setAvailableBalance(account.getAvailableBalance() + paymentInfo.getAmount() - preAuthApplylog.getAuthAmt());
	    	 account.setPreauthorizedAmt(account.getPreauthorizedAmt() + preAuthApplylog.getAuthAmt());
	    	 account.setFrozenAmount(account.getFrozenAmount() + preAuthApplylog.getAuthAmt());
	     }
	     account.setWithdrawBalance(account.getAvailableBalance() < account
				.getWithdrawBalance() ? account.getAvailableBalance() : account
				.getWithdrawBalance());
	     account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 更新账户关键数据密文报文
	     tInfoAccountMapper.updateByPrimaryKeySelective(account);
	     return account.getBalance();
     }
     
     public String closeAccountByAccountNoToInnerInvoke(TInfoAccount account, ManagerLog managerLog, boolean insertAccountAlter) {
 	
 		if (account == null) {
 			// 指定账户不存在
 			throw new BizException(null, BussErrorCode.ERROR_CODE_200013.getErrorcode());
 		}
 		String txnType = null;

 		if (AccountType.FUND.getValue().equals(account.getType())) {
 			// 资金账户销户
 			txnType = TxnInnerType.TXN_TYPE_101020.getTxnInnerType();
 		} 
 		// 检查交易渠道和交易类型合法性
 		txnCheckService.checkAccountManagerTxn(account, managerLog);// 检查交易合法性
 		// 检查受理机构状态权限
 		 TInfoOrg acceptOrg = cacheComponent.getOrgObj(managerLog.getAcceptOrgCode());
 		TInfoOrg acceptOrg2 = orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype()); // 检查商户交易合法性
 		
 		Timestamp txnTime = DateUtil.getTime();

 		String txnSeqNo =sequenceGenerator.generateAccountMgmTxnSeq(); 
 		String beforeStatus = account.getStatus();
 		String afterStatus = AccountStatus.ACCOUNT_STATUS_CLOSED.getStatus();
 		account.setStatus(afterStatus);
 		account.setCloseDate(DateUtil.getDate());
 		account.setLastUpdateTime(txnTime);
 		tInfoAccountMapper.updateAccountStatus(account);

// 		if (account.getType().equals(AccountType.ACCOUNT_TYPE_OFFLINE)) {
// 			// 解绑卡号
// 			accountDao.closeOtaPasswd(account.getAccountNo());
// 			cardDao.unbindAccountCard(account.getAccountNo(), txnTime, orgCode,
// 					inputUid, txnTime);
//
// 		}

 		TLogAccountManagement tLogAccountManagement = new TLogAccountManagement();
 	   tLogAccountManagement.setAcceptDate(managerLog.getAcceptDate());
 	   tLogAccountManagement.setAcceptTime(managerLog.getAcceptTime());
 	   tLogAccountManagement.setAcceptOrgSerialNo(managerLog.getAcceptTxnSeqNo());
 	   tLogAccountManagement.setAccountNo(account.getAccountNo());
 	   tLogAccountManagement.setAccountType(account.getType());
 	   tLogAccountManagement.setAfterStatus(account.getStatus());
 	   tLogAccountManagement.setBeforeStatus(managerLog.getBeforeStatus());
 	   Timestamp tsCheckTime = new Timestamp(managerLog.getCheckTime().getTime());//转换为timeStamp
 	   tLogAccountManagement.setCheckTime(tsCheckTime);//CheckTime
 	   tLogAccountManagement.setCheckUid(managerLog.getCheckUid());
 	   tLogAccountManagement.setCustomerNo(account.getCustomerNo());
 	   tLogAccountManagement.setFeeAmount(managerLog.getFeeAmt());
 	   tLogAccountManagement.setFeeFlag(managerLog.getFeeFlag());
 	   Timestamp tsInputTime = new Timestamp(managerLog.getInputTime().getTime());//转换为timeStamp
 	   tLogAccountManagement.setInputTime(tsInputTime);//inputtime
 	   tLogAccountManagement.setInputUid(managerLog.getInputUid());
 	   tLogAccountManagement.setOrgCode(acceptOrg.getOrgCode());
 	   tLogAccountManagement.setAcceptChannel(managerLog.getTxnChannel());
 	   //Timestamp tsTransTime = new Timestamp(tInfoAccount.getLastUpdateTime().getTime());//转换为timeStamp
 	   tLogAccountManagement.setTransTime(null);//transTime
 	   tLogAccountManagement.setTransMemo(managerLog.getTxnDscpt());
 	   tLogAccountManagement.setTransMonth(Integer.parseInt(new DateTime(tLogAccountManagement.getTransTime()).toString("yyyyMM")));
 	   tLogAccountManagement.setTransSerialNo(sequenceGenerator.generateAccountMgmTxnSeq());	   
 	   tLogAccountManagement.setTransType(managerLog.getInnerTxnType());
 	   tLogAccountManagementMapper.insert(tLogAccountManagement);

// 		if (insertAccountAlter) {
// 			// 处理账户变动通知
// 			this.handleAccountAlterLog(oldCustomer, account, log, acceptOrg, null);
// 			customer = null;
// 		}

 		// 清除对象内存
 		account = null;

 	//	log = null;

 		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
 	}
     
     public String lostAccountByAccountNo(TInfoAccount account, ManagerLog managerLog) {
 		// TODO Auto-generated method stub
 		TInfoAccount oldAccount = (TInfoAccount) tInfoAccountMapper.findFundAccountByAccountNoForUpdate(account.getAccountNo());
 		if (oldAccount == null) {
 			// 指定账户不存在
 			throw new BizException(null, BussErrorCode.ERROR_CODE_200013.getErrorcode());
 		}
 		String txnType = null;
 		if (AccountType.FUND.getValue().equals(oldAccount.getType())) {
 			// 资金账户
 			txnType = TxnInnerType.TXN_TYPE_101030.getTxnInnerType();
 		}  else {
 			// 非法账户类型
 			throw new BizException(txnType, BussErrorCode.ERROR_CODE_200052.getErrorcode());
 		}
 		// 检查交易渠道和交易类型合法性
 		txnCheckService.checkTxnType(txnType,TxnType.TXN_MGM.getTxnCode());

 		txnCheckService.checkAccountManagerTxn(oldAccount, managerLog);// 检查交易合法性
 		// 检查受理机构状态权限
 		 TInfoOrg acceptOrg = (TInfoOrg)cacheComponent.getOrgObj(managerLog.getAcceptOrgCode());
 		// 检查商户交易合法性
 		TInfoOrg checkResult = orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype());
 		Timestamp txnTime = DateUtil.getTime();

 		String txnSeqNo = sequenceGenerator.generateAccountMgmTxnSeq(); 
 		String beforeStatus = account.getStatus();
 		String afterStatus = AccountStatus. ACCOUNT_STATUS_LOSTED.getStatus();
 		oldAccount.setStatus(afterStatus);
 		oldAccount.setLastUpdateTime(txnTime);
 	
 		tInfoAccountMapper.updateAccountStatus(oldAccount);
 		
 		
 		TLogAccountManagement tLogAccountManagement = new TLogAccountManagement();
  	   tLogAccountManagement.setAcceptDate(managerLog.getAcceptDate());
  	   tLogAccountManagement.setAcceptTime(managerLog.getAcceptTime());
  	   tLogAccountManagement.setAcceptOrgSerialNo(managerLog.getAcceptTxnSeqNo());
  	   tLogAccountManagement.setAccountNo(account.getAccountNo());
  	   tLogAccountManagement.setAccountType(account.getType());
  	   tLogAccountManagement.setAfterStatus(account.getStatus());
  	   tLogAccountManagement.setBeforeStatus(managerLog.getBeforeStatus());
  	   Timestamp tsCheckTime = new Timestamp(managerLog.getCheckTime().getTime());//转换为timeStamp
  	   tLogAccountManagement.setCheckTime(tsCheckTime);//CheckTime
  	   tLogAccountManagement.setCheckUid(managerLog.getCheckUid());
  	   tLogAccountManagement.setCustomerNo(account.getCustomerNo());
  	   tLogAccountManagement.setFeeAmount(managerLog.getFeeAmt());
  	   tLogAccountManagement.setFeeFlag(managerLog.getFeeFlag());
  	   Timestamp tsInputTime = new Timestamp(managerLog.getInputTime().getTime());//转换为timeStamp
  	   tLogAccountManagement.setInputTime(tsInputTime);//inputtime
  	   tLogAccountManagement.setInputUid(managerLog.getInputUid());
  	   tLogAccountManagement.setOrgCode(acceptOrg.getOrgCode());
  	   tLogAccountManagement.setAcceptChannel(managerLog.getTxnChannel());
  	   //Timestamp tsTransTime = new Timestamp(tInfoAccount.getLastUpdateTime().getTime());//转换为timeStamp
  	   tLogAccountManagement.setTransTime(null);//transTime
  	   tLogAccountManagement.setTransMemo(managerLog.getTxnDscpt());
  	   tLogAccountManagement.setTransMonth(Integer.parseInt(new DateTime(tLogAccountManagement.getTransTime()).toString("yyyyMM")));
  	   tLogAccountManagement.setTransSerialNo(sequenceGenerator.generateAccountMgmTxnSeq());	   
  	   tLogAccountManagement.setTransType(managerLog.getInnerTxnType());
  	   tLogAccountManagementMapper.insert(tLogAccountManagement);

// 		if (oldAccount.getType().equals(AccountType.FUND.getValue())) {
// 			// 通知
// 			TInfoCustomer customer = (TInfoCustomer) customerDao.findObjectByPk(
// 					"Customer", oldAccount.getCustomerNo());
// 			// 处理账户变动通知
// 			this.handleAccountAlterLog(customer, oldAccount, log, acceptOrg, null);
// 			customer = null;
// 		}

 		// 清除对象内存
 		account = null;
 		//log = null;

 		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
 	}
     
     public String unLostAccountByAccountNo(TInfoAccount account, ManagerLog managerLog) {
  		// TODO Auto-generated method stub
  		TInfoAccount oldAccount = (TInfoAccount) tInfoAccountMapper.findFundAccountByAccountNoForUpdate(account.getAccountNo());
  		if (oldAccount == null) {
  			// 指定账户不存在
  			throw new BizException(null, BussErrorCode.ERROR_CODE_200013.getErrorcode());
  		}
  		String txnType = null;
  		if (AccountType.FUND.getValue().equals(oldAccount.getType())) {
  			// 资金账户
  			txnType = TxnInnerType.TXN_TYPE_101030.getTxnInnerType();
  		}  else {
  			// 非法账户类型
  			throw new BizException(txnType, BussErrorCode.ERROR_CODE_200052.getErrorcode());
  		}
  		// 检查交易渠道和交易类型合法性
  		txnCheckService.checkTxnType(txnType,TxnType.TXN_MGM.getTxnCode());

  		txnCheckService.checkAccountManagerTxn(oldAccount, managerLog);// 检查交易合法性
  		// 检查受理机构状态权限
  		 TInfoOrg acceptOrg = (TInfoOrg)cacheComponent.getOrgObj(managerLog.getAcceptOrgCode());
  		// 检查商户交易合法性
  		TInfoOrg checkResult = orgService.getValidOrg(managerLog.getAcceptOrgCode(),txnType, OrgType.ORG_TYPE_ORG.getOrgtype());
  		Timestamp txnTime = DateUtil.getTime();

  		String txnSeqNo = sequenceGenerator.generateAccountMgmTxnSeq(); 
  		String beforeStatus = account.getStatus();
  		String afterStatus = AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus();
  		oldAccount.setStatus(afterStatus);
  		oldAccount.setLastUpdateTime(txnTime);
  	
  		tInfoAccountMapper.updateAccountStatus(oldAccount);
  		
  		
  		TLogAccountManagement tLogAccountManagement = new TLogAccountManagement();
   	   tLogAccountManagement.setAcceptDate(managerLog.getAcceptDate());
   	   tLogAccountManagement.setAcceptTime(managerLog.getAcceptTime());
   	   tLogAccountManagement.setAcceptOrgSerialNo(managerLog.getAcceptTxnSeqNo());
   	   tLogAccountManagement.setAccountNo(account.getAccountNo());
   	   tLogAccountManagement.setAccountType(account.getType());
   	   tLogAccountManagement.setAfterStatus(account.getStatus());
   	   tLogAccountManagement.setBeforeStatus(managerLog.getBeforeStatus());
   	   Timestamp tsCheckTime = new Timestamp(managerLog.getCheckTime().getTime());//转换为timeStamp
   	   tLogAccountManagement.setCheckTime(tsCheckTime);//CheckTime
   	   tLogAccountManagement.setCheckUid(managerLog.getCheckUid());
   	   tLogAccountManagement.setCustomerNo(account.getCustomerNo());
   	   tLogAccountManagement.setFeeAmount(managerLog.getFeeAmt());
   	   tLogAccountManagement.setFeeFlag(managerLog.getFeeFlag());
   	   Timestamp tsInputTime = new Timestamp(managerLog.getInputTime().getTime());//转换为timeStamp
   	   tLogAccountManagement.setInputTime(tsInputTime);//inputtime
   	   tLogAccountManagement.setInputUid(managerLog.getInputUid());
   	   tLogAccountManagement.setOrgCode(acceptOrg.getOrgCode());
   	   tLogAccountManagement.setAcceptChannel(managerLog.getTxnChannel());
   	   //Timestamp tsTransTime = new Timestamp(tInfoAccount.getLastUpdateTime().getTime());//转换为timeStamp
   	   tLogAccountManagement.setTransTime(null);//transTime
   	   tLogAccountManagement.setTransMemo(managerLog.getTxnDscpt());
   	   tLogAccountManagement.setTransMonth(Integer.parseInt(new DateTime(tLogAccountManagement.getTransTime()).toString("yyyyMM")));
   	   tLogAccountManagement.setTransSerialNo(sequenceGenerator.generateAccountMgmTxnSeq());	   
   	   tLogAccountManagement.setTransType(managerLog.getInnerTxnType());
   	   tLogAccountManagementMapper.insert(tLogAccountManagement);

//  		if (oldAccount.getType().equals(AccountType.FUND.getValue())) {
//  			// 通知
//  			TInfoCustomer customer = (TInfoCustomer) customerDao.findObjectByPk(
//  					"Customer", oldAccount.getCustomerNo());
//  			// 处理账户变动通知
//  			this.handleAccountAlterLog(customer, oldAccount, log, acceptOrg, null);
//  			customer = null;
//  		}

  		// 清除对象内存
  		account = null;
  		//log = null;

  		return BussErrorCode.ERROR_CODE_000000.getErrorcode();
  	}
     
}
