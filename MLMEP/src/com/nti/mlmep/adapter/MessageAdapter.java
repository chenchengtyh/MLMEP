package com.nti.mlmep.adapter;

import java.util.LinkedList;
import java.util.List;

import com.nti.mlmep.R;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackMessage;
import com.nti.mlmep.util.TimeUtils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	
	private Context mContext = null;
	private LinkedList<TrackMessage> trackMessage;
	
	private TextView message_track_CustomName;
	private TextView message_track_time;
	private TextView message_track_status;
	

	public MessageAdapter(Context ctx, LinkedList<TrackMessage> trackMessage) {
		// TODO Auto-generated constructor stub
		mContext = ctx;
		this.trackMessage = trackMessage;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trackMessage.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return trackMessage.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.fragment_message_track, parent, false);
		
		TrackMessage info = trackMessage.get(position);
		
		init(view);
		
		message_track_CustomName.setText(info.getcontractNumber());
		message_track_time.setText(TimeUtils.dateToStr(info.getsendTime()).substring(0, 16));
		
		//拼接数据
		String messageType = null;
		String message_all = null;
		if(info.getmessageType().equals("1")){
			messageType = "运输单形成";
		}else if(info.getmessageType().equals("2")){
			messageType = "已启运发货";
		}else{
			messageType = "已到达商业公司";
		}
		message_all = "从 "+info.getrepertoryName()+" 发往 "+info.getcustomName()+"(合同号"+info.getcontractNumber()+") "+messageType;
		message_track_status.setText(message_all);
		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		message_track_CustomName = (TextView) view.findViewById(R.id.message_track_CustomName);
		message_track_time = (TextView) view.findViewById(R.id.message_track_time);
		message_track_status = (TextView) view.findViewById(R.id.message_track_status);
	}

}
