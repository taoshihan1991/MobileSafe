package com.qingguow.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.qingguow.mobilesafe.ui.SettingItemView;

public class SecGuide2Activity extends BaseSecGuideActivity {
	private SettingItemView siv_item;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find2);
		siv_item=(SettingItemView) findViewById(R.id.siv_item);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//���ݱ������������״̬
		boolean update=sp.getBoolean("xxx", false);
		if(update){
			siv_item.setChecked(true);
		}else{
			siv_item.setChecked(false);
		}
	}

	public void nextStep(View v) {
		showNext();
	}

	public void preStep(View v) {
		showPre();
	}

	@Override
	public void showNext() {
		Intent intent = new Intent(this, SecGuide3Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this, SecGuide1Activity.class);
		startActivity(intent);
		finish();		
	}
}
