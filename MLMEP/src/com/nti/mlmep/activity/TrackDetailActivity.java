package com.nti.mlmep.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nti.mlmep.R;
import com.nti.mlmep.activity.base.BaseBackActivity;
import com.nti.mlmep.adapter.TrackGoodsAdapter;
import com.nti.mlmep.adapter.TrackTraceAdapter;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.util.TimeUtils;

/**
 * @author sunsi
 */
public class TrackDetailActivity extends BaseBackActivity {

	/**
	 * 
	 */

	private TrackInfoBean trackinfo;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Intent intent = this.getIntent();
		// trackinfo=(TrackInfoBean)intent.getSerializableExtra("AlarmInfo");

		init();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_track_detail);

	}

	@Override
	protected String getTitleName() {
		return getString(R.string.title_name_track_detail);
	}

	private void init() {
		Intent intent = this.getIntent();
		trackinfo = (TrackInfoBean) intent.getSerializableExtra("AlarmInfo");
		tv_orderNumber = (TextView) findViewById(R.id.orderNumber_tv);
		tv_contractNumber = (TextView) findViewById(R.id.contractNumber_tv);
		tv_plateNumber = (TextView) findViewById(R.id.plateNumber_tv);
		// tv_transportCode = (TextView) findViewById(R.id.transportCode_tv);
		tv_customName = (TextView) findViewById(R.id.customName_tv);
		tv_repertoryName = (TextView) findViewById(R.id.repertoryName_tv);
		tv_plannedArrivedDate = (TextView) findViewById(R.id.plannedArrivedDate_tv);
		tv_driverName = (TextView) findViewById(R.id.driverName_tv);
		tv_tel = (TextView) findViewById(R.id.tel_tv);

		if (trackinfo != null) {

			tv_orderNumber.setText(trackinfo.getOrderNumber());
			tv_contractNumber.setText(trackinfo.getContractNumber());
			tv_plateNumber.setText(trackinfo.getPlateNumber());
			// tv_transportCode.setText(trackinfo.getTransportCode());
			tv_customName.setText(trackinfo.getCustomName());
			tv_plannedArrivedDate.setText(TimeUtils.dateToStr(trackinfo
					.getPlannedArrivedDate()));
			tv_repertoryName.setText(trackinfo.getRepertoryName());
			tv_driverName.setText(trackinfo.getDriverName());
			tv_tel.setText(trackinfo.getTel());

		}
		
		ScrollView sv = (ScrollView)findViewById(R.id.tracksv);
        sv.scrollTo(0, 0);

		// ∆Ù∂ØŒÔ¡˜∫€º£  ≈‰∆˜
		ListView lv = (ListView) findViewById(R.id.tracelv);
		TrackTraceAdapter mAdapter = new TrackTraceAdapter(this,
				trackinfo.getOrderStatuss(),this.trackinfo.getTrackPosition());
		lv.setAdapter(mAdapter);
		setListViewHeightBasedOnChildren(lv);

		// ∆Ù∂Ø…Ã∆∑√˜œ∏  ≈‰∆˜
		ListView gv = (ListView) findViewById(R.id.goodslv);
		TrackGoodsAdapter gAdapter = new TrackGoodsAdapter(this,
				trackinfo.getOrderDetails());
		gv.setAdapter(gAdapter);
		setListViewHeightBasedOnChildren(gv);

	}

	class MAdapter extends BaseAdapter {
		Context mContext;
		LayoutInflater mInflater;

		public MAdapter(Context c) {
			mContext = c;
			mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return 12;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup arg2) {
			// ViewHolder holder;
			// if (contentView == null) {
			// holder = new ViewHolder();
			// contentView = mInflater.inflate(R.layout.alarm_detail_gv_item,
			// null);
			// holder.mImg = (ImageView) contentView
			// .findViewById(R.id.imgv_video);
			// holder.mTxt = (TextView) contentView
			// .findViewById(R.id.alarm_detail_item_tv);
			// } else {
			// holder = (ViewHolder) contentView.getTag();
			// }
			// holder.mTxt.setText("ÂΩïÂÉè" + position);
			// contentView.setTag(holder);
			return contentView;
		}

	}

	static class ViewHolder {
		ImageView mImg;
		TextView mTxt;
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub

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