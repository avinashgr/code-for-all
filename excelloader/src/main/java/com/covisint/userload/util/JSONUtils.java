package com.covisint.userload.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.covisint.userload.vo.UserVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONUtils {
	
	private static JSONUtils instance;

	private JSONUtils() {
	};

	public static JSONUtils getInstance() {
		if (null == instance) {
			instance = new JSONUtils();
		}
		return instance;
	}

	public <T> T deserialize(String jsonString, Type clazz) {
		return new Gson().fromJson(jsonString, clazz);
	}
	
	public String toString(List<Map<String, Object>> list){
		return new Gson().toJson(list);	
	}

	public  <T> List<T> getListOfVO(String json) {
		Type listType = new TypeToken<ArrayList<T>>() {}.getType();
		List<T> l = getInstance().deserialize(json, listType);
		return l;
	}
}
