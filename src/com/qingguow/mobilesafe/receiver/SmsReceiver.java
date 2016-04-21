package com.qingguow.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.qingguow.mobilesafe.R;
import com.qingguow.mobilesafe.service.GPSService;

public class SmsReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		//��ȡ��������
		Object[] objs=(Object[]) intent.getExtras().get("pdus");
		for(Object obj:objs){
			SmsMessage sms=SmsMessage.createFromPdu((byte[])obj);
			String body=sms.getMessageBody();
			String sender=sms.getOriginatingAddress();
			String secSender=sp.getString("secphone", "");
			//�ж��ǰ�ȫ����Ķ���
			if(secSender.equals(sender)){
				switch (body) {
				case "#*alarm*#"://���ͱ�������
					//Toast.makeText(context, "���ű�������", 1).show();
					MediaPlayer mp=MediaPlayer.create(context, R.raw.alarm);
					mp.start();
					abortBroadcast();
					break;
				case "#*location*#"://�õ�λ����Ϣ
					Intent intent1=new Intent(context,GPSService.class);
					context.startService(intent1);
					String lastLocation= sp.getString("lastlocation", "");
					//���Ͷ���
					if(TextUtils.isEmpty(lastLocation)){
						SmsManager.getDefault().sendTextMessage(sender, null,"getting location", null, null);
					}else{
						SmsManager.getDefault().sendTextMessage(sender, null,lastLocation, null, null);
					}
					System.out.println("��ȡλ����Ϣ"+lastLocation);
					abortBroadcast();
					break;
				default:
					break;
				}
			}
		}
	}

}
