package com.nti.mlmep.util;


/**
 * 应用程序日志打印
 * Debug时将 DEBUG置为 true即可打开Log输出
 * Release时将 DEBUG置为 false即可关闭 Log输出
 * @author wangge
 *
 */
public class AppLog {
	
	//在Release版本软件上将 DEBUG 置为 false即可关闭 Log输出
	private static final boolean DEBUG = true; 
	
	//debug级别的日志
	public static void debug(String tag, String msg) { 
        if(DEBUG) { 
            android.util.Log.d(tag, msg); 
        } 
    } 
	
    public static void debug(String tag, String msg, Throwable tr) { 
        if(DEBUG) { 
            android.util.Log.d(tag, msg, tr); 
        } 
    } 
    
    //verbose级别的日志
    public static void verbose(String tag, String msg) { 
        if(DEBUG) { 
            android.util.Log.v(tag, msg); 
        } 
    } 
    
    public static void verbose(String tag, String msg, Throwable tr) { 
        if(DEBUG) { 
            android.util.Log.v(tag, msg, tr); 
        } 
    } 
    
    //info级别的日志
    public static void info(String tag, String msg) { 
        if(DEBUG) { 
            android.util.Log.i(tag, msg); 
        } 
    } 
    
    public static void info(String tag, String msg, Throwable tr) { 
        if(DEBUG) { 
            android.util.Log.i(tag, msg, tr); 
        } 
    } 
    //warn级别的日志
    public static void warn(String tag, String msg) { 
        if(DEBUG) { 
            android.util.Log.w(tag, msg); 
        } 
    } 
    
    public static void warn(String tag, String msg, Throwable tr) { 
        if(DEBUG) { 
            android.util.Log.w(tag, msg, tr); 
        } 
    } 
    
    public static void warn(String tag, Throwable tr) { 
        if(DEBUG) { 
            android.util.Log.w(tag, tr); 
        } 
    } 
    
    //error级别的日志
    public static void error(String tag, String msg) { 
        if(DEBUG) { 
            android.util.Log.e(tag, msg); 
        } 
    } 
    
    public static void error(String tag, String msg, Throwable tr) { 
        if(DEBUG) { 
            android.util.Log.e(tag, msg, tr); 
        } 
    } 
}
