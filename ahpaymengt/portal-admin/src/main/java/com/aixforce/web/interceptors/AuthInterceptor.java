package com.aixforce.web.interceptors;

import com.aixforce.common.utils.CommonConstants;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Author: jlchen
 * Date: 2013-01-23
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        BaseUser currentUser = UserUtil.getCurrentUser();
        if ((currentUser != null && currentUser.getTypeEnum()==BaseUser.TYPE.ADMIN)
                || requestURI.endsWith("/users/login")) {
            return true;
        } else if (currentUser == null) {
            if (!isAjaxRequest(request)) {
                redirectToLogin(request, response);
            }else{//ajax request
                throw new JsonResponseException(401,"you should login as admin");
            }
        } else {
            throw new RuntimeException("you are not admin");
        }
        return false;
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentUrl = request.getRequestURI();

        if (!Strings.isNullOrEmpty(request.getQueryString())) {
            currentUrl = currentUrl + "?" + request.getQueryString();
        }
        request.getSession().setAttribute(CommonConstants.PREVIOUS_URL, currentUrl);
        response.sendRedirect("/users/login");
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return Objects.equal(request.getHeader(HttpHeaders.X_REQUESTED_WITH), "XMLHttpRequest");
    }
}
