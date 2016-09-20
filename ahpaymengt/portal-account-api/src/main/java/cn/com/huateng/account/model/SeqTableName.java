package cn.com.huateng.account.model;


import java.io.Serializable;

public class SeqTableName implements Serializable{

	private static final long serialVersionUID = -8771964456884363437L;
	/* @property: seq表名字 */
	private String seqName;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }
}