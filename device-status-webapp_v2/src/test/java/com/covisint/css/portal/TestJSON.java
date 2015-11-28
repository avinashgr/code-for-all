package com.covisint.css.portal;

import org.testng.annotations.Test;

import com.google.gson.Gson;

public class TestJSON {
  @Test
  public void testHelloMessage() {
	  DeviceMessage hello = new DeviceMessage();
	  hello.setMessage("TestJson");
	  System.out.println("The json format:"+new Gson().toJson(hello));	  
  }

}
