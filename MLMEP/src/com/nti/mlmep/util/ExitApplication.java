package com.nti.mlmep.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

//用于完全退出APP应用,若想要退出整个APP，必然需要将整个栈中存放的Activity每个杀掉。
public class ExitApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();
	private static ExitApplication instance;

	private ExitApplication() {
	}

	// 单例模式获取唯一的exitapplication
	public static ExitApplication getInstance() {
		if (null == instance) {
			instance = new ExitApplication();
		}
		return instance;
	}

	// 添加activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有的Activiy并finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);

	}

	// 退出所有Activity重新登录
	public void exit_login() {
		for (Activity activity : activityList) {
			if (activity.getLocalClassName().equals(
					"activity.SplashScreenActivity")) {
			} else if (activity.getLocalClassName().equals(
					"activity.LoginActivity")) {
			} else {
				activity.finish();
			}
		}
		System.exit(0);
	}

}
