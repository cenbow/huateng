package com.huateng.p3.hub.accountcore.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 月票
 * User: dongpeiji
 * Date: 14-9-22
 * Time: 下午2:14
 */
public class MonthTicketObject implements Serializable {
    //产品id
    @Setter
    @Getter
    private String id;

    //月票号
    @Getter
    @Setter
    private String[] tickets;

    //路段集   type 2 停车场路段
    @Getter
    @Setter
    private String[] roads_name;

    //起始区域名称   type 2 停车场区域
    @Getter
    @Setter
    private String beginArea_name;

    //结束区域名称
    @Getter
    @Setter
    private String endArea_name;

    //月票类型  1.高速 2.停车场
    @Setter
    @Getter
    private String type;

    //月票名称
    @Setter
    @Getter
    private String name;

    //备注
    @Getter
    @Setter
    private String remark;

    //图片地址
    @Getter
    @Setter
    private String imagePath;

    //内容
    @Getter
    @Setter
    private String context;

    @Getter
    @Setter
    private Date createTime;

    //价格
    @Getter
    @Setter
    private BigDecimal price;

}
