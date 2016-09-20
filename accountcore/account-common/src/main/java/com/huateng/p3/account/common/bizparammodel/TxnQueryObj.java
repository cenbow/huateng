package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
public class TxnQueryObj  implements Serializable {

	
	public static final int chargeQueryType = 1;
	public static final int cashQueryType = 2;
	private static final long serialVersionUID = 5507019552779203041L;
	/**
	 * 查询类型
	 */
    @Getter
    @Setter
    private Integer queryType;
	/**
	 * 外部交易类型
	 */
    @Getter
    @Setter
    private String businessType;
    /**
	 * 内部交易类型
	 */
    @Getter
    @Setter
    private String txnType;
    /**
     * 交易渠道
     */
    @Getter
    @Setter
    private String txnChannel;
    /**
     * 交易流水
     */
    @Getter
    @Setter
    private String acceptTransSeqNo;
    /**
     * 出单机构
     */
    @Getter
    @Setter
    private String supplyOrgCode;
    
    /**
     * 商户名(查询时不需要填写)
     */
    @Getter
    @Setter
    private String merchantName;
    
    /**
     * 终端号
     */
    @Getter
    @Setter
    private String terminalNo;
    
    /**
     * 交易开始时间，查询历史记录时使用
     */
    @Getter
    @Setter
    private Date startDate;
    
    
    /**
     * 交易结束时间，查询历史记录时使用
     */
    @Getter
    @Setter
    private Date endDate;
    
    /**
     * 页数
     */
    @Getter
    @Setter
    private Integer page = 1;
    
    /**
     * 每页条数
     */
    @Getter
    @Setter
    private Integer pageSize = 20;

    
}
