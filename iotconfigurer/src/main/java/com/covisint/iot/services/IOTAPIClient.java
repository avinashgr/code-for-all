package com.covisint.iot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("iotapiclient")
public class IOTAPIClient {
	final static Logger logger = LoggerFactory.getLogger(IOTAPIClient.class);
	@Autowired
	@Qualifier("attrtypeclient")
	private AttributeTypeClient attributeClient;
	
	public void createAttributeType(){
		logger.debug("creating the attribute type:start");
		
	}
}
