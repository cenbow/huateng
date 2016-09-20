package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.huateng.p3.account.common.enummodel.OrgType;

/**
 * User: JamesTang
 * Date: 13-12-13
 * Time: 下午1:49
 */
@ToString
public class RiskOrgTxnDataMergeInfo implements Serializable {

	private static final long serialVersionUID = -696242487423460265L;

	/**
     * 交易渠道
     */
    @Getter
    @Setter
    private String txnChannel;

    /**
     * 交易金额
     */
    @Getter
    @Setter
    private Long txnAmount;
    /**
     * 机构类型
     */
    @Getter
    @Setter
    private OrgType orgType;
    /**
     * 机构编码
     */
    @Getter
    @Setter
    private String orgCode;

    /**
     * 终端号
     */
    @Getter
    @Setter
    private String terminalNo;

    /**
     * 内部交易类型
     */
    @Getter
    @Setter
    private String innerTxnType;


}
