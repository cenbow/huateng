/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller.api.userEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-10
 */
public class LoginEvent extends SessionEvent {

    public LoginEvent(long userId,HttpServletRequest request,HttpServletResponse response) {
        super(userId, request,response);
    }

}
