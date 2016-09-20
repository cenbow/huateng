package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TbPosInfo extends TbPosInfoKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -878822424748475676L;

	private String posTmk;

	private String posPinkey;

	private String posMackey;

	private String posStatus;

	private String posPinSeq;

	public String getPosTmk() {
		return posTmk;
	}

	public void setPosTmk(String posTmk) {
		this.posTmk = posTmk;
	}

	public String getPosPinkey() {
		return posPinkey;
	}

	public void setPosPinkey(String posPinkey) {
		this.posPinkey = posPinkey;
	}

	public String getPosMackey() {
		return posMackey;
	}

	public void setPosMackey(String posMackey) {
		this.posMackey = posMackey;
	}

	public String getPosStatus() {
		return posStatus;
	}

	public void setPosStatus(String posStatus) {
		this.posStatus = posStatus;
	}

	public String getPosPinSeq() {
		return posPinSeq;
	}

	public void setPosPinSeq(String posPinSeq) {
		this.posPinSeq = posPinSeq;
	}

}