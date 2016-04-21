package com.qingguow.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AtoosActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	}
	/**
	 * ≤È—ØπÈ Ùµÿ
	 * @param v
	 */
	public void numberQuery(View v) {
		Intent intent=new Intent(this,NumberQueryActivity.class);
		startActivity(intent);
	}
}
