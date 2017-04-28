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

	private boolean mIsStart = true;// ���������ˢ�»�������ȡ����
	private static int pageNum = 1;// ��¼ҳ��
	private static final int mLoadDataCount = 10;// ÿ�μ��ص�������
	private Date lastRfreshTime;// ��¼��һ��ˢ�µ�ʱ��
	private Date firstRfreshTime;// ��¼ϵͳ��һ��ȡ���ݵ�ʱ�䣬���������ҳȡ����
	boolean isFristRefresh = false;//�Ƿ��һ��ˢ��

	private String jsonParam;
	private WebServiceUtils wsutils;
	private WebServiceUtils gisWsutils;
	
	private LinkedList<TrackInfoBean> mListItems;//�������е�����
	public static List<TrackInfoBean> trackinfoDownbeans;// ������������������
	public static List<TrackInfoBean> trackInfoUPBeans;// ����������ҳȡ������

	private FragmentManager fm;
	private FragmentTransaction ft;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.setAllowFullScreen(false); // �Ƿ���ʾ����
		super.onCreate(savedInstanceState);
		this.setTitle("�������ٲ�ѯ���");
		init();// ����׼��
		webserviceInit();// webservice��ʼ��
		uiInit();// UI��ʼ��
	}

	/**
	 * webservice��ʼ��
	 */
	private void webserviceInit() {
		// ��Ʒ���� WEBSERVICE
		wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(TrackActivity.this.getApplicationContext());
		wsutils.setMethodName("LogisticsTrackingForPhone");
		wsutils.setWstag("TrackService");

		// ���ô������
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
	 * ���ݳ�ʼ��
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
				// ����ˢ�£���������
				mIsStart = true;
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// ����ˢ�£�ȡ�µ�����
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
			// ˢ����ͼ����
			boolean hasMoreData = true;
			if (mIsStart && !isFristRefresh) {
				if(!trackinfoDownbeans.isEmpty()){
					for (int i = 0; i < trackinfoDownbeans.size(); i++) {
						TrackInfoBean cache = trackinfoDownbeans.get(i);
						if(!mListItems.contains(cache)){
							//�����µ����ݼӵ�ǰ��
							mListItems.addFirst(cache);
						}
					}
				}else{
					Toast.makeText(getApplicationContext(), "����������,���Ժ�����!",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				// ����
				if(!trackInfoUPBeans.isEmpty()){
					//��ҳȡ���ݣ����ȡ������������rows����������
					if(trackInfoUPBeans.size() < mLoadDataCount){
						hasMoreData = false;
					}else{
						hasMoreData = true;
					}
					
					for (int i = 0; i < trackInfoUPBeans.size(); i++) {
						TrackInfoBean cache = trackInfoUPBeans.get(i);
						if(!mListItems.contains(cache)){
							//�����������ݼӵ�����
							mListItems.addLast(cache);
						}
					}
				}else{
					String msg = mListItems.isEmpty()?"��ѯ�������ݣ�����������":"�޸�������!";
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
	 * �������µ����ص���Ϣ
	 * 
	 * @param alarmInfo
	 */
	private void updatePosition(TrackInfoBean alarmInfo) {
		// ͨ��WEBSERVICE��ȡ�������µĵ����ص�λ��
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
	 * ����ˢ��,���µ����ݼ������
	 * 
	 * @throws InterruptedException
	 */
	private void downRefreshData() throws InterruptedException {
		Date refreshStartTime = null;// ��ʼʱ��
		Date refreshEndTime = null;// ����ʱ��
		if (lastRfreshTime == null) {
			// ��һ��ˢ��
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
			//ÿ��ˢ���������
			if (trackinfoDownbeans == null) {
				trackinfoDownbeans = new ArrayList<TrackInfoBean>();
			}else{
				trackinfoDownbeans.clear();
			}
			wsutils.setInfactParams(null);// ����̬����
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
				// ������ݵ����
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
	 * ������ҳȡ����
	 * 
	 * @throws InterruptedException
	 */
	private void upRefreshData() throws InterruptedException {
		if (firstRfreshTime == null) {
			firstRfreshTime = new Date();

			// ��һ��ȡ����,���ǵ�һ��ˢ�µ�ʱ��
			lastRfreshTime = firstRfreshTime;
		}

		SoapObject result = null;
		try {
			//ÿ��ˢ��Ҫ��ջ���
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

			// ÿ��ȡ10������,��ҳȡ����
			dynamicParams.put("page", pageNum+"");
			dynamicParams.put("rows", "10");
			Map<String, Object> pms = new HashMap<String, Object>();
			Gson gson = new Gson();
			pms.put("sendJson", gson.toJson(dynamicParams));
			wsutils.setInfactParams(pms);
			result = wsutils.visit();
			if (result != null) {
				jsonParam = result.getProperty(0).toString();
				// ������ݵ����
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
