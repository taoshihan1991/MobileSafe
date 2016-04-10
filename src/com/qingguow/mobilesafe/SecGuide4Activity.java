package com.qingguow.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class SecGuide4Activity extends Activity {
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find4);
	}

	public void preStep(View v) {
		Intent intent = new Intent(this, SecGuide3Activity.class);
		startActivity(intent);
		finish();
	}
	
	public void nextStep(View v) {
		sp=getSharedPreferences("config", MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putBoolean("guided", true);
		editor.commit();
		Intent intent = new Intent(this, SecGuide3Activity.class);
		startActivity(intent);
		finish();
	}
}
