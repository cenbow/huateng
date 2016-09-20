package com.aixforce.web.interceptors;

import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.UserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Author: jlchen
 * Date: 2013-01-22
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        BaseUser admin = (BaseUser)session.getAttribute("ADMIN");
        if(admin != null){
            UserUtil.putCurrentUser(admin);
        }
        return true;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtil.removeUser();
    }
}
