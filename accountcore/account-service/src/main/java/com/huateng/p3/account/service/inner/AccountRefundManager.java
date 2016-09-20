package com.huateng.p3.account.service.inner;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.RefundStatus;
import com.huateng.p3.account.common.enummodel.TrueFalseLabel;
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.component.TxnResultGenComponent;
import com.huateng.p3.account.persistence.TInfoAccountMapper;
import com.huateng.p3.account.persistence.TLogRefundApplyMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;
import com.huateng.p3.account.persistence.models.TLogRefundApply;

/**
 * manager层控制事务.  refund
 * User: huwenjie
 * Date: 13-12-31
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AccountRefundManager {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private TxnCheckService txnCheckService;
    
    @Autowired
    private OrgService orgService;

    @Autowired
    private TLogRefundApplyMapper tLogRefundApplyMapper;

    @Autowired
    private TxnResultGenComponent txnResultGenComponent;
    
    @Autowired
    private SmsComponent smsComponent;
    
    @Autowired
    private RiskService riskService;
    
    @Autowired
    private TInfoAccountMapper tInfoAccountMapper;
        
    @Autowired
    private SequenceGenerator sequenceGenerator;
    
    private static String REFUNDREMARK = "核心系统退货";
    
    private static String APPLYUID = "System";
    
    


    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public TxnResultObject refundApply(PaymentInfo paymentInfo ,PaymentInfo oldPaymentInfo) {
        return innerRefundApply(paymentInfo, oldPaymentInfo);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject refundApplyCheck(PaymentInfo paymentInfo,PaymentInfo oldPaymentInfo) {       	
    	return innerRefundApply(paymentInfo, oldPaymentInfo, true);           	
    }
    
    
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject refundNoApplyAudit(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo) {   	   	
    	String refundTxnSeqNo = sequenceGenerator.generatorRefundTxnSeqNo();
    	TLogOnlinePaymentHis tLogOnlinePaymentHis = accountService.returnOldLogOnlinePayment(paymentInfo, oldPaymentInfo, refundTxnSeqNo);
    	//将原交易的内容设置为paymentInfo的值
    	setRefundPaymentInfo(tLogOnlinePaymentHis,paymentInfo,refundTxnSeqNo);  	    	
    	TInfoAccount tInfoAccount = null;
        TInfoCustomer tInfoCustomer = null;
        if (tLogOnlinePaymentHis.getAccountNo() != null) {
        	tInfoAccount = tInfoAccountMapper.findFundAccountByAccountNoForUpdate(tLogOnlinePaymentHis.getAccountNo());
        	tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        	 //账户交易合法性校验
            txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        }       
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
	            paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
	    //商户机构检查
	    TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
	            paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());       
	    //支付机构检查
	    TInfoOrg payOrg = orgService.getValidOrg(paymentInfo.getPayOrgCode(),
	            paymentInfo.getInnerTxnType(), null);
        
        //检查商户退货规则
	    orgService.getMerchantRefundRule(supplyOrg.getOrgCode(),paymentInfo,tLogOnlinePaymentHis ,OrgType.ORG_TYPE_MERCHANT.getOrgtype());
	    //检查支付机构退货规则
	    orgService.getMerchantRefundRule(payOrg.getOrgCode(),paymentInfo,tLogOnlinePaymentHis ,null);      
	    

	    TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, refundTxnSeqNo ,tLogOnlinePaymentHis.getTransSerialNo(), TrueFalseLabel.TRUE.getLablCode(), true);
	    if (tInfoAccount != null) {
	    	//交易库
	        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, refundTxnSeqNo ,true);    	
	        //计算可提现金额，主要处理多次退货的问题
	        String withdrawBalanceStr = withdrawBalanceCount(tLogOnlinePaymentHis,paymentInfo);	        
	        //账户表更新
	        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo ,tLogOnlinePaymentHis.getInteriorTransType(),withdrawBalanceStr);
	        //短信通知结算
	        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null , paymentInfo);
	        //推送账户交易数据到风控库
	        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, tLogOnlinePaymentHis.getInteriorTransType() ,tLogOnlinePaymentHis.getExtBusinessType());
	    }
	    
	    
        return txnResultGenComponent.genTxnResultObject(oldPaymentInfo,null , tInfoAccount ,tInfoCustomer , refundTxnSeqNo , tLogOnlinePaymentHis.getTransSerialNo());

    }

    
    private void setRefundPaymentInfo(TLogOnlinePaymentHis tLogOnlinePaymentHis,PaymentInfo paymentInfo,String refundTxnSeqNo){
    	DateTime txnDate = DateTime.now();
    	//将原交易的内容设置为paymentInfo的值
        paymentInfo.setChannel(tLogOnlinePaymentHis.getAcceptChannel());
        paymentInfo.setPayOrgCode(tLogOnlinePaymentHis.getPayOrgCode());
        paymentInfo.setSupplyOrgCode(tLogOnlinePaymentHis.getSupplyOrgCode());
        paymentInfo.setAcceptOrgCode(tLogOnlinePaymentHis.getAcceptOrgCode());
        paymentInfo.setBussinessType(paymentInfo.getBussinessType());
        paymentInfo.setInnerTxnType(TxnInnerType.TXN_TYPE_261010.getTxnInnerType());
        //todo 设置暂时没有卡交易 不实现 tLogOnlinePaymentHis.getInnerCardNo();
        paymentInfo.setTxnType(TxnType.TXN_CONSUME);              
        paymentInfo.setTxnSeqType(TxnSeqType.TRANS_SEQ_TYPE_REFUND);
        paymentInfo.setAcceptTxnSeqNo(refundTxnSeqNo);
        paymentInfo.setTerminalNo(null);
        paymentInfo.setAcceptDate(txnDate.toString("yyyyMMdd"));
        paymentInfo.setAcceptTime(txnDate.toString("HHmmss"));        
        paymentInfo.setTxnDate(txnDate.toDate());
    	
    	
    }
   
    private TxnResultObject innerRefundApply(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo,boolean... ischeck) {
    	
        TLogOnlinePaymentHis tLogOnlinePaymentHis = accountService.findOldLogOnlinePayment(
                paymentInfo, oldPaymentInfo);
        //将原交易的内容设置为paymentInfo的值
    	setRefundPaymentInfo(tLogOnlinePaymentHis,paymentInfo,null);  	    
        TInfoAccount tInfoAccount = null;
        TInfoCustomer tInfoCustomer = null;
        if (tLogOnlinePaymentHis.getAccountNo() != null) {
        	tInfoAccount = tInfoAccountMapper.findFundAccountByAccountNo(tLogOnlinePaymentHis.getAccountNo());       	
        	 //账户交易合法性校验
            txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
            tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        }
        
        //受理机构检查
	    orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
	            paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
	    //商户机构检查
	    TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
	            paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());       
	    //支付机构检查
	    TInfoOrg payOrg = orgService.getValidOrg(paymentInfo.getPayOrgCode(),
	            paymentInfo.getInnerTxnType(), null);
        
        //检查商户退货规则
	    orgService.getMerchantRefundRule(supplyOrg.getOrgCode(),paymentInfo,tLogOnlinePaymentHis,OrgType.ORG_TYPE_MERCHANT.getOrgtype());
	    //检查支付机构退货规则
	    orgService.getMerchantRefundRule(payOrg.getOrgCode(),paymentInfo,tLogOnlinePaymentHis,null);      
        
        //如果是退货检查，不需要插入退货记录
        if (!(ischeck.length > 0 && ischeck[0])) {
        	insertRefundApply(tInfoAccount, paymentInfo, tLogOnlinePaymentHis);    
        }
        return txnResultGenComponent.genTxnResultObject(oldPaymentInfo,null , tInfoAccount ,tInfoCustomer , null , tLogOnlinePaymentHis.getTransSerialNo());
    }

    
    
    
    private int insertRefundApply(TInfoAccount tInfoAccount , PaymentInfo paymentInfo, TLogOnlinePaymentHis tLogOnlinePaymentHis){
    	
    	//插入退货申请表
        TLogRefundApply refund =  new TLogRefundApply();
        refund.setRecordNo(sequenceGenerator.generatorRefundApplySeq());
        refund.setAcceptTxnDate(tLogOnlinePaymentHis.getAcceptOrgTransDate());
        refund.setAcceptTxnTime(tLogOnlinePaymentHis.getAcceptOrgTransTime());
        refund.setAccountNo(tInfoAccount.getAccountNo());
        refund.setlTxnSeqNo(tLogOnlinePaymentHis.getTransSerialNo());
        refund.setOrgCode(tLogOnlinePaymentHis.getAcceptOrgCode());
        refund.setStatus(RefundStatus.REFUND_STATUS_FIRST_PASS.getRefundStatusCode());
        refund.setTerminalNo(tLogOnlinePaymentHis.getTerminalNo());
        refund.setTerminalSeqNo(tLogOnlinePaymentHis.getTerminalSerialNo());
        refund.setTxnAmount(paymentInfo.getAmount());
        refund.setRemark(REFUNDREMARK);
        refund.setAcceptSeqNo(tLogOnlinePaymentHis.getAcceptOrgSerialNo());
        refund.setApplyUid(APPLYUID);
        return tLogRefundApplyMapper.insertSelective(refund);
    	
    	
    }
    
    //计算可提现金额
    private String withdrawBalanceCount(TLogOnlinePaymentHis tLogOnlinePaymentHis,PaymentInfo paymentInfo){
    	//原交易没有记录提现额度 ，直接返回null，走老流程
    	if(Strings.isNullOrEmpty(tLogOnlinePaymentHis.getResvFld1())){   		
    		return null;
    	}
    	//可提现金额计算
        String withdrawBalanceStr =  Long.toString(Long.valueOf(tLogOnlinePaymentHis.getResvFld1()) - tLogOnlinePaymentHis.getReturnAmount() +paymentInfo.getAmount());
        //判断是否大于0，可能会小于0
        withdrawBalanceStr =  Long.valueOf(withdrawBalanceStr)>0?withdrawBalanceStr:"0" ;
        return withdrawBalanceStr;
    }

}
