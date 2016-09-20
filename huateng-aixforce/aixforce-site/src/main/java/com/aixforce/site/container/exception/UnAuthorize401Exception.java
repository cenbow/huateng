package com.aixforce.site.container.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Author: jlchen
 * Date: 2013-01-18
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorize401Exception extends RuntimeException{
    public UnAuthorize401Exception() {
    }

    public UnAuthorize401Exception(String message) {
        super(message);
    }

    public UnAuthorize401Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorize401Exception(Throwable cause) {
        super(cause);
    }
}
