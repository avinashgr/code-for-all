package com.covisint.iot.services.utils;

import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class GsonUtil {
	final static Logger logger = LoggerFactory.getLogger(GsonUtil.class);
	public GsonUtil() {
		super();
	}
	/**
	 * gets Elements from json as string
	 * @param newObj
	 * @param elementName
	 * @return
	 */
	protected String getElementAsString(String jsonContent, String elementName) {
		JsonElement json = new JsonParser().parse(jsonContent);
		if(json.isJsonPrimitive()){
			return json.getAsString();
		}
		JsonObject newObj = json.getAsJsonObject();
		String[] nodes = elementName.split(Pattern.quote("."),2);
		JsonElement elem = (nodes.length>0)?newObj.get(nodes[0]):newObj.get(elementName);
		String valueOfElement=null;
		if(null!=elem){
			if(elem.isJsonPrimitive()){
			  valueOfElement= elem.getAsString();
			}
			if(elem.isJsonArray()){
				String content =new Gson().toJson(elem);
				if(nodes.length>1){
				  JsonArray jarray = (JsonArray)elem;
				  valueOfElement = getValueOfNode(nodes, valueOfElement, jarray);				  
				}else{
					valueOfElement=content;
				}
			}
		}
	    return valueOfElement;
	}
	private String getValueOfNode(String[] nodes, String valueOfElement, JsonArray jarray) {
		String content;
		for(JsonElement el: jarray){
			  content =new Gson().toJson(el);
			  valueOfElement=getElementAsString(content, nodes[1]);	
			  if(null!=valueOfElement){
				  break;
			  }
		  }
		return valueOfElement;
	}
	/**
	 * adds the element to the array as a new value
	 * @param desc
	 * @param mapOfObjects
	 */
	protected void addElementsToArray(JsonArray desc, Map<String,String> mapOfObjects) {
	    JsonObject obj = new JsonObject();
		for(Map.Entry<String, String> entry : mapOfObjects.entrySet()){
			JsonPrimitive element = new JsonPrimitive(entry.getValue());
		    if(!entry.getKey().isEmpty()){
		      obj.add(entry.getKey(), element);
		    }else{
		    	obj.add("", element);
		    }
		}
	    desc.add(obj);
	}
	
	/**
	 * adds the element to the array as a new value
	 * @param desc
	 * @param mapOfObjects
	 */
	protected boolean addElementsToArray(String jsonContent, String elementName, Map<String,String> mapOfObjects) {
		JsonElement json = new JsonParser().parse(jsonContent);
		boolean result=false;
		if(json.isJsonPrimitive()){
			return false;
		}
		JsonObject newObj = json.getAsJsonObject();
		String[] nodes = elementName.split(Pattern.quote("."),2);
		JsonElement elem = newObj.get(nodes[0]);
		result=addObjects(elementName, mapOfObjects, elem);	
		jsonContent=new GsonBuilder().setPrettyPrinting().create().toJson(newObj);
		logger.debug("After adding elements:"+jsonContent);
		return result;
	}
	private boolean addObjects(String elementName, Map<String, String> mapOfObjects, JsonElement elem) {
		boolean valueOfElement=false;
		String[] nodes = elementName.split(Pattern.quote("."),2);
		if(elem.isJsonObject()){
			elem = ((JsonObject)elem).get(nodes[0]);
		}
		if(elem.isJsonArray()){
			JsonArray jarray = (JsonArray)elem;
			if(nodes.length<=1){
				  addElementsToArray(jarray, mapOfObjects);
				  valueOfElement=true;
			}else{
					for(JsonElement el: jarray){
						valueOfElement=addObjects(nodes[1],mapOfObjects,el);
						if(valueOfElement){
							break;
						}
					}
			}

		}
		return valueOfElement;
	}

	

}