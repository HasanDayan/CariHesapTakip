package com.hasandayan.cari.util;

import java.util.Base64;

public class Base64Util {

	public static String encode(Long value) {

		Base64.Encoder simpleEncoder = Base64.getEncoder().withoutPadding();

		return simpleEncoder.encodeToString(String.valueOf(value).getBytes());
	}

	public static Long decode(String value) {

		Base64.Decoder simpleDecoder = Base64.getDecoder();
		
		String decodedString = new String(simpleDecoder.decode(value.getBytes()));
		
		return Long.valueOf(decodedString);
	}

}
