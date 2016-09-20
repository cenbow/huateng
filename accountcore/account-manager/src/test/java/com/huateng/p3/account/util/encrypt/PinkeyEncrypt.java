// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PinkeyEncrypt.java

package com.huateng.p3.account.util.encrypt;

import com.huateng.p3.account.util.StringUtil;

/**
 * 此类为单元测试密码加密用
 * @author huwenjie
 *
 */
public class PinkeyEncrypt
{

	private DESede desede;
	
	public PinkeyEncrypt(String s)
	{
		try
		{
			s = s + s + s;
			desede = new DESede(StringUtil.hexStringToBytes(s));
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			throw new BusinessException("000001", "生产密钥失败");
		}
	}

	public PinkeyEncrypt()
	{
		try
		{
			String s = "3134393836323037";
			s = s + s + s;
			desede = new DESede(StringUtil.hexStringToBytes(s));
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			throw new BusinessException("000001", "生产密钥失败");
		}
	}

	public String encrypt(String s, String s1)
	{
		try {
			byte abyte1[];
			validPParas(s, s1);
			String s2 = "0000" + truncAccountNo(s1);
			String s3 = "06" + s + "FFFFFFFF";
			String s4 = xor(s2, s3);
			byte abyte0[] = StringUtil.hexStringToBytes(s4);
			abyte1 = desede.encryptMode(abyte0);
			return StringUtil.bytesToHexString(abyte1).toUpperCase().substring(0, 16);
			
		} catch (BusinessException e) {
			
			e.printStackTrace();
			throw new BusinessException(e.getErrorCode(),e.getErrorMsg());
		}catch (Exception e) {
			
			e.printStackTrace();
			throw new BusinessException("999999", "未知错误");
		}
	}

	public String encrypt(String s, String s1, String s2, String s3, String s4)
	{
		try {
			String s8;
			validPParas(s, s1, s2);
			String s5 = "0000" + truncAccountNo(s1);
			String s6 = "06" + s + "FFFFFFFF";
			String s7 = xor(s5, s6);
			byte abyte0[] = StringUtil.hexStringToBytes(s7);
			byte abyte1[] = desede.encryptMode(abyte0);
			s8 = StringUtil.bytesToHexString(abyte1).toUpperCase().substring(0, 16);
			return RSAEncrypt.encryptKey(s8 + s2, s3, s4);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			throw new BusinessException(e.getErrorCode(),e.getErrorMsg());
		}catch (Exception e) {
			
			e.printStackTrace();
			throw new BusinessException("999999", "未知错误");
		}
	}

	private void validPParas(String s, String s1)
	{
		if (!StringUtil.isNumber(s) || s.length() != 6)
			throw new BusinessException("100001", "密码格式错误");
		if (!StringUtil.isNumber(s1) || s1.length() != 16)
			throw new BusinessException("100002", "卡号格式错误");
		else
			return;
	}

	private void validPParas(String s, String s1, String s2)
	{
		if (!StringUtil.isNumber(s) || s.length() != 6)
			throw new BusinessException("100001", "密码格式错误");
		if (!StringUtil.isNumber(s2) || s2.length() > 20)
			throw new BusinessException("100003", "订单号长度错误");
		if (!StringUtil.isNumber(s1) || s1.length() != 16)
			throw new BusinessException("100002", "卡号格式错误");
		else
			return;
	}

	private String xor(String s, String s1)
	{
		byte abyte0[] = s.getBytes();
		byte abyte1[] = s1.getBytes();
		byte abyte2[] = new byte[16];
		int i = 0;
		for (int j = 0; j < 16; j += 2)
		{
			int k = abyte0[j] - 48 << 4 | abyte0[j + 1] - 48;
			int l = 0;
			if (abyte1[j] > 57 && abyte1[j + 1] > 57)
				l = abyte1[j] - 55 << 4 | abyte1[j + 1] - 55;
			else
			if (abyte1[j] > 57 && abyte1[j + 1] < 57)
				l = abyte1[j] - 55 << 4 | abyte1[j + 1] - 48;
			else
			if (abyte1[j] < 57 && abyte1[j + 1] > 57)
				l = abyte1[j] - 48 << 4 | abyte1[j + 1] - 55;
			else
				l = abyte1[j] - 48 << 4 | abyte1[j + 1] - 48;
			int i1 = k ^ l;
			int j1 = i1;
			int k1 = (i1 >> 4) + 48;
			if (k1 > 57)
				k1 += 7;
			int l1 = (j1 & 0xf) + 48;
			if (l1 > 57)
				l1 += 7;
			abyte2[i++] = (byte)k1;
			abyte2[i++] = (byte)l1;
		}

		return new String(abyte2);
	}

	private String truncAccountNo(String s)
	{
		return s.substring(s.length() - 13, s.length() - 1);
	}

	public static void main(String args[])
	{
		String s = "952926";
		String s1 = "0000015312400405";
		PinkeyEncrypt pinkeyencrypt = new PinkeyEncrypt("3430323437383034");
		System.out.println(pinkeyencrypt.encrypt(s, s1));
	}
}
