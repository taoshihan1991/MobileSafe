package com.qingguow.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecGuide1Activity extends BaseSecGuideActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find1);
	}

	public void nextStep(View v) {
		showNext();
	}
	public void showNext() {
		Intent intent=new Intent(this,SecGuide2Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		
	}
}
