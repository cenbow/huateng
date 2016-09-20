package com.huateng.p3.account.component;

import com.huateng.p3.account.common.tools.activemq.AppCode;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.SmsSendInfo;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.persistence.TLogAccountAlterMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogAccountAlter;
import com.huateng.p3.account.persistence.models.TLogAccountManagement;
import com.huateng.p3.account.persistence.models.TLogOfflinePayment;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;

@Component
public class SmsComponent {

   /* @Autowired
    private RabbitTemplateComponent rabbitTemplateComponent;*/
    @Autowired
    private ActiveMqTemplateComponent activeMqTemplateComponent;
    
    @Autowired
    private TLogAccountAlterMapper tLogAccountAlterMapper;
    
    @Autowired
    private SequenceGenerator sequenceGenerator;


    public void createAndSendSms(String mobilePhone, String bussinessType) {
        if (null == mobilePhone) {
            return;
        }
        // 创建短信模型并赋值
        SmsSendInfo smsSendInfo =new SmsSendInfo();
        smsSendInfo.setTxnTime(DateTime.now().toDate());
        smsSendInfo.setMobilePhone(mobilePhone);
        smsSendInfo.setBussinessType(bussinessType);
        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,null,smsSendInfo);
        /*rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.SMS_QUEKEY,smsSendInfo);*/
        
        
        
    }

    /**
     * 交易类账户变动通知
     */
    public void acountAlterNotice(TInfoCustomer tInfoCustomer,TInfoAccount tInfoAccount,TLogOnlinePayment tLogOnlinePayment,
    		TInfoOrg supplyOrg, TLogOnlinePayment tFeeLogOnlinePayment, PaymentInfo paymentInfo) {
    	if(false == paymentInfo.isSmsSend()){  		
    		return;
    	}
    	SmsSendInfo smsSendInfo =new SmsSendInfo();
    	smsSendInfo.setTxnChannel(tLogOnlinePayment.getAcceptChannel());
		smsSendInfo.setTxnType(tLogOnlinePayment.getInteriorTransType());
		smsSendInfo.setTxnDscpt(tLogOnlinePayment.getTransMemo());
		smsSendInfo.setAcceptOrgCode(tLogOnlinePayment.getAcceptOrgCode());
		smsSendInfo.setAcceptTransDate(tLogOnlinePayment.getAcceptOrgTransDate());
		smsSendInfo.setAcceptTransTime(tLogOnlinePayment.getAcceptOrgTransTime());
		smsSendInfo.setAcceptTransSeqNo(tLogOnlinePayment.getAcceptOrgSerialNo());
		smsSendInfo.setTxnTime(tLogOnlinePayment.getTransTime());
		smsSendInfo.setMerchantName(supplyOrg.getOrgFname());
		if (tInfoCustomer != null) {
			smsSendInfo.setCustomerNo(tInfoCustomer.getCustomerNo());
			smsSendInfo.setMobilePhone(tInfoCustomer.getMobileNo());
			smsSendInfo.setEmail(tInfoCustomer.getEmailAddress());
		}
		
		if (tInfoAccount != null) {
			smsSendInfo.setAccountNo(tInfoAccount.getAccountNo());
			smsSendInfo.setAccountType(tInfoAccount.getType());
			smsSendInfo.setBalance(StringUtil.formatNumber((double) tInfoAccount.getBalance() / 100, 2));			
		}		
		smsSendInfo.setTxnAmount(StringUtil.formatNumber((double) tLogOnlinePayment.getTransAmount() / 100, 2));
		//若外部交易类型不为空且外部交易类型和内部交易类型不同，外部交易类型入预留字段
		if(!Strings.isNullOrEmpty(tLogOnlinePayment.getExtBusinessType()) && tLogOnlinePayment.getExtBusinessType().length()==6){
			smsSendInfo.setBussinessType(tLogOnlinePayment.getExtBusinessType());
		}
		
		if( null != tFeeLogOnlinePayment){			
			smsSendInfo.setFeeAmt(StringUtil.formatNumber((double) tFeeLogOnlinePayment.getTransAmount() / 100, 2));
		}
		smsSendInfo.setFrontOrgCode(paymentInfo.getFrontOrgCode());
        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,null,smsSendInfo);
		/*rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.SMS_QUEKEY,smsSendInfo);*/
    }
    
    
    /**
     * 交易类预授权变动通知
     */
    public void acountAlterPreNotice(TInfoCustomer tInfoCustomer,TInfoAccount tInfoAccount,TLogPreauthApply tLogPreauthApply,
    		TInfoOrg supplyOrg, TLogOnlinePayment tFeeLogOnlinePayment, PaymentInfo paymentInfo) {
    	if(false == paymentInfo.isSmsSend()){  		
    		return;
    	}
    	SmsSendInfo smsSendInfo =new SmsSendInfo();
    	smsSendInfo.setTxnChannel(tLogPreauthApply.getTxnChannel());
		smsSendInfo.setTxnType(tLogPreauthApply.getTxnType());
		smsSendInfo.setTxnDscpt(tLogPreauthApply.getTxnDscpt());
		smsSendInfo.setAcceptOrgCode(tLogPreauthApply.getAcceptOrgCode());
		smsSendInfo.setAcceptTransDate(tLogPreauthApply.getAcceptTransDate());
		smsSendInfo.setAcceptTransTime(tLogPreauthApply.getAcceptTransTime());
		smsSendInfo.setAcceptTransSeqNo(tLogPreauthApply.getAcceptTransSeqNo());
		smsSendInfo.setTxnTime(tLogPreauthApply.getTxnTime());
		smsSendInfo.setMerchantName(supplyOrg.getOrgFname());
		if (tInfoCustomer != null) {
			smsSendInfo.setCustomerNo(tInfoCustomer.getCustomerNo());
			smsSendInfo.setMobilePhone(tInfoCustomer.getMobileNo());
			smsSendInfo.setEmail(tInfoCustomer.getEmailAddress());
		}
		
		if (tInfoAccount != null) {
			smsSendInfo.setAccountNo(tInfoAccount.getAccountNo());
			smsSendInfo.setAccountType(tInfoAccount.getType());
			smsSendInfo.setBalance(StringUtil.formatNumber((double) tInfoAccount.getBalance() / 100, 2));			
		}		
		smsSendInfo.setTxnAmount(StringUtil.formatNumber((double) tLogPreauthApply.getAuthAmt() / 100, 2));
		//若外部交易类型不为空且外部交易类型和内部交易类型不同，外部交易类型入预留字段
		if(!Strings.isNullOrEmpty(tLogPreauthApply.getBusinessType()) && tLogPreauthApply.getBusinessType().length()==6){
			smsSendInfo.setBussinessType(tLogPreauthApply.getBusinessType());
		}
		
		if( null != tFeeLogOnlinePayment){			
			smsSendInfo.setFeeAmt(StringUtil.formatNumber((double) tFeeLogOnlinePayment.getTransAmount() / 100, 2));
		}
		smsSendInfo.setFrontOrgCode(paymentInfo.getFrontOrgCode());
        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,null,smsSendInfo);
		/*rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.SMS_QUEKEY,smsSendInfo);*/
    }
    
    
    /**
     * 管理类账户变动通知
     */
    public void acountManagerNotice(TInfoCustomer tInfoCustomer,TInfoAccount tInfoAccount,TLogAccountManagement tLogAccountManagement,
    		TInfoOrg merchant,TLogOnlinePayment tFeeLogOnlinePayment ,String txnPwd, String loginPwd ,String outTxnType) {
    	//  不是资金子账户不发短信
    	if (!AccountType.FUND.getValue().equals(tInfoAccount.getType())) {
    		return;
    	}  	
    	SmsSendInfo smsSendInfo =new SmsSendInfo();
    	smsSendInfo.setTxnChannel(tLogAccountManagement.getAcceptChannel());
		smsSendInfo.setTxnType(tLogAccountManagement.getTransType());
		smsSendInfo.setTxnDscpt(tLogAccountManagement.getTransMemo());
		smsSendInfo.setAcceptOrgCode(tLogAccountManagement.getOrgCode());
		smsSendInfo.setAcceptTransDate(tLogAccountManagement.getAcceptDate());
		smsSendInfo.setAcceptTransTime(tLogAccountManagement.getAcceptTime());
		smsSendInfo.setAcceptTransSeqNo(tLogAccountManagement.getAcceptOrgSerialNo());
		smsSendInfo.setTxnTime(tLogAccountManagement.getTransTime());
		smsSendInfo.setMerchantName(merchant.getOrgFname());
		if (tInfoCustomer != null) {
			smsSendInfo.setCustomerNo(tInfoCustomer.getCustomerNo());
			smsSendInfo.setMobilePhone(tInfoCustomer.getMobileNo());
			smsSendInfo.setEmail(tInfoCustomer.getEmailAddress());
		}		
		smsSendInfo.setAccountNo(tInfoAccount.getAccountNo());
		smsSendInfo.setAccountType(tInfoAccount.getType());
		smsSendInfo.setBalance(StringUtil.formatNumber((double) tInfoAccount.getBalance() / 100, 2));			
		smsSendInfo.setBussinessType(tLogAccountManagement.getTransType());
		//若外部交易类型不为空，外部交易类型入预留字段
		if(!Strings.isNullOrEmpty(outTxnType)&& outTxnType.length()==6){
			smsSendInfo.setBussinessType(outTxnType);
		}
		
		if( null != tFeeLogOnlinePayment){
			
			smsSendInfo.setFeeAmt(StringUtil.formatNumber((double) tFeeLogOnlinePayment.getTransAmount() / 100, 2));
		}
		smsSendInfo.setTxnPwd(txnPwd);
		smsSendInfo.setLoginPwd(loginPwd);
        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,null,smsSendInfo);
		/*rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.SMS_QUEKEY,smsSendInfo);*/
    }
    
    /**
     * 插入短信表
     * @param smsSendInfo
     */
    public void insertNotice(SmsSendInfo smsSendInfo){
    	
    	TLogAccountAlter alterLog =new TLogAccountAlter();
    	alterLog.setRecordNo(Long.valueOf(sequenceGenerator.getAccountAlterSeq()));
    	alterLog.setTxnChannel(smsSendInfo.getTxnChannel());
		alterLog.setTxnType(smsSendInfo.getTxnType());
		alterLog.setTxnDscpt(smsSendInfo.getTxnDscpt());
		alterLog.setAcceptOrgCode(smsSendInfo.getAcceptOrgCode());
		alterLog.setAcceptTransDate(smsSendInfo.getAcceptTransDate());
		alterLog.setAcceptTransTime(smsSendInfo.getAcceptTransTime());
		alterLog.setAcceptTransSeqNo(smsSendInfo.getAcceptTransSeqNo());
		alterLog.setTxnTime(smsSendInfo.getTxnTime());
		alterLog.setRsvdText2(smsSendInfo.getMerchantName());
		alterLog.setCustomerNo(smsSendInfo.getCustomerNo());
		alterLog.setMobilePhone(smsSendInfo.getMobilePhone());
		alterLog.setEmail(smsSendInfo.getEmail());
		alterLog.setAccountNo(smsSendInfo.getAccountNo());
		alterLog.setAccountType(smsSendInfo.getAccountType());
		alterLog.setRsvdText1(smsSendInfo.getBalance());			
		alterLog.setTxnAmount(smsSendInfo.getTxnAmount());
		alterLog.setRsvdText6(smsSendInfo.getBussinessType());
		alterLog.setFeeAmt(smsSendInfo.getFeeAmt());
		alterLog.setRemark(smsSendInfo.getTxnPwd());
		alterLog.setRemark2(smsSendInfo.getLoginPwd());		
		tLogAccountAlterMapper.insertSelective(alterLog);
    
    }
    
    
    /**
     * 交易类账户变动通知
     */
    public void offlineAcountAlterNotice(TInfoCustomer tInfoCustomer,TInfoAccount tInfoAccount,TLogOfflinePayment tLogOfflinePayment,
    		TInfoOrg supplyOrg, TLogOfflinePayment tFeeLogOfflinePayment, PaymentInfo paymentInfo) {
    	if(false == paymentInfo.isSmsSend()){  		
    		return;
    	}
    	SmsSendInfo smsSendInfo =new SmsSendInfo();
    	smsSendInfo.setTxnChannel(tLogOfflinePayment.getTxnChannel());
		smsSendInfo.setTxnType(tLogOfflinePayment.getTxnType());
		smsSendInfo.setTxnDscpt(tLogOfflinePayment.getTxnDscpt());
		smsSendInfo.setAcceptOrgCode(tLogOfflinePayment.getAcceptOrgCode());
		smsSendInfo.setAcceptTransDate(tLogOfflinePayment.getAcceptTransDate());
		smsSendInfo.setAcceptTransTime(tLogOfflinePayment.getAcceptTransTime());
		smsSendInfo.setAcceptTransSeqNo(tLogOfflinePayment.getAcceptTransSeqNo());
		smsSendInfo.setTxnTime(tLogOfflinePayment.getTxnTime());
		smsSendInfo.setMerchantName(supplyOrg.getOrgFname());
		if (tInfoCustomer != null) {
			smsSendInfo.setCustomerNo(tInfoCustomer.getCustomerNo());
			smsSendInfo.setMobilePhone(tInfoCustomer.getMobileNo());
			smsSendInfo.setEmail(tInfoCustomer.getEmailAddress());
		}
		
		if (tInfoAccount != null) {
			smsSendInfo.setAccountNo(tInfoAccount.getAccountNo());
			smsSendInfo.setAccountType(tInfoAccount.getType());
			smsSendInfo.setBalance(StringUtil.formatNumber((double) tInfoAccount.getBalance() / 100, 2));			
		}		
		smsSendInfo.setTxnAmount(StringUtil.formatNumber((double) tLogOfflinePayment.getTxnAmt() / 100, 2));
		//若外部交易类型不为空且外部交易类型和内部交易类型不同，外部交易类型入预留字段
		if(!Strings.isNullOrEmpty(tLogOfflinePayment.getBusinessType()) && tLogOfflinePayment.getBusinessType().length()==6){
			smsSendInfo.setBussinessType(tLogOfflinePayment.getBusinessType());
		}
		
		if( null != tFeeLogOfflinePayment){			
			smsSendInfo.setFeeAmt(StringUtil.formatNumber((double) tFeeLogOfflinePayment.getTxnAmt() / 100, 2));
		}
		smsSendInfo.setFrontOrgCode(paymentInfo.getFrontOrgCode());
        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,null,smsSendInfo);
		/*rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.SMS_QUEKEY,smsSendInfo);*/
    }
}
