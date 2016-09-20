package com.huateng.p3.hub.accountcore.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存对象
 * User: 董培基
 * Date: 14-12-3
 * Time: 上午11:23
 */
public class GoodsStock  implements Serializable {

    @Getter
    @Setter
    private String relationId;

    @Getter
    @Setter
    private String stockId;

    @Getter
    @Setter
    private Date inTime;

    @Getter
    @Setter
    private String inOperator;

    @Getter
    @Setter
    private Date outTime;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String txnChannel;
}
