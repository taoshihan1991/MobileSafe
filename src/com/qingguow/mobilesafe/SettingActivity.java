package com.qingguow.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.qingguow.mobilesafe.ui.SettingItemView;

public class SettingActivity extends Activity {
	private SettingItemView siv_item;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		siv_item=(SettingItemView) findViewById(R.id.siv_item);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//根据保存的数据设置状态
		boolean update=sp.getBoolean("update", false);
		if(update){
			siv_item.setChecked(true);
		}else{
			siv_item.setChecked(false);
		}
		
		//自动更新的点击事件
		siv_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Editor editor=sp.edit();
				if(siv_item.isChecked()){
					//设置不选中
					siv_item.setChecked(false);
					editor.putBoolean("update", false);
				}else{
					//设置选中
					siv_item.setChecked(true);
					editor.putBoolean("update", true);
				}
				editor.commit();
			}
		});
	}
}
