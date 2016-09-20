package com.aixforce.session;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public class SerializeException extends RuntimeException {
    public SerializeException() {
    }

    public SerializeException(String s) {
        super(s);
    }

    public SerializeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SerializeException(Throwable throwable) {
        super(throwable);
    }
}
