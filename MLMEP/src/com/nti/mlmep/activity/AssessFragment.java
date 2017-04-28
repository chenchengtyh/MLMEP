package com.nti.mlmep.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.myksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.nti.mlmep.R;
import com.nti.mlmep.activity.ServiceEvaluationActivity.SpinnerSelectedListener;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.util.SerializableMap;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AssessFragment extends Fragment implements OnClickListener,
		OnRatingBarChangeListener {

	FragmentCallBack fragmentCallBack = null;
	TrackInfoBean alarmInfo = null;

	private LinearLayout access_layout1;
	private LinearLayout access_layout2;
	
	private EditText assess_CustomName;
	private EditText assess_ContractNumber;
	private EditText assess_NcCreateDate;
	private TextView contractNumber_tv_assess;
	private TextView repertoryName_tv_assess;
	private TextView customName_tv_assess;
	private TextView plannedArrivedDate_tv_assess;
	private Button assess_creat_time;
	private Button button_search;
	private EditText editassess;
	private FragmentTransaction ft;

	// 评价星数
	private RatingBar ratingBar1; // 总体评价
	private RatingBar ratingBar2; // 到货及时性
	private RatingBar ratingBar3; // 到货完好性
	private RatingBar ratingBar4; // 服务态度

	private String totalEvaAssess = "5.0";
	private String intimeAssess = "5.0";
	private String intactAssess = "5.0";
	private String serveAttitudeAssess = "5.0";
	
	private int mYear;
	private int mMonth;
	private int mDay;

	private boolean flag = false;  //标志位，用于判断提交是否成功。

	// 提交
	private Button monitor_bt1;

	public Gson gson = new Gson();

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		alarmInfo = (TrackInfoBean) getArguments().getSerializable("map");

		fragmentCallBack = (FragmentMainActivity) activity;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_monitor, container,
				false);
		init(view);

		if (alarmInfo != null) {
			access_layout1.setVisibility(View.VISIBLE);
			access_layout2.setVisibility(View.VISIBLE);
			Bundle bundle2 = new Bundle();
			bundle2.putString("change_title", "change_back");
			fragmentCallBack.callbackFun1(bundle2);
			// 初始化参数
			assess_CustomName.setText(alarmInfo.getCustomName());
			assess_ContractNumber.setText(alarmInfo.getContractNumber());
			assess_NcCreateDate.setText(TimeUtils.dateToStr(alarmInfo
					.getNcCreateDate()).substring(0, 10));
			contractNumber_tv_assess.setText(alarmInfo.getContractNumber());
			repertoryName_tv_assess.setText(alarmInfo.getRepertoryName());
			customName_tv_assess.setText(alarmInfo.getCustomName());
			plannedArrivedDate_tv_assess.setText(TimeUtils.dateToStr(alarmInfo
					.getNcCreateDate()));
		} else {
			Bundle bundle2 = new Bundle();
			bundle2.putString("change_title", "change_back_home");
			fragmentCallBack.callbackFun1(bundle2);
		}
		button_search.setOnClickListener(this);
		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub

		flag = false;
		
		access_layout1 = (LinearLayout) view.findViewById(R.id.access_layout1);
		access_layout2 = (LinearLayout) view.findViewById(R.id.access_layout2);
		access_layout1.setVisibility(View.GONE);
		access_layout2.setVisibility(View.GONE);
		assess_CustomName = (EditText) view
				.findViewById(R.id.assess_CustomName);
		assess_ContractNumber = (EditText) view
				.findViewById(R.id.assess_ContractNumber);
		assess_NcCreateDate = (EditText) view
				.findViewById(R.id.assess_NcCreateDate);
		Calendar calendar = Calendar.getInstance();
		assess_NcCreateDate.setText(TimeUtils.getTime(
				calendar.getTimeInMillis(), TimeUtils.DATE_FORMAT_DATE));

		assess_creat_time = (Button) view.findViewById(R.id.assess_creat_time);
		
		contractNumber_tv_assess = (TextView) view
				.findViewById(R.id.contractNumber_tv_assess);
		repertoryName_tv_assess = (TextView) view
				.findViewById(R.id.repertoryName_tv_assess);
		customName_tv_assess = (TextView) view
				.findViewById(R.id.customName_tv_assess);
		plannedArrivedDate_tv_assess = (TextView) view
				.findViewById(R.id.plannedArrivedDate_tv_assess);
		button_search = (Button) view.findViewById(R.id.button_search);
		editassess = (EditText) view.findViewById(R.id.editassess);

		ratingBar1 = (RatingBar) view.findViewById(R.id.ratingBar1);
		ratingBar2 = (RatingBar) view.findViewById(R.id.ratingBar2);
		ratingBar3 = (RatingBar) view.findViewById(R.id.ratingBar3);
		ratingBar4 = (RatingBar) view.findViewById(R.id.ratingBar4);

		ratingBar1.setOnRatingBarChangeListener(this);
		ratingBar2.setOnRatingBarChangeListener(this);
		ratingBar3.setOnRatingBarChangeListener(this);
		ratingBar4.setOnRatingBarChangeListener(this);

		monitor_bt1 = (Button) view.findViewById(R.id.monitor_bt1);
		monitor_bt1.setOnClickListener(this);
		assess_creat_time.setOnClickListener(this);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_search:

			FragmentManager fm = getFragmentManager();
			ft = fm.beginTransaction();
			TrackFragment trackFragment = new TrackFragment();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("customName", assess_CustomName.getText().toString());
			params.put("contractNumber", assess_ContractNumber.getText()
					.toString());
			params.put("transportStatus", "");
			params.put("startTime", assess_NcCreateDate.getText().toString());
			params.put("endTime", assess_NcCreateDate.getText().toString());
			SerializableMap smap = new SerializableMap();
			smap.setMap(params);
			Bundle bundle_bt2 = new Bundle();
			bundle_bt2.putSerializable("serializableMap", smap);
			// bundle_bt2.putString("change_title", "change_title");
			// bundle_bt2.putInt("change_title_track", 1);
			//fragmentCallBack.callbackFun3(bundle_bt2);
			trackFragment.setArguments(bundle_bt2);

			ft.replace(R.id.frame_container, trackFragment);
			ft.addToBackStack(null);
			// ft.replace(R.id.frame_container, trackFragment);
			ft.commit();

			break;

		case R.id.monitor_bt1:
			if (alarmInfo != null && !flag) {
				// 拼接json
				Map<String, String> m1 = new HashMap<String, String>();
				m1.put("orderId", alarmInfo.getOrderNumber());
				m1.put("assessPersonId",
						getActivity().getSharedPreferences("userInfo",
								Context.MODE_PRIVATE).getString("userid", ""));
				m1.put("assessPersonName",
						getActivity().getSharedPreferences("userInfo",
								Context.MODE_PRIVATE).getString("username", ""));
				m1.put("totalEvaAssess", totalEvaAssess);
				m1.put("intimeAssess", intimeAssess);
				m1.put("intactAssess", intactAssess);
				m1.put("serveAttitudeAssess", serveAttitudeAssess);
				m1.put("remark", editassess.getText().toString());
				// gson.toJson(m1);
				WebServiceUtils wsutils = WebServiceUtils.getInstance();
				wsutils.setContext(getActivity().getApplicationContext());
				wsutils.setMethodName("insertMobileEva");
				wsutils.setWstag("TrackService");
				// 设置传入参数
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("sendJson", gson.toJson(m1));
				wsutils.setInfactParams(m2);

				SoapObject result = null;
				try {
					ProgressDialog MyDialog = ProgressDialog.show(
							getActivity(), "", "提交中,请稍等 ...", true);
					result = wsutils.visit();

					if (result.getProperty(0).toString().equals("true")) {
						// 将可选内容与ArrayAdapter连接起来
						Toast.makeText(getActivity(), "提交成功，谢谢评价!",
								Toast.LENGTH_SHORT).show();
						flag = true;//提交成功将标志位设为true，防止客户重复提交
						//清空数据
						//alarmInfo = null;
						assess_CustomName.setText("");
						assess_ContractNumber.setText("");
						contractNumber_tv_assess.setText("");
						repertoryName_tv_assess.setText("");
						customName_tv_assess.setText("");
						plannedArrivedDate_tv_assess.setText("");
						Calendar calendar = Calendar.getInstance();
						assess_NcCreateDate.setText(TimeUtils.getTime(
								calendar.getTimeInMillis(), TimeUtils.DATE_FORMAT_DATE));
						editassess.setText("");
						MyDialog.hide();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getActivity(), "亲！请先查询好订单后再进行评价哦~",
						Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.assess_creat_time:
			new DatePickerDialog(getActivity(), benDateSetListener,
					mYear, mMonth, mDay)
			.show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		// TODO Auto-generated method stub

		switch (ratingBar.getId()) {
		case R.id.ratingBar1:
			totalEvaAssess = String.valueOf(rating);
			break;
		case R.id.ratingBar2:
			intimeAssess = String.valueOf(rating);
			break;
		case R.id.ratingBar3:
			intactAssess = String.valueOf(rating);
			break;
		case R.id.ratingBar4:
			serveAttitudeAssess = String.valueOf(rating);
			break;
		default:
			break;
		}

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
			assess_NcCreateDate.setText(new StringBuilder()
			.append(mYear)
			.append("-")
			.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
					: (mMonth + 1)).append("-")
			.append((mDay < 10) ? "0" + mDay : mDay));
		}
	};
}
