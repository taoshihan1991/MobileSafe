package com.qingguow.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private GridView gv_home_list;
	private MyAdapter mAdapter;
	private static String[] names = { "手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计",
			"手机杀毒", "缓存清理", "高级工具", "设置中心" };
	private static int[] icons = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//初始化
		sp=getSharedPreferences("config", MODE_PRIVATE);
		gv_home_list = (GridView) findViewById(R.id.gv_home_list);
		mAdapter = new MyAdapter();
		gv_home_list.setAdapter(mAdapter);
		// 条目的点击事件
		gv_home_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0://手机防盗
					startMobileSec();
					break;
				case 8:// 设置中心
					Intent intent = new Intent(HomeActivity.this,
							SettingActivity.class);
					startActivity(intent);
					break;
				}

			}
		});
	}
	/**
	 * 打开手机防盗的对话框
	 */
	protected void startMobileSec() {
		String password=sp.getString("password", "");
		//设置密码
		if(TextUtils.isEmpty(password)){
			AlertDialog.Builder builder=new Builder(this);
			View view=View.inflate(this, R.layout.dialog_setup_password, null);
			builder.setView(view);
			builder.show();
		}else{
			//输入密码
			
		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = View.inflate(HomeActivity.this,
					R.layout.list_home_item, null);
			TextView tv = (TextView) view.findViewById(R.id.tv_listhome_name);
			tv.setText(names[arg0]);
			ImageView iv = (ImageView) view.findViewById(R.id.iv_listhome_icon);
			iv.setImageResource(icons[arg0]);
			return view;
		}
	}
}
