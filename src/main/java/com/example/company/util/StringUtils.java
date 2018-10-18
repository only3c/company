package com.example.company.util;

public class StringUtils {
	public static boolean isBlank(String args) {
		if(args==null || args.equals(""))
			return true;
		return false;
	}
	
	public static boolean isNotBlank(String args) {
		if(args==null || args.equals(""))
			return false;
		return true;
	}

	public static final String makeLogStr(Object... args) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, imax = args.length; i < imax; i++) {
			sb.append(args[i].toString());
		}

		return sb.toString();
	}
	public static final String addLLetterForStr(String str, int length, String letter) {
		int strLength = str.length();
		StringBuffer sb = null;
		while (strLength < length) {
			sb = new StringBuffer();
			sb.append(letter).append(str); //左
			str = sb.toString();
			strLength = str.length();
		}
		return str;
	}
	public static final String addRLetterForStr(String str, int length, String letter) {
		int strLength = str.length();
		StringBuffer sb = null;
		while (strLength < length) {
			sb = new StringBuffer();
			sb.append(str).append(letter); //右
			str = sb.toString();
			strLength = str.length();
		}
		return str;
	}

}

