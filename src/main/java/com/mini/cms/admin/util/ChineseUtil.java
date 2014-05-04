package com.mini.cms.admin.util;

import java.util.Date;
import java.util.Random;

/**
 * 中文GB2312编码范围：<br/>
 * B0A1 --- F7FE(包括B0A1和F7FE)<br/>
 * 其中： B0A1到D7F9之间为常用字， D8A1到F7FE为其它偏门字
 * @author Eddy
 *
 */
public class ChineseUtil {
	
	private static Random random = null;
	
	private static Random getRandomInstance() {
		if(random == null) {
			random = new Random(new Date().getTime());
		}
		return random;
	}
	
	public static String getARandomChineseWord() throws Exception {
		Random random = getRandomInstance();
		Integer highPos = 176 + Math.abs(random.nextInt(39));//即得到高官节: >=B0 <=D7
		Integer lowPos = 161 + Math.abs(random.nextInt(93));//得到低字节：>=A1 <=FE
		byte[] b = new byte[2];
		b[0] = highPos.byteValue();
		b[1] = lowPos.byteValue();
		String s = new String(b, "GB2312");
		return s;
	}
	
	public static String getFixedLengthChinese(int length) throws Exception {
		String s = "";
		for(int i = 0; i < length; i++)
			s += getARandomChineseWord();
		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(ChineseUtil.getFixedLengthChinese(3));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
