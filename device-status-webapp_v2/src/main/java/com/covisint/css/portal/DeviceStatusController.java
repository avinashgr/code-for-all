package com.covisint.css.portal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.covisint.iot.stream.MQTTStreamClientImpl;
/**
 * Controller for the requests for STOMP
 * @author aranjalkar
 *
 */
@Controller
@MessageMapping("/stomp/device")
public class DeviceStatusController {
	final static Logger logger = LoggerFactory.getLogger(DeviceStatusController.class);
    @Qualifier("mqttservice")
    @Autowired
    private MQTTStreamClientImpl mqttClient;
    
    @MessageMapping("publish")
	public void publish(DeviceMessage deviceMessage) {
		logger.debug("Publishing message to device topic");
		logger.info("The request body has:"+deviceMessage);
		mqttClient.initializeMQTTConnection(deviceMessage.getPublishToTopic(),deviceMessage.getAppId());
		mqttClient.publishCommand(deviceMessage,deviceMessage.getAppId());
	}
    
    @MessageMapping("subscribe/{topicid}")
    @SendTo("/topic/{topicid}")
	public void subscribe(DeviceMessage deviceMessage,@DestinationVariable("topicid") String topicid) {
		logger.debug("Subscribing to device topic");
		logger.info("The request body has:"+deviceMessage);
		mqttClient.initializeMQTTConnection(deviceMessage.getPublishToTopic(),deviceMessage.getAppId());
		mqttClient.subscribeAndInitializeTopic(deviceMessage.getPublishToTopic(),deviceMessage.getAppId());
	}


}
/**
 * change log
 * changed the getstatus to stomp/deviceLog 
 */
