/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller.api.userEvent;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-06-20
 */
public class UserProfileEvent{
    @Getter
    private final Long userId;

    @Getter
    @Setter
    private String mobile;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String realName;

    public UserProfileEvent(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null|| !(o instanceof UserProfileEvent)){
            return false;
        }
        UserProfileEvent that = (UserProfileEvent)o;
        return Objects.equal(this.userId,that.userId)
                && Objects.equal(this.name,that.name)
                && Objects.equal(this.realName,that.realName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId,name,realName);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("userId", userId)
                .add("Mobile", mobile)
                .add("name", name)
                .add("realName", realName)
                .toString();
    }
}
