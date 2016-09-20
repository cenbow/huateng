package com.aixforce.site.container.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Author: jlchen
 * Date: 2013-01-18
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class Server500Exception extends RuntimeException{
    public Server500Exception() {
    }

    public Server500Exception(String message) {
        super(message);
    }

    public Server500Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Server500Exception(Throwable cause) {
        super(cause);
    }
}
