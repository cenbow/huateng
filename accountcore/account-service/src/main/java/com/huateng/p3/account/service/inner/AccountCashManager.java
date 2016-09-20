package com.huateng.p3.account.service.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.KeyType;
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
import com.huateng.p3.account.persistence.models.TLogCashApply;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;
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
public class AccountCashManager {

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
    //提现申请的交易类型后2位
    private static final String APPLYTXNTYPE = "00";

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject cashApply(PaymentInfo paymentInfo, AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CASH);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 交易密码校验
        TbPosInfo tbPosInfo = securityService.getTbPosInfo(paymentInfo.getMerchantCode(), paymentInfo.getTerminalNo(), paymentInfo.getAcceptOrgCode());
        //TODO 交易密码效验下次打开
        securityService.txnPwdCheck(tInfoCustomer,tInfoAccount,accountInfo);

        //账户交易合法性校验
       txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
       
        //受理机构检查
        orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        //账户业务风控检查
       Response<String> accountRiskChkRes = riskCheckService.accountRiskCheck(paymentInfo, tInfoAccount);

        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }
        
        String cashApplyTxnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();

        TLogPreauthApply tLogPreauthApply = preAuthService.generatorPreAuthApplyInDb(supplyOrg, paymentInfo, null, tInfoAccount,cashApplyTxnSeqNo, null);
        accountService.cashAccountAlter(tInfoAccount, paymentInfo);
        //结果对象
        TxnResultObject txnResultObject = txnResultGenComponent.genTxnResultObject(paymentInfo, null ,tInfoAccount, tInfoCustomer, tLogPreauthApply.getTxnSeqNo() , null);
        
        //推送账户交易数据到风控库
        riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        
        return txnResultObject;
    }



    /**
     * 提现完成
     *
     * @param paymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject cashComplete(AccountInfo accountInfo ,PaymentInfo paymentInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CASH, TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        //写死设一个消费大类做检查
        paymentInfo.setTxnType(TxnType.TXN_CASH_END);
        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        // 交易密码校验
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        
        preAuthService.preauthCommit(paymentInfo, tInfoAccount, null);

        // 预授权完成清算流水号
        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tLogOnlinePayment =accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                tInfoCustomer, tInfoAccount, txnSeqNo, null, TrueFalseLabel.TRUE.getLablCode());

        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo);
        //账户表更新
        accountService.accountCashCommitAlterInDb(tInfoAccount, paymentInfo);

        //短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer,tInfoAccount ,tLogOnlinePayment,supplyOrg ,null ,paymentInfo);
        //结果对象
        TxnResultObject txnResultObject = txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, txnSeqNo , null);
        return txnResultObject;
    }



    /**
     * 提现失败
     * * @param paymentInfo
     *
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject cashFailComplete(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {

        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CASH, TxnSeqType.TRANS_SEQ_TYPE_REFUND);

        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);

        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
       

        String refundCashCommitTxnSeqNo = sequenceGenerator.generatorRefundTxnSeqNo();
        TLogOnlinePaymentHis cancelLogOnlinePayment = accountService.returnOldLogOnlinePayment(paymentInfo, oldPaymentInfo, refundCashCommitTxnSeqNo);

        //进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, refundCashCommitTxnSeqNo, cancelLogOnlinePayment.getTransSerialNo(),
                TrueFalseLabel.TRUE.getLablCode(), true);
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, refundCashCommitTxnSeqNo,true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo , cancelLogOnlinePayment.getInteriorTransType() , cancelLogOnlinePayment.getResvFld1());
        //短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer,tInfoAccount ,tLogOnlinePayment,supplyOrg ,null , paymentInfo);
        //推送账户交易数据到风控库
        riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, cancelLogOnlinePayment.getInteriorTransType().substring(0, 4)+APPLYTXNTYPE ,cancelLogOnlinePayment.getExtBusinessType().substring(0, 4)+APPLYTXNTYPE);
       
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, refundCashCommitTxnSeqNo , cancelLogOnlinePayment.getTransSerialNo());
    }

    ;
    
    /**
     * 提现失败撤销
     * * @param paymentInfo
     *
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject cashFailCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {

        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CASH, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);

        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);

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
                tInfoCustomer, tInfoAccount, cancelTxnSeqNo, cancelLogOnlinePayment.getTransSerialNo(),
                TrueFalseLabel.FALSE.getLablCode(), true);
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, cancelTxnSeqNo,true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo , cancelLogOnlinePayment.getInteriorTransType() , cancelLogOnlinePayment.getResvFld1());
        //短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer,tInfoAccount ,tLogOnlinePayment,supplyOrg ,null , paymentInfo);
        //推送账户交易数据到风控库
        riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, cancelLogOnlinePayment.getInteriorTransType().substring(0, 4)+APPLYTXNTYPE ,cancelLogOnlinePayment.getExtBusinessType().substring(0, 4)+APPLYTXNTYPE);
       
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, cancelTxnSeqNo , cancelLogOnlinePayment.getTransSerialNo());
    }




    /**
     * 提现申请撤销
     *
     * @param paymentInfo
     * @param oldPaymentInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject cashApplyCancel(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {


        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CASH, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);

        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());

        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);


        //受理机构检查
        orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
       
        String cancelCashApplyTxnSeqNo = sequenceGenerator.generatorCancelPreAuthTxnSeq();
        TLogPreauthApply oldLogPreauthApply =preAuthService.cancelOldPreAuthApply(oldPaymentInfo, tInfoAccount ,cancelCashApplyTxnSeqNo);
         //开始处理撤销
        TLogPreauthApply cancelLogPreauthApply = preAuthService.generatorPreAuthApplyInDb(supplyOrg,
                paymentInfo, oldPaymentInfo, tInfoAccount , cancelCashApplyTxnSeqNo, oldLogPreauthApply);
        accountService.cashApplyCancelAccountAlter(tInfoAccount,paymentInfo , oldLogPreauthApply.getTxnType());
        //推送账户交易数据到风控库,需要撤销申请的风控金额,并不是完成的金额
        riskMergeService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, oldLogPreauthApply.getTxnType(),oldLogPreauthApply.getBusinessType());
       
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, cancelLogPreauthApply.getTxnSeqNo() ,oldLogPreauthApply.getTxnSeqNo());
    }
    ;
     
    /**
     * 查找体现记录
     *
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public TLogCashApply selectTLogCashApply(String transSeqNo) {
        // 得到账户信息
    	TLogCashApply cashApply = accountService.selectTLogCashApplyByPk(transSeqNo);
        return cashApply;

    }
    /**
     * 插入体现记录
     *
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TLogCashApply insertTLogCashApply(TLogCashApply cashApply) {
        // 得到账户信息
         accountService.insertTlogCash(cashApply);
        return cashApply;

    }
    
    /**
     * 更新体现记录
     *
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TLogCashApply updateTLogCashApply(TLogCashApply cashApply) {
        // 得到账户信息
         accountService.updateTLogCashApply(cashApply);
        return cashApply;

    }
       

}
