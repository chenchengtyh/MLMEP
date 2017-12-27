package com.nti.mlmep.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nti.mlmep.R;
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
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ComplainSearchFragment extends Fragment implements OnClickListener {

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

	static final int DATE_DIALOG1_ID = 0;
	static final int DATE_DIALOG2_ID = 1;

	private TextView bt_date1c;
	private TextView bt_date2c;
	
	DatePickerDialog dpd;

	private FragmentManager fm;
	private FragmentTransaction ft;

	FragmentCallBack fragmentCallBack = null;

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
		View view = inflater.inflate(R.layout.activity_track_main_asscess,
				container, false);
		Bundle bundle2 = new Bundle();
		bundle2.putString("change_title", "change_back_home");
		fragmentCallBack.callbackFun1(bundle2);
		initUI(view);
		return view;
	}

	private void initUI(View view) {
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
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
			// bundle_bt2.putString("change_title", "change_title");
			// bundle_bt2.putInt("change_title_track", 1);
			// fragmentCallBack.callbackFun1(bundle_bt2);
			trackFragment.setArguments(bundle_bt2);

			ft.replace(R.id.frame_container, trackFragment,"TrackFragment");
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
			new DatePickerDialog(getActivity(),R.style.AppDateTheme_Dialog, benDateSetListener,
					b_mYear, b_mMonth, b_mDay).show();
			//.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", Cancle);
			break;
		case R.id.bt_date2:
			new DatePickerDialog(getActivity(),R.style.AppDateTheme_Dialog, endDateSetListener,
					e_mYear, e_mMonth, e_mDay).show();
			break;
		case R.id.bt_date1c:
			new DatePickerDialog(getActivity(),R.style.AppDateTheme_Dialog, benDateSetListener,
					b_mYear, b_mMonth, b_mDay).show();
			break;
		case R.id.bt_date2c:
			new DatePickerDialog(getActivity(),R.style.AppDateTheme_Dialog, endDateSetListener,
					e_mYear, e_mMonth, e_mDay).show();
			break;
		case R.id.et_ben_date:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					benDateSetListener, b_mYear, b_mMonth, b_mDay).show();
			break;
		case R.id.et_end_date:
			new DatePickerDialog(getActivity(), R.style.AppDateTheme_Dialog,
					endDateSetListener, e_mYear, e_mMonth, e_mDay).show();
			break;
		default:
			break;
		}

	}
	
	
	DialogInterface.OnClickListener Cancle = new DialogInterface.OnClickListener() {  
        @Override  
        public void onClick(DialogInterface dialog, int which) {  
            System.out.println("BUTTON_NEGATIVE~~");  
        }  
    }; 


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
