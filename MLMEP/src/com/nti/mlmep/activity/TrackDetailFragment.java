package com.nti.mlmep.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.adapter.TrackGoodsAdapter;
import com.nti.mlmep.adapter.TrackTraceAdapter;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackMessage;
import com.nti.mlmep.domain.TrackInfoBean.OrderStatuss;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TrackDetailFragment extends Fragment {

	private TrackInfoBean trackinfo;

	private TextView detail_status;
	private TextView contractNumber_tv_first;
	private TextView tv_orderNumber;
	private TextView tv_contractNumber;
	private TextView tv_plateNumber;
	private TextView tv_transportCode;
	private TextView tv_transportUnit;
	private TextView tv_orderStatus;
	private TextView tv_orderType;
	private TextView tv_customName;
	private TextView tv_repertoryName;
	private TextView tv_plannedArrivedDate;
	private TextView tv_actualArrivedDate;
	private TextView tv_driverName;
	private TextView tv_tel;
	private ImageButton tel;
	private ImageButton tel_assess;
	private ImageButton trackdetail_complain;
	private String tel_number;
	FragmentCallBack fragmentCallBack = null;
	public Gson gson = new Gson();
	private String jsonParam;
	TrackInfoBean trackInfoBeans;
	View view;
	private ProgressDialog dialog;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				init(view);
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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		trackinfo = (TrackInfoBean) bundle.getSerializable("AlarmInfo");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_track_detail, container,
				false);

		if (trackInfoBeans != null) {
			init(view);
		} else {

			dialog = ProgressDialog.show(getActivity(), null, "数据正在加载，请稍候...",
					true, false);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					// 拼接joson
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("orderNumber", trackinfo.getOrderNumber());
					params.put("contractNumber", trackinfo.getContractNumber());
					WebServiceUtils wsutils = WebServiceUtils.getInstance();
					wsutils.setContext(getActivity().getApplicationContext());
					wsutils.setMethodName("LogisticsTrackingForPhone");
					wsutils.setWstag("TrackService");

					// 设置传入参数
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("sendJson", gson.toJson(params));
					wsutils.setInfactParams(m2);
					SoapObject result = null;
					// 调用webservice方法
					try {
						result = wsutils.visit();
						if (result != null) {
							jsonParam = result.getProperty(0).toString();
							if (!StringUtil.isEmpty(jsonParam)
									&& !"false".equals(jsonParam)) {
								java.lang.reflect.Type type = new TypeToken<TrackInfoBean>() {
								}.getType();
								JSONArray arr = new JSONArray(result
										.getProperty(0).toString());
								for (int i = 0; i < arr.length(); i++) {
									JSONObject temp = (JSONObject) arr.get(i);
									// 将json数据转换成TrackMessage对象
									trackInfoBeans = gson.fromJson(
											temp.toString(), type);
								}
							} else {
								Toast.makeText(getActivity(), "查询不到数据，请检查条件！",
										Toast.LENGTH_LONG).show();
							}
						}
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						handler.post(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(getActivity(), "连接服务器失败",
										Toast.LENGTH_LONG).show();

							}
						});
						e.printStackTrace();
					}
				}
			}).start();
		}
		return view;
	}

	private void init(View view) {
		// Intent intent = this.getIntent();
		// trackinfo = (TrackInfoBean) intent.getSerializableExtra("AlarmInfo");
		// detail_status = (TextView) view.findViewById(R.id.detail_status);
		// contractNumber_tv_first = (TextView) view
		// .findViewById(R.id.contractNumber_tv_first);
		tv_orderNumber = (TextView) view.findViewById(R.id.orderNumber_tv);
		tv_contractNumber = (TextView) view
				.findViewById(R.id.contractNumber_tv);
		tv_plateNumber = (TextView) view.findViewById(R.id.plateNumber_tv);
		// tv_transportCode = (TextView) findViewById(R.id.transportCode_tv);
		// tv_transportUnit = (TextView)
		// view.findViewById(R.id.tv_transportUnit);
		tv_customName = (TextView) view.findViewById(R.id.customName_tv);
		tv_repertoryName = (TextView) view.findViewById(R.id.repertoryName_tv);
		tv_plannedArrivedDate = (TextView) view
				.findViewById(R.id.plannedArrivedDate_tv);
		tv_driverName = (TextView) view.findViewById(R.id.driverName_tv);
		tv_tel = (TextView) view.findViewById(R.id.tel_tv);
		trackdetail_complain = (ImageButton) view
				.findViewById(R.id.trackdetail_complain);
		trackdetail_complain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TrackFragment.rel == 1) {
					Toast.makeText(getActivity(), "您刚才已投诉过此订单",
							Toast.LENGTH_SHORT).show();
				} else {
//					fragmentCallBack = (FragmentMainActivity) getActivity();
					Bundle bundle2 = new Bundle();
//					// bundle2.putString("tab_flag_page", "pingjia");
//
					bundle2.putSerializable("map", trackinfo);
//					fragmentCallBack.callbackFun4(bundle2);
					Intent it = new Intent(getActivity(),ComplainActivity.class);
					it.putExtras(bundle2);
					startActivity(it);
				}
			}
		});
		tel = (ImageButton) view.findViewById(R.id.tel);
		tel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity())
						.setTitle("安徽中烟物流管控平台")
						.setIcon(R.drawable.ic_launcher)
						.setCancelable(false)
						.setMessage("确定拨打" + trackinfo.getTel() + "电话吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										// TODO Auto-generated method stub
										Intent in2 = new Intent();
										in2.setAction(Intent.ACTION_CALL);// 指定意图动作
										in2.setData(Uri.parse("tel:"
												+ trackinfo.getTel()));// 指定电话号码
										startActivity(in2);
									}

								}).setNegativeButton("取消", null);
				builder.show();

			}
		});
		tel_assess = (ImageButton) view.findViewById(R.id.tel_assess);
		tel_assess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TrackFragment.rel == 1) {
					Toast.makeText(getActivity(), "您刚才已评价过此订单",
							Toast.LENGTH_SHORT).show();
				} else {
//					fragmentCallBack = (FragmentMainActivity) getActivity();
					Bundle bundle2 = new Bundle();
					bundle2.putSerializable("map", trackinfo);
//					Log.d("jochen", "trackinfo=" + trackinfo.getOrderNumber());
//					fragmentCallBack.callbackFun3(bundle2);
					Intent it = new Intent(getActivity(),Assess2Activity.class);
					it.putExtras(bundle2);
					startActivity(it);
				}
			}
		});

		if (trackInfoBeans != null) {

			// TrackInfoBean.OrderStatuss info =
			// trackinfo.getOrderStatuss().get(
			// trackinfo.getOrderStatuss().size() - 1);
			// detail_status.setText(info.getOrderStatusText());
			// contractNumber_tv_first.setText(trackinfo.getContractNumber());
			tv_orderNumber.setText(trackInfoBeans.getOrderNumber());
			tv_contractNumber.setText(trackInfoBeans.getContractNumber());
			tv_plateNumber.setText(trackInfoBeans.getPlateNumber());
			// tv_transportCode.setText(trackinfo.getTransportCode());
			// tv_transportUnit.setText(trackinfo.getTransportUnit());
			tv_customName.setText(trackInfoBeans.getCustomName());
			tv_plannedArrivedDate.setText(TimeUtils.dateToStr(trackInfoBeans
					.getNcCreateDate()));
			tv_repertoryName.setText(trackInfoBeans.getRepertoryName());
			tv_driverName.setText(trackInfoBeans.getDriverName());
			tv_tel.setText(trackInfoBeans.getTel());

		}

		ScrollView sv = (ScrollView) view.findViewById(R.id.tracksv);
		sv.scrollTo(0, 0);

		// 启动物流痕迹适配器
		ListView lv = (ListView) view.findViewById(R.id.tracelv);
		TrackTraceAdapter mAdapter = new TrackTraceAdapter(getActivity(),
				trackInfoBeans.getOrderStatuss(),
				this.trackInfoBeans.getTrackPosition());
		lv.setAdapter(mAdapter);
		setListViewHeightBasedOnChildren(lv);

		// 启动商品明细适配器
		ListView gv = (ListView) view.findViewById(R.id.goodslv);
		TrackGoodsAdapter gAdapter = new TrackGoodsAdapter(getActivity(),
				trackInfoBeans.getOrderDetails());
		gv.setAdapter(gAdapter);
		setListViewHeightBasedOnChildren(gv);

	}

	private void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
