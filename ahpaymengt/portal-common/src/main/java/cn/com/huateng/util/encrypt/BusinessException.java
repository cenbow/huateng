// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BusinessException.java

package cn.com.huateng.util.encrypt;


public class BusinessException extends RuntimeException
{

	private static final long serialVersionUID = 0x29059c6a6f648f72L;
	private String errorCode;
	private String errorMsg;

	public BusinessException(String s, String s1)
	{
		errorCode = s;
		errorMsg = s1;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public String toString()
	{
		return errorCode + ":" + errorMsg;
	}
}
