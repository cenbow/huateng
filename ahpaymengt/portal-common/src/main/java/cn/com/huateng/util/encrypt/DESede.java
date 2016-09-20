// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DESede.java

package cn.com.huateng.util.encrypt;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

// Referenced classes of package com.huateng.util:
//			StringUtil

public class DESede
{

	private SecretKey deskey;
	private Cipher c1;

	public DESede(byte abyte0[])
		throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		deskey = new SecretKeySpec(abyte0, "DESede");
		c1 = Cipher.getInstance("DESede");
	}

	public byte[] encryptMode(byte abyte0[])
	{
		try {
			c1.init(1, deskey);
			return c1.doFinal(abyte0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] decryptMode(byte abyte0[])
	{
		try {
			c1.init(2, deskey);
			return c1.doFinal(abyte0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}


}
