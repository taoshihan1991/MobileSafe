package com.qingguow.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * �ֻ���������
 * 
 * @author taoshihan
 * 
 */
public class LostFindActivity extends Activity {
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean guided = sp.getBoolean("guided", false);
		if (guided) {
			setContentView(R.layout.activity_lost_find);
		} else {
			Intent intent=new Intent(this,SecGuide1Activity.class);
			startActivity(intent);
			finish();
		}
	}
}
