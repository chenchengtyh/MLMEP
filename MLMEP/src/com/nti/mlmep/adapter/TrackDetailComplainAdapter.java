package com.nti.mlmep.adapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.activity.FragmentCallBack;
import com.nti.mlmep.activity.Return;
import com.nti.mlmep.activity.TrackDetailFragment;
import com.nti.mlmep.activity.TrackFragment;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackPosition;
import com.nti.mlmep.util.AppLog;
import com.nti.mlmep.util.Constants;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;

@SuppressLint("ViewHolder") public class TrackDetailComplainAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<TrackInfoBean> trackinfo;

	Return mCallBack;

	/**
	 * @param mainActivity
	 */
	public TrackDetailComplainAdapter(Context ctx, List<TrackInfoBean> trackinfo,
			Fragment trackFragment) {
		mContext = ctx;
		this.trackinfo = trackinfo;
		mCallBack = (Return) trackFragment;
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
				R.layout.activity_track_compalin, parent, false);
		TrackInfoBean info = trackinfo.get(position);
		TextView complain_flag = (TextView) view.findViewById(R.id.complain_flag);
        if(info.getMobileComplaint().size() != 0 && info.getMobileComplaint().get(0).getIsAccept().equals("1") && info.getcomplaint_flag().equals("1")){
        	complain_flag.setText("已受理");
        	complain_flag.setTextColor(Color.RED);
        }
        
        if(info.getMobileComplaint().size() != 0 && info.getMobileComplaint().get(0).getIsAccept().equals("10") && info.getcomplaint_flag().equals("1")){
        	complain_flag.setText("受理中");
        	complain_flag.setTextColor(Color.RED);
        }
        
        if(info.getMobileComplaint().size() != 0 && info.getMobileComplaint().get(0).getIsAccept().equals("0") && info.getcomplaint_flag().equals("1")){
        	complain_flag.setText("已投诉");
        	complain_flag.setTextColor(Color.RED);
        }
		// 订单号
		TextView orderNumberTv = (TextView) view
				.findViewById(R.id.orderNumber_tv);
		// 合同号
		TextView contractNumberTv = (TextView) view
				.findViewById(R.id.contractNumber_tv);
		// 车牌号
		TextView plateNumberTv = (TextView) view
				.findViewById(R.id.plateNumber_tv);
		// 发货方
		TextView repertoryNameTv = (TextView) view
				.findViewById(R.id.repertoryName_tv);
		// 收货方
		TextView customNameTv = (TextView) view
				.findViewById(R.id.customName_tv);
		// 制单日期
		TextView plannedArrivedDateTv = (TextView) view
				.findViewById(R.id.plannedArrivedDate_tv);

		// 查询物流
		Button track_check = (Button) view.findViewById(R.id.track_check);
		track_check.setTag(position);
		track_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("jochen", "单击我了:" + v.getTag());
				mCallBack.ReturntoTrack_check((Integer) v.getTag());
			}
		});

		// 评价
		Button track_compalin = (Button) view.findViewById(R.id.track_compalin);
		track_compalin.setTag(position);
		track_compalin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("jochen", "单击我了:" + v.getTag());
				mCallBack.ReturntoTrack_compalin((Integer) v.getTag());
			}
		});

		orderNumberTv.setText(info.getOrderNumber());
		contractNumberTv.setText(info.getContractNumber());
		plateNumberTv.setText(info.getPlateNumber());
		repertoryNameTv.setText(info.getRepertoryName());
		customNameTv.setText(info.getCustomName());
		plannedArrivedDateTv
				.setText(TimeUtils.dateToStr(info.getNcCreateDate()));
		Log.d("jochen", "date = " + info.getNcCreateDate());
		return view;
	}

	public void setTracks(List<TrackInfoBean> trackinfo){
		this.trackinfo = trackinfo;
	}

}

