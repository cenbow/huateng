package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

public class TInfoAccountenterpriseOrg implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2457817094196675115L;

	private String orgCode;

    private String mobilePhone;

    private String emailAddress;

    private String enterpriseCustomerno;

    private String productNo;

    private String orgSname;

    private String bindstatus;

    private Date openTime;

    private Date closeTime;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEnterpriseCustomerno() {
        return enterpriseCustomerno;
    }

    public void setEnterpriseCustomerno(String enterpriseCustomerno) {
        this.enterpriseCustomerno = enterpriseCustomerno;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getOrgSname() {
        return orgSname;
    }

    public void setOrgSname(String orgSname) {
        this.orgSname = orgSname;
    }

    public String getBindstatus() {
        return bindstatus;
    }

    public void setBindstatus(String bindstatus) {
        this.bindstatus = bindstatus;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }
}