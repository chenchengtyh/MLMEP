package com.nti.mlmep.activity.base;

import com.nti.mlmep.R;
import com.nti.mlmep.util.AppLog;
import com.nti.mlmep.util.AppManager;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

/**
 * Activity����
 * ����һ����ʼ��Activity�ؼ��ĳ��󷽷�initWidget()
 * ���Ե���setAllowFullScreen�����Ƿ�ȡ������
 * ʵ����OnClickListener��������Բ���ʵ��
 * ��Ӵ�ӡActivity��������
 * @author wangge
 */
public abstract class BaseActivity extends Activity implements
        OnClickListener {
    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;
 
    public int activityState;
 
    // �Ƿ�����ȫ��
    private boolean mAllowFullScreen = true;
 
    public abstract void initWidget();
 
    public abstract void widgetClick(View v);
 
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }
 
    @Override
    public void onClick(View v) {
        widgetClick(v);
    }
 
    /***************************************************************************
     * 
     * ��ӡActivity��������
     * 
     ***************************************************************************/
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.debug(this.getClass().getName() , "---------onCreat ");
        // ��������
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // ȡ������
        }
        AppManager.getAppManager().addActivity(this);
        initWidget();
    }
 
    @Override
    protected void onStart() {
        super.onStart();
        AppLog.debug(this.getClass().getName(), "---------onStart ");
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
        AppLog.debug(this.getClass().getName(), "---------onResume ");
    }
 
    @Override
    protected void onStop() {
        super.onStop();
        activityState = ACTIVITY_STOP;
        AppLog.debug(this.getClass().getName(), "---------onStop ");
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
        AppLog.debug(this.getClass().getName(), "---------onPause ");
    }
 
    @Override
    protected void onRestart() {
        super.onRestart();
        AppLog.debug(this.getClass().getName(), "---------onRestart ");
    }
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
        AppLog.debug(this.getClass().getName(), "---------onDestroy ");
        AppManager.getAppManager().finishActivity(this);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		//menu.add(Menu.NONE,0,0,"�˳�");
		return true;
	}
    
    public boolean onOptionsItemSelected(MenuItem item) {  
        // TODO Auto-generated method stub  
        switch(item.getItemId()){  
          
           case R.id.item1:  
        	    Toast.makeText(getApplicationContext(), "�˳�Ӧ��", 1).show();
        	    AppManager.getAppManager().finishAllActivity();
        	    System.exit(0);
        	    break;
           }  
//        
        return true;  
    }  

}