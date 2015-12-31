package com.covisint.iot.services.vo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.covisint.iot.services.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestAttributeType extends GsonUtil {
	final static Logger logger = LoggerFactory.getLogger(TestAttributeType.class);
	static String content="";
	@BeforeTest
    public void init(){
		String fileName = "AttributeType.json";
		content = getFileAsString(fileName);
    }
	@Test
	public void testFromJSONToAttributeType() {
		AttributeType attributeType = new Gson().fromJson(content, AttributeType.class);
		Assert.assertNotNull(attributeType);
	}
	
	@Test
	public void testGetElementsFromJSON(){
		String valueOfElement = getElementAsString(content, "creator");
		Assert.assertNotNull(valueOfElement);
		valueOfElement = getElementAsString(content, "blahblah");
		Assert.assertNull(valueOfElement);
		valueOfElement = getElementAsString(content, "items");
		Assert.assertNotNull(valueOfElement);
		valueOfElement = getElementAsString(content, "items.mangoes");
		Assert.assertNotNull(valueOfElement);
		valueOfElement = getElementAsString(content, "items.mangoes.indian");
		Assert.assertNotNull(valueOfElement);
	}
	@Test
	public void testAddElementsToJSONArray(){
		JsonObject newObj = new JsonParser().parse(content).getAsJsonObject();
	    JsonArray desc = newObj.getAsJsonArray("description");
	    HashMap<String,String> mapOfKVPairs = new HashMap<String,String>();
	    mapOfKVPairs.put("lang", "en_US");
	    addElementsToArray(desc,mapOfKVPairs);
	    Assert.assertNotNull(desc, "decription is null");
	}
	
	@Test
	public void testAddElementsToNestedJSONArray(){
	    HashMap<String,String> mapOfKVPairs = new HashMap<String,String>();
	    mapOfKVPairs.put("avinash", "en_US");
	    mapOfKVPairs.put("hello", "en_Fr");
	    boolean result =addElementsToArray(content,"items.mangoes.indian",mapOfKVPairs);
	    Assert.assertNotNull(result, "decription is null");
	}
	@Test
	public void testAddElementsToNestedJSONArray2(){
	    HashMap<String,String> mapOfKVPairs = new HashMap<String,String>();
	    mapOfKVPairs.put("potato", "en_US");
	    boolean result =addElementsToArray(content,"items.potatoes.idaho",mapOfKVPairs);
	    Assert.assertNotNull(result, "decription is null");
	}

	private String getFileAsString(String fileName) {
		String content = "";
		InputStream file = ClassLoader.getSystemResourceAsStream(fileName);
		try {
			content = IOUtils.toString(file);
			logger.debug("Content:" + content);
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
}
