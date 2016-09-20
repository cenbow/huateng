package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.TxnType;

/**
 * User: JamesTang
 * Date: 13-12-13
 * Time: 下午1:49
 */
@ToString
public class RiskCustomerTxnDataMergeInfo implements Serializable {

    private static final long serialVersionUID = 2488674859319414452L;

    /**
     * 交易大类
     */
    @Setter
    @Getter
    private TxnType txnType;


    /**
     * 账户类型
     */
    @Setter
    @Getter
    private AccountType accountType;
    /**
     * 交易渠道
     */
    @Setter
    @Getter
    private String txnChannel;
    /**
     * 账户号
     */
    @Setter
    @Getter
    private String accountNo;

    /**
     * 客户级别
     */
    @Setter
    @Getter
    private String customerGrade;


    /**
     * 交易类型
     */

    @Setter
    @Getter
    private String innerTxnType;
    
    
    /**
     * 外部交易类型
     */

    @Setter
    @Getter
    private String bussinessType;
 
    
    /**
     * 原交易类型
     */
    @Setter
    @Getter
    private String oldInnerTxnType;
    
    
    /**
     * 原外部交易类型
     */
    @Setter
    @Getter
    private String oldBussinessType;
    
    /**
     * 交易金额
     */
    @Setter
    @Getter
    private Long txnAmount;


    /**
     * 交易日 （8位）
     */
    @Setter
    @Getter
    private String txnDate;


    /**
     * 交易月
     */
    @Setter
    @Getter
    private String txnMonth;
}