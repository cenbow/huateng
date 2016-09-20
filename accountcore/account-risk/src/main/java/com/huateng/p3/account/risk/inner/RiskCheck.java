package com.huateng.p3.account.risk.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.persistence.models.RiskCustomerTxntypeRule;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPayment;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPaymentDetail;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TParaAccountBal;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;


/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-11
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RiskCheck {


    @Autowired
    private AccountService accountService;

    @Autowired
    private RiskService riskService;
    
    @Autowired
    private CacheComponent cacheComponent;
    
    @Autowired
    private TxnCheckService txnCheckService;
    
    

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public void doAccountRiskCheck(PaymentInfo paymentInfo, TInfoAccount account) throws BizException {  
        //获取风控累积主表
        RiskLogacnttxnPayment riskLogacnttxnPayment = null;
        //获取风控累积子表
        RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail = null;    
        //总风控
        TRiskCustomerCommonRule checkRule = null;
        //特殊交易类型风控
        RiskCustomerTxntypeRule riskCustomerTxntypeRule = riskService.findRiskCustomerTxntypeRule(paymentInfo.getInnerTxnType(), paymentInfo.getChannel(),AccountType.explain(account.getType()),account.getGrade());
        //无特殊交易类型风控时,判断总风控
        if(!TxnCheckService.noAllRiskTxnSet.contains(paymentInfo.getInnerTxnType())){
	        checkRule = riskService.getCheckRule(paymentInfo, account);
	        riskLogacnttxnPayment = riskService.getriskLogacnttxnPayment(account.getAccountNo(), paymentInfo);
        }
        //有特殊交易类型风控时,判断特殊风控
        if (riskCustomerTxntypeRule != null){
	        //获取风空累积子表
	        riskLogacnttxnPaymentDetail =
	                riskService.getRiskLogacnttxnPaymentDetail(account.getAccountNo(), paymentInfo,riskCustomerTxntypeRule);	        
        }
        TParaAccountBal tParaAccountBal =
                riskService.getTParaAccountBalMapper().findAccountRiskCfg(account.getType(), account.getGrade(), paymentInfo.getTxnDate());
        riskCheck(checkRule, riskCustomerTxntypeRule, account, riskLogacnttxnPayment, riskLogacnttxnPaymentDetail, paymentInfo, tParaAccountBal);
    }

    
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public void doAccountRiskCheckOut(PaymentInfo paymentInfo,  AccountInfo accountInfo) throws BizException {  
    	String innerTxnType = cacheComponent.getInnerTxnType(paymentInfo.getBussinessType()).getInteriorTransCode();
    	TxnType txnType = TxnType.explain(innerTxnType.substring(1, 2).toCharArray()[0]);
    	txnCheckService.txnIniCheck(paymentInfo, txnType);
    	TInfoAccount tInfoAccount = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());    	
    	doAccountRiskCheck(paymentInfo,tInfoAccount);
    }


    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public TRiskCustomerCommonRule queryRiskCustomerCommonRule(AccountInfo accountInfo,
                                                               ManagerLog managerLog) throws BizException {
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        return riskService.findCustomerSelfDefineAccountRisk(account.getAccountNo(), account.getType(), managerLog.getTxnChannel());
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String setRiskCustomerCommonRule(AccountInfo accountInfo,
                                            ManagerLog managerLog, TRiskCustomerCommonRule setRule) throws BizException {
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TRiskCustomerCommonRule rule = riskService.findAccountGeneralRisk(managerLog.getTxnChannel(), account.getGrade(), account.getType());
        isRightSet(setRule, rule);
        riskService.RiskCustomerCommonRuleInDb(setRule, account, managerLog);
        return BussErrorCode.ERROR_CODE_000000.getErrorcode();
    }


    private void riskCheck(TRiskCustomerCommonRule customerCommRule,RiskCustomerTxntypeRule riskCustomerTxntypeRule , TInfoAccount account,
                           RiskLogacnttxnPayment riskLogacnttxnPayment,
                           RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail,
                           PaymentInfo paymentInfo, TParaAccountBal tParaAccountBal) {
    	//余额上线判断
        if ((paymentInfo.getTxnType().equals(TxnType.TXN_CHARGE) || (paymentInfo.getTxnType().equals(TxnType.TXN_TRANSFER_END))) 
        		&& !TxnCheckService.noCheckTxnSet.contains(paymentInfo.getInnerTxnType()) && (
                customerCommRule != null
                        && tParaAccountBal != null
                        && account.getBalance() + paymentInfo.getAmount() > tParaAccountBal
                        .getMaxAmount())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200019.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200019.getErrordesc());
        }
        
        //快捷同步消费由于要事先判断，直接调用风控接口判断账户状态
        if (paymentInfo.getTxnType().equals(TxnType.TXN_CONSUME)){
        	if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
                // 资金账户状态不为正常，不能消费
                throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200007.getErrordesc());
            }
        }

        //针对特殊交易类型的风控走特殊交易类型配置
        specTxnTypeRiskCheck(account, riskLogacnttxnPaymentDetail, riskCustomerTxntypeRule, paymentInfo);
    	//特殊交易类型不走总风控
    	if(!TxnCheckService.noAllRiskTxnSet.contains(paymentInfo.getInnerTxnType())){
		    //走总风控判断
		    specAllRiskCheck(account, riskLogacnttxnPayment, customerCommRule, paymentInfo);
    	}
        

        
    }

    /**
     * 针对特殊交易类型的风控走特殊交易类型配置
     *
     * @param account
     * @param riskLogacnttxnPaymentDetail
     * @param paymentInfo
     * @return
     */
    private boolean specTxnTypeRiskCheck(TInfoAccount account, RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail, RiskCustomerTxntypeRule  riskCustomerTxntypeRule, PaymentInfo paymentInfo) {
     

        if (null == riskCustomerTxntypeRule) {
            return false;
        }
        
        if (paymentInfo.getAmount() < riskCustomerTxntypeRule.getTransMinAmt()) {
            // 单笔金额小于单笔限额
            throw new BizException(BussErrorCode.ERROR_CODE_200164.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200164.getErrordesc(),StringUtil.formatNumber((double) riskCustomerTxntypeRule.getTransMinAmt()/100, 2));
        }

        if (paymentInfo.getAmount() > riskCustomerTxntypeRule.getTransMaxAmt()) {
            // 单笔金额超过单笔限额
            throw new BizException(BussErrorCode.ERROR_CODE_200164.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200164.getErrordesc(),StringUtil.formatNumber((double) riskCustomerTxntypeRule.getTransMaxAmt()/100, 2));
        }
               
        if ((riskLogacnttxnPaymentDetail.getDaySumAmt() + paymentInfo.getAmount()) > riskCustomerTxntypeRule.getDayTransAmt()) {
            //指定交易超过日限额
            throw new BizException(BussErrorCode.ERROR_CODE_200160.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200160.getErrordesc(),StringUtil.formatNumber((double) riskCustomerTxntypeRule.getDayTransAmt()/100, 2));
        }
        if ((riskLogacnttxnPaymentDetail.getDaySumTimes()) >= riskCustomerTxntypeRule.getDayTransTimes()) {
            //指定交易超过日次数
            throw new BizException(BussErrorCode.ERROR_CODE_200162.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200162.getErrordesc(),String.valueOf(riskCustomerTxntypeRule.getDayTransTimes()));
        }
        if ((riskLogacnttxnPaymentDetail.getMonthSumAmt() + paymentInfo.getAmount()) > riskCustomerTxntypeRule.getMonthTransAmt()) {
            //指定交易超过月限额
            throw new BizException(BussErrorCode.ERROR_CODE_200161.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200161.getErrordesc(),StringUtil.formatNumber((double) riskCustomerTxntypeRule.getMonthTransAmt()/100, 2));
        }
        if ((riskLogacnttxnPaymentDetail.getMonthSumTimes()) >= riskCustomerTxntypeRule.getMonthTransTimes()) {
            //指定交易超过月次数
            throw new BizException(BussErrorCode.ERROR_CODE_200163.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200163.getErrordesc(),String.valueOf(riskCustomerTxntypeRule.getMonthTransTimes()));
        }
        return true;
    }

    ;

	/**
	 * 总风控判断
	 *
	 * @param account
	 * @param riskLogacnttxnPaymentDetail
	 * @param paymentInfo
	 * @return
	 */
	private void specAllRiskCheck(TInfoAccount account, RiskLogacnttxnPayment riskLogacnttxnPayment, TRiskCustomerCommonRule customerCommRule, PaymentInfo paymentInfo) {
		switch (paymentInfo.getTxnType()) {
	    case TXN_CONSUME:
	    case TXN_PREAUTH:
	    case TXN_PREAUTH_END:
		
	        if (riskLogacnttxnPayment.getDaySumTimes() >= customerCommRule.getConsumeTransMaxNum())// .getDaySumConsCnt() >= accountRisk
	            // 当日累计消费次数已达最大单日消费次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200105.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200105.getErrordesc(),String.valueOf(customerCommRule.getConsumeTransMaxNum()));
	        if (paymentInfo.getAmount() < customerCommRule.getConsumeTransMinAmt()) {
	            // 单笔消费金额小于单笔消费限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200104.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200104.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getConsumeTransMinAmt()/100, 2));
	        }
	
	        if (paymentInfo.getAmount() > customerCommRule.getConsumeTransMaxAmt()) {
	            // 单笔消费金额超过单笔消费限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200104.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200104.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getConsumeTransMaxAmt()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getDaySumAmt() + paymentInfo.getAmount()) > customerCommRule.getConsumeTransMaxAmtSum()) {
	            // 当日累计消费金额已达最大单日消费金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200106.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200106.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getConsumeTransMaxAmtSum()/100, 2));
	        }
	
	        if ((riskLogacnttxnPayment.getMonthSumAmt() + paymentInfo.getAmount()) > customerCommRule.getMonthMaxConsAmt()) {
	            // 当月累计消费金额已达最大单月消费金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200113.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200113.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getMonthMaxConsAmt()/100, 2));
	        }
	
	        if ((riskLogacnttxnPayment.getMonthSumTimes()) >= customerCommRule.getMonthMaxConsCnt()) {
	            // 当月累计消费次数已达最大单月消费次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200113.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200113.getErrordesc(),String.valueOf(customerCommRule.getMonthMaxConsCnt()));
	        }
	        break;
	    case TXN_CHARGE:
	        // 充值
	        if (riskLogacnttxnPayment.getDaySumTimes() >= customerCommRule.getChargeTransMaxNum()) {
	            // 当日累计充值次数已达最大单日充值次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200102.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200102.getErrordesc(),String.valueOf(customerCommRule.getChargeTransMaxNum()));
	        }
	        if (paymentInfo.getAmount() < customerCommRule.getChargeTransMinAmt()) {
	            // 单笔充值金额小于单笔充值限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200101.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200101.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getChargeTransMinAmt()/100, 2));
	
	        }
	        if (paymentInfo.getAmount() > customerCommRule.getChargeTransMaxAmt()) {
	            // 单笔充值金额大于单笔充值限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200101.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200101.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getChargeTransMaxAmt()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getDaySumAmt() + paymentInfo.getAmount()) > customerCommRule.getChargeTransMaxAmtSum()) {
	            // 当日累计充值金额已达最大单日充值金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200103.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200103.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getChargeTransMaxAmtSum()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getMonthSumAmt() + paymentInfo.getAmount()) > customerCommRule.getMonthMaxChgAmt()) {
	            //超过当月累计充值金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200117.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200117.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getMonthMaxChgAmt()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getMonthSumTimes()) > customerCommRule.getMonthMaxChgCnt()) {
	            //超过当月累计充值次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200125.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200125.getErrordesc(),String.valueOf(customerCommRule.getMonthMaxChgCnt()));
	        }
	        break;
	    case TXN_TRANSFER_END:
	        // 转账转入
	        if (riskLogacnttxnPayment.getDaySumTimes() > customerCommRule.getTransferInMaxNum()) {
	            // 当日累计转入次数已达最大单日转入次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200108.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200108.getErrordesc(),String.valueOf(customerCommRule.getTransferInMaxNum()));
	        }
	        if (paymentInfo.getAmount() < customerCommRule.getTransferInMinAmt()) {
	            // 单笔转入金额小于单笔转入限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200107.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200107.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getTransferInMinAmt()/100, 2));
	        }
	        if (paymentInfo.getAmount() > customerCommRule.getTransferInMaxAmt()) {
	            // 单笔转入金额超过单笔转入限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200107.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200107.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getTransferInMaxAmt()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getDaySumAmt() + paymentInfo.getAmount()) > customerCommRule.getTransferInMaxAmtSum()) {
	            // 当日累计转入金额已达最大单日转入金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200109.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200109.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getTransferInMaxAmtSum()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getMonthSumAmt() + paymentInfo.getAmount()) > customerCommRule.getMonthMaxInAmt()) {
	            // 当月累计转入金额已达最大单月转入金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200115.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200115.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getMonthMaxInAmt()/100, 2));
	        }
	        if (riskLogacnttxnPayment.getMonthSumTimes() >= customerCommRule.getMonthMaxInCnt()) {
	            // 当月累计转入次数已达最大单月转入次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200123.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200123.getErrordesc(),String.valueOf(customerCommRule.getMonthMaxInCnt()));
	        }
	        break;
	    case TXN_TRANSFER:
	        // 检查转账转出
	        if (riskLogacnttxnPayment.getMonthSumTimes() >= customerCommRule.getTransferTransMaxNum()) {
	            // 当日累计转出次数已达最大单日转出次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200123.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200123.getErrordesc(),String.valueOf(customerCommRule.getTransferTransMaxNum()));
	
	        }
	        if (riskLogacnttxnPayment.getDaySumTimes() >= customerCommRule.getTransferTransMaxNum()) {
	            // 当日累计转出次数已达最大单日转出次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200111.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200111.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getTransferTransMaxNum()/100, 2));
	
	        }
	        if (paymentInfo.getAmount() < customerCommRule.getTransferTransMinAmt()) {
	            // 单笔转出金额小于单笔转出限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200110.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200110.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getTransferTransMinAmt()/100, 2));
	
	        }
	        if (paymentInfo.getAmount() > customerCommRule.getTransferTransMaxAmt()) {
	            // 单笔转出金额超过单笔转出限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200110.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200110.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getTransferTransMaxAmt()/100, 2));
	
	        }
	        if ((riskLogacnttxnPayment.getDaySumAmt() + paymentInfo.getAmount()) > customerCommRule.getTransferTransMaxAmtSum()) {
	            // 当日累计转出金额已达最大单日转出金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200112.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200112.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getTransferTransMaxAmtSum()/100, 2));
	        }
	
	        if ((riskLogacnttxnPayment.getMonthSumAmt() + paymentInfo.getAmount()) > customerCommRule.getMonthMaxOutAmt()) {
	            // 当月累计转出金额已达最大单月转出金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200114.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200114.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getMonthMaxOutAmt()/100, 2));
	        }
	
	        if (riskLogacnttxnPayment.getMonthSumTimes() >= customerCommRule.getMonthMaxOutCnt()) {
	            // 当月累计转出次数已达最大单月转出次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200122.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200122.getErrordesc(),String.valueOf(customerCommRule.getMonthMaxOutCnt()));
	        }
	        break;
	
	    case TXN_CASH:
	        // 检查提现
	        if (riskLogacnttxnPayment.getDaySumTimes() >= customerCommRule.getWithdrawTransMaxNum()) {
	            // 当日累计提现次数已达最大单日提现次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200119.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200119.getErrordesc(),String.valueOf(customerCommRule.getWithdrawTransMaxNum()));
	        }
	
	        if (paymentInfo.getAmount() < customerCommRule.getWithdrawTransMinAmt()) {
	            // 单笔提现金额小于单笔提现限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200118.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200118.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getWithdrawTransMinAmt()/100, 2));
	        }
	        if (paymentInfo.getAmount() > customerCommRule.getWithdrawTransMaxAmt()) {
	            // 单笔提现金额超过单笔提现限额
	            throw new BizException(BussErrorCode.ERROR_CODE_200118.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200118.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getWithdrawTransMaxAmt()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getDaySumAmt() + paymentInfo.getAmount()) > customerCommRule.getWithdrawTransMaxAmtSum()) {
	            // 当日累计提现金额已达最大单日提现金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200120.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200120.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getWithdrawTransMaxAmtSum()/100, 2));
	        }
	
	        if ((riskLogacnttxnPayment.getMonthSumAmt() + paymentInfo.getAmount()) > customerCommRule.getMonthMaxCashAmt()) {
	            // 当月累计提现金额已达最大单月提现金额
	            throw new BizException(BussErrorCode.ERROR_CODE_200116.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200116.getErrordesc(),StringUtil.formatNumber((double) customerCommRule.getMonthMaxCashAmt()/100, 2));
	        }
	        if ((riskLogacnttxnPayment.getMonthSumTimes() >= customerCommRule.getMonthMaxCashCnt())) {
	            // 当月累计提现次数已达最大单月提现次数
	            throw new BizException(BussErrorCode.ERROR_CODE_200124.getErrorcode(),
	                    BussErrorCode.ERROR_CODE_200124.getErrordesc(),String.valueOf(customerCommRule.getMonthMaxCashCnt()));
	        }
		       
		}
    	
    	
    }
    	   
    
    

    private void isRightSet(TRiskCustomerCommonRule setRule, TRiskCustomerCommonRule rule) throws BizException {
        // 消费风控设定=========================================
        // 日消费交易最大次数
        if (setRule.getConsumeTransMaxNum() != null && setRule.getConsumeTransMaxNum() > rule.getConsumeTransMaxNum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500001.getErrorcode(), BussErrorCode.ERROR_CODE_500001.getErrordesc(),
                    String.valueOf(rule.getConsumeTransMaxNum()));
        }
        // 日消费交易单笔限额
        if (setRule.getConsumeTransMaxAmt() != null && setRule.getConsumeTransMaxAmt() > rule.getConsumeTransMaxAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500002.getErrorcode(), BussErrorCode.ERROR_CODE_200052.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getConsumeTransMaxAmt() / 100, 2));
        }
        // 日消费交易累计限额
        if (setRule.getConsumeTransMaxAmtSum() != null && setRule.getConsumeTransMaxAmtSum() > rule.getConsumeTransMaxAmtSum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500003.getErrorcode(), BussErrorCode.ERROR_CODE_500003.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getConsumeTransMaxAmtSum() / 100, 2));
        }
        // 日消费交易单笔最小额
        if (setRule.getConsumeTransMinAmt() != null && setRule.getConsumeTransMinAmt() < rule.getConsumeTransMinAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500004.getErrorcode(), BussErrorCode.ERROR_CODE_500004.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getConsumeTransMinAmt() / 100, 2));
        }
        // 充值风控设定=========================================
        // 日充值交易最大次数
        if (setRule.getChargeTransMaxNum() != null && setRule.getChargeTransMaxNum() > rule.getChargeTransMaxNum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500005.getErrorcode(), BussErrorCode.ERROR_CODE_500005.getErrordesc(),
                    String.valueOf(rule.getChargeTransMaxNum()));
        }
        // 日充值交易单笔限额
        if (setRule.getChargeTransMaxAmt() != null && setRule.getChargeTransMaxAmt() > rule.getChargeTransMaxAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500006.getErrorcode(), BussErrorCode.ERROR_CODE_500006.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getChargeTransMaxAmt() / 100, 2));
        }
        // 日充值交易单笔最小额
        if (setRule.getChargeTransMinAmt() != null && setRule.getChargeTransMinAmt() < rule.getChargeTransMinAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500007.getErrorcode(), BussErrorCode.ERROR_CODE_500007.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getChargeTransMinAmt() / 100, 2));
        }
        // 日充值交易累计限额
        if (setRule.getChargeTransMaxAmtSum() != null && setRule.getChargeTransMaxAmtSum() > rule.getChargeTransMaxAmtSum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500008.getErrorcode(), BussErrorCode.ERROR_CODE_500008.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getChargeTransMaxAmtSum() / 100, 2));
        }
        // 提现风控设定=========================================
        // 日提现交易最大次数
        if (setRule.getWithdrawTransMaxNum() != null && setRule.getWithdrawTransMaxNum() > rule.getWithdrawTransMaxNum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500009.getErrorcode(), BussErrorCode.ERROR_CODE_500009.getErrordesc(),
                    String.valueOf(rule.getWithdrawTransMaxNum()));
        }
        // 日提现交易单笔限额
        if (setRule.getWithdrawTransMaxAmt() != null && setRule.getWithdrawTransMaxAmt() > rule.getWithdrawTransMaxAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500010.getErrorcode(), BussErrorCode.ERROR_CODE_500010.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getWithdrawTransMaxAmt() / 100, 2));
        }
        // 日提现交易单笔最小额
        if (setRule.getWithdrawTransMinAmt() != null && setRule.getWithdrawTransMinAmt() < rule.getWithdrawTransMinAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500011.getErrorcode(), BussErrorCode.ERROR_CODE_500011.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getWithdrawTransMinAmt() / 100, 2));
        }
        // 日提现交易累计限额
        if (setRule.getWithdrawTransMaxAmtSum() != null && setRule.getWithdrawTransMaxAmtSum() > rule.getWithdrawTransMaxAmtSum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500012.getErrorcode(), BussErrorCode.ERROR_CODE_500012.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getWithdrawTransMaxAmtSum() / 100, 2));
        }
        // 转账风控设定=========================================
        // 日转账交易最大次数
        if (setRule.getTransferTransMaxNum() != null && setRule.getTransferTransMaxNum() > rule.getTransferTransMaxNum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500013.getErrorcode(), BussErrorCode.ERROR_CODE_500013.getErrordesc(),
                    String.valueOf(rule.getTransferTransMaxNum()));
        }
        // 日转账交易单笔限额
        if (setRule.getTransferTransMaxAmt() != null && setRule.getTransferTransMaxAmt() > rule.getTransferTransMaxAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500014.getErrorcode(), BussErrorCode.ERROR_CODE_500014.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getTransferTransMaxAmt() / 100, 2));
        }
        // 日转账交易单笔最小额
        if (setRule.getTransferInMinAmt() != null && setRule.getTransferInMinAmt() < rule.getTransferInMinAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500015.getErrorcode(), BussErrorCode.ERROR_CODE_500015.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getTransferInMinAmt() / 100, 2));
        }
        // 日转账交易累计限额
        if (setRule.getTransferInMaxAmtSum() != null && setRule.getTransferInMaxAmtSum() > rule.getTransferInMaxAmtSum()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500016.getErrorcode(), BussErrorCode.ERROR_CODE_500016.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getTransferInMaxAmtSum() / 100, 2));
        }
        // 月累计风控设定=========================================
        // 月累计消费次数
        if (setRule.getMonthMaxConsCnt() != null && setRule.getMonthMaxConsCnt() > rule.getMonthMaxConsCnt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500017.getErrorcode(), BussErrorCode.ERROR_CODE_500017.getErrordesc(),
                    String.valueOf(rule.getMonthMaxConsCnt()));
        }
        // 月累计消费限额
        if (setRule.getMonthMaxConsAmt() != null && setRule.getMonthMaxConsAmt() > rule.getMonthMaxConsAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500018.getErrorcode(), BussErrorCode.ERROR_CODE_500018.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getMonthMaxConsAmt() / 100, 2));
        }
        // 月累计转出次数
        if (setRule.getMonthMaxOutCnt() != null && setRule.getMonthMaxOutCnt() > rule.getMonthMaxOutCnt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500019.getErrorcode(), BussErrorCode.ERROR_CODE_500019.getErrordesc(),
                    String.valueOf(rule.getMonthMaxOutCnt()));
        }
        // 月累计转出限额
        if (setRule.getMonthMaxOutAmt() != null && setRule.getMonthMaxOutAmt() > rule.getMonthMaxOutAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500020.getErrorcode(), BussErrorCode.ERROR_CODE_500020.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getMonthMaxOutAmt() / 100, 2));
        }
        // 月累计转入次数
        if (setRule.getMonthMaxInCnt() != null && setRule.getMonthMaxInCnt() > rule.getMonthMaxInCnt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500021.getErrorcode(), BussErrorCode.ERROR_CODE_500021.getErrordesc(),
                    String.valueOf(rule.getMonthMaxInCnt()));
        }
        // 月累计转入限额
        if (setRule.getMonthMaxInAmt() != null && setRule.getMonthMaxInAmt() > rule.getMonthMaxInAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500022.getErrorcode(), BussErrorCode.ERROR_CODE_500022.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getMonthMaxInAmt() / 100, 2));
        }
        // 月累计提现次数
        if (setRule.getMonthMaxCashCnt() != null && setRule.getMonthMaxCashCnt() > rule.getMonthMaxCashCnt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500023.getErrorcode(), BussErrorCode.ERROR_CODE_500023.getErrordesc(),
                    String.valueOf(rule.getMonthMaxCashCnt()));
        }
        // 月累计提现限额
        if (setRule.getMonthMaxCashAmt() != null && setRule.getMonthMaxCashAmt() > rule.getMonthMaxCashAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500024.getErrorcode(), BussErrorCode.ERROR_CODE_500024.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getMonthMaxCashAmt() / 100, 2));
        }
        // 月累计充值次数
        if (setRule.getMonthMaxChgCnt() != null && setRule.getMonthMaxChgCnt() > rule.getMonthMaxChgCnt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500025.getErrorcode(), BussErrorCode.ERROR_CODE_500025.getErrordesc(),
                    String.valueOf(rule.getMonthMaxChgCnt()));
        }
        // 月累计充值限额
        if (setRule.getMonthMaxChgAmt() != null && setRule.getMonthMaxChgAmt() > rule.getMonthMaxChgAmt()) {
            throw new BizException(BussErrorCode.ERROR_CODE_500026.getErrorcode(), BussErrorCode.ERROR_CODE_500026.getErrordesc(),
                    StringUtil.formatNumber((double) rule.getMonthMaxChgAmt() / 100, 2));
        }
    }
}
