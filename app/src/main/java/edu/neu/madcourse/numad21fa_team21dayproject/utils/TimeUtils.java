package edu.neu.madcourse.numad21fa_team21dayproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    private static SimpleDateFormat mShortFormater;
    public static final long HOUR_IN_MILLIS = TimeUnit.HOURS.toMillis(1);
    public static final long MINUTE_IN_MILLIS = TimeUnit.MINUTES.toMillis(1);
    public static final String TIME_DIVIDER = ":";

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            if (date != null) {
                long ts = date.getTime();
                return ts;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        return stampToDate(Long.parseLong(s));
    }

    public static String stampToDate(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(timestamp);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDateDay(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为英语时间
     */
    public static String stampToEnglishDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为英文时间
     */
    public static String toEnglishDateFromUtc(String utcTime) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date(getCurrentTimeFromUTC(utcTime));
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String toBirthdayTime(String time) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime();
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
            Date date2 = new Date(ts);
            String res = simpleDateFormat2.format(date2);
            return res;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long getCurrentTimeFromUTC(String utcTime) {
        //2018-08-13T07:25:00.000Z
        String date = utcTime.replace("Z", " UTC");//注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d != null && d.getTime() > 0) {
            return d.getTime();
        } else {
            return 0;
        }
    }

    public static boolean isSameDay(long time1, long time2) {
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date1.setTimeInMillis(time1);
        date2.setTimeInMillis(time2);
        if (date1.get(Calendar.DAY_OF_MONTH) != date2.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        if (date1.get(Calendar.MONTH) != date2.get(Calendar.MONTH)) {
            return false;
        }
        if (date1.get(Calendar.YEAR) != date2.get(Calendar.YEAR)) {
            return false;
        }
        return true;
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String convertAsShortTime(long time) {
        if (mShortFormater == null) {
            mShortFormater = new SimpleDateFormat("MMM. d   hh:mm aa", Locale.ENGLISH);
        }
        return mShortFormater.format(time);
    }

    public static String convertAsTime(long time) {
        if (mShortFormater == null) {
            mShortFormater = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        }
        return mShortFormater.format(time);
    }

    public static String stampToEngShotDate(long time) {
        SimpleDateFormat mShortFormater = new SimpleDateFormat("MMM d", Locale.ENGLISH);
        return mShortFormater.format(new Date(time));
    }

    public static String stampToWeek(long time) {
        SimpleDateFormat mShortFormater = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        return mShortFormater.format(new Date(time));
    }

    public static boolean isToday(Calendar date) {
        Calendar today = Calendar.getInstance();
        int[] fields = {Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR};
        return equalsDate(today, date, fields);
    }

    public static boolean isToday(long time) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        long todayStart = date.getTimeInMillis();
        return (time >= todayStart);
    }

    public static boolean isYesterday(long time) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, -1);
        int[] fields = {Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR};
        return equalsDate(today, date, fields);
    }

    public static boolean isYesterday(Calendar date) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, -1);
        int[] fields = {Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR};
        return equalsDate(today, date, fields);
    }

    public static boolean isThisWeek(Calendar date) {
        //TODO Implement it.
        return false;
    }


    public static boolean equalsDate(Calendar date1, Calendar date2, int... fields) {
        if (fields.length == 0) {
            return date1.equals(date2);
        }
        for (int field : fields) {
            if (date1.get(field) != date2.get(field)) {
                return false;
            }
        }
        return true;
    }

    public static int calcRestHour(long time) {
        return (int) (time / HOUR_IN_MILLIS);
    }

    public static int calcRestMinute(long time) {
        return (int) ((time % HOUR_IN_MILLIS) / MINUTE_IN_MILLIS);
    }
}
