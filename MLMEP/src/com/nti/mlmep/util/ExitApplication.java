package com.nti.mlmep.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

//������ȫ�˳�APPӦ��,����Ҫ�˳�����APP����Ȼ��Ҫ������ջ�д�ŵ�Activityÿ��ɱ����
public class ExitApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();
	private static ExitApplication instance;

	private ExitApplication() {
	}

	// ����ģʽ��ȡΨһ��exitapplication
	public static ExitApplication getInstance() {
		if (null == instance) {
			instance = new ExitApplication();
		}
		return instance;
	}

	// ���activity��������
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// �������е�Activiy��finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);

	}

}
