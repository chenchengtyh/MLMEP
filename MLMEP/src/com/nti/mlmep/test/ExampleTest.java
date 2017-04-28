package com.nti.mlmep.test;

import java.util.HashMap;
import java.util.Map;

import org.myksoap2.SoapEnvelope;
import org.myksoap2.serialization.SoapObject;
import org.myksoap2.serialization.SoapSerializationEnvelope;
import org.myksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StrictMode;
import android.test.AndroidTestCase;

import com.nti.mlmep.util.WebServiceUtils;

@SuppressLint("NewApi")
public class ExampleTest extends AndroidTestCase {
	final static String SERVICE_NS = "http://impl.service.ws.lemp.nti56.com/";
	final static String SERVICE_URL = "http://61.190.39.3:8081/giscxf/ReportLocationWebService?wsdl";

	static {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			// 4.0以后需要加入下列两行代码，才可以访问Web Service
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());

			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
		}
		// 4.0以前版本不需要以上设置
	}

	public void testSave() {
		WebServiceUtils wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(this.getContext());
		wsutils.setMethodName("getPlaceInfoByCarNumber");
		wsutils.setWstag("LocationService");
		wsutils.setWsType("gis");
		Map map = new HashMap<String, Object>();

		/**
		 * <soapenv:Header/> <soapenv:Body> <ser:getPlaceInfoByCarNumber>
		 * <!--Optional:--> <arg0>皖B14745</arg0> </ser:getPlaceInfoByCarNumber>
		 * </soapenv:Body>
		 */

		String request = "<Header><Body></Header";
		map.put("arg0", "皖B14745");
		wsutils.setInfactParams(map);

		try {
			SoapObject result = wsutils.visit();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test2() {
		// 调用的方法
		String methodName = "getPlaceInfoByCarNumber";
		// 创建httpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug = true;
		// 使用soap1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// 实例化SoapObject对象
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		/**
		 * 设置参数，参数名不一定需要跟调用的服务器端的参数名相同，只需要对应的顺序相同即可
		 * */
		request.addProperty("arg0", "皖B14745");
		// 将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
		envelope.bodyOut = request;
		try {
			// 调用webService
			ht.call(null, envelope);
			// txt1.setText("看看"+envelope.getResponse());
			if (envelope.getResponse() != null) {
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				System.out.println(name);
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void test4() {
		WebServiceUtils wsutils = WebServiceUtils.getInstance();
		wsutils.setContext(getContext());
		wsutils.setMethodName("userLogin");
		wsutils.setWstag("LoginService");

		// 设置传入参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "admin");
		params.put("password", "yszdds");

		try {
			SoapObject result = WebServiceUtils.getInstance().visit();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		wsutils.setInfactParams(params);
	}

}
