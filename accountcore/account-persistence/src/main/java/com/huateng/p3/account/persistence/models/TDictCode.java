package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

public class TDictCode extends TDictCodeKey implements Serializable {

	private static final long serialVersionUID = 8288711248577134942L;

	private String codeDesc;

    private String dictId;

    private Short priority;

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }
}