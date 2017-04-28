package com.nti.mlmep.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.nti.mlmep.R;
import com.nti.mlmep.activity.base.BaseActivity;
import com.nti.mlmep.util.SerializableMap;
import com.nti.mlmep.util.TimeUtils;

/**
 * @author sunsi
 * 物流跟踪
 */
public class TrackMainActivity extends BaseActivity implements OnClickListener {

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
	private EditText et_ben_date;
	private EditText et_end_date;
	private EditText et_al;
	private int mYear;
	private int mMonth;
	private int mDay;
	private ArrayAdapter<String> adapter;
	@SuppressWarnings("rawtypes")
	private List strArr = new ArrayList();

	static final int DATE_DIALOG1_ID = 0;
	static final int DATE_DIALOG2_ID = 1;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_main);
		bt2 = (Button) findViewById(R.id.bt2);
		bt1 = (Button) findViewById(R.id.bt1);
		//bt3 = (Button) findViewById(R.id.bt3);
		bt2.setOnClickListener(this);
		bt1.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt_date1 = (Button) findViewById(R.id.bt_date1);
		bt_date2 = (Button) findViewById(R.id.bt_date2);
		bt_date1.setOnClickListener(this);
		bt_date2.setOnClickListener(this);
		et_on = (EditText) findViewById(R.id.et_on);
		et_cn = (EditText) findViewById(R.id.et_cn);
		//et_pn = (EditText) findViewById(R.id.et_pn);
		//et_tc = (EditText) findViewById(R.id.et_tc);
		et_os = (Spinner) findViewById(R.id.et_os);
		et_al = (EditText) findViewById(R.id.et_al);
		et_ben_date = (EditText) findViewById(R.id.et_ben_date);
		et_end_date = (EditText) findViewById(R.id.et_end_date);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// 默认显示近3天的数据
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		et_end_date.setText(TimeUtils.getTime(calendar.getTimeInMillis(),
				TimeUtils.DATE_FORMAT_DATE));
		calendar.add(Calendar.DATE, -3);
		et_ben_date.setText(TimeUtils.getTime(calendar.getTimeInMillis(),
				TimeUtils.DATE_FORMAT_DATE));

		// 将可选内容与ArrayAdapter连接起来
		// '01','订单生成','02','运输单形成','03'
		// ,'运输单审核通过','04','到达发货仓库','05','准运证生成','06','订单发货','07','订单到达商业仓库','08','订单到货确认'
		strArr.add("全部");
		strArr.add("01订单生成");
		strArr.add("02运输单形成");
		strArr.add("03运输单审核通过");
		strArr.add("04到达发货仓库");
		strArr.add("05准运证生成");
		strArr.add("06订单发货");
		strArr.add("07订单到达商业仓库");
		strArr.add("08订单到货确认");

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strArr);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		et_os.setAdapter(adapter);

		// 添加事件Spinner事件监听
		et_os.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		et_os.setVisibility(View.VISIBLE);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt2:
			Intent intent = new Intent();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderNumber", et_on.getText().toString());
			params.put("contractNumber", et_cn.getText().toString());
			params.put("plateNumber", et_pn.getText().toString());
			params.put("transportCode", et_tc.getText().toString());
			if (adapter.getItem(et_os.getSelectedItemPosition()).trim()
					.equals("全部")) {
				params.put("transportStatus", "");
			} else {
				params.put("transportStatus",
						adapter.getItem(et_os.getSelectedItemPosition())
								.substring(0, 2));
			}
			params.put("customName", et_al.getText().toString());
			params.put("startTime", et_ben_date.getText().toString());
			params.put("endTime", et_end_date.getText().toString());
			SharedPreferences sharedPreferences = getSharedPreferences(
					"userInfo", Context.MODE_PRIVATE);
			params.put("userName", sharedPreferences.getString("username", ""));
			params.put("password", sharedPreferences.getString("password", ""));

			SerializableMap smap = new SerializableMap();
			smap.setMap(params);
			Bundle bundle = new Bundle();
			bundle.putSerializable("serializableMap", smap);
			intent.putExtras(bundle);
			intent.setClass(getApplicationContext(), TrackActivity.class);
			startActivity(intent);
			break;
		case R.id.bt1:
			et_on.setText("");
			et_cn.setText("");
			et_pn.setText("");
			et_tc.setText("");
			et_os.setSelection(0);
			;
			et_al.setText("");
			et_ben_date.setText("");
			et_end_date.setText("");
			// et_ben_date.setText(TimeUtils.getTime(Calendar.getInstance().getTimeInMillis(),TimeUtils.DATE_FORMAT_DATE));
			// et_end_date.setText(TimeUtils.getTime(Calendar.getInstance().getTimeInMillis(),TimeUtils.DATE_FORMAT_DATE));
			break;
		case R.id.bt_date1:
			showDialog(DATE_DIALOG1_ID);
			break;
		case R.id.bt_date2:
			showDialog(DATE_DIALOG2_ID);
			break;
		}
	}

	// updates the date in the TextView
	private void updateDisplay(String btn) {
		if (btn.equals("et_ben_date")) {

			if (TimeUtils
					.compareDate(
							new StringBuilder()
									.append(mYear)
									.append("-")
									.append((mMonth + 1) < 10 ? "0"
											+ (mMonth + 1) : (mMonth + 1))
									.append("-")
									.append((mDay < 10) ? "0" + mDay : mDay)
									.toString(), et_end_date.getText()
									.toString()) == 1) {
				new AlertDialog.Builder(this).setTitle("错误")
						.setMessage("起始日期不能大于结束日期")
						.setPositiveButton("确定", null).show();
			} else {
				et_ben_date.setText(new StringBuilder()
						.append(mYear)
						.append("-")
						.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
								: (mMonth + 1)).append("-")
						.append((mDay < 10) ? "0" + mDay : mDay));
			}
		}
		if (btn.equals("et_end_date")) {
			if (TimeUtils
					.compareDate(
							new StringBuilder()
									.append(mYear)
									.append("-")
									.append((mMonth + 1) < 10 ? "0"
											+ (mMonth + 1) : (mMonth + 1))
									.append("-")
									.append((mDay < 10) ? "0" + mDay : mDay)
									.toString(), et_ben_date.getText()
									.toString()) == -1) {
				new AlertDialog.Builder(this).setTitle("错误")
						.setMessage("结束日期不能小于起始日期")
						.setPositiveButton("确定", null).show();
			} else {
				et_end_date.setText(new StringBuilder()
						.append(mYear)
						.append("-")
						.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
								: (mMonth + 1)).append("-")
						.append((mDay < 10) ? "0" + mDay : mDay));
			}
		}

	}

	private DatePickerDialog.OnDateSetListener benDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay("et_ben_date");
		}
	};

	private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay("et_end_date");
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG1_ID:
			return new DatePickerDialog(this, benDateSetListener, mYear,
					mMonth, mDay);

		case DATE_DIALOG2_ID:
			return new DatePickerDialog(this, endDateSetListener, mYear,
					mMonth, mDay);

		}
		return null;
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
	public void initWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub

	}
}