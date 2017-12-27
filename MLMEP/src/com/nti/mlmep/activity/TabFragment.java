package com.nti.mlmep.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nti.mlmep.R;
import com.nti.mlmep.activity.TrackMainActivity.SpinnerSelectedListener;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.util.SerializableMap;
import com.nti.mlmep.util.TimeUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class TabFragment extends Fragment implements OnClickListener {

	private Button bt2;
	private Button bt1;
	private Button bt3;
	private Button bt_date1;
	private Button bt_date2;
	private EditText et_on;
	private EditText et_cn;
	private EditText et_pn;
	private EditText et_tc;
	private Spinner et_os;
	private TextView et_ben_date;
	private TextView et_end_date;
	private EditText et_al;
	private int b_mYear = 0;
	private int b_mMonth;
	private int b_mDay;
	private int e_mYear = 0;
	private int e_mMonth;
	private int e_mDay;
	private ArrayAdapter<String> adapter;
	@SuppressWarnings("rawtypes")
	private List strArr = new ArrayList();

	static final int DATE_DIALOG1_ID = 0;
	static final int DATE_DIALOG2_ID = 1;

	private String tab_flag_page;
	private FragmentManager fm;
	private FragmentTransaction ft;

	FragmentCallBack fragmentCallBack = null;

	private Button imageButton1;
	private Button imageButton2;
	private Button imageButton3;
	private Button imageButton4;

	private TextView bt_date1c;
	private TextView bt_date2c;

	private TextView tab_spinner_right;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		tab_flag_page = getArguments().getString("flag_page");

		fragmentCallBack = (FragmentMainActivity) activity;

		Log.d("jochen", "TabFragment-----onAttach-------");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (tab_flag_page.equals("首页")) {
			View view = inflater.inflate(R.layout.fragment_home, container,
					false);
			imageButton1 = (Button) view.findViewById(R.id.imageButton1);
			imageButton2 = (Button) view.findViewById(R.id.imageButton2);
			imageButton3 = (Button) view.findViewById(R.id.imageButton3);
			imageButton4 = (Button) view.findViewById(R.id.imageButton4);
			imageButton1.setOnClickListener(this);
			imageButton2.setOnClickListener(this);
			imageButton3.setOnClickListener(this);
			imageButton4.setOnClickListener(this);
			return view;
		} else {

			Bundle bundle2 = new Bundle();
			bundle2.putString("change_title", "change_back_home");
			fragmentCallBack.callbackFun1(bundle2);

			View view = inflater.inflate(R.layout.activity_track_main,
					container, false);

			tab_spinner_right = (TextView) view
					.findViewById(R.id.tab_spinner_right);
			tab_spinner_right.setOnClickListener(this);
			strArr = initTrack(view);

			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, strArr);

			// 设置下拉列表的风格
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// 将adapter 添加到spinner中
			et_os.setAdapter(adapter);

			// 添加事件Spinner事件监听
			et_os.setOnItemSelectedListener(new SpinnerSelectedListener());

			// 设置默认值
			et_os.setVisibility(View.VISIBLE);
			return view;
		}
		// } else if (tab_flag_page.equals("评价")) {
		// Bundle bundle2 = new Bundle();
		// bundle2.putString("change_title", "change_back_home");
		// fragmentCallBack.callbackFun1(bundle2);
		// View view = inflater.inflate(R.layout.fragment_monitor, container,
		// false);
		// return view;
		// } else if (tab_flag_page.equals("投诉")) {
		// Bundle bundle2 = new Bundle();
		// bundle2.putString("change_title", "change_back_home");
		// fragmentCallBack.callbackFun1(bundle2);
		// View view = inflater.inflate(R.layout.fragment_complain, container,
		// false);
		// return view;
		// } else {
		// Bundle bundle2 = new Bundle();
		// bundle2.putString("change_title", "change_back_home");
		// fragmentCallBack.callbackFun1(bundle2);
		// View view = inflater.inflate(R.layout.fragment_mymessage,
		// container, false);
		// return view;
		// }
	}

	@SuppressWarnings("unchecked")
	private List initTrack(View view) {
		// TODO Auto-generated method stub

		bt2 = (Button) view.findViewById(R.id.bt2);
		// bt1 = (Button) view.findViewById(R.id.bt1);
		bt2.setOnClickListener(this);
		// bt1.setOnClickListener(this);
		// bt3.setOnClickListener(this);
		bt_date1 = (Button) view.findViewById(R.id.bt_date1);
		bt_date2 = (Button) view.findViewById(R.id.bt_date2);
		bt_date1.setOnClickListener(this);
		bt_date2.setOnClickListener(this);

		bt_date1c = (TextView) view.findViewById(R.id.bt_date1c);
		bt_date2c = (TextView) view.findViewById(R.id.bt_date2c);
		bt_date1c.setOnClickListener(this);
		bt_date2c.setOnClickListener(this);

		et_on = (EditText) view.findViewById(R.id.et_on);
		et_cn = (EditText) view.findViewById(R.id.et_cn);
		et_os = (Spinner) view.findViewById(R.id.et_os);
		et_al = (EditText) view.findViewById(R.id.et_al);
		
		et_ben_date = (TextView) view.findViewById(R.id.et_ben_date);
		et_end_date = (TextView) view.findViewById(R.id.et_end_date);
		et_ben_date.setOnClickListener(this);
		et_end_date.setOnClickListener(this);
		final Calendar c = Calendar.getInstance();
		if (0 == e_mYear) {
			e_mYear = c.get(Calendar.YEAR);
			e_mMonth = c.get(Calendar.MONTH);
			e_mDay = c.get(Calendar.DAY_OF_MONTH);
		}

		// 默认显示近3天的数据
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if(0 == e_mYear){
			et_end_date.setText(TimeUtils.getTime(calendar.getTimeInMillis(),
					TimeUtils.DATE_FORMAT_DATE));
		}else{
			et_end_date.setText(new StringBuilder()
			.append(e_mYear)
			.append("-")
			.append((e_mMonth + 1) < 10 ? "0" + (e_mMonth + 1)
					: (e_mMonth + 1)).append("-")
			.append((e_mDay < 10) ? "0" + e_mDay : e_mDay));
		}
		
		calendar.add(Calendar.DATE, -3);
		if (0 == b_mYear) {

			b_mYear = calendar.get(Calendar.YEAR);
			b_mMonth = calendar.get(Calendar.MONTH);
			b_mDay = calendar.get(Calendar.DAY_OF_MONTH);
			et_ben_date.setText(TimeUtils.getTime(calendar.getTimeInMillis(),
					TimeUtils.DATE_FORMAT_DATE));
		} else{
			et_ben_date.setText(new StringBuilder()
			.append(b_mYear)
			.append("-")
			.append((b_mMonth + 1) < 10 ? "0" + (b_mMonth + 1)
					: (b_mMonth + 1)).append("-")
			.append((b_mDay < 10) ? "0" + b_mDay : b_mDay));
		}

		// 将可选内容与ArrayAdapter连接起来
		// '01','订单生成','02','运输单形成','03'
		// ,'运输单审核通过','04','到达发货仓库','05','准运证生成','06','订单发货','07','订单到达商业仓库','08','订单到货确认'
		List strArr = new ArrayList();
		strArr.add("物流状态(全部)");
		strArr.add("01订单生成");
		strArr.add("02运输单形成");
		strArr.add("03运输单审核通过");
		strArr.add("04到达发货仓库");
		strArr.add("05准运证生成");
		strArr.add("06订单在途");
		strArr.add("07订单到达商业仓库");

		return strArr;
	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// view.setText();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("chencheng", "v,getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.imageButton1:
			Bundle bundle4 = new Bundle();
			bundle4.putString("tab_flag_page", "xiaoxi");
			fragmentCallBack.callbackFun2(bundle4);
			break;
		case R.id.imageButton2:
			Bundle bundle2 = new Bundle();
			TrackInfoBean alarmInfo = null;
			// bundle2.putString("tab_flag_page", "pingjia");
			bundle2.putSerializable("map", alarmInfo);
			fragmentCallBack.callbackFun3(bundle2);
			break;
		case R.id.imageButton3:
			Bundle bundle = new Bundle();
			bundle.putString("tab_flag_page", "wuliu");
			fragmentCallBack.callbackFun2(bundle);
			break;
		case R.id.imageButton4:
			Bundle bundle3 = new Bundle();
			bundle3.putString("tab_flag_page", "tousu");
			fragmentCallBack.callbackFun2(bundle3);
			break;
		case R.id.bt2:
			FragmentManager fm = getFragmentManager();
			ft = fm.beginTransaction();
			TrackFragment trackFragment = new TrackFragment();
			Map<String, Object> params = new HashMap<String, Object>();

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
			params.put("orderNumber", et_on.getText().toString());
			params.put("contractNumber", et_cn.getText().toString());
			// params.put("plateNumber", et_pn.getText().toString());
			// params.put("transportCode", et_tc.getText().toString());
			if (adapter.getItem(et_os.getSelectedItemPosition()).trim()
					.equals("物流状态(全部)")) {
				params.put("transportStatus", "");
			} else {
				params.put("transportStatus",
						adapter.getItem(et_os.getSelectedItemPosition())
								.substring(0, 2));
			}
			params.put("customName", et_al.getText().toString());
			params.put("startTime", et_ben_date.getText().toString());
			params.put("endTime", et_end_date.getText().toString());
			SharedPreferences sharedPreferences = getActivity()
					.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
			params.put("userName", sharedPreferences.getString("username", ""));
			params.put("password", sharedPreferences.getString("password", ""));

			SerializableMap smap = new SerializableMap();
			smap.setMap(params);
			Bundle bundle_bt2 = new Bundle();
			bundle_bt2.putSerializable("serializableMap", smap);
			bundle_bt2.putString("change_title", "change_title");
			// bundle_bt2.putInt("change_title_track", 1);
			fragmentCallBack.callbackFun1(bundle_bt2);
			trackFragment.setArguments(bundle_bt2);

			ft.replace(R.id.frame_container, trackFragment, "TrackFragment");
			ft.addToBackStack(null);
			// ft.replace(R.id.frame_container, trackFragment);
			ft.commit();
			break;
		case R.id.bt1:
			et_on.setText("");
			et_cn.setText("");
			et_pn.setText("");
			et_tc.setText("");
			et_os.setSelection(0);
			et_al.setText("");
			et_ben_date.setText("");
			et_end_date.setText("");
			// et_ben_date.setText(TimeUtils.getTime(Calendar.getInstance().getTimeInMillis(),TimeUtils.DATE_FORMAT_DATE));
			// et_end_date.setText(TimeUtils.getTime(Calendar.getInstance().getTimeInMillis(),TimeUtils.DATE_FORMAT_DATE));
			break;
		case R.id.bt_date1:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					benDateSetListener, b_mYear, b_mMonth, b_mDay).show();
			break;
		case R.id.bt_date2:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					endDateSetListener, e_mYear, e_mMonth, e_mDay).show();
			break;
		case R.id.bt_date1c:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					benDateSetListener, b_mYear, b_mMonth, b_mDay).show();
			break;
		case R.id.bt_date2c:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					endDateSetListener, e_mYear, e_mMonth, e_mDay).show();
			break;
		case R.id.et_ben_date:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					benDateSetListener, b_mYear, b_mMonth, b_mDay).show();
			break;
		case R.id.et_end_date:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					endDateSetListener, e_mYear, e_mMonth, e_mDay).show();
			break;
		case R.id.tab_spinner_right:
			et_os.performClick();
			break;
		default:
			break;
		}
	}

	private DatePickerDialog.OnDateSetListener benDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			b_mYear = year;
			b_mMonth = monthOfYear;
			b_mDay = dayOfMonth;
			updateDisplay("et_ben_date");
		}
	};

	private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			e_mYear = year;
			e_mMonth = monthOfYear;
			e_mDay = dayOfMonth;
			updateDisplay("et_end_date");
		}
	};

	// updates the date in the TextView
	private void updateDisplay(String btn) {
		if (btn.equals("et_ben_date")) {

			if (TimeUtils.compareDate(
					new StringBuilder()
							.append(b_mYear)
							.append("-")
							.append((b_mMonth + 1) < 10 ? "0" + (b_mMonth + 1)
									: (b_mMonth + 1)).append("-")
							.append((b_mDay < 10) ? "0" + b_mDay : b_mDay)
							.toString(), et_end_date.getText().toString()) == 1) {
				new AlertDialog.Builder(getActivity()).setTitle("错误")
						.setMessage("起始日期不能大于结束日期")
						.setPositiveButton("确定", null).show();
			} else {
				et_ben_date.setText(new StringBuilder()
						.append(b_mYear)
						.append("-")
						.append((b_mMonth + 1) < 10 ? "0" + (b_mMonth + 1)
								: (b_mMonth + 1)).append("-")
						.append((b_mDay < 10) ? "0" + b_mDay : b_mDay));
			}
		}
		if (btn.equals("et_end_date")) {
			if (TimeUtils.compareDate(
					new StringBuilder()
							.append(e_mYear)
							.append("-")
							.append((e_mMonth + 1) < 10 ? "0" + (e_mMonth + 1)
									: (e_mMonth + 1)).append("-")
							.append((e_mDay < 10) ? "0" + e_mDay : e_mDay)
							.toString(), et_ben_date.getText().toString()) == -1) {
				new AlertDialog.Builder(getActivity()).setTitle("错误")
						.setMessage("结束日期不能小于起始日期")
						.setPositiveButton("确定", null).show();
			} else {
				et_end_date.setText(new StringBuilder()
						.append(e_mYear)
						.append("-")
						.append((e_mMonth + 1) < 10 ? "0" + (e_mMonth + 1)
								: (e_mMonth + 1)).append("-")
						.append((e_mDay < 10) ? "0" + e_mDay : e_mDay));
			}
		}

	}

}
