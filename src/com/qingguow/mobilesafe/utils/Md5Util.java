package com.qingguow.mobilesafe.utils;

import java.security.MessageDigest;

public class Md5Util {
	/**
	 * ��ȡMD5�����ַ���
	 * @param pass
	 * @return
	 */
	public static String md5Password(String pass){
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("md5");
			byte[] bytes=messageDigest.digest(pass.getBytes());
			StringBuffer sb=new StringBuffer();
			for(byte b:bytes){
				int number=b & 0xff;
				String str=Integer.toHexString(number);
				if(str.length()==1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}

}
