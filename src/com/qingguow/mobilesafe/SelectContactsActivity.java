package com.qingguow.mobilesafe;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qingguow.mobilesafe.utils.PhoneContactsUtil;
/**
 * 选择联系人
 * @author taoshihan
 *
 */
public class SelectContactsActivity extends Activity {
	private ListView lv_select_contacts;
	private List<Map<String,String>> contacts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contacts);
		lv_select_contacts=(ListView) findViewById(R.id.lv_select_contacts);
		contacts=PhoneContactsUtil.getContacts(this);
		lv_select_contacts.setAdapter(new SimpleAdapter(this, contacts, R.layout.select_contacts_item, new String[]{"name","phone"}, new int[]{R.id.tv_contact_name,R.id.tv_contact_phone}));
		lv_select_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String phone=contacts.get(position).get("phone");
				Intent data=new Intent();
				data.putExtra("phone", phone);
				setResult(0, data);
				finish();
			}
		});
	}
}
