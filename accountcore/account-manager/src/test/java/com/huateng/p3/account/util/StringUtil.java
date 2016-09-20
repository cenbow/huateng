package com.huateng.p3.account.util;

import java.util.Map;
import java.util.Random;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * User: 胡文杰  此类为单元测试密码加密用
 * Date: 13-7-30
 * Time: 下午6:02
 */
public class StringUtil {

    private static final String FOLDER_SEPARATOR = "/";
    private static char[] codeSequence1 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static char[] codeSequence2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'};

    public static String generatePassWordRandomString(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int j = 0; j < i; j++) {
            stringBuffer.append(codeSequence2[random.nextInt(codeSequence2.length)]);
        }
        return stringBuffer.toString();
    }

    public static String generateRandomString(int i) {
        StringBuffer stringbuffer = new StringBuffer();
        Random random = new Random();
        for (int j = 0; j < i; j++)
            stringbuffer.append(codeSequence1[random.nextInt(codeSequence1.length)]);

        return stringbuffer.toString();
    }

    public static String getStringByNumber(int num) {
        Random random = new Random();
        String str = "";
        for (int i = 0; i < num; i++) {
            str += String.valueOf(codeSequence1[random.nextInt(10)]);
        }
        return str;
    }

    public static String bytesToHexString(byte abyte0[]) {
        StringBuilder stringbuilder = new StringBuilder("");
        if (abyte0 == null || abyte0.length <= 0)
            return null;
        for (byte anAbyte0 : abyte0) {
            int j = anAbyte0 & 0xff;
            String s = Integer.toHexString(j);
            if (s.length() < 2)
                stringbuilder.append(0);
            stringbuilder.append(s);
        }

        return stringbuilder.toString();
    }

    public static byte[] hexStringToBytes(String s) {
        if (s == null || s.equals(""))
            return null;
        s = s.toUpperCase();
        int i = s.length() / 2;
        char ac[] = s.toCharArray();
        byte abyte0[] = new byte[i];
        for (int j = 0; j < i; j++) {
            int k = j * 2;
            abyte0[j] = (byte) (charToByte(ac[k]) << 4 | charToByte(ac[k + 1]));
        }

        return abyte0;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String fillLeft(String s, char c, int i) {
        return fillStr(s, c, i, true);
    }

    public static String fillRight(String s, char c, int i) {
        return fillStr(s, c, i, false);
    }

    private static String fillStr(String s, char c, int i, boolean flag) {
        int j = i - s.length();
        if (j <= 0)
            return s;
        StringBuilder stringbuilder = new StringBuilder(s);
        for (; j > 0; j--)
            if (flag)
                stringbuilder.insert(0, c);
            else
                stringbuilder.append(c);

        return stringbuilder.toString();
    }

    /**
     * 获取指定文件路径中对应的文件名
     *
     * @param path 文件路径
     * @return 文件名
     * @see {@link org.springframework.util.StringUtils#getFilename(String)}
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    public static int length(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return 0;
        } else {
            return s.length();
        }
    }

    public static String generateParams(Map<String, Object> paramMap) {
        StringBuilder params = new StringBuilder();
        for (String key : paramMap.keySet()) {
            if (paramMap.get(key) != null) {
                params.append("<").append(key).append(">").append(paramMap.get(key)).append("</").append(key).append(">");
            } else {
                params.append("<").append(key).append(">").append("</").append(key).append(">");
            }
        }
        return params.toString();
    }

    public static boolean isNumber(String s) {
        if (s == null || s.equals(""))
            return false;
        String s1 = "0123456789";
        for (int i = 0; i < s.length(); i++)
            if (s1.indexOf(s.charAt(i)) < 0)
                return false;

        return true;
    }

    /*
     *生成随机x位数字
     */
    public static String random(int x) {
        Double d = Math.random();
        String s = d.toString();
        int i = x > s.length() - 2 ? s.length() - 2 : x;
        return s.substring(2, 2 + i);
    }

    public static Map<String, String> getMap(String s) {
        Map<String, String> hashMap = Maps.newHashMap();
        if (s == null)
            return hashMap;
        String as[] = s.split("&");
        for (int i = 0; i < as.length; i++) {
            String s1 = as[i];
            String as1[] = s1.split("=");
            if (as1.length == 2)
                hashMap.put(as1[0], as1[1]);
        }

        return hashMap;
    }

   

    public static String toTrim(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        } else {
            return str.trim();
        }
    }

}
