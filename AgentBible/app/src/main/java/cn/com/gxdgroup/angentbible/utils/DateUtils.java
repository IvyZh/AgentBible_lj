package cn.com.gxdgroup.angentbible.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ivy on 2017/6/14.
 */

public class DateUtils {

    //Date—>String

    /**
     * @param date
     * @return yyyy-MM-dd
     */
    public static String date2String(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * @param date
     * @return 2007年1月18日
     */
    public static String date2StringFull(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
        String s1 = format.format(date);
        String s = s1.substring(0, s1.length() - 3);
        return s;
    }

    //String转化为时间戳

    /**
     * @param time yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long string2UnixTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
            long unixTimestamp = date.getTime() / 1000;
            return unixTimestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

}
