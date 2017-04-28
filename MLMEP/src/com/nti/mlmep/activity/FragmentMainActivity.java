package com.nti.mlmep.activity;

import com.nti.mlmep.R;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.util.ExitApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMainActivity extends Activity implements FragmentCallBack {

	/** �ײ����� */
	private RadioGroup fmainTab;
	private RadioButton fradio_button_alarm;
	private RadioButton fradio_button_home;
	private RadioButton fradio_button_monitor;
	private RadioButton fradio_button_complain;
	private RadioButton fradio_button_logoff;

	private FragmentManager fm;
	private FragmentTransaction ft;

	TrackInfoBean alarmInfo = null;

	Button title_back;

	// UserInfo userinfo;

	// ImageAdapter imageAdapter;
	// GridView gridview;
	static String msg;
	Button button1;
	static TextView layout_title_tv;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setTitle("�������������ܿ�ƽ̨")
					.setIcon(R.drawable.ic_launcher)
					.setCancelable(false)
					.setMessage("ȷ���˳���")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									// TODO Auto-generated method stub
									ExitApplication.getInstance().exit();//ȫ������ջ��activity
								}

							}).setNegativeButton("ȡ��", null);
			builder.show();

			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	public void setTitle2(int name) {
		this.layout_title_tv.setText(name);
		msg = layout_title_tv.getText().toString();
	}

	public static String getTitle2() {
		return layout_title_tv.getText().toString();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main_fg);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		
		ExitApplication.getInstance().addActivity(this); //��ջ��������

		fmainTab = (RadioGroup) findViewById(R.id.fmain_radiogroup);
		layout_title_tv = (TextView) findViewById(R.id.layout_title_tv);

		// mainTab.setOnCheckedChangeListener(this);
		fmainTab.setBackgroundResource(R.drawable.tab_title);
		fm = getFragmentManager();
		fmainTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				// ��ҳ
				case R.id.fradio_button_alarm:
					setTitle2(R.string.menu_text_alarm);
					homePage();
					break;
				// ����
				case R.id.fradio_button_home:
					setTitle2(R.string.menu_text_home);
					trackPage();
					break;
				// ����
				case R.id.fradio_button_monitor:
					setTitle2(R.string.menu_text_monitor);
					assessPage();
					break;
				// Ͷ��
				case R.id.fradio_button_complain:
					setTitle2(R.string.menu_text_complain);
					changeComplain2(alarmInfo);
					break;
				// ��Ϣ
				case R.id.fradio_button_logoff:
					setTitle2(R.string.menu_text_access);
					messagePage();
					break;
				default:
					break;
				}
			}
		});
		/** Ĭ��ѡ���һ�� */
		homePage();
	}

	/*
	 * ��Ϣҳ
	 */
	protected void messagePage() {
		// TODO Auto-generated method stub
		ft = fm.beginTransaction();
		MessageFragment tab1 = new MessageFragment();
		Bundle bundle = new Bundle();
		bundle.putString("flag_page", "��Ϣ");
		tab1.setArguments(bundle);
		ft.replace(R.id.frame_container, tab1);
		ft.commit();
	}

	/*
	 * ����ҳ
	 */
	private void assessPage() {
		// TODO Auto-generated method stub
		ft = fm.beginTransaction();
		AssessSearchFragment tab1 = new AssessSearchFragment();
		Bundle bundle = new Bundle();
		// bundle.putString("flag_page", "����");
		// bundle.putSerializable("map", alarmInfo);
		tab1.setArguments(bundle);
		ft.replace(R.id.frame_container, tab1);
		// ft.addToBackStack(null);
		ft.commit();
	}

	/*
	 * ����ҳ
	 */
	protected void trackPage() {
		// TODO Auto-generated method stub
		ft = fm.beginTransaction();
		TabFragment tab1 = new TabFragment();
		Bundle bundle = new Bundle();
		bundle.putString("flag_page", "����");
		tab1.setArguments(bundle);
		ft.replace(R.id.frame_container, tab1);
		ft.commit();
	}

	/*
	 * ��ҳ
	 */
	protected void homePage() {
		// TODO Auto-generated method stub
		title_back = (Button) findViewById(R.id.title_back);
		title_back.setVisibility(View.GONE);
		ft = fm.beginTransaction();
		TabFragment tab1 = new TabFragment();
		Bundle bundle = new Bundle();
		bundle.putString("flag_page", "��ҳ");
		tab1.setArguments(bundle);
		ft.replace(R.id.frame_container, tab1);
		ft.commit();
	}

	/*
	 * Ͷ��ҳ
	 */
	private void changeComplain() {

		ft = fm.beginTransaction();
		ComplainSearchFragment tab1 = new ComplainSearchFragment();
		Bundle bundle = new Bundle();
		// bundle.putString("flag_page", "Ͷ��");
		//bundle.putSerializable("map", alarmInfo);
		tab1.setArguments(bundle);
		ft.replace(R.id.frame_container, tab1);
		ft.commit();
	}

	@Override
	public void callbackFun1(Bundle bundle) {
		// TODO Auto-generated method stub
		String str = bundle.getString("change_title");
		// int str2 = bundle.getInt("change_title_track");
		if (str.equals("change_title")) {
			setTitle2(R.string.menu_text_home);
		}
		if (str.equals("change_title_track")) {
			if (layout_title_tv.getText().toString().equals("����")) {
				setTitle2(R.string.menu_text_home_track);
			}
		}
		if (str.equals("change_title_complain")) {
			setTitle2(R.string.menu_text_complain);
		}
		if (str.equals("change_back")) {
			title_back = (Button) findViewById(R.id.title_back);
			title_back.setVisibility(View.VISIBLE);
			title_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (layout_title_tv.getText().toString().equals("��������")) {
						setTitle2(R.string.menu_text_home);
					}
					// setTitle2(R.string.menu_text_home);
					FragmentManager manager = getFragmentManager();
					manager.popBackStack();
				}
			});
		}
		if (str.equals("change_back_home")) {
			// layout_title_tv.setText(R.string.menu_text_alarm);
			title_back = (Button) findViewById(R.id.title_back);
			title_back.setVisibility(View.VISIBLE);
			title_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					setTitle2(R.string.menu_text_alarm);
					fradio_button_alarm = (RadioButton) findViewById(R.id.fradio_button_alarm);
					fradio_button_alarm.setChecked(true);
					homePage();
				}
			});
		}

	}

	@Override
	public void callbackFun2(Bundle bundle) {
		// TODO Auto-generated method stub
		String str = bundle.getString("tab_flag_page");
		if (str.equals("wuliu")) {
			fradio_button_home = (RadioButton) findViewById(R.id.fradio_button_home);
			fradio_button_home.setChecked(true);
			setTitle2(R.string.menu_text_home);
			trackPage();
		}
		if (str.equals("pingjia")) {
			fradio_button_monitor = (RadioButton) findViewById(R.id.fradio_button_monitor);
			fradio_button_monitor.setChecked(true);
			setTitle2(R.string.menu_text_monitor);
			// assessPage();
		}
		if (str.equals("tousu")) {
			fradio_button_complain = (RadioButton) findViewById(R.id.fradio_button_complain);
			fradio_button_complain.setChecked(true);
			setTitle2(R.string.menu_text_complain);
			changeComplain();
		}
		if (str.equals("xiaoxi")) {
			fradio_button_logoff = (RadioButton) findViewById(R.id.fradio_button_logoff);
			fradio_button_logoff.setChecked(true);
			setTitle2(R.string.menu_text_access);
			//messagePage();
		}
	}

	// ����--ʵ�ֽӿڻص�
	@Override
	public void callbackFun3(Bundle bundle) {
		// TODO Auto-generated method stub
		// String str = bundle.getString("tab_flag_page");
		TrackInfoBean alarmInfo = (TrackInfoBean) bundle.getSerializable("map");
		fradio_button_monitor = (RadioButton) findViewById(R.id.fradio_button_monitor);
		fradio_button_monitor.setChecked(true);
		setTitle2(R.string.menu_text_monitor);
		assessPage2(alarmInfo);
	}

	// �˷������ڴ�������������ת������ҳʹ�ã�������������Ϣ
	private void assessPage2(TrackInfoBean alarmInfo) {
		// TODO Auto-generated method stub
		if (alarmInfo != null) {
			ft = fm.beginTransaction();
			Assess2Fragment tab1 = new Assess2Fragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("map", alarmInfo);
			tab1.setArguments(bundle);
			ft.replace(R.id.frame_container, tab1);
			ft.addToBackStack(null);
			ft.commit();
		} else {

			ft = fm.beginTransaction();
			AssessSearchFragment tab1 = new AssessSearchFragment();
			Bundle bundle = new Bundle();
			// bundle.putSerializable("map", alarmInfo);
			tab1.setArguments(bundle);
			ft.replace(R.id.frame_container, tab1);
			ft.addToBackStack(null);
			ft.commit();
		}
	}

	/*
	 * Ͷ��ҳ
	 */
	private void changeComplain2(TrackInfoBean alarmInfo) {

		if (alarmInfo != null) {

			ft = fm.beginTransaction();
			ComplainFragment tab1 = new ComplainFragment();
			Bundle bundle = new Bundle();
			// bundle.putString("flag_page", "Ͷ��");
			bundle.putSerializable("map", alarmInfo);
			tab1.setArguments(bundle);
			ft.replace(R.id.frame_container, tab1);
			ft.addToBackStack(null);
			ft.commit();
		} else {
			ft = fm.beginTransaction();
			ComplainSearchFragment tab1 = new ComplainSearchFragment();
			Bundle bundle = new Bundle();
			// bundle.putString("flag_page", "Ͷ��");
			// bundle.putSerializable("map", alarmInfo);
			tab1.setArguments(bundle);
			ft.replace(R.id.frame_container, tab1);
			ft.addToBackStack(null);
			ft.commit();
		}

	}

	// Ͷ��--ʵ�ֽӿڻص�
	@Override
	public void callbackFun4(Bundle bundle) {
		// TODO Auto-generated method stub
		TrackInfoBean alarmInfo = (TrackInfoBean) bundle.getSerializable("map");
		fradio_button_complain = (RadioButton) findViewById(R.id.fradio_button_complain);
		fradio_button_complain.setChecked(true);
		setTitle2(R.string.menu_text_complain);
		changeComplain2(alarmInfo);
	}

}
