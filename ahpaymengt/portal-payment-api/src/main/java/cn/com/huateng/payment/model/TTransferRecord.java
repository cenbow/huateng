package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class TTransferRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    @Getter
    @Setter
	private String orderseq;
    @Getter
    @Setter
	private String payee;
    @Getter
    @Setter
	private Long amount;
    @Getter
    @Setter
	private String remark1;
    @Getter
    @Setter
	private String  remark2;
    @Getter
    @Setter
	private  String remark3;
    @Getter
    @Setter
	private Long  remark4;
    @Getter
    @Setter
	private Date remark5;
    @Getter
    @Setter
	private String id;


}
