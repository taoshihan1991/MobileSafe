package com.qingguow.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTools {
	/**
	 * ∂¡»° ‰»Î¡˜
	 * 
	 * @param is
	 * @return
	 */
	public static String readInputStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			is.close();
			byte[] res = baos.toByteArray();
			return new String(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
