package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TInfoAccountBond implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2520874622951338699L;

	private Short recordNo;

    private String customerNo;

    private String bondAccountNo;

    private String bondNo;

    private String fundAccountNo;

    private Short bondRecordNo;

    private String bondName;

    private String bondType;

    private String issueOrgCode;

    private Date bindingTime;

    private Date downloadTime;

    private String validFlag;

    private Date effectiveDate;

    private Date validDate;

    private String payMethod;

    private Short amount;

    private Short balance;

    private Short availableBalance;

    public Short getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Short recordNo) {
        this.recordNo = recordNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getBondAccountNo() {
        return bondAccountNo;
    }

    public void setBondAccountNo(String bondAccountNo) {
        this.bondAccountNo = bondAccountNo;
    }

    public String getBondNo() {
        return bondNo;
    }

    public void setBondNo(String bondNo) {
        this.bondNo = bondNo;
    }

    public String getFundAccountNo() {
        return fundAccountNo;
    }

    public void setFundAccountNo(String fundAccountNo) {
        this.fundAccountNo = fundAccountNo;
    }

    public Short getBondRecordNo() {
        return bondRecordNo;
    }

    public void setBondRecordNo(Short bondRecordNo) {
        this.bondRecordNo = bondRecordNo;
    }

    public String getBondName() {
        return bondName;
    }

    public void setBondName(String bondName) {
        this.bondName = bondName;
    }

    public String getBondType() {
        return bondType;
    }

    public void setBondType(String bondType) {
        this.bondType = bondType;
    }

    public String getIssueOrgCode() {
        return issueOrgCode;
    }

    public void setIssueOrgCode(String issueOrgCode) {
        this.issueOrgCode = issueOrgCode;
    }

    public Date getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(Date bindingTime) {
        this.bindingTime = bindingTime;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public Short getBalance() {
        return balance;
    }

    public void setBalance(Short balance) {
        this.balance = balance;
    }

    public Short getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Short availableBalance) {
        this.availableBalance = availableBalance;
    }
}