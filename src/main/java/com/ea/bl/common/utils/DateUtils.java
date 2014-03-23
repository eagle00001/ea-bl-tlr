package com.ea.bl.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-1
 */
public class DateUtils {
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public final static String NUMBER_DATE_FORMAT = "yyyyMMdd";
	
	/**
     * @param date1
     * @param date2
     * @return
     */
    public static long subDays(Date date1, Date date2) {
        long d1 = toDate(formatDateToDefaultString(date1), DEFAULT_DATE_FORMAT).getTime();
        long d2 = toDate(formatDateToDefaultString(date2), DEFAULT_DATE_FORMAT).getTime();
        return  (d1 - d2) / (1000 * 60 * 60 * 24);
    }
	
	/**
     * @param d
     * @return
     */
    public static String formatDateToDefaultString(Date d) {
        Date date = d;
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        String time = df.format(date);
        return time;
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String formatDateToString(Date date,String format) {
        if(date==null){
            date=new Date();
        }
        if(StringUtils.isBlank(format)){
           format=DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        String time = df.format(date);
        return time;
    }
    
    /**
     * @see DateUtils#toDate(String, String)
     * @param dateStr
     * @return
     */
    public static Date toDefaultDate(String dateStr){
    	return toDate(dateStr,DEFAULT_DATE_FORMAT);
    }

    /**
     * @param dateStr
     * @param format
     * @return
     */
    public static Date toDate(String dateStr, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }
    
    public static void main(String[] args){
    	Date today = new Date();
    	today = toDefaultDate("2012-11-30");
    	Date nextDay = org.apache.commons.lang3.time.DateUtils.addDays(today, 1);
    	System.out.println("Today is :"+formatDateToDefaultString(today));
    	System.out.println("Next day is :"+formatDateToDefaultString(nextDay));
    	
    	System.out.println("subtract the tow days is :"+subDays(nextDay, today));
    }
}
