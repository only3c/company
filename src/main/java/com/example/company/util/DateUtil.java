package com.example.company.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * 私有构造方法，禁止对该类进行实例化
	 */
	private DateUtil() {
	}

	/**
	 * 得到系统当前日期时间
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 得到用缺省方式格式化的当前日期
	 */
	public static String getDate() {
		return getDateTime(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 得到用指定方式格式化的日期
	 */
	public static String getDate(Date date) {
		return getDateTime(date, DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 得到用指定方式格式化的日期
	 */
	public static String getDate(Date date, String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		
		return getDateTime(date, pattern);
	}

	/**
	 * 得到用缺省方式格式化的当前系统当前日期时间
	 */
	public static String getTime() {
		return getDateTime(DEFAULT_TIME_FORMAT);
	}

	/**
	 * 得到用缺省方式格式化的当前日期及时间
	 */
	public static String getDateTime() {
		return getDateTime(DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 得到系统当前日期及时间，并用指定的方式格式化
	 */
	public static String getDateTime(String pattern) {
		Date datetime = Calendar.getInstance().getTime();
		return getDateTime(datetime, pattern);
	}

	/**
	 * 得到用指定方式格式化的日期
	 */
	public static String getDateTime(Date date, String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATETIME_FORMAT;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 得到用指定方式格式化的日期
	 */
	public static String getChineseDateTime() {
		return getDateTime("yyyy年MM月dd日 HH时mm分ss秒");
	}

	/**
	 * 得到当前年份
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到当前月份
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到当前日
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}
	
	/**
	 * 得到当前是星期几
	 */
	public static int getCurrentWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
	 */
	public static Date addDays(int days) {
		return add(getNow(), days, Calendar.DATE);
	}

	/**
	 * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。
	 */
	public static Date addDays(Date date, int days) {
		return add(date, days, Calendar.DATE);
	}

	/**
	 * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
	 */
	public static Date addMonths(int months) {
		return add(getNow(), months, Calendar.MONTH);
	}

	/**
	 * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
	 */
	public static Date addMonths(Date date, int months) {
		return add(date, months, Calendar.MONTH);
	}

	/**
	 * 内部方法。为指定日期增加相应的天数或月数
	 */
	private static Date add(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(field, amount);
		
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
	 */
	public static long diffDays(Date one, Date two) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(one);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date d1 = calendar.getTime();
		calendar.clear();
		calendar.setTime(two);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date d2 = calendar.getTime();
		BigDecimal r = new BigDecimal(new Double((d1.getTime() - d2.getTime()))
				/ (24 * 60 * 60 * 1000));
		return Math.round(r.doubleValue());
	}

	/**
	 * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
	 */
	public static int diffMonths(Date one, Date two) {
		Calendar calendar = Calendar.getInstance();
		
		// 得到第一个日期的年分和月份数
		calendar.setTime(one);
		int yearOne = calendar.get(Calendar.YEAR);
		int monthOne = calendar.get(Calendar.MONDAY);
		// 得到第二个日期的年份和月份
		calendar.setTime(two);
		int yearTwo = calendar.get(Calendar.YEAR);
		int monthTwo = calendar.get(Calendar.MONDAY);
		
		return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
	}

	/**
	 * 获取某一个日期的年份
	 */
	public static int getYear(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取某一个日期的日
	 */
	public static int getDay(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 将一个字符串用给定的格式转换为日期类型。
	 * 注意：如果返回null，则表示解析失败
	 */
	public static Date parseToDate(String datestr, String pattern) {
		Date date = null;
		
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}

	/**
	 * 将一个字符串用默认的格式转换为日期类型。
	 * 注意：如果返回null，则表示解析失败
	 */
	public static Date parseToDate(String datestr) {
		return parseToDate(datestr, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 将一个字符串用给定的格式转换为日期时间类型。
	 * 注意：如果返回null，则表示解析失败
	 */
	public static Date parseToDateTime(String datetimestr, String pattern) {
		Date date = null;

		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATETIME_FORMAT;
		}
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(datetimestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	/**
	 * 将一个字符串用默认的格式转换为日期时间类型。
	 * 注意：如果返回null，则表示解析失败
	 */
	public static Date parseToDateTime(String datetimestr) {
		return parseToDateTime(datetimestr, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 返回本月的最后一天
	 */
	public static Date getMonthLastDay() {
		return getMonthLastDay(getNow());
	}

	/**
	 * 返回给定日期中的月份中的最后一天
	 */
	public static Date getMonthLastDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		// 将日期设置为下一月第一天
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);
		
		// 减去1天，得到的即本月的最后一天
		calendar.add(Calendar.DATE, -1);
		
		return calendar.getTime();
	}

	/**
	 * 计算两个具体日期之间的秒差，第一个日期-第二个日期
	 */
	public static long diffSeconds(Date date1, Date date2, boolean onlyTime) {
		if (onlyTime) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			calendar.set(1990, 1, 1);
			long t1 = calendar.getTimeInMillis();
			calendar.setTime(date2);
			calendar.set(1990, 1, 1);
			long t2 = calendar.getTimeInMillis();
			return (t1 - t2) / 1000;
		} else {
			return (date1.getTime() - date2.getTime()) / 1000;
		}
	}

	/**
	 * 计算两个具体日期之间的秒差，第一个日期-第二个日期
	 */
	public static long diffSeconds(Date date1, Date date2) {
		return diffSeconds(date1, date2, false);
	}

	/**
	 * 根据日期确定星期几:1-星期日，2-星期一.....
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int mydate = cd.get(Calendar.DAY_OF_WEEK);
		return mydate;
	}

	/**
	 * 得到上月月份
	 */
	public static String getLastMonth() {
		Calendar cd = Calendar.getInstance();
		int year = cd.get(Calendar.YEAR);
		int month = cd.get(Calendar.MONTH);
		if (month == 0) {
			year = year - 1;
			month = 12;
		}
		
		String lastMonth = String.valueOf(year);
		lastMonth += ((month < 10) ? "0" : "") + String.valueOf(month);
		return lastMonth;
	}
	public static long getmilitime(String e) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d=null;
		try {
			if(e==null||"".equals(e))
			{
				d=new Date();
			}else{
			d = dateFormat.parse(e);
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return d.getTime();
	}
}
