package cn.com.huateng.common;

import cn.com.huateng.util.DateUtil;
import cn.com.huateng.util.StringUtil;

/**
 * User: 董培基
 * Date: 13-8-1
 * Time: 下午2:54
 */
public class CodeGenerator {


    public static String generateSeqNo(Long orderSeq) {
        try {
            return DateUtil.formatDate(DateUtil.getDate(), "yyyyMMdd")
                    + StringUtil.fillLeft(Long.toString(orderSeq), '0', 12);
        } catch (Exception e) {
            return StringUtil.fillLeft(Long.toString(orderSeq), '0', 20);
        }
    }

    public static String transSerialNo(){
               String rd= StringUtil.generateRandomString(6);
        return DateUtil.formatDate(DateUtil.getDate(), "yyyyMMddHHmmss") +rd;
    }



}
