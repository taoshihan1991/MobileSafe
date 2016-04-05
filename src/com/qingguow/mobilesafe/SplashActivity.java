package com.qingguow.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
	private TextView tv_show_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("�汾��" + getVersionName());
		// ������
		checkVersion();
		// ���涯��
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(1000);
		findViewById(R.id.rl_splash_root).setAnimation(aa);
		tv_show_progress=(TextView) findViewById(R.id.tv_show_progress);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ENTER_HOME:
				enterHome();
				break;
			case VERSION_UPDATE:
				// ������ʾ
				AlertDialog.Builder builder = new Builder(SplashActivity.this);
				builder.setTitle("��ʾ����");
				builder.setMessage(description);
				builder.setCancelable(false);
				builder.setPositiveButton("��������", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							 FinalHttp finalhttp=new FinalHttp();
							 finalhttp.download(apkurl,Environment.getExternalStorageDirectory()+"/mobilesafe2.0.apk",new AjaxCallBack<File>(){
								 //����ʧ��
								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									t.printStackTrace();
									Toast.makeText(getApplicationContext(), "����ʧ��",
											1).show();
									super.onFailure(t, errorNo, strMsg);
									enterHome();
								}
								//��������
								@Override
								public void onLoading(long count, long current) {
									int precent=(int)(current*100/count);
									tv_show_progress.setText("�������أ�"+precent+"%");
									super.onLoading(count, current);
								}
								//���سɹ�
								@Override
								public void onSuccess(File t) {
									Intent intent=new Intent();
									intent.setAction("android.intent.action.VIEW");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
									startActivity(intent);
									super.onSuccess(t);
								}
								 
							 });
						} else {
							Toast.makeText(getApplicationContext(), "δ��⵽SD��",
									1).show();
						}

					}
				});
				builder.setNegativeButton("�Ժ���˵", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						enterHome();
					}
				});
				builder.show();
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
		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	};

	/**
	 * ����°汾
	 */
	private void checkVersion() {
		new Thread() {
			public void run() {
				long startTime = System.currentTimeMillis();
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
							description = (String) json.get("description");
							apkurl = (String) json.get("apkurl");
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
					// �ӳ�Ч��
					long endTime = System.currentTimeMillis();
					long dTime = endTime - startTime;
					if (dTime < 3000) {
						try {
							Thread.sleep(3000 - dTime);
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
