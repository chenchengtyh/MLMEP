package com.nti.mlmep.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.domain.TrackMessage;
import com.nti.mlmep.util.ExitApplication;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class UserFragment extends Fragment implements OnClickListener {

	private LinearLayout user_message;
	private LinearLayout user_changepwd;
	private LinearLayout user_findversion;
	private LinearLayout user_version;
	private LinearLayout user_exit;
	private Button user_hasfindversion;
	private Button user_message_num;
	private TextView user_version_num;
	private TextView user_fullname;
	private TextView user_infos;
	private ProgressDialog dialog;

	public Gson gson = new Gson();
	private FragmentManager fm;
	private FragmentTransaction ft;
	FragmentCallBack fragmentCallBack = null;

	private String username = null;
	private String password = null;

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				user_hasfindversion.setVisibility(View.GONE);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity())
						.setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher)
						.setCancelable(false)
						.setMessage("发现 v" + msg.obj + "新版本,是否现在更新?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										// TODO Auto-generated method stub
										// Toast.makeText(getActivity(),
										// "开始下载.....", Toast.LENGTH_LONG)
										// .show();
										// http://61.190.39.3:9080/LMEP/attachments/MLEMP.apk
//										SharedPreferences mSharedPreferences = getActivity()
//												.getSharedPreferences(
//														"ServiceUrl",
//														Context.MODE_PRIVATE);
//										String url = mSharedPreferences
//												.getString("service_url", "")
//												.toString();
										Uri uri = Uri
												.parse("http://61.190.39.3:9080/LMEP/attachments/MLMEP.apk");
										Intent intent = new Intent(
												Intent.ACTION_VIEW, uri);
										startActivity(intent);
									}

								}).setNegativeButton("取消", null);
				builder.show();
				break;

			case 2:
				dialog.dismiss();
				AlertDialog.Builder builder2 = new AlertDialog.Builder(
						getActivity()).setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher).setCancelable(false)
						.setMessage("当前版本为最新版本").setPositiveButton("确定", null);
				builder2.show();
				break;
			case 3:
				dialog.dismiss();
				user_hasfindversion.setVisibility(View.VISIBLE);
				break;
			case 4:
				dialog.dismiss();
				user_hasfindversion.setVisibility(View.GONE);
				break;
			case 5:
				dialog.dismiss();
				user_message_num.setVisibility(View.VISIBLE);
				if (msg.arg1 > 100) {
					user_message_num.setText("99+");
				} else {
					user_message_num.setText("" + msg.arg1);
				}
				break;
			case 6:
				dialog.dismiss();
				user_message_num.setVisibility(View.GONE);
				break;
			case 7:
				dialog.dismiss();
				AlertDialog.Builder builder7 = new AlertDialog.Builder(
						getActivity()).setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher).setCancelable(false)
						.setMessage("没有用户").setPositiveButton("确定", null);
				builder7.show();
				break;
			case 8:
				dialog.dismiss();
				AlertDialog.Builder builder8 = new AlertDialog.Builder(
						getActivity()).setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher).setCancelable(false)
						.setMessage("检查失败，请联系管理员")
						.setPositiveButton("确定", null);
				builder8.show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		fragmentCallBack = (FragmentMainActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle2 = new Bundle();
		bundle2.putString("change_title", "change_back_home");
		fragmentCallBack.callbackFun1(bundle2);
		View view = inflater.inflate(R.layout.fragment_user, container, false);

		dialog = ProgressDialog.show(getActivity(), null, "加载数据中...", true,
				false);

		init(view); // 初始化控件
		// 版本检测
		CheckVersion();
		// 消息提醒
		MessageNum();
		return view;
	}

	private void MessageNum() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LinkedList<TrackMessage> trackMessage = new LinkedList<TrackMessage>();
				Map<String, Object> params;
				// 拼接joson
				params = new HashMap<String, Object>();
				SharedPreferences sp = getActivity().getSharedPreferences(
						"userInfo", Context.MODE_PRIVATE);
				String username = null;
				String password = null;
				if (sp.getBoolean("ISCHECK", false)) {
					username = sp.getString("username", "");
					password = sp.getString("password", "");
				}
				params.put("password", password);
				params.put("userName", username);
				Calendar calendar = Calendar.getInstance();
				String current_time = TimeUtils.getTime(
						calendar.getTimeInMillis(), TimeUtils.DATE_FORMAT_DATE);
				params.put("ncCreateDateFrom", current_time);
				params.put("ncCreateDateThrough", current_time);

				WebServiceUtils wsutils = WebServiceUtils.getInstance();
				wsutils.setContext(getActivity().getApplicationContext());
				wsutils.setMethodName("searchMobileMessage");
				wsutils.setWstag("TrackService");

				// 设置传入参数
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("sendJson", gson.toJson(params));
				wsutils.setInfactParams(m2);
				SoapObject result = null;
				try {
					// 调用webservice方法
					result = wsutils.visit();
					/**
					 * 返回的json解析成TrackMessage对象
					 * 
					 */
					if (result != null) {
						String jsonParam = result.getProperty(0).toString();
						if (!StringUtil.isEmpty(jsonParam)
								&& !"false".equals(jsonParam)) {
							java.lang.reflect.Type type = new TypeToken<TrackMessage>() {
							}.getType();
							JSONArray arr = new JSONArray(result.getProperty(0)
									.toString());
							for (int i = 0; i < arr.length(); i++) {
								JSONObject temp = (JSONObject) arr.get(i);
								// 将json数据转换成TrackMessage对象
								TrackMessage trackMessage_single = gson
										.fromJson(temp.toString(), type);
								if (!trackMessage.contains(trackMessage_single)) {
									trackMessage.addLast(trackMessage_single);
								}
							}
							trackMessage = FilterMessage(trackMessage);// 筛选

							Message msg = new Message();
							msg.what = 5;
							msg.arg1 = trackMessage.size();
							mHandler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 6;
							mHandler.sendMessage(msg);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void CheckVersion() {
		// TODO Auto-generated method stub

		if (username.equals("") || password.equals("")) {
			return;
		}

		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("password", password);
		m1.put("username", username);

		final WebServiceUtils wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(getActivity().getApplicationContext());
		wsutils.setMethodName("versionCheck");
		wsutils.setWstag("TrackService");
		// 设置传入参数
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("sendJson", gson.toJson(m1));
		wsutils.setInfactParams(m2);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				SoapObject results = null;
				try {
					results = wsutils.visit();
					if (results != null) {
						String result = results.getProperty(0).toString()
								.replaceAll("\"", "");
						Message msg = new Message();
						if (!result.equals(getAppVersionName(getActivity()))) {
							msg.what = 3;
							mHandler.sendMessage(msg);
						} else {
							msg.what = 4;
							mHandler.sendMessage(msg);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void init(View view) {
		// TODO Auto-generated method stub

		user_message = (LinearLayout) view.findViewById(R.id.user_message);
		user_changepwd = (LinearLayout) view.findViewById(R.id.user_changepwd);
		user_findversion = (LinearLayout) view
				.findViewById(R.id.user_findversion);
		user_version = (LinearLayout) view.findViewById(R.id.user_version);
		user_exit = (LinearLayout) view.findViewById(R.id.user_exit);
		user_hasfindversion = (Button) view
				.findViewById(R.id.user_hasfindversion);
		user_message_num = (Button) view.findViewById(R.id.user_message_num);
		user_version_num = (TextView) view.findViewById(R.id.user_version_num);
		user_fullname = (TextView) view.findViewById(R.id.user_fullname);
		user_infos = (TextView) view.findViewById(R.id.user_infos);
		user_message.setOnClickListener(this);
		user_changepwd.setOnClickListener(this);
		user_findversion.setOnClickListener(this);
		user_version.setOnClickListener(this);
		user_exit.setOnClickListener(this);

		user_version_num.setText("系统版本 v" + getAppVersionName(getActivity()));
		fm = getFragmentManager();
		SharedPreferences sp = getActivity().getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);

		username = sp.getString("username", "");
		password = sp.getString("password", "");
		user_fullname.setText(sp.getString("fullName", ""));
		user_infos.setText(sp.getString("organizationName", "") + " - "
				+ sp.getString("departmentName", "") + " - "
				+ sp.getString("position", ""));

	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			// versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("jochen", "getAppVersionName Exception", e);
		}
		return versionName;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_message:
			user_message_num.setVisibility(View.GONE);
			ft = fm.beginTransaction();
			MessageFragment tab1 = new MessageFragment();
			Bundle bundle = new Bundle();
			bundle.putString("flag_page", "消息");
			tab1.setArguments(bundle);
			ft.replace(R.id.frame_container, tab1, "MessageFragment");
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.user_changepwd:
			Intent changepwd = new Intent();
			changepwd.setClass(getActivity(), ChangePwdActivity.class);
			startActivity(changepwd);
			break;
		case R.id.user_findversion:
			if (username.equals("") || password.equals("")) {
				return;
			}
			dialog = ProgressDialog.show(getActivity(), null, "正在检测新版本，请稍候...",
					true, false);
			Map<String, String> m1 = new HashMap<String, String>();
			m1.put("password", password);
			m1.put("username", username);

			final WebServiceUtils wsutils = WebServiceUtils.getInstance();
			wsutils.setContext(getActivity().getApplicationContext());
			wsutils.setMethodName("versionCheck");
			wsutils.setWstag("TrackService");
			// 设置传入参数
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("sendJson", gson.toJson(m1));
			wsutils.setInfactParams(m2);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					SoapObject results = null;
					try {
						results = wsutils.visit();
						if (results != null) {
							Log.d("jochen--versioncheck=",
									results.getProperty(0).toString());

							String result = results.getProperty(0).toString()
									.replaceAll("\"", "");
							Message msg = new Message();
							if (result.equals("0")) {
								msg.what = 7;
								msg.obj = result;
								mHandler.sendMessageDelayed(msg, 3000);
							} else if (result.equals("-1")) {
								msg.what = 8;
								msg.obj = result;
								mHandler.sendMessageDelayed(msg, 3000);
							} else {

								if (!result
										.equals(getAppVersionName(getActivity()))) {
									msg.what = 1;
									msg.obj = result;
									mHandler.sendMessageDelayed(msg, 3000);
								} else {
									msg.what = 2;
									msg.obj = result;
									mHandler.sendMessageDelayed(msg, 3000);
								}
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.user_version:
			AlertDialog.Builder builder2 = new AlertDialog.Builder(
					getActivity()).setTitle("安徽中烟物流管控平台")
					.setIcon(R.drawable.ic_launcher).setCancelable(false)
					.setMessage("系统版本 v" + getAppVersionName(getActivity()))
					.setPositiveButton("确定", null);
			builder2.show();
			break;
		case R.id.user_exit:
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
					.setTitle("安徽中烟物流管控平台")
					.setIcon(R.drawable.ic_launcher)
					.setCancelable(false)
					.setMessage("确定退出登录吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									// TODO Auto-generated method stub
									getActivity().finish();// 全部销毁栈内activity
								}

							}).setNegativeButton("取消", null);
			builder.show();
			break;
		default:
			break;
		}
	}

	private LinkedList<TrackMessage> FilterMessage(
			LinkedList<TrackMessage> trackMessage) {
		// TODO Auto-generated method stub

		LinkedList<TrackMessage> tm = new LinkedList<TrackMessage>();

		if (trackMessage.size() > 1) {
			for (int i = 0; i < trackMessage.size() - 1; i++) {
				if (trackMessage.get(i).getcontractNumber()
						.equals(trackMessage.get(i + 1).getcontractNumber())) {
					if (Integer.parseInt(trackMessage.get(i).getmessageType()) < Integer
							.parseInt(trackMessage.get(i + 1).getmessageType())) {
						if (!tm.isEmpty()) {
							tm.removeLast();
						}
						tm.addLast(trackMessage.get(i + 1));
					}
				} else {
					tm.addLast(trackMessage.get(i + 1));
				}
			}
		} else {
			tm = trackMessage;
		}
		return tm;
	}
}
