package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TInfoOtapwdTransfer implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4363110674121545830L;

	private String cardNo;

    private String otaPasswd;

    private String ocxPasswd;

    private String rsvdText1;

    private String rsvdText2;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOtaPasswd() {
        return otaPasswd;
    }

    public void setOtaPasswd(String otaPasswd) {
        this.otaPasswd = otaPasswd;
    }

    public String getOcxPasswd() {
        return ocxPasswd;
    }

    public void setOcxPasswd(String ocxPasswd) {
        this.ocxPasswd = ocxPasswd;
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
}