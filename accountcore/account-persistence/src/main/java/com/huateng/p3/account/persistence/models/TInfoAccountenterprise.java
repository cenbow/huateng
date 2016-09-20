package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

public class TInfoAccountenterprise implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9010753997167419733L;

	private String enterpriseCustomerno;

    private String orgSname;

    private String enterpriseAccountlevel;

    private String productNo;

    private String grade;

    private String type;

    private String mobilePhone;

    private String emailAddress;

    private String pinkey;

    private String ivrPinkey;

    private String queryPinkey;

    private String officePhone;

    private String otherPhone;

    private Date lockTimeLimit;

    private String question;

    private String answer;

    private String activeAddress;

    private String activeCode;

    private String activeStatus;

    private Date registerTime;

    private String isClosed;

    private String acceptOrgCode;

    private String acceptUid;

    private String manager;

    private Date lastSuccessLoginTime;

    private String lastSuccessLoginIp;

    private Date lastFailLoginTime;

    private String lastFailLoginIp;

    private Date lastUpdateTime;

    private String status;

    private String cityCode;

    private String areaCode;

    private Date closeTime;

    private Date activeTimeLimit;

    private Date activeTime;

    private String createUid;

    private Date createTime;

    private String lastModifyUid;

    private Date lastModifyTime;

    private String remark;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String notifyamttime;

    private Short notifyamt;

    public String getEnterpriseCustomerno() {
        return enterpriseCustomerno;
    }

    public void setEnterpriseCustomerno(String enterpriseCustomerno) {
        this.enterpriseCustomerno = enterpriseCustomerno;
    }

    public String getOrgSname() {
        return orgSname;
    }

    public void setOrgSname(String orgSname) {
        this.orgSname = orgSname;
    }

    public String getEnterpriseAccountlevel() {
        return enterpriseAccountlevel;
    }

    public void setEnterpriseAccountlevel(String enterpriseAccountlevel) {
        this.enterpriseAccountlevel = enterpriseAccountlevel;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPinkey() {
        return pinkey;
    }

    public void setPinkey(String pinkey) {
        this.pinkey = pinkey;
    }

    public String getIvrPinkey() {
        return ivrPinkey;
    }

    public void setIvrPinkey(String ivrPinkey) {
        this.ivrPinkey = ivrPinkey;
    }

    public String getQueryPinkey() {
        return queryPinkey;
    }

    public void setQueryPinkey(String queryPinkey) {
        this.queryPinkey = queryPinkey;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public Date getLockTimeLimit() {
        return lockTimeLimit;
    }

    public void setLockTimeLimit(Date lockTimeLimit) {
        this.lockTimeLimit = lockTimeLimit;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getActiveAddress() {
        return activeAddress;
    }

    public void setActiveAddress(String activeAddress) {
        this.activeAddress = activeAddress;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public String getAcceptOrgCode() {
        return acceptOrgCode;
    }

    public void setAcceptOrgCode(String acceptOrgCode) {
        this.acceptOrgCode = acceptOrgCode;
    }

    public String getAcceptUid() {
        return acceptUid;
    }

    public void setAcceptUid(String acceptUid) {
        this.acceptUid = acceptUid;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getLastSuccessLoginTime() {
        return lastSuccessLoginTime;
    }

    public void setLastSuccessLoginTime(Date lastSuccessLoginTime) {
        this.lastSuccessLoginTime = lastSuccessLoginTime;
    }

    public String getLastSuccessLoginIp() {
        return lastSuccessLoginIp;
    }

    public void setLastSuccessLoginIp(String lastSuccessLoginIp) {
        this.lastSuccessLoginIp = lastSuccessLoginIp;
    }

    public Date getLastFailLoginTime() {
        return lastFailLoginTime;
    }

    public void setLastFailLoginTime(Date lastFailLoginTime) {
        this.lastFailLoginTime = lastFailLoginTime;
    }

    public String getLastFailLoginIp() {
        return lastFailLoginIp;
    }

    public void setLastFailLoginIp(String lastFailLoginIp) {
        this.lastFailLoginIp = lastFailLoginIp;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getActiveTimeLimit() {
        return activeTimeLimit;
    }

    public void setActiveTimeLimit(Date activeTimeLimit) {
        this.activeTimeLimit = activeTimeLimit;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyUid() {
        return lastModifyUid;
    }

    public void setLastModifyUid(String lastModifyUid) {
        this.lastModifyUid = lastModifyUid;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResvFld1() {
        return resvFld1;
    }

    public void setResvFld1(String resvFld1) {
        this.resvFld1 = resvFld1;
    }

    public String getResvFld2() {
        return resvFld2;
    }

    public void setResvFld2(String resvFld2) {
        this.resvFld2 = resvFld2;
    }

    public String getResvFld3() {
        return resvFld3;
    }

    public void setResvFld3(String resvFld3) {
        this.resvFld3 = resvFld3;
    }

    public String getNotifyamttime() {
        return notifyamttime;
    }

    public void setNotifyamttime(String notifyamttime) {
        this.notifyamttime = notifyamttime;
    }

    public Short getNotifyamt() {
        return notifyamt;
    }

    public void setNotifyamt(Short notifyamt) {
        this.notifyamt = notifyamt;
    }
}