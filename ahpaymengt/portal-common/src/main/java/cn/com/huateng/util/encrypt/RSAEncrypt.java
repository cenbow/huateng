// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RSAEncrypt.java

package cn.com.huateng.util.encrypt;

import java.math.BigInteger;

public class RSAEncrypt
{

	

	public RSAEncrypt()
	{
	}

	public static final String encryptKey(String s, String s1, String s2)
	{
		byte abyte0[] = s.getBytes();
		BigInteger biginteger = null;
		BigInteger biginteger1 = new BigInteger(abyte0);
		if (s.length() <= 1024)
			biginteger = biginteger1.modPow(new BigInteger(s1), new BigInteger(s2));
		return biginteger.toString();
	}

	public static final String decryptKey(String s, String s1, String s2)
	{
		BigInteger biginteger = (new BigInteger(s)).modPow(new BigInteger(s1), new BigInteger(s2));
		return new String(biginteger.toByteArray());
	}

}
