package com.aixforce.user.base.exception;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-06-14
 */
public class DuplicatedMobileException extends RuntimeException{

    public DuplicatedMobileException() {
    }

    public DuplicatedMobileException(String message) {
        super(message);
    }


    public DuplicatedMobileException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedMobileException(Throwable cause) {
        super(cause);
    }
}
