package com.nti.mlmep.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.myksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.nti.mlmep.R;
import com.nti.mlmep.util.ExitApplication;
import com.nti.mlmep.util.WebServiceUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePwdActivity extends Activity {

	// private TextView layout_title_tv_name;
	// private Button title_back2;

	private Button ok_change;
	private EditText origin_pwd;
	private EditText new_pwd;
	private EditText new_pwd_again;
	private String username = null;
	private String password = null;
	private String origin_pwd_val;
	private String new_pwd_val;
	private String new_pwd_again_val;
	public Gson gson = new Gson();
	ProgressDialog myDialog = null;
	private String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				myDialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ChangePwdActivity.this)
						.setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher)
						.setCancelable(false)
						.setMessage("修改成功,将退出用户重新登录")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										// TODO Auto-generated method stub
										ExitApplication.getInstance()
												.exit_login();
									}

								});
				builder.show();
				break;
			case 2:
				myDialog.dismiss();
				AlertDialog.Builder builder2 = new AlertDialog.Builder(
						ChangePwdActivity.this).setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher).setCancelable(false)
						.setMessage("修改失败，系统没有该用户")
						.setPositiveButton("确定", null);
				builder2.show();
				break;
			case 3:
				myDialog.dismiss();
				AlertDialog.Builder builder3 = new AlertDialog.Builder(
						ChangePwdActivity.this).setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher).setCancelable(false)
						.setMessage("修改失败，系统异常联系管理员")
						.setPositiveButton("确定", null);
				builder3.show();
				break;
			case 4:
				myDialog.dismiss();
				AlertDialog.Builder builder4 = new AlertDialog.Builder(
						ChangePwdActivity.this).setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher).setCancelable(false)
						.setMessage("网络异常").setPositiveButton("确定", null);
				builder4.show();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.edit_password);
		ExitApplication.getInstance().addActivity(this); // 入栈用于销毁
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.title2);

		// layout_title_tv_name = (TextView)
		// findViewById(R.id.layout_title_tv_name);
		// title_back2 = (Button) findViewById(R.id.title_back2);
		// layout_title_tv_name.setText("密码修改");
		// title_back2.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// finish();
		// }
		// });

		ok_change = (Button) findViewById(R.id.ok_change);
		origin_pwd = (EditText) findViewById(R.id.origin_pwd);
		new_pwd = (EditText) findViewById(R.id.new_pwd);
		new_pwd_again = (EditText) findViewById(R.id.new_pwd_again);

		SharedPreferences sp = getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);

		if (sp.getBoolean("ISCHECK", false)) {
			username = sp.getString("username", "");
			password = sp.getString("password", "");
		}

		ok_change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				origin_pwd_val = origin_pwd.getText().toString().trim();
				new_pwd_val = new_pwd.getText().toString().trim();
				new_pwd_again_val = new_pwd_again.getText().toString().trim();

				Log.d("jochen", "origin_pwd_val=" + origin_pwd_val);
				Log.d("jochen", "new_pwd_val=" + new_pwd_val);
				Log.d("jochen", "new_pwd_again_val=" + new_pwd_again_val);
				Log.d("jochen", "password=" + password);
				Log.d("jochen", "isSpecialChar(new_pwd_val)="
						+ isSpecialChar(new_pwd_val));
				Log.d("jochen", "isSpecialChar(new_pwd_again_val)="
						+ isSpecialChar(new_pwd_again_val));
				Log.d("jochen",
						"new_pwd_val.matches(regex)="
								+ new_pwd_val.matches(regex));
				Log.d("jochen", "new_pwd_again_val.matches(regex)="
						+ new_pwd_again_val.matches(regex));

				if (origin_pwd_val.equals("") || new_pwd_val.equals("")
						|| new_pwd_again_val.equals("")) {
					Toast.makeText(ChangePwdActivity.this, "请将信息输入完整再提交",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (!origin_pwd_val.equals(password)) {
					Toast.makeText(ChangePwdActivity.this, "原密码输入不正确!",
							Toast.LENGTH_SHORT).show();
					return;
				} else if ((new_pwd_val.length() < 6)
						|| (new_pwd_again_val.length() < 6)) {
					Toast.makeText(ChangePwdActivity.this, "新密码长度必须大于6位!",
							Toast.LENGTH_SHORT).show();
					return;
				} else if ((new_pwd_val.length() > 16)
						|| (new_pwd_again_val.length() > 16)) {
					Toast.makeText(ChangePwdActivity.this, "新密码长度不能大于16位!",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (isSpecialChar(new_pwd_val)) {
					Toast.makeText(ChangePwdActivity.this, "新密码含有特殊字符!",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (isSpecialChar(new_pwd_again_val)) {
					Toast.makeText(ChangePwdActivity.this, "新密码含有特殊字符!",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (!new_pwd_val.equals(new_pwd_again_val)) {
					Toast.makeText(ChangePwdActivity.this, "新密码两次输入不相同!",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (!(new_pwd_val.matches(regex))
						|| !(new_pwd_again_val.matches(regex))) {
					Toast.makeText(ChangePwdActivity.this, "新密码格式不正确!",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (origin_pwd_val.equals(new_pwd)) {
					Toast.makeText(ChangePwdActivity.this, "新密码与原始密码相同!",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					ModifyPassword(username, password, new_pwd_val);

				}
			}

		});
	}

	/**
	 * 判断是否含有特殊字符
	 * 
	 * @param str
	 * @return true为包含，false为不包含
	 */
	public static boolean isSpecialChar(String str) {
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	private void ModifyPassword(String username, String password,
			String new_pwd_val) {
		// TODO Auto-generated method stub
		if (username.equals("") || new_pwd_val.equals("") || password.equals("")) {
			return;
		}

		Map<String, String> m1 = new HashMap<String, String>();

		m1.put("username", username);		
		m1.put("oldPassword", password);
		m1.put("newPassword", new_pwd_val);

		final WebServiceUtils wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(ChangePwdActivity.this.getApplicationContext());
		wsutils.setMethodName("modifyPassword");
		wsutils.setWstag("TrackService");
		// 设置传入参数
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("sendJson", gson.toJson(m1));
		wsutils.setInfactParams(m2);

		myDialog = ProgressDialog.show(ChangePwdActivity.this, "",
				"提交中,请稍等 ...", true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SoapObject results = null;
				try {
					results = wsutils.visit();
					if (results != null) {
						Log.d("jochen--changepwd=", results.getProperty(0)
								.toString());
						// modifyPasswordResponse{modifyPasswordReturn="0"; }
						String result = results.getProperty(0).toString()
								.replaceAll("\"", "");
						Message msg = new Message();
						if (result.equals("1")) {
							msg.what = 1;
							mHandler.sendMessageDelayed(msg, 3000);
						} else if (result.equals("0")) {
							msg.what = 2;
							mHandler.sendMessageDelayed(msg, 3000);
						} else {
							msg.what = 3;
							mHandler.sendMessageDelayed(msg, 3000);
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msgs = new Message();
					msgs.what = 4;
					mHandler.sendMessageDelayed(msgs, 3000);
				}

			}
		}).start();
	}

}
