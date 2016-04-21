package com.qingguow.mobilesafe.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NumberQueryAddressUtil {
	private static final String path = "/data/data/com.qingguow.mobilesafe/files/address.db";
	/**
	 * ≤È—Ø∫≈¬ÎπÈ Ùµÿ
	 * @param phone
	 * @return
	 */
	public static String queryAddress(String phone){
		SQLiteDatabase db=SQLiteDatabase.openDatabase(path, null,SQLiteDatabase.OPEN_READONLY);
		Cursor cursor=db.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)", new String[]{phone.substring(0,7)});
		while(cursor.moveToNext()){
			String address=cursor.getString(0);
			return address;
		}
		cursor.close();
		return "";
	}
}
