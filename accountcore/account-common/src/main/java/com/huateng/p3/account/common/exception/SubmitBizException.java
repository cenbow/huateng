package com.huateng.p3.account.common.exception;



import lombok.Getter;

import com.huateng.p3.account.common.util.StringUtil;

/**
 * Created by IntelliJ IDEA.
 * User: huwenjie
 * Date: 14-4-22
 * 需要提交事务的异常
 */
public class SubmitBizException extends RuntimeException {


	private static final long serialVersionUID = 3654723329900477406L;
	@Getter
    private String code;

    public SubmitBizException(String code) {
        this.code = code;
    }

    public SubmitBizException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public SubmitBizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SubmitBizException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
    public SubmitBizException(String code, String message, String... arg)
    {
    	super(StringUtil.replaceString(message, arg));
    	this.code = code;
    }

}
