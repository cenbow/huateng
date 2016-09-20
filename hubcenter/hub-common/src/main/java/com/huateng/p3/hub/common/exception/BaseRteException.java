package com.huateng.p3.hub.common.exception;

/**
 * @author cmt
 * @version 2014-1-21 下午10:53:55 
 */
public class BaseRteException extends RuntimeException {

	
	private static final long serialVersionUID = -5202217343363684206L;
	/** 错误结果代码 */
	private String errorCode;
	/** 错误信息 */
	private String errorMessage;

	/**
	 * 构造函数
	 * 
	 * @param prompt
	 *            错误结果描述
	 * @param errorCode
	 *            错误代码
	 * @param throwable
	 *            Throwable
	 */
	public BaseRteException(String errorCode, Throwable throwable) {
		super(throwable.getMessage());
		this.errorCode = errorCode;
		this.errorMessage = throwable.getMessage();
	}

	/**
	 * 构造函数
	 * 
	 * @param prompt
	 *            错误信息描述
	 * @param errorCode
	 *            错误代码
	 * @param errorMessage
	 *            错误信息
	 */
	public BaseRteException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 构造函数
	 * 
	 * @param prompt
	 *            错误信息描述
	 * @param errorCode
	 *            错误代码
	 * @param errorMessage
	 *            错误信息
	 * @param throwable
	 */
	public BaseRteException(String errorCode, String errorMessage,
			Throwable throwable) {
		super(errorMessage, throwable);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 得到错误代码
	 * 
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 得到错误信息
	 * 
	 * @return String
	 */
	public String getMessage() {
		return errorMessage;
	}
}
