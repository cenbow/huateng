package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class TTransferOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4192237489664595361L;
    @Getter
    @Setter
	private String orderseq;
    @Getter
    @Setter
	private String payer;
    @Getter
    @Setter
	private Long amount;
    @Getter
    @Setter
	private String remark;
    @Getter
    @Setter
	private String flag;
    @Getter
    @Setter
	private String isSendMsg;
    @Getter
    @Setter
	private Date createTime;
    @Getter
    @Setter
	private  Date transactionTime;
    @Getter
    @Setter
	private  String blackFlag;
    @Getter
    @Setter
	private Long transferFee;

}
