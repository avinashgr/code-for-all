package com.covisint.iot.stream;

import java.util.Base64;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.covisint.css.portal.DeviceMessage;
import com.covisint.iot.stream.test.RandomizeUtils;
import com.google.gson.GsonBuilder;


public class TestPublishSimple {
  public static void main(String[] args) {
	  ClassPathXmlApplicationContext cp= new ClassPathXmlApplicationContext("application-config.xml");
	  MQTTStreamClientImpl sh = (MQTTStreamClientImpl) cp.getBean("mqttservice");
	  try{
		  for(int i=0;i<=100;i++){
			  System.out.println("Here "+i);
			  sendSampleMessage(sh); 
			  Thread.sleep(1000);
		  }
	  }catch(InterruptedException ex){
		  System.out.println("Interrupted");
	  }
  }

private static void sendSampleMessage(MQTTStreamClientImpl sh) {
	DeviceMessage deviceMessage = new DeviceMessage();

	  MessageContent content = new MessageContent();
	  content.setCommandId("CommandTSSequenceNum1100112");
	  content.setDeviceId("f6d4bb54-cb61-47d7-be2e-b8b3c81c3c28");
	  content.setCommandId("5a59525e-106d-4ac0-9f0c-1dffe8b7a3cb");
	  content.setMessage(createSampleMessage());
	  content.setEncodingType("base64");

	  deviceMessage.setPublishToTopic("f8dbc3f8-6140-471e-b18e-72ffa289f3a8");
	  deviceMessage.setMessage((new GsonBuilder().disableHtmlEscaping().create()).toJson(content));
	  deviceMessage.setAppId("fa72d353-3ccb-4776-9529-767f3741454e");
	  sh.initializeMQTTConnection(deviceMessage.getPublishToTopic(), deviceMessage.getAppId());
	  sh.publishCommand(deviceMessage,deviceMessage.getAppId());
}

private static String createSampleMessage() {
	
	String message = "{"+"\""+"SetTemperature"+":"+RandomizeUtils.randomInt(100)+","+"\""+"SetHumidity"+"\""+":13"+"}";
	String decodedBytes =Base64.getEncoder().encodeToString( message.getBytes());
	return decodedBytes;
}

}
