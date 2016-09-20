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
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.common.util.BeanMapper;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.CardService;
import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.SecurityComponent;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.component.TxnResultGenComponent;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TbPosInfo;
import com.huateng.p3.account.risk.RiskCheckService;
import com.huateng.p3.component.Response;

/**
 * manager层控制事务.  enclosure
 * User: jijiandong
 * Date: 14-5-7
 */
@Component
public class AccountEnclosureManager {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;
    
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
    public TxnResultObject enclosureInAccount(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	//外部一个paymentInfo与accountInfo，内部拆分成转出与转入两个
    	PaymentInfo targetPaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, targetPaymentInfo);
    	//写死设一个消费大类
    	txnCheckService.txnIniNoCheck(paymentInfo,TxnInnerType.TXN_TYPE_111010.getTxnInnerType(), TxnType.TXN_CONSUME);
    	txnCheckService.txnIniNoCheck(targetPaymentInfo,TxnInnerType.TXN_TYPE_112010.getTxnInnerType(), TxnType.TXN_CHARGE);
 
    	//获取现金账户,accountKey为accountNo,keyType为product
    	TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());    	
    	//通过cardNo查询卡
    	TInfoCard card = cardService.findCardByCardNo(accountInfo.getTargetAccountKey());
    	//校验卡交易状态，交易类型为圈存转入
    	txnCheckService.checkCardManagerTxn(card, targetPaymentInfo.getInnerTxnType());		

    	TInfoAccount tTargetInfoAccount = null;
        //accountService.getOfflineAccountForUpdate(accountInfo.getTargetAccountKey(), accountInfo.getTargetKeyType());
    	
    	//判断是否是同一个业务账户
    	txnCheckService.checkCustomerNo(tInfoAccount.getCustomerNo(),tTargetInfoAccount.getCustomerNo());
    	
             
        //获取业务账户
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

        //特殊处理,某些地区关闭空中圈存功能
        txnCheckService.checkAreaOta(tInfoCustomer.getAreaCode());
        
        //转出账号交易密码校验        
       // TbPosInfo tbPosInfo = securityService.getTbPosInfo(paymentInfo.getMerchantCode(), paymentInfo.getTerminalNo(), paymentInfo.getAcceptOrgCode());                
        securityService.txnPwdCheck(tInfoCustomer, tInfoAccount, accountInfo);
        
        //账户交易合法性校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        txnCheckService.checkAccountPaymentTxn(tTargetInfoAccount, targetPaymentInfo);
        
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        
        //现金账户业务分控检查
        Response<String> accountRiskChkRes = riskCheckService.accountRiskCheck(paymentInfo, tInfoAccount);
        
        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }
        //脱机账户圈存转入检查
        accountRiskChkRes = riskCheckService.accountRiskCheck(targetPaymentInfo, tTargetInfoAccount);

        if (!accountRiskChkRes.isSuccess()) {
            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
        }
        
        //现金账户清算记录入库,现金账户减值
        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                tInfoCustomer, tInfoAccount , txnSeqNo , null, TrueFalseLabel.TRUE.getLablCode());
        //现金账户账户交易入库，
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo);
        //脱机账户清算记录入库，脱机账户增值，最后一个参数为true
        String targetTxnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        accountService.accountClearInDb(supplyOrg, acceptOrg, targetPaymentInfo, null,
        		tInfoCustomer, tTargetInfoAccount , targetTxnSeqNo , null, TrueFalseLabel.TRUE.getLablCode(), true);
        //脱机账户账户交易入库，脱机账户增值，最后一个参数为true
        accountService.accounTxnInDb(targetPaymentInfo, tTargetInfoAccount, acceptOrg, targetTxnSeqNo, true);
        
        // 是否存在主子账户关系的标志
     	accountService.checkSubaccountinfoByMainaccno(tInfoAccount,tTargetInfoAccount);
        //账户表更新
        accountService.accountDecreaseAlterInDb(tInfoAccount, paymentInfo, null);
        accountService.accountIncreaseAlterInDb(tTargetInfoAccount, targetPaymentInfo , null , null);
        //短信通知结算 
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null ,paymentInfo);
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        riskService.cutomerTxnDataSend(targetPaymentInfo, tTargetInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
    
        //结果对象
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, txnSeqNo , null);
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject rollbackEnclosureInAccount(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	PaymentInfo targetOldPaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	PaymentInfo targetPaymentInfo = new PaymentInfo();//复制一份，以免传入引用
    	BeanMapper.copy(oldPaymentInfo, targetOldPaymentInfo);
    	BeanMapper.copy(paymentInfo, targetPaymentInfo);
    	
    	//设置交易类型
    	txnCheckService.txnIniNoCheck(paymentInfo,TxnInnerType.TXN_TYPE_111012.getTxnInnerType(), TxnType.TXN_CONSUME);
    	txnCheckService.txnIniNoCheck(targetPaymentInfo,TxnInnerType.TXN_TYPE_112012.getTxnInnerType(), TxnType.TXN_CHARGE);
    	
    	//用户信息校验
    	//获取现金账户,accountKey为accountNo,keyType为product
    	TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
    	//查询脱机子账户
        TInfoAccount tTargetInfoAccount = null;
        //accountService.getOfflineAccountForUpdate(accountInfo.getTargetAccountKey(), accountInfo.getTargetKeyType());
    	//判断是否是同一个业务账户
    	txnCheckService.checkCustomerNo(tInfoAccount.getCustomerNo(),tTargetInfoAccount.getCustomerNo());
        //获取业务账户
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        
		//受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        
    	//现金账户冲正
        String rollbackTxnSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        //判断是否伪造冲正对象
        TxnResultObject txnResultObject = new TxnResultObject();
        //清算冲正
        TLogOnlinePayment rollbackLogOnlinePayment = accountService.rollbackOldLogOnlinePayment(supplyOrg, acceptOrg,
                paymentInfo, oldPaymentInfo, tInfoCustomer, tInfoAccount, rollbackTxnSeqNo, TrueFalseLabel.FALSE.getLablCode() ,txnResultObject);
        //交易日志撤销
        accountService.rollbackOldLogAccountPayment(rollbackLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        //进清结算，现金账户增值，设置为true
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, rollbackTxnSeqNo , rollbackLogOnlinePayment.getTransSerialNo(), rollbackLogOnlinePayment.getIsClearing(), true);
        //交易库，现金账户增值，设置为true
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackTxnSeqNo, true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo , rollbackLogOnlinePayment.getInteriorTransType(), rollbackLogOnlinePayment.getResvFld1(),txnResultObject.isRollbackFake());
        
        //脱机账户冲正
        String rollbackTargetTxnSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        //判断是否伪造冲正对象
        TxnResultObject targetTxnResultObject = new TxnResultObject();
        //清算冲正
        TLogOnlinePayment targetRollbackLogOnlinePayment = accountService.rollbackOldLogOnlinePayment(supplyOrg, acceptOrg,
                targetPaymentInfo, targetOldPaymentInfo, tInfoCustomer, tTargetInfoAccount, rollbackTargetTxnSeqNo, TrueFalseLabel.FALSE.getLablCode() ,targetTxnResultObject);
        //交易日志撤销
        accountService.rollbackOldLogAccountPayment(targetRollbackLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_OFFLINE);
        //进清结算,脱机账户减值
        accountService.accountClearInDb(supplyOrg, acceptOrg, targetPaymentInfo, targetOldPaymentInfo,
                tInfoCustomer, tTargetInfoAccount, rollbackTargetTxnSeqNo , targetRollbackLogOnlinePayment.getTransSerialNo(), targetRollbackLogOnlinePayment.getIsClearing());
        //交易库
        accountService.accounTxnInDb(targetPaymentInfo, tTargetInfoAccount, acceptOrg, rollbackTargetTxnSeqNo);
        //账户表更新，脱机账户减值
        accountService.accountDecreaseAlterInDb(tTargetInfoAccount, targetPaymentInfo, targetRollbackLogOnlinePayment.getInteriorTransType(), targetTxnResultObject.isRollbackFake());
        
        //不是伪造的交易处理以下操作
        if(!txnResultObject.isRollbackFake()){
	        //短信通知结算
	        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null , paymentInfo);
	        //推送账户交易数据到风控库
	        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, rollbackLogOnlinePayment.getInteriorTransType() ,rollbackLogOnlinePayment.getExtBusinessType());
	    }
        //不是伪造的交易处理以下操作
        if(!targetTxnResultObject.isRollbackFake()){
	        //推送账户交易数据到风控库
	        riskService.cutomerTxnDataSend(targetPaymentInfo, tTargetInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, targetRollbackLogOnlinePayment.getInteriorTransType() ,targetRollbackLogOnlinePayment.getExtBusinessType());
	    }
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, rollbackTxnSeqNo , rollbackLogOnlinePayment.getTransSerialNo(), txnResultObject.isRollbackFake());
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public TxnResultObject enclosureOutAccount(PaymentInfo paymentInfo, AccountInfo accountInfo) {
    	//外部一个paymentInfo与accountInfo，内部拆分成转出与转入两个
    	PaymentInfo targetPaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	BeanMapper.copy(paymentInfo, targetPaymentInfo);
    	//写死设一个消费大类,针对现金账户是圈提转入，针对脱机账户是圈提转出
    	txnCheckService.txnIniNoCheck(paymentInfo,TxnInnerType.TXN_TYPE_111020.getTxnInnerType(), TxnType.TXN_CHARGE);
    	txnCheckService.txnIniNoCheck(targetPaymentInfo,TxnInnerType.TXN_TYPE_112020.getTxnInnerType(), TxnType.TXN_CONSUME);
    	//获取现金账户,accountKey为accountNo,keyType为product
    	TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());    	
    	//通过cardNo查询卡
    	TInfoCard card = cardService.findCardByCardNo(accountInfo.getTargetAccountKey());
    	//校验卡交易状态，交易类型为圈提转出
    	txnCheckService.checkCardManagerTxn(card, targetPaymentInfo.getInnerTxnType());
        TInfoAccount tTargetInfoAccount = null;
        //accountService.getOfflineAccountForUpdate(accountInfo.getTargetAccountKey(), accountInfo.getTargetKeyType());
    	//判断是否是同一个业务账户
    	txnCheckService.checkCustomerNo(tInfoAccount.getCustomerNo(),tTargetInfoAccount.getCustomerNo());
        //获取业务账户
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());

        //特殊处理,某些地区关闭OTA功能
        txnCheckService.checkAreaOta(tInfoCustomer.getAreaCode());
               
        //账户交易合法性校验，包含账户、渠道和交易类型的校验
        txnCheckService.checkAccountPaymentTxn(tInfoAccount, paymentInfo);
        txnCheckService.checkAccountPaymentTxn(tTargetInfoAccount, targetPaymentInfo);
        
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype());
        
//        //现金账户业务分控检查
//        Response<String> accountRiskChkRes = riskCheckService.accountRiskCheck(paymentInfo, tInfoAccount);
//        
//        if (!accountRiskChkRes.isSuccess()) {
//            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
//        }
//        //脱机账户风控检查
//        accountRiskChkRes = riskCheckService.accountRiskCheck(targetPaymentInfo, tTargetInfoAccount);
//
//        if (!accountRiskChkRes.isSuccess()) {
//            throw new BizException(accountRiskChkRes.getErrorCode(), accountRiskChkRes.getErrorMsg());
//        }
        
        //脱机账户清算记录入库，圈提转出交易
        String targetTxnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        accountService.accountClearInDb(supplyOrg, acceptOrg, targetPaymentInfo, null,
        		tInfoCustomer, tTargetInfoAccount , targetTxnSeqNo , null, TrueFalseLabel.TRUE.getLablCode());
        //脱机账户账户交易入库
        accountService.accounTxnInDb(targetPaymentInfo, tTargetInfoAccount, acceptOrg, targetTxnSeqNo);
        //现金账户清算记录入库,增值设置为true
        String txnSeqNo = sequenceGenerator.generatorTxnSeqNo();
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, null,
                tInfoCustomer, tInfoAccount , txnSeqNo , null, TrueFalseLabel.TRUE.getLablCode(), true);
        //现金账户账户交易入库,增值设置为true
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, txnSeqNo, true);
        
        //是否存在主子账户关系的标志
     	accountService.checkSubaccountinfoByMainaccno(tInfoAccount,tTargetInfoAccount);
        //账户表更新
        accountService.accountDecreaseAlterInDb(tTargetInfoAccount, targetPaymentInfo, null);//脱机账户减值
        accountService.accountIncreaseAlterInDb(tInfoAccount, paymentInfo , null , null);//现金账户增值
        //短信通知结算 
        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null ,paymentInfo);
        //推送账户交易数据到风控库
        riskService.cutomerTxnDataSend(targetPaymentInfo, tTargetInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null);
        
        //结果对象
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, txnSeqNo , null);
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TxnResultObject rollbackEnclosureOutAccount(PaymentInfo paymentInfo, PaymentInfo oldPaymentInfo, AccountInfo accountInfo) {
    	PaymentInfo targetOldPaymentInfo = new PaymentInfo(); //复制一份，以免传入引用
    	PaymentInfo targetPaymentInfo = new PaymentInfo();//复制一份，以免传入引用
    	BeanMapper.copy(oldPaymentInfo, targetOldPaymentInfo);
    	BeanMapper.copy(paymentInfo, targetPaymentInfo);
    	
    	//设置交易类型
    	txnCheckService.txnIniNoCheck(paymentInfo,TxnInnerType.TXN_TYPE_111022.getTxnInnerType(), TxnType.TXN_CHARGE);//现金账户为圈提转入冲正
    	txnCheckService.txnIniNoCheck(targetPaymentInfo,TxnInnerType.TXN_TYPE_112022.getTxnInnerType(), TxnType.TXN_CONSUME);//脱机账户为圈提转出冲正
    	
    	//用户信息校验
    	//获取现金账户,accountKey为accountNo,keyType为product
    	TInfoAccount tInfoAccount = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());    	
    	//查询脱机子账户
        TInfoAccount tTargetInfoAccount = null;
        //accountService.getOfflineAccountForUpdate(accountInfo.getTargetAccountKey(), accountInfo.getTargetKeyType());
    	//判断是否是同一个业务账户
    	txnCheckService.checkCustomerNo(tInfoAccount.getCustomerNo(),tTargetInfoAccount.getCustomerNo());
        //获取业务账户
        TInfoCustomer tInfoCustomer = customerService.findValidCustomerByCustomerNo(tInfoAccount.getCustomerNo());
        
		//受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(paymentInfo.getAcceptOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype(),true);
        //商户机构检查
        TInfoOrg supplyOrg = orgService.getValidOrg(paymentInfo.getSupplyOrgCode(),
                paymentInfo.getInnerTxnType(), OrgType.ORG_TYPE_MERCHANT.getOrgtype(),true);
        
        //脱机账户冲正
        String rollbackTargetTxnSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        //判断是否伪造冲正对象
        TxnResultObject targetTxnResultObject = new TxnResultObject();
        //清算冲正
        TLogOnlinePayment targetRollbackLogOnlinePayment = accountService.rollbackOldLogOnlinePayment(supplyOrg, acceptOrg,
                targetPaymentInfo, targetOldPaymentInfo, tInfoCustomer, tTargetInfoAccount, rollbackTargetTxnSeqNo, TrueFalseLabel.FALSE.getLablCode() ,targetTxnResultObject);
        //交易日志撤销
        accountService.rollbackOldLogAccountPayment(targetRollbackLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_OFFLINE);
        //进清结算，脱机账户增值设置为true
        accountService.accountClearInDb(supplyOrg, acceptOrg, targetPaymentInfo, targetOldPaymentInfo,
                tInfoCustomer, tTargetInfoAccount, rollbackTargetTxnSeqNo , targetRollbackLogOnlinePayment.getTransSerialNo(), targetRollbackLogOnlinePayment.getIsClearing(), true);
        //交易库，脱机账户增值设置为true
        accountService.accounTxnInDb(targetPaymentInfo, tTargetInfoAccount, acceptOrg, rollbackTargetTxnSeqNo, true);
        //账户表更新
        accountService.accountIncreaseAlterInDb(tTargetInfoAccount, targetPaymentInfo , targetRollbackLogOnlinePayment.getInteriorTransType(), targetRollbackLogOnlinePayment.getResvFld1(),targetTxnResultObject.isRollbackFake());
        
    	//现金账户冲正
        String rollbackTxnSeqNo = sequenceGenerator.generatorTxnRollbackSeqNo();
        //判断是否伪造冲正对象
        TxnResultObject txnResultObject = new TxnResultObject();
        //清算冲正
        TLogOnlinePayment rollbackLogOnlinePayment = accountService.rollbackOldLogOnlinePayment(supplyOrg, acceptOrg,
                paymentInfo, oldPaymentInfo, tInfoCustomer, tInfoAccount, rollbackTxnSeqNo, TrueFalseLabel.FALSE.getLablCode() ,txnResultObject);
        //交易日志撤销
        accountService.rollbackOldLogAccountPayment(rollbackLogOnlinePayment.getTransSerialNo(), TxnForm.TXN_LABL_ONLINE);
        //进清结算，现金账户减值，不设置
        TLogOnlinePayment tLogOnlinePayment = accountService.accountClearInDb(supplyOrg, acceptOrg, paymentInfo, oldPaymentInfo,
                tInfoCustomer, tInfoAccount, rollbackTxnSeqNo , rollbackLogOnlinePayment.getTransSerialNo(), rollbackLogOnlinePayment.getIsClearing());
        //交易库
        accountService.accounTxnInDb(paymentInfo, tInfoAccount, acceptOrg, rollbackTxnSeqNo);
        //账户表更新，现金账户减值
        accountService.accountDecreaseAlterInDb(tInfoAccount, targetPaymentInfo, rollbackLogOnlinePayment.getInteriorTransType(), txnResultObject.isRollbackFake());
        
      //不是伪造的交易处理以下操作
        if(!targetTxnResultObject.isRollbackFake()){
	        //推送账户交易数据到风控库
	        riskService.cutomerTxnDataSend(targetPaymentInfo, tTargetInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, targetRollbackLogOnlinePayment.getInteriorTransType() ,targetRollbackLogOnlinePayment.getExtBusinessType());
	    }
        //不是伪造的交易处理以下操作
        if(!txnResultObject.isRollbackFake()){
	        //短信通知结算
	        smsComponent.acountAlterNotice(tInfoCustomer, tInfoAccount,tLogOnlinePayment,supplyOrg ,null , paymentInfo);
	        //推送账户交易数据到风控库
	        riskService.cutomerTxnDataSend(paymentInfo, tInfoAccount.getAccountNo(), AccountType.FUND, tInfoCustomer.getCustomerGrade(), null, rollbackLogOnlinePayment.getInteriorTransType() ,rollbackLogOnlinePayment.getExtBusinessType());
	    }
        
        return txnResultGenComponent.genTxnResultObject(paymentInfo,null , tInfoAccount, tInfoCustomer, rollbackTxnSeqNo , rollbackLogOnlinePayment.getTransSerialNo(), txnResultObject.isRollbackFake());
    }
}
