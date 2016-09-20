package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class MarketTxntypeCfg implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3574080709795731973L;

	private String markettxntypeid;

    private String marketcfgid;

    private String outtxntype;

    private String txntype;

    private String validflag;

    public String getMarkettxntypeid() {
        return markettxntypeid;
    }

    public void setMarkettxntypeid(String markettxntypeid) {
        this.markettxntypeid = markettxntypeid;
    }

    public String getMarketcfgid() {
        return marketcfgid;
    }

    public void setMarketcfgid(String marketcfgid) {
        this.marketcfgid = marketcfgid;
    }

    public String getOuttxntype() {
        return outtxntype;
    }

    public void setOuttxntype(String outtxntype) {
        this.outtxntype = outtxntype;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getValidflag() {
        return validflag;
    }

    public void setValidflag(String validflag) {
        this.validflag = validflag;
    }
}