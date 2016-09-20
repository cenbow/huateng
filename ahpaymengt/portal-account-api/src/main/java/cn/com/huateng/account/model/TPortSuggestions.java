package cn.com.huateng.account.model;

import java.io.Serializable;
import java.util.Date;

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

    private String unifyId;

    private Date createTime;

    private Date startDate;

    private Date endDate;

    private int StartIndex;

    private int endIndex;

    public int getStartIndex() {
        return StartIndex;
    }

    public void setStartIndex(int startIndex) {
        StartIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnifyId() {
        return unifyId;
    }

    public void setUnifyId(String unifyId) {
        this.unifyId = unifyId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
