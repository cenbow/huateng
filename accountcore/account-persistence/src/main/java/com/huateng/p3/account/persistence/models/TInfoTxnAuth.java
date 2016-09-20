package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

public class TInfoTxnAuth implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -932741320199519127L;

	private String seqNo;

    private String txnType;

    private String areaCode;

    private String cityCode;

    private String diffNetworkStatus;

    private String homeNetworkStatus;

    private String createUid;

    private String createTime;

    private String lastModifyUid;

    private String lastModifyTime;

    private String checkUid;

    private String checkTime;

    private String checkFlag;

    private String archiveUid;

    private String archiveTime;

    private String archiveFlag;

    private String remark;

    private String lSeqNo;

    private String userGrade;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDiffNetworkStatus() {
        return diffNetworkStatus;
    }

    public void setDiffNetworkStatus(String diffNetworkStatus) {
        this.diffNetworkStatus = diffNetworkStatus;
    }

    public String getHomeNetworkStatus() {
        return homeNetworkStatus;
    }

    public void setHomeNetworkStatus(String homeNetworkStatus) {
        this.homeNetworkStatus = homeNetworkStatus;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyUid() {
        return lastModifyUid;
    }

    public void setLastModifyUid(String lastModifyUid) {
        this.lastModifyUid = lastModifyUid;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getCheckUid() {
        return checkUid;
    }

    public void setCheckUid(String checkUid) {
        this.checkUid = checkUid;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getArchiveUid() {
        return archiveUid;
    }

    public void setArchiveUid(String archiveUid) {
        this.archiveUid = archiveUid;
    }

    public String getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(String archiveTime) {
        this.archiveTime = archiveTime;
    }

    public String getArchiveFlag() {
        return archiveFlag;
    }

    public void setArchiveFlag(String archiveFlag) {
        this.archiveFlag = archiveFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getlSeqNo() {
        return lSeqNo;
    }

    public void setlSeqNo(String lSeqNo) {
        this.lSeqNo = lSeqNo;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }
}