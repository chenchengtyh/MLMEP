package com.nti.mlmep.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nti.mlmep.R;
import com.nti.mlmep.R.color;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.domain.TrackPosition;
import com.nti.mlmep.util.StringUtil;
import com.nti.mlmep.util.TimeUtils;

public class TrackTraceAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<TrackInfoBean.OrderStatuss> trackinfo;
	private TrackPosition trackPosition;
	
	private View view_top_line;
	private ImageView iv_expres_spot;
	private View view_top_line_down;
	private TextView trace_time;

	/**
	 * @param mainActivity
	 */
	public TrackTraceAdapter(Context ctx,
			List<TrackInfoBean.OrderStatuss> trackinfo, TrackPosition position) {
		mContext = ctx;
		this.trackinfo = trackinfo;
		this.trackPosition = position;
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
				R.layout.activity_track_trace, parent, false);
		TrackInfoBean.OrderStatuss info = trackinfo.get(getCount()-position-1);
		TextView tracetime_tv = (TextView) view.findViewById(R.id.tracetime_tv);
		TextView tracestatus_tv = (TextView) view
				.findViewById(R.id.tracestatus_tv);
		view_top_line = view.findViewById(R.id.view_top_line);
		view_top_line_down = view.findViewById(R.id.view_top_line_down);
		trace_time = (TextView) view.findViewById(R.id.trace_time);
		view_top_line_down.setBackgroundColor(color.gainsboro);
		iv_expres_spot = (ImageView) view.findViewById(R.id.iv_expres_spot);

		if(info.getOrderStatusText().indexOf("车辆到达") != -1){
			trace_time.setText("最后定位时间");
		}else{
			trace_time.setText("时间");
		}
		tracetime_tv.setText(TimeUtils.dateToStr(info.getRecordDate()));
		tracestatus_tv.setText(info.getOrderStatusText());

		//更新在途情况
		updateTrackPosition(view, info);
		
		if(position == 0){
			//透明
			view_top_line.setBackgroundColor(Color.TRANSPARENT);
			iv_expres_spot.setBackgroundResource(R.drawable.ponit_select_3);
			//tracetime_tv.setTextColor(view.getResources().getColor(R.color.track_ok));
			//tracestatus_tv.setTextColor(view.getResources().getColor(R.color.track_ok));
			tracetime_tv.setTextColor(Color.RED);
			tracestatus_tv.setTextColor(Color.RED);
		}else if(position == 1){
			//tracestatus_tv.setTextColor(Color.RED);
			view_top_line.setBackgroundColor(color.gainsboro);
			tracetime_tv.setTextColor(color.gainsboro);
			tracestatus_tv.setTextColor(color.gainsboro);
			iv_expres_spot.setBackgroundResource(R.drawable.ponit_unselect);
		}else{
			view_top_line.setBackgroundColor(color.gainsboro);
			tracetime_tv.setTextColor(color.gainsboro);
			tracestatus_tv.setTextColor(color.gainsboro);
			iv_expres_spot.setBackgroundResource(R.drawable.ponit_unselect);
		}

		return view;
	}

	private void updateTrackPosition(View view, TrackInfoBean.OrderStatuss info) {
		LinearLayout l = (LinearLayout) view.findViewById(R.id.dinamic_trace_linelayout);
		if (this.trackPosition != null && "06".equals(info.getOrderStatus())) {
			TextView positonTextV = (TextView) view.findViewById(R.id.tracePosition_tv);
			l.setVisibility(View.VISIBLE);
			String address = "";
			// 设置mTextView的文字
			if(!StringUtil.isBlank(this.trackPosition.getProvince())){
				address +=trackPosition.getProvince();
			}
			if(!StringUtil.isBlank(this.trackPosition.getCity())){
				address +=trackPosition.getCity();
			}
			positonTextV.setText(StringUtil.isEmpty(address)?"未能获取位置,检查车辆GPS是否开启!":address);
		}else{
			l.setVisibility(View.GONE);
		}
		view.invalidate();
	}

	public void setTracks(List<TrackInfoBean.OrderStatuss> trackinfo) {
		this.trackinfo = trackinfo;
	}

	public List<TrackInfoBean.OrderStatuss> getTrackinfo() {
		return trackinfo;
	}

	public void setTrackinfo(List<TrackInfoBean.OrderStatuss> trackinfo) {
		this.trackinfo = trackinfo;
	}

	public TrackPosition getPosition() {
		return trackPosition;
	}

	public void setPosition(TrackPosition position) {
		this.trackPosition = position;
	}

}
