package com.qingguow.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecGuide1Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find);
	}
	
	public void nextStep(View v) {
		Intent intent=new Intent(this,SecGuide2Activity.class);
		startActivity(intent);
		finish();
	}
}
