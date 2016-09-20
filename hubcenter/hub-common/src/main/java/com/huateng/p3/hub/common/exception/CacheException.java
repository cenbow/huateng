package com.huateng.p3.hub.common.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cmt
 * @version 2014-1-21 下午10:53:51 
 */

@Slf4j
public class CacheException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public CacheException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
		log.error("errorCode:{},errorMessage:{}", new Object[] { errorCode,
				errorMessage });
	}

	/**
	 * @param errorCode
	 * @param throwable
	 */
	public CacheException(String errorCode, Throwable throwable) {
		super(errorCode, throwable);
		log.error("errorCode:{},throwable:{}", new Object[] { errorCode,
				throwable.getMessage() });
	}

	/**
	 * @param errorCode
	 * @param errorMessage
	 * @param throwable
	 */
	public CacheException(String errorCode, String errorMessage,
			Throwable throwable) {
		super(errorCode, errorMessage, throwable);
		log.error("errorCode:{},errorMessage:{},throwable:{}", new Object[] {
				errorCode, errorMessage, throwable.getMessage() });
	}

	/** 内部交易代码 */
	private Long intTxnSeq;

	public Long getIntTxnSeq() {
		return intTxnSeq;
	}

	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public CacheException(Long intTxnSeq, String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
		this.intTxnSeq = intTxnSeq;
		log.error("intTxnSeq:{},errorCode:{},errorMessage", new Object[] {
				intTxnSeq, errorCode, errorMessage });
	}

	/**
	 * @param errorCode
	 * @param throwable
	 */
	public CacheException(Long intTxnSeq, String errorCode, Throwable throwable) {
		super(errorCode, throwable);
		this.intTxnSeq = intTxnSeq;
		log.error("intTxnSeq:{},errorCode:{},throwable:{}", new Object[] {
				intTxnSeq, errorCode, throwable.getMessage() });
	}

	/**
	 * @param errorCode
	 * @param errorMessage
	 * @param throwable
	 */
	public CacheException(Long intTxnSeq, String errorCode, String errorMessage,
			Throwable throwable) {
		super(errorCode, errorMessage, throwable);
		this.intTxnSeq = intTxnSeq;
		log.error(
				"intTxnSeq:{},errorCode:{},errorMessage:{},throwable:{}",
				new Object[] { intTxnSeq, errorCode, errorMessage,
						throwable.getMessage() });
	}

}