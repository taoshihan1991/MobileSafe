package com.qingguow.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecGuide3Activity extends BaseSecGuideActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find3);
	}

	public void nextStep(View v) {
		showNext();
	}

	public void preStep(View v) {
		showPre();
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this, SecGuide2Activity.class);
		startActivity(intent);
		finish();		
	}

	@Override
	public void showNext() {
		Intent intent = new Intent(this, SecGuide4Activity.class);
		startActivity(intent);
		finish();		
	}
}
