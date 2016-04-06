package com.qingguow.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.qingguow.mobilesafe.ui.SettingItemView;

public class SettingActivity extends Activity {
	private SettingItemView siv_item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		siv_item=(SettingItemView) findViewById(R.id.siv_item);
		//�Զ����µĵ���¼�
		siv_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(siv_item.isChecked()){
					//���ò�ѡ��
					siv_item.setChecked(false);
					siv_item.setDesc("�Զ������Ѿ��ر�");
				}else{
					//����ѡ��
					siv_item.setChecked(true);
					siv_item.setDesc("�Զ������Ѿ�����");
				}
			}
		});
	}
}
