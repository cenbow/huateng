package com.huateng.p3.account.common.exception;

import lombok.Getter;

/**
 * /**
 * Created by IntelliJ IDEA.
 * User:James      Tang
 *  Dte: 14-7-22
 */
public class ClearException extends RuntimeException {

	private static final long serialVersionUID = 1165876351848409310L;
	@Getter
    private String code;

    public ClearException(String code) {
        this.code = code;
    }

    public ClearException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ClearException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ClearException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

}
