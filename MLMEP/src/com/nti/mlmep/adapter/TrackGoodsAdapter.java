package com.nti.mlmep.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nti.mlmep.R;
import com.nti.mlmep.domain.TrackInfoBean;

public class TrackGoodsAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<TrackInfoBean.OrderDetails> trackinfo;

	/**
	 * @param mainActivity
	 */
	public TrackGoodsAdapter(Context ctx,List<TrackInfoBean.OrderDetails> trackinfo) {
		mContext = ctx;
		this.trackinfo = trackinfo;
	}

	@Override
	public int getCount() {
		return trackinfo.size();
	}

	@Override
	public Object getItem(int position) {
		return trackinfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.activity_track_goods, parent, false);
		TrackInfoBean.OrderDetails info = trackinfo.get(position);
		TextView goodsbrand_tv = (TextView) view
				.findViewById(R.id.goodsbrand_tv);
//		TextView versionCode_tv = (TextView) view
//				.findViewById(R.id.versionCode_tv);
		
//		TextView versionName_tv = (TextView) view
//				.findViewById(R.id.versionName_tv);
		TextView pNumber_tv = (TextView) view
				.findViewById(R.id.pNumber_tv);
		
//		TextView lNumber_tv = (TextView) view
//				.findViewById(R.id.lNumber_tv);
//		TextView taxMoney_tv = (TextView) view
//				.findViewById(R.id.taxMoney_tv);
		

		goodsbrand_tv.setText(info.getProductName());
//		versionCode_tv.setText(info.getVersionCode());
//		versionName_tv.setText(info.getVersionName());
		pNumber_tv.setText(info.getpNumber());
//		lNumber_tv.setText(info.getlNumber());
//		taxMoney_tv.setText(info.getTaxMoney());
		
		return view;
	}

	public void setTracks(List<TrackInfoBean.OrderDetails> trackinfo){
		this.trackinfo = trackinfo;
	}

}
