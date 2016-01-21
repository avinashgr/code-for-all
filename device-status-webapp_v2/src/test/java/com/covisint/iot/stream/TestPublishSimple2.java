package com.covisint.iot.stream;

import java.util.Base64;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.covisint.css.portal.DeviceMessage;
import com.covisint.iot.stream.test.RandomizeUtils;
import com.google.gson.GsonBuilder;

public class TestPublishSimple2 {
  public static void main(String[] args) {
	  ClassPathXmlApplicationContext cp= new ClassPathXmlApplicationContext("application-config.xml");
	  MQTTStreamClientImpl sh = (MQTTStreamClientImpl) cp.getBean("mqttservice");
	  try{
		  for(int i=0;i<=100;i++){
			  System.out.println("Here "+i);
			  sendSampleMessage(sh); 
			  Thread.sleep(3000);
		  }
	  }catch(InterruptedException ex){
		  System.out.println("Interrupted");
	  }
  }

private static void sendSampleMessage(MQTTStreamClientImpl sh) {
	DeviceMessage deviceMessage = new DeviceMessage();

	  MessageContent content = new MessageContent();
	  content.setCommandId("CommandTSSequenceNum1100112");
	  content.setDeviceId("7e57a97c-4e1a-49c6-a2b1-6304507c633e");
	  content.setCommandId("9b6ea254-8089-445b-85a4-248c635e4d4e");
	  content.setMessage(createSampleMessage());
	  content.setEncodingType("base64");

	  deviceMessage.setPublishToTopic("cb829305-3805-48ee-b834-db9ddb75bf43");
	  deviceMessage.setMessage((new GsonBuilder().disableHtmlEscaping().create()).toJson(content));
	  deviceMessage.setAppId("6ef69644-d2f3-4fab-97b0-45550fb3e374");
	  sh.initializeMQTTConnection(deviceMessage.getPublishToTopic(), deviceMessage.getAppId());
	  sh.publishCommand(deviceMessage,deviceMessage.getAppId());
}

private static String createSampleMessage() {
	
	String message = "{"+"\""+"SetTemperature"+":"+RandomizeUtils.randomInt(100)+","+"\""+"SetHumidity"+"\""+":13"+"}";
	String decodedBytes = Base64.getEncoder().encodeToString( message.getBytes());
	return decodedBytes;
}

}
