package cn.com.huateng.account.model;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString(exclude = { "initPinKey", "question", "answer", "identifyNo" })
public class TInfoCustomer implements Serializable {
	/**
	 * 客户编号，不填
	 */
	private String customerNo;
	/**
	 * 统一帐号，不填
	 */
	private String unifyId;
	/**
	 * 是否实名，开户选填
	 */
	private String isRealname;
	/**
	 * 姓名，开户必填
	 */
	private String name;
	/**
	 * 是否申请证书，不填
	 */
	private String isRequestCertificate;
	/**
	 * 注册类型 开户必填
	 */
	private String registerType;
	/**
	 * 联系手机 开户必填
	 */
	private String mobileNo;
	/**
	 * 电子邮件地址 信息同步选填
	 */
	private String emailAddress;

    /**
     * 登录密码
     */
    private String pinKey;
	/**
	 * 用户名 不填
	 */
	private String userName;
	/**
	 * 昵称 开户选填
	 */
	private String nickName;
	/**
	 * 网站登录密码 不填
	 */
	private String webLoginPassword;
	/**
	 * IVR密码 不填
	 */
	private String ivrPassword;
	/**
	 * 账户查询密码 不填
	 */
	private String accountQueryPassword;
	/**
	 * 性别 开户必填、信息同步选填
	 */
	private String gender;
	/**
	 * 证件类型 开户必填、信息同步选填
	 */
	private String identifyType;
	/**
	 * 证件号码 开户必填、信息同步选填
	 */
	private String identifyNo;
	/**
	 * 家庭电话 信息同步选填
	 */
	private String homeTelephone;
	/**
	 * 办公电话 信息同步选填
	 */
	private String officeTelephone;
	/**
	 * 其他联系人电话 信息同步选填
	 */
	private String otherTelephone;
	/**
	 * 属地 开户必填
	 */
	private String apanage;
	/**
	 * 地区代码 开户registerType不为1时必填
	 */
	private String areaCode;
	/**
	 * 城市代码 开户registerType不为1时必填
	 */
	private String cityCode;
	/**
	 * 联系地址名称 信息同步选填
	 */
	private String contactAddress;
	/**
	 * 联系地址邮编 信息同步选填
	 */
	private String contactZipcode;
	/**
	 * 单位地址名称 信息同步选填
	 */
	private String organizationAddress;
	/**
	 * 单位地址邮编 信息同步选填
	 */
	private String organizationZipcode;
	/**
	 * 登录密码错误输入次数 不填
	 */
	private Long pwdErrCount = 0l;
	/**
	 * 锁定期限 不填
	 */
	private Date lockTimeLimit;
	/**
	 * 单位代码 不填
	 */
	private String organizationCode;
	/**
	 * 安全保护问题 开户、信息同步选填
	 */
	private String question;
	/**
	 * 提示答案 开户、信息同步选填
	 */
	private String answer;
	/**
	 * 激活用链接地址 不填
	 */
	private String activeAddress;
	/**
	 * 激活验证码 不填
	 */
	private String activeCode;
	/**
	 * 激活状态 不填
	 */
	private String activeStatus;
	/**
	 * 注册时间 不填
	 */
	private Date registeDate;
	/**
	 * 是否销户 不填
	 */
	private String isClosingAccount;
	/**
	 * 注册受理机构 开户必填
	 */
	private String registeOrgCode;
	/**
	 * 注册受理人员 不填
	 */
	private String registeUid;
	/**
	 * 客户经理 不填
	 */
	private String manager;
	/**
	 * 最近一次成功登录时间 不填
	 */
	private Date lastSuccessLoginTime;
	/**
	 * 最近一次成功登录IP 不填
	 */
	private String lastSuccessLoginIp;
	/**
	 * 最近一次失败登录时间 不填
	 */
	private Date lastFailLoginTime;
	/**
	 * 最近一次失败登录IP 不填
	 */
	private String lastFailLoginIp;
	/**
	 * 最近一次更新时间 不填
	 */
	private Date lastUpdateTime;
	/**
	 * 客户状态 不填
	 */
	private String customerStatus;
	/**
	 * 销户时间 不填
	 */
	private Date closeTime;
	/**
	 * 激活期限 不填
	 */
	private Date activeTimeLimit;
	/**
	 * 激活时间 不填
	 */
	private Date activeTime;
	/**
	 * 客户等级 不填
	 */
	private String customerGrade;
	/**
	 * 客户类型 不填
	 */
	private String customerType;
	/**
	 * 国籍 开户、信息同步选填推荐填写
	 */
	private String nationality;
	/**
	 * 职业 开户、信息同步选填推荐填写
	 */
	private String profession;
	/**
	 * 证件有效期 开户、信息同步选填推荐填写
	 */
	private String identifyExpiredDate;
	/**
	 * 黑名单 不填
	 */
	private String blacklistFlag;
	/**
	 * 头像 不填
	 */
	private byte[] portrait;
	/**
	 * 原始交易密码，发送短信使用,不填
	 */
	private String initPinKey;

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

    public String getRealname() {
        return isRealname;
    }

    public void setRealname(String realname) {
        isRealname = realname;
    }

    public String getRequestCertificate() {
        return isRequestCertificate;
    }

    public void setRequestCertificate(String requestCertificate) {
        isRequestCertificate = requestCertificate;
    }

    public String getClosingAccount() {
        return isClosingAccount;
    }

    public void setClosingAccount(String closingAccount) {
        isClosingAccount = closingAccount;
    }

    public String getPinKey() {
        return pinKey;
    }

    public void setPinKey(String pinKey) {
        this.pinKey = pinKey;
    }

    public String getIsRealname() {
		return isRealname;
	}

	public void setIsRealname(String isRealname) {
		this.isRealname = isRealname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsRequestCertificate() {
		return isRequestCertificate;
	}

	public void setIsRequestCertificate(String isRequestCertificate) {
		this.isRequestCertificate = isRequestCertificate;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getApanage() {
		return apanage;
	}

	public void setApanage(String apanage) {
		this.apanage = apanage;
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

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactZipcode() {
		return contactZipcode;
	}

	public void setContactZipcode(String contactZipcode) {
		this.contactZipcode = contactZipcode;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public byte[] getPortrait() {
		if (portrait != null) {
			byte[] temp = new byte[portrait.length];
			System.arraycopy(portrait, 0, temp, 0, portrait.length);
			return temp;
		} else {
			return new byte[0];
		}
	}

	public void setPortrait(byte[] portrait) {
		byte[] temp = new byte[portrait.length];
		System.arraycopy(portrait, 0, temp, 0, portrait.length);
		this.portrait = temp;
	}

	public String getInitPinKey() {
		return initPinKey;
	}

	public void setInitPinKey(String initPinKey) {
		this.initPinKey = initPinKey;
	}

	public String getUnifyId() {
		return unifyId;
	}

	public void setUnifyId(String unifyId) {
		this.unifyId = unifyId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWebLoginPassword() {
		return webLoginPassword;
	}

	public void setWebLoginPassword(String webLoginPassword) {
		this.webLoginPassword = webLoginPassword;
	}

	public String getIvrPassword() {
		return ivrPassword;
	}

	public void setIvrPassword(String ivrPassword) {
		this.ivrPassword = ivrPassword;
	}

	public String getAccountQueryPassword() {
		return accountQueryPassword;
	}

	public void setAccountQueryPassword(String accountQueryPassword) {
		this.accountQueryPassword = accountQueryPassword;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyNo() {
		return identifyNo;
	}

	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}


	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public String getOrganizationZipcode() {
		return organizationZipcode;
	}

	public void setOrganizationZipcode(String organizationZipcode) {
		this.organizationZipcode = organizationZipcode;
	}

	public Long getPwdErrCount() {
		return pwdErrCount;
	}

	public void setPwdErrCount(Long pwdErrCount) {
		this.pwdErrCount = pwdErrCount;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public Date getRegisteDate() {
		return registeDate;
	}

	public void setRegisteDate(Date registeDate) {
		this.registeDate = registeDate;
	}

	public String getIsClosingAccount() {
		return isClosingAccount;
	}

	public void setIsClosingAccount(String isClosingAccount) {
		this.isClosingAccount = isClosingAccount;
	}

	public String getRegisteOrgCode() {
		return registeOrgCode;
	}

	public void setRegisteOrgCode(String registeOrgCode) {
		this.registeOrgCode = registeOrgCode;
	}

	public String getRegisteUid() {
		return registeUid;
	}

	public void setRegisteUid(String registeUid) {
		this.registeUid = registeUid;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getCustomerGrade() {
		return customerGrade;
	}

	public void setCustomerGrade(String customerGrade) {
		this.customerGrade = customerGrade;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getIdentifyExpiredDate() {
		return identifyExpiredDate;
	}

	public void setIdentifyExpiredDate(String identifyExpiredDate) {
		this.identifyExpiredDate = identifyExpiredDate;
	}

	public String getBlacklistFlag() {
		return blacklistFlag;
	}

	public void setBlacklistFlag(String blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
	}

	public String getHomeTelephone() {
		return homeTelephone;
	}

	public void setHomeTelephone(String homeTelephone) {
		this.homeTelephone = homeTelephone;
	}

	public String getOfficeTelephone() {
		return officeTelephone;
	}

	public void setOfficeTelephone(String officeTelephone) {
		this.officeTelephone = officeTelephone;
	}

	public String getOtherTelephone() {
		return otherTelephone;
	}

	public void setOtherTelephone(String otherTelephone) {
		this.otherTelephone = otherTelephone;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

}
