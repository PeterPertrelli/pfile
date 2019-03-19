package com.zxf.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String SIMPLE_DATE_FORMAT="yyyy-MM-dd";
	
	public static final String HHMM_DATE_FORMAT="yyyy-MM-dd HH:mm";

	public static final String HHMMSS_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public static final String HHMMSSSSS1_DATE_FORMAT="yyyy-MM-dd HH:mm:ss,SSS";
	
	public static Date toDate(String sdate, String fmString) {
		DateFormat df = new SimpleDateFormat(fmString);
		try {
			return df.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToString(Date date, String format) {
		if (date != null) {
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(date);
		} else {
			return null;
		}
	}
	
	/**
	 * 取日期是哪一年
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Object year = cal.get(Calendar.YEAR);
		if (year != null) {
			return Integer.valueOf(year.toString());
		} else {
			return 0;
		}
	}

}
