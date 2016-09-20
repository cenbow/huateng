package com.huateng.p3.account.persistence.models;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class TInfoBondMerchantKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -323817658547716279L;

	private Short bondRecordNo;

    private String rangeType;

    private String useRange;

    public Short getBondRecordNo() {
        return bondRecordNo;
    }

    public void setBondRecordNo(Short bondRecordNo) {
        this.bondRecordNo = bondRecordNo;
    }

    public String getRangeType() {
        return rangeType;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public String getUseRange() {
        return useRange;
    }

    public void setUseRange(String useRange) {
        this.useRange = useRange;
    }
}