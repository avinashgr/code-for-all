package com.covisint.css.portal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.covisint.css.portal.DeviceMessage;

public class MessageValidator {
	final static Logger logger = LoggerFactory.getLogger(MessageValidator.class);
	/**
	 * Validates a message for topic, appid, message
	 * @param deviceMessage
	 * @return
	 */
	public static boolean isMessageValid(DeviceMessage deviceMessage) {
		logger.info("Message validation -start");
		String topic = deviceMessage.getPublishToTopic();
		String appId = deviceMessage.getAppId();
		if(StringUtils.isEmpty(topic)){
			topic= deviceMessage.getSubscribeToTopic();
		}
		if(!checkForTopicAndApp(topic, appId)){
			logger.error("failed check for topic and appId");
			return false;
		}
		if( StringUtils.isEmpty(deviceMessage.getMessage())){
			logger.error("The message is empty");
			return false;
		}
		logger.info("The request validation completed successfully");
		return true;
	}
	/**
	 * Checks for empty topic and app
	 * @param topic
	 * @param appId
	 * @return
	 */
	public static boolean checkForTopicAndApp(String topic, String appId) {
		logger.info("topic and appid validation -start");
		if( StringUtils.isEmpty(topic)){
			logger.error("topic is empty");
			return false;
		}
		if( StringUtils.isEmpty(appId)){
			logger.error("appId is empty");
			return false;
		}
		return true;
	}
}
