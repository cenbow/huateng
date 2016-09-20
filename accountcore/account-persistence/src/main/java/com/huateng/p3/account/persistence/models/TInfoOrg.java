package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TInfoOrg implements Serializable {

	private static final long serialVersionUID = 8465154427874163053L;

	private Long recordNo;

    private String orgCode;

    private String orgType;

    private String subType;

    private String orgFname;

    private String orgSname;

    private String industryType;

    private String address;

    private String zipCode;

    private String businessLicenseNo;

    private String organizationCode;

    private String legalRepresentiveName;

    private String linkmanName;

    private String linkmanTelephone;

    private String linkmanFax;

    private String linkmanEmail;

    private String serviceLevel;

    private String riskLevel;

    private String allowTrans;

    private String orgStatus;

    private String areaCode;

    private String cityCode;

    private String upOrgCode;

    private String signOrgCode;

    private String clearingOrgCode;

    private String settlementOrgCode;

    private String settlementMode;

    private String isRealClear;

    private String clearingDate;

    private String settleDate;

    private String fLinkmanName;

    private String fLinkmanTelephone;

    private String fLinkmanFax;

    private String fLinkmanEmail;

    private String createUid;

    private Date createTime;

    private String checkFlag;

    private String checkUid;

    private Date checkTime;

    private String lastModifyUid;

    private Date lastModifyTime;

    private Long settlementMin1;

    private Long settlementMin2;

    private Long settlementMin3;

    private Long settlementMin4;

    private Long settlementMin5;

    private String chargeAccFlag;

    private String orgKind;

    private Date archiveTime;

    private String archiveFlag;

    private Long lRecordNo;

    private String remark;

    private String settleFlag;

    private String payType;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    
    public String getBlackLabel() {
        return blackLabel;
    }

    public void setBlackLabel(String blackLabel) {
        this.blackLabel = blackLabel;
    }

    // 黑名单标识
    private String blackLabel;
    
	public Long getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Long recordNo) {
		this.recordNo = recordNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getOrgFname() {
		return orgFname;
	}

	public void setOrgFname(String orgFname) {
		this.orgFname = orgFname;
	}

	public String getOrgSname() {
		return orgSname;
	}

	public void setOrgSname(String orgSname) {
		this.orgSname = orgSname;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getLegalRepresentiveName() {
		return legalRepresentiveName;
	}

	public void setLegalRepresentiveName(String legalRepresentiveName) {
		this.legalRepresentiveName = legalRepresentiveName;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanFax() {
		return linkmanFax;
	}

	public void setLinkmanFax(String linkmanFax) {
		this.linkmanFax = linkmanFax;
	}

	public String getLinkmanEmail() {
		return linkmanEmail;
	}

	public void setLinkmanEmail(String linkmanEmail) {
		this.linkmanEmail = linkmanEmail;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getAllowTrans() {
		return allowTrans;
	}

	public void setAllowTrans(String allowTrans) {
		this.allowTrans = allowTrans;
	}

	public String getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
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

	public String getUpOrgCode() {
		return upOrgCode;
	}

	public void setUpOrgCode(String upOrgCode) {
		this.upOrgCode = upOrgCode;
	}

	public String getSignOrgCode() {
		return signOrgCode;
	}

	public void setSignOrgCode(String signOrgCode) {
		this.signOrgCode = signOrgCode;
	}

	public String getClearingOrgCode() {
		return clearingOrgCode;
	}

	public void setClearingOrgCode(String clearingOrgCode) {
		this.clearingOrgCode = clearingOrgCode;
	}

	public String getSettlementOrgCode() {
		return settlementOrgCode;
	}

	public void setSettlementOrgCode(String settlementOrgCode) {
		this.settlementOrgCode = settlementOrgCode;
	}

	public String getSettlementMode() {
		return settlementMode;
	}

	public void setSettlementMode(String settlementMode) {
		this.settlementMode = settlementMode;
	}

	public String getIsRealClear() {
		return isRealClear;
	}

	public void setIsRealClear(String isRealClear) {
		this.isRealClear = isRealClear;
	}

	public String getClearingDate() {
		return clearingDate;
	}

	public void setClearingDate(String clearingDate) {
		this.clearingDate = clearingDate;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getfLinkmanName() {
		return fLinkmanName;
	}

	public void setfLinkmanName(String fLinkmanName) {
		this.fLinkmanName = fLinkmanName;
	}


	public String getfLinkmanFax() {
		return fLinkmanFax;
	}

	public void setfLinkmanFax(String fLinkmanFax) {
		this.fLinkmanFax = fLinkmanFax;
	}

	public String getfLinkmanEmail() {
		return fLinkmanEmail;
	}

	public void setfLinkmanEmail(String fLinkmanEmail) {
		this.fLinkmanEmail = fLinkmanEmail;
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

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getCheckUid() {
		return checkUid;
	}

	public void setCheckUid(String checkUid) {
		this.checkUid = checkUid;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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

	public Long getSettlementMin1() {
		return settlementMin1;
	}

	public void setSettlementMin1(Long settlementMin1) {
		this.settlementMin1 = settlementMin1;
	}

	public Long getSettlementMin2() {
		return settlementMin2;
	}

	public void setSettlementMin2(Long settlementMin2) {
		this.settlementMin2 = settlementMin2;
	}

	public Long getSettlementMin3() {
		return settlementMin3;
	}

	public void setSettlementMin3(Long settlementMin3) {
		this.settlementMin3 = settlementMin3;
	}

	public Long getSettlementMin4() {
		return settlementMin4;
	}

	public void setSettlementMin4(Long settlementMin4) {
		this.settlementMin4 = settlementMin4;
	}

	public Long getSettlementMin5() {
		return settlementMin5;
	}

	public void setSettlementMin5(Long settlementMin5) {
		this.settlementMin5 = settlementMin5;
	}

	public String getChargeAccFlag() {
		return chargeAccFlag;
	}

	public void setChargeAccFlag(String chargeAccFlag) {
		this.chargeAccFlag = chargeAccFlag;
	}

	public String getOrgKind() {
		return orgKind;
	}

	public void setOrgKind(String orgKind) {
		this.orgKind = orgKind;
	}

	public Date getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	public String getArchiveFlag() {
		return archiveFlag;
	}

	public void setArchiveFlag(String archiveFlag) {
		this.archiveFlag = archiveFlag;
	}

	public Long getlRecordNo() {
		return lRecordNo;
	}

	public void setlRecordNo(Long lRecordNo) {
		this.lRecordNo = lRecordNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public String getLinkmanTelephone() {
		return linkmanTelephone;
	}

	public void setLinkmanTelephone(String linkmanTelephone) {
		this.linkmanTelephone = linkmanTelephone;
	}

	public String getfLinkmanTelephone() {
		return fLinkmanTelephone;
	}

	public void setfLinkmanTelephone(String fLinkmanTelephone) {
		this.fLinkmanTelephone = fLinkmanTelephone;
	}
	 
}