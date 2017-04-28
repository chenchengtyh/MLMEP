package com.nti.mlmep.util;

import java.util.ArrayList;

import com.nti.mlmep.activity.Task;

public class TestUtil {
	
	private static ArrayList<Task>allActivity;
	
	public static void removeActivity(){
		allActivity = null;
		allActivity = new ArrayList<Task>();
	}
	
	public static void addActivity(Task iw){
		allActivity.add(iw);
	}
	
	public static Task getActivityByName(String name){
		for(Task iw:allActivity){
			if(iw.getClass().getName().indexOf(name)>=0){
				return iw;
			}
		}
		return null;
	}

}
