package com.huateng.p3.account.service.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.TrueFalseLabel;
import com.huateng.p3.account.common.enummodel.TxnForm;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.SecurityComponent;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.component.TxnResultGenComponent;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TbPosInfo;
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;

/**
 * manager层控制事务.  consume
 * User: JamesTang
 * Date: 13-12-4
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AccountConsumeManager {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TxnCheckService txnCheckService;

    @Autowired
    private SecurityComponent securityService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private RiskCheckService riskCheckService;

    @Autowired
    private RiskService riskService;//    skMergeService;

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private TxnResultGenComponent txnResultGenComponent;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject consume(PaymentInfo paymentInfo, AccountInfo accountInfo) {
        return innerConsume(accountInfo, paymentInfo, true);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject consumeWithoutPwd(PaymentInfo paymentInfo, AccountInfo accountInfo) {
        return innerConsume(accountInfo, paymentInfo);
    }
    
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject quickConsume(AccountInfo accountInfo, PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo) {   
    	txnCheckService.quickConsumecheck(consumePaymentInfo, chargePaymentInfo);
    	txnCheckService.txnIniCheck(chargePaymentInfo, TxnType.TXN_CHARGE);
    	txnCheckService.txnIniCheck(consumePaymentInfo, TxnType.TXN_CONSUME);    	
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, chargePaymentInfo);        
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(chargePaymentInfo.getAcceptOrgCode(),
        		chargePaymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(chargePaymentInfo.getSupplyOrgCode(),
        		chargePaymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        String chargeTxnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        accountService.accountClearInDb(supplyOrg, acceptOrg, chargePaymentInfo, null,
                tInfoCustomer, tInfoAccount, chargeTxnSeqNo ,null, TrueFalseLabel.TRUE.getLablCode(), true);
         
        //充值交易库
        accountService.accounTxnInDb(chargePaymentInfo, tInfoAccount, acceptOrg, chargeTxnSeqNo ,true);
        //充值账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, chargePaymentInfo,null ,null);
        //充值后再检查消费的账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, consumePaymentInfo);
        
        String consumeTxnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        accountService.accountClearInDb(supplyOrg, acceptOrg, consumePaymentInfo, null,
                tInfoCustomer, tInfoAccount , consumeTxnSeqNo , null, TrueFalseLabel.TRUE.getLablCode());      
        //消费交易库
        accountService.accounTxnInDb(consumePaymentInfo, tInfoAccount, acceptOrg, consumeTxnSeqNo);
        //消费账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, consumePaymentInfo, null);     
        //充值推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(chargePaymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        //消费推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(consumePaymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        //结果对象
        TxnResultObject txnResultObject = txnResultGenComponent.genTxnResultObject(consumePaymentInfo,null , tInfoAccount, tInfoCustomer, consumeTxnSeqNo , null);
        return txnResultObject;
    }
    
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject cancelQuickConsume(AccountInfo accountInfo, PaymentInfo consumePaymentInfo, PaymentInfo chargePaymentInfo ,PaymentInfo oldConsumePaymentInfo,PaymentInfo oldChargePaymentInfo) {   
       	txnCheckService.txnIniCheck(chargePaymentInfo, TxnType.TXN_CHARGE, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
       	txnCheckService.txnIniCheck(consumePaymentInfo, TxnType.TXN_CONSUME, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
        
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        //账户交易合法性校验
        //txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(chargePaymentInfo.getAcceptOrgCode(),
        		chargePaymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(chargePaymentInfo.getSupplyOrgCode(),
        		chargePaymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
            
        
        // 获得中心撤销流水号
        String cancelConsumeTxnSeqNo = sequenceGenerator.generatorTxnCancelSeqNo();
     	//消费清算撤销
        TLogOnlinePayment cancelConsumeLogOnlinePayment =
                accountService.cancelOldLogOnlinePayment(tInfoAccount, consumePaymentInfo, oldConsumePaymentInfo ,cancelConsumeTxnSeqNo);
        //消费交易日志撤销
        accountService.cancelOldLogAccountPayment(cancelConsumeLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
     
        //消费进清结算
        accountService.accountClearInDb(supplyOrg, acceptOrg, consumePaymentInfo, oldConsumePaymentInfo,
                tInfoCustomer, tInfoAccount, cancelConsumeTxnSeqNo ,cancelConsumeLogOnlinePayment.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode(), true);
        
        accountService.accounTxnInDb(consumePaymentInfo, tInfoAccount, acceptOrg, cancelConsumeTxnSeqNo ,true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, consumePaymentInfo , cancelConsumeLogOnlinePayment.getInteriorTransType() ,cancelConsumeLogOnlinePayment.getResvFld1());
        
        // 获得中心撤销流水号
        String cancelChargeTxnSeqNo = sequenceGenerator.generatorTxnCancelSeqNo();
        //充值清算撤销
        TLogOnlinePayment cancelChargeLogOnlinePayment =
                accountService.cancelOldLogOnlinePayment(tInfoAccount, chargePaymentInfo, oldChargePaymentInfo , cancelChargeTxnSeqNo);
        //充值交易日志撤销
        accountService.cancelOldLogAccountPayment(cancelChargeLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);

        //充值进清结算
        accountService.accountClearInDb(supplyOrg, acceptOrg, chargePaymentInfo, oldChargePaymentInfo,
                tInfoCustomer, tInfoAccount, cancelChargeTxnSeqNo ,cancelChargeLogOnlinePayment.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode());
        //交易库
        accountService.accounTxnInDb(chargePaymentInfo, tInfoAccount, acceptOrg, cancelChargeTxnSeqNo);
        //账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, chargePaymentInfo, cancelChargeLogOnlinePayment.getInteriorTransType());    
        
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(chargePaymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, cancelChargeLogOnlinePayment.getInteriorTransType() ,cancelChargeLogOnlinePayment.getExtBusinessType());
        riskService.cutomerTxnDataSend(consumePaymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, cancelConsumeLogOnlinePayment.getInteriorTransType() ,cancelConsumeLogOnlinePayment.getExtBusinessType());
         
        return txnResultGenComponent.genTxnResultObject(consumePaymentInfo,null , tInfoAccount, tInfoCustomer, cancelConsumeTxnSeqNo , cancelConsumeLogOnlinePayment.getTransSerialNo());

    }
    
    

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject cancelConsume(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CONSUME, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        //账户交易合法性校验
        //txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        // 获得中心撤销流水号
        String cancelTxnSeqNo = sequenceGenerator.generatorTxnCancelSeqNo();
        //清算撤销
        TLogOnlinePayment cancelLogOnlinePayment =
                accountService.cancelOldLogOnlinePayment(tInfoAccount, paymentInfo, oldPaymentInfo ,cancelTxnSeqNo);
        //交易日志撤销
        accountService.cancelOldLogAccountPayment(cancelLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);

        //进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, cancelTxnSeqNo ,cancelLogOnlinePayment.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode(), true);
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, cancelTxnSeqNo, true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo , cancelLogOnlinePayment.getInteriorTransType() ,cancelLogOnlinePayment.getResvFld1());
        //短信通知结算 
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null , paymentInfo);
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, cancelLogOnlinePayment.getInteriorTransType() ,cancelLogOnlinePayment.getExtBusinessType());
      
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, cancelTxnSeqNo , cancelLogOnlinePayment.getTransSerialNo());
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject rollbackConsume(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CONSUME, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldTxnType(paymentInfo, oldPaymentInfo);
        txnCheckService.txnIniCheck(oldPaymentInfo, TxnType.TXN_CONSUME, TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        //账户交易合法性校验
        //txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        
        String rollbackTxnSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        //判断是否伪造冲正对象
        TxnResultObject txnResultObject = new TxnResultObject();
        //清算冲正
        TLogOnlinePayment rollbackLogOnlinePayment = accountService.rollbackOldLogOnlinePayment(supplyOrg, acceptOrg,
                paymentInfo, oldPaymentInfo, tInfoCustomer, tInfoAccount, rollbackTxnSeqNo, TrueFalseLabel.FALSE.getLablCode() ,txnResultObject);
        //交易日志撤销
        accountService.rollbackOldLogAccountPayment(rollbackLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        //进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, rollbackTxnSeqNo , rollbackLogOnlinePayment.getTransSerialNo(), rollbackLogOnlinePayment.getIsClearing(), true);
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackTxnSeqNo, true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo , rollbackLogOnlinePayment.getInteriorTransType(), rollbackLogOnlinePayment.getResvFld1(),txnResultObject.isRollbackFake());
        //不是伪造的交易处理以下操作
        if(!txnResultObject.isRollbackFake()){
	        //短信通知结算
	        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null , paymentInfo);
	        //推送账户交易数据到风控库
	        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, rollbackLogOnlinePayment.getInteriorTransType() ,rollbackLogOnlinePayment.getExtBusinessType());
	    }
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, rollbackTxnSeqNo , rollbackLogOnlinePayment.getTransSerialNo(), txnResultObject.isRollbackFake());
    }

   
    private TxnResultObject innerConsume(AccountInfo accountInfo, PaymentInfo paymentInfo, boolean... ispwdcheck) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CONSUME);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 交易密码校验
        if (ispwdcheck.length > 0 && ispwdcheck[0]) {
        	//TbPosInfo tbPosInfo = securityService.getTbPosInfo(paymentInfo.getMerchantCode(), paymentInfo.getTerminalNo(), paymentInfo.getAcceptOrgCode());        
            securityService.txnPwdCheck(tInfoCustomer, tInfoAccount, accountInfo);
        }

        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
       
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        //账户业务分控检查
        Response<String> accountRiskChkRes = riskCheckService.accountRiskCheck(paymentInfo, tInfoAccount);

        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }
       
        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                tInfoCustomer, tInfoAccount , txnSeqNo , null, TrueFalseLabel.TRUE.getLablCode());

        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo);
        //账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo, null);

        //短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null, paymentInfo);
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
       
        //结果对象
        TxnResultObject txnResultObject = txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, txnSeqNo , null);
        return txnResultObject;
    }
    
    /**
     * 消费撤消交易的冲正
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject rollbackConsumeCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CONSUME, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldCancelTxnType(paymentInfo, oldPaymentInfo);
        txnCheckService.txnIniCheck(oldPaymentInfo, TxnType.TXN_CONSUME, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 账户交易合法性校验
        //txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        // 受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        // 商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        // 获取交易冲正流水号
        String rollbackTxnSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        // 判断是否伪造消费撤销对象
        TxnResultObject txnResultObject = new TxnResultObject();
        // 清算消费撤销交易冲正
        TLogOnlinePayment cancelLogOnlinePayment = accountService.rollbackCancelOldLogOnlinePayment(supplyOrg, acceptOrg, paymentInfo,
        		oldPaymentInfo, tInfoCustomer, tInfoAccount, rollbackTxnSeqNo, TrueFalseLabel.FALSE.getLablCode(), txnResultObject, true);
        // 交易日志撤销冲正（账户支付交易）
        accountService.rollbackOldLogAccountPayment(cancelLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        // 进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, rollbackTxnSeqNo , cancelLogOnlinePayment.getTransSerialNo(),
                cancelLogOnlinePayment.getIsClearing());
        // 交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackTxnSeqNo);
        // 原交易撤消标识清除，原账户支付撤消标识清除
        TLogOnlinePayment originalTLogOnlinePayment = accountService.rollbackCancelOriginalLogPayment(cancelLogOnlinePayment);
        // 账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo, originalTLogOnlinePayment.getInteriorTransType(),
        		txnResultObject.isRollbackFake());
        // 不是伪造的交易处理以下操作
        if(!txnResultObject.isRollbackFake()){
	        // 短信通知结算
	        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount, tLogOnlinePayment, supplyOrg , null , paymentInfo);
	        // 推送账户交易数据到风控库
	        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null,
	        		null, null, originalTLogOnlinePayment.getInteriorTransType() , originalTLogOnlinePayment.getExtBusinessType());
	    }
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, rollbackTxnSeqNo,
        		cancelLogOnlinePayment.getTransSerialNo(), txnResultObject.isRollbackFake());
    }
    

  /*  @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject offlineConsume(AccountInfo accountInfo, PaymentInfo paymentInfo) {
        return doOfflineConsume(accountInfo, paymentInfo);
    }*/
    
    /*private TxnResultObject doOfflineConsume(AccountInfo accountInfo, PaymentInfo paymentInfo) {
    	TInfoAccount tInfoOfflineAccount = null;
    	TInfoCustomer tInfoCustomer = null;
    	TInfoOrg supplyOrg = null;
    	TInfoOrg acceptOrg = null;
    	// 获取脱机交易流水
    	String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        try{
        	// 外部交易类型转换为内部交易类型
        	txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CONSUME);
        	// 查询脱机账户
        	//tInfoOfflineAccount = accountService.getOfflineAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        	// 查询对应的客户
        	tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoOfflineAccount.getCustomerNo());
        	// 商户机构检查
        	supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
        			paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        	// 受理机构检查
        	acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
        			paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
    		// 账户交易合法性校验
        	txnCheckService.checkAccountPaymentTxn(tInfoOfflineAccount, paymentInfo);
        	// 客户交易合法性校验
        	txnCheckService.checkCustomerPaymentTxn(tInfoCustomer);
        	// 卡TAC检验
        	txnCheckService.checkCardTAC(paymentInfo, tInfoOfflineAccount);
           	// 记录脱机交易的清结算日志
        	accountService.offlineAccountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
        			tInfoCustomer, tInfoOfflineAccount , txnSeqNo , null, null);
        	//交易库
        	accountService.offlineAccounTxnInDb(paymentInfo, tInfoOfflineAccount, supplyOrg, acceptOrg, txnSeqNo);
        	//账户表更新
        	accountService.offlineAccountDecreaseAlterInDb(tInfoOfflineAccount, paymentInfo);
    	}catch(BizException e){
    		accountService.offlineAccountDubiousTxn(supplyOrg, acceptOrg, paymentInfo, null,
    				tInfoCustomer, tInfoOfflineAccount, txnSeqNo, null, null, e.getCode(), e.getMessage());
    	}
		TxnResultObject txnResultObject = txnResultGenComponent.genOfflineTxnResultObject(paymentInfo, null, tInfoOfflineAccount,
		    		tInfoCustomer, txnSeqNo, null);
		return txnResultObject;
    }*/
    
}
