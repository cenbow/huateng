package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TDictTxnErrcode implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8637894319352628874L;

	private String errcode;

    private String errflag;

    private String errdesc;

    private String outercode;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrflag() {
        return errflag;
    }

    public void setErrflag(String errflag) {
        this.errflag = errflag;
    }

    public String getErrdesc() {
        return errdesc;
    }

    public void setErrdesc(String errdesc) {
        this.errdesc = errdesc;
    }

    public String getOutercode() {
        return outercode;
    }

    public void setOutercode(String outercode) {
        this.outercode = outercode;
    }
}