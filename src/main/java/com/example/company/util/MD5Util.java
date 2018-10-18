package com.example.company.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
	private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f'};
    private static final Integer RESULTLENGTH = 32;
	public static String getMD5ofStr(String str){
		String res = StringUtils.addRLetterForStr("", RESULTLENGTH, " ");
		try {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'A', 'B', 'C', 'D', 'E', 'F' };
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(str.getBytes("UTF-8"));
			byte[] md = mdInst.digest();
			int j = md.length;
			char charArr[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				charArr[k++] = hexDigits[byte0 >>> 4 & 0xf];
				charArr[k++] = hexDigits[byte0 & 0xf];
			}
			res = new String(charArr);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.info("没有找到MD5校验算法，生成MD5校验码失败！");
		} catch (UnsupportedEncodingException e) {
			LOGGER.info("不支持获取UTF-8转码！");
		}
		return res;
	}
}
