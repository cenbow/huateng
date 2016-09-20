package cn.com.huateng.web.interceptor;

import cn.com.huateng.CommonUser;
import cn.com.huateng.web.controller.api.UserCache;
import com.aixforce.common.utils.CommonConstants;
import com.aixforce.user.base.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Author: jlchen
 * Date: 2013-01-22
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private final static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    private final UserCache userCache;

    public LoginInterceptor(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object userId = session.getAttribute(CommonConstants.SESSION_USER_ID);
            if (userId != null) {
                try {
                    CommonUser baseUser = this.userCache.getByUserId(Long.parseLong(userId.toString()));
                    UserUtil.putCurrentUser(baseUser);
                } catch (Exception e) {
                    log.error("failed to get user from loadingCache",e);
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtil.removeUser();
    }
}
