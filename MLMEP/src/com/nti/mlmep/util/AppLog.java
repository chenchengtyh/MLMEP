package com.nti.mlmep.util;


/**
 * Ӧ�ó�����־��ӡ
 * Debugʱ�� DEBUG��Ϊ true���ɴ�Log���
 * Releaseʱ�� DEBUG��Ϊ false���ɹر� Log���
 * @author wangge
 *
 */
public class AppLog {
	
	//��Release�汾����Ͻ� DEBUG ��Ϊ false���ɹر� Log���
	private static final boolean DEBUG = true; 
	
	//debug�������־
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
    
    //verbose�������־
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
    
    //info�������־
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
    //warn�������־
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
    
    //error�������־
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
