package com.qingguow.mobilesafe;

import com.qingguow.mobilesafe.ui.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SecGuide2Activity extends Activity {
	private SettingItemView siv_item;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find2);
		siv_item=(SettingItemView) findViewById(R.id.siv_item);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//根据保存的数据设置状态
		boolean update=sp.getBoolean("xxx", false);
		if(update){
			siv_item.setChecked(true);
		}else{
			siv_item.setChecked(false);
		}
	}

	public void nextStep(View v) {
		Intent intent = new Intent(this, SecGuide3Activity.class);
		startActivity(intent);
		finish();
	}

	public void preStep(View v) {
		Intent intent = new Intent(this, SecGuide1Activity.class);
		startActivity(intent);
		finish();
	}
}
