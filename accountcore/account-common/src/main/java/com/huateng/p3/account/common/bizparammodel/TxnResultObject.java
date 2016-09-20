package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.google.common.base.Objects;

/**
 * User: JamesTang
 * Date: 13-12-23
 * Time: 下午6:10
 */

public class TxnResultObject implements Serializable {
   
	private static final long serialVersionUID = 26568454911003213L;

	/**
     * 翼支付资金账户
     */
    @Getter
    @Setter
    private String fundAccountNo;

    

    /**
     * 目标帐号（转账交易有用）
     */
    @Getter
    @Setter
    private String targetAccountNo;


    /**
     * 客户号
     */
    @Getter
    @Setter
    private String customerNo;


    /**
     * 目标客户号（转账交易有用）
     * 产品号
     */
    @Getter
    @Setter
    private String targetCustomerNo;

    /**
     * 交易流水
     */
    @Getter
    @Setter
    private String txnSeqNo;
    
    /**
     * 原交易流水 （退货有用）
     */
    @Getter
    @Setter
    private String oldTxnSeqNo;
    
    /**
     * 目标交易流水（转账交易有用）
     */
    @Getter
    @Setter
    private String targetTxnSeqNo;
    
    /**
     * 交易金额，理财的金额，预授权完成时，实际交易金额非外部传入金额。
     */
    @Getter
    @Setter
    private Long txnAmount;
    
    /**
     * 账户可用金额
     */
    @Getter
    @Setter
    private Long availableBalance;
   
    /**
    * 账户总金额
    */
    @Getter
    @Setter
    private Long balance;
    
    /**
    * 账户可提现金额
    */
    @Getter
    @Setter
    private Long withdrawBalance;
    
    /**
     * 手续费金额
     */
    @Getter
    @Setter
    private Long feeTxnAmount;
    
    /**
     * 手续费流水
     */
    @Getter
    @Setter
    private String feeTxnSeqNo;
    
    

    /**
     * 外部交易类型
     * （确定业务 对应到基金到底是申购还是赎回，还是提现。还是信用支付等）
     */
    @Getter
    @Setter
    private String outType;
    
    
    /**
     * 手续费外部交易类型
     */
    @Getter
    @Setter
    private String feeOutType;
    
    /**
     * 内部交易类型
     */
    @Getter
    @Setter
    private String innerType;
    
    
    /**
     * 手续费内部交易类型
     */
    @Getter
    @Setter
    private String feeInnerType;
    
    /**
     * 外部交易类型（转账圈存交易有用）
     * （确定业务 对应到基金到底是申购还是赎回，还是提现。还是信用支付等）
     */
    @Getter
    @Setter
    private String targetOutType;
    /**
     * 内部交易类型（转账圈存交易有用）
     */
    @Getter
    @Setter
    private String targetInnerType;
    
    /**
     * 目标账户可用金额
     */
    @Getter
    @Setter
    private Long targetAvailableBalance;
   
    /**
    * 目标账户总金额
    */
    @Getter
    @Setter
    private Long targetBalance;
    
    /**
    * 目标账户可提现金额
    */
    @Getter
    @Setter
    private Long targetWithdrawBalance;

    @Getter
    @Setter
    private Date txnDate;
    
    /**
     * 清算日期
     */
    @Getter
    @Setter
    private String currDate;

    @Getter
    @Setter
    private boolean isRollbackFake = false;
    
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("fundAccountNo", fundAccountNo)
                .add("targetAccountNo", targetAccountNo)
				.add("customerNo", customerNo)
				.add("targetCustomerNo", targetCustomerNo)
				.add("txnSeqNo", txnSeqNo)
				.add("oldTxnSeqNo", oldTxnSeqNo)
				.add("targetTxnSeqNo", targetTxnSeqNo)
				.add("txnAmount", txnAmount)
				.add("availableBalance", availableBalance)
				.add("balance", balance)
				.add("withdrawBalance", withdrawBalance)
				.add("feeTxnAmount", feeTxnAmount)
				.add("feeTxnSeqNo", feeTxnSeqNo)
				.add("outType", outType)
				.add("feeOutType", feeOutType)
				.add("innerType", innerType)
				.add("feeInnerType", feeInnerType)
				.add("targetOutType", targetOutType)
				.add("targetInnerType", targetInnerType)
				.add("targetAvailableBalance", targetAvailableBalance)
				.add("targetBalance", targetBalance)
				.add("targetWithdrawBalance", targetWithdrawBalance)
				.add("txnDate", txnDate)
				.add("currDate", currDate)
				.add("isRollbackFake", isRollbackFake )
                .omitNullValues()
                .toString();
    }
}
