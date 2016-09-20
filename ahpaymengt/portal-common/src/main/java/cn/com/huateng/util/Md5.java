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
import java.security.MessageDigest;

/**
 * User: 董培基
 * Date: 13-7-31
 * Time: 上午10:42
 */
public class Md5 {
    private static final String hexDigits[] = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F"
    };

    private static BASE64Encoder base64 = new BASE64Encoder();
    private static byte[] myIV = {50, 51, 52, 53, 54, 55, 56, 57};

    public Md5() {
    }

    /**
     * 理想网关支付MD5加密方法-GBK
     * @param str
     * @return
     */
    public static final String encodeGBK(String str) {
        try {
            byte strtemp[] = str.getBytes("GBK");
            MessageDigest mdtemp = MessageDigest.getInstance("MD5");
            mdtemp.update(strtemp);
            byte md[] = mdtemp.digest();
            return byteToHEX(md);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 理想网关支付MD5加密方法-UTF8
     * @param str
     * @return
     */
    public static final String encodeUTF8(String str) {
        try {
            byte strtemp[] = str.getBytes("UTF8");
            MessageDigest mdtemp = MessageDigest.getInstance("MD5");
            mdtemp.update(strtemp);
            byte md[] = mdtemp.digest();
            return byteToHEX(md);
        } catch (Exception e) {
            return null;
        }
    }

    private static String byteToHEX(byte bytes[]) {
        char Digit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        StringBuffer sb = new StringBuffer();
        char ob[] = new char[2];
        for (int i = 0; i < bytes.length; i++) {
            byte byte0 = bytes[i];
            ob[0] = Digit[byte0 >>> 4 & 0xf];
            ob[1] = Digit[byte0 & 0xf];
            sb.append(ob);
        }

        return sb.toString();
    }

    private static String byteArrayToHexString(byte abyte0[]) {
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < abyte0.length; i++)
            stringbuffer.append(byteToHexString(abyte0[i]));

        return stringbuffer.toString();
    }

    private static String byteToHexString(byte byte0) {
        int i = byte0;
        if (i < 0)
            i += 256;
        int j = i / 16;
        int k = i % 16;
        return hexDigits[j] + hexDigits[k];
    }

    public static String encode(String s) {
        String s1 = null;
        try {
            s1 = s;
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            s1 = byteArrayToHexString(messagedigest.digest(s1.getBytes()));
        } catch (Exception exception) {
        }
        return s1;
    }
    public static String desEncrypt(String input, String strkey) throws Exception {
        strkey = procKey(strkey);
        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp = null;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
        Key key = null;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);

        input = padding(input);
        byte[] plainBytes = (byte[]) null;
        Cipher cipher = null;
        byte[] cipherText = (byte[]) null;

        plainBytes = input.getBytes("UTF8");
        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(1, myKey, ivspec);
        cipherText = cipher.doFinal(plainBytes);
        String regStr = removeBR(base64.encode(cipherText));
        String rtn = byteArrayToHexString(regStr.getBytes());
        return rtn;
    }

    public static byte[] removePadding(byte[] oldByteArray) {
        int numberPaded = 0;
        for (int i = oldByteArray.length; i >= 0; i--) {
            if (oldByteArray[(i - 1)] == 0)
                continue;
            numberPaded = oldByteArray.length - i;
            break;
        }

        byte[] newByteArray = new byte[oldByteArray.length - numberPaded];
        System.arraycopy(oldByteArray, 0, newByteArray, 0, newByteArray.length);

        return newByteArray;
    }

    private static String procKey(String key) {
        if (key.length() < 32) {
            for (; key.length() < 32; key = (new StringBuilder(String.valueOf(key))).append("0").toString()) ;
            return key;
        }
        if (key.length() > 32)
            return key.substring(0, 32);
        else
            return key;
    }

    public static String padding(String str) {
        try {
            byte[] oldByteArray = str.getBytes("UTF8");
            int numberToPad = 8 - oldByteArray.length % 8;
            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
            System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
            for (int i = oldByteArray.length; i < newByteArray.length; i++) {
                newByteArray[i] = 0;
            }
            return new String(newByteArray, "UTF8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Crypter.padding UnsupportedEncodingException");
        }
        return null;
    }

    private static String removeBR(String str) {
        StringBuffer sf = new StringBuffer(str);

        for (int i = 0; i < sf.length(); i++) {
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
}
