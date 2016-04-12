package com.qingguow.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class BaseSecGuideActivity extends Activity {
	// ��������ʶ����
	protected GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ʵ����
		gestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						//����б�Ż�
						if(Math.abs(e1.getRawY()-e2.getRawY())>100){
							return true;
						}
						if ((e1.getRawX() - e2.getRawX()) > 100) {
							System.out.println("�������󻮣���һҳ");
							showNext();
							return true;
						}
						if ((e2.getRawX() - e1.getRawX()) > 100) {
							System.out.println("�������һ�����һҳ");
							showPre();
							return true;
						}
						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});
	}
	public abstract void showPre();
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	public abstract void showNext();
}
