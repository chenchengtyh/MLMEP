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
import com.nti.mlmep.adapter.MessageAdapter;
import com.nti.mlmep.adapter.TrackDetailAdapter;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackMessage;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MessageFragment extends Fragment implements OnClickListener {

	FragmentCallBack fragmentCallBack = null;

	private MessageAdapter mAdapter;

	private EditText message_CustomName;
	private EditText message_ContractNumber;
	private EditText message_PlannedArrivedDate;

	private Button message_button_search;
	private ListView message_listView;

	private WebServiceUtils wsutils;
	private String jsonParam;
	public Gson gson = new Gson();
	private LinkedList<TrackMessage> trackMessage;
	private Button message_creat_time;

	private int mYear;
	private int mMonth;
	private int mDay;
	private ProgressDialog dialog;
	Map<String, Object> params;

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: {
				@SuppressWarnings("unchecked")
				LinkedList<TrackMessage> trackMessage = (LinkedList<TrackMessage>) msg.obj;
				MessageAdapter mAdapter = new MessageAdapter(getActivity(),
						trackMessage);
				mAdapter.notifyDataSetChanged();
				message_listView.setAdapter(mAdapter);
				Toast.makeText(getActivity(), "查询完毕", Toast.LENGTH_LONG).show();
				break;
			}
			case 2: {
				@SuppressWarnings("unchecked")
				LinkedList<TrackMessage> trackMessage = (LinkedList<TrackMessage>) msg.obj;
				MessageAdapter mAdapter = new MessageAdapter(getActivity(),
						trackMessage);
				mAdapter.notifyDataSetChanged();
				message_listView.setAdapter(mAdapter);
				Toast.makeText(getActivity(), "已自动为您查询今天的订单消息",
						Toast.LENGTH_LONG).show();
			}
				break;
			case 3: {
				Toast.makeText(getActivity(),
						"查询不到数据，请检查条件！", Toast.LENGTH_LONG)
						.show();
				LinkedList<TrackMessage> trackMessage = new LinkedList<TrackMessage>();
				MessageAdapter mAdapter = new MessageAdapter(getActivity(),
						trackMessage);
				mAdapter.notifyDataSetChanged();
				message_listView.setAdapter(mAdapter);
			}
				break;
			case 4:
				if (dialog.isShowing())
					dialog.dismiss();

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
		View view = inflater.inflate(R.layout.fragment_mymessage, container,
				false);
		init(view);
		webserviceInit();// webservice初始化
		// mAdapter = new MessageAdapter();

		// 初始化第一次查询
		dialog = ProgressDialog.show(getActivity(), null, "数据正在加载，请稍候...",
				true, false);
		// 清空原本的缓存数据
		trackMessage = new LinkedList<TrackMessage>();
		if (trackMessage != null && trackMessage.size() > 0) {
			trackMessage.clear();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				// 拼接joson
				params = new HashMap<String, Object>();
				params.put("orderNumber", "");
				// params.put("contractNumber",
				// message_ContractNumber.getText().toString());
				// params.put("plateNumber", et_pn.getText().toString());
				// params.put("transportCode", et_tc.getText().toString());
				// params.put("transportStatus", "");
				// params.put("customName",
				// message_CustomName.getText().toString());
				// params.put("startTime", message_PlannedArrivedDate.getText()
				// .toString());
				// params.put("endTime", message_PlannedArrivedDate.getText()
				// .toString());

				// test
				params.put("contractNumber", message_ContractNumber.getText()
						.toString());
				params.put("receiveEnterprise", message_CustomName.getText()
						.toString());
				params.put("ncCreateDateFrom", message_PlannedArrivedDate
						.getText().toString());
				params.put("ncCreateDateThrough", message_PlannedArrivedDate
						.getText().toString());
				params.put("ncCreateDateFrom", message_PlannedArrivedDate
						.getText().toString());
				params.put("ncCreateDateThrough", message_PlannedArrivedDate
						.getText().toString());
				// test

				SharedPreferences sharedPreferences = getActivity()
						.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
				params.put("userName",
						sharedPreferences.getString("username", ""));
				params.put("password",
						sharedPreferences.getString("password", ""));

				wsutils = WebServiceUtils.getInstance();
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
						jsonParam = result.getProperty(0).toString();
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
							Message msg2 = new Message();
							msg2.what = 2;
							msg2.obj = trackMessage;
							mHandler.sendMessage(msg2);
						} else {
//							Toast.makeText(getActivity(), "查询不到数据，请检查条件！",
//									Toast.LENGTH_LONG).show();
							Message msg = new Message();
							msg.what = 3;
							mHandler.sendMessage(msg);
						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// mHandler.post(new Runnable() {
					//
					// @Override
					// public void run() {
					// Toast.makeText(getActivity(),
					// "连接服务器失败", Toast.LENGTH_LONG)
					// .show();
					//
					// }
					// });
					e.printStackTrace();
				}

				Message msg = new Message();
				msg.what = 4;
				mHandler.sendMessage(msg);

			}
		}).start();

		message_listView.setDivider(null);
		message_listView.setAdapter(mAdapter);
		//

		return view;
	}

	private void webserviceInit() {
		// TODO Auto-generated method stub

	}

	private void init(View view) {
		// TODO Auto-generated method stub

		trackMessage = new LinkedList<TrackMessage>();
		if (trackMessage != null && trackMessage.size() > 0) {
			trackMessage.clear();
		}

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		message_creat_time = (Button) view
				.findViewById(R.id.message_creat_time);
		message_creat_time.setOnClickListener(this);
		message_CustomName = (EditText) view
				.findViewById(R.id.message_CustomName);
		message_ContractNumber = (EditText) view
				.findViewById(R.id.message_ContractNumber);
		message_PlannedArrivedDate = (EditText) view
				.findViewById(R.id.message_PlannedArrivedDate);
		Calendar calendar = Calendar.getInstance();
		message_PlannedArrivedDate.setText(TimeUtils.getTime(
				calendar.getTimeInMillis(), TimeUtils.DATE_FORMAT_DATE));
		message_button_search = (Button) view
				.findViewById(R.id.message_button_search);
		message_listView = (ListView) view.findViewById(R.id.message_listView);
		message_button_search.setOnClickListener(this);

	}

	private DatePickerDialog.OnDateSetListener benDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateTime();
		}

		private void updateTime() {
			// TODO Auto-generated method stub
			message_PlannedArrivedDate.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.message_button_search:

			if (message_PlannedArrivedDate.getText().toString().equals("")) {
				Toast.makeText(getActivity(), "日期不能为空", Toast.LENGTH_LONG)
						.show();
			} else {

				// 初始化第一次查询
				dialog = ProgressDialog.show(getActivity(), null,
						"数据正在加载，请稍候...", true, false);

				// 清空原本的缓存数据
				trackMessage = new LinkedList<TrackMessage>();
				if (trackMessage != null && trackMessage.size() > 0) {
					trackMessage.clear();
				}

				new Thread(new Runnable() {

					@Override
					public void run() {
						// 拼接joson
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("orderNumber", "");
						// params.put("contractNumber",
						// message_ContractNumber.getText().toString());
						// params.put("plateNumber",
						// et_pn.getText().toString());
						// params.put("transportCode",
						// et_tc.getText().toString());
						// params.put("transportStatus", "");
						// params.put("customName",
						// message_CustomName.getText().toString());
						params.put("startTime", message_PlannedArrivedDate
								.getText().toString());
						params.put("endTime", message_PlannedArrivedDate
								.getText().toString());

						// test
						params.put("contractNumber", message_ContractNumber
								.getText().toString());
						params.put("receiveEnterprise", message_CustomName
								.getText().toString());
						params.put("ncCreateDateFrom",
								message_PlannedArrivedDate.getText().toString());
						params.put("ncCreateDateThrough",
								message_PlannedArrivedDate.getText().toString());
						// test

						SharedPreferences sharedPreferences = getActivity()
								.getSharedPreferences("userInfo",
										Context.MODE_PRIVATE);
						params.put("userName",
								sharedPreferences.getString("username", ""));
						params.put("password",
								sharedPreferences.getString("password", ""));

						WebServiceUtils wsutils = WebServiceUtils.getInstance();
						wsutils.setContext(getActivity()
								.getApplicationContext());
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
							Log.d("jochen", "----2----" + result);
							if (result != null) {
								jsonParam = result.getProperty(0).toString();
								if (!StringUtil.isEmpty(jsonParam)
										&& !"false".equals(jsonParam)) {
									java.lang.reflect.Type type = new TypeToken<TrackMessage>() {
									}.getType();
									JSONArray arr = new JSONArray(result
											.getProperty(0).toString());
									for (int i = 0; i < arr.length(); i++) {
										JSONObject temp = (JSONObject) arr
												.get(i);
										// 将json数据转换成TrackMessage对象
										TrackMessage trackMessage_single = gson
												.fromJson(temp.toString(), type);
										if (!trackMessage
												.contains(trackMessage_single)) {
											trackMessage
													.addLast(trackMessage_single);
										}
									}

									Message msg2 = new Message();
									msg2.what = 1;
									msg2.obj = trackMessage;
									mHandler.sendMessage(msg2);
								} else {
									
									Message msg2 = new Message();
									msg2.what = 3;
									msg2.obj = trackMessage;
									mHandler.sendMessage(msg2);
								}
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							mHandler.post(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(getActivity(), "连接服务器失败",
											Toast.LENGTH_LONG).show();

								}
							});
							e.printStackTrace();
						}
						Message msg = new Message();
						msg.what = 4;
						mHandler.sendMessage(msg);

					}
				}).start();
			}

			break;
		case R.id.message_creat_time:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					benDateSetListener, mYear, mMonth, mDay).show();
			break;
		default:
			break;
		}
	}

}
