package com.huateng.p3.account.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.persistence.TBatCutCtlMapper;
import com.huateng.p3.account.persistence.models.TBatCutCtl;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TLogOfflinePayment;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;

/**
 * User: JamesTang
 * Date: 13-12-23
 * Time: 下午6:33
 */
@Component
public class TxnResultGenComponent {
	
	@Autowired
    private TBatCutCtlMapper tBatCutCtlMapper;

    public TxnResultObject genTxnResultObject(PaymentInfo paymentInfo,TLogOnlinePayment tFeeLogOnlinePayment, TInfoAccount tInfoAccount,
                                              TInfoCustomer tInfoCustomer, String txnSeqNo ,String oldTxnSeqNo, boolean... isRollbackFake) {
        TxnResultObject txnResultObject = new TxnResultObject();
        txnResultObject.setFundAccountNo( tInfoAccount != null && tInfoAccount.getType().equals(AccountType.FUND.getValue()) ? tInfoAccount.getAccountNo() : null );
        txnResultObject.setAvailableBalance(tInfoAccount != null ? tInfoAccount.getAvailableBalance() : null);
        txnResultObject.setBalance(tInfoAccount != null ? tInfoAccount.getBalance() : null);
        txnResultObject.setWithdrawBalance(tInfoAccount != null ? tInfoAccount.getWithdrawBalance() : null);
        txnResultObject.setInnerType(paymentInfo.getInnerTxnType());
        txnResultObject.setOutType(paymentInfo.getBussinessType());
        txnResultObject.setCustomerNo(tInfoCustomer != null ? tInfoCustomer.getCustomerNo() : null  );
        txnResultObject.setTxnAmount(paymentInfo.getAmount());
        txnResultObject.setTxnSeqNo(txnSeqNo);
        txnResultObject.setOldTxnSeqNo(oldTxnSeqNo);
        txnResultObject.setTxnDate(paymentInfo.getTxnDate());
        if (isRollbackFake.length > 0 && isRollbackFake[0]) {
        	txnResultObject.setRollbackFake(isRollbackFake[0]);
        }
        if(tFeeLogOnlinePayment != null){
	        txnResultObject.setFeeTxnAmount(tFeeLogOnlinePayment.getTransAmount());
	        txnResultObject.setFeeTxnSeqNo(tFeeLogOnlinePayment.getTransSerialNo());
	        txnResultObject.setFeeOutType(tFeeLogOnlinePayment.getExtBusinessType());
	        txnResultObject.setFeeInnerType(tFeeLogOnlinePayment.getInteriorTransType());
        }
        //返回清算日期
        TBatCutCtl tBatCutCtl=tBatCutCtlMapper.selectByPrimaryKey(1);
        txnResultObject.setCurrDate(tBatCutCtl.getCurrDate());
        return txnResultObject;
    }
    //预授权完成时，理财需要实际申购的金额为原始预授权金额减去预授权完成金额
    public TxnResultObject genTxnResultObjectForPreAuth(PaymentInfo paymentInfo,TLogOnlinePayment tFeeLogOnlinePayment, TInfoAccount tInfoAccount,
            TInfoCustomer tInfoCustomer, String txnSeqNo ,String oldTxnSeqNo, TLogPreauthApply oldPreauthApply ,boolean... isRollbackFake) {
    	TxnResultObject txnResultObject = genTxnResultObject(paymentInfo, tFeeLogOnlinePayment, tInfoAccount, tInfoCustomer, txnSeqNo, oldTxnSeqNo, isRollbackFake);
    	txnResultObject.setTxnAmount(oldPreauthApply.getAuthAmt() - paymentInfo.getAmount());
    	return txnResultObject;
    	
    }
    
    
    public TxnResultObject genTxnResultObjectForTransfer(PaymentInfo paymentInfo, PaymentInfo targetPaymentInfo,TInfoAccount tInfoAccount,TInfoAccount tTargetInfoAccount ,
            TInfoCustomer tInfoCustomer, TInfoCustomer tTargetInfoCustomer, String txnSeqNo ,String targetTxnSeqNo) {
		TxnResultObject txnResultObject = new TxnResultObject();
		txnResultObject.setFundAccountNo(tInfoAccount.getAccountNo());
		txnResultObject.setAvailableBalance(tInfoAccount.getAvailableBalance());
        txnResultObject.setBalance(tInfoAccount.getBalance());
        txnResultObject.setWithdrawBalance(tInfoAccount.getWithdrawBalance());
		txnResultObject.setTargetAvailableBalance(tTargetInfoAccount.getAvailableBalance());
        txnResultObject.setTargetBalance(tTargetInfoAccount.getBalance());
        txnResultObject.setTargetWithdrawBalance(tTargetInfoAccount.getWithdrawBalance());
		txnResultObject.setTargetAccountNo(tTargetInfoAccount.getAccountNo());		
		txnResultObject.setInnerType(paymentInfo.getInnerTxnType());
		txnResultObject.setTargetInnerType(targetPaymentInfo.getInnerTxnType());
		txnResultObject.setOutType(paymentInfo.getBussinessType());		
		txnResultObject.setTargetOutType(targetPaymentInfo.getBussinessType());
		txnResultObject.setCustomerNo(tInfoCustomer.getCustomerNo());
		txnResultObject.setTargetCustomerNo(tTargetInfoCustomer != null ? tTargetInfoCustomer.getCustomerNo() : null);
		txnResultObject.setTxnAmount(paymentInfo.getAmount());
		txnResultObject.setTxnSeqNo(txnSeqNo);
		txnResultObject.setTargetTxnSeqNo(targetTxnSeqNo);
		txnResultObject.setTxnDate(paymentInfo.getTxnDate());
        //返回清算日期
        TBatCutCtl tBatCutCtl=tBatCutCtlMapper.selectByPrimaryKey(1);
        txnResultObject.setCurrDate(tBatCutCtl.getCurrDate());
		return txnResultObject;
    }

    public TxnResultObject getIntegralTxnResultObject(PaymentInfo paymentInfo,TInfoAccount tInfoAccount,
                                                      TInfoCustomer tInfoCustomer, String txnSeqNo ,String oldTxnSeqNo, boolean... isRollbackFake){
        TxnResultObject txnResultObject = new TxnResultObject();
        txnResultObject.setFundAccountNo( tInfoAccount != null ? tInfoAccount.getAccountNo() : null );
        txnResultObject.setAvailableBalance(tInfoAccount != null ? tInfoAccount.getAvailableBalance() : null);
        txnResultObject.setBalance(tInfoAccount != null ? tInfoAccount.getBalance() : null);
        txnResultObject.setWithdrawBalance(tInfoAccount != null ? tInfoAccount.getWithdrawBalance() : null);
        txnResultObject.setInnerType(paymentInfo.getInnerTxnType());
        txnResultObject.setOutType(paymentInfo.getBussinessType());
        txnResultObject.setCustomerNo(tInfoCustomer != null ? tInfoCustomer.getCustomerNo() : null  );
        txnResultObject.setTxnAmount(paymentInfo.getAmount());
        txnResultObject.setTxnSeqNo(txnSeqNo);
        txnResultObject.setOldTxnSeqNo(oldTxnSeqNo);
        txnResultObject.setTxnDate(paymentInfo.getTxnDate());
        if (isRollbackFake.length > 0 && isRollbackFake[0]) {
            txnResultObject.setRollbackFake(isRollbackFake[0]);
        }
        return txnResultObject;
    }
    
    public TxnResultObject genOfflineTxnResultObject(PaymentInfo paymentInfo,TLogOfflinePayment tFeeLogOfflinePayment, TInfoAccount tInfoAccount,
            TInfoCustomer tInfoCustomer, String txnSeqNo ,String oldTxnSeqNo, boolean... isRollbackFake) {
		TxnResultObject txnResultObject = new TxnResultObject();
		txnResultObject.setFundAccountNo( tInfoAccount != null ? tInfoAccount.getAccountNo() : null );
		txnResultObject.setAvailableBalance(tInfoAccount != null ? tInfoAccount.getAvailableBalance() : null);
		txnResultObject.setBalance(tInfoAccount != null ? tInfoAccount.getBalance() : null);
		txnResultObject.setWithdrawBalance(tInfoAccount != null ? tInfoAccount.getWithdrawBalance() : null);
		txnResultObject.setInnerType(paymentInfo.getInnerTxnType());
		txnResultObject.setOutType(paymentInfo.getBussinessType());
		txnResultObject.setCustomerNo(tInfoCustomer != null ? tInfoCustomer.getCustomerNo() : null  );
		txnResultObject.setTxnAmount(paymentInfo.getAmount());
		txnResultObject.setTxnSeqNo(txnSeqNo);
		txnResultObject.setOldTxnSeqNo(oldTxnSeqNo);
		txnResultObject.setTxnDate(paymentInfo.getTxnDate());
		if (isRollbackFake.length > 0 && isRollbackFake[0]) {
		txnResultObject.setRollbackFake(isRollbackFake[0]);
		}
		if(tFeeLogOfflinePayment != null){
		txnResultObject.setFeeTxnAmount(tFeeLogOfflinePayment.getTxnAmt());
		txnResultObject.setFeeTxnSeqNo(tFeeLogOfflinePayment.getTxnSeqNo());
		txnResultObject.setFeeOutType(tFeeLogOfflinePayment.getBusinessType());
		txnResultObject.setFeeInnerType(tFeeLogOfflinePayment.getTxnType());
		}
		//返回清算日期
		TBatCutCtl tBatCutCtl=tBatCutCtlMapper.selectByPrimaryKey(1);
		txnResultObject.setCurrDate(tBatCutCtl.getCurrDate());
		return txnResultObject;
	}
}
