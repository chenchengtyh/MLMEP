package com.nti.mlmep.activity.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nti.mlmep.R;

/**
 * @author sunsi
 */
public abstract class BaseBackActivity extends BaseActivity {

	private Button back;
	private TextView title;
	
	protected abstract void setContentView();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		setTitle();
		init();
	}

	private void setTitle() {
		// 设置title
		title = (TextView) findViewById(R.id.title_name);
		title.setText(getTitleName());
	}

	protected abstract String getTitleName();

	private void init() {
		findView();
		setListener();

	}

	private void setListener() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				back();
			}
		});
	}

	// 通用返回�?��调用
	protected void back() {
		finish();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	private void findView() {
		back = (Button) findViewById(R.id.title_back);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return false;
	}
}