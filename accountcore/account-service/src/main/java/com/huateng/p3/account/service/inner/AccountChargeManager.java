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
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.component.TxnResultGenComponent;
import com.huateng.p3.account.persistence.TLogAccountPaymentMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;

/**
 * manager层控制事务
 * <p/>
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-12-4
 */
@Component
public class AccountChargeManager {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TxnCheckService txnCheckService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RiskCheckService riskCheckService;

    @Autowired
    private RiskService riskService;

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private TxnResultGenComponent txnResultGenComponent;
    
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject charge(AccountInfo accountInfo, PaymentInfo paymentInfo){
    	return innerCharge(accountInfo,paymentInfo);
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject chargeCheck(AccountInfo accountInfo, PaymentInfo paymentInfo){
    	return innerCharge(accountInfo,paymentInfo,true);
    }
    
    
    private TxnResultObject innerCharge(AccountInfo accountInfo, PaymentInfo paymentInfo,boolean... ischeck) {
        /*************************
         * 1. 获取账户
         * 2. 常规校验
         * 3. 权限校验
         * 4. 风控校验
         * 5. 账户操作
         * 6. 写清结算日志
         * 7. 风控推送
         *************************/
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CHARGE);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

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
        
        
        //如果是充值检查，到这里已经完成了所有检测，可以返回
        if (ischeck.length > 0 && ischeck[0]) {
        	//结果对象
        	return txnResultGenComponent.genTxnResultObject(paymentInfo, null, tInfoAccount, tInfoCustomer ,null ,null);
      
        }
        
        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                tInfoCustomer, tInfoAccount, txnSeqNo ,null, TrueFalseLabel.TRUE.getLablCode(), true);
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo ,true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo,null ,null);

        //手续费计算并且入清结算
        TLogOnlinePayment tFeeLogOnlinePayment = accountService.feeCatulatorInDb(paymentInfo, tInfoAccount, tInfoCustomer, acceptOrg, supplyOrg, txnSeqNo);

        //短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,tFeeLogOnlinePayment ,paymentInfo);
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);

        return txnResultGenComponent.genTxnResultObject(paymentInfo, null , tInfoAccount, tInfoCustomer, txnSeqNo , null);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject cancelCharge(PaymentInfo paymentInfo, PaymentInfo originalPayInfo,AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CHARGE, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        //账户交易合法性校验
        //txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

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
                accountService.cancelOldLogOnlinePayment(tInfoAccount, paymentInfo, originalPayInfo , cancelTxnSeqNo);
        //交易日志撤销
        accountService.cancelOldLogAccountPayment(cancelLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);

        //进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, originalPayInfo,
                tInfoCustomer, tInfoAccount, cancelTxnSeqNo ,cancelLogOnlinePayment.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode());

        //取消手续费计算并且入清结算
        TLogOnlinePayment tFeeLogOnlinePayment =accountService.cancelFeeCatulatorInDb(paymentInfo, tInfoAccount, tInfoCustomer, acceptOrg, supplyOrg,
                cancelLogOnlinePayment.getTransSerialNo());
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, cancelTxnSeqNo);

        //账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo, cancelLogOnlinePayment.getInteriorTransType());

        //短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,tFeeLogOnlinePayment , paymentInfo);
        
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, cancelLogOnlinePayment.getInteriorTransType() ,cancelLogOnlinePayment.getExtBusinessType());
        return txnResultGenComponent.genTxnResultObject(paymentInfo, tFeeLogOnlinePayment ,tInfoAccount, tInfoCustomer, cancelTxnSeqNo ,cancelLogOnlinePayment.getTransSerialNo());
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject rollbackCharge(PaymentInfo paymentInfo, PaymentInfo originalPayInfo,AccountInfo accountInfo) {
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CHARGE, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldTxnType(paymentInfo, originalPayInfo);
        txnCheckService.txnIniCheck(originalPayInfo, TxnType.TXN_CHARGE, TxnSeqType.TRANS_SEQ_TYPE_NORMAL);
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        //账户交易合法性校验
        //txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        // 获得中心撤销流水号
        String rollbackSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        //判断是否伪造冲正对象
        TxnResultObject txnResultObject = new TxnResultObject();
        //清算冲正
        TLogOnlinePayment rollbakLogOnlinePayment = accountService.rollbackOldLogOnlinePayment(supplyOrg, acceptOrg,
                paymentInfo, originalPayInfo, tInfoCustomer, tInfoAccount, rollbackSeqNo, TrueFalseLabel.FALSE.getLablCode(),txnResultObject,true);
        //交易日志撤销
        accountService.rollbackOldLogAccountPayment(rollbakLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);

        //进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, originalPayInfo,
                tInfoCustomer, tInfoAccount, rollbackSeqNo ,rollbakLogOnlinePayment.getTransSerialNo(), rollbakLogOnlinePayment.getIsClearing());

        //取消手续费计算并且入清结算
        TLogOnlinePayment tFeeLogOnlinePayment = accountService.rollbackFeeCatulatorInDb(paymentInfo, tInfoAccount, tInfoCustomer, acceptOrg,
        		supplyOrg, rollbakLogOnlinePayment.getTransSerialNo());
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackSeqNo);
        //账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo, rollbakLogOnlinePayment.getInteriorTransType(), txnResultObject.isRollbackFake());               
        //不是伪造的交易处理以下操作
        if(!txnResultObject.isRollbackFake()){
        	//短信通知结算            
            smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,tFeeLogOnlinePayment , paymentInfo);
            //推送账户交易数据到风控库
            riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, rollbakLogOnlinePayment.getInteriorTransType() ,rollbakLogOnlinePayment.getExtBusinessType());
        }
        return txnResultGenComponent.genTxnResultObject(paymentInfo, tFeeLogOnlinePayment,tInfoAccount, tInfoCustomer, rollbackSeqNo , rollbakLogOnlinePayment.getTransSerialNo() , txnResultObject.isRollbackFake());
    }
    
    /**
     * 充值撤销交易的冲正
     * @param paymentInfo
     * @param originalPayInfo
     * @param accountInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject rollbackChargeCancel(PaymentInfo paymentInfo, PaymentInfo originalPayInfo,AccountInfo accountInfo) {
    	// 充值撤消冲正交易的外部交易类型转内部交易类型
        txnCheckService.txnIniCheck(paymentInfo, TxnType.TXN_CHARGE, TxnSeqType.TRANS_SEQ_TYPE_ROLLBACK);
        accountService.setOldCancelTxnType(paymentInfo, originalPayInfo);
        // 充值撤消交易的外部交易类型转内部交易类型
        txnCheckService.txnIniCheck(originalPayInfo, TxnType.TXN_CHARGE, TxnSeqType.TRANS_SEQ_TYPE_CANCEL);
        // 获取账户信息
        TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        // 账户交易合法性校验
        //txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        // 受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        // 商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        // 获得冲正流水号
        String rollbackSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        // 判断是否伪造充值撤销冲正对象
        TxnResultObject txnResultObject = new TxnResultObject();
        // 清算充值撤销冲正交易
        TLogOnlinePayment cancelLogOnlinePayment = accountService.rollbackCancelOldLogOnlinePayment(supplyOrg, acceptOrg,
                paymentInfo, originalPayInfo, tInfoCustomer, tInfoAccount, rollbackSeqNo, TrueFalseLabel.FALSE.getLablCode(), txnResultObject);
        // 交易日志撤销冲正（账户支付交易）
        accountService.rollbackOldLogAccountPayment(cancelLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        // 进清结算
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, originalPayInfo,
                tInfoCustomer, tInfoAccount, rollbackSeqNo ,cancelLogOnlinePayment.getTransSerialNo(), cancelLogOnlinePayment.getIsClearing(), true);
        // 交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackSeqNo, true);
        // 原交易撤消标识清除，原账户支付撤消标识清除
        TLogOnlinePayment originalTLogOnlinePayment = accountService.rollbackCancelOriginalLogPayment(cancelLogOnlinePayment);
        // 账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo, originalTLogOnlinePayment.getInteriorTransType(), null, txnResultObject.isRollbackFake());
        // 充值撤销冲正手续费计算并且入清结算
        TLogOnlinePayment tFeeLogOnlinePayment = accountService.rollbackCancelFeeCatulatorInDb(paymentInfo, tInfoAccount, tInfoCustomer, acceptOrg,
        		supplyOrg, cancelLogOnlinePayment);
        // 不是伪造的交易处理以下操作
        if(!txnResultObject.isRollbackFake()){
        	// 短信通知结算            
            smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,tFeeLogOnlinePayment , paymentInfo);
            // 推送账户交易数据到风控库
           riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, cancelLogOnlinePayment.getInteriorTransType() ,cancelLogOnlinePayment.getExtBusinessType());
        }
        return txnResultGenComponent.genTxnResultObject(paymentInfo, tFeeLogOnlinePayment,tInfoAccount, tInfoCustomer, rollbackSeqNo , cancelLogOnlinePayment.getTransSerialNo() , txnResultObject.isRollbackFake());
    }

    @Autowired
    private TLogAccountPaymentMapper tLogAccountPaymentMapper;

}
