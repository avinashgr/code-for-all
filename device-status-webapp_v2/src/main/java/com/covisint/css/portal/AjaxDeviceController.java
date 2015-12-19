package com.covisint.css.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.covisint.css.portal.utils.MessageValidator;
import com.covisint.iot.stream.AjaxMQTTStreamClientImpl;
import com.google.gson.Gson;
/**
 * Controller for the AJAX requests coming to the webapp
 * @author aranjalkar
 *
 */
@CrossOrigin({"https://presales-ilab.portal.covapp.io","http://device-status-ui.run.covapp.io","http://my.covapp.io:8080"})
@Controller
public class AjaxDeviceController{
	final static Logger logger = LoggerFactory.getLogger(AjaxDeviceController.class);
    @Qualifier("ajaxmqttservice")
    @Autowired
    private AjaxMQTTStreamClientImpl mqttClient;

	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void publish(HttpEntity<String> httpEntity) {
		logger.debug("Publishing message to device topic");
		String deviceMessage = httpEntity.getBody();
		DeviceMessage device = new Gson().fromJson(deviceMessage, DeviceMessage.class);
		if(!MessageValidator.isMessageValid(device)){
			logger.debug("Unable to publish message to device topic");
		}else{
			mqttClient.initializeMQTTConnection(device.getPublishToTopic(),device.getAppId());
			mqttClient.publishCommand(device,device.getAppId());
		}
	}
	@RequestMapping(value = "/devicelog",method = RequestMethod.GET)	
	public @ResponseBody DeviceResponse[] chatlog(@RequestParam("topic") String topic, @RequestParam("appId") String appId ) {
		if(!MessageValidator.checkForTopicAndApp(topic, appId)){
			return createResponse("Invalid topic/app"); 
		}
		mqttClient.initializeMQTTConnection(topic,appId);
		mqttClient.subscribeAndInitializeTopic(topic,appId);
		Queue<DeviceResponse> deviceMessages = mqttClient.getMessages(topic);
		List<DeviceResponse> devices = new ArrayList<DeviceResponse>();

		if(null==deviceMessages || deviceMessages.isEmpty()){
			return createResponse("No message yet");
		}else{
			for (Iterator<DeviceResponse> iterator = deviceMessages.iterator(); iterator.hasNext();) {
				DeviceResponse device = (DeviceResponse) iterator.next();
				logger.info("Devicelog:logging device message:"+device.getContent());
				devices.add(device);
			};
			DeviceResponse[] array = new DeviceResponse[devices.size()];
			devices.toArray(array);
			return (array);	
		}
	}
	private DeviceResponse[] createResponse(String responseMessage) {
		DeviceResponse d = new DeviceResponse(responseMessage);
		DeviceResponse[] array = new DeviceResponse[1];
		array[0] = d;
		return array;
	}
	@RequestMapping(value = "/stopLog", method = RequestMethod.POST)	
	@ResponseStatus(value = HttpStatus.OK)
	public void stopLogs(HttpEntity<String> httpEntity) {
		logger.info("stopping the logs");
		String deviceMessage = httpEntity.getBody();
		DeviceMessage device = new Gson().fromJson(deviceMessage, DeviceMessage.class);
		mqttClient.stopSubscribe(device.getPublishToTopic(),device.getAppId());
	}


}