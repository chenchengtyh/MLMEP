package com.nti.mlmep.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.myksoap2.serialization.SoapObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nti.mlmep.R;
import com.nti.mlmep.activity.base.BaseActivity;
import com.nti.mlmep.util.AppLog;
import com.nti.mlmep.util.AppManager;
import com.nti.mlmep.util.ExitApplication;
import com.nti.mlmep.util.PropertiesUtil;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.WebServiceUtils;

/**
 * 登录界面
 * 
 * @author wangge
 */

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private CheckBox rememberPwdCbx;
	private ProgressBar progressBar;
	private TextView textViewTip;
	private TextView forget_password;
	private TextView change_url;
	private String wsType;
	private String username;
	private String password;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case 1:
				// 用户无登录权限
				progressBar.setVisibility(View.INVISIBLE);
				textViewTip.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "该用户没有登陆权限",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				// 登录成功隐藏进度条
				progressBar.setVisibility(View.INVISIBLE);
				textViewTip.setVisibility(View.INVISIBLE);
				break;
			case 3:
				// 用户密码错误
				progressBar.setVisibility(View.INVISIBLE);
				textViewTip.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "用户名或密码错误",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				progressBar.setVisibility(View.INVISIBLE);
				textViewTip.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "连接服务器失败",
						Toast.LENGTH_LONG).show();
				break;
			case 5:
				progressBar.setVisibility(View.INVISIBLE);
				textViewTip.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "用户名或密码不能为空",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// super.setAllowFullScreen(false); //是否显示标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ExitApplication.getInstance().addActivity(this); // 入栈用于销毁

		usernameEditText = (EditText) findViewById(R.id.login_username_edt);
		loginButton = (Button) findViewById(R.id.login_bt_login);
		passwordEditText = (EditText) findViewById(R.id.login_password_edt);
		forget_password = (TextView) findViewById(R.id.forget_password);
		// jochen modify
		rememberPwdCbx = (CheckBox) LoginActivity.this
				.findViewById(R.id.login_cb_rmwd);
		// jochen modify
		loginButton.setOnClickListener(this);
		forget_password.setOnClickListener(this);
		rememberPwdCbx.setOnClickListener(this);
		progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
		textViewTip = (TextView) findViewById(R.id.login_tv_tip);
		change_url = (TextView) findViewById(R.id.change_url);
		change_url.setOnClickListener(this);

		// 如果记住密码，显示用户名密码
		SharedPreferences sp = getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		if (sp.getBoolean("ISCHECK", false)) {
			String share_name = sp.getString("username", "");
			String share_pwd = sp.getString("password", "");
			if (!TextUtils.isEmpty(share_name)) {
				usernameEditText.setText(share_name);
				passwordEditText.setText(share_pwd);
				rememberPwdCbx.setChecked(true);
			}
		}

		// 初始化地址
		String service_url = "http://61.190.39.3:9080";
		// 初始化服务器地址
		SharedPreferences mSharedPreferences = getSharedPreferences(
				"ServiceUrl", Context.MODE_PRIVATE);
		String service_url_save = mSharedPreferences.getString("service_url",
				"");
		if (service_url_save.equals("")) {
			SharedPreferences.Editor editor = mSharedPreferences.edit();
			editor.putString("service_url", service_url);
			editor.commit();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_bt_login:
			// loginButton.setBackgroundColor(Color.YELLOW);
			username = usernameEditText.getText().toString().trim();
			password = passwordEditText.getText().toString().trim();
			AppLog.debug("login", username + "---------" + password);
			// 启动主页面的activity
			// Intent intent = new Intent();
			// intent.setClass(getApplicationContext(),
			// MainActivity.class);
			// startActivity(intent);
			login(username, password);

			break;
		case R.id.forget_password:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(false);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("温馨提示");
			builder.setMessage("请致电我们为您人工服务：4009-565-656");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}

					});
			builder.show();
			break;
		case R.id.change_url:

			final EditText inputServer = new EditText(LoginActivity.this);
			SharedPreferences mSharedPreferences = getSharedPreferences(
					"ServiceUrl", Context.MODE_PRIVATE);
			inputServer
					.setText(mSharedPreferences.getString("service_url", ""));

			AlertDialog.Builder builder_edit = new AlertDialog.Builder(
					LoginActivity.this);
			builder_edit.setTitle("更改服务地址").setIcon(R.drawable.ic_launcher)
					.setView(inputServer).setNegativeButton("取消", null);
			builder_edit.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							SharedPreferences mSharedPreferences = getSharedPreferences(
									"ServiceUrl", Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = mSharedPreferences
									.edit();
							editor.putString("service_url", inputServer
									.getText().toString().trim());
							editor.commit();
						}
					});
			builder_edit.show();

			break;
		default:
			break;
		}
	}

	private void login(String username, final String password) {
		if (!(TextUtils.isEmpty(username)) && !(TextUtils.isEmpty(password))) {
			// 登陆时显示进度条
			progressBar.setVisibility(View.VISIBLE);
			textViewTip.setVisibility(View.VISIBLE);
			final WebServiceUtils wsutils = WebServiceUtils.getInstance();
			wsutils.setContext(LoginActivity.this.getApplicationContext());
			wsutils.setMethodName("userLogin");
			wsutils.setWstag("LoginService");

			// 设置传入参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("password", password);
			params.put("userName", username);

			wsutils.setInfactParams(params);
			new Thread(new Runnable() {
				@SuppressWarnings("rawtypes")
				@Override
				public void run() {
					SoapObject result = null;
					try {
						result = wsutils.visit();
						String userResult = result.getProperty(0).toString()
								.split("@#")[0];
						// boolean v = userResult.equals("\"NO_PERMISSION! \"");
						// Log.d("jochen", v+"----"+userResult);
						if (result != null && !userResult.equals("\"0\"")
								&& !userResult.equals("\"NO_PERMISSION\"")) {
							// 登录成功隐藏进度条
							// handler.post(new Runnable() {
							// @Override
							// public void run() {
							// progressBar.setVisibility(View.INVISIBLE);
							// textViewTip.setVisibility(View.INVISIBLE);
							// }
							// });

							Message msg_loginSuccess = new Message();
							msg_loginSuccess.what = 2;
							handler.sendMessage(msg_loginSuccess);

							// 启动主页面的activity
							Intent intent = new Intent();
							intent.setClass(getApplicationContext(),
									FragmentMainActivity.class);
							startActivity(intent);

							// 登陆成功后，判读是否记住密码，缓存用户名密码
							SharedPreferences sharedPreferences = getSharedPreferences(
									"userInfo", Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedPreferences
									.edit();
							Gson userInfoGson = new Gson();
							Map userinfomap = userInfoGson.fromJson(userResult,
									Map.class);

							if (rememberPwdCbx.isChecked()) {
								editor.putString("username",
										userinfomap.get("userName").toString());
								editor.putString("password",
										password);
								editor.putString("userid",
										userinfomap.get("userId").toString());
								editor.putBoolean("ISCHECK", true);
								editor.putString("position",
										userinfomap.get("position").toString());
								editor.putString("organizationName",
										userinfomap.get("organizationName")
												.toString());
								editor.putString("fullName",
										userinfomap.get("fullName").toString());
								editor.putString("departmentName", userinfomap
										.get("departmentName").toString());
								editor.commit();
							} else {
								// 清除缓存
								editor.clear();
								editor.putString("username",
										userinfomap.get("userName").toString());
								editor.putString("password",
										password);
								editor.putString("userid",
										userinfomap.get("userId").toString());
								editor.putBoolean("ISCHECK", true);
								editor.putString("position",
										userinfomap.get("position").toString());
								editor.putString("organizationName",
										userinfomap.get("organizationName")
												.toString());
								editor.putString("fullName",
										userinfomap.get("fullName").toString());
								editor.putString("departmentName", userinfomap
										.get("departmentName").toString());
								editor.commit();
							}
						} else if (result != null
								&& userResult.equals("\"NO_PERMISSION\"")) {

							// 发现handler消息做登录进度显示操作
							Message msg_noAuth = new Message();
							msg_noAuth.what = 1;
							handler.sendMessage(msg_noAuth);
						} else {
							Message msg_error = new Message();
							msg_error.what = 3;
							handler.sendMessage(msg_error);
						}

					} catch (Exception e) {

						Message msg_connectError = new Message();
						msg_connectError.what = 4;
						handler.sendMessage(msg_connectError);

						e.printStackTrace();
					}
				}
			}).start();
		} else {

			Message msg_nameEmpty = new Message();
			msg_nameEmpty.what = 5;
			handler.sendMessage(msg_nameEmpty);
		}
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(Menu.NONE, 1, 1, "远程服务设置");
		return true;
	}

	@SuppressLint("ShowToast")
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.item1:
			Toast.makeText(getApplicationContext(), "退出应用", 1).show();
			AppManager.getAppManager().finishAllActivity();
			System.exit(0);
			break;
		case 1:
			final EditText inputServer = new EditText(this);
			inputServer.setFocusable(true);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.settings_ws))
					.setIcon(R.drawable.setting_ws).setView(inputServer)
					.setNegativeButton(getString(R.string.setws_cancel), null);
			builder.setPositiveButton(getString(R.string.setws_ok),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							String inputName = inputServer.getText().toString();
							Properties pro = new Properties();
							pro.put("ws.nameSpace", "http://" + inputName
									+ "/LMEP/services/InterfacesForPhone/");
							pro.put("ws.LoginService.endPoint", "http://"
									+ inputName
									+ "/LMEP/services/InterfacesForPhone/");
							pro.put("ws.TrackService.endPoint", "http://"
									+ inputName
									+ "/LMEP/services/InterfacesForPhone/");

							// saveConfig(LoginActivity.this.getApplicationContext(),
							// "/setting.properties", pro);

						}
					});
			builder.show();
			break;
		}
		//
		return true;
	}

	public void saveConfig(Context context, String file, Properties properties) {
		try {
			File fil = new File(file);
			if (!fil.exists())
				fil.createNewFile();
			FileOutputStream s = new FileOutputStream(fil);
			// FileOutputStream s = new FileOutputStream(file, false);
			properties.store(s, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}