/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.user.base.exception;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-13
 */
public class UserNotActivateException extends RuntimeException {
    public UserNotActivateException() {
    }

    public UserNotActivateException(String message) {
        super(message);
    }

    public UserNotActivateException(Throwable cause) {
        super(cause);
    }

    public UserNotActivateException(String message, Throwable cause) {
        super(message, cause);
    }
}
