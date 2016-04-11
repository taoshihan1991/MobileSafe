package com.qingguow.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class SecGuide4Activity extends BaseSecGuideActivity {
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find4);
	}

	public void preStep(View v) {
		showPre();
	}
	
	public void nextStep(View v) {
		showNext();
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this, SecGuide3Activity.class);
		startActivity(intent);
		finish();		
	}

	@Override
	public void showNext() {
		sp=getSharedPreferences("config", MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putBoolean("guided", true);
		editor.commit();
		Intent intent = new Intent(this, LostFindActivity.class);
		startActivity(intent);
		finish();
	}
}
