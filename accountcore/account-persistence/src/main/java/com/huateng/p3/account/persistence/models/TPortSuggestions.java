package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: WYH
 * Date: 14-10-16
 * Time: 下午12:35
 * To change this template use File | Settings | File Templates.
 */
public class TPortSuggestions implements Serializable {

    private String id;

    private String orderNo;

    private String context;

    private String name;

    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
