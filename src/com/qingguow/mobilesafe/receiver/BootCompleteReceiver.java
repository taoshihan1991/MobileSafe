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
		sp=cotext.getSharedPreferences("config", Context.MODE_PRIVATE);
		//读取当前的sim卡序列号
		tm=(TelephonyManager) cotext.getSystemService(Context.TELEPHONY_SERVICE);
		String relSim=tm.getSimSerialNumber();
		//读取之前保存的
		String saveSim=sp.getString("sim", "");
		//判断后发短信
		if(saveSim.equals(relSim)){
			
		}else{
			//发送报警短息给安全号码
			System.out.println("sim卡变更了");
			Toast.makeText(cotext, "sim卡变更了", 1).show();
		}
	}

}
