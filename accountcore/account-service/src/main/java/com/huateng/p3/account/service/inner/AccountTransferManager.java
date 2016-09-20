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
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.common.util.BeanMapper;
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
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;

/**
 * manager层控制事务.  transfer
 * User: huwenjie
 * Date: 13-12-31
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AccountTransferManager {
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
    public TxnResultObject transferCheck(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	//外部一个paymentInfo与accountInfo，内部拆分成转出与转入两个
    	PaymentInfo targetPaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, targetPaymentInfo);
    	TInfoAccount tInfoAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	TInfoAccount tTargetInfoAccount = accountService.getAccount(accountInfo.getTargetAccountKey(), accountInfo.getTargetKeyType());
        return innerTransfer(accountInfo, paymentInfo,  targetPaymentInfo, tInfoAccount, tTargetInfoAccount ,true);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject transfer(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	//外部一个paymentInfo与accountInfo，内部拆分成转出与转入两个
    	PaymentInfo targetPaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, targetPaymentInfo);
    	TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());    	
    	TInfoAccount tTargetInfoAccount = accountService.getAccountForUpdate(accountInfo.getTargetAccountKey(), accountInfo.getTargetKeyType());
    	return innerTransfer(accountInfo, paymentInfo,  targetPaymentInfo,tInfoAccount ,tTargetInfoAccount);
    }

   
    private TxnResultObject innerTransfer(AccountInfo accountInfo, PaymentInfo paymentInfo, PaymentInfo targetPaymentInfo, TInfoAccount tInfoAccount ,TInfoAccount tTargetInfoAccount,boolean... ischeck) {
    	//写死设一个消费大类和交易类型
    	txnCheckService.txnIniNoCheck(paymentInfo,TxnInnerType.TXN_TYPE_141020.getTxnInnerType(), TxnType.TXN_TRANSFER);
    	txnCheckService.txnIniNoCheck(targetPaymentInfo,TxnInnerType.TXN_TYPE_141010.getTxnInnerType(), TxnType.TXN_TRANSFER_END);
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        TInfoCustomer tTargetInfoCustomer = customerService.findValidCustomerByCustomerNo(tTargetInfoAccount.getCustomerNo());
       
        
        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        txnCheckService.checkAccountPaymentTxn(tTargetInfoAccount, targetPaymentInfo,false);
        

        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        
//      
//        //账户业务分控检查
        Response<String> accountRiskChkRes = riskCheckService.accountRiskCheck(paymentInfo, tInfoAccount);
        
        
        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }
        //转入检查
        accountRiskChkRes = riskCheckService.accountRiskCheck(targetPaymentInfo, tTargetInfoAccount);

        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }
        //如果是转账检查，到这里已经完成了所有检测，可以返回
        if (ischeck.length > 0 && ischeck[0]) {
        	//结果对象
        	return txnResultGenComponent.genTxnResultObjectForTransfer(paymentInfo,targetPaymentInfo, tInfoAccount, tTargetInfoAccount, tInfoCustomer, tTargetInfoCustomer ,null , null);     
        }
              
        
        // 转出账号交易密码校验        
        //TbPosInfo tbPosInfo = securityService.getTbPosInfo(paymentInfo.getMerchantCode(), paymentInfo.getTerminalNo(), paymentInfo.getAcceptOrgCode());                
        securityService.txnPwdCheck(tInfoCustomer, tInfoAccount, accountInfo);
        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                tInfoCustomer, tInfoAccount , txnSeqNo , null, TrueFalseLabel.TRUE.getLablCode());
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo);
        
        String targetTxnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tTargetLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, targetPaymentInfo, null,
        		tTargetInfoCustomer, tTargetInfoAccount , targetTxnSeqNo , null, TrueFalseLabel.TRUE.getLablCode() ,true);
        //交易库
        accountService.accounTxnInDb(targetPaymentInfo, tTargetInfoAccount, acceptOrg, targetTxnSeqNo ,true);
        
        // 是否存在主子账户关系的标志
     	accountService.checkSubaccountinfoByMainaccno(tInfoAccount,tTargetInfoAccount);
        //账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo, null);
        accountService.accountIncreaseAlterInDb(tTargetInfoAccount, targetPaymentInfo , null , null);
        //短信通知结算 
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null ,paymentInfo);
        smsComponent.acountAlterNotice(tTargetInfoCustomer, tTargetInfoAccount,tTargetLogOnlinePayment,supplyOrg ,null ,targetPaymentInfo);
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        riskService.cutomerTxnDataSend(targetPaymentInfo, tTargetInfoAccount.getAccountNo(), AccountType.FUND, tTargetInfoCustomer.getCustomerGrade(), null);
    
        //结果对象
        TxnResultObject txnResultObject = txnResultGenComponent.genTxnResultObjectForTransfer(paymentInfo, targetPaymentInfo,tInfoAccount, tTargetInfoAccount, tInfoCustomer, tTargetInfoCustomer ,txnSeqNo , targetTxnSeqNo);
        return txnResultObject; 
    }


}
