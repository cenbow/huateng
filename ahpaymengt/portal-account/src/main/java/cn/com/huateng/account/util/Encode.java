// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Encode.java

package cn.com.huateng.account.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Encode
{

	public Encode()
	{
	}

	public static final String encode(String s)
	{
		byte abyte1[];
		byte abyte0[] = s.getBytes();
		MessageDigest messagedigest;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(abyte0);
			abyte1 = messagedigest.digest();
			return byteToHEX(abyte1);
		} catch (NoSuchAlgorithmException e) {
			
			return null;
		}
		
		
	}

	private static String byteToHEX(byte abyte0[])
	{
		char ac[] = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F'
		};
		StringBuffer stringbuffer = new StringBuffer();
		char ac1[] = new char[2];
		for (int i = 0; i < abyte0.length; i++)
		{
			byte byte0 = abyte0[i];
			ac1[0] = ac[byte0 >>> 4 & 0xf];
			ac1[1] = ac[byte0 & 0xf];
			stringbuffer.append(ac1);
		}

		return stringbuffer.toString();
	}

	public static String getMobileActiveCode()
	{
		String s = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++)
		{
			String s1 = String.valueOf(random.nextInt(10));
			s = s + s1;
		}

		return s;
	}

	public static String toUtf8String(String s)
	{
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c >= 0 && c <= '\377')
			{
				stringbuffer.append(c);
			} else
			{
				byte abyte0[];
				try
				{
					abyte0 = Character.toString(c).getBytes("utf-8");
				}
				catch (Exception exception)
				{
					abyte0 = new byte[0];
				}
				for (int j = 0; j < abyte0.length; j++)
				{
					int k = abyte0[j];
					if (k < 0)
						k += 256;
					stringbuffer.append("%" + Integer.toHexString(k).toUpperCase());
				}

			}
		}

		return stringbuffer.toString();
	}

	public static String trimLeft(String s, char c)
	{
		return String.valueOf(Long.parseLong(s));
	}
}
