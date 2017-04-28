package com.nti.mlmep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.nti.mlmep.R;
import com.nti.mlmep.activity.base.BaseActivity;
import com.nti.mlmep.util.ExitApplication;

/**
 * App启动加载页(闪屏)
 * @author wangge
 * 
 */
public class SplashScreenActivity extends BaseActivity {
	//private final int SPLASH_DISPLAY_LENGHT = 5000; // 延迟五秒
	private final int SPLASH_DISPLAY_LENGHT = 1000; // test

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		ExitApplication.getInstance().addActivity(this); //入栈用于销毁

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 启动登录界面
				Intent intent = new Intent(SplashScreenActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, SPLASH_DISPLAY_LENGHT);
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		
	}

}