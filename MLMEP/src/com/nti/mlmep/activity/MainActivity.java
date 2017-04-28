package com.nti.mlmep.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.nti.mlmep.R;

/**
 * @author sunsi
 * @date 2011-05-18
 */
public class MainActivity extends TabActivity implements
		OnCheckedChangeListener {
	private RadioGroup mainTab;
	private TabHost mTabHost;

	// 内容Intent
	private Intent mHomeIntent;
	private Intent mMonitorIntent;
	//private Intent mAlarmIntent;
	//private Intent mAccessIntent;
	//private Intent mMoreIntent;

	private final static String TAB_TAG_HOME = "tab_tag_home";
	private final static String TAB_TAG_MONITOR = "tab_tag_monitor";
	//private final static String TAB_TAG_ALARM = "tab_tag_alerm";
	//private final static String TAB_TAG_ACCESS = "tab_tag_access";
	//private final static String TAB_TAG_MORE = "tab_tag_more";

	//private PmcDataCache pdc;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);   
		
		//pdc = (PmcDataCache) getApplicationContext();
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		
		mainTab.setOnCheckedChangeListener(this);
		mainTab.setBackgroundResource(R.drawable.title);
		
		prepareIntent();
		setupIntent();

		// 打开时默认显示主页
		mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
	}

	/**
	 * 准备tab的内容Intent
	 */
	private void prepareIntent() {
		mHomeIntent = new Intent(this, TrackMainActivity.class);
		mMonitorIntent = new Intent(this, ServiceEvaluationActivity.class);
		//mAlarmIntent = new Intent(this, AlarmHistoryActivity.class);
		//mAccessIntent = new Intent(this, AccessActivity.class);
		//mMoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));  
	}

	@SuppressWarnings("deprecation")
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		mTabHost.setBackgroundResource(R.drawable.title);
		localTabHost.addTab(buildTabSpec(TAB_TAG_HOME, R.string.menu_text_home,
				R.drawable.menu_home, mHomeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_MONITOR,
				R.string.menu_text_monitor, R.drawable.menu_monitor,
				mMonitorIntent));
		//localTabHost.addTab(buildTabSpec(TAB_TAG_ALARM,
		//		R.string.menu_text_alarm, R.drawable.menu_alarm, mAlarmIntent));
		//localTabHost.addTab(buildTabSpec(TAB_TAG_ACCESS,
		//		R.string.menu_text_access, R.drawable.menu_access,
		//		mAccessIntent));
		//localTabHost.addTab(buildTabSpec(TAB_TAG_MORE, R.string.menu_text_more,
		//		R.drawable.menu_more, mMoreIntent));
	}

	/**
	 * 构建TabHost的Tab页
	 * 
	 * @param tag
	 *            标记
	 * @param resLabel
	 *            标签
	 * @param resIcon
	 *            图标
	 * @param content
	 *            该tab展示的内容
	 * @return 一个tab
	 */
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		TextView title = (TextView) findViewById(R.id.layout_title_tv);
		switch (checkedId) {
		case R.id.radio_button_home:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			title.setText(R.string.menu_text_home);
			
			//mainTab.setBackgroundResource(R.drawable.title);
			break;
		case R.id.radio_button_monitor:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_MONITOR);
			title.setText(R.string.menu_text_monitor);
			
			//mainTab.setBackgroundResource(R.drawable.title);
			break;
		//case R.id.radio_button_alarm:
		//	this.mTabHost.setCurrentTabByTag(TAB_TAG_ALARM);
		//	title.setText(R.string.menu_text_alarm);
		//	break;
		//case R.id.radio_button_logoff:
		//	this.mTabHost.setCurrentTabByTag(TAB_TAG_ACCESS);
		//	title.setText(R.string.menu_text_access);
		//	break;
		//case R.id.radio_button_more:
		//	this.mTabHost.setCurrentTabByTag(TAB_TAG_MORE);
		//	title.setText(R.string.menu_text_more);
		//	break;
		}
	}

	

}