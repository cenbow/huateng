package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

public class TDictCodeKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7121643590438158137L;

	private String codeId;

    private String codeValue;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}