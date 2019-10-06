package com.myboot.dataprocess.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class RandomCharacterBuilder {
	
	public static String getRandomStr(int num) {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<num;i++) {
			sb.append(String.valueOf(getRandomChar()));
		}
		return sb.toString();
	}
	
	private static char getRandomChar() {
		String str = "";
		int hightPos; //
		int lowPos;
		Random random = new Random();
		hightPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = (161 + Math.abs(random.nextInt(93)));
		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(hightPos)).byteValue();
		b[1] = (Integer.valueOf(lowPos)).byteValue();
		try {
		  str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
		  e.printStackTrace();
		  System.out.println("错误");
		}
		    return str.charAt(0);
	}
	
	/**
	 * 随机获取一串汉字
	 * @return
	 */
			
	public static String getRandomString(int num) {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<num;i++) {
			char c = (char)(0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
			sb.append(String.valueOf(c));
		}
		return sb.toString();
        //return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }
	
	public static void main(String[] args) {
		System.out.println(getRandomStr(24));
		System.out.println(getRandomString(24));
	}

}
