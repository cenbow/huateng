package com.aixforce.user.base.exception;

/*
 * Author: jlchen
 * Date: 2013-01-22
 */
public class PasswordIncorrectException extends RuntimeException {

    public PasswordIncorrectException() {
    }

    public PasswordIncorrectException(String message) {
        super(message);
    }

    public PasswordIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordIncorrectException(Throwable cause) {
        super(cause);
    }
}
