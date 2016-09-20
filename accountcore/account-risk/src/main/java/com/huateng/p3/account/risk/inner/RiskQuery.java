package com.huateng.p3.account.risk.inner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskQueryObject;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.component.RiskQueryComponent;
import com.huateng.p3.account.persistence.models.RiskCustomerTxntypeRule;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPayment;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPaymentDetail;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TParaAccountBal;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;



/**
 * Created with IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-3-20
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RiskQuery {


    @Autowired
    private AccountService accountService;

    @Autowired
    private RiskService riskService;
    
    @Autowired
    private TxnCheckService txnCheckService;
    
    @Autowired
    private RiskQueryComponent riskQueryComponent;
    
    @Autowired
    private CacheComponent cacheComponent;
    

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public RiskQueryObject doAccountRiskQuery(AccountInfo accountInfo,
    		PaymentInfo paymentInfo) throws BizException {
    	//根据内部交易类型判断交易大类
    	String innerTxnType = cacheComponent.getInnerTxnType(paymentInfo.getBussinessType()).getInteriorTransCode();
    	TxnType txnType = TxnType.explain(innerTxnType.substring(1, 2).toCharArray()[0]);
    	txnCheckService.txnIniCheck(paymentInfo, txnType);
    	TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());        
        //获取风空累积主表
        RiskLogacnttxnPayment riskLogacnttxnPayment = null;
        //获取风空累积子表
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
	                riskService.getRiskLogacnttxnPaymentDetail(account.getAccountNo(), paymentInfo, riskCustomerTxntypeRule);	        
        }
        TParaAccountBal tParaAccountBal =
                riskService.getTParaAccountBalMapper().findAccountRiskCfg(account.getType(), account.getGrade(), paymentInfo.getTxnDate());
        return riskQuery(checkRule, riskCustomerTxntypeRule, account, riskLogacnttxnPayment, riskLogacnttxnPaymentDetail, paymentInfo ,tParaAccountBal);
    }


   


    private RiskQueryObject riskQuery(TRiskCustomerCommonRule customerCommRule,RiskCustomerTxntypeRule riskCustomerTxntypeRule , TInfoAccount account,
                           RiskLogacnttxnPayment riskLogacnttxnPayment,
                           RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail,
                           PaymentInfo paymentInfo, TParaAccountBal tParaAccountBal) {
    	//金额与次数风控list，最后获取最小值
    	List<Long>  amountList =Lists.newArrayList();
        List<Long>  timesList =Lists.newArrayList();   	
        
        
        //余额上线判断
        if ((paymentInfo.getTxnType().equals(TxnType.TXN_CHARGE) || (paymentInfo.getTxnType().equals(TxnType.TXN_TRANSFER_END))) 
        		&& !TxnCheckService.noCheckTxnSet.contains(paymentInfo.getInnerTxnType()) && customerCommRule != null&& tParaAccountBal != null) {
        	amountList.add(tParaAccountBal.getMaxAmount()-account.getBalance());
        	
        }
        //快捷同步消费判断账户状态
        if (paymentInfo.getTxnType().equals(TxnType.TXN_CONSUME)){
        	if (!AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus().equals(account.getStatus())) {
                // 资金账户状态不为正常，不能消费
                throw new BizException(BussErrorCode.ERROR_CODE_200007.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200007.getErrordesc());
            }
        }
        
        //针对特殊交易类型的风控走特殊交易类型配置
        specTxnTypeRiskQuery(account, riskLogacnttxnPaymentDetail, riskCustomerTxntypeRule, paymentInfo,amountList,timesList);
    	//特殊交易类型不走总风控
    	if(!TxnCheckService.noAllRiskTxnSet.contains(paymentInfo.getInnerTxnType())){
            //走总风控判断  
    		specAllRiskQuery(account, riskLogacnttxnPayment, customerCommRule, paymentInfo,amountList,timesList);   
    	}      	        
        return riskQueryComponent.genRiskQueryResultObject(account, paymentInfo,amountList,timesList);
    }

    /**
     * 针对特殊交易类型的风控走特殊交易类型配置
     *
     * @param account
     * @param riskLogacnttxnPaymentDetail
     * @param paymentInfo
     * @return
     */
    private boolean specTxnTypeRiskQuery(TInfoAccount account, RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail, RiskCustomerTxntypeRule  riskCustomerTxntypeRule, PaymentInfo paymentInfo,List<Long>  amountList ,List<Long>  timesList) {

        if (null == riskCustomerTxntypeRule) {
            return false;
        }        
        amountList.add(riskCustomerTxntypeRule.getDayTransAmt() - riskLogacnttxnPaymentDetail.getDaySumAmt() );
        amountList.add(riskCustomerTxntypeRule.getMonthTransAmt() - riskLogacnttxnPaymentDetail.getMonthSumAmt() );
        amountList.add(riskCustomerTxntypeRule.getTransMaxAmt());
        timesList.add(riskCustomerTxntypeRule.getDayTransTimes() - riskLogacnttxnPaymentDetail.getDaySumTimes());
        timesList.add(riskCustomerTxntypeRule.getMonthTransTimes() - riskLogacnttxnPaymentDetail.getMonthSumTimes());
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
	private void specAllRiskQuery(TInfoAccount account, RiskLogacnttxnPayment riskLogacnttxnPayment, TRiskCustomerCommonRule customerCommRule, PaymentInfo paymentInfo ,List<Long>  amountList ,List<Long>  timesList) {		
		switch (paymentInfo.getTxnType()) {
	    case TXN_CONSUME:
	    case TXN_PREAUTH:
	    case TXN_PREAUTH_END:
			amountList.add(customerCommRule.getConsumeTransMaxAmtSum() - riskLogacnttxnPayment.getDaySumAmt() );
	        amountList.add(customerCommRule.getMonthMaxConsAmt() - riskLogacnttxnPayment.getMonthSumAmt() );
	        amountList.add(customerCommRule.getConsumeTransMaxAmt());
	        timesList.add(customerCommRule.getConsumeTransMaxNum() - riskLogacnttxnPayment.getDaySumTimes());
	        timesList.add(customerCommRule.getMonthMaxConsCnt() - riskLogacnttxnPayment.getMonthSumTimes());
	        break;
	        
	    case TXN_CHARGE:
	    	amountList.add(customerCommRule.getChargeTransMaxAmtSum() - riskLogacnttxnPayment.getDaySumAmt() );
	        amountList.add(customerCommRule.getMonthMaxChgAmt() - riskLogacnttxnPayment.getMonthSumAmt() );
	        amountList.add(customerCommRule.getChargeTransMaxAmt());
	        timesList.add(customerCommRule.getChargeTransMaxNum() - riskLogacnttxnPayment.getDaySumTimes());
	        timesList.add(customerCommRule.getMonthMaxChgCnt() - riskLogacnttxnPayment.getMonthSumTimes());
	        break;
	    case TXN_TRANSFER_END:
	    	amountList.add(customerCommRule.getTransferInMaxAmtSum() - riskLogacnttxnPayment.getDaySumAmt() );
	        amountList.add(customerCommRule.getMonthMaxInAmt() - riskLogacnttxnPayment.getMonthSumAmt() );
	        amountList.add(customerCommRule.getTransferInMaxAmt());
	        timesList.add(customerCommRule.getTransferInMaxNum() - riskLogacnttxnPayment.getDaySumTimes());
	        timesList.add(customerCommRule.getMonthMaxInCnt() - riskLogacnttxnPayment.getMonthSumTimes());
	        break;
	    case TXN_TRANSFER:
	    	amountList.add(customerCommRule.getTransferTransMaxAmtSum() - riskLogacnttxnPayment.getDaySumAmt() );
	        amountList.add(customerCommRule.getMonthMaxOutAmt() - riskLogacnttxnPayment.getMonthSumAmt() );
	        amountList.add(customerCommRule.getTransferTransMaxAmt());
	        timesList.add(customerCommRule.getTransferTransMaxNum() - riskLogacnttxnPayment.getDaySumTimes());
	        timesList.add(customerCommRule.getMonthMaxOutCnt() - riskLogacnttxnPayment.getMonthSumTimes());
	        break;
	    case TXN_CASH:
	    	amountList.add(customerCommRule.getWithdrawTransMaxAmtSum() - riskLogacnttxnPayment.getDaySumAmt() );
	        amountList.add(customerCommRule.getMonthMaxCashAmt() - riskLogacnttxnPayment.getMonthSumAmt() );
	        amountList.add(customerCommRule.getWithdrawTransMaxAmt());
	        timesList.add(customerCommRule.getWithdrawTransMaxNum() - riskLogacnttxnPayment.getDaySumTimes());
	        timesList.add(customerCommRule.getMonthMaxCashCnt() - riskLogacnttxnPayment.getMonthSumTimes());
	        break; 
		       
		}
	}

   

    
}
