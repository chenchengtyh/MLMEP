package com.nti.mlmep.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class StringUtil {
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isBlank(Object str) {
		return str == null || "".equals(str);
	}

	public static String getNonNullStr(Object o) {
		if (o == null) {
			return "";
		} else {
			return String.valueOf(o);
		}
	}

	public static boolean equals(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (!isEmpty(str1) && !isEmpty(str2) && str1.equals(str2)) {
			return true;
		}
		return false;
	}

	// If string is less than aLength, pad on left.
	public static String lPad(String aString, int aLength, String aPadCharacter) {
		if (aString == null)
			return null;
		int len = aString.length();
		StringBuffer pad = new StringBuffer("");
		if (aLength > len) {
			for (int i = 1; i <= aLength - len; i++)
				pad.append(aPadCharacter);
			return pad.toString() + aString;
		}
		return aString;
	}

	/**
	 * 去除字符；
	 * 
	 * @param vStr
	 * @return
	 */
	public static String removeSymbol(String vStr) {
		if (!isEmpty(vStr)) {
			String regEx = "[-`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(vStr);
			return m.replaceAll("").trim();
		}
		return vStr;
	}

	public static String subString(String vStr, int start, int end) {
		if (!isEmpty(vStr) && vStr.length() >= end) {
			return vStr.substring(start, end);
		}
		return vStr;
	}

	public static List<String> toList(String str, String separator) {
		List<String> list = new ArrayList<String>();
		if (!isEmpty(str) && str.indexOf(separator) > -1) {
			String[] arr = str.split(separator);
			for (String item : arr) {
				if (!isEmpty(item)) {
					list.add(item);
				}
			}
		}
		return list;
	}

	public static String changeCharset(String str, String charset)
			throws UnsupportedEncodingException {
		if (!isEmpty(str)) {
			byte[] bs = str.getBytes();
			return new String(bs, charset);
		}
		return null;
	}

	@SuppressLint("DefaultLocale")
	public static String getGUID() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

	/**
	 * 计算两个时间相差的天数、小时、分钟、秒数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Map<String, Long> getDateSubVal(Date start, Date end) {
		long diff = start.getTime() - end.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60))
				/ (1000 * 60);
		long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours
				* (60 * 60 * 1000) - minutes * (60 * 1000)) / 1000;
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("days", days);
		map.put("hours", hours);
		map.put("minutes", minutes);
		map.put("seconds", seconds);
		return map;
	}

	/**
	 * 返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String returnNull(String str) {
		if (!str.equals("null") && !str.equals("") && str != null) {
			return str.trim();
		} else {
			return null;
		}
	}

	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = " ";
		}
		StringBuffer sb = new StringBuffer();
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (i < len - 1) {
				sb.append(array[i]).append(separator);
			} else {
				sb.append(array[i]);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// String str = "皖C-20578/挂HC77";
		// System.out.println(changeCharset(str,"GBK"));
		// String rtn = removeSymbol(str);
		// System.out.println(subString(rtn,0, 7));
		System.out.println(getGUID());
	}

}
