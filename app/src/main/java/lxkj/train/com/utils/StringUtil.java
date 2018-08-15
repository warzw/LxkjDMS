package lxkj.train.com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dell on 2018/7/6.
 */

public class StringUtil {
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
    //把spiner里面String转化成毫秒
    static String[] arr={"10分钟","20分钟","30分钟","40分钟","50分钟","60分钟","70分钟","80分钟","90分钟","100分钟","110分钟","120分钟"};
    public static int timeInt(String str){
        if (arr[0].equals(str)) {
            return 60000*10;
        }else if (arr[1].equals(str)) {
            return 60000*20;
        }else if (arr[2].equals(str)) {
            return 60000*30;
        }else if (arr[3].equals(str)) {
            return 60000*40;
        }else if (arr[4].equals(str)) {
            return 60000*50;
        }else if (arr[5].equals(str)) {
            return 60000*60;
        }else if (arr[6].equals(str)) {
            return 60000*70;
        }else if (arr[7].equals(str)) {
            return 60000*80;
        }else if (arr[8].equals(str)) {
            return 60000*90;
        }else if (arr[9].equals(str)) {
            return 60000*100;
        }else if (arr[10].equals(str)) {
            return 60000*110;
        }else if (arr[11].equals(str)) {
            return 60000*120;
        }
         return 1000000;
    }
    //int类型转成String类型年月日
    public static String getTimeStringToInt(int time){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(time*1000));
    }
    //获得当天0点时间
    public static int getTimesmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }
    //获得当天24点时间
    public static int getTimesnight(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }
}
