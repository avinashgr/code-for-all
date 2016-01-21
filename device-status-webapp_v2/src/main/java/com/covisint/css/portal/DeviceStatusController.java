package com.covisint.css.portal;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.covisint.css.portal.utils.MessageValidator;
import com.covisint.css.portal.utils.StreamConfigUtil;
import com.covisint.iot.stream.MQTTStreamClientImpl;
/**
 * Controller for the requests for STOMP
 * @author aranjalkar
 *
 */
@Controller
@MessageMapping("/stomp/device")
public class DeviceStatusController {
	@Autowired
	ServletContext servletContext;
	final static Logger logger = LoggerFactory.getLogger(DeviceStatusController.class);
    @Qualifier("mqttservice")
    @Autowired
    private MQTTStreamClientImpl mqttClient;
    @PostConstruct
    public void initStreamConfig(){
    	new StreamConfigUtil().setPath(servletContext.getRealPath("/WEB-INF/classes/streaminfo.json"));
    } 
    @MessageMapping("publish")
	public void publish(DeviceMessage deviceMessage) {
		logger.debug("Publishing message to device for:"+deviceMessage);
		if(!MessageValidator.isMessageValid(deviceMessage)){
			return;
		}
		if(mqttClient.initializeMQTTConnection(deviceMessage.getPublishToTopic(),deviceMessage.getAppId())){
			return;
		}
		mqttClient.publishCommand(deviceMessage,deviceMessage.getAppId());
	}
    


	@MessageMapping("subscribe/{topicid}")
    @SendTo("/topic/{topicid}")
	public void subscribe(DeviceMessage deviceMessage,@DestinationVariable("topicid") String topicid) {
		logger.debug("Subscribing to device topic");
		logger.info("The request body has:"+deviceMessage);
		if(!MessageValidator.checkForTopicAndApp(deviceMessage.getPublishToTopic(),deviceMessage.getAppId())){
			return; 
		}
		if(mqttClient.initializeMQTTConnection(deviceMessage.getPublishToTopic(),deviceMessage.getAppId())){
			return;
		}
		mqttClient.subscribeAndInitializeTopic(deviceMessage.getPublishToTopic(),deviceMessage.getAppId());
	}


}
/**
 * change log
 * changed the getstatus to stomp/deviceLog 
 */
