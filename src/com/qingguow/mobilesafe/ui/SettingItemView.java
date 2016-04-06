package com.qingguow.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qingguow.mobilesafe.R;

public class SettingItemView extends RelativeLayout {
	private TextView tv_title;
	private TextView tv_desc;
	private CheckBox cb_status;
	/**
	 * ��ʼ��View����
	 * @param context
	 */
	private void initView(Context context) {
		View.inflate(context, R.layout.setting_item_view, this);
		cb_status=(CheckBox) this.findViewById(R.id.cb_status);
		tv_desc=(TextView) this.findViewById(R.id.tv_desc);
	}
	/**
	 * �ж��Ƿ�ѡ��
	 * @return
	 */
	public boolean isChecked(){
		return cb_status.isChecked();
	}
	/**
	 * �����Ƿ�ѡ��
	 * @param status
	 */
	public void setChecked(boolean status){
		cb_status.setChecked(status);
	}
	/**
	 * ������ʾ�ı�
	 * @param text
	 */
	public void setDesc(String text){
		tv_desc.setText(text);
	}
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

}
