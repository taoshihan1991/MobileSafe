package com.qingguow.mobilesafe.receiver;

import com.qingguow.mobilesafe.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		//获取短信内容
		Object[] objs=(Object[]) intent.getExtras().get("pdus");
		for(Object obj:objs){
			SmsMessage sms=SmsMessage.createFromPdu((byte[])obj);
			String body=sms.getMessageBody();
			String sender=sms.getOriginatingAddress();
			String secSender=sp.getString("secphone", "");
			//判断是安全号码的短信
			if(secSender.equals(sender)){
				switch (body) {
				case "#*alarm*#"://发送报警音乐
					//Toast.makeText(context, "播放报警音乐", 1).show();
					MediaPlayer mp=MediaPlayer.create(context, R.raw.alarm);
					mp.start();
					abortBroadcast();
					break;
				default:
					break;
				}
			}
		}
	}

}
