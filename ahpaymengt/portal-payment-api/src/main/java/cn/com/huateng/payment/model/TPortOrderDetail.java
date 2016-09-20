package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;



/**
 * 订单总表
 *
 * @author huwenjie
 */
@ToString
public class TPortOrderDetail implements Serializable{

	private static final long serialVersionUID = 754279571020164141L;

    @Getter
    @Setter
    private String feeType;

	
	
}
