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
import com.huateng.p3.account.commonservice.PreAuthService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.SecurityComponent;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.component.TxnResultGenComponent;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;
import com.huateng.p3.account.persistence.models.TbPosInfo;
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;

/**
 * User: JamesTang
 * Date: 14-1-6
 * Time: 上午10:43
 */
@Component
public class AccountPreAuthManager {

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
    private PreAuthService preAuthService;

    @Autowired
    private TxnResultGenComponent txnResultGenComponent;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private RiskService riskMergeService;//    skMergeService;
    
    @Autowired
    private SmsComponent smsComponent;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject preAuthApply(PaymentInfo paymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH);
        
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 交易密码校验
        //TbPosInfo tbPosInfo = securityService.getTbPosInfo(paymentInfo.getMerchantCode(), paymentInfo.getTerminalNo(), paymentInfo.getAcceptOrgCode());                
        securityService.txnPwdCheck(tInfoCustomer, tInfoAccount , accountInfo);

        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
       
        //受理机构检查
        orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        //账户业务分控检查
        Response<String> accountRiskChkRes = riskCheckService.accountRiskCheck(paymentInfo, tInfoAccount);

        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }
        
        String preAuthTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
        
        TLogPreauthApply tLogPreauthApply = preAuthService.generatorPreAuthApplyInDb(supplyOrg, paymentInfo, null, tInfoAccount, preAuthTxnSeqNo, null);
        accountService.preAuthAccountAlter(tInfoAccount, paymentInfo);
        //短信通知结算
        smsComponent.acountAlterPreNotice(tInfoCustomer,tInfoAccount ,tLogPreauthApply,supplyOrg ,null ,paymentInfo);
        //推送账户交易数据到风控库
        riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        //结果对象
        TxnResultObject txnResultObject = txnResultGenComponent.genTxnResultObject(paymentInfo, null ,tInfoAccount, tInfoCustomer, tLogPreauthApply.getTxnSeqNo() , null);
        return txnResultObject;
    };
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject rollbackPreAuthApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldTxnType(paymentInfo, oldPaymentInfo);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
       
        //受理机构检查
        orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        

        String preAuthTxnSeqNo = sequenceGenerator.generatorRollbackPreAuthTxnSeq();
        
        TxnResultObject txnResultObject = new TxnResultObject();
        
        //查找原预授权申请记录日志
        TLogPreauthApply oldPreAuthApply = preAuthService.rollbackOldPreAuthApply(supplyOrg, oldPaymentInfo, tInfoAccount, preAuthTxnSeqNo, txnResultObject);
        
        
        TLogPreauthApply rollbackLogPreauthApply = preAuthService.generatorPreAuthApplyInDb(supplyOrg,
        		paymentInfo, oldPaymentInfo, tInfoAccount, preAuthTxnSeqNo, oldPreAuthApply);
        
        //伪造交易不需要资金变动和风控
        if(!txnResultObject.isRollbackFake()){
        	// 资金变动
        	accountService.preAuthApplyCancelAccountAlter(tInfoAccount, paymentInfo, oldPreAuthApply.getTxnType());
        	//短信通知结算
            smsComponent.acountAlterPreNotice(tInfoCustomer,tInfoAccount ,rollbackLogPreauthApply,supplyOrg ,null ,paymentInfo);
        	//推送账户交易数据到风控库,需要撤销申请的风控金额,并不是完成的金额
            riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(),
            		null, oldPreAuthApply.getTxnType(), oldPreAuthApply.getBusinessType());
        }
        
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, rollbackLogPreauthApply.getTxnSeqNo() ,oldPreAuthApply.getTxnSeqNo(), txnResultObject.isRollbackFake());
    };
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject cancelPreAuthApply(PaymentInfo paymentInfo, PaymentInfo oldpaymentInfo, AccountInfo accountInfo) {
    	txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
        
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        //受理机构检查
        orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
       
        String cancelPreAuthTxnSeqNo = sequenceGenerator.generatorCancelPreAuthTxnSeq();
        //查找原预授权申请记录日志
        TLogPreauthApply oldLogPreauthApply =preAuthService.cancelOldPreAuthApply(oldpaymentInfo, tInfoAccount, cancelPreAuthTxnSeqNo);
        
       //开始处理撤销
       TLogPreauthApply cancelLogPreauthApply = preAuthService.generatorPreAuthApplyInDb(supplyOrg,
                paymentInfo, oldpaymentInfo, tInfoAccount , cancelPreAuthTxnSeqNo, oldLogPreauthApply);

       accountService.preAuthApplyCancelAccountAlter(tInfoAccount, paymentInfo, oldLogPreauthApply.getTxnType());
       //短信通知结算
       smsComponent.acountAlterPreNotice(tInfoCustomer,tInfoAccount ,cancelLogPreauthApply,supplyOrg ,null ,paymentInfo);
       //推送账户交易数据到风控库,需要撤销申请的风控金额,并不是完成的金额
       riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(),
    		   null, oldLogPreauthApply.getTxnType(), oldLogPreauthApply.getBusinessType());
      
       return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, cancelLogPreauthApply.getTxnSeqNo() ,oldLogPreauthApply.getTxnSeqNo());
    };
    
    /**
     * 预授权完成
     * 
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject preAuthComplete(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
    	
    	 TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

         TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        
         paymentInfo.setTxnType(TxnType.TXN_PREAUTH_END);
         
         //账户交易合法性校验
         txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        
         //受理机构检查
         TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                 paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
         //商户机构检查
         TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                 paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
         
         // 交易密码校验
         //TbPosInfo tbPosInfo = securityService.getTbPosInfo(paymentInfo.getMerchantCode(), paymentInfo.getTerminalNo(), paymentInfo.getAcceptOrgCode());                
         securityService.txnPwdCheck(tInfoCustomer, tInfoAccount , accountInfo);
         
         //确认预授权金额
         TLogPreauthApply oldPreauthApply =preAuthService.preauthCommit(paymentInfo, tInfoAccount, null);
         
         // 预授权完成清算流水号
         String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
         TLogOnlinePayment tLogOnlinePayment =accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                 tInfoCustomer, tInfoAccount, txnSeqNo, null, TrueFalseLabel.TRUE.getLablCode());

         //交易库
         accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo);
         //账户表更新
         accountService.accountPreAuthCommitAlterInDb(tInfoAccount, paymentInfo, oldPreauthApply);

         //短信通知结算
         smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount, tLogOnlinePayment, supplyOrg, null, paymentInfo);
         // 推送账户交易数据到风控库，减少预授权申请和完成的差额
         // TODO 此处会造成风控中的交易次数少一次，暂时不做关注
         riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(),
        		 oldPreauthApply, oldPreauthApply.getTxnType(), oldPreauthApply.getBusinessType());
         //结果对象
         TxnResultObject txnResultObject = txnResultGenComponent.genTxnResultObjectForPreAuth(paymentInfo, null, tInfoAccount, tInfoCustomer, txnSeqNo, null, oldPreauthApply);
         return txnResultObject;
    }
    
    /**
     * 预授权申请的撤销冲正
     * 
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject rollbackCancelPreAuthApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldTxnType(paymentInfo, oldPaymentInfo);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        //受理机构检查
        orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(), true);
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(), true);
        String rollbackCancelPreAuthTxnSeqNo = sequenceGenerator.generatorRollbackPreAuthTxnSeq();
        TxnResultObject txnResultObject = new TxnResultObject();
        //查找原预授权申请撤销记录日志
        TLogPreauthApply oldCancelPreAuthApply = preAuthService.rollbackCancelPreAuthApply(supplyOrg, oldPaymentInfo, tInfoAccount,
        		rollbackCancelPreAuthTxnSeqNo, txnResultObject);
        TLogPreauthApply rollbackCancelLogPreauthApply = preAuthService.generatorPreAuthApplyInDb(supplyOrg,
        		paymentInfo, oldPaymentInfo, tInfoAccount, rollbackCancelPreAuthTxnSeqNo, oldCancelPreAuthApply);
        TLogPreauthApply originalTLogPreauthApply = preAuthService.rollbackCancelPreAuthApply(oldCancelPreAuthApply, txnResultObject.isRollbackFake());
        //伪造交易不需要资金变动和风控
        if(!txnResultObject.isRollbackFake()){
        	// 资金变动
        	accountService.preAuthAccountAlter(tInfoAccount, paymentInfo);
        	//短信通知结算
            smsComponent.acountAlterPreNotice(tInfoCustomer, tInfoAccount, rollbackCancelLogPreauthApply, supplyOrg, null, paymentInfo);
        	//推送账户交易数据到风控库,增加风控额
            riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(),
            		null, null, null, originalTLogPreauthApply.getTxnType(), originalTLogPreauthApply.getBusinessType());
        }
        
        return txnResultGenComponent.genTxnResultObject(paymentInfo, null, tInfoAccount, tInfoCustomer,
        		rollbackCancelLogPreauthApply.getTxnSeqNo(), oldCancelPreAuthApply.getTxnSeqNo(), txnResultObject.isRollbackFake());
    };
    
    /**
     * 预授权完成冲正
     * 
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject rollbackPreAuthComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldTxnType(paymentInfo, oldPaymentInfo);
    	txnCheckService.txnIniCheck(oldPaymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        // 商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        TxnResultObject txnResultObject = new TxnResultObject();
        String rollbackTxnSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        // 查找原预授权清结算日志，设置冲正标识
        TLogOnlinePayment oldTLogOnlinePayment = accountService.rollbackOldLogOnlinePayment(supplyOrg, acceptOrg, paymentInfo,
        		oldPaymentInfo, tInfoCustomer, tInfoAccount, rollbackTxnSeqNo, TrueFalseLabel.FALSE.getLablCode(), txnResultObject);
        // 交易日志冲正（账户支付交易）
        accountService.rollbackOldLogAccountPayment(oldTLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        // 进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo,
        		oldPaymentInfo, tInfoCustomer, tInfoAccount, rollbackTxnSeqNo , oldTLogOnlinePayment.getTransSerialNo(),
        		oldTLogOnlinePayment.getIsClearing(), true);
        // 交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackTxnSeqNo, true);
        
        // 伪造交易不需要资金变动和风控
        if(!txnResultObject.isRollbackFake()){
        	// 清除预授权申请完成的金额和时间
        	TLogPreauthApply tLogPreauthApply = preAuthService.preAuthCompleteCancel(tInfoAccount, oldPaymentInfo);
        	// 资金变动
        	accountService.preAuthCompleteCancelAccountAlter(tInfoAccount, oldPaymentInfo, tLogPreauthApply);
        	// 短信通知结算
            smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount, tLogOnlinePayment, supplyOrg, null, paymentInfo);
            // 推送账户交易数据到风控库，增加预授权申请和完成的差额
            // TODO 此处会造成风控中的交易次数多一次，暂时不做关注
            riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(),
            		tLogPreauthApply, null, null, tLogPreauthApply.getTxnType(), tLogPreauthApply.getBusinessType());
        }
        return txnResultGenComponent.genTxnResultObject(paymentInfo, null, tInfoAccount, tInfoCustomer,
        		tLogOnlinePayment.getTransSerialNo(), oldTLogOnlinePayment.getTransSerialNo(), txnResultObject.isRollbackFake());
    };
    
    /**
     * 预授权完成撤销
     * 
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject cancelPreAuthComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        // 商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        String cancelTxnSeqNo = sequenceGenerator.generatorTxnCancelSeqNo();
        // 查找原预授权清结算日志，设置撤销标识
        TLogOnlinePayment oldTLogOnlinePayment = accountService.cancelOldLogOnlinePayment(tInfoAccount, paymentInfo,
        		oldPaymentInfo, cancelTxnSeqNo);
        // 交易日志撤销（账户支付交易）
        accountService.cancelOldLogAccountPayment(oldTLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        // 进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo,
        		oldPaymentInfo, tInfoCustomer, tInfoAccount, cancelTxnSeqNo , oldTLogOnlinePayment.getTransSerialNo(),
        		oldTLogOnlinePayment.getIsClearing(), true);
        // 交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, cancelTxnSeqNo, true);
        // 清除预授权申请完成的金额和时间
    	TLogPreauthApply tLogPreauthApply = preAuthService.preAuthCompleteCancel(tInfoAccount, oldPaymentInfo);
    	// 资金变动
    	accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo, oldTLogOnlinePayment.getInteriorTransType(), null);
    	// 短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount, tLogOnlinePayment, supplyOrg, null, paymentInfo);
    	// 推送账户交易数据到风控库，减少风控额
        riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND,
        		tInfoCustomer.getCustomerGrade(), null, tLogPreauthApply.getTxnType(), tLogPreauthApply.getBusinessType());
        return txnResultGenComponent.genTxnResultObject(paymentInfo, null, tInfoAccount, tInfoCustomer,
        		tLogOnlinePayment.getTransSerialNo(), oldTLogOnlinePayment.getTransSerialNo());
    };
    
    /**
     * 预授权完成撤销冲正
     * 
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject rollbackCancelPreAuthComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldCancelTxnType(paymentInfo, oldPaymentInfo);
        txnCheckService.txnIniCheck(oldPaymentInfo, TxnType.TXN_PREAUTH, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
    	TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        // 商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        String rollbackSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        TxnResultObject txnResultObject = new TxnResultObject();
        // 查找原预授权完成撤销记录日志
        TLogOnlinePayment cancelLogOnlinePayment = accountService.rollbackCancelOldLogOnlinePayment(supplyOrg, acceptOrg,
        		paymentInfo, oldPaymentInfo, tInfoCustomer, tInfoAccount, rollbackSeqNo, TrueFalseLabel.FALSE.getLablCode(),
        		txnResultObject, true);
        // 交易日志撤销冲正（账户支付交易）
        accountService.rollbackOldLogAccountPayment(cancelLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        // 进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, rollbackSeqNo, cancelLogOnlinePayment.getTransSerialNo(),
                cancelLogOnlinePayment.getIsClearing());
        // 交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackSeqNo);
        // 原交易撤消标识清除，原账户支付撤消标识清除
        TLogOnlinePayment originalTLogOnlinePayment = accountService.rollbackCancelOriginalLogPayment(cancelLogOnlinePayment);
        // 账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo, originalTLogOnlinePayment.getInteriorTransType(), txnResultObject.isRollbackFake());
        // 不是伪造的交易处理以下操作
        if(!txnResultObject.isRollbackFake()){
        	 //确认预授权金额
            TLogPreauthApply oldPreauthApply =preAuthService.preauthCommit(paymentInfo, tInfoAccount, originalTLogOnlinePayment);
        	// 短信通知结算
            smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount, tLogOnlinePayment, supplyOrg, null, paymentInfo);
            // 推送账户交易数据到风控库，增加风控额
            riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND,
            		tInfoCustomer.getCustomerGrade(), null, cancelLogOnlinePayment.getInteriorTransType(), cancelLogOnlinePayment.getExtBusinessType(),
            		oldPreauthApply.getTxnType(), oldPreauthApply.getBusinessType());
        }
        return txnResultGenComponent.genTxnResultObject(paymentInfo, null, tInfoAccount, tInfoCustomer,
        		rollbackSeqNo, cancelLogOnlinePayment.getTransSerialNo(), txnResultObject.isRollbackFake());
    };
}
