package com.hasandayan.cari.util;

public class DataUtil {

	public static boolean nonNull(String value) {
		return !isNull(value);
	}
	
	public static boolean isNull(String value) {
		
		if(value == null)
			return true;
		
		if (value.trim().isEmpty())
			return true;
		
		return false;
	}

}
