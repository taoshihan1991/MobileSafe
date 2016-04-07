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
	private String desc_on;
	private String desc_off;
	/**
	 * 初始化View对象
	 * @param context
	 */
	private void initView(Context context) {
		View.inflate(context, R.layout.setting_item_view, this);
		cb_status=(CheckBox) this.findViewById(R.id.cb_status);
		tv_desc=(TextView) this.findViewById(R.id.tv_desc);
		tv_title=(TextView) this.findViewById(R.id.tv_title);
		
	}
	/**
	 * 判断是否选中
	 * @return
	 */
	public boolean isChecked(){
		return cb_status.isChecked();
	}
	/**
	 * 设置是否选中
	 * @param status
	 */
	public void setChecked(boolean status){
		if(status){
			tv_desc.setText(desc_on);
		}else{
			tv_desc.setText(desc_off);
		}
		cb_status.setChecked(status);
	}
	/**
	 * 设置显示文本
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
		String title=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.qingguow.mobilesafe", "title");
		tv_title.setText(title);
		desc_on=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.qingguow.mobilesafe", "desc_on");
		desc_off=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.qingguow.mobilesafe", "desc_off");
	}

	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

}
