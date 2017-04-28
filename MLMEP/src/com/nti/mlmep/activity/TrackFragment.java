package com.nti.mlmep.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.adapter.TrackDetailAccessAdapter;
import com.nti.mlmep.adapter.TrackDetailAdapter;
import com.nti.mlmep.adapter.TrackDetailComplainAdapter;
import com.nti.mlmep.component.PullToRefreshBase;
import com.nti.mlmep.component.PullToRefreshListView;
import com.nti.mlmep.component.PullToRefreshBase.OnRefreshListener;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackPosition;
import com.nti.mlmep.util.AppLog;
import com.nti.mlmep.util.Constants;
import com.nti.mlmep.util.SerializableMap;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

import android.R.bool;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TrackFragment extends Fragment implements Return {

	private FragmentManager fm;
	private FragmentTransaction ft;

	private ListView mListView;
	private PullToRefreshListView mPullListView;
	private TrackDetailAdapter trackDetailmAdapter;
	private TrackDetailComplainAdapter trackDetailcomplianmAdapter;
	private TrackDetailAccessAdapter trackDetailAccessmAdapter;

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	private boolean mIsStart = true;// 标记是上拉刷新还是下拉取数据
	private static int pageNum = 1;// 记录页数
	private static final int mLoadDataCount = 10;// 每次加载的数据量
	private Date lastRfreshTime;// 记录上一次刷新的时间
	private Date firstRfreshTime;// 记录系统第一次取数据的时间，方便后续分页取数据
	boolean isFristRefresh = false;// 是否第一次刷新

	private String jsonParam;
	private WebServiceUtils wsutils;
	private WebServiceUtils gisWsutils;

	private LinkedList<TrackInfoBean> mListItems = null;// 缓存所有的数据
	public static List<TrackInfoBean> trackinfoDownbeans;// 缓存下拉的最新数据
	public static List<TrackInfoBean> trackInfoUPBeans;// 缓存上拉分页取的数据

	FragmentCallBack fragmentCallBack = null;
	FrameLayout track_listview_layout;
	private EditText search_track2_edit;
	private Button search_track2_null;
	private Button search_track2;
	SerializableMap serializableMap;
	private boolean flag = false;
	static boolean hasMoreData = true;
	private View view;
	public static int rel = 0;//标记位，当用户评价投诉成功后返回上层自动刷新数据,1刷新,否则不刷新

//	public void setResult(int rel) {
//		this.rel = rel;
//	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message mg) {
			// TODO Auto-generated method stub
			// super.handleMessage(msg);
			switch (mg.what) {
			case 1:
				String msg;
				msg = mListItems.isEmpty() ? "查询不到数据，请检查条件！" : "无更多数据!";
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getActivity().getApplicationContext(),
						"无最新数据,请稍后重试!", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}

		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		Log.d("jochen", "11onAttach=");
		fragmentCallBack = (FragmentMainActivity) activity;

		super.onAttach(activity);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.d("jochen", "11onPause=");
		super.onPause();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.d("jochen", "11onAttach="+hasMoreData);
		//重新唤醒时判断是否需要重新加载数据
		if (rel == 1) {
			init();
			webserviceInit();
			uiInit();// UI初始化
			track_listview_layout.removeAllViews();
			track_listview_layout.addView(mPullListView);
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();
			mPullListView.doPullRefreshing(true, 200);
			rel = 0;
		}
		super.onResume();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		flag = true;
		
		Log.d("jochen", "11onStop="+hasMoreData);
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// Log.d("jochen", "That is onDestroy()");
		// Bundle bundle2 = new Bundle();
		// bundle2.putString("change_title", "change_back_false");
		// fragmentCallBack.callbackFun1(bundle2);
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.track_listview, container, false);
		track_listview_layout = (FrameLayout) view
				.findViewById(R.id.track_listview_layout);
		search_track2_edit = (EditText) view
				.findViewById(R.id.search_track2_edit);
		search_track2 = (Button) view.findViewById(R.id.search_track2);
		search_track2_null = (Button) view
				.findViewById(R.id.search_track2_null);

		// mListView = (ListView) view.findViewById(R.id.track_listview);
		if (mListItems == null) {
			init();// 数据准备
		}
		// 设置传入参数
		// Bundle bundle = new Bundle();
		// SerializableMap serializableMap = (SerializableMap) bundle
		// .get("serializableMap");
		serializableMap = (SerializableMap) getArguments().get(
				"serializableMap");

		webserviceInit();// webservice初始化

		mPullListView = new PullToRefreshListView(getActivity());
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		if (FragmentMainActivity.getTitle2().equals("服务投诉")) {
			trackDetailcomplianmAdapter = new TrackDetailComplainAdapter(
					getActivity(), mListItems, this);
			trackDetailmAdapter = null;
			trackDetailAccessmAdapter = null;
		} else if (FragmentMainActivity.getTitle2().equals("服务评价")) {
			trackDetailAccessmAdapter = new TrackDetailAccessAdapter(
					getActivity(), mListItems, this);
			trackDetailmAdapter = null;
			trackDetailcomplianmAdapter = null;
		} else {

			trackDetailmAdapter = new TrackDetailAdapter(getActivity(),
					mListItems, this);
			trackDetailcomplianmAdapter = null;
			trackDetailAccessmAdapter = null;
		}
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(null);
		// 判断当前属于物流，评价，投诉哪种行为,做适配器适配
		if (trackDetailmAdapter != null && trackDetailcomplianmAdapter == null
				&& trackDetailAccessmAdapter == null) {
			mListView.setAdapter(trackDetailmAdapter);
		} else if (trackDetailAccessmAdapter != null
				&& trackDetailmAdapter == null
				&& trackDetailcomplianmAdapter == null) {
			mListView.setAdapter(trackDetailAccessmAdapter);
		} else {
			mListView.setAdapter(trackDetailcomplianmAdapter);
		}

		// getActivity().setTitle("物流跟踪查询结果");

		uiInit();// UI初始化

		// 清空按钮
		search_track2_null.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				search_track2_edit.setText("");
			}
		});

		// 搜索
		search_track2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String serach_track2 = search_track2_edit.getText().toString();
				Map<String, Object> params = new HashMap<String, Object>();
				params = serializableMap.getMap();
				// 通过中文判断识别输入的是合同号还是发货地
				if (serach_track2.getBytes().length != serach_track2.length()) {
					params.put("contractNumber", "");
					params.put("customName", serach_track2);
				} else {
					params.put("contractNumber", serach_track2);
					params.put("customName", "");
				}
				serializableMap.setMap(params);
				init();
				webserviceInit();
				uiInit();// UI初始化
				track_listview_layout.removeAllViews();
				track_listview_layout.addView(mPullListView);
				mPullListView.setHasMoreData(hasMoreData);
				setLastUpdateTime();
				mPullListView.doPullRefreshing(true, 200);

			}
		});

		Bundle bundle = new Bundle();
		bundle.putString("change_title", "change_back");
		fragmentCallBack.callbackFun1(bundle);
		track_listview_layout.addView(mPullListView);
		return view;
	}

	/**
	 * 数据初始化
	 */
	private void init() {
		pageNum = 1;
		trackinfoDownbeans = new ArrayList<TrackInfoBean>();
		trackInfoUPBeans = new ArrayList<TrackInfoBean>();
		mListItems = new LinkedList<TrackInfoBean>();
		if (mListItems != null && mListItems.size() > 0) {
			mListItems.clear();
		}
		lastRfreshTime = null;
		firstRfreshTime = null;
	}

	/**
	 * webservice初始化
	 */
	private void webserviceInit() {
		// 成品运输 WEBSERVICE
		wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(getActivity().getApplicationContext());
		// wsutils.setMethodName("LogisticsTrackingForPhone");
		wsutils.setMethodName("getInfoForPhone");
		wsutils.setWstag("TrackService");

		if (serializableMap != null && serializableMap.getMap() != null) {
			wsutils.setOriginParams(serializableMap.getMap());
		}

		// GIS WEBSERVICE
		// gisWsutils = WebServiceUtils.getInstance();
		// gisWsutils.setContext(getActivity().getApplicationContext());
		// gisWsutils.setMethodName("getPlaceInfoByCarNumber");
		// gisWsutils.setWstag("LocationService");
		// gisWsutils.setWsType("gis");
	}

	private void uiInit() {
		// mPullListView = new PullToRefreshListView(getActivity());
		// getActivity().setContentView(mPullListView);
		// Log.d("jochen", "flag----------------------1");
		// mPullListView.setPullLoadEnabled(false);
		// mPullListView.setScrollLoadEnabled(true);
		// mAdapter = new TrackDetailAdapter(getActivity(), mListItems);
		// mListView = mPullListView.getRefreshableView();
		// mListView.setDivider(null);
		// mListView.setAdapter(mAdapter);
		// Log.d("jochen", "flag----------------------2");
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TrackInfoBean alarmInfo = mListItems.get(position);
				// updatePosition(alarmInfo);
				// FragmentManager fm = getFragmentManager();
				// ft = fm.beginTransaction();
				// TrackDetailFragment trackDetailFragment = new
				// TrackDetailFragment();
				// Bundle mBundle = new Bundle();
				// mBundle.putString("change_title", "change_title_track");
				// fragmentCallBack.callbackFun1(mBundle);
				// mBundle.putSerializable("AlarmInfo", (Serializable)
				// alarmInfo);
				// trackDetailFragment.setArguments(mBundle);
				// ft.replace(R.id.frame_container, trackDetailFragment);
				// ft.addToBackStack("add TrackDetailFragment");
				// ft.commit();
			}
		});

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 下拉刷新，加载数据
				mIsStart = true;
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 上拉刷新，取新的数据
				mIsStart = false;
				new GetDataTask().execute();
			}
		});
		mPullListView.setHasMoreData(hasMoreData);
		setLastUpdateTime();
		if (!flag) {
			mPullListView.doPullRefreshing(true, 200);
		}
	}

	/**
	 * 更新最新到货地点信息
	 * 
	 * @param alarmInfo
	 */
	private void updatePosition(final TrackInfoBean alarmInfo) {
		// 通过WEBSERVICE获取订单最新的到货地点位置
		if (!StringUtil.isEmpty(alarmInfo.getPlateNumber())
				&& !StringUtil.isEmpty(alarmInfo.getOrderStatus())
				&& "06".equals(alarmInfo.getOrderStatus())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("arg0", alarmInfo.getPlateNumber().trim());
			gisWsutils.setInfactParams(map);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					// /////////////
					try {
						SoapObject result = null;
						// result = gisWsutils.visit();
						Log.d("jochen", "result1=" + result);
						if (result != null) {
							String jsonParam = result.getProperty(0).toString();
							if (!StringUtil.isEmpty(jsonParam)
									&& !"false".equals(jsonParam)) {
								java.lang.reflect.Type type = new TypeToken<TrackPosition>() {
								}.getType();
								Gson gson = new Gson();
								JSONObject temp = new JSONObject(result
										.getProperty(0).toString());
								TrackPosition p = gson.fromJson(
										temp.toString(), type);
								if (p != null
										&& !StringUtil.isEmpty(p
												.getFormatted_address())) {
									alarmInfo.setTrackPosition(p);
								}
							}
						}

					} catch (Exception e) {
						AppLog.error(Constants.LOG_EXCEPTION, e.getMessage(), e);
						e.printStackTrace();
					}
					// /////////////

				}
			}).start();

		}
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected String[] doInBackground(Void... params) {
			try {
				if (mIsStart) {
					downRefreshData();
				} else {
					upRefreshData();
				}
				// Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// 刷新视图数据
			// hasMoreData = true;
			if (mIsStart && !isFristRefresh) {
				if (!trackinfoDownbeans.isEmpty()) {
					for (int i = 0; i < trackinfoDownbeans.size(); i++) {
						TrackInfoBean cache = trackinfoDownbeans.get(i);
						if (!mListItems.contains(cache)) {
							// 把最新的数据加到前面
							mListItems.addFirst(cache);
						}
					}
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"无最新数据,请稍后重试!", Toast.LENGTH_SHORT).show();
				}

			} else {
				// 上拉
				if (!trackInfoUPBeans.isEmpty()) {
					// 分页取数据，如果取到的数据满足rows代表还有数据
					if (trackInfoUPBeans.size() < mLoadDataCount) {
						hasMoreData = false;
					} else {
						hasMoreData = true;
					}

					for (int i = 0; i < trackInfoUPBeans.size(); i++) {
						TrackInfoBean cache = trackInfoUPBeans.get(i);
						if (!mListItems.contains(cache)) {
							// 把下拉的数据加到后面
							mListItems.addLast(cache);
						}
					}
				} else {
					String msg;
					msg = mListItems.isEmpty() ? "查询不到数据，请检查条件！" : "无更多数据!";
					Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT)
							.show();

				}

			}

			if (trackDetailmAdapter != null
					&& trackDetailcomplianmAdapter == null
					&& trackDetailAccessmAdapter == null) {
				trackDetailmAdapter.setTracks(mListItems);
				trackDetailmAdapter.notifyDataSetChanged();
			} else if (trackDetailAccessmAdapter != null
					&& trackDetailmAdapter == null
					&& trackDetailcomplianmAdapter == null) {
				trackDetailAccessmAdapter.setTracks(mListItems);
				trackDetailAccessmAdapter.notifyDataSetChanged();

			} else {
				trackDetailcomplianmAdapter.setTracks(mListItems);
				trackDetailcomplianmAdapter.notifyDataSetChanged();

			}

			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();

			super.onPostExecute(result);
		}
	}

	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		mPullListView.setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));
	}

	/**
	 * 下拉刷新,最新的数据加入进来
	 * 
	 * @throws InterruptedException
	 */
	private void downRefreshData() throws InterruptedException {
		Date refreshStartTime = null;// 开始时间
		Date refreshEndTime = null;// 结束时间
		if (lastRfreshTime == null) {
			// 第一次刷新
			isFristRefresh = true;
			upRefreshData();
			return;
		} else {
			isFristRefresh = false;
		}

		refreshStartTime = lastRfreshTime;
		refreshEndTime = new Date();
		lastRfreshTime = refreshEndTime;

		SoapObject result = null;
		try {
			// 每次刷新清空数据
			if (trackinfoDownbeans == null) {
				trackinfoDownbeans = new ArrayList<TrackInfoBean>();
			} else {
				trackinfoDownbeans.clear();
			}
			wsutils.setInfactParams(null);// 清理动态参数
			Map<String, Object> orginParams = wsutils.getOriginParams();

			Map<String, Object> dynamicParams = new HashMap<String, Object>();
			if (orginParams != null && !orginParams.isEmpty()) {
				dynamicParams.putAll(orginParams);
			}

			if (refreshStartTime.getTime() < refreshEndTime.getTime()) {
				dynamicParams.put("ncCreateTimeStart", TimeUtils.format(
						refreshStartTime, TimeUtils.TIME_FORMAT_STRING));
				dynamicParams.put("ncCreateTimeEnd", refreshEndTime);
			}

			Map<String, Object> pms = new HashMap<String, Object>();
			Gson gson = new Gson();
			pms.put("sendJson", gson.toJson(dynamicParams));
			wsutils.setInfactParams(pms);
			result = wsutils.visit();
			Log.d("jochen", "result3=" + result);
			if (result != null) {
				jsonParam = result.getProperty(0).toString();
				// 差不多数据的情况
				if (!StringUtil.isEmpty(jsonParam)
						&& !"false".equals(jsonParam)) {
					java.lang.reflect.Type type = new TypeToken<TrackInfoBean>() {
					}.getType();
					JSONArray arr = new JSONArray(result.getProperty(0)
							.toString());
					for (int i = 0; i < arr.length(); i++) {
						JSONObject temp = (JSONObject) arr.get(i);
						TrackInfoBean trackInfoBean = gson.fromJson(
								temp.toString(), type);
						trackinfoDownbeans.add(trackInfoBean);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Thread.sleep(400);
	}

	/**
	 * 上拉分页取数据
	 * 
	 * @throws InterruptedException
	 */
	private void upRefreshData() throws InterruptedException {
		if (firstRfreshTime == null) {
			firstRfreshTime = new Date();

			// 第一次取数据,就是第一次刷新的时间
			lastRfreshTime = firstRfreshTime;
		}

		SoapObject result = null;
		try {
			// 每次刷新要清空缓存
			if (trackInfoUPBeans == null) {
				trackInfoUPBeans = new ArrayList<TrackInfoBean>();
			} else {
				trackInfoUPBeans.clear();
			}

			Map<String, Object> orginParams = wsutils.getOriginParams();
			Map<String, Object> dynamicParams = new HashMap<String, Object>();
			if (orginParams != null && !orginParams.isEmpty()) {
				dynamicParams.putAll(orginParams);
			}

			dynamicParams.put("ncCreateTimeEnd", TimeUtils.format(
					firstRfreshTime, TimeUtils.TIME_FORMAT_STRING));

			// 每次取10条数据,分页取数据
			dynamicParams.put("page", pageNum + "");
			dynamicParams.put("rows", "10");
			Map<String, Object> pms = new HashMap<String, Object>();
			Gson gson = new Gson();
			pms.put("sendJson", gson.toJson(dynamicParams));
			wsutils.setInfactParams(pms);
			result = wsutils.visit();
			Log.d("jochen", "result2=" + result);
			if (result != null) {
				jsonParam = result.getProperty(0).toString();
				// 差不多数据的情况
				if (!StringUtil.isEmpty(jsonParam)
						&& !"false".equals(jsonParam)) {
					java.lang.reflect.Type type = new TypeToken<TrackInfoBean>() {
					}.getType();
					JSONArray arr = new JSONArray(result.getProperty(0)
							.toString());
					if (arr.length() > 0 && arr.length() == 10) {
						pageNum++;
					}
					for (int i = 0; i < arr.length(); i++) {
						JSONObject temp = (JSONObject) arr.get(i);
						TrackInfoBean trackInfoBean = gson.fromJson(
								temp.toString(), type);
						trackInfoUPBeans.add(trackInfoBean);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Thread.sleep(400);
	}

	// listview中button监听响应事件(接口回调)
	// 物流查询
	@Override
	public void ReturntoTrack_check(int pos) {
		// TODO Auto-generated method stub
		TrackInfoBean alarmInfo = mListItems.get(pos);
		// updatePosition(alarmInfo);
		FragmentManager fm = getFragmentManager();
		ft = fm.beginTransaction();
		TrackDetailFragment trackDetailFragment = new TrackDetailFragment();
		Bundle mBundle = new Bundle();
		mBundle.putString("change_title", "change_title_track");
		fragmentCallBack.callbackFun1(mBundle);
		mBundle.putSerializable("AlarmInfo", (Serializable) alarmInfo);
		trackDetailFragment.setArguments(mBundle);
		ft.replace(R.id.frame_container, trackDetailFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	// 物流评价，参数为所有缓存数据
	@Override
	public void ReturntoTrack_assess(int pos) {
		// TODO Auto-generated method stub
		TrackInfoBean alarmInfo = mListItems.get(pos);
		Log.d("jochen",
				"alarmInfo: " + alarmInfo.getOrderNumber() + "--"
						+ alarmInfo.getContractNumber() + "--"
						+ alarmInfo.getPlateNumber() + "--"
						+ alarmInfo.getTransportCode() + "--"
						+ alarmInfo.getCustomName() + "--"
						+ alarmInfo.getPlannedArrivedDate() + "--"
						+ alarmInfo.getRepertoryName() + "--"
						+ alarmInfo.getDriverName() + "--" + alarmInfo.getTel()
						+ "--" + alarmInfo.getMobileComplaint().size());

//		fragmentCallBack = (FragmentMainActivity) getActivity();
//		Bundle bundle2 = new Bundle();
//		bundle2.putString("tab_flag_page", "pingjia");
//		bundle2.putSerializable("map", alarmInfo);
//		fragmentCallBack.callbackFun3(bundle2);
		
		Bundle bundle2 = new Bundle();
		bundle2.putSerializable("map", alarmInfo);
		Intent it = new Intent(getActivity(),Assess2Activity.class);
		it.putExtras(bundle2);
		startActivity(it);

		// FragmentManager fm = getFragmentManager();
		// ft = fm.beginTransaction();
		// AssessFragment tab1 = new AssessFragment();
		// Bundle bundle = new Bundle();
		// // bundle.putString("flag_page", "评价");
		// bundle.putSerializable("map", alarmInfo);
		// tab1.setArguments(bundle);
		// ft.replace(R.id.frame_container, tab1);
		// ft.addToBackStack(null);
		// ft.commit();

	}

	// 物流投诉，参数为所有缓存数据
	@Override
	public void ReturntoTrack_compalin(int pos) {
		// TODO Auto-generated method stub
		TrackInfoBean alarmInfo = mListItems.get(pos);
		Log.d("jochen",
				"alarmInfo: " + alarmInfo.getOrderNumber() + "--"
						+ alarmInfo.getContractNumber() + "--"
						+ alarmInfo.getPlateNumber() + "--"
						+ alarmInfo.getTransportCode() + "--"
						+ alarmInfo.getCustomName() + "--"
						+ alarmInfo.getPlannedArrivedDate() + "--"
						+ alarmInfo.getRepertoryName() + "--"
						+ alarmInfo.getDriverName() + "--" + alarmInfo.getTel());

//		fragmentCallBack = (FragmentMainActivity) getActivity();
//		Bundle bundle2 = new Bundle();
//		bundle2.putString("tab_flag_page", "tousu");
//		bundle2.putSerializable("map", alarmInfo);
//		fragmentCallBack.callbackFun4(bundle2);
		
		Bundle bundle2 = new Bundle();
		bundle2.putSerializable("map", alarmInfo);
		Intent it = new Intent(getActivity(),ComplainActivity.class);
		it.putExtras(bundle2);
		startActivity(it);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("jochen", "onActivityResult=" + resultCode + "--" + requestCode);

	}

}
