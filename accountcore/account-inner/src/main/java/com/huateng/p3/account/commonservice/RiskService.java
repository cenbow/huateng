package com.huateng.p3.account.commonservice;

import java.sql.Date;

import com.huateng.p3.account.common.tools.activemq.AppCode;
import com.huateng.p3.account.component.ActiveMqTemplateComponent;
import lombok.Getter;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskAllRuleQry;
import com.huateng.p3.account.common.bizparammodel.RiskCustomerTxnDataMergeInfo;
import com.huateng.p3.account.common.bizparammodel.RiskOrgTxnDataMergeInfo;
import com.huateng.p3.account.common.bizparammodel.RiskTxnTypeRuleQry;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.ArchivedFlag;
import com.huateng.p3.account.common.enummodel.CheckFlag;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.RiskRuleType;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnChannel;
import com.huateng.p3.account.common.enummodel.UseFlag;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.component.RabbitTemplateComponent;
import com.huateng.p3.account.persistence.RiskLogacnttxnPaymentDetailMapper;
import com.huateng.p3.account.persistence.RiskLogacnttxnPaymentMapper;
import com.huateng.p3.account.persistence.TParaAccountBalMapper;
import com.huateng.p3.account.persistence.TRiskBlackManageMapper;
import com.huateng.p3.account.persistence.TRiskCustomerCommonRuleMapper;
import com.huateng.p3.account.persistence.models.RiskCustomerTxntypeRule;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPayment;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPaymentDetail;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;
import com.huateng.p3.account.persistence.models.TRiskBlackManage;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;

/**
 * User: JamesTang
 * Date: 13-12-13
 * Time: 下午2:37
 */

@Service
public class RiskService {
    @Autowired
    private RabbitTemplateComponent rabbitTemplateComponent;

    @Autowired
    private ActiveMqTemplateComponent activeMqTemplateComponent;

    @Autowired
    private TRiskCustomerCommonRuleMapper tRiskCustomerCommonRuleMapper;

    @Autowired
    private RiskLogacnttxnPaymentMapper riskLogacnttxnPaymentMapper;

    @Autowired
    private RiskLogacnttxnPaymentDetailMapper riskLogacnttxnPaymentDetailMapper;

    @Autowired
    @Getter
    private TParaAccountBalMapper tParaAccountBalMapper;
    
    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private TRiskBlackManageMapper tRiskBlackManageMapper;

    @Autowired
    private TxnCheckService txnCheckService;


    private static String selfdefine = "self-define";
    /**
     * 
     * @param paymentInfo
     * @param accountNo
     * @param accountType
     * @param customerGrade
     * @param oldTxnType  原交易的内部交易类型 与外部交易类型
     */
    public void cutomerTxnDataSend(PaymentInfo paymentInfo, String accountNo, AccountType accountType, String customerGrade,
    		TLogPreauthApply oldPreauthApply, String... oldTxnType) {
        RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo = new RiskCustomerTxnDataMergeInfo();
        riskCustomerTxnDataMergeInfo.setInnerTxnType(paymentInfo.getInnerTxnType());
        riskCustomerTxnDataMergeInfo.setBussinessType(paymentInfo.getBussinessType());
        riskCustomerTxnDataMergeInfo.setAccountNo(accountNo);
        riskCustomerTxnDataMergeInfo.setAccountType(accountType);
        riskCustomerTxnDataMergeInfo.setCustomerGrade(customerGrade);
        if(oldPreauthApply == null){
            riskCustomerTxnDataMergeInfo.setTxnAmount(paymentInfo.getAmount());
        }else{
        	riskCustomerTxnDataMergeInfo.setTxnAmount(oldPreauthApply.getAuthAmt() - paymentInfo.getAmount());
        }
        riskCustomerTxnDataMergeInfo.setTxnChannel(paymentInfo.getChannel());
        riskCustomerTxnDataMergeInfo.setTxnType(paymentInfo.getTxnType());
        riskCustomerTxnDataMergeInfo.setTxnDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        riskCustomerTxnDataMergeInfo.setTxnMonth(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMM"));
        // 输入两个参数表示风控值减少
        if (oldTxnType.length == 2){
        	riskCustomerTxnDataMergeInfo.setOldInnerTxnType(oldTxnType[0]);
        	riskCustomerTxnDataMergeInfo.setOldBussinessType(oldTxnType[1]);
        }
        // 输入四个参数表示风控值增加，用输入参数替换交易类型
        if (oldTxnType.length == 4){
        	riskCustomerTxnDataMergeInfo.setInnerTxnType(oldTxnType[2]);
        	riskCustomerTxnDataMergeInfo.setBussinessType(oldTxnType[3]);
        }
        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,"reqRisk",riskCustomerTxnDataMergeInfo);
        //rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.RISK_BIZ, riskCustomerTxnDataMergeInfo);
    }
    @Deprecated
    public void OrgTxnDataSend(PaymentInfo paymentInfo, OrgType orgType) {
        RiskOrgTxnDataMergeInfo riskOrgTxnDataMergeInfo = new RiskOrgTxnDataMergeInfo();
        riskOrgTxnDataMergeInfo.setTxnChannel(paymentInfo.getChannel());
        riskOrgTxnDataMergeInfo.setTxnAmount(paymentInfo.getAmount());
        riskOrgTxnDataMergeInfo.setInnerTxnType(paymentInfo.getInnerTxnType());
        riskOrgTxnDataMergeInfo.setOrgCode(paymentInfo.getMerchantCode());
        riskOrgTxnDataMergeInfo.setOrgType(orgType);
        riskOrgTxnDataMergeInfo.setTerminalNo(paymentInfo.getTerminalNo());

        activeMqTemplateComponent.aSyncSendMsg(AppCode.INST_ID,"reqRisk",riskOrgTxnDataMergeInfo);
        //rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.RISK_BIZ, riskOrgTxnDataMergeInfo);

    }

    public void RiskCustomerCommonRuleInDb(TRiskCustomerCommonRule rule, TInfoAccount account, ManagerLog managerLog) {
        rule.setRuleType(RiskRuleType.RISK_RULE_TYPE_SELF.getTypeCode());// 按账户号码控制
        rule.setAccountNo(account.getAccountNo());
        rule.setAccountType(account.getType());
        rule.setUserGrade(account.getGrade());
        rule.setAcceptChannel(managerLog.getTxnChannel());
        rule.setUseFlag(UseFlag.USE_FLAG_OPEN.getFlag());// 启用
        rule.setEffectiveDate(new Date(System.currentTimeMillis()));// 生效日期
        // 规则默认有效期100年
        rule.setValidDate(new DateTime(rule.getEffectiveDate()).plusYears(100).toDate());// 失效日期
        rule.setCreateUid(managerLog.getInputUid());// 录入人
        rule.setCreateTime(managerLog.getInputTime());// 录入时间
        rule.setCheckFlag(CheckFlag.USE_FLAG_CHECKED.getFlag());// 已审核
        rule.setCheckTime(DateTime.now().toDate());//审核时间
        rule.setArchivedFlag(ArchivedFlag.ARCHIV_FLAG_UNARCHIV.getFlag());//归档标志	1-未归档
        rule.setResvFld1(selfdefine);// 预留字段，存放self-define，表示此风控规则是用户自定义的
        if (tRiskCustomerCommonRuleMapper.updateCustomerSelfDefineAccountRisk(rule) == 0) {
            tRiskCustomerCommonRuleMapper.insertSelective(rule);
        }
    }

    /**
     * 查渠道的总风控规则
     */
    public TRiskCustomerCommonRule findAccountGeneralRisk(String txnChannel, String grade, String accountType) {
    	RiskAllRuleQry riskAllRuleQry = new RiskAllRuleQry();
    	riskAllRuleQry.setAccountType(accountType);
    	riskAllRuleQry.setTxnChannel(txnChannel);
    	riskAllRuleQry.setUserGrade(grade);
    	return cacheComponent.getRiskCustomerAllRule(riskAllRuleQry);
    }


    /**
     * 查询用户设置的风控规则
     */
    public TRiskCustomerCommonRule findCustomerSelfDefineAccountRisk(String accountNo,
                                                                     String accountType, String txnChannel) {
        return tRiskCustomerCommonRuleMapper.findCustomerSelfDefineAccountRisk(txnChannel, accountNo,
                accountType, selfdefine, DateTime.now().toDate());
    }
    
    /**
     * 查询特殊交易类型配置
     * @param innerTxnType
     * @param txnChannel
     * @param accountType
     * @param userGrade
     * @return
     */
    public RiskCustomerTxntypeRule  findRiskCustomerTxntypeRule(String innerTxnType,String txnChannel,AccountType accountType,String userGrade){
    	RiskTxnTypeRuleQry riskTxnTypeRuleQry = new RiskTxnTypeRuleQry();
        riskTxnTypeRuleQry.setTxnType(innerTxnType);
        riskTxnTypeRuleQry.setAccountType(accountType.getValue());
        riskTxnTypeRuleQry.setTxnChannel(txnChannel);
        riskTxnTypeRuleQry.setUserGrade(userGrade);
        return cacheComponent.getRiskCustomerTxntypeRule(riskTxnTypeRuleQry);
  	
    }


    /**
     * 获取账户累积交易信息
     * * @param accountNo
     *
     * @param paymentInfo
     * @return
     */
    public RiskLogacnttxnPayment getriskLogacnttxnPayment(String accountNo, PaymentInfo paymentInfo) {
        RiskLogacnttxnPayment riskLogacnttxnPayment = riskLogacnttxnPaymentMapper.selectByTxnKey(accountNo,
                String.valueOf(paymentInfo.getTxnType().getTxnCode()));
        if (null == riskLogacnttxnPayment) {
            riskLogacnttxnPayment = new RiskLogacnttxnPayment();
            riskLogacnttxnPayment.setMonthSumAmt(0l);
            riskLogacnttxnPayment.setMonthSumTimes(0l);
            riskLogacnttxnPayment.setDaySumTimes(0l);
            riskLogacnttxnPayment.setDaySumAmt(0l);
            riskLogacnttxnPayment.setlastTransMonth(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMM"));
            riskLogacnttxnPayment.setlastTransDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
        }
        if (!riskLogacnttxnPayment.getlastTransDate().equals(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"))) {
            riskLogacnttxnPayment.setDaySumTimes(0l);
            riskLogacnttxnPayment.setDaySumAmt(0l);
        }
        if (!riskLogacnttxnPayment.getlastTransMonth().equals(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMM"))) {
            riskLogacnttxnPayment.setMonthSumTimes(0l);
            riskLogacnttxnPayment.setMonthSumAmt(0l);
        }
        return riskLogacnttxnPayment;
    }

    /**
     * 获取交易累积详细信息 * @param accountNo
     *
     * @param paymentInfo
     * @return
     */
    public RiskLogacnttxnPaymentDetail getRiskLogacnttxnPaymentDetail(String accountNo, PaymentInfo paymentInfo ,RiskCustomerTxntypeRule riskCustomerTxntypeRule) {
    	//需要合并渠道
        if(TrueOrFalse.TRUE.getLablCode().equals(riskCustomerTxntypeRule.getMergerFlag())){
        	paymentInfo.setChannel(TxnChannel.TXN_CHANNEL_All.getTxnCode());
        }
        RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail =
                riskLogacnttxnPaymentDetailMapper.selectByTxnKey(accountNo, paymentInfo.getChannel(), paymentInfo.getInnerTxnType());

        if (null == riskLogacnttxnPaymentDetail) {
            riskLogacnttxnPaymentDetail = new RiskLogacnttxnPaymentDetail();
            riskLogacnttxnPaymentDetail.setMonthSumAmt(0l);
            riskLogacnttxnPaymentDetail.setMonthSumTimes(0l);
            riskLogacnttxnPaymentDetail.setDaySumTimes(0l);
            riskLogacnttxnPaymentDetail.setDaySumAmt(0l);
            riskLogacnttxnPaymentDetail.setLastTransMonth(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMM"));
            riskLogacnttxnPaymentDetail.setLastTransDate(new DateTime(paymentInfo.getTxnDate()).toString("yyyyMMdd"));
            riskLogacnttxnPaymentDetail.setTransType(paymentInfo.getInnerTxnType());
        }

        if (!riskLogacnttxnPaymentDetail.getLastTransDate().equals(DateTime.now().toString("yyyyMMdd"))) {
            riskLogacnttxnPaymentDetail.setDaySumAmt(0l);
            riskLogacnttxnPaymentDetail.setDaySumTimes(0l);
        }

        if (!riskLogacnttxnPaymentDetail.getLastTransMonth().equals(DateTime.now().toString("yyyyMM"))) {
            riskLogacnttxnPaymentDetail.setMonthSumAmt(0l);
            riskLogacnttxnPaymentDetail.setMonthSumTimes(0l);

        }
        return riskLogacnttxnPaymentDetail;
    }
    
    
    public TRiskCustomerCommonRule getCheckRule(PaymentInfo paymentInfo, TInfoAccount account){
    	TRiskCustomerCommonRule checkRule = null;
    	//交易类型的总风控规则
        TRiskCustomerCommonRule tRiskCustomerCommonRule = this.findAccountGeneralRisk(paymentInfo.getChannel(),
        		account.getGrade(), account.getType());
        //自定义风控
        TRiskCustomerCommonRule selfdefCustomerCommonRule = this.findCustomerSelfDefineAccountRisk(account.getAccountNo(),
        		 account.getType(), paymentInfo.getChannel());
        //默认风控
        TRiskCustomerCommonRule defaultCustomerCommRule = this.getDefaultRule();
    	
    	if ((null != tRiskCustomerCommonRule) || (null != selfdefCustomerCommonRule)) {
            checkRule = this.mergeRiskCustomerCommonRule(tRiskCustomerCommonRule, selfdefCustomerCommonRule);
        }
        if (null == checkRule) {
            checkRule = defaultCustomerCommRule;
        }
    	return checkRule;
    }
    
    
    /**
     * 合并风控
     * @param generalRule
     * @param personalRule
     * @return
     */
    public TRiskCustomerCommonRule mergeRiskCustomerCommonRule(TRiskCustomerCommonRule generalRule, TRiskCustomerCommonRule personalRule) {
        if ((null == generalRule) && (null == personalRule)) {
            return null;
        }
        if (null == generalRule) {
            return personalRule;
        }
        if ((null == personalRule)) {
            return generalRule;
        }
        TRiskCustomerCommonRule tRiskCustomerCommonRule = new TRiskCustomerCommonRule();
        tRiskCustomerCommonRule.setConsumeTransMaxAmt(generalRule.getConsumeTransMaxAmt() > personalRule.getConsumeTransMaxAmt() ? personalRule.getConsumeTransMaxAmt() : generalRule.getConsumeTransMaxAmt());
        tRiskCustomerCommonRule.setConsumeTransMaxAmtSum(generalRule.getConsumeTransMaxAmtSum() > personalRule.getConsumeTransMaxAmtSum() ? personalRule.getConsumeTransMaxAmtSum() : generalRule.getConsumeTransMaxAmtSum());
        tRiskCustomerCommonRule.setConsumeTransMaxNum(generalRule.getConsumeTransMaxNum() > personalRule.getConsumeTransMaxNum() ? personalRule.getConsumeTransMaxNum() : generalRule.getConsumeTransMaxNum());
        tRiskCustomerCommonRule.setConsumeTransMinAmt(generalRule.getConsumeTransMinAmt() < personalRule.getConsumeTransMinAmt() ? personalRule.getConsumeTransMinAmt() : generalRule.getConsumeTransMinAmt());


        tRiskCustomerCommonRule.setChargeTransMaxAmt(generalRule.getChargeTransMaxAmt() > personalRule.getChargeTransMaxAmt() ? personalRule.getChargeTransMaxAmt() : generalRule.getChargeTransMaxAmt());
        tRiskCustomerCommonRule.setChargeTransMaxNum(generalRule.getChargeTransMaxNum() > personalRule.getChargeTransMaxNum() ? personalRule.getChargeTransMaxNum() : generalRule.getChargeTransMaxNum());
        tRiskCustomerCommonRule.setChargeTransMaxAmtSum(generalRule.getChargeTransMaxAmtSum() > personalRule.getChargeTransMaxAmtSum() ? personalRule.getChargeTransMaxAmtSum() : generalRule.getChargeTransMaxAmtSum());
        tRiskCustomerCommonRule.setChargeTransMinAmt(generalRule.getChargeTransMinAmt() < personalRule.getChargeTransMinAmt() ? personalRule.getChargeTransMinAmt() : generalRule.getChargeTransMinAmt());

        tRiskCustomerCommonRule.setTransferInMinAmt(generalRule.getTransferInMinAmt() < personalRule.getTransferInMinAmt() ? personalRule.getTransferInMinAmt() : generalRule.getTransferInMinAmt());
        tRiskCustomerCommonRule.setTransferInMaxNum(generalRule.getTransferInMaxNum() > personalRule.getTransferInMaxNum() ? personalRule.getTransferInMaxNum() : generalRule.getTransferInMaxNum());
        tRiskCustomerCommonRule.setTransferInMaxAmtSum(generalRule.getTransferInMaxAmtSum() > personalRule.getTransferInMaxAmtSum() ? personalRule.getTransferInMaxAmtSum() : generalRule.getTransferInMaxAmtSum());
        tRiskCustomerCommonRule.setTransferInMaxAmt(generalRule.getTransferInMaxAmt() > personalRule.getTransferInMaxAmt() ? personalRule.getTransferInMaxAmt() : generalRule.getTransferInMaxAmt());

        tRiskCustomerCommonRule.setTransferTransMaxAmt(generalRule.getTransferTransMaxAmt() > personalRule.getTransferTransMaxAmt() ? personalRule.getTransferTransMaxAmt() : generalRule.getTransferTransMaxAmt());
        tRiskCustomerCommonRule.setTransferTransMaxAmtSum(generalRule.getTransferTransMaxAmtSum() > personalRule.getTransferTransMaxAmtSum() ? personalRule.getTransferTransMaxAmtSum() : generalRule.getTransferTransMaxAmtSum());
        tRiskCustomerCommonRule.setTransferTransMaxNum(generalRule.getTransferTransMaxNum() > personalRule.getTransferTransMaxNum() ? personalRule.getTransferTransMaxNum() : generalRule.getTransferTransMaxNum());
        tRiskCustomerCommonRule.setTransferTransMinAmt(generalRule.getTransferTransMinAmt() < personalRule.getTransferTransMinAmt() ? personalRule.getTransferTransMinAmt() : generalRule.getTransferTransMinAmt());

        tRiskCustomerCommonRule.setWithdrawTransMinAmt(generalRule.getWithdrawTransMinAmt() < personalRule.getWithdrawTransMinAmt() ? personalRule.getWithdrawTransMinAmt() : generalRule.getWithdrawTransMinAmt());
        tRiskCustomerCommonRule.setWithdrawTransMaxNum(generalRule.getWithdrawTransMaxNum() > personalRule.getWithdrawTransMaxNum() ? personalRule.getWithdrawTransMaxNum() : generalRule.getWithdrawTransMaxNum());
        tRiskCustomerCommonRule.setWithdrawTransMaxAmtSum(generalRule.getWithdrawTransMaxAmtSum() > personalRule.getWithdrawTransMaxAmtSum() ? personalRule.getWithdrawTransMaxAmtSum() : generalRule.getWithdrawTransMaxAmtSum());
        tRiskCustomerCommonRule.setWithdrawTransMaxAmt(generalRule.getWithdrawTransMaxAmt() > personalRule.getWithdrawTransMaxAmt() ? personalRule.getWithdrawTransMaxAmt() : generalRule.getWithdrawTransMaxAmt());

        tRiskCustomerCommonRule.setMonthMaxChgAmt(generalRule.getMonthMaxChgAmt() > personalRule.getMonthMaxChgAmt() ? personalRule.getMonthMaxChgAmt() : generalRule.getMonthMaxChgAmt());
        tRiskCustomerCommonRule.setMonthMaxCashAmt(generalRule.getMonthMaxCashAmt() > personalRule.getMonthMaxCashAmt() ? personalRule.getMonthMaxCashAmt() : generalRule.getMonthMaxCashAmt());
        tRiskCustomerCommonRule.setMonthMaxInAmt(generalRule.getMonthMaxInAmt() > personalRule.getMonthMaxInAmt() ? personalRule.getMonthMaxInAmt() : generalRule.getMonthMaxInAmt());
        tRiskCustomerCommonRule.setMonthMaxOutAmt(generalRule.getMonthMaxOutAmt() > personalRule.getMonthMaxOutAmt() ? personalRule.getMonthMaxOutAmt() : generalRule.getMonthMaxOutAmt());
        tRiskCustomerCommonRule.setMonthMaxConsAmt(generalRule.getMonthMaxConsAmt() > personalRule.getMonthMaxConsAmt() ? personalRule.getMonthMaxConsAmt() : generalRule.getMonthMaxConsAmt());

        tRiskCustomerCommonRule.setMonthMaxCashCnt(generalRule.getMonthMaxCashCnt() > personalRule.getMonthMaxCashCnt() ? personalRule.getMonthMaxCashCnt() : generalRule.getMonthMaxCashCnt());
        tRiskCustomerCommonRule.setMonthMaxChgCnt(generalRule.getMonthMaxChgCnt() > personalRule.getMonthMaxChgCnt() ? personalRule.getMonthMaxChgCnt() : generalRule.getMonthMaxChgCnt());
        tRiskCustomerCommonRule.setMonthMaxInCnt(generalRule.getMonthMaxInCnt() > personalRule.getMonthMaxInCnt() ? personalRule.getMonthMaxInCnt() : generalRule.getMonthMaxInCnt());
        tRiskCustomerCommonRule.setMonthMaxOutCnt(generalRule.getMonthMaxOutCnt() > personalRule.getMonthMaxOutCnt() ? personalRule.getMonthMaxOutCnt() : generalRule.getMonthMaxOutCnt());
        tRiskCustomerCommonRule.setMonthMaxConsCnt(generalRule.getMonthMaxConsCnt() > personalRule.getMonthMaxConsCnt() ? personalRule.getMonthMaxConsCnt() : generalRule.getMonthMaxConsCnt());

        return tRiskCustomerCommonRule;
    }

    /**
     * 默认风控
     * @return
     */
    public TRiskCustomerCommonRule getDefaultRule() {
        TRiskCustomerCommonRule tRiskCustomerCommonRule = new TRiskCustomerCommonRule();
        tRiskCustomerCommonRule.setConsumeTransMaxNum(10L);//日消费交易最大次数
        tRiskCustomerCommonRule.setConsumeTransMaxAmt(100000L); //日消费交易单笔限额
        tRiskCustomerCommonRule.setConsumeTransMaxAmtSum(200000L);   // 日消费交易累计限额
        tRiskCustomerCommonRule.setConsumeTransMinAmt(1l);     //  日消费交易单笔最小额

        tRiskCustomerCommonRule.setChargeTransMaxAmt(10000L);    //   日充值交易单笔限额
        tRiskCustomerCommonRule.setChargeTransMaxAmtSum(20000L);    // 日充值交易累计限额
        tRiskCustomerCommonRule.setChargeTransMaxNum(5l);// 日充值交易最大次数
        tRiskCustomerCommonRule.setChargeTransMinAmt(1L);   //日充值交易单笔最小额


        tRiskCustomerCommonRule.setWithdrawTransMaxAmt(300000L);  //日提现交易单笔限额
        tRiskCustomerCommonRule.setWithdrawTransMaxAmtSum(300000L);   // 日提现交易累计限额
        tRiskCustomerCommonRule.setWithdrawTransMaxNum(3L); //日提现交易最大次数
        tRiskCustomerCommonRule.setWithdrawTransMinAmt(1L);  //日提现交易单笔最小额

        tRiskCustomerCommonRule.setTransferTransMaxAmt(20000L);     //日转账交易单笔限额
        tRiskCustomerCommonRule.setTransferTransMaxAmtSum(20000L);  //   日转账交易累计限额
        tRiskCustomerCommonRule.setTransferTransMaxNum(8L);  //  日转账交易最大次数
        tRiskCustomerCommonRule.setTransferTransMinAmt(1L);  // 日转账交易单笔最小额

        tRiskCustomerCommonRule.setMonthMaxConsAmt(2000000l); //月累计消费限额
        tRiskCustomerCommonRule.setMonthMaxOutAmt(2000000l);//;   月累计转出限额
        tRiskCustomerCommonRule.setMonthMaxInAmt(2000000l); //  月累计转入限额
        tRiskCustomerCommonRule.setMonthMaxCashAmt(300000l);  //月累计提现限额
        tRiskCustomerCommonRule.setMonthMaxChgAmt(2000000l);   //  月累计充值限额

        tRiskCustomerCommonRule.setMonthMaxConsCnt(999l); //月累计消费限额
        tRiskCustomerCommonRule.setMonthMaxOutCnt(999l);//;   月累计转出限额
        tRiskCustomerCommonRule.setMonthMaxInCnt(999l); //  月累计转入限额
        tRiskCustomerCommonRule.setMonthMaxCashCnt(999l);  //月累计提现限额
        tRiskCustomerCommonRule.setMonthMaxChgCnt(999l);   //  月累计充值限额

        tRiskCustomerCommonRule.setTransferInMaxAmt(1000000l);       // 单笔转入最大金额
        tRiskCustomerCommonRule.setTransferInMaxAmtSum(1000000l);   //日累计转入最高金额
        tRiskCustomerCommonRule.setTransferInMaxNum(999l);     //日转入最大次数
        tRiskCustomerCommonRule.setTransferInMinAmt(1l);  //单笔转入最小金额
        return tRiskCustomerCommonRule;
    }

    public void queryRiskBlack(String blackCode, String blackType) {
    	if(!Strings.isNullOrEmpty(blackCode)){
	        TRiskBlackManage tRiskBlackManage = tRiskBlackManageMapper.queryRiskBlack(blackCode, blackType);
	        txnCheckService.checkRiskBlackManage(tRiskBlackManage, blackType);
    	}
    }

}


