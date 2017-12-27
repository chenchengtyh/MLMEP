package com.nti.mlmep.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.domain.TrackComplain;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.util.SerializableMap;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

public class ComplainFragment extends Fragment implements OnClickListener {

	FragmentCallBack fragmentCallBack = null;
	TrackInfoBean alarmInfo = null;
	private FragmentTransaction ft;

	private EditText complain_CustomName;
	private EditText complain_ContractNumber;
	private EditText complain_NcCreateDate;
	private TextView contractNumber_tv_complain;
	private TextView repertoryName_tv_complain;
	private TextView customName_tv_complain;
	private TextView plannedArrivedDate_tv_complain;

	private TextView complain_one;
	private TextView complain_two;
	private TextView complain_three;
	private EditText complain_others;

	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;

	private Button complain_bt1;
	private Button complain_creat_time;
	private Button complain_button_search;

	private LinearLayout search_flag;
	private LinearLayout complain_commitall;
	private LinearLayout complain_result;
	private LinearLayout complain_layout1;
	private TextView complain_flag;
	private TextView complain_results;
	public Gson gson = new Gson();

	private int mYear;
	private int mMonth;
	private int mDay;

	private boolean flag = false; // 标志位，用于判断提交是否成功。

	ProgressDialog myDialog = null;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			myDialog.dismiss();
			switch (msg.what) {
			case 1:
				flag = true;
				// 清空数据
				complain_CustomName.setText("");
				complain_ContractNumber.setText("");
				Calendar calendar = Calendar.getInstance();
				complain_NcCreateDate
						.setText(TimeUtils.getTime(calendar.getTimeInMillis(),
								TimeUtils.DATE_FORMAT_DATE));
				contractNumber_tv_complain.setText("");
				repertoryName_tv_complain.setText("");
				customName_tv_complain.setText("");
				plannedArrivedDate_tv_complain.setText("");
				complain_others.setText("");
				Toast.makeText(getActivity(), "提交成功，感谢您的投诉，我们会及时处理!",
						Toast.LENGTH_SHORT).show();
				TrackFragment.rel_complaint = 1;
				getFragmentManager().popBackStack();
				break;
			case 2:
				Toast.makeText(getActivity(), "提交失败,请联系管理员", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
			// super.handleMessage(msg);
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		alarmInfo = (TrackInfoBean) getArguments().getSerializable("map");

		fragmentCallBack = (FragmentMainActivity) activity;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Bundle bundle3 = new Bundle();
		bundle3.putString("change_title", "change_title_complain");
		fragmentCallBack.callbackFun1(bundle3);

		View view = inflater.inflate(R.layout.fragment_complain, container,
				false);
		init(view);

		// 初始化参数
		if (alarmInfo != null) {
			search_flag.setVisibility(View.GONE);
			complain_button_search.setVisibility(View.GONE);
			complain_layout1.setVisibility(View.VISIBLE);
			complain_commitall.setVisibility(View.VISIBLE);
			if (alarmInfo.getMobileComplaint().size() != 0
					&& alarmInfo.getMobileComplaint().get(0).getIsAccept()
							.equals("1")
					&& alarmInfo.getcomplaint_flag().equals("1")) {
				// 初始化参数
				// complain_CustomName.setText(alarmInfo.getCustomName());
				// complain_ContractNumber.setText(alarmInfo.getContractNumber());
				// complain_NcCreateDate.setText(TimeUtils.dateToStr(
				// alarmInfo.getNcCreateDate()).substring(0, 10));
				// contractNumber_tv_complain.setText(alarmInfo
				// .getContractNumber());
				// repertoryName_tv_complain.setText(alarmInfo.getRepertoryName());
				// customName_tv_complain.setText(alarmInfo.getCustomName());
				// plannedArrivedDate_tv_complain.setText(TimeUtils
				// .dateToStr(alarmInfo.getNcCreateDate()));
				// // 隐藏投诉，显示受理结果
				// complain_commitall.setVisibility(View.GONE);
				// complain_flag.setVisibility(View.VISIBLE);

				// ------

				// ------

				checkBox1.setEnabled(false);
				checkBox2.setEnabled(false);
				checkBox3.setEnabled(false);
				complain_others.setEnabled(false);
				Bundle bundle2 = new Bundle();
				bundle2.putString("change_title", "change_back");
				fragmentCallBack.callbackFun1(bundle2);
				complain_bt1.setVisibility(View.GONE);
				if (alarmInfo.getMobileComplaint().get(0).getNoIntimeAssess()
						.equals("1")) {
					checkBox1.setChecked(true);
					complain_one.setTextColor(getResources().getColor(
							R.color.complain_select));
				} else {
					checkBox1.setChecked(false);
					complain_one.setTextColor(getResources().getColor(
							R.color.complain_unselect));
				}
				if (alarmInfo.getMobileComplaint().get(0).getNoAnswerPhone()
						.equals("1")) {
					checkBox2.setChecked(true);
					complain_two.setTextColor(getResources().getColor(
							R.color.complain_select));
				} else {
					checkBox2.setChecked(false);
					complain_two.setTextColor(getResources().getColor(
							R.color.complain_unselect));
				}
				if (alarmInfo.getMobileComplaint().get(0).getErrorPhoneNumber()
						.equals("1")) {
					checkBox3.setChecked(true);
					complain_three.setTextColor(getResources().getColor(
							R.color.complain_select));
				} else {
					checkBox3.setChecked(false);
					complain_three.setTextColor(getResources().getColor(
							R.color.complain_unselect));
				}
				complain_others.setText(alarmInfo.getMobileComplaint().get(0)
						.getRemark());

				// 初始化参数
				complain_CustomName.setText(alarmInfo.getCustomName());
				complain_ContractNumber.setText(alarmInfo.getContractNumber());
				complain_NcCreateDate.setText(TimeUtils.dateToStr(
						alarmInfo.getNcCreateDate()).substring(0, 10));
				contractNumber_tv_complain.setText(alarmInfo
						.getContractNumber());
				repertoryName_tv_complain.setText(alarmInfo.getRepertoryName());
				customName_tv_complain.setText(alarmInfo.getCustomName());
				plannedArrivedDate_tv_complain.setText(TimeUtils
						.dateToStr(alarmInfo.getNcCreateDate()));

				// complain_flag.setVisibility(View.VISIBLE);
				complain_flag.setText("已受理");

				complain_results.setText(alarmInfo.getMobileComplaint().get(0)
						.getFeedback());
				complain_result.setVisibility(View.VISIBLE);

			} else if (alarmInfo.getMobileComplaint().size() != 0
					&& alarmInfo.getMobileComplaint().get(0).getIsAccept()
							.equals("0")
					&& alarmInfo.getcomplaint_flag().equals("1")) {
				checkBox1.setEnabled(false);
				checkBox2.setEnabled(false);
				checkBox3.setEnabled(false);
				complain_others.setEnabled(false);
				Bundle bundle2 = new Bundle();
				complain_flag.setText("已投诉");
				complain_flag.setVisibility(View.VISIBLE);
				bundle2.putString("change_title", "change_back");
				fragmentCallBack.callbackFun1(bundle2);
				complain_bt1.setVisibility(View.GONE);
				if (alarmInfo.getMobileComplaint().get(0).getNoIntimeAssess()
						.equals("1")) {
					checkBox1.setChecked(true);
					complain_one.setTextColor(getResources().getColor(
							R.color.complain_select));
				} else {
					checkBox1.setChecked(false);
					complain_one.setTextColor(getResources().getColor(
							R.color.complain_unselect));
				}
				if (alarmInfo.getMobileComplaint().get(0).getNoAnswerPhone()
						.equals("1")) {
					checkBox2.setChecked(true);
					complain_two.setTextColor(getResources().getColor(
							R.color.complain_select));
				} else {
					checkBox2.setChecked(false);
					complain_two.setTextColor(getResources().getColor(
							R.color.complain_unselect));
				}
				if (alarmInfo.getMobileComplaint().get(0).getErrorPhoneNumber()
						.equals("1")) {
					checkBox3.setChecked(true);
					complain_three.setTextColor(getResources().getColor(
							R.color.complain_select));
				} else {
					checkBox3.setChecked(false);
					complain_three.setTextColor(getResources().getColor(
							R.color.complain_unselect));
				}
				complain_others.setText(alarmInfo.getMobileComplaint().get(0)
						.getRemark());
				// 初始化参数
				complain_CustomName.setText(alarmInfo.getCustomName());
				complain_ContractNumber.setText(alarmInfo.getContractNumber());
				complain_NcCreateDate.setText(TimeUtils.dateToStr(
						alarmInfo.getNcCreateDate()).substring(0, 10));
				contractNumber_tv_complain.setText(alarmInfo
						.getContractNumber());
				repertoryName_tv_complain.setText(alarmInfo.getRepertoryName());
				customName_tv_complain.setText(alarmInfo.getCustomName());
				plannedArrivedDate_tv_complain.setText(TimeUtils
						.dateToStr(alarmInfo.getNcCreateDate()));

			} else {
				Bundle bundle2 = new Bundle();
				bundle2.putString("change_title", "change_back");
				fragmentCallBack.callbackFun1(bundle2);
				// 初始化参数
				complain_CustomName.setText(alarmInfo.getCustomName());
				complain_ContractNumber.setText(alarmInfo.getContractNumber());
				complain_NcCreateDate.setText(TimeUtils.dateToStr(
						alarmInfo.getNcCreateDate()).substring(0, 10));
				contractNumber_tv_complain.setText(alarmInfo
						.getContractNumber());
				repertoryName_tv_complain.setText(alarmInfo.getRepertoryName());
				customName_tv_complain.setText(alarmInfo.getCustomName());
				plannedArrivedDate_tv_complain.setText(TimeUtils
						.dateToStr(alarmInfo.getNcCreateDate()));
			}

		} else {
			Bundle bundle2 = new Bundle();
			bundle2.putString("change_title", "change_back_home");
			fragmentCallBack.callbackFun1(bundle2);
		}

		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub

		flag = false;
		search_flag = (LinearLayout) view.findViewById(R.id.search_flag);
		complain_layout1 = (LinearLayout) view
				.findViewById(R.id.complain_layout1);
		complain_layout1.setVisibility(View.GONE);
		complain_results = (TextView) view.findViewById(R.id.complain_results);
		complain_flag = (TextView) view.findViewById(R.id.complain_flag);
		// complain_flag.setVisibility(View.GONE);
		complain_commitall = (LinearLayout) view
				.findViewById(R.id.complain_commitall);
		complain_commitall.setVisibility(View.GONE);
		complain_result = (LinearLayout) view
				.findViewById(R.id.complain_result);
		complain_result.setVisibility(View.GONE);
		complain_CustomName = (EditText) view
				.findViewById(R.id.complain_CustomName);
		complain_ContractNumber = (EditText) view
				.findViewById(R.id.complain_ContractNumber);
		complain_NcCreateDate = (EditText) view
				.findViewById(R.id.complain_NcCreateDate);
		Calendar calendar = Calendar.getInstance();
		complain_NcCreateDate.setText(TimeUtils.getTime(
				calendar.getTimeInMillis(), TimeUtils.DATE_FORMAT_DATE));
		contractNumber_tv_complain = (TextView) view
				.findViewById(R.id.contractNumber_tv_complain);
		repertoryName_tv_complain = (TextView) view
				.findViewById(R.id.repertoryName_tv_complain);
		customName_tv_complain = (TextView) view
				.findViewById(R.id.customName_tv_complain);
		plannedArrivedDate_tv_complain = (TextView) view
				.findViewById(R.id.plannedArrivedDate_tv_complain);

		complain_creat_time = (Button) view
				.findViewById(R.id.complain_creat_time);
		complain_creat_time.setOnClickListener(this);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		complain_one = (TextView) view.findViewById(R.id.complain_one);
		complain_two = (TextView) view.findViewById(R.id.complain_two);
		complain_three = (TextView) view.findViewById(R.id.complain_three);
		complain_others = (EditText) view.findViewById(R.id.complain_others);

		checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
		checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
		checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);

		complain_bt1 = (Button) view.findViewById(R.id.complain_bt1);
		complain_bt1.setOnClickListener(this);

		complain_button_search = (Button) view
				.findViewById(R.id.complain_button_search);
		complain_button_search.setOnClickListener(this);
		checkBox1
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							complain_one.setTextColor(getResources().getColor(
									R.color.complain_select));

						} else {
							complain_one.setTextColor(getResources().getColor(
									R.color.complain_unselect));
						}
					}
				});
		checkBox2
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {

							complain_two.setTextColor(getResources().getColor(
									R.color.complain_select));
						} else {
							complain_two.setTextColor(getResources().getColor(
									R.color.complain_unselect));
						}
					}
				});
		checkBox3
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							complain_three.setTextColor(getResources()
									.getColor(R.color.complain_select));
						} else {
							complain_three.setTextColor(getResources()
									.getColor(R.color.complain_unselect));
						}
					}
				});
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.complain_button_search:

			FragmentManager fm = getFragmentManager();
			ft = fm.beginTransaction();
			TrackFragment trackFragment = new TrackFragment();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("customName", complain_CustomName.getText().toString());
			params.put("contractNumber", complain_ContractNumber.getText()
					.toString());
			params.put("transportStatus", "");
			params.put("startTime", complain_NcCreateDate.getText().toString());
			params.put("endTime", complain_NcCreateDate.getText().toString());
			SerializableMap smap = new SerializableMap();
			smap.setMap(params);
			Bundle bundle_bt2 = new Bundle();
			bundle_bt2.putSerializable("serializableMap", smap);
			// bundle_bt2.putString("change_title", "change_title");
			// bundle_bt2.putInt("change_title_track", 1);
			// fragmentCallBack.callbackFun4(bundle_bt2);
			trackFragment.setArguments(bundle_bt2);
			ft.replace(R.id.frame_container, trackFragment,"TrackFragment");
			ft.addToBackStack(null);
			ft.commit();
			break;

		case R.id.complain_bt1:

			if (alarmInfo != null && !flag && check_status()) {
				// 拼接json
				Map<String, String> m1 = new HashMap<String, String>();
				m1.put("orderId", alarmInfo.getOrderNumber());
				m1.put("assessPersonId",
						getActivity().getSharedPreferences("userInfo",
								Context.MODE_PRIVATE).getString("userid", ""));
				m1.put("assessPersonName",
						getActivity().getSharedPreferences("userInfo",
								Context.MODE_PRIVATE).getString("username", ""));
				m1.put("contractNumber", alarmInfo.getContractNumber());
				// 未及时到货
				m1.put("noIntimeAssess", (checkBox1.isChecked() ? "1" : "0"));
				// 驾驶员未接电话
				m1.put("noAnswerPhone", (checkBox2.isChecked() ? "1" : "0"));
				// 驾驶员联系方式不正确
				m1.put("errorPhoneNumber", (checkBox3.isChecked() ? "1" : "0"));
				// 其他
				if (complain_others.getText().toString().trim().length() > 60) {
					Toast.makeText(getActivity(), "您输入的内容已超过限定字数 ,无法提交",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					m1.put("remark", complain_others.getText().toString());
				}

				// 投诉人ID
				m1.put("complaintUserId",
						getActivity().getSharedPreferences("userInfo",
								Context.MODE_PRIVATE).getString("userid", ""));

				final WebServiceUtils wsutils = WebServiceUtils.getInstance();
				wsutils.setContext(getActivity().getApplicationContext());
				wsutils.setMethodName("insertMobileComplaint");
				wsutils.setWstag("TrackService");
				// 设置传入参数
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("sendJson", gson.toJson(m1));
				wsutils.setInfactParams(m2);

				myDialog = ProgressDialog.show(getActivity(), "",
						"提交中,请稍等 ...", true);

				new Thread(new Runnable() {
					@Override
					public void run() {
						SoapObject result = null;
						try {
							result = wsutils.visit();
						} catch (Exception e) {
							Log.e("COMPLAIN_E", e.getMessage());
							handler.post(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(getActivity(), "连接服务器失败",
											Toast.LENGTH_LONG).show();

								}
							});
							e.printStackTrace();
						}

						if (result.getProperty(0).toString().equals("true")) {
							handler.sendEmptyMessage(1);
						} else {
							handler.sendEmptyMessage(2);
						}
					}
				}).start();
			} else {
				Toast.makeText(getActivity(), "请输入或者选择投诉内容！",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.complain_creat_time:
			new DatePickerDialog(getActivity(), benDateSetListener, mYear,
					mMonth, mDay).show();
			break;
		default:
			break;
		}
	}

	private boolean check_status() {
		if (checkBox1.isChecked())
			return true;
		if (checkBox2.isChecked())
			return true;
		if (checkBox3.isChecked())
			return true;
		if (!complain_others.getText().toString().equals(""))
			return true;
		else
			return false;
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
			complain_NcCreateDate.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
		}
	};

}
