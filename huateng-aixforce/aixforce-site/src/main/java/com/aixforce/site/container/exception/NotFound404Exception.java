package com.aixforce.site.container.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Author: jlchen
 * Date: 2013-01-17
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFound404Exception extends RuntimeException {
    public NotFound404Exception() {
    }

    public NotFound404Exception(Throwable cause) {
        super(cause);
    }

    public NotFound404Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFound404Exception(String message) {
        super(message);
    }
}
