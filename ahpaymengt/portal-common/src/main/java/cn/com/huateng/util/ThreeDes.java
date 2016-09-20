package cn.com.huateng.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * User: 董培基
 * Date: 13-8-26
 * Time: 下午3:10
 */
public class ThreeDes {

    private static BASE64Encoder base64 = new BASE64Encoder();
    private static byte[] myIV = { 50, 51, 52, 53, 54, 55, 56, 57 };

    private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

    public static String desEncrypt(String input, String strkey) throws Exception {
        strkey = procKey(strkey);
        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
        Key key;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);

        input = padding(input);
        byte[] plainBytes;
        Cipher cipher;
        byte[] cipherText;

        plainBytes = input.getBytes("UTF8");
        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(1, myKey, ivspec);
        cipherText = cipher.doFinal(plainBytes);
        String regStr = removeBR(base64.encode(cipherText));
        return byteArrayToHexString(regStr.getBytes());
    }

    public static String desDecrypt(String cipherText, String strkey) throws Exception
    {
        cipherText = new String(hexString2ByteArray(cipherText));
        strkey = procKey(strkey);
        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
        Key key;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);

        Cipher cipher;
        byte[] inPut = base64d.decodeBuffer(cipherText);
        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(2, myKey, ivspec);
        byte[] output = removePadding(cipher.doFinal(inPut));

        return new String(output, "UTF8");
    }
    private static String removeBR(String str)
    {
        StringBuffer sf = new StringBuffer(str);

        for (int i = 0; i < sf.length(); i++)
        {
            if (sf.charAt(i) != '\n')
                continue;
            sf = sf.deleteCharAt(i);
        }

        for (int i = 0; i < sf.length(); i++) {
            if (sf.charAt(i) != '\r')
                continue;
            sf = sf.deleteCharAt(i);
        }

        return sf.toString();
    }

    public static String padding(String str)
    {
        try
        {
            byte[] oldByteArray = str.getBytes("UTF8");
            int numberToPad = 8 - oldByteArray.length % 8;
            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
            System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
            for (int i = oldByteArray.length; i < newByteArray.length; i++)
            {
                newByteArray[i] = 0;
            }
            return new String(newByteArray, "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println("Crypter.padding UnsupportedEncodingException");
        }
        return null;
    }

    public static byte[] removePadding(byte[] oldByteArray) {
        int numberPaded = 0;
        for (int i = oldByteArray.length; i >= 0; i--)
        {
            if (oldByteArray[(i - 1)] == 0)
                continue;
            numberPaded = oldByteArray.length - i;
            break;
        }

        byte[] newByteArray = new byte[oldByteArray.length - numberPaded];
        System.arraycopy(oldByteArray, 0, newByteArray, 0, newByteArray.length);

        return newByteArray;
    }

    private static String procKey(String key)
    {
        if (key.length() < 32) {
            while (key.length() < 32) {
                key = key + "0";
            }
            return key;
        }if (key.length() > 32) {
        return key.substring(0, 32);
    }

        return key;
    }
    public static String byteArrayToHexString(byte[] b)
    {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString().toUpperCase();
    }
    public static byte[] hexString2ByteArray(String strIn)
            throws Exception
    {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[(i / 2)] = (byte)Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
