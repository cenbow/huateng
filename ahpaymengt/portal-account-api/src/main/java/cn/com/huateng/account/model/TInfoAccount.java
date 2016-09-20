package cn.com.huateng.account.model;


import java.io.Serializable;
import java.util.Date;

public class TInfoAccount implements Serializable{
    /* @property: 客户编号 */
    private String accountNo;
	/* @property: 客户编号 */
	private String customerNo;
	/* @property: 是否实名 */
	private String isRealname;
	/* @property: 姓名 */
	private String name;
	/* @property: 是否申请证书 */
	private String isRequestCertificate;
	/* @property: 客户等级 */
	private String grade;
	/* @property: 客户类型 */
	private String type;
	/* @property: 注册类型 */
	private String registerType;
	/* @property: 统一编号 */
	private String unifyId;
	/* @property: 移动电话 */
	private String mobilePhone;
	/* @property: 电子邮件地址 */
	private String emailAddress;
	/* @property: 用户名 */
	private String loginId;
	/* @property: 昵称 */
	private String nickName;
	/* @property: 登录密码 */
	private String pinkey;
	/* @property: IVR密码 */
	private String ivrPinkey;
	/* @property: 性别 */
	private String gender;
	/* @property: 证件类型 */
	private String idType;
	/* @property: 证件号码 */
	private String idNo;
	/* @property: 家庭电话 */
	private String homePhone;
	/* @property: 办公电话 */
	private String officePhone;
	/* @property: 其他联系人电话 */
	private String otherPhone;
	/* @property: 属地 */
	private String apanage;
	/* @property: 地区代码 */
	private String areaCode;
	/* @property: 城市代码 */
	private String cityCode;
	/* @property: 联系地址名称 */
	private String contactAddress;
	/* @property: 联系地址邮编 */
	private String contactZipcode;
	/* @property: 单位地址名称 */
	private String unitAddress;
	/* @property: 单位地址邮编 */
	private String unitZipcode;
	/* @property: 登录密码错误输入次数 */
	private Long pwdErrNum;
	/* @property： 锁定期限 */
	private Date lockTimeLimit;
	/* @property: 单位代码 */
	private String unitCode;
	/* @property: 安全保护问题 */
	private String question;
	/* @property: 提示答案 */
	private String answer;
	/* @property: 激活用链接地址 */
	private String activeAddress;
	/* @property: 激活验证码 */
	private String activeCode;
	/* @property: 激活状态 */
	private String activeStatus;
	/* @property: 注册时间 */
	private Date registerTime;
	/* @property: 是否销户 */
	private String isClosed;
	/* @property: 客户状态 */
	private String status;
	private Date closeTime;
	private Date activeTimeLimit;
	private Date activeTime;
	private String fundAccountNo;
	private Integer bondCount;
	private String cardNo;
	private String offlineAccountNo;
    /* @property: 余额 */
	private Long balance;
    /* @property: 有效余额 */
	private Long availableBalance;
    /* @property: 可提现余额 */
	private Long withdrawBalance;
	private Long walletBalance;
	private Long integralAvailableBalance;
    private Long frozenAmount;

    public Long getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestCertificate() {
        return isRequestCertificate;
    }

    public void setRequestCertificate(String requestCertificate) {
        isRequestCertificate = requestCertificate;
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

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getUnifyId() {
        return unifyId;
    }

    public void setUnifyId(String unifyId) {
        this.unifyId = unifyId;
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
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

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getUnitZipcode() {
        return unitZipcode;
    }

    public void setUnitZipcode(String unitZipcode) {
        this.unitZipcode = unitZipcode;
    }

    public Long getPwdErrNum() {
        return pwdErrNum;
    }

    public void setPwdErrNum(Long pwdErrNum) {
        this.pwdErrNum = pwdErrNum;
    }

    public Date getLockTimeLimit() {
        return lockTimeLimit;
    }

    public void setLockTimeLimit(Date lockTimeLimit) {
        this.lockTimeLimit = lockTimeLimit;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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

    public String getClosed() {
        return isClosed;
    }

    public void setClosed(String closed) {
        isClosed = closed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getFundAccountNo() {
        return fundAccountNo;
    }

    public void setFundAccountNo(String fundAccountNo) {
        this.fundAccountNo = fundAccountNo;
    }

    public Integer getBondCount() {
        return bondCount;
    }

    public void setBondCount(Integer bondCount) {
        this.bondCount = bondCount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOfflineAccountNo() {
        return offlineAccountNo;
    }

    public void setOfflineAccountNo(String offlineAccountNo) {
        this.offlineAccountNo = offlineAccountNo;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Long getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(Long withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

    public Long getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Long walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Long getIntegralAvailableBalance() {
        return integralAvailableBalance;
    }

    public void setIntegralAvailableBalance(Long integralAvailableBalance) {
        this.integralAvailableBalance = integralAvailableBalance;
    }
}