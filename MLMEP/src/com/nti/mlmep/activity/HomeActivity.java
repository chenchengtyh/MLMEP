package com.nti.mlmep.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nti.mlmep.R;
import com.nti.mlmep.activity.base.BaseActivity;
import com.nti.mlmep.util.AppLog;

/**
 * @author sunsi
 */
public class HomeActivity extends BaseActivity implements OnClickListener {

	private Button bt2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		bt2 = (Button) findViewById(R.id.bt2);
		bt2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt2:
			  //loginButton.setBackgroundColor(Color.YELLOW);
			//String username = usernameEditText.getText().toString().trim();
			//String password = passwordEditText.getText().toString().trim();
			AppLog.debug("bt2", "bt2"+"---------"+"bt2");
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(),
					TrackActivity.class);
			startActivity(intent);
			//login(username, password);
			break;
		}
	}
	
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		
	}

}