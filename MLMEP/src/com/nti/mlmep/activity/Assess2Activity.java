package com.nti.mlmep.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.domain.SearchMobileEva;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackMessage;
import com.nti.mlmep.domain.TrackPosition;
import com.nti.mlmep.util.ExitApplication;
import com.nti.mlmep.util.SerializableMap;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Assess2Activity extends Activity implements OnClickListener {

	TextView layout_title_tv_name;
	Button title_back2;
	TrackInfoBean alarmInfo = null;

	private LinearLayout access_layout1;
	private LinearLayout access_layout2;
	private LinearLayout search_asscess_flag;
	private LinearLayout all_layout;

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
	private String b_time;
	private TrackInfoBean.MobileEva searchMobileEva = null;
	private ProgressDialog dialog;
	private ProgressDialog MyDialog;
	// ��������
	// private RatingBar ratingBar1; // ��������
	// private RatingBar ratingBar2; // ������ʱ��
	// private RatingBar ratingBar3; // ���������
	// private RatingBar ratingBar4; // ����̬��

	private RadioButton mRadio4, mRadio5, mRadio6, mRadio7, mRadio8, mRadio9,
			mRadio10, mRadio11, mRadio12, mRadio13, mRadio14, mRadio15,
			mRadio16, mRadio17;
	private RadioGroup rGroup1, rGroup2, rGroup3, rGroup4, rGroup5;
	private RadioButton radioButton1, radioButton2, radioButton3, radioButton4,
			radioButton5;
	private TextView has_assess1;
	private TextView has_assess2;
	private TextView has_assess3;

	// private String totalEvaAssess = "5.0";
	// private String intimeAssess = "5.0";
	// private String intactAssess = "5.0";
	// private String serveAttitudeAssess = "5.0";

	private TextView assess_flag;
	private int mYear;
	private int mMonth;
	private int mDay;
	private static boolean sleep;
	private boolean flag = false; // ��־λ�������ж��ύ�Ƿ�ɹ���

	// �ύ
	private Button monitor_bt1;

	public Gson gson = new Gson();

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// super.handleMessage(msg);
			switch (msg.what) {
			case 11:
				MyDialog.dismiss();
				Toast.makeText(Assess2Activity.this, "�ύʧ�ܣ�����ϵ����Ա!",
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				if (searchMobileEva != null) {
					if (searchMobileEva.getintimeAssess().equals("")) {
						mRadio8.setChecked(true);
						mRadio9.setChecked(false);
						mRadio8.setEnabled(true);
						mRadio9.setEnabled(true);
					} else if (searchMobileEva.getintimeAssess().equals("1")) {
						mRadio7.setChecked(false);
						mRadio8.setChecked(true);
						mRadio9.setChecked(false);
						mRadio7.setEnabled(false);
						mRadio8.setEnabled(false);
						mRadio9.setEnabled(false);
						mRadio8.setTextColor(R.color.bgDark);
						mRadio9.setTextColor(R.color.bgDark);
						has_assess1.setText("(������Ͷ��)");
					} else {
						mRadio7.setChecked(false);
						mRadio8.setChecked(false);
						mRadio9.setChecked(true);
						mRadio7.setEnabled(false);
						mRadio8.setEnabled(false);
						mRadio9.setEnabled(false);
						mRadio8.setTextColor(R.color.bgDark);
						mRadio9.setTextColor(R.color.bgDark);
						has_assess1.setText("(������Ͷ��)");
					}

					if (searchMobileEva.getintactAssess().equals("")) {
						mRadio11.setChecked(true);
						mRadio12.setChecked(false);
						mRadio11.setEnabled(true);
						mRadio12.setEnabled(true);
					} else if (searchMobileEva.getintactAssess().equals("1")) {
						mRadio10.setChecked(false);
						mRadio11.setChecked(true);
						mRadio12.setChecked(false);
						mRadio10.setEnabled(false);
						mRadio11.setEnabled(false);
						mRadio12.setEnabled(false);
						mRadio11.setTextColor(R.color.bgDark);
						mRadio12.setTextColor(R.color.bgDark);
						has_assess2.setText("(������Ͷ��)");
					} else {
						mRadio10.setChecked(false);
						mRadio11.setChecked(false);
						mRadio12.setChecked(true);
						mRadio10.setEnabled(false);
						mRadio11.setEnabled(false);
						mRadio12.setEnabled(false);
						mRadio11.setTextColor(R.color.bgDark);
						mRadio12.setTextColor(R.color.bgDark);
						has_assess2.setText("(������Ͷ��)");
					}

					if (searchMobileEva.getserveAttitudeAssess().equals("")) {
						mRadio14.setChecked(true);
						mRadio15.setChecked(false);
						mRadio14.setEnabled(true);
						mRadio15.setEnabled(true);
					} else if (searchMobileEva.getserveAttitudeAssess().equals(
							"1")) {
						mRadio13.setChecked(false);
						mRadio14.setChecked(true);
						mRadio15.setChecked(false);
						mRadio13.setEnabled(false);
						mRadio14.setEnabled(false);
						mRadio15.setEnabled(false);
						mRadio14.setTextColor(R.color.bgDark);
						mRadio15.setTextColor(R.color.bgDark);
						has_assess3.setText("(������Ͷ��)");
					} else {
						mRadio13.setChecked(false);
						mRadio14.setChecked(false);
						mRadio15.setChecked(true);
						mRadio13.setEnabled(false);
						mRadio14.setEnabled(false);
						mRadio15.setEnabled(false);
						mRadio14.setTextColor(R.color.bgDark);
						mRadio15.setTextColor(R.color.bgDark);
						has_assess3.setText("(������Ͷ��)");
					}

				}
				monitor_bt1.setVisibility(View.VISIBLE);
				assess_flag.setText("");
				dialog.dismiss();
				break;
			case 2:
				monitor_bt1.setVisibility(View.VISIBLE);
				editassess.setEnabled(true);// �ı���ɱ༭
				// assess_flag.setVisibility(View.GONE);// ���ر�־
				assess_flag.setText("");
				dialog.dismiss();
				break;
			case 3:
				AlertDialog.Builder builder2 = new AlertDialog.Builder(
						Assess2Activity.this)
						.setTitle("�������������ܿ�ƽ̨")
						.setIcon(R.drawable.ic_launcher)
						.setCancelable(false)
						.setMessage("�����쳣�����Ժ�����")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										// TODO Auto-generated method stub
										finish();
									}

								});
				builder2.show();
				dialog.dismiss();
				break;
			case 4:

				monitor_bt1.setVisibility(View.GONE);// �ύ��ť���أ������۵Ķ����޷��ٴ��ύ
				editassess.setEnabled(false);// �ı��򲻿ɱ༭
				editassess.setText(alarmInfo.getMobileEva().get(0).getremark());// ��������
				assess_flag.setText("������");
				mRadio16.setEnabled(false);
				mRadio17.setEnabled(false);
				if (alarmInfo.getMobileEva().get(0).getotherAssess()
						.equals("1")) {// �������۰�ť��Ŀǰ������
					mRadio16.setChecked(true);
					mRadio17.setChecked(false);
					mRadio16.setEnabled(false);
					mRadio17.setEnabled(false);
					editassess.setVisibility(View.VISIBLE);
				} else if (alarmInfo.getMobileEva().get(0).getotherAssess()
						.equals("2")) {
					mRadio16.setChecked(false);
					mRadio17.setChecked(true);
					mRadio16.setEnabled(false);
					mRadio17.setEnabled(false);
					editassess.setVisibility(View.VISIBLE);
				} else {
					mRadio16.setChecked(false);
					mRadio17.setChecked(false);
					mRadio16.setEnabled(false);
					mRadio17.setEnabled(false);
					editassess.setVisibility(View.GONE);
				}
				if (alarmInfo.getMobileEva().get(0).getintimeAssess()
						.equals("0")) {// ��ʱ�������۰�ť,0����(����),1�ϸ�,2���ϸ�
					mRadio7.setChecked(true);
					mRadio8.setChecked(false);
					mRadio9.setChecked(false);
					mRadio7.setEnabled(false);
					mRadio8.setEnabled(false);
					mRadio9.setEnabled(false);

				} else if (alarmInfo.getMobileEva().get(0).getintimeAssess()
						.equals("1")) {
					mRadio7.setChecked(false);
					mRadio8.setChecked(true);
					mRadio9.setChecked(false);
					mRadio7.setEnabled(false);
					mRadio8.setEnabled(false);
					mRadio9.setEnabled(false);
				} else {
					mRadio7.setChecked(false);
					mRadio8.setChecked(false);
					mRadio9.setChecked(true);
					mRadio7.setEnabled(false);
					mRadio8.setEnabled(false);
					mRadio9.setEnabled(false);
				}
				//
				if (alarmInfo.getMobileEva().get(0).getintactAssess()
						.equals("0")) {// ������������۰�ť,0����(����),1�ϸ�,2���ϸ�
					mRadio10.setChecked(true);
					mRadio11.setChecked(false);
					mRadio12.setChecked(false);
					mRadio10.setEnabled(false);
					mRadio11.setEnabled(false);
					mRadio12.setEnabled(false);

				} else if (alarmInfo.getMobileEva().get(0).getintactAssess()
						.equals("1")) {
					mRadio10.setChecked(false);
					mRadio11.setChecked(true);
					mRadio12.setChecked(false);
					mRadio10.setEnabled(false);
					mRadio11.setEnabled(false);
					mRadio12.setEnabled(false);
				} else {
					mRadio10.setChecked(false);
					mRadio11.setChecked(false);
					mRadio12.setChecked(true);
					mRadio10.setEnabled(false);
					mRadio11.setEnabled(false);
					mRadio12.setEnabled(false);
				}
				//
				if (alarmInfo.getMobileEva().get(0).getserveAttitudeAssess()
						.equals("0")) {// ����̬�����۰�ť,0����(����),1�ϸ�,2���ϸ�
					mRadio13.setChecked(true);
					mRadio14.setChecked(false);
					mRadio15.setChecked(false);
					mRadio13.setEnabled(false);
					mRadio14.setEnabled(false);
					mRadio15.setEnabled(false);

				} else if (alarmInfo.getMobileEva().get(0)
						.getserveAttitudeAssess().equals("1")) {
					mRadio13.setChecked(false);
					mRadio14.setChecked(true);
					mRadio15.setChecked(false);
					mRadio13.setEnabled(false);
					mRadio14.setEnabled(false);
					mRadio15.setEnabled(false);
				} else {
					mRadio13.setChecked(false);
					mRadio14.setChecked(false);
					mRadio15.setChecked(true);
					mRadio13.setEnabled(false);
					mRadio14.setEnabled(false);
					mRadio15.setEnabled(false);

				}

				dialog.dismiss();
				break;
			case 5:
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Assess2Activity.this)
						.setTitle("�������������ܿ�ƽ̨")
						.setIcon(R.drawable.ic_launcher)
						.setCancelable(false)
						.setMessage("����δ�������޷��������۲���")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										// TODO Auto-generated method stub
										finish();
									}

								});
				builder.show();

				break;
			default:
				break;
			}
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.fragment_monitor_second);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title2);

		ExitApplication.getInstance().addActivity(this); // ��ջ��������

		Intent it = getIntent();
		alarmInfo = (TrackInfoBean) it.getSerializableExtra("map");

		init();

		search_asscess_flag.setVisibility(View.GONE);
		access_layout1.setVisibility(View.VISIBLE);
		access_layout2.setVisibility(View.VISIBLE);
		contractNumber_tv_assess.setText(alarmInfo.getContractNumber());
		repertoryName_tv_assess.setText(alarmInfo.getRepertoryName());
		customName_tv_assess.setText(alarmInfo.getCustomName());
		plannedArrivedDate_tv_assess.setText(TimeUtils.dateToStr(alarmInfo
				.getNcCreateDate()));
		SearchMoblieEva(alarmInfo.getContractNumber());

		// if (alarmInfo != null) {// ���ϼ���ȡ��������
		// search_asscess_flag.setVisibility(View.GONE);// ��ѯ���۱�־����
		// access_layout1.setVisibility(View.VISIBLE);
		// access_layout2.setVisibility(View.VISIBLE);
		// // Bundle bundle2 = new Bundle();
		// // bundle2.putString("change_title", "change_back");
		// // fragmentCallBack.callbackFun1(bundle2);
		// // ��ʼ������
		// assess_CustomName.setText(alarmInfo.getCustomName());
		// assess_ContractNumber.setText(alarmInfo.getContractNumber());
		// assess_NcCreateDate.setText(TimeUtils.dateToStr(
		// alarmInfo.getNcCreateDate()).substring(0, 10));
		// contractNumber_tv_assess.setText(alarmInfo.getContractNumber());
		// repertoryName_tv_assess.setText(alarmInfo.getRepertoryName());
		// customName_tv_assess.setText(alarmInfo.getCustomName());
		// plannedArrivedDate_tv_assess.setText(TimeUtils.dateToStr(alarmInfo
		// .getNcCreateDate()));
		//
		// // �ж��Ƿ����۹���eva_flagΪ1��ʾ���۹�������0.
		// //intimeAssess,intactAssess.serveAttitudeAssessĬ�ϲ�Ϊ��
		// if (alarmInfo.geteva_flag().equals("1")
		// && (!alarmInfo.getMobileEva().get(0).getintimeAssess()
		// .equals(""))
		// && (!alarmInfo.getMobileEva().get(0).getintactAssess()
		// .equals(""))
		// && (!alarmInfo.getMobileEva().get(0)
		// .getserveAttitudeAssess().equals(""))){
		// //if (alarmInfo.getMobileEva().get(0).getevaState().equals("2")) {//
		// ���۹��������ύ��ť
		// monitor_bt1.setVisibility(View.GONE);// �ύ��ť���أ������۵Ķ����޷��ٴ��ύ
		// editassess.setEnabled(false);// �ı��򲻿ɱ༭
		// Log.d("jochen", "jochen----assess_flag");
		// // assess_flag.setVisibility(View.VISIBLE);// ��ʾ�����۱�ʶ
		// assess_flag.setText("������");
		// editassess.setText(alarmInfo.getMobileEva().get(0).getremark());//
		// ��������
		// if (alarmInfo.getMobileEva().size() != 0) {// ���۹�,��������Ϣ
		//
		// if (alarmInfo.getMobileEva().get(0).getotherAssess()
		// .equals("1")) {// �������۰�ť��Ŀǰ������
		// mRadio16.setChecked(true);
		// mRadio17.setChecked(false);
		// mRadio16.setEnabled(false);
		// mRadio17.setEnabled(false);
		// editassess.setVisibility(View.VISIBLE);
		// } else if (alarmInfo.getMobileEva().get(0).getotherAssess()
		// .equals("2")) {
		// mRadio16.setChecked(false);
		// mRadio17.setChecked(true);
		// mRadio16.setEnabled(false);
		// mRadio17.setEnabled(false);
		// editassess.setVisibility(View.VISIBLE);
		// } else {
		// mRadio16.setChecked(false);
		// mRadio17.setChecked(false);
		// mRadio16.setEnabled(false);
		// mRadio17.setEnabled(false);
		// editassess.setVisibility(View.GONE);
		// }
		//
		// /*
		// * if (alarmInfo.getMobileEva().get(0).gettotalEvaAssess()
		// * .equals("0")) { mRadio4.setChecked(true);
		// * mRadio5.setChecked(false); mRadio6.setChecked(false);
		// * mRadio4.setEnabled(false); mRadio5.setEnabled(false);
		// * mRadio6.setEnabled(false);
		// *
		// * } else if (alarmInfo.getMobileEva().get(0)
		// * .gettotalEvaAssess().equals("1")) {
		// * mRadio4.setChecked(false); mRadio5.setChecked(true);
		// * mRadio6.setChecked(false); mRadio4.setEnabled(false);
		// * mRadio5.setEnabled(false); mRadio6.setEnabled(false); }
		// * else { mRadio4.setChecked(false);
		// * mRadio5.setChecked(false); mRadio6.setChecked(true);
		// * mRadio4.setEnabled(false); mRadio5.setEnabled(false);
		// * mRadio6.setEnabled(false); }
		// */
		// //
		// if (alarmInfo.getMobileEva().get(0).getintimeAssess()
		// .equals("0")) {// ��ʱ�������۰�ť,0����(����),1�ϸ�,2���ϸ�
		// mRadio7.setChecked(true);
		// mRadio8.setChecked(false);
		// mRadio9.setChecked(false);
		// mRadio7.setEnabled(false);
		// mRadio8.setEnabled(false);
		// mRadio9.setEnabled(false);
		//
		// } else if (alarmInfo.getMobileEva().get(0)
		// .getintimeAssess().equals("1")) {
		// mRadio7.setChecked(false);
		// mRadio8.setChecked(true);
		// mRadio9.setChecked(false);
		// mRadio7.setEnabled(false);
		// mRadio8.setEnabled(false);
		// mRadio9.setEnabled(false);
		// } else {
		// mRadio7.setChecked(false);
		// mRadio8.setChecked(false);
		// mRadio9.setChecked(true);
		// mRadio7.setEnabled(false);
		// mRadio8.setEnabled(false);
		// mRadio9.setEnabled(false);
		// }
		// //
		// if (alarmInfo.getMobileEva().get(0).getintactAssess()
		// .equals("0")) {// ������������۰�ť,0����(����),1�ϸ�,2���ϸ�
		// mRadio10.setChecked(true);
		// mRadio11.setChecked(false);
		// mRadio12.setChecked(false);
		// mRadio10.setEnabled(false);
		// mRadio11.setEnabled(false);
		// mRadio12.setEnabled(false);
		//
		// } else if (alarmInfo.getMobileEva().get(0)
		// .getintactAssess().equals("1")) {
		// mRadio10.setChecked(false);
		// mRadio11.setChecked(true);
		// mRadio12.setChecked(false);
		// mRadio10.setEnabled(false);
		// mRadio11.setEnabled(false);
		// mRadio12.setEnabled(false);
		// } else {
		// mRadio10.setChecked(false);
		// mRadio11.setChecked(false);
		// mRadio12.setChecked(true);
		// mRadio10.setEnabled(false);
		// mRadio11.setEnabled(false);
		// mRadio12.setEnabled(false);
		// }
		// //
		// if (alarmInfo.getMobileEva().get(0)
		// .getserveAttitudeAssess().equals("0")) {// ����̬�����۰�ť,0����(����),1�ϸ�,2���ϸ�
		// mRadio13.setChecked(true);
		// mRadio14.setChecked(false);
		// mRadio15.setChecked(false);
		// mRadio13.setEnabled(false);
		// mRadio14.setEnabled(false);
		// mRadio15.setEnabled(false);
		//
		// } else if (alarmInfo.getMobileEva().get(0)
		// .getserveAttitudeAssess().equals("1")) {
		// mRadio13.setChecked(false);
		// mRadio14.setChecked(true);
		// mRadio15.setChecked(false);
		// mRadio13.setEnabled(false);
		// mRadio14.setEnabled(false);
		// mRadio15.setEnabled(false);
		// } else {
		// mRadio13.setChecked(false);
		// mRadio14.setChecked(false);
		// mRadio15.setChecked(true);
		// mRadio13.setEnabled(false);
		// mRadio14.setEnabled(false);
		// mRadio15.setEnabled(false);
		// }
		// }
		// } else {
		//
		// // ��ⶩ��Ϊδ���ۣ����Ȳ�ѯ�Ƿ���������״̬
		// SearchMoblieEva(alarmInfo.getContractNumber());
		// }
		//
		// }

	}

	// ��Ҫ����ʱ���Ȳ�ѯ����״̬���Ƿ��Ѵ�Ͷ�ߴ������˲���ѡ��
	/*
	 * @pram ������ͬ��
	 */
	private void SearchMoblieEva(final String ContractNumber) {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(Assess2Activity.this, null, "���ڼ��أ����Ե�...",
				true, false);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				// ��װ������json
				Map<String, String> m1 = new HashMap<String, String>();
				m1.put("contractNumber", ContractNumber);
				WebServiceUtils wsutils = WebServiceUtils.getInstance();
				wsutils.setContext(Assess2Activity.this.getApplicationContext());
				wsutils.setMethodName("searchMobileEva");
				wsutils.setWstag("TrackService");
				// ���ô������
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("sendJson", gson.toJson(m1));
				wsutils.setInfactParams(m2);

				SoapObject result = null;

				try {
					result = wsutils.visit();
					String jsonParam = result.getProperty(0).toString();
					Log.d("jochen", "" + jsonParam);

					if (!jsonParam.equals("string{}")) {

						if (jsonParam.equals("\"false\"")) {
							Message msg = new Message();
							msg.what = 5;
							handler.sendMessage(msg);
							return;
						}

						searchMobileEva = new TrackInfoBean.MobileEva();
						java.lang.reflect.Type type = new TypeToken<TrackInfoBean.MobileEva>() {
						}.getType();

						// String test =
						// "[{\"evaId\":\"6056DF0FE75C43989C286A9C17504A60\",\"contractNumber\":\"106820486\",\"orderId\":\"SO170601805\",\"totalEvaAssess\":\"1\",\"intimeAssess\":\"2\",\"intactAssess\":\"0\",\"serveAttitudeAssess\":\"0\",\"assessPersonId\":\"709A1410E8104E95B6C02B29D14FD8E9\",\"assessPersonName\":\"masddjl\",\"assessDatetime\":\"Sep 11, 2017 4:18:57 PM\",\"remark\":\"��������ģ��20170911\",\"otherAssess\":\"ceshi\",\"repertoryName\":\"���ݳ�Ʒ��\",\"customName\":\"����ʡ�̲ݹ�˾��ɽ�й�˾\",\"sendDate\":\"2017-06-13 11:12:56.0\",\"enterpriseName\":\"�����а�˳�����������޹�˾\"}]";
						JSONArray arr = new JSONArray(jsonParam);
						JSONObject temp = (JSONObject) arr.get(0);
						// ��json����ת����TrackMessage����
						searchMobileEva = gson.fromJson(temp.toString(), type);
						if (searchMobileEva.getevaState().equals("2")) {
							Message msg = new Message();
							msg.what = 4;
							handler.sendMessage(msg);
						} else if (searchMobileEva.getevaState().equals("1")) {
							// �����������ݣ����ʼ����Ĭ�ϲ���ѡ
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 2;
							handler.sendMessage(msg);
						}

					} else {
						Message msg = new Message();
						msg.what = 2;
						handler.sendMessage(msg);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				}
			}
		}).start();

	}

	private void init() {
		// TODO Auto-generated method stub
		// �����˵�
		layout_title_tv_name = (TextView) findViewById(R.id.layout_title_tv_name);
		title_back2 = (Button) findViewById(R.id.title_back2);
		title_back2.setOnClickListener(this);
		all_layout = (LinearLayout) findViewById(R.id.all_layout);
		search_asscess_flag = (LinearLayout) findViewById(R.id.search_asscess_flag);
		flag = false;
		assess_flag = (TextView) findViewById(R.id.assess_flag);
		access_layout1 = (LinearLayout) findViewById(R.id.access_layout1);
		access_layout2 = (LinearLayout) findViewById(R.id.access_layout2);
		access_layout1.setVisibility(View.GONE);
		access_layout2.setVisibility(View.GONE);
		assess_CustomName = (EditText) findViewById(R.id.assess_CustomName);
		assess_ContractNumber = (EditText) findViewById(R.id.assess_ContractNumber);
		assess_NcCreateDate = (EditText) findViewById(R.id.assess_NcCreateDate);
		Calendar calendar = Calendar.getInstance();
		assess_NcCreateDate.setText(TimeUtils.getTime(
				calendar.getTimeInMillis(), TimeUtils.DATE_FORMAT_DATE));

		assess_creat_time = (Button) findViewById(R.id.assess_creat_time);

		contractNumber_tv_assess = (TextView) findViewById(R.id.contractNumber_tv_assess);
		repertoryName_tv_assess = (TextView) findViewById(R.id.repertoryName_tv_assess);
		customName_tv_assess = (TextView) findViewById(R.id.customName_tv_assess);
		plannedArrivedDate_tv_assess = (TextView) findViewById(R.id.plannedArrivedDate_tv_assess);
		button_search = (Button) findViewById(R.id.button_search);
		editassess = (EditText) findViewById(R.id.editassess);

		// ///
		rGroup1 = (RadioGroup) findViewById(R.id.gendergroup1);
		rGroup2 = (RadioGroup) findViewById(R.id.gendergroup2);
		rGroup3 = (RadioGroup) findViewById(R.id.gendergroup3);
		rGroup4 = (RadioGroup) findViewById(R.id.gendergroup4);
		rGroup5 = (RadioGroup) findViewById(R.id.gendergroup5);
		mRadio4 = (RadioButton) findViewById(R.id.radioButton4);
		mRadio5 = (RadioButton) findViewById(R.id.radioButton5);
		mRadio6 = (RadioButton) findViewById(R.id.radioButton6);
		mRadio7 = (RadioButton) findViewById(R.id.radioButton7);
		mRadio8 = (RadioButton) findViewById(R.id.radioButton8);
		mRadio9 = (RadioButton) findViewById(R.id.radioButton9);
		mRadio10 = (RadioButton) findViewById(R.id.radioButton10);
		mRadio11 = (RadioButton) findViewById(R.id.radioButton11);
		mRadio12 = (RadioButton) findViewById(R.id.radioButton12);
		mRadio13 = (RadioButton) findViewById(R.id.radioButton13);
		mRadio14 = (RadioButton) findViewById(R.id.radioButton14);
		mRadio15 = (RadioButton) findViewById(R.id.radioButton15);
		mRadio16 = (RadioButton) findViewById(R.id.radioButton16);
		mRadio17 = (RadioButton) findViewById(R.id.radioButton17);

		radioButton1 = (RadioButton) findViewById(rGroup1
				.getCheckedRadioButtonId());
		radioButton2 = (RadioButton) findViewById(rGroup2
				.getCheckedRadioButtonId());
		radioButton3 = (RadioButton) findViewById(rGroup3
				.getCheckedRadioButtonId());
		radioButton4 = (RadioButton) findViewById(rGroup4
				.getCheckedRadioButtonId());
		radioButton5 = (RadioButton) findViewById(rGroup5
				.getCheckedRadioButtonId());

		has_assess1 = (TextView) findViewById(R.id.has_assess1);
		has_assess2 = (TextView) findViewById(R.id.has_assess2);
		has_assess3 = (TextView) findViewById(R.id.has_assess3);

		monitor_bt1 = (Button) findViewById(R.id.monitor_bt1);
		monitor_bt1.setOnClickListener(this);
		assess_creat_time.setOnClickListener(this);
		mRadio16.setOnClickListener(this);
		mRadio17.setOnClickListener(this);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// ǰ7��r�g
		final Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, -7);
		b_time = TimeUtils.getTime(calendar2.getTimeInMillis(),
				TimeUtils.DATE_FORMAT_DATE);

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
			// fragmentCallBack.callbackFun3(bundle_bt2);
			trackFragment.setArguments(bundle_bt2);

			ft.replace(R.id.frame_container, trackFragment, "TrackFragment");
			ft.addToBackStack(null);
			// ft.replace(R.id.frame_container, trackFragment);
			ft.commit();

			break;

		case R.id.monitor_bt1:

			if (alarmInfo != null && !flag) {
				// ƴ��json
				Map<String, String> m1 = new HashMap<String, String>();
				m1.put("orderId", alarmInfo.getOrderNumber());
				m1.put("contractNumber", alarmInfo.getContractNumber());
				m1.put("assessPersonId", Assess2Activity.this
						.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
						.getString("userid", ""));
				m1.put("assessPersonName", Assess2Activity.this
						.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
						.getString("fullName", ""));
				if (searchMobileEva != null) {
					m1.put("evaId", searchMobileEva.getevaId());
				}

				// ����button
				// m1.put("totalEvaAssess", String
				// .valueOf(CheckRadioButton(rGroup1
				// .getCheckedRadioButtonId())));
				m1.put("intimeAssess", String.valueOf(CheckRadioButton(rGroup2
						.getCheckedRadioButtonId())));
				m1.put("intactAssess", String.valueOf(CheckRadioButton(rGroup3
						.getCheckedRadioButtonId())));
				m1.put("serveAttitudeAssess", String
						.valueOf(CheckRadioButton(rGroup4
								.getCheckedRadioButtonId())));
				if (rGroup5.getCheckedRadioButtonId() != -1) {
					m1.put("otherAssess", String
							.valueOf(CheckRadioButton(rGroup5
									.getCheckedRadioButtonId())));
					if (editassess.getText().toString().trim().length() > 60) {
						Toast.makeText(Assess2Activity.this,
								"������������ѳ����޶����� ,�޷��ύ", Toast.LENGTH_SHORT)
								.show();
						return;
					} else if (editassess.getText().toString().trim()
							.equals("")) {
						Toast.makeText(Assess2Activity.this, "������������������Ŀ",
								Toast.LENGTH_SHORT).show();
						return;
					} else {
						m1.put("remark", editassess.getText().toString());
					}
				} else {
					m1.put("otherAssess", "");
					m1.put("remark", "");
				}
				// gson.toJson(m1);
				WebServiceUtils wsutils = WebServiceUtils.getInstance();
				wsutils.setContext(Assess2Activity.this.getApplicationContext());
				// if (searchMobileEva != null) {
				// wsutils.setMethodName("updateMobileEva");
				// } else {
				// wsutils.setMethodName("insertMobileEva");
				// }
				wsutils.setMethodName("customeMobilEvaluate");
				wsutils.setWstag("TrackService");
				// ���ô������
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("sendJson", gson.toJson(m1));
				wsutils.setInfactParams(m2);

				SoapObject result = null;
				try {
					MyDialog = ProgressDialog.show(Assess2Activity.this, "",
							"�ύ��,���Ե� ...", true);

					result = wsutils.visit();

					if (result.getProperty(0).toString().replace("\"", "")
							.equals("true")) {
						// ����ѡ������ArrayAdapter��������
						Toast.makeText(Assess2Activity.this, "�ύ�ɹ���лл����!",
								Toast.LENGTH_SHORT).show();
						flag = true;// �ύ�ɹ�����־λ��Ϊtrue����ֹ�ͻ��ظ��ύ
						// �������
						// alarmInfo = null;
						assess_CustomName.setText("");
						assess_ContractNumber.setText("");
						contractNumber_tv_assess.setText("");
						repertoryName_tv_assess.setText("");
						customName_tv_assess.setText("");
						plannedArrivedDate_tv_assess.setText("");
						Calendar calendar = Calendar.getInstance();
						assess_NcCreateDate.setText(TimeUtils.getTime(
								calendar.getTimeInMillis(),
								TimeUtils.DATE_FORMAT_DATE));
						editassess.setText("");
						MyDialog.hide();
						TrackFragment.rel_assess = 1;
						// getFragmentManager().popBackStack();
						Assess2Activity.this.finish();
					} else {
						Message msg = new Message();
						msg.what = 11;
						handler.sendMessage(msg);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					handler.post(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(Assess2Activity.this, "���ӷ�����ʧ��",
									Toast.LENGTH_LONG).show();
							MyDialog.dismiss();
						}
					});
					e.printStackTrace();
				}
			} else {
				Toast.makeText(Assess2Activity.this, "�ף����Ȳ�ѯ�ö������ٽ�������Ŷ~",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.assess_creat_time:
			new DatePickerDialog(Assess2Activity.this, benDateSetListener,
					mYear, mMonth, mDay).show();
			break;
		case R.id.title_back2:
			Assess2Activity.this.finish();
			break;
		case R.id.radioButton16:
			editassess.setVisibility(View.VISIBLE);
			break;
		case R.id.radioButton17:
			editassess.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}

	private int CheckRadioButton(int pos) {

		if (mRadio15.getId() == pos) {
			return 2;
		} else if (mRadio4.getId() == pos) {
			return 0;
		} else if (mRadio5.getId() == pos) {
			return 1;
		} else if (mRadio6.getId() == pos) {
			return 2;
		} else if (mRadio7.getId() == pos) {
			return 0;
		} else if (mRadio8.getId() == pos) {
			return 1;
		} else if (mRadio9.getId() == pos) {
			return 2;
		} else if (mRadio10.getId() == pos) {
			return 0;
		} else if (mRadio11.getId() == pos) {
			return 1;
		} else if (mRadio12.getId() == pos) {
			return 2;
		} else if (mRadio13.getId() == pos) {
			return 0;
		} else if (mRadio14.getId() == pos) {
			return 1;
		} else if (mRadio16.getId() == pos) {
			return 1;
		} else if (mRadio17.getId() == pos) {
			return 2;
		} else {
			return -1;
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
