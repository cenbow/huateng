package com.aixforce.exception;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-27
 */
public class ParamInfoException extends RuntimeException {
    private static final long serialVersionUID = 6295717443044895278L;

    public ParamInfoException() {
    }

    public ParamInfoException(String message) {
        super(message);
    }

    public ParamInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamInfoException(Throwable cause) {
        super(cause);
    }
}
