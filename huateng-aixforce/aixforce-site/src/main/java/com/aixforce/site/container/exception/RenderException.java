package com.aixforce.site.container.exception;

/*
 * Author: jlchen
 * Date: 2013-01-17
 */
public class RenderException extends RuntimeException{
    public RenderException() {
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderException(Throwable cause) {
        super(cause);
    }
}
