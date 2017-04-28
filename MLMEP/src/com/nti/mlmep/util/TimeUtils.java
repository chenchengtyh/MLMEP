package com.nti.mlmep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    
    /**
     * compare date
     * 
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
        	
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            System.out.println((new SimpleDateFormat("yyyyMMdd")).format(dt1));
            System.out.println((new SimpleDateFormat("yyyyMMdd")).format(dt2));
            if (Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(dt1)) > Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(dt2))) {
                return 1;
            } else if (Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(dt1)) < Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(dt2))) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
        
    }
    
    /**
     * compare date
     * 
     * @return
     */
    public static String dateToStr(String dateStr) {
    	//String dateStr = "Jun 26,2014 4:15:04 PM";
    	  try {
    		  SimpleDateFormat formatFrom = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss aa", Locale.ENGLISH);
			  Date date = formatFrom.parse(dateStr);
			  SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  return formatTo.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return dateStr;
		}
    }
    
    public static String format(Date date, String format)
    {
    	if(date == null){
    		return "";
    	}
    	SimpleDateFormat sdf = null;
   		if(StringUtil.isEmpty(format)){
   			sdf = DEFAULT_DATE_FORMAT;
   		}else{
   			sdf = new SimpleDateFormat(format);
   		}
    	return sdf.format(date);
    }
    
	public static java.util.Date parseDate(String dtStr, String format) throws Exception
   	{
   		if (dtStr == null || dtStr.trim().equals("")){
   			return null;
   		}
   		SimpleDateFormat sdf = null;
   		if(StringUtil.isEmpty(format)){
   			sdf = DEFAULT_DATE_FORMAT;
   		}else{
   			sdf = new SimpleDateFormat(format);
   		}
   		return sdf.parse(dtStr);
   		
   	}
    
}