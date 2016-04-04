package com.qingguow.mobilesafe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.qingguow.mobilesafe.utils.StreamTools;

public class SplashActivity extends Activity {
	private static final String TAG = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int VERSION_UPDATE = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROE = 4;
	private TextView tv_splash_version;
	private String description;
	private String apkurl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("�汾��" + getVersionName());
		// ������
		checkVersion();
		//���涯��
		AlphaAnimation aa=new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(1000);
		findViewById(R.id.rl_splash_root).setAnimation(aa);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ENTER_HOME:
				enterHome();
				break;
			case VERSION_UPDATE:
				Toast.makeText(getApplicationContext(), description, 0).show();
				break;
			case URL_ERROR:
				Toast.makeText(getApplicationContext(), "URL����", 0).show();
				enterHome();
				break;
			case NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "�������", 0).show();
				enterHome();
				break;
			case JSON_ERROE:
				Toast.makeText(getApplicationContext(), "JSON��������", 0).show();
				enterHome();
				break;
			
			}
		}

		
	};
	/**
	 * ������ҳ
	 */
	private void enterHome() {
		Intent intent =new Intent(SplashActivity.this,HomeActivity.class);
		startActivity(intent);
		finish();
	};
	/**
	 * ����°汾
	 */
	private void checkVersion() {
		new Thread() {
			public void run() {
				long startTime=System.currentTimeMillis();
				Message mes = Message.obtain();
				URL url;
				try {
					url = new URL(getString(R.string.serverurl));
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(4000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream is = conn.getInputStream();
						String result = StreamTools.readInputStream(is);
						JSONObject json = new JSONObject(result);
						String newVersion = (String) json.get("version");
						if (newVersion.equals(getVersionName())) {
							// ����������
							mes.what = ENTER_HOME;
						} else {
							// �汾����
							mes.what = VERSION_UPDATE;
							description=(String) json.get("description");
							apkurl=(String) json.get("apkurl");
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Log.i(TAG, "URL����");
					mes.what = URL_ERROR;
				} catch (IOException e) {
					e.printStackTrace();
					Log.i(TAG, "�������Ӵ���");
					mes.what = NETWORK_ERROR;
				} catch (JSONException e) {
					e.printStackTrace();
					Log.i(TAG, "JSON��������");
					mes.what = JSON_ERROE;
				} finally {
					//�ӳ�Ч��
					long endTime=System.currentTimeMillis();
					long dTime=endTime-startTime;
					if(dTime<3000){
						try {
							Thread.sleep(3000-dTime);
						} catch (InterruptedException e) {
						}
					}
					handler.sendMessage(mes);
				}

			};
		}.start();

	}

	// ���Ӧ�ð汾����
	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
}
