package com.huateng.p3.account.risk;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.huateng.p3.account.common.bizparammodel.RiskCustomerTxnDataMergeInfo;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnChannel;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.persistence.RiskLogacnttxnPaymentDetailMapper;
import com.huateng.p3.account.persistence.RiskLogacnttxnPaymentMapper;
import com.huateng.p3.account.persistence.models.RiskCustomerTxntypeRule;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPayment;
import com.huateng.p3.account.persistence.models.RiskLogacnttxnPaymentDetail;

/**
 * 风控交易数据累计
 * User: JamesTang
 * Date: 13-12-11
 * Time: 上午10:49
 */
@Service
@Slf4j
public class RiskMergeService {
    @Autowired
    RiskLogacnttxnPaymentDetailMapper riskLogacnttxnPaymentDetailMapper;

    @Autowired
    RiskLogacnttxnPaymentMapper riskLogacnttxnPaymentMapper;
    
    @Autowired
    RiskService riskService;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void accountRiskMerge(RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo) {
        log.info("call accountRiskMerge,PARAMETER:{}", riskCustomerTxnDataMergeInfo);
        //预授权风控设置为消费风控
        if(TxnType.TXN_PREAUTH.equals(riskCustomerTxnDataMergeInfo.getTxnType())||TxnType.TXN_PREAUTH_END.equals(riskCustomerTxnDataMergeInfo.getTxnType())){
        	riskCustomerTxnDataMergeInfo.setTxnType(TxnType.TXN_CONSUME);
        }
        //没有原始交易类型,风控累加值
        if(Strings.isNullOrEmpty(riskCustomerTxnDataMergeInfo.getOldInnerTxnType())){
        	//特殊交易类型不累加总风控
        	if(!TxnCheckService.noAllRiskTxnSet.contains(riskCustomerTxnDataMergeInfo.getInnerTxnType())){
        		txnLogRiskMergeIncrease(riskCustomerTxnDataMergeInfo);
        	}
	        
	        txnLogDetailMergeIncrease(riskCustomerTxnDataMergeInfo);
        }
        //有原始交易类型，风控减少值
        if(!Strings.isNullOrEmpty(riskCustomerTxnDataMergeInfo.getOldInnerTxnType())){
        	//特殊交易类型不累加总风控
        	if(!TxnCheckService.noAllRiskTxnSet.contains(riskCustomerTxnDataMergeInfo.getOldInnerTxnType())){
        		txnLogRiskMergeDecrease(riskCustomerTxnDataMergeInfo);
        	}
	        txnLogDetailMergeDecrease(riskCustomerTxnDataMergeInfo);        
        }
    }

    private void txnLogRiskMergeIncrease(RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo) {
        RiskLogacnttxnPayment riskLogacnttxnPayment =
                riskLogacnttxnPaymentMapper.selectByTxnKeyForUpdate(riskCustomerTxnDataMergeInfo.getAccountNo(), String.valueOf(riskCustomerTxnDataMergeInfo.getTxnType().getTxnCode()));
        if (null == riskLogacnttxnPayment) {
            riskLogacnttxnPayment = new RiskLogacnttxnPayment();
            riskLogacnttxnPayment.setAccountNo(riskCustomerTxnDataMergeInfo.getAccountNo());
            riskLogacnttxnPayment.setDaySumAmt(riskCustomerTxnDataMergeInfo.getTxnAmount());
            riskLogacnttxnPayment.setDaySumTimes(1L);
            riskLogacnttxnPayment.setMonthSumAmt(riskCustomerTxnDataMergeInfo.getTxnAmount());
            riskLogacnttxnPayment.setMonthSumTimes(1L);
            riskLogacnttxnPayment.settransBigtype(String.valueOf(riskCustomerTxnDataMergeInfo.getTxnType().getTxnCode()));
            riskLogacnttxnPayment.setlastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
            riskLogacnttxnPayment.setlastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
            riskLogacnttxnPaymentMapper.insert(riskLogacnttxnPayment);
            return;
        }

        if (!riskLogacnttxnPayment.getlastTransDate().equals(riskCustomerTxnDataMergeInfo.getTxnDate())) {
            riskLogacnttxnPayment.setDaySumAmt(0L);
            riskLogacnttxnPayment.setDaySumTimes(0L);
            riskLogacnttxnPayment.setlastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
        }
        if (!riskLogacnttxnPayment.getlastTransMonth().equals(riskCustomerTxnDataMergeInfo.getTxnMonth())) {
            riskLogacnttxnPayment.setMonthSumAmt(0L);
            riskLogacnttxnPayment.setMonthSumTimes(0L);
            riskLogacnttxnPayment.setlastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
        }

       
        riskLogacnttxnPayment.setDaySumAmt(riskLogacnttxnPayment.getDaySumAmt() + riskCustomerTxnDataMergeInfo.getTxnAmount());
        riskLogacnttxnPayment.setDaySumTimes(riskLogacnttxnPayment.getDaySumTimes() + 1);
        riskLogacnttxnPayment.setMonthSumAmt(riskLogacnttxnPayment.getMonthSumAmt() + riskCustomerTxnDataMergeInfo.getTxnAmount());
        riskLogacnttxnPayment.setMonthSumTimes(riskLogacnttxnPayment.getMonthSumTimes() + 1);       
        riskLogacnttxnPayment.setlastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
        riskLogacnttxnPayment.setlastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
        riskLogacnttxnPaymentMapper.updateByPrimaryKey(riskLogacnttxnPayment);
    }

    private void txnLogDetailMergeIncrease(RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo) {
    	
    	//特殊交易类型风控
        RiskCustomerTxntypeRule riskCustomerTxntypeRule = riskService.findRiskCustomerTxntypeRule( riskCustomerTxnDataMergeInfo.getInnerTxnType(), riskCustomerTxnDataMergeInfo.getTxnChannel() , riskCustomerTxnDataMergeInfo.getAccountType() ,riskCustomerTxnDataMergeInfo.getCustomerGrade());
        //有特殊交易类型风控时,判断特殊风控
        if (riskCustomerTxntypeRule != null){
        	//需要合并渠道
	        if(TrueOrFalse.TRUE.getLablCode().equals(riskCustomerTxntypeRule.getMergerFlag())){
	        	riskCustomerTxnDataMergeInfo.setTxnChannel(TxnChannel.TXN_CHANNEL_All.getTxnCode());
	        }
        }
    	
        RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail = riskLogacnttxnPaymentDetailMapper.selectByTxnKeyForUpdate(riskCustomerTxnDataMergeInfo.getAccountNo(),
                riskCustomerTxnDataMergeInfo.getTxnChannel(), riskCustomerTxnDataMergeInfo.getInnerTxnType());
        if (null == riskLogacnttxnPaymentDetail) {
            riskLogacnttxnPaymentDetail = new RiskLogacnttxnPaymentDetail();
            riskLogacnttxnPaymentDetail.setAccountNo(riskCustomerTxnDataMergeInfo.getAccountNo());
            riskLogacnttxnPaymentDetail.setDaySumAmt(riskCustomerTxnDataMergeInfo.getTxnAmount());
            riskLogacnttxnPaymentDetail.setDaySumTimes(1L);
            riskLogacnttxnPaymentDetail.setMonthSumAmt(riskCustomerTxnDataMergeInfo.getTxnAmount());
            riskLogacnttxnPaymentDetail.setMonthSumTimes(1L);
            riskLogacnttxnPaymentDetail.setTransType(riskCustomerTxnDataMergeInfo.getInnerTxnType());
            riskLogacnttxnPaymentDetail.setAcceptChannel(riskCustomerTxnDataMergeInfo.getTxnChannel());
            riskLogacnttxnPaymentDetail.setTransBigtype(String.valueOf(riskCustomerTxnDataMergeInfo.getTxnType().getTxnCode()));
            riskLogacnttxnPaymentDetail.setLastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
            riskLogacnttxnPaymentDetail.setLastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
            riskLogacnttxnPaymentDetailMapper.insert(riskLogacnttxnPaymentDetail);
            return;
        }

        if (!riskLogacnttxnPaymentDetail.getLastTransDate().equals(riskCustomerTxnDataMergeInfo.getTxnDate())) {
            riskLogacnttxnPaymentDetail.setDaySumAmt(0l);
            riskLogacnttxnPaymentDetail.setDaySumTimes(0l);
            riskLogacnttxnPaymentDetail.setLastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
        }
        if (!riskLogacnttxnPaymentDetail.getLastTransMonth().equals(riskCustomerTxnDataMergeInfo.getTxnMonth())) {
            riskLogacnttxnPaymentDetail.setMonthSumAmt(0l);
            riskLogacnttxnPaymentDetail.setMonthSumTimes(0l);
            riskLogacnttxnPaymentDetail.setLastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
        }
       
        riskLogacnttxnPaymentDetail.setDaySumAmt(riskLogacnttxnPaymentDetail.getDaySumAmt() + riskCustomerTxnDataMergeInfo.getTxnAmount());
        riskLogacnttxnPaymentDetail.setDaySumTimes(riskLogacnttxnPaymentDetail.getDaySumTimes() + 1);
        riskLogacnttxnPaymentDetail.setMonthSumAmt(riskLogacnttxnPaymentDetail.getMonthSumAmt() + riskCustomerTxnDataMergeInfo.getTxnAmount());
        riskLogacnttxnPaymentDetail.setMonthSumTimes(riskLogacnttxnPaymentDetail.getMonthSumTimes() + 1);
        riskLogacnttxnPaymentDetail.setLastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
        riskLogacnttxnPaymentDetail.setLastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
        riskLogacnttxnPaymentDetailMapper.updateByPrimaryKeySelective(riskLogacnttxnPaymentDetail);
    }

    private void txnLogRiskMergeDecrease(RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo) {
    	RiskLogacnttxnPayment riskLogacnttxnPayment =
                riskLogacnttxnPaymentMapper.selectByTxnKeyForUpdate(riskCustomerTxnDataMergeInfo.getAccountNo(), String.valueOf(riskCustomerTxnDataMergeInfo.getTxnType().getTxnCode()));
        if (null == riskLogacnttxnPayment) {
            return;
        }

        if (!riskLogacnttxnPayment.getlastTransDate().equals(riskCustomerTxnDataMergeInfo.getTxnDate())) {
            riskLogacnttxnPayment.setDaySumAmt(0L);
            riskLogacnttxnPayment.setDaySumTimes(0L);
            riskLogacnttxnPayment.setlastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
            if (!riskLogacnttxnPayment.getlastTransMonth().equals(riskCustomerTxnDataMergeInfo.getTxnMonth())) {
                riskLogacnttxnPayment.setMonthSumAmt(0L);
                riskLogacnttxnPayment.setMonthSumTimes(0L);
                riskLogacnttxnPayment.setlastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());               
            }
            riskLogacnttxnPaymentMapper.updateByPrimaryKey(riskLogacnttxnPayment);           
            return;
        }
            
        riskLogacnttxnPayment.setDaySumAmt(riskLogacnttxnPayment.getDaySumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount()>0 ? riskLogacnttxnPayment.getDaySumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount():0);
        riskLogacnttxnPayment.setDaySumTimes(riskLogacnttxnPayment.getDaySumTimes() - 1 > 0 ?riskLogacnttxnPayment.getDaySumTimes() - 1 : 0);
        riskLogacnttxnPayment.setMonthSumAmt(riskLogacnttxnPayment.getMonthSumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount()>0?riskLogacnttxnPayment.getMonthSumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount():0);
        riskLogacnttxnPayment.setMonthSumTimes(riskLogacnttxnPayment.getMonthSumTimes() - 1 >0 ? riskLogacnttxnPayment.getMonthSumTimes() - 1 : 0);
        riskLogacnttxnPayment.setlastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
        riskLogacnttxnPayment.setlastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
        riskLogacnttxnPaymentMapper.updateByPrimaryKey(riskLogacnttxnPayment);
    }

    private void txnLogDetailMergeDecrease(RiskCustomerTxnDataMergeInfo riskCustomerTxnDataMergeInfo) {
    	//特殊交易类型风控
        RiskCustomerTxntypeRule riskCustomerTxntypeRule = riskService.findRiskCustomerTxntypeRule( riskCustomerTxnDataMergeInfo.getOldInnerTxnType(), riskCustomerTxnDataMergeInfo.getTxnChannel() , riskCustomerTxnDataMergeInfo.getAccountType() ,riskCustomerTxnDataMergeInfo.getCustomerGrade());
        //有特殊交易类型风控时,判断特殊风控
        if (riskCustomerTxntypeRule != null){
        	//需要合并渠道
	        if(TrueOrFalse.TRUE.getLablCode().equals(riskCustomerTxntypeRule.getMergerFlag())){
	        	riskCustomerTxnDataMergeInfo.setTxnChannel(TxnChannel.TXN_CHANNEL_All.getTxnCode());
	        }
        }
    	
    	RiskLogacnttxnPaymentDetail riskLogacnttxnPaymentDetail = riskLogacnttxnPaymentDetailMapper.selectByTxnKeyForUpdate(riskCustomerTxnDataMergeInfo.getAccountNo(),
                riskCustomerTxnDataMergeInfo.getTxnChannel(), riskCustomerTxnDataMergeInfo.getOldInnerTxnType());
        if (null == riskLogacnttxnPaymentDetail) {
            return;
        }

        if (!riskLogacnttxnPaymentDetail.getLastTransDate().equals(riskCustomerTxnDataMergeInfo.getTxnDate())) {
            riskLogacnttxnPaymentDetail.setDaySumAmt(0l);
            riskLogacnttxnPaymentDetail.setDaySumTimes(0l);
            riskLogacnttxnPaymentDetail.setLastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
            if (!riskLogacnttxnPaymentDetail.getLastTransMonth().equals(riskCustomerTxnDataMergeInfo.getTxnMonth())) {
                riskLogacnttxnPaymentDetail.setMonthSumAmt(0l);
                riskLogacnttxnPaymentDetail.setMonthSumTimes(0l);
                riskLogacnttxnPaymentDetail.setLastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
            }
            riskLogacnttxnPaymentDetailMapper.updateByPrimaryKeySelective(riskLogacnttxnPaymentDetail);
            return;
        }
        

        riskLogacnttxnPaymentDetail.setDaySumAmt(riskLogacnttxnPaymentDetail.getDaySumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount() > 0 ?riskLogacnttxnPaymentDetail.getDaySumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount():0);
        riskLogacnttxnPaymentDetail.setDaySumTimes(riskLogacnttxnPaymentDetail.getDaySumTimes() - 1 > 0 ?riskLogacnttxnPaymentDetail.getDaySumTimes() - 1 :0);
        riskLogacnttxnPaymentDetail.setMonthSumAmt(riskLogacnttxnPaymentDetail.getMonthSumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount() > 0 ?riskLogacnttxnPaymentDetail.getMonthSumAmt() - riskCustomerTxnDataMergeInfo.getTxnAmount():0);
        riskLogacnttxnPaymentDetail.setMonthSumTimes(riskLogacnttxnPaymentDetail.getMonthSumTimes() - 1 > 0 ?riskLogacnttxnPaymentDetail.getMonthSumTimes() - 1:0);
        riskLogacnttxnPaymentDetail.setLastTransDate(riskCustomerTxnDataMergeInfo.getTxnDate());
        riskLogacnttxnPaymentDetail.setLastTransMonth(riskCustomerTxnDataMergeInfo.getTxnMonth());
        riskLogacnttxnPaymentDetailMapper.updateByPrimaryKeySelective(riskLogacnttxnPaymentDetail);
    }

}
