package com.aixforce.user.base.exception;

/*
 * Author: jlchen
 * Date: 2013-01-22
 */
public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {
    }

    public UserNotExistException(String message) {
        super(message);
    }

    public UserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExistException(Throwable cause) {
        super(cause);
    }
}
