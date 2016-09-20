package com.huateng.p3.account.common.exception;



import lombok.Getter;

import com.huateng.p3.account.common.util.StringUtil;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-12-4
 */
public class BizException extends RuntimeException {
   
	private static final long serialVersionUID = 1165876351848409310L;
	@Getter
    private String code;

    public BizException(String code) {
        this.code = code;
    }

    public BizException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
    public BizException(String code, String message, String... arg)
    {
    	super(StringUtil.replaceString(message, arg));
    	this.code = code;
    }

}
