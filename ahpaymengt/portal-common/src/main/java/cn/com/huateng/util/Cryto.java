package cn.com.huateng.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * User: 董培基
 * Date: 13-9-5
 * Time: 上午9:32
 */
public class Cryto {
    private static  final Logger logger = LoggerFactory.getLogger(Cryto.class);
    private static String algorithm = "DESede";
    private static String default_charset = "UTF-8";

    public static String encryptBase643DES(String source, String key)
    {
        return encryptBase643DES(source, key, default_charset);
    }

    public static String encryptBase643DES(String source, String key, String charset) {
        try {
            byte[] abyte0 = Hex.decode(key);
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
            cipher.init(1, new SecretKeySpec(abyte0, algorithm), new IvParameterSpec(Hex.decode("0102030405060708")));
            byte[] abyte1 = cipher.doFinal(source.getBytes(charset));
            return base64Encode(abyte1);
        } catch (Exception e) {
            logger.error("对字符串进行3DES加密时出错!@" + e.getMessage());
        }return null;
    }

    public static String decryptBase643DES(String source, String key) {
        return decryptBase643DES(source, key, default_charset);
    }

    public static String decryptBase643DES(String source, String key, String charset) {
        try {
            byte[] abyte0 = Hex.decode(key);
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
            cipher.init(2, new SecretKeySpec(abyte0, algorithm), new IvParameterSpec(Hex.decode("0102030405060708")));

            byte[] base64ed = base64Decode(source);
            if (base64ed == null) return null;

            byte[] abyte1 = cipher.doFinal(base64ed);
            return new String(abyte1, charset);
        } catch (Throwable e) {
            logger.error("对字符串进行3DES解密时出错!@");
        }

        return null;
    }
    public static byte[] base64Decode(String str) {
        try {
            byte[] a = new BASE64Decoder().decodeBuffer(str);
            return a;
        } catch (Exception e) {
            logger.error("使用增强型BASE64编码格式对字符串进行解码时出错!@" + e.getMessage());
        }
        return null;
    }
    public static String base64Encode(byte[] b) {
        try {
            return new BASE64Encoder().encode(b);
        } catch (Exception e) {
            logger.error("使用增强型BASE64编码格式对字节数组进行编码时出错!@" + e.getMessage());
        }
        return null;
    }
}
