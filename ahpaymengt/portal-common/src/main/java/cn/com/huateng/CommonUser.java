package cn.com.huateng;

import com.google.common.base.Objects;
import com.aixforce.user.base.BaseUser;

import java.io.Serializable;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-25
 */
public class CommonUser extends BaseUser implements Serializable{

    private static final long serialVersionUID = -1025537675479310138L;

    private String unifyId;

    private String accountNo;



    private String lastLoginTime;

    private  String mobileNo;

    public void setUnifyId(String unifyId) {
        this.unifyId = unifyId;
    }

    public CommonUser(Long id, String principal, TYPE type, String unifyId,String accountNo) {
        super(id, principal, type);
        this.unifyId = unifyId;
        this.accountNo = accountNo;
    }

    public CommonUser(Long id, String principal, TYPE type, String unifyId) {
        super(id, principal, type);
        this.unifyId = unifyId;
    }

    public CommonUser(Long id, String principal, TYPE type, String unifyId,String lastLoginTime,String mobileNo) {
        super(id, principal, type);
        this.unifyId = unifyId;
        this.lastLoginTime=lastLoginTime;
        this.mobileNo=mobileNo;
    }


    public CommonUser() {
        super();
    }

    public String getUnifyId() {
        return unifyId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
       this.accountNo=unifyId;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof CommonUser)){
            return false;
        }
        CommonUser that = (CommonUser)o;
        return Objects.equal(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("baseUser",super.toString())
                .add("unifyId", unifyId)
                .omitNullValues()
                .toString();
    }
}