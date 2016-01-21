package com.covisint.css.portal.utils;

import java.util.List;
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
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	final static Logger logger = LoggerFactory.getLogger(GsonUtil.class);
	public GsonUtil() {
		super();
	}
	/**
	 * Gets the object 
	 * @param content
	 * @param t
	 * @return
	 */
	public static <T> T getObjectFromContent(String content, Class<T> t){
		 Gson gson = new Gson();
		 T token = (T)gson.fromJson(content, t);
		 return token;
	}
	/**
	 * Gets the list of objects from the content
	 * @param content
	 * @param t
	 * @return
	 */
	public static <T> List<T> getListFromContent(String content){
		 Gson gson = new Gson();
		 List<T> token =  gson.fromJson(content, new TypeToken<List<T>>(){}.getType());
		 return token;
	}
	/**
	 * gets Elements from json as string
	 * @param newObj
	 * @param elementName
	 * @return
	 */
	protected String getElementFromArrayElementAsString(String jsonContent, String elementName, int indexOfItem) {
		JsonElement json = new JsonParser().parse(jsonContent);
		if(json.isJsonPrimitive()){
			return json.getAsString();
		}
		String valueOfElement ="";
		if(json.isJsonArray()){		
			JsonArray jsonArr = (JsonArray)json;
			JsonElement el = jsonArr.get(indexOfItem);
			valueOfElement = getValueFromObject(elementName,el);
		}
	    return valueOfElement;
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
		String valueOfElement ="";
		if(json.isJsonObject()){		
			valueOfElement=	getValueFromObject(elementName, json);
		}
	    return valueOfElement;
	}
	private String getValueFromObject(String elementName, JsonElement json) {
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
	protected<T> void addElementsToArray(JsonArray desc, Map<String,T> mapOfObjects) {
	    JsonObject obj = new JsonObject();
		for(Map.Entry<String, T> entry : mapOfObjects.entrySet()){
/*			JsonPrimitive element = new JsonPrimitive(entry.getValue().toString());
		    if(!entry.getKey().isEmpty()){
		      obj.add(entry.getKey(), element);
		    }else{
		    	obj.add("", element);
		    }*/
			obj = new JsonParser().parse(new Gson().toJson(entry.getValue())).getAsJsonObject();
		}
	    desc.add(obj);
	    logger.debug("After adding elements:"+prettyPrintJson(desc));
	}
	
	/**
	 * adds the element to the array as a new value
	 * @param desc
	 * @param mapOfObjects
	 */
	protected <T> boolean addElementsToArray(String jsonContent, String elementName, Map<String,T> mapOfObjects) {
		JsonElement json = new JsonParser().parse(jsonContent);
		boolean result=false;
		if(json.isJsonPrimitive()){
			return false;
		}
		JsonObject newObj = json.getAsJsonObject();
		String[] nodes = elementName.split(Pattern.quote("."),2);
		JsonElement elem = newObj.get(nodes[0]);
		result=addObjects(elementName, mapOfObjects, elem);	
		jsonContent=prettyPrintJson(newObj);
		logger.debug("After adding elements:"+jsonContent);
		return result;
	}
	private String prettyPrintJson(JsonObject newObj) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(newObj);
	}
	public String prettyPrintJson(JsonArray newObj) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(newObj);
	}
	private<T> boolean addObjects(String elementName, Map<String, T> mapOfObjects, JsonElement elem) {
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
	protected String updateElementFromArrayElementAsString(String jsonContent, String elementName, int indexOfItem, String toValue) {
		JsonElement json = new JsonParser().parse(jsonContent);
		String result="";
		if(json.isJsonPrimitive()){
			return json.getAsString();
		}
		if(json.isJsonArray()){		
			JsonArray jsonArr = (JsonArray)json;
			JsonElement el = jsonArr.get(indexOfItem);
			updateElementProperty(elementName, toValue, el);
		}
		result=new GsonBuilder().setPrettyPrinting().create().toJson(json);
		logger.debug("The converted content:"+result);
	    return result;
	}
	protected String deleteElementFromArrayElementAsString(String jsonContent, int indexOfItem) {
		JsonElement json = new JsonParser().parse(jsonContent);
		String result="";
		if(json.isJsonPrimitive()){
			return json.getAsString();
		}
		if(json.isJsonArray()){		
			JsonArray jsonArr = (JsonArray)json;
			jsonArr.remove(indexOfItem);
		
		}
		result=new GsonBuilder().setPrettyPrinting().create().toJson(json);
		logger.debug("The converted content:"+result);
	    return result;
	}
	/**
	 * Updates the element - removes the element and adds with a new value
	 * @param elementName
	 * @param toValue
	 * @param el
	 */
	private void updateElementProperty(String elementName, String toValue, JsonElement el) {
		JsonObject  obj= el.getAsJsonObject();
		obj.remove(elementName);
		obj.addProperty(elementName, toValue);
	}

	private static class Foo<T> {
	    final Class<T> typeParameterClass;

	    public Foo(Class<T> typeParameterClass) {
	        this.typeParameterClass = typeParameterClass;
	    }

	    public void bar() {
	        // you can access the typeParameterClass here and do whatever you like
	    }
	}

}