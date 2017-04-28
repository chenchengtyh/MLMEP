package com.nti.mlmep.util;

import java.util.Map;
import java.util.Properties;

import org.myksoap2.SoapEnvelope;
import org.myksoap2.serialization.SoapObject;
import org.myksoap2.serialization.SoapSerializationEnvelope;
import org.myksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

@SuppressLint("NewApi")
public class WebServiceUtils {
	// 方法名称
	private String methodName;

	private Context context;
	// 原始参数
	private Map<String, Object> originParams;

	// 最终查询的时候实际传入到后台的参数
	private Map<String, Object> infactParams;

	// 服务名
	private String wstag;
	// 类型分割
	private String wsType;

	// 不同的版本调用webservice需要设置,4.0以前版本不需要设置
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
	}

	private WebServiceUtils() {

	}

	public static WebServiceUtils getInstance() {
		return new WebServiceUtils();
	}

	/**
	 * 访问webservice统一方法
	 * 
	 * @return SoapObject
	 */
	public SoapObject visit() throws Exception {
		if ((context == null) || (TextUtils.isEmpty(wstag))
				|| (TextUtils.isEmpty(methodName))) {
			return null;
		}
		String nameSpace;
		String endPoint;
		String soapAction;
		Properties pro = PropertiesUtil.getProperties(context, "assets");
		if (!StringUtil.isEmpty(this.wsType)) {
			nameSpace = pro.getProperty("ws." + this.wsType + ".nameSpace");
			endPoint = pro.getProperty("ws." + this.wsType + "." + wstag
					+ ".endPoint");
		} else {
			nameSpace = pro.getProperty("ws.nameSpace");
			endPoint = pro.getProperty("ws." + wstag + ".endPoint");
		}
		soapAction = nameSpace + methodName;
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 传入调用参数
		if (infactParams != null && !infactParams.isEmpty()) {
			for (String key : infactParams.keySet()) {
				rpc.addProperty(key, infactParams.get(key));
			}
		}

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版�?
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		envelope.dotNet = false;

		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);
		//120000
		HttpTransportSE transport = new HttpTransportSE(endPoint, 60000);
		transport.call(soapAction, envelope);

		SoapObject object = (SoapObject) envelope.bodyIn;
		return object;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getWstag() {
		return wstag;
	}

	public void setWstag(String wstag) {
		this.wstag = wstag;
	}

	public Map<String, Object> getOriginParams() {
		return originParams;
	}

	public void setOriginParams(Map<String, Object> originParams) {
		this.originParams = originParams;
	}

	public Map<String, Object> getInfactParams() {
		return infactParams;
	}

	public void setInfactParams(Map<String, Object> infactParams) {
		this.infactParams = infactParams;
	}

	public String getWsType() {
		return wsType;
	}

	public void setWsType(String wsType) {
		this.wsType = wsType;
	}

}
