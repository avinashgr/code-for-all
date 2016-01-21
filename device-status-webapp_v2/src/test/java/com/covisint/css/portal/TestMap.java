package com.covisint.css.portal;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class TestMap {
  @Test
  public void f() {
	  Map<String, String> mp = new HashMap<String, String>();
	  mp.put("a", "hello");
	  mp.put("b", "hello");
	  System.out.println("Get a:"+mp.get("a"));
	  System.out.println("Get c:"+mp.get("c"));
	  mp.remove("c");
  }
}
