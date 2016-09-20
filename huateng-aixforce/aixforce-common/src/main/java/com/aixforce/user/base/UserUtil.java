/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.user.base;

import java.util.Collections;


/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-19
 */
public final class UserUtil {

    private static ThreadLocal<BaseUser> user = new ThreadLocal<BaseUser>();

    private static ThreadLocal<InnerCookie> cookies = new ThreadLocal<InnerCookie>(){
        @Override
        protected InnerCookie initialValue() {
            return new InnerCookie(Collections.<String,String>emptyMap());
        }
    };

    public static <T extends BaseUser> void putCurrentUser(T baseUser){
        user.set(baseUser);
    }

    public static <T extends BaseUser> T getCurrentUser(){
        //noinspection unchecked
        return (T) user.get();
    }

    public static void removeUser(){
        user.remove();
    }

    public static Long getUserId() {
        BaseUser baseUser = getCurrentUser();
        return baseUser!=null?baseUser.getId():null;
    }

    /**
     * 对于子帐号,返回其主帐号的userId
     *
     * @return userId
     */
    public static Long getMasterId(){
        BaseUser baseUser = getCurrentUser();
        if(baseUser==null){
            return null;
        }
        Long parentId = baseUser.getParentId();
        if(parentId == null || parentId<0){ //如果主账号为空,或者主帐号是无效的负值,返回空
            return null;
        }
        return parentId;
    }

    public static void putCookies(InnerCookie innerCookie){
        cookies.set(innerCookie);
    }

    public static InnerCookie getInnerCookie(){
        return cookies.get();
    }

    public static void clearCookies(){
        cookies.remove();
    }
}
