package com.qingguow.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

/**
 * 手机防盗界面
 * 
 * @author taoshihan
 * 
 */
public class LostFindActivity extends Activity {
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean guided = sp.getBoolean("guided", false);
		if (guided) {
			setContentView(R.layout.activity_lost_find);
		} else {
			Intent intent = new Intent(this, SecGuide1Activity.class);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * 重新设置
	 * 
	 * @param v
	 */
	public void reConfig(View v) {
		Intent intent = new Intent(this, SecGuide1Activity.class);
		startActivity(intent);
		finish();
	}
}
