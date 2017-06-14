package cn.com.gxdgroup.angentbible.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    /**
     * 格式化显示话题回复时间：秒前、分前、时前、10天前、日期
     *
     * @param str : yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatReplayDate(String str) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(str);
            long time = parse.getTime();
            long timeMillis = System.currentTimeMillis();
            long dx = (timeMillis - time) / 1000;
            System.out.println("dx:" + dx);
            System.out.println("str:" + str);
            if (dx < 60) {
                result = dx + "秒前";
            } else if (dx / 60 < 60) {
                result = (dx / 60) + "分钟前";
            } else if (dx / 60 / 60 < 24) {
                result = (dx / 60 / 60) + "小时前";
            } else if (dx / 60 / 60 / 24 < 10) {
                result = (dx / 60 / 60 / 24) + "天前";
            } else {
                result = str.split(" ")[0];
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 这是在formatReplayDate  上进行改进的，为了显示24小时之后的时间
     *
     * @param str
     * @return
     */
    public static String newFormatReplayDate(String str) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(str);
            long time = parse.getTime();
            long timeMillis = System.currentTimeMillis();
            long dx = (timeMillis - time) / 1000;
            System.out.println("dx:" + dx);
            System.out.println("str:" + str);
            if (dx < 60) {
                result = dx + "秒前";
            } else if (dx / 60 < 60) {
                result = (dx / 60) + "分钟前";
            } else if (dx / 60 / 60 < 24) {
                result = (dx / 60 / 60) + "小时前";
            } else if (dx / 60 / 60 / 24 < 10) {
//				result = (dx / 60 / 60 / 24) + "天前";
                result = str;
            } else {
                result = str.split(" ")[0];
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 处理Unix时间戳的
     *
     * @param time ： 1443169080
     * @return : yyyy-MM-dd
     */
    public static String formatUnixTime(String time) {
        if (TextUtils.isEmpty(time))
            return "";
        if (TextUtils.equals(time, "null"))
            return "";

        Long timestamp = Long.parseLong(time);
        return new java.text.SimpleDateFormat("yyyy-MM-dd")
                .format(new java.util.Date(timestamp));
    }

    public static String formatUnixTimeShort(String time) {
        if (TextUtils.isEmpty(time))
            return "";
        if (TextUtils.equals(time, "null"))
            return "";

        Long timestamp = Long.parseLong(time);
        return new java.text.SimpleDateFormat("yyyy-MM-dd")
                .format(new java.util.Date(timestamp));
    }

    /**
     * @param time
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatUnixTime2(String time) {

        if (TextUtils.isEmpty(time))
            return "";
        if (TextUtils.equals(time, "null"))
            return "";
        Long timestamp = Long.parseLong(time);
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date(timestamp));
    }

    public static String formatBirthDateTime(String time) {
        Long timestamp = Long.parseLong(time) * 1000;
        String format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date(timestamp));
        return format;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式的时间戳转换成 yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String Date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time[] = sdf.format(date).toString().split(" ");
        String startDate[] = time[0].split("-");
        String st = "";
        for (int i = 0; i < startDate.length; i++) {
            st += startDate[i];
        }

        String startTime[] = time[1].split(":");
        String sd = "";
        for (int i = 0; i < startTime.length; i++) {
            sd += startTime[i];
        }

        return st + sd;
    }


    public static String get2FormatDate(int t) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(t);
    }


    public static int getYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));//获取东八区时间
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));//获取东八区时间
        return (calendar.get(Calendar.MONTH) + 1);   //获取月份，0表示1月份
    }

    public static int getDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));//获取东八区时间
        return calendar.get(Calendar.DAY_OF_MONTH);// 3
    }
}
