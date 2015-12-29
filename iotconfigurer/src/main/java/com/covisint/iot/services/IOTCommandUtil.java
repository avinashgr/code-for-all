package com.covisint.iot.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOTCommandUtil {
	public static void main(String[] args) {
		ApplicationContext appContext;
		if (args.length > 0 && args[0].equalsIgnoreCase("create")) {
			appContext = new ClassPathXmlApplicationContext(new String[] { "app-properties.xml" });
			IOTAPIClient client =(IOTAPIClient)appContext.getBean("iotapiclient");
			client.createAttributeType();
			System.out.println("done!");
		}
	}
}
