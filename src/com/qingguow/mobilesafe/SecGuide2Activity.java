package com.qingguow.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.qingguow.mobilesafe.ui.SettingItemView;

public class SecGuide2Activity extends BaseSecGuideActivity {
	private SettingItemView siv_sim;
	private SharedPreferences sp;
	private TelephonyManager telephonyManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find2);
		siv_sim = (SettingItemView) findViewById(R.id.siv_sim);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		// 根据保存的数据设置状态
		String sim = sp.getString("sim", "");
		if (TextUtils.isEmpty(sim)) {
			siv_sim.setChecked(false);
		} else {
			siv_sim.setChecked(true);
		}
		// 绑定sim卡
		siv_sim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String sim = telephonyManager.getSimSerialNumber();
				Editor editor = sp.edit();
				String simSp = sp.getString("sim", "");
				if (TextUtils.isEmpty(simSp)) {
					siv_sim.setChecked(true);
					editor.putString("sim", sim);
				} else {
					siv_sim.setChecked(false);
					editor.putString("sim", "");
				}

				editor.commit();

			}
		});
	}

	public void nextStep(View v) {
		
		showNext();
	}

	public void preStep(View v) {
		showPre();
	}

	@Override
	public void showNext() {
		String sim = sp.getString("sim", "");
		if (TextUtils.isEmpty(sim)) {
			Toast.makeText(this, "sim卡没有绑定", 1).show();
			return;
		}
		
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
