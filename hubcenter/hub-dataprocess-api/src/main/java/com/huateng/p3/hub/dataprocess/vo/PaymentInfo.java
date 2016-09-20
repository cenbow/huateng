package com.huateng.p3.hub.dataprocess.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.google.common.base.Objects;
import com.huateng.p3.hub.common.model.TxnSeqType;
import com.huateng.p3.hub.common.model.TxnType;


/**
 * 清结算等记日志相关的参数
 * @author cmt
 *
 */
public class PaymentInfo implements Serializable {

	private static final long serialVersionUID = 9071622324405362569L;


	/**
     * 商户号，加密密码时获取密钥使用
     */
    @Getter
    @Setter
    private String merchantCode;
    
    
    /**
     * 出单机构
     */
    @Getter
    @Setter
    private String supplyOrgCode;
    
    /**
     * 渠道
     */
    @Getter
    @Setter
    private String channel;

    /**
     * 外部交易类型
     */
    @Getter
    @Setter
    private String bussinessType;

    /**
     * 内部交易类型   （外部无需填写 ，填了也白填）
     */
    @Getter
    @Setter
    private String innerTxnType;

    /**
     * 交易金额
     */
    @Getter
    @Setter
    private Long amount;
    /**
     * 终端号，只在渠道为POS时才有效，其他填写受理机构前8位，加密密码时获取密钥使用
     */
    @Getter
    @Setter
    private String terminalNo;
    /**
     * 终端流水号，只在渠道为POS时才有效
     */
    @Getter
    @Setter
    private String terminalSeqNo;
    /**
     * 受理机构代码
     */
    @Getter
    @Setter
    private String acceptOrgCode;
    /**
     * 受理交易流水  非常重要  add by James Tang
     */
    @Getter
    @Setter
    private String acceptTxnSeqNo;
    /**
     * 受理人编号
     */
    @Getter
    @Setter
    private String acceptOperatorNo;
    /**
     * 受理日期
     */
    @Getter
    @Setter
    private String acceptDate;

    /**
     * 受理时间
     */
    @Getter
    @Setter
    private String acceptTime;
    /**
     * 支付机构代码
     */
    @Getter
    @Setter
    private String payOrgCode;

    /**
     * 交易类型 外部不需要填写
     */
    @Getter
    @Setter
    private TxnType txnType;

    /**
     * 交易描述
     */
    @Getter
    @Setter
    private String txnDscpt;

    /**
     * 交易流水的类型  外部不需要填写   默认是交易类型的流水
     */

    @Getter
    @Setter
    private TxnSeqType txnSeqType = TxnSeqType.TRANS_SEQ_TYPE_NORMAL;

    /**
     *       交易时间    外部不需要填写     是核心服务时间
     *       */
    @Getter
    @Setter
    private Date txnDate;
    
    
    /**
     *       退货时使用，外部不需要填写
     *       */
    @Getter
    @Setter
    private String payFeeFlag;
    
    /**
     *       退货时使用，外部不需要填写
     *       */
    @Getter
    @Setter
    private String supplyFeeFlag;
    
    /**
     * 前端商户(网关前端商户,用来发送短信内容用)
     */
    @Getter
    @Setter
    private String frontOrgCode;
    
    /**
     * 卡TAC(脱机交易用)
     */
    @Getter
    @Setter
    private String cardTAC;
    
    /**
     * 卡次数(脱机交易用)
     */
    @Getter
    @Setter
    private String cardCnt;
    
    /**
     * 是否发送短信(默认为发送 设置为fasle则不发送)
     */
    @Getter
    @Setter
    private boolean isSmsSend = true;
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("merchantCode", merchantCode)
                .add("supplyOrgCode", supplyOrgCode)
                .add("channel", channel)
				.add("bussinessType", bussinessType)
				.add("innerTxnType", innerTxnType)
				.add("amount", amount)
				.add("terminalNo", terminalNo)
				.add("terminalSeqNo", terminalSeqNo)
				.add("acceptOrgCode", acceptOrgCode)
				.add("acceptTxnSeqNo", acceptTxnSeqNo)
				.add("acceptOperatorNo", acceptOperatorNo)
				.add("acceptDate", acceptDate)
				.add("acceptTime", acceptTime)
				.add("payOrgCode", payOrgCode)
				.add("txnType", txnType)
				.add("txnDscpt", txnDscpt)
				.add("txnSeqType", txnSeqType)
				.add("txnDate", txnDate)
				.add("payFeeFlag", payFeeFlag)
				.add("supplyFeeFlag", supplyFeeFlag)
				.add("frontOrgCode", frontOrgCode)
				.add("cardTAC", cardTAC)
				.add("cardCnt", cardCnt)
				.add("isSmsSend", isSmsSend)				
                .omitNullValues()
                .toString();
    }

}
