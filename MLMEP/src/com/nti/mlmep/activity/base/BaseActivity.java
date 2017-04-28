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
 * Activity基类
 * 定义一个初始化Activity控件的抽象方法initWidget()
 * 可以调用setAllowFullScreen设置是否取消标题
 * 实现了OnClickListener，子类可以不必实现
 * 添加打印Activity生命周期
 * @author wangge
 */
public abstract class BaseActivity extends Activity implements
        OnClickListener {
    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;
 
    public int activityState;
 
    // 是否允许全屏
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
     * 打印Activity生命周期
     * 
     ***************************************************************************/
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.debug(this.getClass().getName() , "---------onCreat ");
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
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
		//menu.add(Menu.NONE,0,0,"退出");
		return true;
	}
    
    public boolean onOptionsItemSelected(MenuItem item) {  
        // TODO Auto-generated method stub  
        switch(item.getItemId()){  
          
           case R.id.item1:  
        	    Toast.makeText(getApplicationContext(), "退出应用", 1).show();
        	    AppManager.getAppManager().finishAllActivity();
        	    System.exit(0);
        	    break;
           }  
//        
        return true;  
    }  

}