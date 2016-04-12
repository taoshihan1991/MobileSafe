package com.qingguow.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
/**
 * 选择联系人
 * @author taoshihan
 *
 */
public class SelectContactsActivity extends Activity {
	private ListView lv_select_contacts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contacts);
		lv_select_contacts=(ListView) findViewById(R.id.lv_select_contacts);
	}
}
