package com.qingguow.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecGuide3Activity extends BaseSecGuideActivity {
	private EditText et_sec_phone;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find3);
		et_sec_phone = (EditText) findViewById(R.id.et_sec_phone);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		String phone = sp.getString("secphone", "");
		et_sec_phone.setText(phone);
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
		String phone = et_sec_phone.getText().toString().trim();

		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "请填写安全号码", 1).show();
			return;
		}

		Editor editor = sp.edit();
		editor.putString("secphone", phone);
		editor.commit();
		Intent intent = new Intent(this, SecGuide4Activity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 选择联系人
	 */
	public void selectContacts(View v) {
		Intent intent = new Intent(this, SelectContactsActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			String phone = data.getStringExtra("phone").replace("-", "")
					.replace(" ", "");
			et_sec_phone.setText(phone);
		}
	}
}
