package cn.com.huateng.payment.model;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class TDictAreaCity implements Serializable{

	private static final long serialVersionUID = 1949370216892006691L;
	@Getter
    @Setter
    /*当前代码*/
    private String currentCode;
	@Getter
    @Setter
    /*电信区号*/
    private String teleCode;
	@Getter
    @Setter
    /*当前名字*/
    private String currentName;
	@Getter
    @Setter
    /*父亲代码*/
    private String parenetCode;
	@Getter
    @Setter
    /*代码标识*/
    private String codeFlag;
	@Getter
    @Setter
    private String mobileHCity;
	@Getter
    @Setter
    private String parenetName;
	@Getter
    @Setter
    private List<TDictAreaCity> subCity;
	@Getter
    @Setter
    /*地区拼音*/
    private String pinyin;
	
    @Override
    public int hashCode() {
        return Objects.hashCode(currentCode);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null|| !(obj instanceof TDictAreaCity)){
            return false;
        }
        TDictAreaCity that = (TDictAreaCity)obj;
        return Objects.equal(this.currentCode,that.currentCode);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("currentCode", currentCode)
                .add("teleCode", teleCode)
                .add("currentName", currentName)
                .add("parenetCode", parenetCode)
                .add("codeFlag", codeFlag)
                .add("mobileHCity", mobileHCity)
                .omitNullValues()
                .toString();
    }
}