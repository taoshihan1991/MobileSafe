package com.qingguow.mobilesafe;

import com.qingguow.mobilesafe.utils.NumberQueryAddressUtil;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberQueryActivity extends Activity {
	private EditText et_phone;
	private TextView tv_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_query);
		et_phone = (EditText) findViewById(R.id.et_phone);
		tv_address = (TextView) findViewById(R.id.tv_address);
	}

	/**
	 * 查询归属地
	 */
	public void queryNumber(View v) {
		String phone = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(this, "请输入手机号码", 0).show();
			return;
		}
		String result=NumberQueryAddressUtil.queryAddress(phone);
		tv_address.setText(result);
	}

}
