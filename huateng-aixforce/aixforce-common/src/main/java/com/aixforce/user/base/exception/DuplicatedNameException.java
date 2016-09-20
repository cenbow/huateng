package com.aixforce.user.base.exception;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-04-25
 */
public class DuplicatedNameException extends RuntimeException {

    public DuplicatedNameException() {
    }

    public DuplicatedNameException(String s) {
        super(s);
    }

    public DuplicatedNameException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DuplicatedNameException(Throwable throwable) {
        super(throwable);
    }
}
