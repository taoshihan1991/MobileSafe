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
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本号" + getVersionName());
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean update = sp.getBoolean("update", false);
		if (update) {
			// 检查更新
			checkVersion();
		} else {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);
		}

		// 界面动画
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(1000);
		findViewById(R.id.rl_splash_root).setAnimation(aa);
		tv_show_progress = (TextView) findViewById(R.id.tv_show_progress);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ENTER_HOME:
				enterHome();
				break;
			case VERSION_UPDATE:
				// 弹窗提示
				AlertDialog.Builder builder = new Builder(SplashActivity.this);
				builder.setTitle("提示更新");
				builder.setMessage(description);
				builder.setCancelable(false);
				builder.setPositiveButton("立即更新", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							FinalHttp finalhttp = new FinalHttp();
							finalhttp.download(apkurl,
									Environment.getExternalStorageDirectory()
											+ "/mobilesafe2.0.apk",
									new AjaxCallBack<File>() {
										// 下载失败
										@Override
										public void onFailure(Throwable t,
												int errorNo, String strMsg) {
											t.printStackTrace();
											Toast.makeText(
													getApplicationContext(),
													"下载失败", 1).show();
											super.onFailure(t, errorNo, strMsg);
											enterHome();
										}

										// 正在下载
										@Override
										public void onLoading(long count,
												long current) {
											tv_show_progress.setVisibility(View.VISIBLE);
											int precent = (int) (current * 100 / count);
											tv_show_progress.setText("正在下载："
													+ precent + "%");
											super.onLoading(count, current);
										}

										// 下载成功
										@Override
										public void onSuccess(File t) {
											Intent intent = new Intent();
											intent.setAction("android.intent.action.VIEW");
											intent.addCategory("android.intent.category.DEFAULT");
											intent.setDataAndType(
													Uri.fromFile(t),
													"application/vnd.android.package-archive");
											startActivity(intent);
											super.onSuccess(t);
										}

									});
						} else {
							Toast.makeText(getApplicationContext(), "未检测到SD卡",
									1).show();
						}

					}
				});
				builder.setNegativeButton("稍后再说", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						enterHome();
					}
				});
				builder.show();
				break;
			case URL_ERROR:
				Toast.makeText(getApplicationContext(), "URL错误", 0).show();
				enterHome();
				break;
			case NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络错误", 0).show();
				enterHome();
				break;
			case JSON_ERROE:
				Toast.makeText(getApplicationContext(), "JSON解析错误", 0).show();
				enterHome();
				break;

			}
		}

	};

	/**
	 * 进入主页
	 */
	private void enterHome() {
		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	};

	/**
	 * 检查新版本
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
							// 进入主界面
							mes.what = ENTER_HOME;
						} else {
							// 版本更新
							mes.what = VERSION_UPDATE;
							description = (String) json.get("description");
							apkurl = (String) json.get("apkurl");
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Log.i(TAG, "URL错误");
					mes.what = URL_ERROR;
				} catch (IOException e) {
					e.printStackTrace();
					Log.i(TAG, "网络连接错误");
					mes.what = NETWORK_ERROR;
				} catch (JSONException e) {
					e.printStackTrace();
					Log.i(TAG, "JSON解析错误");
					mes.what = JSON_ERROE;
				} finally {
					// 延迟效果
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

	// 获得应用版本名称
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
