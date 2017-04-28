package com.nti.mlmep.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.myksoap2.serialization.SoapObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti.mlmep.R;
import com.nti.mlmep.activity.base.BaseActivity;
import com.nti.mlmep.domain.TrackInfoBean;
import com.nti.mlmep.util.WebServiceUtils;

/**
 * @author sunsi
 * ��������
 */
@SuppressLint("NewApi")
public class ServiceEvaluationActivity extends BaseActivity implements OnClickListener {

	private Button monitor_bt1;
	private Button monitor_bt2;
	private RadioButton mRadio4,mRadio7,mRadio10,mRadio13;
	private RadioGroup rGroup1,rGroup2,rGroup3,rGroup4;  
	
	private List strArr = new ArrayList();  
    private TextView view ;  
    private Spinner spinner;  
    private ArrayAdapter<String> adapter;  
    public Gson gson = new Gson();
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_monitor);  
        //ǿ��ʹ�����߳��������
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        monitor_bt1 = (Button) findViewById(R.id.monitor_bt1);  
        monitor_bt2 = (Button) findViewById(R.id.monitor_bt2);  
        rGroup1 = (RadioGroup) findViewById(R.id.gendergroup1);  
        rGroup2 = (RadioGroup) findViewById(R.id.gendergroup2);  
        rGroup3 = (RadioGroup) findViewById(R.id.gendergroup3);  
        rGroup4 = (RadioGroup) findViewById(R.id.gendergroup4);
        mRadio4 = (RadioButton) findViewById(R.id.radioButton4);
        mRadio7 = (RadioButton) findViewById(R.id.radioButton7);
        mRadio10 = (RadioButton) findViewById(R.id.radioButton10);
        mRadio13 = (RadioButton) findViewById(R.id.radioButton13);
        monitor_bt1.setOnClickListener(this);
        monitor_bt2.setOnClickListener(this);
        //view = (TextView) findViewById(R.id.spinnerText);  
        spinner = (Spinner) findViewById(R.id.Spinner01);  
        
        /**��ѯ�������۵Ķ���
		 * ����״̬transportStatus=08
		 * userName,password,��������Ϊ��
		 */
        WebServiceUtils wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(ServiceEvaluationActivity.this.getApplicationContext());
		wsutils.setMethodName("LogisticsTrackingForMobileEva");
		wsutils.setWstag("TrackService");
		//ƴ�Ӳ���JSON
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNumber", "");
		params.put("contractNumber", "");
		params.put("plateNumber", "");
		params.put("transportCode", "");
		params.put("transportStatus", "05");
		params.put("customName", "");
		params.put("startTime", "");
		params.put("endTime", "");
		SharedPreferences sharedPreferences = getSharedPreferences(
				"userInfo", Context.MODE_PRIVATE);
		params.put("userName", sharedPreferences.getString("username", ""));
		params.put("password", sharedPreferences.getString("password", ""));
		// ���ô������
		
		Map<String, Object> sendParams = new HashMap<String, Object>();
		sendParams.put("sendJson", gson.toJson(params));
		wsutils.setInfactParams(sendParams);

		SoapObject result = null;
		try {
			//����webservice����
			result = wsutils.visit();
			/**���ص�json������TrackInfoBean����
			 * 
			 */
			java.lang.reflect.Type type = new TypeToken<TrackInfoBean>() {}.getType();
			
            if(!result.getProperty(0).toString().equals("false")){
            	JSONArray arr = new JSONArray(result.getProperty(0).toString()); 
	            for (int i = 0; i < arr.length(); i++) {  
	                JSONObject temp = (JSONObject) arr.get(i);  
	                TrackInfoBean trackInfoBean = gson.fromJson(temp.toString(), type);
	                strArr.add("�����ţ�"+trackInfoBean.getOrderNumber());
	            }  
            }else{
            	strArr.add("û�п������۵Ķ���");
            }
            //strArr.remove("�����ţ�SO140800762");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			strArr.add("û�п������۵Ķ���");
			e.printStackTrace();
		}
        
        //����ѡ������ArrayAdapter��������  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,strArr);  
          
        //���������б�ķ��  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
          
        //��adapter ��ӵ�spinner��  
        spinner.setAdapter(adapter);  
          
        //����¼�Spinner�¼�����    
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
          
        //����Ĭ��ֵ  
        spinner.setVisibility(View.VISIBLE);  
          
    }  
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.monitor_bt1:
			//��ȡѡ�е�radio����
			if(adapter.getItem(spinner.getSelectedItemPosition()).equals("û�п������۵Ķ���")){
				new  AlertDialog.Builder(this).setTitle("��ʾ").setMessage("û�п������۵Ķ���").setPositiveButton("ȷ��",null ).show();
		    	
				break;
			}
			
			RadioButton radioButton1 = (RadioButton)findViewById(rGroup1.getCheckedRadioButtonId());
			RadioButton radioButton2 = (RadioButton)findViewById(rGroup2.getCheckedRadioButtonId());
			RadioButton radioButton3 = (RadioButton)findViewById(rGroup3.getCheckedRadioButtonId());
			RadioButton radioButton4 = (RadioButton)findViewById(rGroup4.getCheckedRadioButtonId());
			//ƴ��json
			Map<String, String> m1 = new HashMap<String, String>();
			m1.put("orderId", adapter.getItem(spinner.getSelectedItemPosition()).substring(4));
			m1.put("assessPersonId", getSharedPreferences(
					"userInfo", Context.MODE_PRIVATE).getString("userid", ""));
			m1.put("assessPersonName", getSharedPreferences(
					"userInfo", Context.MODE_PRIVATE).getString("username", ""));
			m1.put("totalEvaAssess", radioButton1.getTag().toString());
			m1.put("intimeAssess", radioButton2.getTag().toString());
			m1.put("intactAssess", radioButton3.getTag().toString());
			m1.put("serveAttitudeAssess", radioButton4.getTag().toString());
			//gson.toJson(m1);
			WebServiceUtils wsutils = WebServiceUtils.getInstance();
			wsutils.setContext(ServiceEvaluationActivity.this.getApplicationContext());
			wsutils.setMethodName("insertMobileEva");
			wsutils.setWstag("TrackService");
			// ���ô������
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("sendJson", gson.toJson(m1));
			wsutils.setInfactParams(m2);

			SoapObject result = null;
			try {
				ProgressDialog MyDialog = ProgressDialog.show( ServiceEvaluationActivity.this, "" , "�ύ��,���Ե� ...", true);
				result = wsutils.visit();
				
				if(result.getProperty(0).toString().equals("true")){
					//����ѡ������ArrayAdapter��������  
					
					strArr.remove(adapter.getItem(spinner.getSelectedItemPosition()));
					if(strArr.size()==0){
						strArr.add("û�п������۵Ķ���");
					}
			        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,strArr);  
			          
			        //���������б�ķ��  
			        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
			          
			        //��adapter ��ӵ�spinner��  
			        spinner.setAdapter(adapter);  
			          
			        //����¼�Spinner�¼�����    
			        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
			          
			        //����Ĭ��ֵ  
			        spinner.setVisibility(View.VISIBLE);  
					MyDialog.hide();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//String jsonParam = result.getProperty(0).toString();
			//new  AlertDialog.Builder(this).setTitle("����").setMessage(gson.toJson(m1)).setPositiveButton("ȷ��",null ).show();
	    	
			break;
		
		case R.id.monitor_bt2:
			rGroup1.check(mRadio4.getId()); 
			rGroup2.check(mRadio7.getId()); 
			rGroup3.check(mRadio10.getId()); 
			rGroup4.check(mRadio13.getId()); 
			break;
		}
	}
      
    //ʹ��������ʽ����  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
            //view.setText();  
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
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
