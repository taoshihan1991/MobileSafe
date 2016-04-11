package com.qingguow.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class BaseSecGuideActivity extends Activity {
	// 定义手势识别器
	protected GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 实例化
		gestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						//屏蔽斜着划
						if(Math.abs(e1.getRawY()-e2.getRawY())>100){
							return true;
						}
						if ((e1.getRawX() - e2.getRawX()) > 100) {
							System.out.println("从右往左划，下一页");
							showNext();
							return true;
						}
						if ((e2.getRawX() - e1.getRawX()) > 100) {
							System.out.println("从左往右划，上一页");
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
