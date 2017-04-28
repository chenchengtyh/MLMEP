package com.nti.mlmep.component;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nti.mlmep.R;

public class RoundCornerListView extends ListView {

	private List<Map<Integer, String>> datas;

	// private int resource;

	public RoundCornerListView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RoundCornerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public RoundCornerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int itemnum = pointToPosition(x, y);

			if (itemnum == AdapterView.INVALID_POSITION)
				break;
			else {
				if (itemnum == 0) {
					if (itemnum == (getAdapter().getCount() - 1)) {
						setSelector(R.drawable.listview_round_corner_selector);
					} else {
						setSelector(R.drawable.listview_round_corner_top_selector);
					}
				} else if (itemnum == (getAdapter().getCount() - 1))
					setSelector(R.drawable.listview_round_corner_bottom_selector);
				else {
					setSelector(R.drawable.listview_round_corner_medium_selector);
				}
			}

			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	public List<Map<Integer, String>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<Integer, String>> datas) {
		this.datas = datas;
	}

}
