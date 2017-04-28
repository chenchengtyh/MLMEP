package com.nti.mlmep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.content.Context;

public class PropertiesUtil {
	public static Properties getProperties(Context c,String path) {
		Properties props = new Properties();
		try {
			if(path.equals("assets")){
				InputStream in = c.getAssets().open("setting.properties");
				props.load(in);
			}else{
				InputStream in = new FileInputStream(getSettingFile(path));
				props.load(in);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return props;
	}
	
//	public static void setProperties(String param, String value) {
//		Properties props = new Properties();
//		try {
//			props.load(new FileInputStream(getSettingFile()));
//
//			OutputStream out = new FileOutputStream(FileUtils.setting);
//			
//			props.setProperty(param, value);
//			props.store(out, null);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private static File getSettingFile(String path){
		File setting = new File(path);
		if(!setting.exists())
			try {
				setting.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return setting;
	}

}
