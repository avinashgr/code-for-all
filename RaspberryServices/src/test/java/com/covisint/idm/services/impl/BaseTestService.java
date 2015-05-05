package com.covisint.idm.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTestService {
	static final Logger logger = LoggerFactory.getLogger(BaseTestService.class);
	protected ApplicationContext appContext;

	@BeforeTest
	public void beforeTest() {
		String[] filenames = new String[] { "app-properties.xml" };
		appContext = new ClassPathXmlApplicationContext(filenames);
		logger.debug("Loaded the application context");
	}

	@AfterTest
	public void afterTest() {
		
	}

}
