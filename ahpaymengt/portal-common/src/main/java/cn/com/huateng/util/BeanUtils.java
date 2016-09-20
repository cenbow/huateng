package cn.com.huateng.util;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 14-11-7
 * Time: 下午5:18
 * To change this template use File | Settings | File Templates.
 */
public class BeanUtils {

    /**
     * 对字符串按字符随即排列
     *
     * @param data
     *            字符串
     * @return String
     */
    public static String data2Random(String data) {
        StringBuffer from = new StringBuffer(data);
        StringBuffer to = new StringBuffer("");
        Random r = new Random();
        for (int i = from.length(); i != 0; i--) {
            int index = r.nextInt(from.length());
            to.append(from.charAt(index));
            from.deleteCharAt(index);
        }
        return to.toString();

    }
}
