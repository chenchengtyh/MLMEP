package com.nti.mlmep.util;

import java.io.Serializable;
import java.util.Map;

public class SerializableMap implements Serializable {
	private static final long serialVersionUID = 628651494450174690L;
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
