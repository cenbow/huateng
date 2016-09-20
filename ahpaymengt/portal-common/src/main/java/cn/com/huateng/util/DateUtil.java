package cn.com.huateng.util;

import com.google.common.base.Objects;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * User: 董培基
 * Date: 13-7-30
 * Time: 下午6:26
 */
public class DateUtil {
    private final static DateTimeFormatter yyyyMM = DateTimeFormat.forPattern("yyyyMM");

    private final static DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyyMMdd");
    private final static DateTimeFormatter yyyyMMddHHmmss = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    private final static DateTimeFormatter yyyyMMddHHmmsssss = DateTimeFormat.forPattern("yyyyMMddHHmmsssss");
    private final static DateTimeFormatter timeyyyyMMddHHmmss = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public static Date getDate() {
        return new Date();
    }

    public static String formatDate(Date date, String pattern) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.print(new DateTime(date.getTime()));
    }

    public static String formatTimestamp(Timestamp timestamp) {
        return yyyyMMddHHmmss.print(new DateTime(timestamp.getTime()));
    }

    public static Date formatString(String date) {
        return yyyyMMdd.parseDateTime(date).toDate();
    }


    public static String getDateYYYYMMddHHMMSS() {
        return yyyyMMddHHmmss.print(new DateTime());
    }
    public static  String getDateYYYYMMddHHMMSSSSS(){
        return yyyyMMddHHmmsssss.print(new DateTime());
    }

    public static String getDateYYYYMMdd() {
        return yyyyMMdd.print(new DateTime());
    }

    public static String getMonth() {
        return yyyyMM.print(new DateTime());
    }

    public static String getDateYYYYMMDD() {
        return timeyyyyMMddHHmmss.print(new DateTime()).substring(0, 10);
    }

    public static Timestamp getTime() {
        String s = timeyyyyMMddHHmmss.print(new DateTime());
        return Timestamp.valueOf(s);
    }
    public static Timestamp getFirstTime(Date date)
    {
        if (date == null)
            return null;
        else
            return new Timestamp(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0, 0);
    }

    public static Timestamp getLastTime(Date date)
    {
        if (date == null)
            return null;
        else
            return new Timestamp(date.getYear(), date.getMonth(), date.getDate(), 23, 59, 59, 0);
    }

    public static int compareTime(Date createTime){
        if(Objects.equal(formatDate(createTime,"yyyyMMdd"),DateUtil.getDateYYYYMMdd())){
           return 0;
        } else{
           return 1;
        }
    }


}
