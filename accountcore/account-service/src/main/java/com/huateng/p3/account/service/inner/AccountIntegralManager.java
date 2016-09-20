package com.huateng.p3.account.service.inner;

import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.TrueFalseLabel;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.commonservice.*;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.component.TxnResultGenComponent;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dongpeiji
 * Date: 14-9-14
 * Time: 下午1:05
 */
@Component
public class AccountIntegralManager {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TxnCheckService txnCheckService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RiskService riskService;

    @Autowired
    private RiskCheckService riskCheckService;

    @Autowired
    private TxnResultGenComponent txnResultGenComponent;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject chargeOrConsume(AccountInfo accountInfo, PaymentInfo paymentInfo,boolean bool){
        return innerChargeOrConsume(accountInfo,paymentInfo,bool);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject chargeOrConsumeCheck(AccountInfo accountInfo, PaymentInfo paymentInfo,boolean bool){
        return innerChargeOrConsume(accountInfo,paymentInfo,bool,true);
    }

    private TxnResultObject innerChargeOrConsume(AccountInfo accountInfo, PaymentInfo paymentInfo,boolean bool,boolean... ischeck) {
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

        //账户业务风控检查
        Response<String> accountRiskChkRes = riskCheckService.accountRiskCheck(paymentInfo, tInfoAccount);
        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }


        //如果是充值后者消费检查，到这里已经完成了所有检测，可以返回
        if (ischeck.length > 0 && ischeck[0]) {
            //结果对象
            return txnResultGenComponent.getIntegralTxnResultObject(paymentInfo, tInfoAccount, tInfoCustomer ,null ,null);

        }

        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                tInfoCustomer, tInfoAccount, txnSeqNo ,null, TrueFalseLabel.TRUE.getLablCode(), true);
        if(bool){//充值
            //交易库
            accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo ,true);
            //账户表更新
            accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo,null ,null);
        }else{//消费
            //交易库
            accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo ,false);
            //账户表更新
            accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo,null ,null);
        }




       /* //短信通知结算
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null ,paymentInfo);
        */
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.INTEGRAL, tInfoCustomer.getCustomerGrade(), null);

        return txnResultGenComponent.getIntegralTxnResultObject(paymentInfo, tInfoAccount, tInfoCustomer, txnSeqNo , null);
    }
}
