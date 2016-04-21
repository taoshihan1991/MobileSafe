package com.qingguow.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GPSService extends Service {
	private LocationManager lm;
	private LocationListener listener;
	private SharedPreferences sp;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	// ���񴴽�
	@Override
	public void onCreate() {
		super.onCreate();
		sp=getSharedPreferences("config", MODE_PRIVATE);
		// ��ȡλ�ù�����
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		listener = new MyLocationListener();
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(provider, 0, 0, listener);
		
	}

	// ��������
	@Override
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);
		listener=null;
	}

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// ��ȡ����
			String longitude = "longitude��" + location.getLongitude();
			String latitude = "latitude��" + location.getLatitude();
			String acc = "accuracy��" + location.getAccuracy();
			// ת����������
			try {
				ModifyOffset offset = ModifyOffset.getInstance(getAssets()
						.open("axisoffset.dat"));
				PointDouble pinit = offset.s2c(new PointDouble(location
						.getLongitude(), location.getLatitude()));
				longitude = "longitude��" + pinit.x;
				latitude = "latitude��" + pinit.y;
			} catch (Exception e) {
				e.printStackTrace();
			}
			//��������
			Editor editor=sp.edit();
			editor.putString("lastlocation", longitude+latitude+acc);
			editor.commit();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {

		}

	}
}
