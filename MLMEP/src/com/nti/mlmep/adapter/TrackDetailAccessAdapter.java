package com.nti.mlmep.adapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

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

public class TrackDetailAccessAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<TrackInfoBean> trackinfo;

	Return mCallBack;

	/**
	 * @param mainActivity
	 */
	public TrackDetailAccessAdapter(Context ctx, List<TrackInfoBean> trackinfo,
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
				R.layout.activity_track_access, parent, false);
		TrackInfoBean info = trackinfo.get(position);

		TextView access_flag = (TextView) view.findViewById(R.id.access_flag);
		// if (info.geteva_flag().equals("1") &&
		// (info.getMobileEva().get(0).getevaState().equals("2"))
		// && (!info.getMobileEva().get(0).getintimeAssess().equals(""))
		// && (!info.getMobileEva().get(0).getintactAssess().equals(""))
		// && (!info.getMobileEva().get(0).getserveAttitudeAssess()
		// .equals(""))) {
		if ((null != info.getMobileEva()) && (info.getMobileEva().size() != 0)) {
			if (info.getMobileEva().get(0).getevaState().equals("2")) {
				access_flag.setText("������");
				access_flag.setTextColor(Color.RED);
			}
		}
		// ������
		TextView orderNumberTv = (TextView) view
				.findViewById(R.id.orderNumber_tv);
		// ��ͬ��
		TextView contractNumberTv = (TextView) view
				.findViewById(R.id.contractNumber_tv);
		// ���ƺ�
		TextView plateNumberTv = (TextView) view
				.findViewById(R.id.plateNumber_tv);
		// ������
		TextView repertoryNameTv = (TextView) view
				.findViewById(R.id.repertoryName_tv);
		// �ջ���
		TextView customNameTv = (TextView) view
				.findViewById(R.id.customName_tv);
		// �Ƶ�����
		TextView plannedArrivedDateTv = (TextView) view
				.findViewById(R.id.plannedArrivedDate_tv);

		// ��ѯ����
		Button track_check = (Button) view.findViewById(R.id.track_check);
		track_check.setTag(position);
		track_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("jochen", "��������:" + v.getTag());
				mCallBack.ReturntoTrack_check((Integer) v.getTag());
			}
		});

		// ����
		Button track_assess = (Button) view.findViewById(R.id.track_assess);
		track_assess.setTag(position);
		track_assess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("jochen", "��������:" + v.getTag());
				mCallBack.ReturntoTrack_assess((Integer) v.getTag());
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

	public void setTracks(List<TrackInfoBean> trackinfo) {
		this.trackinfo = trackinfo;
	}

}
