package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TDictOuterTxnCode implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3720765135751968392L;

	private String extTransCode;

    private String extTransName;

    private String transMemo;

    private String archievdFlag;

    private String createDate;

    private String createTime;

    private String createUid;

    private String updateDate;

    private String updateTime;

    private String updateUid;

    private String interiorTransCode;

    private String enableFlag;
    private String  lastModifyUid;
    private String lastModifyTime;
    private String archiveTime;
    private String archivedFlag;
    private String lRecordNo;
    private String remark;
    private String resvFld1;
    private String resvFld2;
    private String investmark;
	public String getExtTransCode() {
		return extTransCode;
	}
	public void setExtTransCode(String extTransCode) {
		this.extTransCode = extTransCode;
	}
	public String getExtTransName() {
		return extTransName;
	}
	public void setExtTransName(String extTransName) {
		this.extTransName = extTransName;
	}
	public String getTransMemo() {
		return transMemo;
	}
	public void setTransMemo(String transMemo) {
		this.transMemo = transMemo;
	}
	public String getArchievdFlag() {
		return archievdFlag;
	}
	public void setArchievdFlag(String archievdFlag) {
		this.archievdFlag = archievdFlag;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	public String getInteriorTransCode() {
		return interiorTransCode;
	}
	public void setInteriorTransCode(String interiorTransCode) {
		this.interiorTransCode = interiorTransCode;
	}
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
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
	public String getArchiveTime() {
		return archiveTime;
	}
	public void setArchiveTime(String archiveTime) {
		this.archiveTime = archiveTime;
	}
	public String getArchivedFlag() {
		return archivedFlag;
	}
	public void setArchivedFlag(String archivedFlag) {
		this.archivedFlag = archivedFlag;
	}
	public String getlRecordNo() {
		return lRecordNo;
	}
	public void setlRecordNo(String lRecordNo) {
		this.lRecordNo = lRecordNo;
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
	public String getInvestmark() {
		return investmark;
	}
	public void setInvestmark(String investmark) {
		this.investmark = investmark;
	}

 
}