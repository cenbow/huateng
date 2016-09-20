package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.huateng.p3.account.common.enummodel.TxnStatus;


/**
 * 产品属性欠费金额
 * <p/>
 * Created by IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-01-14
 */
@ToString
@Deprecated
public class ProductPropertyTxnStatus implements Serializable {

    private static final long serialVersionUID = -8014568563319610244L;
    /**
     * 欠费标示
     */
    @Getter
    @Setter
    private TxnStatus txnStatus = TxnStatus.TXN_LABL_NORMAL;
    /**
     * 欠费金额
     */
    @Getter
    @Setter
    private Long feeAmount  =0l ;


}
