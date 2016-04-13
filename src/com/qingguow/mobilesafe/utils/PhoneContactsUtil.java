package com.qingguow.mobilesafe.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
/**
 * 读取手机联系人
 * @author taoshihan
 *
 */
public class PhoneContactsUtil {
	public static List<Map<String,String>> getContacts(Context context){
		ContentResolver resolver=context.getContentResolver();
        Uri uri=Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri=Uri.parse("content://com.android.contacts/data");
        List<Map<String,String>> contacts=new ArrayList<Map<String,String>>();
        
        //循环联系人表
        Cursor cursor=resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        while(cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndex("contact_id"));
            if(id!=null){
            	Map<String,String> contact=new HashMap<String,String>();
            	//查找数据表
                Cursor dataCursor=resolver.query(dataUri, new String[]{"data1","mimetype"},"raw_contact_id=?", new String[]{id}, null);
                while(dataCursor.moveToNext()){
                    String data1=dataCursor.getString(dataCursor.getColumnIndex("data1"));
                    String mimetype=dataCursor.getString(dataCursor.getColumnIndex("mimetype")); 
                    System.out.println("data1:"+data1+",mimetype:"+mimetype);
                    if(mimetype.equals("vnd.android.cursor.item/name")){
                    	contact.put("name", data1);
                    }else if(mimetype.equals("vnd.android.cursor.item/phone_v2")){
                    	contact.put("phone", data1);
                    }
                }
                contacts.add(contact);
                dataCursor.close();
            }
        }
        cursor.close();
		return contacts;
	}
}
