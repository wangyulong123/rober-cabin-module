package group.rober.runtime.kit;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cytsir on 17/2/16.
 */
public abstract class DateKit {
    public static final String DATE_TIME_MS_FORMAT="yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT="yyyy-MM-dd";


    public static Date now(){
        return new Date();
    }
    public static Date parse(String str){
        String fixedStr = StringKit.nvl(str,"");
        //如果是数字字串，则直接转下
        if(fixedStr.matches("\\d+")){
            return parse(Long.parseLong(str));
        }
        if(fixedStr.indexOf("/")>0){
            fixedStr = fixedStr.replaceAll("/+","-");
        }
        fixedStr = fixedStr.replaceAll("-+","-");

        try{
            DateTimeFormatter format = DateTimeFormat.forPattern(DATE_TIME_MS_FORMAT);
            return DateTime.parse(fixedStr,format).toDate();
        }catch(IllegalArgumentException e){
            try{
                DateTimeFormatter format = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
                return DateTime.parse(fixedStr,format).toDate();
            }catch(IllegalArgumentException e1){
                DateTimeFormatter format = DateTimeFormat.forPattern(DATE_FORMAT);
                return DateTime.parse(fixedStr,format).toDate();
            }
        }
    }

    public static Date parse(Long value){
        return new Date(value);
    }

    public static int getYear(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.getYear();
    }

    public static int getMonth(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.getMonthOfYear();
    }

    public static int getDay(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfMonth();
    }

    public static int getYearOfCentury(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.getYearOfCentury();
    }

    public static String format(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_MS_FORMAT);
        return format(date,DATE_TIME_FORMAT);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 取两个日期间隔的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int getRangeDays(Date date1, Date date2){
        DateTime begin = new DateTime(date1);
        DateTime end = new DateTime(date2);
        Period period = new Period(begin,end, PeriodType.days());
        return period.getDays();
    }
    /**
     * 取两个日期间隔的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int getRangeMonths(Date date1, Date date2){
        DateTime begin = new DateTime(date1);
        DateTime end = new DateTime(date2);
        Period period = new Period(begin,end, PeriodType.months());
        return period.getMonths();
    }

    /**
     * 判断两个日期是否是同一天
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isSameDay(Date startDate, Date endDate) {
        if (getRangeDays(startDate, endDate) == 0)
            return true;
        else
            return false;
    }


    public static Date plusMonths(Date startDate, int plusValue) {
        DateTime oStartDate = new DateTime(startDate);
        DateTime oEndDate = oStartDate.plusMonths(plusValue);
        return oEndDate.toDate();
    }
    public static Date plusDays(Date startDate, int plusValue) {
        DateTime oStartDate = new DateTime(startDate);
        DateTime oEndDate = oStartDate.plusDays(plusValue);
        return oEndDate.toDate();
    }
    public static Date minusDays(Date startDate, int plusValue) {
        DateTime oStartDate = new DateTime(startDate);
        DateTime oEndDate = oStartDate.minusDays(plusValue);
        return oEndDate.toDate();
    }
    public static Date minusYears(Date startDate, int plusValue) {
        DateTime oStartDate = new DateTime(startDate);
        DateTime oEndDate = oStartDate.minusYears(plusValue);
        return oEndDate.toDate();
    }

    /**
     * 指定时间是否在指定区间内
     * @param date
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean inRange(Date date,Date startDate,Date endDate){
        DateTime oDate = new DateTime(date);
        DateTime oStartDate = new DateTime(startDate);
        DateTime oEndDate = new DateTime(endDate);
        Interval interval = new Interval(oStartDate,oEndDate);
        return interval.contains(oDate);
    }

    /**
     * 两个日期之间是否相关整月，如1月1号 至 3月1号为true,
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean rangIsFullMonth(Date startDate, Date endDate){
        int rangMonths = getRangeMonths(startDate,endDate);
        Date oEndDate = plusMonths(startDate, rangMonths);
        return isSameDay(endDate, oEndDate);
    }

    /**
     * 指定日期区间是否在另一个日期区间内
     * @param innerStartDate 内层日期区间开始日期
     * @param innerEndDate 内层日期区间结束日期
     * @param outerStartDate 外层日期区间开始日期
     * @param outerEndDate 外层日期区间结束日期
     * @param containsEnd 是否算尾期
     * @return
     */
    public static boolean inRange(Date innerStartDate,Date innerEndDate,Date outerStartDate,Date outerEndDate,boolean containsEnd){
        if(containsEnd){
            return inRange(innerStartDate,outerStartDate,outerEndDate) && (inRange(innerEndDate,outerStartDate,outerEndDate)|| compareLimitToDay(innerEndDate,outerEndDate) == 0);
        }else{
            return inRange(innerStartDate,outerStartDate,outerEndDate) && (inRange(innerEndDate,outerStartDate,outerEndDate));
        }
    }

    /**
     * 指定日期区间是否在另一个日期区间内
     * @param innerStartDate 内层日期区间开始日期
     * @param innerEndDate 内层日期区间结束日期
     * @param outerStartDate 外层日期区间开始日期
     * @param outerEndDate 外层日期区间结束日期
     * @return
     */
    public static boolean inRange(Date innerStartDate,Date innerEndDate,Date outerStartDate,Date outerEndDate){
        return inRange(innerStartDate, innerEndDate,outerStartDate, outerEndDate,true);
    }

    /**
     * 两个日期比较（到天）如果date1>date2返回1，date1=date2返回0，date1<date2返回-1
     * @param date1
     * @param date2
     * @return
     */
    public static int compareLimitToDay(Date date1,Date date2){
        DateTime startDate = new DateTime(date1);
        DateTime endDate = new DateTime(date2);
        int year1 = startDate.getYear();
        int year2 = endDate.getYear();
        if(year1>year2){
            return 1;
        }else if(year1<year2){
            return -1;
        }else {
            int days1 = startDate.getDayOfYear();
            int days2 = endDate.getDayOfYear();
            if(days1 > days2){
                return 1;
            }else if(days1 < days2){
                return -1;
            }else{
                return 0;
            }
        }
    }

    /**
     * 获取指定日期当月的天数
     * @param date
     * @return
     */
    public static int getMonthDays(Date date){
        DateTime oDate = new DateTime(date);
        Integer days = oDate.dayOfMonth().getMaximumValue();
        return days;
    }

    /**
     * 判断日期date是否在referDate之后
     * @param referDate 前一个日期
     * @param date 比较的后一个日期
     * @param containEquals 如果是同一天，是否包含
     * @return
     */
    public static boolean isAfter(Date referDate,Date date,boolean containEquals){
        int r = compareLimitToDay(date,referDate);
        if(r == 1){
            return true;
        }else if(r == 0){
            if(containEquals)return true;
            else return false;
        }else{
            return false;
        }
    }

    /**
     * 判断日期date是否在referDate之后,相等返会false
     * @param referDate
     * @param date
     * @return
     */
    public static boolean isAfter(Date referDate,Date date){
        return isAfter(referDate,date,false);
    }

    /**
     * 获取指定年的天数
     * @param date
     * @return
     */
    public static int getYearDays(Date date){
        DateTime oDate = new DateTime(date);
        Integer days = oDate.dayOfYear().getMaximumValue();
        return days;
    }

}
