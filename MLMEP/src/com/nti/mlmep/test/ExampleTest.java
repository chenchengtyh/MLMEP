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
			// 4.0�Ժ���Ҫ�����������д��룬�ſ��Է���Web Service
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());

			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
		}
		// 4.0��ǰ�汾����Ҫ��������
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
		 * <!--Optional:--> <arg0>��B14745</arg0> </ser:getPlaceInfoByCarNumber>
		 * </soapenv:Body>
		 */

		String request = "<Header><Body></Header";
		map.put("arg0", "��B14745");
		wsutils.setInfactParams(map);

		try {
			SoapObject result = wsutils.visit();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test2() {
		// ���õķ���
		String methodName = "getPlaceInfoByCarNumber";
		// ����httpTransportSE�������
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug = true;
		// ʹ��soap1.1Э�鴴��Envelop����
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// ʵ����SoapObject����
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		/**
		 * ���ò�������������һ����Ҫ�����õķ������˵Ĳ�������ͬ��ֻ��Ҫ��Ӧ��˳����ͬ����
		 * */
		request.addProperty("arg0", "��B14745");
		// ��SoapObject��������ΪSoapSerializationEnvelope����Ĵ���SOAP��Ϣ
		envelope.bodyOut = request;
		try {
			// ����webService
			ht.call(null, envelope);
			// txt1.setText("����"+envelope.getResponse());
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

		// ���ô������
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
