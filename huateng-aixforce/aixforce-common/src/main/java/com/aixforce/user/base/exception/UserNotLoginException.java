/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.user.base.exception;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/27/12 3:28 PM
 */
public class UserNotLoginException extends RuntimeException {
    public UserNotLoginException() {
    }

    public UserNotLoginException(String message) {
        super(message);
    }

    public UserNotLoginException(Throwable cause) {
        super(cause);
    }

    public UserNotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
