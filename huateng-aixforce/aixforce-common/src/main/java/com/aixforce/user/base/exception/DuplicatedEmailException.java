package com.aixforce.user.base.exception;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-04-25
 */
public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
    }

    public DuplicatedEmailException(String s) {
        super(s);
    }

    public DuplicatedEmailException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DuplicatedEmailException(Throwable throwable) {
        super(throwable);
    }
}
