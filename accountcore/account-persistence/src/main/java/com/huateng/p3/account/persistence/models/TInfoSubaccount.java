package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TInfoSubaccount implements Serializable{
   
	private static final long serialVersionUID = -5804920915840699832L;

	private String accountNo;

    private String mainaccountNo;

    private String accountName;

    private String customerNo;

    private String customerName;

    private String accountCard;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getMainaccountNo() {
        return mainaccountNo;
    }

    public void setMainaccountNo(String mainaccountNo) {
        this.mainaccountNo = mainaccountNo;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountCard() {
        return accountCard;
    }

    public void setAccountCard(String accountCard) {
        this.accountCard = accountCard;
    }
}