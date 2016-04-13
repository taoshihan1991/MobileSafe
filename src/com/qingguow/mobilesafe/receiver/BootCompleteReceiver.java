package com.qingguow.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
	private TelephonyManager tm;
	private SharedPreferences sp;
	@Override
	public void onReceive(Context cotext, Intent arg1) {
		System.out.println("�������ֻ�����");
		sp=cotext.getSharedPreferences("config", Context.MODE_PRIVATE);
		//��ȡ��ǰ��sim�����к�
		tm=(TelephonyManager) cotext.getSystemService(Context.TELEPHONY_SERVICE);
		String relSim=tm.getSimSerialNumber();
		//��ȡ֮ǰ�����
		String saveSim=sp.getString("sim", "");
		//�жϺ󷢶���
		if(saveSim.equals(relSim)){
			System.out.println("sim������");
		}else{
			//���ͱ�����Ϣ����ȫ����
			System.out.println("sim�������");
			Toast.makeText(cotext, "sim�������", 1).show();
		}
	}

}
