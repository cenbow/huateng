package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.Objects;

import lombok.ToString;

public class TInfoAccount implements Serializable{
    
	private static final long serialVersionUID = 2901661309242592124L;

	private String accountNo;

    private String accountName;

    private String customerNo;

    private Date openDate;

    private Date closeDate;

    private Date expiredDate;

    private String payPasswd;

    private String initPasswd;

    private String initPasswdModified;

    private Long passwdErrNum = 0l;

    private Date passwdErrLockTime;

    private String status;

    private String commStatus;

    private String type;

    private String grade;

    private String isRealname;

    private String apanage;

    private String organizationCode;

    private String areaCode;

    private String cityCode;

    private String openChannel;

    private Long balance = 0l;

    private Long lastDayBal = 0l;

    private Long lastMonthBal = 0l;

    private Long availableBalance = 0l;

    private Long unavailableBalance = 0l;

    private Long withdrawBalance= 0l;

    private Long frozenAmount= 0l;

    private Long preauthorizedAmt = 0l;

    private String lastTxnDate;

    private Long daySumConsAmt = 0l;

    private Integer daySumConsCnt = 0;

    private Long daySumOutAmt = 0l;

    private Integer daySumOutCnt = 0;

    private Long daySumInAmt = 0l;

    private Integer daySumInCnt = 0;

    private Long daySumCashAmt= 0l;

    private Integer daySumCashCnt = 0;

    private Long daySumDepositAmt = 0l;

    private Integer daySumDepositCnt = 0;

    private Long monthSumConsAmt = 0l;

    private Long monthSumOutAmt = 0l;

    private Long monthSumInAmt = 0l;

    private Long monthSumCashAmt = 0l;

    private Long monthSumChgAmt = 0l;

    private String isAllowedClose;

    private String isCloseRtnCash;

    private String encryptedMsg;

    private Date lastUpdateTime;

    private Date lastTxnTime;

    private Long rsvdAmt1 = 0l;

    private Long rsvdAmt2 = 0l;

    private String rsvdText1;

    private String rsvdText2;

    private Long monthSumConsCnt = 0l;

    private Long monthSumOutCnt = 0l;

    private Long monthSumInCnt = 0l;

    private Long monthSumCashCnt = 0l;

    private Long monthSumChgCnt = 0l;

    private String masterAccountNo;

    private String forbiddenTxn;

    private Long extendCount = 0l;

    private String forbiddenChannel;
    
    private String cardNo;
    private String cardType;
    private String cardBindingMethod;
    private boolean existMainSubRelation = false;//是否存在主子账户关系,转账用

    public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getPayPasswd() {
		return payPasswd;
	}

	public void setPayPasswd(String payPasswd) {
		this.payPasswd = payPasswd;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public Long getPreauthorizedAmt() {
		return preauthorizedAmt;
	}

	public void setPreauthorizedAmt(Long preauthorizedAmt) {
		this.preauthorizedAmt = preauthorizedAmt;
	}

	public Long getDaySumDepositAmt() {
		return daySumDepositAmt;
	}

	public void setDaySumDepositAmt(Long daySumDepositAmt) {
		this.daySumDepositAmt = daySumDepositAmt;
	}

	public Integer getDaySumDepositCnt() {
		return daySumDepositCnt;
	}

	public void setDaySumDepositCnt(Integer daySumDepositCnt) {
		this.daySumDepositCnt = daySumDepositCnt;
	}

	public Long getExtendCount() {
		return extendCount;
	}

	public void setExtendCount(Long extendCount) {
		this.extendCount = extendCount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardBindingMethod() {
		return cardBindingMethod;
	}

	public void setCardBindingMethod(String cardBindingMethod) {
		this.cardBindingMethod = cardBindingMethod;
	}

	public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getInitPasswd() {
        return initPasswd;
    }

    public void setInitPasswd(String initPasswd) {
        this.initPasswd = initPasswd;
    }

    public String getInitPasswdModified() {
        return initPasswdModified;
    }

    public void setInitPasswdModified(String initPasswdModified) {
        this.initPasswdModified = initPasswdModified;
    }

    public Long getPasswdErrNum() {
        return passwdErrNum;
    }

    public void setPasswdErrNum(Long passwdErrNum) {
        this.passwdErrNum = passwdErrNum;
    }

    public Date getPasswdErrLockTime() {
        return passwdErrLockTime;
    }

    public void setPasswdErrLockTime(Date passwdErrLockTime) {
        this.passwdErrLockTime = passwdErrLockTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommStatus() {
        return commStatus;
    }

    public void setCommStatus(String commStatus) {
        this.commStatus = commStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getIsRealname() {
        return isRealname;
    }

    public void setIsRealname(String isRealname) {
        this.isRealname = isRealname;
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

    public String getOpenChannel() {
        return openChannel;
    }

    public void setOpenChannel(String openChannel) {
        this.openChannel = openChannel;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getLastDayBal() {
        return lastDayBal;
    }

    public void setLastDayBal(Long lastDayBal) {
        this.lastDayBal = lastDayBal;
    }

    public Long getLastMonthBal() {
        return lastMonthBal;
    }

    public void setLastMonthBal(Long lastMonthBal) {
        this.lastMonthBal = lastMonthBal;
    }

    public Long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Long getUnavailableBalance() {
        return unavailableBalance;
    }

    public void setUnavailableBalance(Long unavailableBalance) {
        this.unavailableBalance = unavailableBalance;
    }

    public Long getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(Long withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

    public Long getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getLastTxnDate() {
        return lastTxnDate;
    }

    public void setLastTxnDate(String lastTxnDate) {
        this.lastTxnDate = lastTxnDate;
    }

    public Long getDaySumConsAmt() {
        return daySumConsAmt;
    }

    public void setDaySumConsAmt(Long daySumConsAmt) {
        this.daySumConsAmt = daySumConsAmt;
    }

    public Integer getDaySumConsCnt() {
        return daySumConsCnt;
    }

    public void setDaySumConsCnt(Integer daySumConsCnt) {
        this.daySumConsCnt = daySumConsCnt;
    }

    public Long getDaySumOutAmt() {
        return daySumOutAmt;
    }

    public void setDaySumOutAmt(Long daySumOutAmt) {
        this.daySumOutAmt = daySumOutAmt;
    }

    public Integer getDaySumOutCnt() {
        return daySumOutCnt;
    }

    public void setDaySumOutCnt(Integer daySumOutCnt) {
        this.daySumOutCnt = daySumOutCnt;
    }

    public Long getDaySumInAmt() {
        return daySumInAmt;
    }

    public void setDaySumInAmt(Long daySumInAmt) {
        this.daySumInAmt = daySumInAmt;
    }

    public Integer getDaySumInCnt() {
        return daySumInCnt;
    }

    public void setDaySumInCnt(Integer daySumInCnt) {
        this.daySumInCnt = daySumInCnt;
    }

    public Long getDaySumCashAmt() {
        return daySumCashAmt;
    }

    public void setDaySumCashAmt(Long daySumCashAmt) {
        this.daySumCashAmt = daySumCashAmt;
    }

    public Integer getDaySumCashCnt() {
        return daySumCashCnt;
    }

    public void setDaySumCashCnt(Integer daySumCashCnt) {
        this.daySumCashCnt = daySumCashCnt;
    }

    public Long getMonthSumConsAmt() {
        return monthSumConsAmt;
    }

    public void setMonthSumConsAmt(Long monthSumConsAmt) {
        this.monthSumConsAmt = monthSumConsAmt;
    }

    public Long getMonthSumOutAmt() {
        return monthSumOutAmt;
    }

    public void setMonthSumOutAmt(Long monthSumOutAmt) {
        this.monthSumOutAmt = monthSumOutAmt;
    }

    public Long getMonthSumInAmt() {
        return monthSumInAmt;
    }

    public void setMonthSumInAmt(Long monthSumInAmt) {
        this.monthSumInAmt = monthSumInAmt;
    }

    public Long getMonthSumCashAmt() {
        return monthSumCashAmt;
    }

    public void setMonthSumCashAmt(Long monthSumCashAmt) {
        this.monthSumCashAmt = monthSumCashAmt;
    }

    public Long getMonthSumChgAmt() {
        return monthSumChgAmt;
    }

    public void setMonthSumChgAmt(Long monthSumChgAmt) {
        this.monthSumChgAmt = monthSumChgAmt;
    }

    public String getIsAllowedClose() {
        return isAllowedClose;
    }

    public void setIsAllowedClose(String isAllowedClose) {
        this.isAllowedClose = isAllowedClose;
    }

    public String getIsCloseRtnCash() {
        return isCloseRtnCash;
    }

    public void setIsCloseRtnCash(String isCloseRtnCash) {
        this.isCloseRtnCash = isCloseRtnCash;
    }

    public String getEncryptedMsg() {
        return encryptedMsg;
    }

    public void setEncryptedMsg(String encryptedMsg) {
        this.encryptedMsg = encryptedMsg;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastTxnTime() {
        return lastTxnTime;
    }

    public void setLastTxnTime(Date lastTxnTime) {
        this.lastTxnTime = lastTxnTime;
    }

    public Long getRsvdAmt1() {
        return rsvdAmt1;
    }

    public void setRsvdAmt1(Long rsvdAmt1) {
        this.rsvdAmt1 = rsvdAmt1;
    }

    public Long getRsvdAmt2() {
        return rsvdAmt2;
    }

    public void setRsvdAmt2(Long rsvdAmt2) {
        this.rsvdAmt2 = rsvdAmt2;
    }

    public String getRsvdText1() {
        return rsvdText1;
    }

    public void setRsvdText1(String rsvdText1) {
        this.rsvdText1 = rsvdText1;
    }

    public String getRsvdText2() {
        return rsvdText2;
    }

    public void setRsvdText2(String rsvdText2) {
        this.rsvdText2 = rsvdText2;
    }

    public Long getMonthSumConsCnt() {
        return monthSumConsCnt;
    }

    public void setMonthSumConsCnt(Long monthSumConsCnt) {
        this.monthSumConsCnt = monthSumConsCnt;
    }

    public Long getMonthSumOutCnt() {
        return monthSumOutCnt;
    }

    public void setMonthSumOutCnt(Long monthSumOutCnt) {
        this.monthSumOutCnt = monthSumOutCnt;
    }

    public Long getMonthSumInCnt() {
        return monthSumInCnt;
    }

    public void setMonthSumInCnt(Long monthSumInCnt) {
        this.monthSumInCnt = monthSumInCnt;
    }

    public Long getMonthSumCashCnt() {
        return monthSumCashCnt;
    }

    public void setMonthSumCashCnt(Long monthSumCashCnt) {
        this.monthSumCashCnt = monthSumCashCnt;
    }

    public Long getMonthSumChgCnt() {
        return monthSumChgCnt;
    }

    public void setMonthSumChgCnt(Long monthSumChgCnt) {
        this.monthSumChgCnt = monthSumChgCnt;
    }

    public String getMasterAccountNo() {
        return masterAccountNo;
    }

    public void setMasterAccountNo(String masterAccountNo) {
        this.masterAccountNo = masterAccountNo;
    }

    public String getForbiddenTxn() {
        return forbiddenTxn;
    }

    public void setForbiddenTxn(String forbiddenTxn) {
        this.forbiddenTxn = forbiddenTxn;
    }

    public String getForbiddenChannel() {
        return forbiddenChannel;
    }

    public void setForbiddenChannel(String forbiddenChannel) {
        this.forbiddenChannel = forbiddenChannel;
    }

	public boolean isExistMainSubRelation() {
		return existMainSubRelation;
	}

	public void setExistMainSubRelation(boolean existMainSubRelation) {
		this.existMainSubRelation = existMainSubRelation;
	}
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("accountNo", accountNo)
                .add("customerNo", customerNo)
                .add("status", status)
				.add("type", type)
				.add("grade", grade)
				.add("isRealname", isRealname)
				.add("balance", balance)
				.add("availableBalance", availableBalance)
				.add("withdrawBalance", withdrawBalance)		
                .omitNullValues()
                .toString();
    }
}