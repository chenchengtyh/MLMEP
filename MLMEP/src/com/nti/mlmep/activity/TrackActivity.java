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

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.activity.base.BaseActivity;
import com.nti.mlmep.adapter.TrackDetailAdapter;
import com.nti.mlmep.component.PullToRefreshBase;
import com.nti.mlmep.component.PullToRefreshBase.OnRefreshListener;
import com.nti.mlmep.component.PullToRefreshListView;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackPosition;
import com.nti.mlmep.util.AppLog;
import com.nti.mlmep.util.Constants;
import com.nti.mlmep.util.SerializableMap;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;
import com.nti.mlmep.util.WebServiceUtils;

@SuppressLint("SimpleDateFormat")
public class TrackActivity extends BaseActivity {
	private ListView mListView;
	private PullToRefreshListView mPullListView;
	private TrackDetailAdapter mAdapter;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	private boolean mIsStart = true;// 标记是上拉刷新还是下拉取数据
	private static int pageNum = 1;// 记录页数
	private static final int mLoadDataCount = 10;// 每次加载的数据量
	private Date lastRfreshTime;// 记录上一次刷新的时间
	private Date firstRfreshTime;// 记录系统第一次取数据的时间，方便后续分页取数据
	boolean isFristRefresh = false;//是否第一次刷新

	private String jsonParam;
	private WebServiceUtils wsutils;
	private WebServiceUtils gisWsutils;
	
	private LinkedList<TrackInfoBean> mListItems;//缓存所有的数据
	public static List<TrackInfoBean> trackinfoDownbeans;// 缓存下拉的最新数据
	public static List<TrackInfoBean> trackInfoUPBeans;// 缓存上拉分页取的数据

	private FragmentManager fm;
	private FragmentTransaction ft;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.setAllowFullScreen(false); // 是否显示标题
		super.onCreate(savedInstanceState);
		this.setTitle("物流跟踪查询结果");
		init();// 数据准备
		webserviceInit();// webservice初始化
		uiInit();// UI初始化
	}

	/**
	 * webservice初始化
	 */
	private void webserviceInit() {
		// 成品运输 WEBSERVICE
		wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(TrackActivity.this.getApplicationContext());
		wsutils.setMethodName("LogisticsTrackingForPhone");
		wsutils.setWstag("TrackService");

		// 设置传入参数
		Bundle bundle = getIntent().getExtras();
		SerializableMap serializableMap = (SerializableMap) bundle
				.get("serializableMap");
		if (serializableMap != null && serializableMap.getMap() != null) {
			wsutils.setOriginParams(serializableMap.getMap());
		}

		// GIS WEBSERVICE
		gisWsutils = WebServiceUtils.getInstance();
		gisWsutils.setContext(TrackActivity.this.getApplicationContext());
		gisWsutils.setMethodName("getPlaceInfoByCarNumber");
		gisWsutils.setWstag("LocationService");
		gisWsutils.setWsType("gis");
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

	private void uiInit() {
		mPullListView = new PullToRefreshListView(this);
		setContentView(mPullListView);
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		//mAdapter = new TrackDetailAdapter(this, mListItems);
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(null);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				TrackInfoBean alarmInfo = mListItems.get(position);
				updatePosition(alarmInfo);
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						TrackDetailActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("AlarmInfo", (Serializable) alarmInfo);
				intent.putExtras(mBundle);
				startActivity(intent);
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
		setLastUpdateTime();
		mPullListView.doPullRefreshing(true, 500);
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
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// 刷新视图数据
			boolean hasMoreData = true;
			if (mIsStart && !isFristRefresh) {
				if(!trackinfoDownbeans.isEmpty()){
					for (int i = 0; i < trackinfoDownbeans.size(); i++) {
						TrackInfoBean cache = trackinfoDownbeans.get(i);
						if(!mListItems.contains(cache)){
							//把最新的数据加到前面
							mListItems.addFirst(cache);
						}
					}
				}else{
					Toast.makeText(getApplicationContext(), "无最新数据,请稍后重试!",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				// 上拉
				if(!trackInfoUPBeans.isEmpty()){
					//分页取数据，如果取到的数据满足rows代表还有数据
					if(trackInfoUPBeans.size() < mLoadDataCount){
						hasMoreData = false;
					}else{
						hasMoreData = true;
					}
					
					for (int i = 0; i < trackInfoUPBeans.size(); i++) {
						TrackInfoBean cache = trackInfoUPBeans.get(i);
						if(!mListItems.contains(cache)){
							//把下拉的数据加到后面
							mListItems.addLast(cache);
						}
					}
				}else{
					String msg = mListItems.isEmpty()?"查询不到数据，请检查条件！":"无更多数据!";
					Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
				}
				
			}

			mAdapter.setTracks(mListItems);
			mAdapter.notifyDataSetChanged();
			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();

			super.onPostExecute(result);
		}
	}

	/**
	 * 更新最新到货地点信息
	 * 
	 * @param alarmInfo
	 */
	private void updatePosition(TrackInfoBean alarmInfo) {
		// 通过WEBSERVICE获取订单最新的到货地点位置
		if (!StringUtil.isEmpty(alarmInfo.getPlateNumber())
				&& !StringUtil.isEmpty(alarmInfo.getOrderStatus())
				&& "06".equals(alarmInfo.getOrderStatus())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("arg0", alarmInfo.getPlateNumber().trim());
			gisWsutils.setInfactParams(map);
			try {
				SoapObject result = gisWsutils.visit();
				if (result != null) {
					String jsonParam = result.getProperty(0).toString();
					if (!StringUtil.isEmpty(jsonParam)
							&& !"false".equals(jsonParam)) {
						java.lang.reflect.Type type = new TypeToken<TrackPosition>() {
						}.getType();
						Gson gson = new Gson();
						JSONObject temp = new JSONObject(result.getProperty(0)
								.toString());
						TrackPosition p = gson.fromJson(temp.toString(), type);
						if (p != null
								&& !StringUtil
										.isEmpty(p.getFormatted_address())) {
							alarmInfo.setTrackPosition(p);
						}
					}
				}

			} catch (Exception e) {
				AppLog.error(Constants.LOG_EXCEPTION, e.getMessage(), e);
				e.printStackTrace();
			}
		}
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
		}else{
			isFristRefresh = false;
		}

		refreshStartTime = lastRfreshTime;
		refreshEndTime = new Date();
		lastRfreshTime = refreshEndTime;

		SoapObject result = null;
		try {
			//每次刷新清空数据
			if (trackinfoDownbeans == null) {
				trackinfoDownbeans = new ArrayList<TrackInfoBean>();
			}else{
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
		Thread.sleep(400);
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
			//每次刷新要清空缓存
			if (trackInfoUPBeans == null) {
				trackInfoUPBeans = new ArrayList<TrackInfoBean>();
			}else{
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
			dynamicParams.put("page", pageNum+"");
			dynamicParams.put("rows", "10");
			Map<String, Object> pms = new HashMap<String, Object>();
			Gson gson = new Gson();
			pms.put("sendJson", gson.toJson(dynamicParams));
			wsutils.setInfactParams(pms);
			result = wsutils.visit();
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
		Thread.sleep(400);
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

	@Override
	public void initWidget() {

	}

	@Override
	public void widgetClick(View v) {

	}
}
